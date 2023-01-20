package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCTileEntityFishTrapRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float partialTickCount )
    {
    	SCTileEntityFishTrap fishTrap = (SCTileEntityFishTrap)tileEntity;
    	
    	ItemStack fishStack = fishTrap.getFishStack();
    	
    	if(fishStack != null)
    	{
    		if (fishStack.itemID != Item.fishRaw.itemID &&
    				fishStack.itemID != SCDefs.cod.itemID && 
    				fishStack.itemID != SCDefs.salmon.itemID &&
    				fishStack.itemID != SCDefs.tropical.itemID && 
    				fishStack.itemID != SCDefs.puffer.itemID )
    		{
    			renderStorageStack( fishTrap, xCoord, yCoord, zCoord );
    		}
    		else renderAliveFish( fishTrap, xCoord, yCoord, zCoord);    		
    	}    	
    }


	private void renderFish(SCTileEntityFishTrap fishTrap, double xCoord, double yCoord, double zCoord, int fishType) {
		ModelBase fishModel;
		
		float scale = 0.0625F;
		

		if (fishType == 1)
		{
			fishModel = new SCModelEntityCod();
			this.bindTextureByName("/scmodtex/fishtrap/cod.png");
			
		}
		else if (fishType == 2)
		{
			fishModel = new SCModelEntitySalmon();
			this.bindTextureByName("/scmodtex/fishtrap/salmon.png");
		}
		else if (fishType == 3)
		{
			fishModel = new SCModelEntityTropical();
			this.bindTextureByName("/scmodtex/fishtrap/tropical.png");
		}
		else if (fishType == 4)
		{
			fishModel = new SCModelEntityPufferfish();
			this.bindTextureByName("/scmodtex/fishtrap/puffer.png");
		}
		else
		{
			fishModel = new SCModelEntityCod();
			this.bindTextureByName("/scmodtex/fishtrap/fish.png");
		}
		
		scale = scale * 0.6F;
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glTranslatef((float)xCoord + 0.5F,(float)yCoord + 8/16F,(float)zCoord + 0.5F);    	        
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
        fishModel.render((Entity)null, fishTrap.ticks, (float) fishTrap.distanceToPlayer, 0.0F, 0F, 0.0F, scale);
        
        GL11.glPopMatrix();
		
	}
    
    private void renderAliveFish(SCTileEntityFishTrap fishTrap, double xCoord, double yCoord, double zCoord)
    {
    	    	
    	if (fishTrap.hasFish() && fishTrap.getFishStack() != null)
    	{
    		renderFish(fishTrap, xCoord, yCoord, zCoord, getFishType(fishTrap));
    	}
		
	}

	private int getFishType(SCTileEntityFishTrap fishTrap) {
		ItemStack fishStack = fishTrap.getFishStack();
		
		if (fishStack != null)
		{
			boolean fish = fishStack.itemID == Item.fishRaw.itemID;
			boolean cod = fishStack.itemID == SCDefs.cod.itemID;
			boolean salmon = fishStack.itemID == SCDefs.salmon.itemID;
			boolean tropical = fishStack.itemID == SCDefs.tropical.itemID;
			boolean puffer = fishStack.itemID == SCDefs.puffer.itemID;
			
			if (cod) return 1;
			else if (salmon) return 2;
			else if (tropical) return 3;
			else if (puffer) return 4;
			else return 0;
		}
		
		return -1;
	
	}
	

	private void renderStorageStack(SCTileEntityFishTrap fishTrap, double xCoord, double yCoord, double zCoord)
    {
    	if (fishTrap.hasFish() && fishTrap.getFishStack() != null)
    	{
    		ItemStack fishStack = fishTrap.getFishStack();

    		EntityItem entity = new EntityItem( fishTrap.worldObj, 0.0D, 0.0D, 0.0D, fishStack );

    		entity.getEntityItem().stackSize = 1;
    		entity.hoverStart = 0.0F;

    		GL11.glPushMatrix();

    		GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 2F / 16F ), (float)zCoord + 8F/16F ); 
    		GL11.glScalef(1.5F, 1.5F, 1.5F);

    		RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);

    		GL11.glPopMatrix();
    	}
    }
}
