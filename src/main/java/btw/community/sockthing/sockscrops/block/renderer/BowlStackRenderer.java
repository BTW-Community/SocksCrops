package btw.community.sockthing.sockscrops.block.renderer;

import btw.community.sockthing.sockscrops.block.models.BowlModel;
import btw.community.sockthing.sockscrops.block.models.CookingPotModel;
import btw.community.sockthing.sockscrops.block.tileentities.BowlStackTileEntity;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import net.minecraft.src.TileEntity;
import net.minecraft.src.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BowlStackRenderer extends TileEntitySpecialRenderer {

    private BowlModel bowlModel = new BowlModel();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        BowlStackTileEntity bowl = (BowlStackTileEntity)tileEntity;
        this.renderBowlAt(bowl, (float) xCoord, (float) yCoord, (float) zCoord, fPartialTickCount);
    }

    private void renderBowlAt(BowlStackTileEntity bowl, float xCoord, float yCoord, float zCoord, float fPartialTickCount) {
        BowlModel bowlModel = this.bowlModel;
        this.bindTextureByName("/scmodtex/bowlStack/bowl.png");

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glTranslatef(xCoord + 0.5F, yCoord, zCoord + 0.5F);

        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        //Center
        int count = bowl.centerPositions[0];
        for (int j = 0; j < count; j++) {
            GL11.glPushMatrix();

            // center stays at origin, only stack upward
            GL11.glTranslatef(0F, j * -3/16F, 0F);

            bowlModel.render(null, 0F, 0F, 0F, 0F, 0F, var10);

            GL11.glPopMatrix();
        }

        //Square
        for (int i = 0; i < 4; i++) {
            count = bowl.squarePositions[i];
            if (count == 0) continue;

            // skip rendering if blocked by a diamond
//            if (i == 0 && (bowl.diamondPositions[0] > 0 || bowl.diamondPositions[1] > 0)) continue; // NW blocked by N/W
//            if (i == 1 && (bowl.diamondPositions[0] > 0 || bowl.diamondPositions[2] > 0)) continue; // NE blocked by N/E
//            if (i == 2 && (bowl.diamondPositions[1] > 0 || bowl.diamondPositions[3] > 0)) continue; // SW blocked by W/S
//            if (i == 3 && (bowl.diamondPositions[2] > 0 || bowl.diamondPositions[3] > 0)) continue; // SE blocked by E/S

            for (int j = 0; j < count; j++) {
                GL11.glPushMatrix();

                if (i == 0) GL11.glTranslatef(-0.25F, 0F, -0.25F); // NW
                if (i == 1) GL11.glTranslatef(+0.25F, 0F, -0.25F); // NE
                if (i == 2) GL11.glTranslatef(-0.25F, 0F, +0.25F); // SW
                if (i == 3) GL11.glTranslatef(+0.25F, 0F, +0.25F); // SE

                GL11.glTranslatef(0F, j * -3/16F, 0F); // stacking
                bowlModel.render(null, 0F,0F,0F, 0F,0F,var10);

                GL11.glPopMatrix();
            }
        }

        //Diamonds
        for (int i = 0; i < 4; i++) {
            count = bowl.diamondPositions[i];
            if (count == 0) continue;

            // skip rendering if blocked by squares
//            if (i == 0 && (bowl.squarePositions[0] > 0 || bowl.squarePositions[1] > 0)) continue; // N blocked by NW/NE
//            if (i == 1 && (bowl.squarePositions[0] > 0 || bowl.squarePositions[2] > 0)) continue; // W blocked by NW/SW
//            if (i == 2 && (bowl.squarePositions[1] > 0 || bowl.squarePositions[3] > 0)) continue; // E blocked by NE/SE
//            if (i == 3 && (bowl.squarePositions[2] > 0 || bowl.squarePositions[3] > 0)) continue; // S blocked by SW/SE

            for (int j = 0; j < count; j++) {
                GL11.glPushMatrix();

                if (i == 0) GL11.glTranslatef(0F, 0F, -0.25F - 1/16F); // N
                if (i == 1) GL11.glTranslatef(-0.25F - 1/16F, 0F, 0F); // W
                if (i == 2) GL11.glTranslatef(+0.25F + 1/16F, 0F, 0F); // E
                if (i == 3) GL11.glTranslatef(0F, 0F, +0.25F + 1/16F); // S

                GL11.glTranslatef(0F, j * -3/16F, 0F); // stacking
                bowlModel.render(null, 0F,0F,0F, 0F,0F,var10);

                GL11.glPopMatrix();
            }
        }

        GL11.glPopMatrix();
    }
}
