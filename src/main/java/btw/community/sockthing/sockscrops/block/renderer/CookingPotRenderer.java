package btw.community.sockthing.sockscrops.block.renderer;

import btw.community.sockthing.sockscrops.block.models.CookingPotModel;
import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class CookingPotRenderer extends TileEntitySpecialRenderer {
    private CookingPotModel pot = new CookingPotModel();

    /**
     * Render a skull tile entity.
     */
    public void renderTileEntitySkullAt(CookingPotTileEntity potTile, double par2, double par4, double par6, float time)
    {
        float var9 = potTile.tickCount;
        this.renderPot((float)par2, (float)par4, (float)par6, potTile.getBlockMetadata(), (float)((potTile.getSkullRotation() * 360) / 8.0F), potTile, var9);
    }

    public void renderPot(float x, float y, float z, int meta, float rot, CookingPotTileEntity potTile, float time)
    {
        CookingPotModel pot = this.pot;

        rot = (float)(potTile.getSkullRotation() * 360) / 8.0F;

        this.bindTextureByName("/scmodtex/cookingPot/cookingPot.png");

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);

        if (meta != 1)
        {
            GL11.glTranslatef(x + 0.5F, y - 0.5F, z + 0.5F);
        }
        else
        {
            GL11.glTranslatef(x + 0.5F, y, z + 0.5F);
        }

        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);

        pot.render((Entity)null, 0.0F, 0.0F, 0.0F, rot, time, var10);

        if (potTile.hasLid())
        {
            pot.renderLid((Entity)null, 0.0F, 0.0F, 0.0F, rot, time, var10);
        }

        if (potTile.getCookType() > 0)
        {
            pot.renderContents((Entity)null, 0.0F, 0.0F, 0.0F, rot, time, var10, potTile.getCookType());
        }
        GL11.glPopMatrix();

    }


    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        CookingPotTileEntity pot = (CookingPotTileEntity)tileEntity;
        this.renderTileEntitySkullAt(pot, xCoord, yCoord, zCoord, fPartialTickCount);

        RenderCookStack( pot, xCoord, yCoord, zCoord, fPartialTickCount );
    }

    private void RenderCookStack( CookingPotTileEntity pan, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        ItemStack cookStack = pan.getCookStack();

        if ( cookStack != null )
        {

            EntityItem entity = new EntityItem( pan.worldObj, 0.0D, 0.0D, 0.0D, cookStack );

            Item item = entity.getEntityItem().getItem();
            int rot = pan.getSkullRotation();

            entity.getEntityItem().stackSize = 1;
            entity.hoverStart = 0.0F;

            GL11.glPushMatrix();

            GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 3F / 16F ), (float)zCoord + 8F/16F );

            GL11.glScalef(0.9F, 0.9F, 0.9F);

            GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);

            GL11.glTranslatef(0 , -4/16F , 2/16F);

            if (pan.getBlockMetadata() == 2)
            {
                GL11.glTranslatef( 0, 0, 8/16F);
            }



            RenderManager.instance.renderEntityWithPosYaw(entity, 0D, 0D, 0D, 0F, 0F);

            GL11.glPopMatrix();
        }
    }
}
