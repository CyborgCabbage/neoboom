package net.cyborgcabbage.neoboom.block.bombtype;

import net.cyborgcabbage.neoboom.entity.PrimedBomb;

public abstract class BombType {
    public abstract String getName();

    public abstract void explode(PrimedBomb entity);

    public abstract int getTextureForSide(String prefix);
    public abstract int getTextureForTop(String prefix);
    public abstract int getTextureForBottom(String prefix);
    /*{
        NeoExplosion explosion = new NeoExplosion(entity.level, null, entity.x, entity.y, entity.z, 4.0f);
        explosion.explode();
    }*/
}
