package net.cyborgcabbage.neoboom.entity;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.EnvironmentInterface;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationloader.api.server.entity.CustomTracking;
@EnvironmentInterface(value = EnvType.SERVER, itf = CustomTracking.class)
public class PrimedBomb extends EntityBase implements CustomTracking {
    public int fuse;
    public int tileMeta;
    public int blockId;

    public PrimedBomb(Level arg) {
        super(arg);
    }

    public PrimedBomb(Level arg, int blockId, int tileMeta) {
        super(arg);
        this.fuse = 0;
        this.field_1593 = true;
        this.setSize(0.98F, 0.98F);
        this.standingEyeHeight = this.height / 2.0F;
        this.tileMeta = tileMeta;
        this.blockId = blockId;
    }

    public PrimedBomb(Level arg, int fuse, int blockId, int tileMeta, double x, double y, double z) {
        this(arg, blockId, tileMeta);
        this.setPosition(x, y, z);
        float var8 = (float)(Math.random()*Math.PI*2.0);
        this.velocityX = (double)(-MathHelper.sin(var8 * 3.1415927F / 180.0F) * 0.02F);
        this.velocityY = 0.2D;
        this.velocityZ = (double)(-MathHelper.cos(var8 * 3.1415927F / 180.0F) * 0.02F);
        this.fuse = fuse;
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    protected void initDataTracker() {
    }

    protected boolean canClimb() {
        return false;
    }

    public boolean method_1356() {
        return !this.removed;
    }

    public void tick() {
        this.prevX = this.x;
        this.prevY = this.y;
        this.prevZ = this.z;
        this.velocityY -= 0.04;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.velocityX *= 0.98;
        this.velocityY *= 0.98;
        this.velocityZ *= 0.98;
        if (this.onGround) {
            this.velocityX *= 0.7;
            this.velocityZ *= 0.7;
            this.velocityY *= -0.5;
        }

        if (this.fuse-- <= 0) {
            if (!this.level.isClient) {
                this.remove();
                BombPower.getType(this.tileMeta).explode(this);
            } else {
                this.remove();
            }
        } else {
            this.level.addParticle("smoke", this.x, this.y + 0.5D, this.z, 0.0D, 0.0D, 0.0D);
        }

    }

    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("Fuse", this.fuse);
        tag.put("Meta", this.tileMeta);
        tag.put("Block", this.blockId);
    }

    protected void readCustomDataFromTag(CompoundTag tag) {
        this.fuse = tag.getInt("Fuse");
        this.tileMeta = tag.getInt("Meta");
        this.blockId = tag.getInt("Block");
    }

    @Environment(EnvType.CLIENT)
    public float getEyeHeight() {
        return 0.0F;
    }


    @Override
    @Environment(EnvType.SERVER)
    public int getTrackingDistance() {
        return 100;
    }

    @Override
    @Environment(EnvType.SERVER)
    public int getUpdateFrequency() {
        return 10;
    }

    @Override
    public boolean sendVelocity() {
        return true;
    }
}
