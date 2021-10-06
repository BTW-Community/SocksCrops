// FCMOD (client only)

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCTileEntityCuttingBoardRenderer extends TileEntitySpecialRenderer
{
    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityCuttingBoard campfire = (SCTileEntityCuttingBoard)tileEntity;
    	
		RenderCookStack( campfire, xCoord, yCoord, zCoord );
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
    
    private void RenderCookStack( SCTileEntityCuttingBoard cuttingBoard, double xCoord, double yCoord, double zCoord )
    {
    	ItemStack stack = cuttingBoard.GetStorageStack();
    	
    	if ( stack != null )
    	{
	    	int iMetadata = cuttingBoard.worldObj.getBlockMetadata( cuttingBoard.xCoord, cuttingBoard.yCoord, cuttingBoard.zCoord );
	    	//boolean bIAligned = FCBetterThanWolves.fcBlockCampfireUnlit.GetIsIAligned( iMetadata );
	    	
	        EntityItem entity = new EntityItem( cuttingBoard.worldObj, 0.0D, 0.0D, 0.0D, stack );
	        
	        int itemID = entity.getEntityItem().itemID;
	        
	        
	        entity.getEntityItem().stackSize = 1;
	        entity.hoverStart = 0.0F;
	        
	        GL11.glPushMatrix();
	        
	        if (isBlock(itemID)) {
	        	
	        	GL11.glTranslatef( (float)xCoord + 0.5F, (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 8F/18F ); 
	        	GL11.glScalef(1.5F, 1.5F, 1.5F);
	        	GL11.glRotatef( 0F, 0F, 0.0F, 0.0F);
	        	
	        } else {
	        	GL11.glTranslatef( (float)xCoord + 0.5F, (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 3F/18F ); 
	        	GL11.glScalef(1.5F, 1.5F, 1.5F);
	        	GL11.glRotatef( 90F, 1F, 0.0F, 0.0F);
	        }
	               
	        
//	        if ( RenderManager.instance.options.fancyGraphics )
//	        {        	
	            // don't rotate items rendered as billboards (fancyGraphics test)

//	        }
	
	        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
	
	        GL11.glPopMatrix();
    	}
    }
}
