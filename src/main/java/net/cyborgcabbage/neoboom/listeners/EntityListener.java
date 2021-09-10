package net.cyborgcabbage.neoboom.listeners;

import com.google.common.collect.Maps;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.EntityRegistry;
import net.modificationstation.stationloader.api.common.event.entity.EntityRegister;
import uk.co.benjiweber.expressions.functions.TriConsumer;

import java.util.Map;
import java.util.Set;

public class EntityListener implements EntityRegister {
    private static int startID = 87;
    @Override
    public void registerEntities(TriConsumer<Class<? extends EntityBase>, String, Integer> triConsumer) {
        triConsumer.accept(PrimedBomb.class,"PrimedBomb",startID++);
    }
}
