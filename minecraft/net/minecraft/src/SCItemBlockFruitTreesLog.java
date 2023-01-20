package net.minecraft.src;

public class SCItemBlockFruitTreesLog extends ItemMultiTextureTile {

	public SCItemBlockFruitTreesLog(int itemID, Block block, String[] names) {
		super(itemID, block, names);
	}
	
    public int GetBlockIDToPlace( int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
    	return getBlockID();
    }
	
    @Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )    
    {
        int iNewBlockID = GetBlockIDToPlace( itemStack.getItemDamage(), iFacing, fClickX, fClickY, fClickZ );

        //SC: ADDED
        if (player.isUsingSpecialKey())
        {
        	int damage = itemStack.getItemDamage();
        	
        	switch (damage) {
			case SCBlockFruitTreeLogStack.APPLE:
				iNewBlockID = SCDefs.logStackApple.blockID;
				break;
			case SCBlockFruitTreeLogStack.CHERRY:
				iNewBlockID = SCDefs.logStackCherry.blockID;
				break;
			case SCBlockFruitTreeLogStack.LEMON:
				iNewBlockID = SCDefs.logStackLemon.blockID;
				break;
			case SCBlockFruitTreeLogStack.OLIVE:
				iNewBlockID = SCDefs.logStackOlive.blockID;
				break;
			}
        }
        //END SC
        
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

}
