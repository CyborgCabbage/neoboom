package net.cyborgcabbage.neoboom.listeners;

import net.cyborgcabbage.neoboom.client.entity.render.PrimedBombRenderer;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PrimedTntRenderer;
import net.minecraft.entity.EntityBase;
import net.modificationstation.stationloader.api.client.event.render.entity.EntityRendererRegister;

import java.util.Map;

public class EntityRendererListener implements EntityRendererRegister {
    @Override
    public void registerEntityRenderers(Map<Class<? extends EntityBase>, EntityRenderer> map) {
        map.put(PrimedBomb.class, new PrimedBombRenderer());
    }
}
