package net.cyborgcabbage.neoboom.level;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.TilePos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColdExplosion extends NeoExplosion{
    public ColdExplosion(Level level, EntityBase cause, double x, double y, double z, float power) {
        super(level, cause, x, y, z, power);
    }

    @Override
    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);
        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            int belowTileId = this.level.getTileId(tilePos.x, tilePos.y-1, tilePos.z);
            if (tileId == 0 && belowTileId > 0) {
                if (belowTileId == BlockBase.STILL_WATER.id || belowTileId == BlockBase.FLOWING_WATER.id) {
                    this.level.setTile(tilePos.x, tilePos.y-1, tilePos.z, BlockBase.ICE.id);
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, BlockBase.SNOW.id);
                }else if(BlockBase.BY_ID[belowTileId].isFullCube()){
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, BlockBase.SNOW.id);
                }
            }
        }
    }
}
