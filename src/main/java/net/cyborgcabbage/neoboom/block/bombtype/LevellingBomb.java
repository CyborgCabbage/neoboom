package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.GoldenRayProvider;
import net.cyborgcabbage.neoboom.level.LifeExplosion;
import net.cyborgcabbage.neoboom.level.NeoExplosion;
import net.cyborgcabbage.neoboom.level.UpsideRayProvider;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.cyborgcabbage.neoboom.listeners.TextureListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;

import java.util.Objects;

public class LevellingBomb extends BombType {
    @Override
    public String getName() {
        return "levelling";
    }

    @Override
    public void explode(PrimedBomb entity){
        NeoExplosion explosion = new NeoExplosion(entity.level, null, entity.x, entity.y, entity.z, ((BombPower)BlockBase.BY_ID[entity.blockId]).power);
        explosion.explode(new UpsideRayProvider(),0.1f,true,true,true,true,0.0f,true);

    }

    @Override
    public int getTextureForSide(String prefix) {
        return TextureListener.getBlockTexture(prefix+"_levelling_bomb_side");
    }

    @Override
    public int getTextureForTop(String prefix) {
        if(prefix == "normal") return BlockBase.TNT.texture+1;
        return TextureListener.getBlockTexture(prefix+"_bomb_top");
    }

    @Override
    public int getTextureForBottom(String prefix) {
        return BlockBase.OBSIDIAN.texture;
    }

}
