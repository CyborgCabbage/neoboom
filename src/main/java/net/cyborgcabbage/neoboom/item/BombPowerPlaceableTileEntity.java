package net.cyborgcabbage.neoboom.item;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.PlaceableTileEntity;

public class BombPowerPlaceableTileEntity extends PlaceableTileEntity {
    private final String name;
    public BombPowerPlaceableTileEntity(int i, BombPower bombPower) {
        super(i);
        this.setDurability(0);
        this.setHasSubItems(true);
        this.name = bombPower.getName();
    }

    @Environment(EnvType.CLIENT)
    public int getTexturePosition(int damage) {
        return BlockListener.getBlock(name).getTextureForSide(3, damage);
    }

    public int getMetaData(int i) {
        return i;
    }

    @Environment(EnvType.CLIENT)
    public String getTranslationKey(ItemInstance item) {
        return super.getTranslationKey();// + "." + Dye.NAMES[net.minecraft.block.Wool.getColour(item.getDamage())];
    }
}
