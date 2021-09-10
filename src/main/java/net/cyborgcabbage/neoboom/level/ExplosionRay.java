package net.cyborgcabbage.neoboom.level;

import net.minecraft.util.maths.Vec3f;

public class ExplosionRay {
    public Vec3f dir;
    public float multiplier;
    public float power;
    public Vec3f pos;
    public float distance;

    public ExplosionRay(Vec3f dir, float multiplier) {
        this.dir = dir;
        this.multiplier = multiplier;
        this.power = 0.0f;
        this.pos = Vec3f.from(0.0,0.0,0.0);
        this.distance = 0.0f;
    }
}
