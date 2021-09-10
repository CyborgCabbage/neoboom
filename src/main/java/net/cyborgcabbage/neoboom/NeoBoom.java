package net.cyborgcabbage.neoboom;

import net.cyborgcabbage.neoboom.listeners.*;
import net.modificationstation.stationloader.api.client.event.render.entity.EntityRendererRegister;
import net.modificationstation.stationloader.api.client.event.texture.TextureRegister;
import net.modificationstation.stationloader.api.common.event.block.BlockRegister;
import net.modificationstation.stationloader.api.common.event.entity.EntityRegister;
import net.modificationstation.stationloader.api.common.event.item.ItemRegister;
import net.modificationstation.stationloader.api.common.lang.I18n;
import net.modificationstation.stationloader.api.common.mod.StationMod;

import java.util.Locale;

public class NeoBoom implements StationMod {
    public static final String MOD_ID = "neoboom";
    public static JsonConfig configBlocks = new JsonConfig("blocks");
    public static JsonConfig configItems = new JsonConfig("items");
    @Override
    public void preInit() {
        TextureRegister.EVENT.register(new TextureListener());
        BlockRegister.EVENT.register(new BlockListener());
        ItemRegister.EVENT.register(new ItemListener());
        EntityRegister.EVENT.register(new EntityListener());
        EntityRendererRegister.EVENT.register(new EntityRendererListener());
        RecipeListener.EVENT.register(new RecipeListener());
    }

    public static String getID(String name) {
        return MOD_ID + "." + name;
    }

    public static String getTexturePath(String category) {
        return String.format(Locale.ROOT, "/assets/%s/textures/%s/", MOD_ID, category);
    }

    public static String getTexturePath(String category, String name) {
        return String.format(Locale.ROOT, "/assets/%s/textures/%s/%s.png", MOD_ID, category, name);
    }
}
