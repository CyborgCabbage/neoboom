package net.cyborgcabbage.neoboom.listeners;

import com.google.common.collect.Maps;
import net.cyborgcabbage.neoboom.NeoBoom;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationloader.api.common.event.item.ItemRegister;
import java.util.Map;
import java.util.Set;

public class ItemListener implements ItemRegister {
    private static final Map<String, ItemBase> ITEMS = Maps.newHashMap();
    private static Set<Integer> occupiedIDs;
    private static int startID = 3300;

    @Override
    public void registerItems() {
        NeoBoom.configItems.load();
        occupiedIDs = NeoBoom.configItems.getSet("items");

        occupiedIDs = null;
        NeoBoom.configItems.save();
    }

    private static int getID(String name) {
        while ((ItemBase.byId[startID + BlockBase.BY_ID.length] != null || occupiedIDs.contains(startID)) && startID < ItemBase.byId.length) {
            startID++;
        }
        return NeoBoom.configBlocks.getInt("items." + name, startID);
    }

    @SuppressWarnings("unchecked")
    public static <T extends ItemBase> T getItem(String name) {
        return (T) ITEMS.get(name);
    }
}
