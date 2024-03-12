package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCTileEntityRenderPan extends TileEntitySpecialRenderer
{
    private SCModelPan pan = new SCModelPan();

    /**
     * Render a skull tile entity.
     */
    public void renderTileEntitySkullAt(SCTileEntityPan par1TileEntitySkull, double par2, double par4, double par6, float par8)
    {
        this.renderPan((float)par2, (float)par4, (float)par6, par1TileEntitySkull.getBlockMetadata(), (float)(par1TileEntitySkull.getSkullRotation() * 360) / 8.0F);
    }
    
    public void renderPan(float x, float y, float z, int meta, float rot)
    {
    	SCModelPan pan = this.pan;

    	this.bindTextureByName("/scmodtex/pan/pan.png");
    	
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
        
        pan.render((Entity)null, 0.0F, 0.0F, 0.0F, rot, 0.0F, var10);

        GL11.glPopMatrix();
		
	}


    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        SCTileEntityPan pan = (SCTileEntityPan)tileEntity;
        this.renderTileEntitySkullAt(pan, xCoord, yCoord, zCoord, fPartialTickCount);
    	
		RenderCookStack( pan, xCoord, yCoord, zCoord, fPartialTickCount );
    }

    private void RenderCookStack( SCTileEntityPan pan, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
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
