package net.minecraft.src;

public class SCItemBlockMixer extends ItemBlock {

	public SCItemBlockMixer(int itemID) {
		super(itemID);
	}
	
	@Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )    
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
        		
        		//SC ADDED:
        		if (oldBlock.blockID == SCDefs.barrel.blockID && iFacing == 1)
        		{
        			targetPos.j++;
        		}
        		//SC END
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

}
