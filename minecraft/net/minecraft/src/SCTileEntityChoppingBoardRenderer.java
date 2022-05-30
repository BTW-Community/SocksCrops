// FCMOD (client only)

package net.minecraft.src;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class SCTileEntityChoppingBoardRenderer extends TileEntitySpecialRenderer
{
	private static ArrayList<Item> cuttingItems = new ArrayList();
	
	public static boolean isCuttingItem(Item item) {
		return cuttingItems.contains(item);
	}
	
	public static void addValidCuttingItem(Item item) {
		cuttingItems.add(item);
	}
	
	static 	{
		addValidCuttingItem(SCDefs.knifeStone);
		addValidCuttingItem(SCDefs.knifeIron);
		addValidCuttingItem(SCDefs.knifeDiamond);
		addValidCuttingItem(Item.axeStone);
		addValidCuttingItem(Item.axeIron);
		addValidCuttingItem(Item.axeDiamond);
	}
	
    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityChoppingBoard cuttingBoard = (SCTileEntityChoppingBoard)tileEntity;
    	
		RenderKnifeStackStack( cuttingBoard, xCoord, yCoord, zCoord );
    }
    
    private boolean isBlock(Item item )
    {
    	if ( item instanceof ItemBlock )
    		return true;
    	
    	else return false;
	}
    
    private void RenderKnifeStackStack( SCTileEntityChoppingBoard cuttingBoard, double xCoord, double yCoord, double zCoord )
    {
    	ItemStack stack = cuttingBoard.getKnifeStack();
    	
    	if ( stack != null )
    	{

	      EntityItem entity = new EntityItem( cuttingBoard.worldObj, 0.0D, 0.0D, 0.0D, stack );

	      Item item = entity.getEntityItem().getItem();
	      int meta = cuttingBoard.worldObj.getBlockMetadata( cuttingBoard.xCoord, cuttingBoard.yCoord, cuttingBoard.zCoord );
	      
	      entity.getEntityItem().stackSize = 1;
	      entity.hoverStart = 0.0F;
	        
	      GL11.glPushMatrix();
	        
	      if ( isBlock(item)) {
	        	
	      	GL11.glTranslatef( (float)xCoord + 0.5F, (float)yCoord + ( 2.75F / 16F ), (float)zCoord + 8F/16F ); 
	       	GL11.glScalef(1.75F, 1.75F, 1.75F);
	       	//GL11.glRotatef( 0F, 0F, 0.0F, 0.0F);
	       	
	    	if (meta == 0) 
	    	{
	    		GL11.glRotatef( 90F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 2) 
	    	{
	    		GL11.glRotatef( 270F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 1) 
	    	{
	    		
	    	}
	    	else if (meta == 3) 
	    	{
	    		GL11.glRotatef( 180F, 0.0F, 1.0F, 0.0F);
	    	}
	        	
	      } else {
	    	  

		    GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 8F/16F ); 
	       	
	       	GL11.glScalef(1.45F, 1.45F, 1.45F);
	       	
//	       	GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);
	       	
	    	
	    	
	    	if (meta == 0) 
	    	{
	    		GL11.glRotatef( 0F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 2) 
	    	{
	    		GL11.glRotatef( 180F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 1) 
	    	{
	    		GL11.glRotatef( 270F, 0.0F, 1.0F, 0.0F);
	    	}
	    	else if (meta == 3) 
	    	{
	    		GL11.glRotatef( 90F, 0.0F, 1.0F, 0.0F);
	    	}
	    	
	    	Item storedItem = cuttingBoard.getKnifeStack().getItem();
	    	
		    if ( isCuttingItem(storedItem))
		    {
		    	GL11.glRotatef( 180F, 1.0F, 0.0F, 0.0F);
		    	
		    	if (storedItem instanceof SCItemKnife)
		    	{
		    		GL11.glTranslatef( 0,  -5F / 16F , 0 ); 
		    	}
		    	else GL11.glTranslatef( 0,  -5.7F / 16F , 0 ); 
		    	
		    }
		    else
		    {
		    	GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);
		    	
		    	float shift = - (14/64F) - 3/512F;
		    	
		    	if (meta == 0) 
		    	{
		    		GL11.glTranslatef( 0, shift , 0); 
		    	}
		    	else if (meta == 2) 
		    	{
		    		GL11.glTranslatef( 0, shift,  0); 
		    	}
		    	else if (meta == 1) 
		    	{
		    		GL11.glTranslatef( 0, shift ,  0); 
		    	}
		    	else if (meta == 3) 
		    	{
		    		GL11.glTranslatef( 0, shift ,  0); 
		    	}
		    }
	    	

	    	

	    	
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