package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.ColdExplosion;
import net.cyborgcabbage.neoboom.level.GoldenRayProvider;
import net.cyborgcabbage.neoboom.level.NeoExplosion;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.cyborgcabbage.neoboom.listeners.TextureListener;
import net.minecraft.block.BlockBase;

public class ColdBomb  extends BombType {
    @Override
    public String getName() {
        return "cold";
    }

    @Override
    public void explode(PrimedBomb entity) {
        ColdExplosion explosion = new ColdExplosion(entity.level, null, entity.x, entity.y, entity.z, ((BombPower)BlockBase.BY_ID[entity.blockId]).power);
        explosion.explode(new GoldenRayProvider(), 0.1f, true, false, true, true, 0.0f, true,0,0.0f);
    }

    @Override
    public int getTextureForSide(String prefix) {
        //if (prefix == "normal") return BlockBase.TNT.texture;
        return TextureListener.getBlockTexture(prefix + "_cold_bomb_side");
    }

    @Override
    public int getTextureForTop(String prefix) {
        if (prefix == "normal") return BlockBase.TNT.texture + 1;
        return TextureListener.getBlockTexture(prefix + "_bomb_top");
    }

    @Override
    public int getTextureForBottom(String prefix) {
        if (prefix == "normal") return BlockBase.TNT.texture + 2;
        return TextureListener.getBlockTexture(prefix + "_bomb_bottom");
    }
}
