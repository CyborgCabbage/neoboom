package net.cyborgcabbage.neoboom.level;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Sapling;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.level.structure.BirchTree;
import net.minecraft.level.structure.OakTree;
import net.minecraft.level.structure.SpruceTree;
import net.minecraft.util.maths.TilePos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LifeExplosion extends NeoExplosion{

    public LifeExplosion(Level level, EntityBase cause, double x, double y, double z, float power) {
        super(level, cause, x, y, z, power);
    }

    @Override
    public void destroyBlocks(int replacementBlock, float dropChance) {
        List blockList = Arrays.asList(
                BlockBase.STONE.id,
                BlockBase.DIRT.id,
                BlockBase.COBBLESTONE.id,
                BlockBase.WOOD.id,
                BlockBase.SAND.id,
                BlockBase.GRAVEL.id,
                BlockBase.SANDSTONE.id,
                BlockBase.DOUBLE_STONE_SLAB.id,
                BlockBase.STONE_SLAB.id,
                BlockBase.BRICK.id,
                BlockBase.MOSSY_COBBLESTONE.id,
                BlockBase.OBSIDIAN.id,
                BlockBase.STAIRS_WOOD.id,
                BlockBase.STAIRS_STONE.id,
                BlockBase.SNOW_BLOCK.id,
                BlockBase.NETHERRACK.id,
                BlockBase.SOUL_SAND.id
        );

        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);
        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            if(tileId > 0){
                if (blockList.contains(tileId)) {
                    int aboveTileId = this.level.getTileId(tilePos.x, tilePos.y+1, tilePos.z);
                    if(aboveTileId == 0 || aboveTileId == BlockBase.SNOW.id){
                        this.level.setTile(tilePos.x, tilePos.y, tilePos.z, BlockBase.GRASS.id);
                        this.level.setTile(tilePos.x, tilePos.y+1, tilePos.z, 0);

                        switch(this.random.nextInt(4)) {
                            case 0:
                                switch (this.random.nextInt(3)) {
                                    case 0:
                                        new BirchTree().generate(this.level, this.level.rand, tilePos.x, tilePos.y + 1, tilePos.z);
                                        break;
                                    case 1:
                                        new SpruceTree().generate(this.level, this.level.rand, tilePos.x, tilePos.y + 1, tilePos.z);
                                        break;
                                    default:
                                        new OakTree().generate(this.level, this.level.rand, tilePos.x, tilePos.y + 1, tilePos.z);
                                        break;
                                }
                                break;
                            case 1:
                                switch(this.random.nextInt(3)) {
                                    case 0:
                                        level.placeBlockWithMetaData(tilePos.x, tilePos.y + 1, tilePos.z, BlockBase.TALLGRASS.id, 1);
                                        break;
                                    case 1:
                                        level.setTile(tilePos.x, tilePos.y + 1, tilePos.z, BlockBase.DANDELION.id);
                                        break;
                                    case 2:
                                        level.setTile(tilePos.x, tilePos.y + 1, tilePos.z, BlockBase.ROSE.id);
                                        break;
                                }
                                break;
                            case 2:
                                int meta = this.level.rand.nextInt(3);
                                this.level.setTileWithMetadata(tilePos.x, tilePos.y + 1, tilePos.z, BlockBase.LEAVES.id, meta);
                                if(this.level.getTileId(tilePos.x, tilePos.y+2, tilePos.z) == 0 && this.random.nextInt(2) == 0){
                                    this.level.setTileWithMetadata(tilePos.x, tilePos.y + 2, tilePos.z, BlockBase.LEAVES.id, meta);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }
}
