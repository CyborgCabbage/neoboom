package net.cyborgcabbage.neoboom.listeners;

import com.google.common.collect.Maps;
import net.cyborgcabbage.neoboom.NeoBoom;
import net.cyborgcabbage.neoboom.block.BombPower;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationloader.api.common.event.block.BlockRegister;

import java.util.Map;
import java.util.Set;

public class BlockListener implements BlockRegister {
    private static final Map<String, BlockBase> BLOCKS = Maps.newHashMap();
    private static Set<Integer> occupiedIDs;
    private static int startID = 170;
    @Override
    public void registerBlocks() {
        NeoBoom.configBlocks.load();
        occupiedIDs = NeoBoom.configBlocks.getSet("blocks");

        BLOCKS.put("normal_bomb", new BombPower(4.0f,80,"normal",getID("normal_bomb"), Material.TNT).setName("normal_bomb"));
        BLOCKS.put("compressed_bomb", new BombPower(16.0f,160,"compressed",getID("compressed_bomb"), Material.TNT).setName("compressed_bomb"));
        BLOCKS.put("nuclear_bomb", new BombPower(64.0f,320,"nuclear",getID("nuclear_bomb"), Material.TNT).setName("nuclear_bomb"));

        occupiedIDs = null;
        NeoBoom.configBlocks.save();
    }
    private static int getID(String name) {
        while ((BlockBase.BY_ID[startID] != null || occupiedIDs.contains(startID)) && startID < BlockBase.BY_ID.length) {
            startID++;
        }
        return NeoBoom.configBlocks.getInt("blocks." + name, startID);
    }

    @SuppressWarnings("unchecked")
    public static <T extends BlockBase> T getBlock(String name) {
        return (T) BLOCKS.getOrDefault(name, BlockBase.STONE);
    }
}
