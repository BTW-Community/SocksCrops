package net.minecraft.src;

public class SCBlockCake extends FCBlockCake {

	protected SCBlockCake(int iBlockID) {
		super(iBlockID);
	}
	
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack heldStack = player.getHeldItem();
        
        if (heldStack == null)
        {
        	 super.onBlockActivated(world, i, j, k, player, iFacing, fXClick, fYClick, fZClick);
        	 return true;
        }
        else if (heldStack.getItem() instanceof SCItemKnife)
        {
        	cutCake(world, i, j, k, player);
        	world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );
        	
            heldStack.attemptDamageItem(1, world.rand);
            
            int maxDamage = heldStack.getMaxDamage();
            if (heldStack.getItemDamage() >= maxDamage)
            {
            	//break tool
            	heldStack.stackSize = 0;
            	player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );            	            	
            }
        	return true;
        }
        return false;
    }

	private void cutCake(World world, int i, int j, int k, EntityPlayer player) {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

        if ( iEatState >= 6 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );                
        }
        
        if(!world.isRemote)
        {
        	
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, SCDefs.cakeSlice.itemID, 0);
        }
		
	}
	
    public int GetEatState( int meta )
    {
    	return ( meta & 7 );
    }
	
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( int meta)
    {
        int iEatState = GetEatState( meta );
        
        float fWidth = (float)( 1 + iEatState * 2 ) / 16.0F;
        
        return AxisAlignedBB.getAABBPool().getAABB(         
        	fWidth, 0.0F, m_fBorderWidth, 
        	1.0F - m_fBorderWidth, m_fHeight, 1.0F - m_fBorderWidth );
    }
    
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
		renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( iItemDamage ) );

		FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F,-0.5F, iItemDamage);
	}
	
    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float var1 = 0.0625F;
        float var2 = 0.5F;
        this.setBlockBounds(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
    }

}
