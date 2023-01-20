package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCTileEntityRopeHangingItemsRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityRopeHangingItems rope = (SCTileEntityRopeHangingItems)tileEntity;
    	
    	ItemStack storageStack = rope.getStorageStack();
    	
    	RenderStorageStack( rope, xCoord, yCoord, zCoord, storageStack );
    	
    	
    }

	private void RenderStorageStack(SCTileEntityRopeHangingItems rope, double xCoord, double yCoord, double zCoord,	ItemStack stack) {
	
		if ( stack != null )
    	{

    		EntityItem entity = new EntityItem( rope.worldObj, 0.0D, 0.0D, 0.0D, stack );

    		Item item = entity.getEntityItem().getItem();
    		int meta = rope.worldObj.getBlockMetadata( rope.xCoord, rope.yCoord, rope.zCoord );

    		entity.getEntityItem().stackSize = 1;
    		entity.hoverStart = 0.0F;

    		GL11.glPushMatrix();
    		
    		float rotation = -(float)(rope.getItemRotation()[0] * 22.5F );
    		
    		GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( -1F / 16F ), (float)zCoord + 8F/16F ); 

			GL11.glScalef(1.45F, 1.45F, 1.45F);

			GL11.glRotatef(rotation, 0F, 1F, 0F);

//			GL11.glRotatef( 180F, 1.0F, 0.0F, 0.0F);

//			GL11.glTranslatef( 0,  -5.7F / 16F , 0 ); 

    		RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);

    		GL11.glPopMatrix();
    	}
	}
    

}
