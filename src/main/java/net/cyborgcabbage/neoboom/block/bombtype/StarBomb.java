package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.level.NeoExplosion;
import net.cyborgcabbage.neoboom.level.StarRayProvider;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.cyborgcabbage.neoboom.listeners.TextureListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;

public class StarBomb extends BombType {
    public String getName() {
        return "star";
    }

    @Override
    public void explode(PrimedBomb entity) {
        NeoExplosion explosion = new NeoExplosion(entity.level, null, entity.x, entity.y, entity.z, ((BombPower)BlockBase.BY_ID[entity.blockId]).power);
        explosion.explode(new StarRayProvider(),0.1f,true,true,true,true,0.0f,true, BlockBase.GLOWSTONE.id,0.0f);
    }

    @Override
    public int getTextureForSide(String prefix) {
        return TextureListener.getBlockTexture(prefix+"_star_bomb_side");
    }

    @Override
    public int getTextureForTop(String prefix) {
        if(prefix == "normal") return BlockBase.TNT.texture+1;
        return TextureListener.getBlockTexture(prefix+"_bomb_top");
    }

    @Override
    public int getTextureForBottom(String prefix) {
        if(prefix == "normal") return BlockBase.TNT.texture+2;
        return TextureListener.getBlockTexture(prefix+"_bomb_bottom");
    }
}
