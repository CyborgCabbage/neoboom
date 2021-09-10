package net.cyborgcabbage.neoboom.level;

import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;

public interface RayProvider {
    ArrayList<ExplosionRay> getRays(int count);
}
