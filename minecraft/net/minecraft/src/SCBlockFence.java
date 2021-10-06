package net.minecraft.src;

public class SCBlockFence extends FCBlockFenceWood {
	
	Icon IconRope;
	
	public static final double ropeKnotWidth = ( 2D / 16D ) * 1.25;
	
	public SCBlockFence(int iBlockID) {
		super(iBlockID);
	}
	
	@Override
	public int GetFacing( int iMetadata )
	{
		// stake facing is opposite attachment point
		
    	return ( iMetadata & 7 );
	}
	
	@Override
	public int SetFacing( int iMetadata, int iFacing )
	{
    	iMetadata &= ~7; // filter out old facing
    	
    	iMetadata |= iFacing;
    	
        return iMetadata;
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack equippedItem = player.getCurrentEquippedItem();
    	
		int iTargetFacing = Block.GetOppositeFacing( FCUtilsMisc.ConvertPlacingEntityOrientationToBlockFacingReversed( player ) );
    	
		if ( HasConnectedStringToFacing( world, i, j, k, iTargetFacing ) )
		{
			// remove the connected string and drop it at the player's feet

			if ( !world.isRemote )
			{
				int iStringCount = ClearStringToFacingNoDrop( world, i, j, k, iTargetFacing );
				
				if ( iStringCount > 0 )
				{
					FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, new ItemStack( FCBetterThanWolves.fcItemRope.itemID, iStringCount, 0 ) );
				}
				
	            world.playSoundAtEntity( player, "random.bow", 0.25F, world.rand.nextFloat() * 0.4F + 1.2F );
			}
			
			return true;
		}
		else if ( equippedItem != null && equippedItem.getItem().itemID == FCBetterThanWolves.fcItemRope.itemID )
    	{
			int iStringStackSize = equippedItem.stackSize;
			
			if ( iStringStackSize > 0 )
			{
				int iStakeFacing = GetFacing( world, i, j, k );
				
				// scan in the chosen direction for another stake

				int iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
				
				if ( iDistanceToOtherStake <= 0 )
				{
					// check alternate facings as we didn't find anything in the primary
					
				    int iYawOctant = MathHelper.floor_double( (double)( ( player.rotationYaw * 8F ) / 360F ) ) & 7;
				    
				    if ( iYawOctant >= 0 && iYawOctant <= 3 )
				    {
				    	iTargetFacing = 4;
				    }
				    else
				    {
						iTargetFacing = 5;
				    }
				    
					iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
					
					if ( iDistanceToOtherStake <= 0 )
					{
					    if ( iYawOctant >= 2 && iYawOctant <= 5 )
					    {
					    	iTargetFacing = 2;
					    }
					    else
					    {
					    	iTargetFacing = 3;
					    }
					}
					
					iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
					
					// scan alternate facings
					
					if ( iDistanceToOtherStake <= 0 )
					{
						if ( player.rotationPitch > 0 )
						{
					    	iTargetFacing = 0;
						}
						else
						{
					    	iTargetFacing = 1;
						}
						
						iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
					}
				}
				
				if ( iDistanceToOtherStake > 0 )
				{
					if ( !world.isRemote )
					{
						// place the string blocks
						
						SCBlockFenceRope ropeBlock = (SCBlockFenceRope)(SCDefs.fenceRope);
						
						FCUtilsBlockPos tempPos = new FCUtilsBlockPos( i, j, k );
						
						for ( int iTempDistance = 0; iTempDistance < iDistanceToOtherStake; iTempDistance++ )
						{
							tempPos.AddFacingAsOffset( iTargetFacing );
							
							int iTargetBlockID = world.getBlockId( tempPos.i, tempPos.j, tempPos.k );
	
							if ( iTargetBlockID != ropeBlock.blockID )
							{
								// no notify here as it will disrupt the strings still being placed
								
								world.setBlock( tempPos.i, tempPos.j, tempPos.k, ropeBlock.blockID, 0, 2 );
							}
							
							ropeBlock.SetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing, true, false );
							
			                if ( !player.capabilities.isCreativeMode )
			                {
			                	equippedItem.stackSize--;
			                }
						}
						
						// cycle back through and give block change notifications
						
						tempPos = new FCUtilsBlockPos( i, j, k );
						
						for ( int iTempDistance = 0; iTempDistance < iDistanceToOtherStake; iTempDistance++ )
						{
							tempPos.AddFacingAsOffset( iTargetFacing );
							
							world.notifyBlockChange( tempPos.i, tempPos.j, tempPos.k, SCDefs.fenceRope.blockID );
						}
						
			            world.playSoundAtEntity( player, "random.bow", 0.25F, world.rand.nextFloat() * 0.2F + 0.8F );
					}
					else
					{
		                if ( !player.capabilities.isCreativeMode )
		                {
		                	equippedItem.stackSize -= iDistanceToOtherStake;
		                }
					}
				}
				
				return true;
			}
    	}
    	
    	return false;
    }
	
	public boolean HasConnectedStringToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{		
		SCBlockFenceRope ropeBlock = (SCBlockFenceRope)(SCDefs.fenceRope);
		SCBlockGrapeLeaves leavesBlock = (SCBlockGrapeLeaves)(SCDefs.grapeLeaves);
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
    	
    	targetPos.AddFacingAsOffset( iFacing );
    	
    	int iTargetBlockID = blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k );
    	
    	Boolean isRopeInstance = Block.blocksList[blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k )] instanceof SCIRope;
    	
    	if ( iTargetBlockID == ropeBlock.blockID || iTargetBlockID == leavesBlock.blockID)
    	{
    		return ropeBlock.GetExtendsAlongFacing( blockAccess, targetPos.i, targetPos.j, targetPos.k, iFacing );
    	}
    	
		return false;
	}
	
	private int ClearStringToFacingNoDrop( World world, int i, int j, int k, int iTargetFacing )
	{
		int iStringCount = 0;
		
		FCUtilsBlockPos tempPos = new FCUtilsBlockPos( i, j, k ); 
		
		do
		{
			tempPos.AddFacingAsOffset( iTargetFacing );
			
			if ( world.getBlockId( tempPos.i, tempPos.j, tempPos.k ) == SCDefs.fenceRope.blockID )
			{
				SCBlockFenceRope ropeBlock = (SCBlockFenceRope)(SCDefs.fenceRope);
				
				if ( ropeBlock.GetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing ) )
				{
					if ( ropeBlock.GetExtendsAlongOtherFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing ) )
					{
						ropeBlock.SetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing, false, false );
					}
					else
					{
						world.setBlock( tempPos.i, tempPos.j, tempPos.k, 0, 0, 2 );
					}
				}
				else
				{					
					break;
				}
			}
			else
			{
				break;
			}
			
			iStringCount++;
		}
		while ( iStringCount < 64 );
		
		if ( iStringCount > 0 )
		{
			// cycle back through and provide notifications to surrounding blocks
			
			tempPos = new FCUtilsBlockPos( i, j, k );
			
			for ( int iTempCount = 0; iTempCount < iStringCount; iTempCount++ )
			{
				tempPos.AddFacingAsOffset( iTargetFacing );
				
				world.notifyBlockChange( tempPos.i, tempPos.j, tempPos.k, SCDefs.fenceRope.blockID );				
			}
		}
		
		return iStringCount;
	}
	
	/*
	 * returns the distance to the valid stake in the direction, 0 otherwise
	 */
	private int CheckForValidConnectingStakeToFacing( World world, int i, int j, int k, int iFacing, int iMaxDistance )
	{
		SCBlockFenceRope ropeBlock = (SCBlockFenceRope)(SCDefs.fenceRope);	
		FCUtilsBlockPos tempPos = new FCUtilsBlockPos( i, j, k );
		boolean bFoundOtherStake = false;
		
		for ( int iDistanceToOtherStake = 0; iDistanceToOtherStake <= iMaxDistance; iDistanceToOtherStake++ )
		{
			tempPos.AddFacingAsOffset( iFacing );
			
			if ( !world.isAirBlock( tempPos.i, tempPos.j, tempPos.k ) )
			{
				int iTargetBlockID = world.getBlockId( tempPos.i, tempPos.j, tempPos.k );
				
				if ( iTargetBlockID == blockID )
				{
					return iDistanceToOtherStake;
				}
				else if ( iTargetBlockID == ropeBlock.blockID )
				{
					if ( ropeBlock.GetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iFacing ) )
					{
						return 0;
					}    									
				}
				else
				{
					Block tempBlock = Block.blocksList[iTargetBlockID];
					
					if ( !tempBlock.blockMaterial.isReplaceable() || tempBlock.blockMaterial.isLiquid() )
					{
						return 0;
					}
				}
			}    						
		}
		
		return 0;
	}
	
	//Render
	
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons(register);
		
		IconRope = register.registerIcon( "fcBlockRope_side" );
    }
	
	private AxisAlignedBB GetBoundsFromPoolForStringToFacing( int iFacing )
	{
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - SCBlockFenceRope.halfHeight, 0.5D, 0.5D - SCBlockFenceRope.halfHeight, 
    		0.5D + SCBlockFenceRope.halfHeight, 1D, 0.5F + SCBlockFenceRope.halfHeight );
		
		box.TiltToFacingAlongJ( iFacing );
		
		return box;
	}
	
	static AxisAlignedBB GetRopeKnotBounds()
	{
		AxisAlignedBB knotBox = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - ropeKnotWidth, 0.25D + 0.125D, 0.5D - ropeKnotWidth, 
    		0.5D + ropeKnotWidth, 0.75D - 0.125D, 0.5F + ropeKnotWidth );
		
		return knotBox;
	}
	
	private void RenderRopeConnections( RenderBlocks renderer, int i, int j, int k )
	{		
    	IBlockAccess blockAccess = renderer.blockAccess;
    	
    	int iFacing = GetFacing( blockAccess, i, j, k );
    	
    	if ( iFacing == 0 )
    	{
    		renderer.SetUvRotateSouth( 3 );
    		renderer.SetUvRotateNorth( 3 );
    		renderer.SetUvRotateEast( 3 );
    		renderer.SetUvRotateWest( 3 );
    	}
    	else if ( iFacing == 2 )
    	{
    		renderer.SetUvRotateSouth( 1 );
    		renderer.SetUvRotateNorth( 2 );
    	}
    	else if ( iFacing == 3 )
    	{
    		renderer.SetUvRotateSouth( 2 );
    		renderer.SetUvRotateNorth( 1 );
    		renderer.SetUvRotateTop( 3 );
    		renderer.SetUvRotateBottom( 3 );
    	}
    	else if ( iFacing == 4 )
    	{    		
    		renderer.SetUvRotateEast( 1 );
    		renderer.SetUvRotateWest( 2 );
    		renderer.SetUvRotateTop( 2 );
    		renderer.SetUvRotateBottom( 1 );
    	}
    	else if (  iFacing == 5 )
    	{
    		renderer.SetUvRotateEast( 2 );
    		renderer.SetUvRotateWest( 1 );
    		renderer.SetUvRotateTop( 1 );
    		renderer.SetUvRotateBottom( 2 );
    	}
    	
        renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );
        
        //renderer.renderStandardBlock( this, i, j, k );

        renderer.ClearUvRotation();
        
        // render any attached string
        
        Icon stringTexture = IconRope;
        
        for ( int iStringFacing = 0; iStringFacing < 6; iStringFacing++ )
        {
        	if ( HasConnectedStringToFacing( blockAccess, i, j, k, iStringFacing ) )
        	{
        		renderer.setRenderBounds( GetBoundsFromPoolForStringToFacing( 
        			iStringFacing ) );
    			
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );

    			renderer.setRenderBounds(GetRopeKnotBounds());
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );
        	}
        	
        }
	}
	
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	super.RenderBlock(renderer, i, j, k);
        this.RenderRopeConnections(renderer, i, j, k);
    	return true;
    }
}
