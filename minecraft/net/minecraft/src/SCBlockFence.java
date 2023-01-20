package net.minecraft.src;

import java.util.List;

public class SCBlockFence extends FCBlockFence {
	
	public static final String[] types = new String[] {
			"oak", "spruce", "birch", "jungle",
			"blood", "bamboo", "strippedBamboo"};
	
	protected Material material;
	
	public static final double ropeKnotWidth = ( 2D / 16D ) * 1.25;

	public SCBlockFence(int iBlockID, Material material) {
		super( iBlockID, null , material );
		
        setHardness( 1.5F );
        setResistance( 5F );
        
        SetAxesEffectiveOn();
        
        SetBuoyant();
        
		SetFireProperties( FCEnumFlammability.PLANKS );
		
        setStepSound( soundWoodFootstep );
        
        setUnlocalizedName( "SCBlockFence" );
                
        setCreativeTab( CreativeTabs.tabDecorations );
	}

	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}

	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0) );
		par3List.add(new ItemStack(par1, 1, 1) );
		par3List.add(new ItemStack(par1, 1, 2) );
		par3List.add(new ItemStack(par1, 1, 3) );
		par3List.add(new ItemStack(par1, 1, 4) );
		par3List.add(new ItemStack(par1, 1, 5) );
		par3List.add(new ItemStack(par1, 1, 6) );
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
			
			if ( iStringStackSize > 0 && iTargetFacing > 1 ) // iTargetFacing > 1 disables placing vertical rope between fences
			{
				//int iStakeFacing = GetFacing( world, i, j, k );
				
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
					
//					if ( iDistanceToOtherStake <= 0 )
//					{
//						if ( player.rotationPitch > 0 )
//						{
//					    	iTargetFacing = 0;
//						}
//						else
//						{
//					    	iTargetFacing = 1;
//						}
//						
//						iDistanceToOtherStake = this.CheckForValidConnectingStakeToFacing( world, i, j, k, iTargetFacing, iStringStackSize );
//					}
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
//		SCBlockGrapeLeaves leavesBlock = (SCBlockGrapeLeaves)(SCDefs.grapeLeaves);
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
    	
    	targetPos.AddFacingAsOffset( iFacing );
    	
    	int iTargetBlockID = blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k );
    	
    	boolean isGrapeLeaves = false; //TODO //Block.blocksList[blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k )] instanceof SCBlockGrapeLeaves;
    	boolean isRope = Block.blocksList[blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k )] instanceof SCIRope;
    	
    	if ( isGrapeLeaves || isRope )
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
				Block block = Block.blocksList[world.getBlockId( tempPos.i, tempPos.j, tempPos.k )];
				
				SCBlockFenceRope ropeBlock = (SCBlockFenceRope)(SCDefs.fenceRope);
				SCBlockGrapeLeaves leavesBlock = (SCBlockGrapeLeaves)(SCDefs.redGrapeLeaves);
				
				if ( ropeBlock.GetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing ) || leavesBlock.GetExtendsAlongFacing( world, tempPos.i, tempPos.j, tempPos.k, iTargetFacing ) )
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

	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	//Render
	
	private Icon IconRope;
	private Icon IconRopeTop;

	private Icon[] topIcons = new Icon[types.length];
	private Icon[] sideIcons = new Icon[types.length];
	
	public static final int OAK = 0;
	public static final int SPRUCE = 1;
	public static final int BIRCH = 2;
	public static final int JUNGLE = 3;
	public static final int BLOOD = 4;
	public static final int BAMBOO = 5;
	public static final int STRIPPED_BAMBOO = 6;
	
	private String[] topTextures = {
			"wood", "wood_spruce", "wood_birch", "wood_jungle",
			"fcBlockPlanks_blood", "SCBlockFenceTop_bamboo", "SCBlockFenceTop_strippedBamboo"
	};
	
	private String[] sideTextures = {
			"wood", "wood_spruce", "wood_birch", "wood_jungle",
			"fcBlockPlanks_blood", "SCBlockFenceSide_bamboo", "SCBlockFenceSide_strippedBamboo"
	};
	
	@Override
    public void registerIcons( IconRegister register )
    {
		IconRope = register.registerIcon( "fcBlockRope_side" );
		IconRopeTop = register.registerIcon("fcBlockRope_top" );

		for (int type = 0; type < types.length; type++)
		{
			topIcons[type] = register.registerIcon(topTextures[type]);
			sideIcons[type] = register.registerIcon(sideTextures[type]);
		}
		
		
    }
	
	@Override
	public Icon getIcon( int side, int meta )
	{
		int type = meta;
		
		if (side < 2)
		{
			return topIcons[type];
		}
		else return sideIcons[type];
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
    		
    		renderer.SetUvRotateTop( 3 );
    		renderer.SetUvRotateBottom( 3 );
    		
    	}
    	else if ( iFacing == 2 )
    	{
    	}
    	else if ( iFacing == 3 )
    	{

    	}
    	else if ( iFacing == 4 )
    	{    		

    	}
    	else if (  iFacing == 5 )
    	{

    	}
    	
        renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );
        
        //renderer.renderStandardBlock( this, i, j, k );
        
        // render any attached string
        
        Icon stringTexture = IconRope;
        
        for ( int iStringFacing = 0; iStringFacing < 6; iStringFacing++ )
        {
        	if ( HasConnectedStringToFacing( blockAccess, i, j, k, iStringFacing ) )
        	{
        		renderer.setRenderBounds( GetBoundsFromPoolForStringToFacing( 
        			iStringFacing ) );
        		
        		if ( iStringFacing == 4)
            	{
            		renderer.SetUvRotateTop( 2 );
            		
            		
            	}else if (iStringFacing == 5) 
            	{
            		renderer.SetUvRotateTop( 1 );
            	}		
            	
        		
            		renderer.SetUvRotateEast( 1 ); //North South are swapped somehow
            		renderer.SetUvRotateWest( 1 );
            		renderer.SetUvRotateNorth( 1 );
            		renderer.SetUvRotateSouth( 1 );
            	
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );

    			renderer.ClearUvRotation();
    			
    			renderer.setRenderBounds(GetRopeKnotBounds());
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, stringTexture );
        	}
        	
        }
	}
	
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	
        this.RenderRopeConnections(renderer, i, j, k);
        renderer.clearOverrideBlockTexture();
        renderer.ClearUvRotation();
        
        super.RenderBlock(renderer, i, j, k);
        
    	return true;
    }
    
    @Override
    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
    {
    	renderBlocks.setOverrideBlockTexture(sideIcons[iItemDamage]);
		m_model.RenderAsItemBlock( renderBlocks, this, iItemDamage );
		renderBlocks.clearOverrideBlockTexture();
    }

}
