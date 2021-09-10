package net.cyborgcabbage.neoboom.level;

import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;

public class UpsideRayProvider extends GoldenRayProvider{
    @Override
    public ArrayList<ExplosionRay> getRays(int count) {
        ArrayList<ExplosionRay> arrayList = super.getRays(count);
        arrayList.removeIf(e -> e.dir.y < 0.0);
        return arrayList;
    }
}
