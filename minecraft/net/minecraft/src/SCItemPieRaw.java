package net.minecraft.src;

public class SCItemPieRaw extends FCItemFood {
	
	protected int m_iBlockID;
	protected int m_iBlockMetadata = 0;
	
	protected boolean m_bRequireNoEntitiesInTargetBlock = false;
	
	public SCItemPieRaw(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat,
			String sItemName, int iBlockID, int iBlockMetadata) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName);
	
    	m_iBlockID = iBlockID;       
    	m_iBlockMetadata = 0;
    	
    	setAlwaysEdible();
	}
	
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer player, World par3World, int par4,
			int par5, int par6, int par7, float par8, float par9, float par10) {
		
		if (player.isUsingSpecialKey())
		{
			return onItemUse2(par1ItemStack, player, par3World, par4, par5, par6, par7, par8, par9, par10);
		}
		
		else return super.onItemUse(par1ItemStack, player, par3World, par4, par5, par6, par7, par8, par9, par10);
	}
	
	//Copied from FCItemPlaceAsBlock
	public boolean onItemUse2( ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )    
	{
	    int iNewBlockID = GetBlockIDToPlace( itemStack.getItemDamage(), iFacing, fClickX, fClickY, fClickZ );
	    
	    if ( itemStack.stackSize == 0 ||
	    	( player != null && !player.canPlayerEdit( i, j, k, iFacing, itemStack ) ) ||        	
			( j == 255 && Block.blocksList[iNewBlockID].blockMaterial.isSolid() ) )
	    {
	        return false;
	    }
	    
	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	int iOldBlockID = world.getBlockId( i, j, k );
	    Block oldBlock = Block.blocksList[iOldBlockID];

	    if ( oldBlock != null )
	    {
	    	if ( oldBlock.IsGroundCover( ) )
	    	{
	    		iFacing = 1;
	    	}
	    	else if ( !oldBlock.blockMaterial.isReplaceable() )
	    	{
	    		targetPos.AddFacingAsOffset( iFacing );
	    	}
	    }

	    if ( ( !m_bRequireNoEntitiesInTargetBlock || IsTargetFreeOfObstructingEntities( world, targetPos.i, targetPos.j, targetPos.k ) ) && 
	    	world.canPlaceEntityOnSide( iNewBlockID, targetPos.i, targetPos.j, targetPos.k, false, iFacing, player, itemStack ) )
	    {
	        Block newBlock = Block.blocksList[iNewBlockID];
	        
	    	int iNewMetadata = getMetadata( itemStack.getItemDamage() );
	    	
	    	iNewMetadata = newBlock.onBlockPlaced( world, targetPos.i, targetPos.j, targetPos.k, iFacing, fClickX, fClickY, fClickZ, iNewMetadata );

	    	iNewMetadata = newBlock.PreBlockPlacedBy( world, targetPos.i, targetPos.j, targetPos.k, iNewMetadata, player );            
	        
	        if ( world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, 
	    		targetPos.k, iNewBlockID, iNewMetadata ) )
	        {
	            if ( world.getBlockId( targetPos.i, targetPos.j, targetPos.k ) == iNewBlockID )
	            {
	                newBlock.onBlockPlacedBy( world, targetPos.i, targetPos.j, 
	            		targetPos.k, player, itemStack );
	                
	                newBlock.onPostBlockPlaced( world, targetPos.i, targetPos.j, targetPos.k, iNewMetadata );
	                
	                // Panick animals when blocks are placed near them
	                world.NotifyNearbyAnimalsOfPlayerBlockAddOrRemove( player, newBlock, targetPos.i, targetPos.j, targetPos.k );            
	            }
	            
	            PlayPlaceSound( world, targetPos.i, targetPos.j, targetPos.k, newBlock );
	            
	            itemStack.stackSize--;
	        }
	        
	    	return true;    	
	    }
	    
		return false;    	
	}
	
    public int GetBlockIDToPlace( int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
    	return getBlockID();
    }
    
    public int getBlockID()
    {
        return m_iBlockID;
    }
    
    protected boolean IsTargetFreeOfObstructingEntities( World world, int i, int j, int k )
    {
    	AxisAlignedBB blockBounds = AxisAlignedBB.getAABBPool().getAABB(
        	(double)i, (double)j, (double)k, (double)(i + 1), (double)( j + 1 ), (double)(k + 1) );
    	
    
    	
    	return world.checkNoEntityCollision( blockBounds );
    }
    
    protected void PlayPlaceSound( World world, int i, int j, int k, Block block )
    {
    	StepSound stepSound = block.GetStepSound(world, i, j, k);
    	
        world.playSoundEffect( (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, stepSound.getPlaceSound(), 
        	( stepSound.getVolume() + 1F ) / 2F, stepSound.getPitch() * 0.8F );
    }
}
