package net.cyborgcabbage.neoboom.block;

import net.cyborgcabbage.neoboom.block.bombtype.*;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.PlaceableTileEntity;
import net.minecraft.level.Level;
import net.modificationstation.stationloader.api.common.block.BlockItemProvider;
import net.modificationstation.stationloader.impl.common.preset.item.PlaceableTileEntityWithMeta;

import java.util.Random;

public class BombPower extends BlockBase implements BlockItemProvider {
    public String prefix;
    public float power; //With NeoExplosion, power is the approximate radius of the crater (in dirt)
    public int fuseLength;
    public static BombType[] BOMB_TYPES = new BombType[]{
            new Bomb(),//0
            new LifeBomb(),//2
            new LevellingBomb(),//4
            new StarBomb(),//6
            new ColdBomb(),//8
            new ExtinguishingBomb(),//10
            new FireBomb(),//12
            new MagneticBomb()//14
    };

    public BombPower(float power, int fuse, String prefix, int id, Material material){
        super(id,material);
        this.prefix = prefix;
        this.power = power;
        this.fuseLength = fuse;
    }

    public PlaceableTileEntity getBlockItem(int id) {
        return new PlaceableTileEntityWithMeta(id){
            @Override
            public String getTranslationKey(ItemInstance item) {
                return this.getTranslationKey() + item.getDamage()/2;
            }
        };
    }

    protected int droppedMeta(int i) {
        return i;
    }

    public static BombType getType(int meta){
        return BOMB_TYPES[meta/2];
    }

    @Override
    public int getTextureForSide(int side, int meta) {
        if (side == 0) return getType(meta).getTextureForBottom(prefix);
        if (side == 1) return getType(meta).getTextureForTop(prefix);
        return getType(meta).getTextureForSide(prefix);
    }

    @Override
    public void onBlockPlaced(Level level, int x, int y, int z) {
        super.onBlockPlaced(level, x, y, z);
        if (level.hasRedstonePower(x, y, z)) {
            this.method_1612(level, x, y, z, 1 | level.getTileMeta(x,y,z));
            level.setTile(x, y, z, 0);
        }

    }

    @Override
    public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int id) {
        if (id > 0 && BlockBase.BY_ID[id].emitsRedstonePower() && level.hasRedstonePower(x, y, z)) {
            this.method_1612(level, x, y, z, 1 | level.getTileMeta(x,y,z));
            level.setTile(x, y, z, 0);
        }

    }

    @Override
    public int getDropCount(Random rand) {
        return 0;
    }

    public void onDestroyedByExplosion(Level level, int x, int y, int z, int meta) {
        PrimedBomb primedBomb = new PrimedBomb(level, this.fuseLength, this.id, meta, (float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        primedBomb.fuse = level.rand.nextInt(primedBomb.fuse / 4) + primedBomb.fuse / 8;
        level.spawnEntity(primedBomb);
    }

    @Override
    public void method_1612(Level level, int x, int y, int z, int meta) {
        if (!level.isClient) {
            if ((meta & 1) == 0) {
                this.drop(level, x, y, z, new ItemInstance(this.id, 1, meta));
            } else {
                PrimedBomb primedBomb = new PrimedBomb(level, this.fuseLength, this.id, meta, (float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
                level.spawnEntity(primedBomb);
                level.playSound(primedBomb, "random.fuse", 1.0F, 1.0F);
            }
        }
    }

    @Override
    public void activate(Level level, int x, int y, int z, PlayerBase player) {
        if (player.getHeldItem() != null && player.getHeldItem().itemId == ItemBase.flintAndSteel.id) {
            level.method_223(x, y, z, 1 | level.getTileMeta(x,y,z));
        }

        super.activate(level, x, y, z, player);
    }
}
