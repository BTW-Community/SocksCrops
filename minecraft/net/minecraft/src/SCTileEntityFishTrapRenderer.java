package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCTileEntityFishTrapRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityFishTrap fishTrap = (SCTileEntityFishTrap)tileEntity;
    	
		renderStorageStack( fishTrap, xCoord, yCoord, zCoord );
    }

	private void renderStorageStack(SCTileEntityFishTrap fishTrap, double xCoord, double yCoord, double zCoord)
	{
		if (fishTrap.hasFish() )
		{
			ItemStack fish;
			if (fishTrap.getFishStack() != null)
			{
				fish = fishTrap.getFishStack();
			}
			else
			{
				fish = new ItemStack(Item.fishRaw);
			}
			
		      EntityItem entity = new EntityItem( fishTrap.worldObj, 0.0D, 0.0D, 0.0D, fish );

		      int itemID = entity.getEntityItem().itemID;

		      entity.getEntityItem().stackSize = 1;
		      entity.hoverStart = 0.0F;
		        
		      GL11.glPushMatrix();
		        
		       	GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 2F / 16F ), (float)zCoord + 8F/16F ); 
		       	GL11.glScalef(1.5F, 1.5F, 1.5F);
//		       	GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);

		      RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);
		      
		      GL11.glPopMatrix();
		}
	}
}
