package net.cyborgcabbage.neoboom.level;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.minecraft.util.maths.MathHelper;
import net.minecraft.util.maths.TilePos;
import net.minecraft.util.maths.Vec3f;

import java.util.*;

public class NeoExplosion {
    protected final Random random = new Random();
    protected final Level level;
    public double x;
    public double y;
    public double z;
    public EntityBase cause;
    public float power;
    public HashSet<TilePos> damagedTiles = new HashSet<>();

    public NeoExplosion(Level level, EntityBase cause, double x, double y, double z, float power) {
        this.level = level;
        this.cause = cause;
        this.power = power;
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public void explode(RayProvider rayProvider, float dropChance, boolean destroyBlocks, boolean harmEntities, boolean pushEntities, boolean createParticles, float fireChance, boolean createSound){
        this.explode(rayProvider, dropChance, destroyBlocks, harmEntities,  pushEntities, createParticles, fireChance, createSound, 0,0.3f);
    }

    public void explode(RayProvider rayProvider, float dropChance, boolean destroyBlocks, boolean harmEntities, boolean pushEntities, boolean createParticles, float fireChance, boolean createSound, int replacementBlock, float randomisation){
        if (fireChance > 0.0 || destroyBlocks) this.updateDamagedTiles(rayProvider,randomisation);
        this.effectEntities(harmEntities, pushEntities);
        if (destroyBlocks) this.destroyBlocks(replacementBlock,dropChance);
        if (fireChance > 0.0) this.createFires(fireChance);
        if (createParticles) this.createParticles();
        if (createSound) this.createSound();
    }
    public void explode() {
        this.explode(new GoldenRayProvider());
    }
    public void explode(RayProvider rayProvider) {
        this.explode(rayProvider,0.3F,true,true,true,true, 0.0f, true);
    }

    public void alternateUpdateDamagedTiles(RayProvider rayProvider, float randomisation) {
        damagedTiles.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);

        for (ExplosionRay ray: rays) {
            ray.power = ray.multiplier * ray.multiplier * this.power * this.power * 7.5f;
            ray.power *= ((1.0f-randomisation) + this.level.rand.nextFloat() * (2.0f*randomisation));
            ray.pos = Vec3f.from(this.x,this.y,this.z);
            ray.distance = 0.3f;
        }
        float step_size = 0.3f;
        int ray_count = rays.size();
        int active_ray_count = ray_count;
        float previous_average_blast_resistance = 1.0f;
        while(active_ray_count > 0){
            float total_blast_resistance = 0.0f;
            active_ray_count = 0;
            for (int i=0; i<ray_count; i++) {
                ExplosionRay ray = rays.get(i);
                if(ray.power > 0.3f){
                    int block_x = MathHelper.floor(ray.pos.x);
                    int block_y = MathHelper.floor(ray.pos.y);
                    int block_z = MathHelper.floor(ray.pos.z);
                    int tile_id = this.level.getTileId(block_x, block_y, block_z);
                    if (tile_id > 0) {
                        float blast_resistance = BlockBase.BY_ID[tile_id].getBlastResistance(this.cause) + 0.3F;
                        ray.power -= (blast_resistance+2.0f*(blast_resistance+previous_average_blast_resistance)) * step_size;
                        total_blast_resistance += blast_resistance;
                    }else{
                        ray.power -= (0.0f+2.0f*(0.0f+previous_average_blast_resistance)) * step_size;
                    }

                    if (ray.power > 0.0F) {
                        damagedTiles.add(new TilePos(block_x, block_y, block_z));
                        active_ray_count++;
                    }

                    ray.pos.x += ray.dir.x * (double)step_size;
                    ray.pos.y += ray.dir.y * (double)step_size;
                    ray.pos.z += ray.dir.z * (double)step_size;

                    ray.power *= Math.pow(ray.distance/(ray.distance+step_size),2.0); //applies inverse square law incrementally
                    ray.distance += step_size;
                }
            }
            previous_average_blast_resistance = total_blast_resistance/(float)active_ray_count;
        }
    }

    public void effectEntities(boolean harm, boolean push) {
        if (!harm && !push) return;
        double damagingRange = this.power * 2.0;
        int min_x = MathHelper.floor(this.x - damagingRange - 1.0D);
        int max_x = MathHelper.floor(this.x + damagingRange + 1.0D);
        int min_y = MathHelper.floor(this.y - damagingRange - 1.0D);
        int max_y = MathHelper.floor(this.y + damagingRange + 1.0D);
        int min_z = MathHelper.floor(this.z - damagingRange - 1.0D);
        int max_z = MathHelper.floor(this.z + damagingRange + 1.0D);
        List inBounds = this.level.getEntities(this.cause, Box.method_94( min_x,  min_y,  min_z,  max_x,  max_y,  max_z));
        Vec3f explosionOrigin = Vec3f.from(this.x, this.y, this.z);

        for (Object inBound : inBounds) {
            EntityBase entity = (EntityBase) inBound;
            double distanceFraction = entity.method_1350(this.x, this.y, this.z) / damagingRange;
            if (distanceFraction <= 1.0D) { //Is distance less than damagingRange?
                double vecX = entity.x - this.x;
                double vecY = entity.y - this.y;
                double vecZ = entity.z - this.z;
                double length = MathHelper.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
                vecX /= length;
                vecY /= length;
                vecZ /= length;
                //Calculates what fraction of an entity is exposed to the explosion?
                double exposedFraction = this.level.method_163(explosionOrigin, entity.boundingBox);
                double impactForce = (1.0D - distanceFraction) * exposedFraction;
                if (harm) {
                    entity.damage(this.cause, (int) ((impactForce * impactForce + impactForce) / 2.0D * 8.0D * damagingRange + 1.0D));
                }
                if (push) {
                    entity.velocityX += vecX * impactForce;
                    entity.velocityY += vecY * impactForce;
                    entity.velocityZ += vecZ * impactForce;
                }
            }
        }
    }
    public void createFires(float fireChance) {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);
        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            int tileBelowId = this.level.getTileId(tilePos.x, tilePos.y - 1, tilePos.z);
            if ((tileId == 0 || tileId == BlockBase.SNOW.id) && BlockBase.FULL_OPAQUE[tileBelowId]) {
                if(this.random.nextFloat() < fireChance) {
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, BlockBase.FIRE.id);
                }
            }
        }
    }

    public void createSound(){
        this.level.playSound(this.x, this.y, this.z, "random.explode", 4.0F, (1.0F + (this.level.rand.nextFloat() - this.level.rand.nextFloat()) * 0.2F) * 0.7F);
    }

    public void createParticles() {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);

        for (int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            double particle_x = (float) tilePos.x + this.level.rand.nextFloat();
            double particle_y = (float) tilePos.y + this.level.rand.nextFloat();
            double particle_z = (float) tilePos.z + this.level.rand.nextFloat();
            double vec_x = particle_x - this.x;
            double vec_y = particle_y - this.y;
            double vec_z = particle_z - this.z;
            double distance = MathHelper.sqrt(vec_x * vec_x + vec_y * vec_y + vec_z * vec_z);
            vec_x /= distance;
            vec_y /= distance;
            vec_z /= distance;
            double inverse_distance = 0.5D / (distance / (double) this.power + 0.1D); //is 5 at the centre, decays as you move away
            inverse_distance *= this.level.rand.nextFloat() * this.level.rand.nextFloat() + 0.3F;
            vec_x *= inverse_distance;
            vec_y *= inverse_distance;
            vec_z *= inverse_distance;
            this.level.addParticle("explode", (particle_x + this.x) / 2.0D, (particle_y + this.y) / 2.0D, (particle_z + this.z) / 2.0D, vec_x, vec_y, vec_z);
            this.level.addParticle("smoke", particle_x, particle_y, particle_z, vec_x, vec_y, vec_z);
        }
    }

    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);

        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            if (tileId > 0) {
                int meta = this.level.getTileMeta(tilePos.x, tilePos.y, tilePos.z);
                BlockBase.BY_ID[tileId].beforeDestroyedByExplosion(this.level, tilePos.x, tilePos.y, tilePos.z, meta, dropChance);
                if (tileId == BlockBase.ICE.id) {
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, BlockBase.FLOWING_WATER.id);
                } else {
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, replacementBlock);
                }
                if(BlockBase.BY_ID[tileId] instanceof BombPower){
                    ((BombPower)BlockBase.BY_ID[tileId]).onDestroyedByExplosion(this.level, tilePos.x, tilePos.y, tilePos.z, meta);
                }else{
                    BlockBase.BY_ID[tileId].onDestroyedByExplosion(this.level, tilePos.x, tilePos.y, tilePos.z);
                }

            }else{
                this.level.setTile(tilePos.x, tilePos.y, tilePos.z, replacementBlock);
            }
        }
    }

    public void updateDamagedTiles(RayProvider rayProvider, float randomisation) {
        damagedTiles.clear();
        ArrayList<ExplosionRay> rays = rayProvider.getRays((int)(this.power*this.power)*8);
        for (ExplosionRay explosionRay: rays) {
            Vec3f dir = explosionRay.dir;
            float ray_power = explosionRay.multiplier * explosionRay.multiplier * this.power * this.power * 7.5f;
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
                    ray_power -= (BlockBase.BY_ID[tile_id].getBlastResistance(this.cause) + 0.3F) * step_size;
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
}