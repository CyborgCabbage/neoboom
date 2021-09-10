package net.cyborgcabbage.neoboom.level;

import net.minecraft.util.maths.Vec3f;

import java.util.ArrayList;

public class GoldenRayProvider implements RayProvider{
    @Override
    public ArrayList<ExplosionRay> getRays(int count) {
        ArrayList<ExplosionRay> arrayList = new ArrayList<>();
        double phi = Math.PI * (3.0 - Math.sqrt(5.0)); // golden angle in radians

        for(int i = 0; i < count; i++) {
            double index = (float)i;
            double y = 1.0 - (index / (float)(count - 1))*2.0;  //y goes from 1 to - 1
            double radius = Math.sqrt(1.0 - y * y); //radius at y

            double theta = phi * index; //golden angle increment

            double x = Math.cos(theta) * radius;
            double z = Math.sin(theta) * radius;

            arrayList.add(new ExplosionRay(Vec3f.from(x, y, z),1.0f));
        }
        return arrayList;
    }
}
