/*package net.cyborgcabbage.neoboom.block;

import net.cyborgcabbage.neoboom.block.bombtype.BombType;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.NeoExplosion;
import net.cyborgcabbage.neoboom.level.SingleRayProvider;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;

public class BoomStick extends BlockBase {
    public BoomStick(String registryName, int id, Material material) {
        super(registryName, id, material);
        this.fuseLength = 1;
        this.power = 27.0f;
    }

    public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
        level.setTileMeta(x, y, z, facing*2);
    }

    @Override
    public void method_1612(Level level, int x, int y, int z, int meta) {
        if (!level.isClient) {
            if ((meta & 1) == 0) {
                this.drop(level, x, y, z, new ItemInstance(this.id, 1, 0));
            } else {
                PrimedBomb primedBomb = new PrimedBomb(level,this, meta, (float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);
                level.spawnEntity(primedBomb);
                level.playSound(primedBomb, "random.fuse", 1.0F, 1.0F);
            }
        }
    }

    public void explode(PrimedBomb entity){
        NeoExplosion neoExplosion = new NeoExplosion(entity.level, null,entity.x,entity.y,entity.z,this.power);
        SingleRayProvider rayProvider;
        switch(entity.tileMeta/2){
            case 0: rayProvider = new SingleRayProvider(0,1,0); break;
            case 1: rayProvider = new SingleRayProvider(0,-1,0); break;
            case 2: rayProvider = new SingleRayProvider(0,0,1); break;
            case 3: rayProvider = new SingleRayProvider(0,0,-1); break;
            case 4: rayProvider = new SingleRayProvider(1,0,0); break;
            default: rayProvider = new SingleRayProvider(-1,0,0); break;
        }
        neoExplosion.explode(rayProvider,0.0f,true,false,false,true,0.0f,true);
    }
}
*/