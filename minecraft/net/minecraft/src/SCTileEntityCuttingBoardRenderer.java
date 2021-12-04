// FCMOD (client only)

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCTileEntityCuttingBoardRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityCuttingBoard cuttingBoard = (SCTileEntityCuttingBoard)tileEntity;
    	
		RenderStorageStack( cuttingBoard, xCoord, yCoord, zCoord );
    }
    
    private boolean isBlock( int itemID)
    {
    	if ( itemID <= 4096) 
    	{
    		if (itemID >= 0 && itemID <= 255) 
        	{
        		return true;
        	} 
        	else if (itemID >= 1000 && itemID <= 1091) 
        	{
        		return true;
        	}
    	}
    	
    	return false;
	}
    
    private void RenderStorageStack( SCTileEntityCuttingBoard cuttingBoard, double xCoord, double yCoord, double zCoord )
    {
    	ItemStack stack = cuttingBoard.GetStorageStack();
    	
    	if ( stack != null )
    	{

	      EntityItem entity = new EntityItem( cuttingBoard.worldObj, 0.0D, 0.0D, 0.0D, stack );

	      int itemID = entity.getEntityItem().itemID;

	      entity.getEntityItem().stackSize = 1;
	      entity.hoverStart = 0.0F;
	        
	      GL11.glPushMatrix();
	        
	      if (isBlock(itemID)) {
	        	
	      	GL11.glTranslatef( (float)xCoord + 0.5F, (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 8F/16F ); 
	       	GL11.glScalef(1.5F, 1.5F, 1.5F);
	       	//GL11.glRotatef( 0F, 0F, 0.0F, 0.0F);
	        	
	      } else {
	       	GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 8F/16F ); 
	       	GL11.glScalef(1.5F, 1.5F, 1.5F);
	       	GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);
	      }
	               
	    	int meta = cuttingBoard.worldObj.getBlockMetadata( cuttingBoard.xCoord, cuttingBoard.yCoord, cuttingBoard.zCoord );
	    	
	    	
	    	
	    	if (meta == 0) 
	    	{
	    		GL11.glRotatef( 0F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 1) 
	    	{
	    		GL11.glRotatef( -90F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 2) 
	    	{
	    		GL11.glRotatef( 180F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 3) 
	    	{
	    		GL11.glRotatef( 90F, 0.0F, 1.0F, 0.0F);
	    	}

	        
//	        if ( RenderManager.instance.options.fancyGraphics )
//	        {        	
	            // don't rotate items rendered as billboards (fancyGraphics test)

//	        }      	
	      	
	        RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);
	
	        GL11.glPopMatrix();
    	}
    }
}
