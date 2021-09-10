package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.GoldenRayProvider;
import net.cyborgcabbage.neoboom.level.LifeExplosion;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.cyborgcabbage.neoboom.listeners.TextureListener;
import net.minecraft.block.BlockBase;

public class LifeBomb extends BombType{
    @Override
    public String getName() {
        return "life";
    }

    @Override
    public void explode(PrimedBomb entity) {
        LifeExplosion explosion = new LifeExplosion(entity.level, null, entity.x, entity.y, entity.z, ((BombPower)BlockBase.BY_ID[entity.blockId]).power);
        explosion.explode(new GoldenRayProvider(),0.1f,true,false,true,true,0.0f,true);
    }

    @Override
    public int getTextureForSide(String prefix) {
        return TextureListener.getBlockTexture(prefix+"_life_bomb_side");
    }

    @Override
    public int getTextureForTop(String prefix) {
        return TextureListener.getBlockTexture(prefix+"_life_bomb_top");
    }

    @Override
    public int getTextureForBottom(String prefix) {
        return TextureListener.getBlockTexture("life_bomb_bottom");
    }
}
