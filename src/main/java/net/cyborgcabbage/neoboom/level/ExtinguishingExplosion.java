package net.cyborgcabbage.neoboom.level;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.TilePos;
import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;

public class ExtinguishingExplosion extends NeoExplosion {
    public ExtinguishingExplosion(Level level, EntityBase cause, double x, double y, double z, float power) {
        super(level, cause, x, y, z, power);
    }

    @Override
    public void updateDamagedTiles(RayProvider rayProvider, float randomisation) {
        damagedTiles.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);
        for (ExplosionRay explosionRay: rays) {
            Vec3f dir = explosionRay.dir;
            float ray_power = explosionRay.multiplier * explosionRay.multiplier * this.power * this.power * 5.0f;
            ray_power *= ((1.0f-randomisation) + this.level.rand.nextFloat() * (2.0f*randomisation)); //Randomly sample between 70% and 130% of the power
            double ray_x = this.x;
            double ray_y = this.y;
            double ray_z = this.z;
            float step_size = 0.3F;
            float distance = 0.0f;
            while (ray_power > 0.3F) {
                int block_x = MathHelper.floor(ray_x);
                int block_y = MathHelper.floor(ray_y);
                int block_z = MathHelper.floor(ray_z);
                int tile_id = this.level.getTileId(block_x, block_y, block_z);
                if (tile_id > 0) {
                    if(tile_id == BlockBase.STILL_LAVA.id || tile_id == BlockBase.FLOWING_LAVA.id){
                        ray_power -= (BlockBase.DIRT.getBlastResistance(this.cause) + 0.3F) * step_size;
                    }else {
                        ray_power -= (BlockBase.BY_ID[tile_id].getBlastResistance(this.cause) + 0.3F) * step_size;
                    }
                }

                if (ray_power > 0.0F) {
                    damagedTiles.add(new TilePos(block_x, block_y, block_z));
                }

                ray_x += dir.x * (double) step_size;
                ray_y += dir.y * (double) step_size;
                ray_z += dir.z * (double) step_size;
                distance += step_size;
                //ray_power -= step_size;
                ray_power *= Math.pow(distance/(distance+step_size),2.0); //applies inverse square law incrementally
            }
        }
    }

    @Override
    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);
        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            if (tileId > 0) {
                if(tileId == BlockBase.FIRE.id){
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z,0);
                }else if(tileId == BlockBase.STILL_LAVA.id || tileId == BlockBase.FLOWING_LAVA.id){
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z,BlockBase.STONE.id);
                }
            }
        }
    }
}
