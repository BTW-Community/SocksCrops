package net.minecraft.src;

public class SCBlockGrape extends BlockFlower {

	protected SCBlockGrape(int par1) {
		super(par1);
		this.setUnlocalizedName("SCBlockGrapes");
	}
	
	@Override
	public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
		
		renderer.renderCrossedSquares(this, i, j, k);
    
    	return true;
    }
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack playerEquippedItem = player.getCurrentEquippedItem();
    	
    	if ( playerEquippedItem != null )
    	{
    		return false;
    	}
        if ( !world.isRemote ) // must have empty hand to rc
        {
	        // click sound	        
            world.playAuxSFX( 2001, i, j, k, blockID );
            FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, 
            		new ItemStack( SCDefs.grape, 1, 0 ) );
            world.setBlock(i, j, k, 0);
            
        }
        
        return true;
    }

}
