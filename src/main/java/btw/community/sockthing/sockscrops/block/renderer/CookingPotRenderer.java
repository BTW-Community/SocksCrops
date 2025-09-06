package btw.community.sockthing.sockscrops.block.renderer;

import btw.community.sockthing.sockscrops.block.models.CookingPotModel;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.List;

public class CookingPotRenderer extends TileEntitySpecialRenderer {
    private CookingPotModel pot = new CookingPotModel();

    /**
     * Render a skull tile entity.
     */
    public void renderTileEntitySkullAt(CookingPotTileEntity potTile, double par2, double par4, double par6, float time)
    {
        float var9 = potTile.tickCount;
        this.renderPot((float)par2, (float)par4, (float)par6, potTile.getBlockMetadata(), (float)((potTile.getSkullRotation() * 360) / 8.0F), potTile, var9);

        renderCookStacks( potTile, (float) par2,(float) par4,(float) par6 );

    }

    public void renderPot(float x, float y, float z, int meta, float rot, CookingPotTileEntity potTile, float time) {
        CookingPotModel pot = this.pot;

        // Calculate rotation from tile entity
        rot = (float) (potTile.getSkullRotation() * 360) / 8.0F;

        if (potTile.isFoodCooked()){
            this.bindTextureByName("/scmodtex/cookingPot/cooking_pot_charred.png");
        }
        else this.bindTextureByName("/scmodtex/cookingPot/cooking_pot.png");

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        // Base translation
        if (potTile.isOnCampfire()) {
            GL11.glTranslatef(x + 0.5F, y - 0.5F, z + 0.5F);
        } else {
            GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
        }

        float scale = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        // Render the main pot
        pot.render(null, 0.0F, 0.0F, 0.0F, rot, time, scale);

        // --- Lid Animation ---
        float progress = potTile.lidProgress; // 0 → 1
        float slide = progress * 0.4F;        // lid sliding distance
        float rotation = progress * 30.0F;    // max 30° rotation

        GL11.glPushMatrix();
        GL11.glTranslatef(slide, 0F, 0F);     // slide lid
        GL11.glRotatef(rotation, 0F, 1F, 0F); // rotate lid

        // Optional wobble
        if (potTile.isFoodCooked() && potTile.lidProgress == 0F && potTile.getFireLevel() > 1) {
            float angle = potTile.tickCount * 0.9F;
            float wobbleAmount = 5F;
            float wobbleX = (float)Math.sin(angle) * wobbleAmount;
            float wobbleY = (float)Math.cos(angle) * wobbleAmount;
            GL11.glRotatef(wobbleX, 1F, 0F, 0F);
            GL11.glRotatef(wobbleY, 0F, 1F, 0F);
        }
        else GL11.glTranslatef(0F, 1/16F, 0F);     // y fix

        pot.renderLid(null, 0.0F, 0.0F, 0.0F, rot, time, scale);
        GL11.glPopMatrix();

        // --- Render liquid contents ---
        if (potTile.getLiquidStack() != null) {
            float liquidHeight = potTile.getLiquidStack().stackSize * 2.0F / 16.0F; // float division
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, -6.0F / 16.0F + liquidHeight, 0F);
            pot.renderContents(null, 0.0F, 0.0F, 0.0F, rot, time, scale, potTile.getCookType());
            GL11.glPopMatrix();
        }

        // --- Render cooked food on top ---
        if (potTile.isFoodCooked()) {
            int count = 0;
            for (int i = 0; i < 6; i++) {
                if (potTile.getCookStack(i) != null) count++;
            }
            float liquidHeight = count * 1.0F / 16.0F; // float division
            GL11.glPushMatrix();
            GL11.glTranslatef(0F, 4.0F / 16.0F - liquidHeight, 0F);
            pot.renderContents(null, 0.0F, 0.0F, 0.0F, rot, time, scale, potTile.getCookType());
            GL11.glPopMatrix();
        }

        GL11.glEnable(GL11.GL_CULL_FACE); // restore state
        GL11.glPopMatrix();
    }


    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        CookingPotTileEntity pot = (CookingPotTileEntity)tileEntity;
        this.renderTileEntitySkullAt(pot, xCoord, yCoord, zCoord, fPartialTickCount);
    }

//    private void renderCookStack(CookingPotTileEntity cookingPot, float xCoord, float yCoord, float zCoord)
//    {
//        ItemStack stack = cookingPot.getCookStack();
//
//        if ( stack != null )
//        {
//
//            EntityItem entity = (EntityItem) EntityList.createEntityOfType(EntityItem.class, cookingPot.worldObj, 0.0D, 0.0D, 0.0D, stack );
//
//            entity.getEntityItem().stackSize = 1;
//            entity.hoverStart = 0.0F;
//
//            GL11.glPushMatrix();
//
//            float campfireAdjustment = 0F;
//
//            if (cookingPot.isOnCampfire()){
//                campfireAdjustment -= 8/16F;
//            }
//            GL11.glTranslatef( xCoord + 0.5F, yCoord + ( 2 / 16F ) + campfireAdjustment, zCoord + 0.5F );
//
//            float height = 2/16F;
//
//            float progress = cookingPot.lidProgress; // 0 → 1
//            float slide = progress * height;    // adjust for how far it slides
//
//            GL11.glTranslatef( 0F, slide, 0F);
//
//            if ( RenderManager.instance.options.fancyGraphics )
//            {
//                // don't rotate items rendered as billboards (fancyGraphics test)
//
//                GL11.glRotatef( 90F, 0.0F, 1.0F, 0.0F);
//                float newScale = 0.75F;
//                GL11.glScalef( newScale, newScale, newScale);
//            }
//
//            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
//
//            GL11.glPopMatrix();
//        }
//    }

    private void renderCookStacks(CookingPotTileEntity cookingPot, float xCoord, float yCoord, float zCoord) {

        if (cookingPot.isFoodCooked()) return;

        // Assuming you add a method getCookStacks() that returns List<ItemStack> or ItemStack[]
        List<ItemStack> stacks = cookingPot.getCookStacks();

        if (stacks == null || stacks.isEmpty()) {
            return;
        }

        GL11.glPushMatrix();

        float campfireAdjustment = 0F;
        if (cookingPot.isOnCampfire()) {
            campfireAdjustment -= 8 / 16F;
        }

        // Base translation for the center of the pot
        GL11.glTranslatef(xCoord + 0.5F, yCoord + (2 / 16F) + campfireAdjustment, zCoord + 0.5F);

        float height = 1 / 16F;
        float progress = cookingPot.lidProgress; // 0 → 1
        float slide = progress * height;
        GL11.glTranslatef(0F, slide, 0F);

        int index = 0;
        for (ItemStack stack : stacks) {
            if (stack == null) continue;

            progress *= index/16F;

            EntityItem entity = new EntityItem(
                    cookingPot.getWorldObj(), 0.0D, 0.0D, 0.0D, stack.copy()
            );
            entity.getEntityItem().stackSize = 1;
            entity.hoverStart = 0.0F;

            GL11.glPushMatrix();

            // Spread items slightly in a circle
            double angle = (2 * Math.PI / stacks.size()) * index;
            float radius = 0.15F; // how far from the center each item goes
            float offsetX = (float) (Math.cos(angle) * radius);
            float offsetZ = (float) (Math.sin(angle) * radius);

            GL11.glTranslatef(offsetX, 0F, offsetZ);

            if (RenderManager.instance.options.fancyGraphics) {
                GL11.glRotatef(90F, 0.0F, 1.0F, 0.0F);
                float newScale = 0.75F;
                GL11.glScalef(newScale, newScale, newScale);
            }

            RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);

            GL11.glPopMatrix();
            index++;
        }

        GL11.glPopMatrix();
    }
}
