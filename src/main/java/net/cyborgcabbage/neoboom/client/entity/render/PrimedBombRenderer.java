package net.cyborgcabbage.neoboom.client.entity.render;

import net.cyborgcabbage.neoboom.NeoBoom;
import net.cyborgcabbage.neoboom.block.BombPower;
import net.cyborgcabbage.neoboom.entity.PrimedBomb;
import net.cyborgcabbage.neoboom.listeners.BlockListener;
import net.minecraft.block.BlockBase;
import net.minecraft.client.render.TileRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.PrimedTnt;
import org.lwjgl.opengl.GL11;

public class PrimedBombRenderer extends EntityRenderer {
    private TileRenderer tileRenderer = new TileRenderer();

    public PrimedBombRenderer() {
        this.field_2678 = 0.5F;
    }

    public void render(EntityBase entity, double d, double d1, double d2, float f, float f1) {
        if (entity instanceof PrimedBomb) {
            PrimedBomb arg = (PrimedBomb)entity;
            GL11.glPushMatrix();
            GL11.glTranslatef((float) d, (float) d1, (float) d2);
            float var10;
            if ((float) arg.fuse - f1 + 1.0F < 10.0F) {
                var10 = 1.0F - ((float) arg.fuse - f1 + 1.0F) / 10.0F;
                if (var10 < 0.0F) {
                    var10 = 0.0F;
                }

                if (var10 > 1.0F) {
                    var10 = 1.0F;
                }

                var10 *= var10;
                var10 *= var10;
                float var11 = 1.0F + var10 * 0.3F;
                GL11.glScalef(var11, var11, var11);
            }

            var10 = (1.0F - ((float) arg.fuse - f1 + 1.0F) / 100.0F) * 0.8F;
            //this.bindTexture(NeoBoom.getTexturePath("block","test_bomb"));
            this.bindTexture("/terrain.png");
            this.tileRenderer.method_48(BlockBase.BY_ID[arg.blockId], arg.tileMeta, arg.getBrightnessAtEyes(f1));
            if (arg.fuse / 5 % 2 == 0) {
                GL11.glDisable(3553);
                GL11.glDisable(2896);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 772);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, var10);
                this.tileRenderer.method_48(BlockBase.BY_ID[arg.blockId], arg.tileMeta, 1.0F);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(3042);
                GL11.glEnable(2896);
                GL11.glEnable(3553);
            }

            GL11.glPopMatrix();
        }
    }
}
