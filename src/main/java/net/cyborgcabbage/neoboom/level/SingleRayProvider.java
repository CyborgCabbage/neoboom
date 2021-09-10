package net.cyborgcabbage.neoboom.level;

import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;

public class SingleRayProvider implements RayProvider{
    public Vec3f dir;
    public SingleRayProvider(double x, double y, double z){
        this.dir = Vec3f.from(x,y,z);
    }

    @Override
    public ArrayList<ExplosionRay> getRays(int count) {
        ArrayList<ExplosionRay> arrayList = new ArrayList<>();
        arrayList.add(new ExplosionRay(this.dir,1.0f));
        return arrayList;
    }
}
