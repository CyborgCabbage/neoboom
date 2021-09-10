package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.GoldenRayProvider;
import net.cyborgcabbage.neoboom.level.MagneticExplosion;
import net.cyborgcabbage.neoboom.level.NeoExplosion;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.cyborgcabbage.neoboom.listeners.TextureListener;
import net.minecraft.block.BlockBase;

public class MagneticBomb extends BombType {
    @Override
    public String getName() {
        return "magnetic";
    }

    @Override
    public void explode(PrimedBomb entity) {
        MagneticExplosion explosion = new MagneticExplosion(entity.level, null, entity.x, entity.y, entity.z, ((BombPower)BlockBase.BY_ID[entity.blockId]).power);
        explosion.explode(new GoldenRayProvider(), 0.0f, true, false, false, true, 0.0f, true, BlockBase.FIRE.id,0.1f);
    }

    @Override
    public int getTextureForSide(String prefix) {
        return TextureListener.getBlockTexture(prefix + "_magnetic_bomb_side");
    }

    @Override
    public int getTextureForTop(String prefix) {
        return TextureListener.getBlockTexture(prefix + "_magnetic_bomb_top");
    }

    @Override
    public int getTextureForBottom(String prefix) {
        return TextureListener.getBlockTexture(prefix + "_magnetic_bomb_bottom");
    }
}
