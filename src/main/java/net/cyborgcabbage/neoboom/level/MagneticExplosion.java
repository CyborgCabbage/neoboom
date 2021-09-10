package net.cyborgcabbage.neoboom.level;

import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.minecraft.util.maths.TilePos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MagneticExplosion extends NeoExplosion{
    public MagneticExplosion(Level level, EntityBase cause, double x, double y, double z, float power) {
        super(level, cause, x, y, z, power);
    }

    @Override
    public void destroyBlocks(int replacementBlock, float dropChance) {
        ArrayList<TilePos> tiles = new ArrayList<>(this.damagedTiles);
        List blockList = Arrays.asList(
                BlockBase.REDSTONE_DUST.id,
                BlockBase.REDSTONE_TORCH_LIT.id,
                BlockBase.REDSTONE_TORCH.id,
                BlockBase.REDSTONE_REPEATER_LIT.id,
                BlockBase.REDSTONE_REPEATER.id
        );
        for(int tile_index = tiles.size() - 1; tile_index >= 0; --tile_index) {
            TilePos tilePos = tiles.get(tile_index);
            int tileId = this.level.getTileId(tilePos.x, tilePos.y, tilePos.z);
            if (tileId > 0) {
                if (blockList.contains(tileId)) {
                    int meta = this.level.getTileMeta(tilePos.x, tilePos.y, tilePos.z);
                    BlockBase.BY_ID[tileId].beforeDestroyedByExplosion(this.level, tilePos.x, tilePos.y, tilePos.z, meta, dropChance);
                    this.level.setTile(tilePos.x, tilePos.y, tilePos.z, replacementBlock);
                    BlockBase.BY_ID[tileId].onDestroyedByExplosion(this.level, tilePos.x, tilePos.y, tilePos.z);
                }
            }
        }
    }
}
