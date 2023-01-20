package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockGrapeLeaves extends SCBlockFenceRope implements SCIRope {

	private String type;
	private int stemBlock;
	private int vineBlock;
	private int deadLeaves;
	private int grapeBlock;
	
	public SCBlockGrapeLeaves(int iBlockID, int grapeBlock, int stemBlock, int vineBlock, int deadLeaves, String type) {
		super(iBlockID);
		this.type = type;
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
		this.deadLeaves = deadLeaves;
		this.grapeBlock = grapeBlock;
		
		SetBlockMaterial(Material.leaves);

		this.setTickRandomly(true);
		
		InitBlockBounds(0,0,0,1,1,1);
		
		this.setHardness( 0.2F );        
	    this.SetAxesEffectiveOn( true );
        this.setLightOpacity( 1 );
        this.SetFireProperties( FCEnumFlammability.LEAVES );
        this.setStepSound( soundGrassFootstep );
        Block.useNeighborBrightness[blockID] = true;
		
		setUnlocalizedName("SCBlockGrapeLeaves");
	}
	
    public float GetBaseGrowthChance()
    {
    	return 0.75F;
    }
    
    public float getGrapesGrowthChance()
    {
    	return 0.05F;
    }
	
    protected int GetLightLevelForGrowth()
    {
    	return 11;
    }
    
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick,
    		float fYClick, float fZClick) {
		ItemStack equippedItem = player.getCurrentEquippedItem();
		
    	if (equippedItem == null || !(equippedItem.getItem() instanceof FCItemShears))
		{
			return false;
		}
    	else
    	{
    		int meta = world.getBlockMetadata(i, j, k);
    		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.fenceRope.blockID, meta);

    		world.playSoundAtEntity(player, "mob.sheep.shear", 1.0F, 1.0F);
    		
    		equippedItem.damageItem(1, player);
    		return true;
    	}
    	
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
		ValidateState(world, i, j, k);
		
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID )
        {
        	
            if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
            {
	            if ( random.nextFloat() <= GetBaseGrowthChance() )
	            {
	            	grow( world, i, j, k, random );
	            }
            	
	            if ( random.nextFloat() <= getGrapesGrowthChance() )
	            {
	            	int belowID = world.getBlockId(i, j -1, k);
	            	Block blockBelow = Block.blocksList[belowID];
	            	
	            	if (blockBelow == null || world.isAirBlock(i, j - 1, k) || blockBelow.IsReplaceableVegetation(world, i, j - 1, k))
	            	{
	            		growGrapes(world, i, j, k, random);
	            		
	            	}
	            }
            }
        }
    }
    
	public void growGrapes( World world, int i, int j, int k, Random random)
    {	
		world.setBlockAndMetadata(i, j - 1, k, grapeBlock, 0);
		
//		world.setBlockAndMetadata(i, j, k, deadLeaves, world.getBlockMetadata(i, j, k));
    }

    protected void grow(World world, int i, int j, int k, Random random) {
		
    	boolean xAxis = false;
    	boolean yAxis = false;
    	boolean zAxis = false;
    	
    	if (GetExtendsAlongAxis(world, i, j, k, 0)) xAxis = true;
    	if (GetExtendsAlongAxis(world, i, j, k, 1)) yAxis = true;
    	if (GetExtendsAlongAxis(world, i, j, k, 2)) zAxis = true;
    	
    	//System.out.println("Extends Along: " + xAxis + " " + yAxis + " " + zAxis);
    	
    	int axis = random.nextInt(2) + random.nextInt(2);
    	
    	boolean atLeastOneStem = checkForStemAlongAxis(world, i, j, k, getIsStem(world, i, j, k), axis, 0, 1, true);
    	
    	//System.out.println("stem: " + atLeastOneStem);
    	
    	if (atLeastOneStem) growLeavesAlongAxis(world, i, j, k, axis, xAxis, yAxis, zAxis, random);
		
	}

	private void growLeavesAlongAxis(World world, int i, int j, int k, int axis, boolean xAxis, boolean yAxis, boolean zAxis, Random random) {

		if (axis == 0 && !xAxis) return;
		if (axis == 1 && !yAxis) return;
		if (axis == 2 && !zAxis) return;
		
		boolean tooManyLeaves = checkForBlocksAlongAxis(world, i, j, k, getIsLeaves(world, i, j, k), axis, 3, 4, false);

		if (tooManyLeaves) return;

		int dir = random.nextInt(2);
		
//		System.out.println("Axis: " + axis + " Dir: " + dir);
		
		if (axis == 0)
		{
			growVinesXAxis(world, i, j, k, dir);
		}
		else if (axis == 1)
        {
        	growVinesYAxis(world, i, j, k);
        }
		else if (axis == 2)
        {
        	growVinesZAxis(world, i, j, k, dir);
        }
	}

	protected void growVinesYAxis(World world, int i, int j, int k) {
		int ropeID = SCDefs.fenceRope.blockID;
		
    	if (world.getBlockId(i, j + 1, k) == ropeID && GetExtendsAlongAxis(world, i, j + 1, k, 1)) { 
    		
    		int meta = world.getBlockMetadata(i, j + 1, k);
    		this.growVines( world, i, j + 1, k, meta );
    	}
	}
	
	protected void growVinesXAxis( World world, int i, int j, int k, int dir)
	{
		int ropeID = SCDefs.fenceRope.blockID;

		if (dir == 0)
		{
	    	if (world.getBlockId(i - 1, j, k) == ropeID) { 
	    		
	    		int meta = world.getBlockMetadata(i - 1, j, k);
	    		this.growVines( world, i - 1, j, k, meta );
	    	}
		}
		else
		{
	    	if (world.getBlockId(i + 1, j, k) == ropeID) { 
	    		
	    		int meta = world.getBlockMetadata(i + 1, j, k);
	    		this.growVines( world, i +1, j, k, meta );
	    	}
		}
	}
	
	protected void growVinesZAxis( World world, int i, int j, int k, int dir)
	{
		int ropeID = SCDefs.fenceRope.blockID;
		
		if (dir == 0)
		{
	    	if (world.getBlockId(i, j, k - 1) == ropeID) { 
	    		
	    		int meta = world.getBlockMetadata(i, j, k - 1);
	    		this.growVines( world, i, j, k - 1, meta );
	    	}
		}
		else
		{
	    	if (world.getBlockId(i, j, k + 1) == ropeID) { 
	    		
	    		int meta = world.getBlockMetadata(i, j, k + 1);
	    		this.growVines( world, i, j, k + 1, meta );
	    	}
		}
	}
	
	protected void growVines( World world, int i, int j, int k, int iMetadata )
    {	
		world.setBlockAndMetadataWithNotify(i, j, k, vineBlock, iMetadata);
//		System.out.println("vines grow");
    }

	private boolean hasRopeAdjacent(World world, int x, int y, int z, int i, int j, int k, int axis) {
		
		return world.getBlockId(x + i, y + j, z + k) == SCDefs.fenceRope.blockID && GetExtendsAlongAxis(world, x + i, y + j, z + k, axis);
	}

	protected boolean checkForBlocksAlongAxis(World world, int x, int y, int z,
			boolean isLeaves, int axis, int range, int count, boolean stump) {

		
		
        int neighboringLeavesCountdown = count;
        
        if (stump)
        {        	
        	int spread = 4;
        	
        	if (axis == 0)
        	{
                for ( int i = x - spread; i <= x + spread; ++i )
                {
                    for ( int k = z - spread/2; k <= z + spread/2; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsLeaves(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}
        	else if (axis == 1)
        	{
                for ( int i = x - spread/2; i <= x + spread/2; ++i )
                {
                    for ( int k = z - spread/2; k <= z + spread/2; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsLeaves(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}
        	else if (axis == 2)
        	{
                for ( int i = x - spread/2; i <= x + spread/2; ++i )
                {
                    for ( int k = z - spread; k <= z + spread; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsLeaves(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}

            
            return false;
        }
        
        neighboringLeavesCountdown = count;
        
        if (axis == 0)
        {
            for ( int i = x - range; i <= x + range; ++i )
            {
            	isLeaves = getIsLeaves(world, i, y, z);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}            
            }
        }
        else if (axis == 1)
        {
        	neighboringLeavesCountdown = count + 1;
        	
            for ( int j = y - range-1; j <= y + range+1; ++j )
            {
            	isLeaves = getIsLeaves(world, x, j, z);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}
            }

        }
        else if (axis == 2)
        {
            for ( int k = z - range; k <= z + range; ++k )
            {
				isLeaves = getIsLeaves(world, x, y, k);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}
            }
        }
                
        return false;
	}

	
	protected boolean checkForStemAlongAxis(World world, int x, int y, int z,
			boolean isLeaves, int axis, int range, int count, boolean stump) {

		
		
        int neighboringLeavesCountdown = count;
        
        if (stump)
        {        	
        	int spread = 4;
        	
        	if (axis == 0)
        	{
                for ( int i = x - spread; i <= x + spread; ++i )
                {
                    for ( int k = z - spread/2; k <= z + spread/2; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsStem(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}
        	else if (axis == 1)
        	{
                for ( int i = x - spread/2; i <= x + spread/2; ++i )
                {
                    for ( int k = z - spread/2; k <= z + spread/2; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsStem(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}
        	else if (axis == 2)
        	{
                for ( int i = x - spread/2; i <= x + spread/2; ++i )
                {
                    for ( int k = z - spread; k <= z + spread; ++k )
                    {
                        for ( int j = y - spread; j <= y + spread; ++j )
                        {
                        	isLeaves = getIsStem(world, i, j, k);

							if (isLeaves) {
								--neighboringLeavesCountdown;

								if (neighboringLeavesCountdown <= 0) {
									return true;
								}
							}
                        }
                    }           
                }
        	}

            
            return false;
        }
        
        neighboringLeavesCountdown = count;
        
        if (axis == 0)
        {
            for ( int i = x - range; i <= x + range; ++i )
            {
            	isLeaves = getIsStem(world, i, y, z);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}            
            }
        }
        else if (axis == 1)
        {
        	neighboringLeavesCountdown = count + 1;
        	
            for ( int j = y - range-1; j <= y + range+1; ++j )
            {
            	isLeaves = getIsStem(world, x, j, z);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}
            }

        }
        else if (axis == 2)
        {
            for ( int k = z - range; k <= z + range; ++k )
            {
				isLeaves = getIsStem(world, x, y, k);

				if (isLeaves) {
					--neighboringLeavesCountdown;

					if (neighboringLeavesCountdown <= 0) {
						return true;
					}
				}
            }
        }
                
        return false;
	}

	protected boolean getIsLeaves(World world, int x, int y, int z)
	{
		Block leaf = Block.blocksList[world.getBlockId(x, y, z)];
		
		if (leaf == null) return false;
		
		return leaf.blockID == this.blockID || leaf.blockID == vineBlock;
	}
	
	protected boolean getIsStem(World world, int x, int y, int z)
	{
		Block block = Block.blocksList[world.getBlockId(x, y, z)];
		
		if (block == null) return false;
		
		return block.blockID == stemBlock;
	}

	//------------- Class Specific Methods ------------//
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int iNeighborBlockID)
	{

		this.ValidateState(world, i, j, k);
		
		if (GetExtendsAlongAxis(world, i, j, k, 1) && !GetExtendsAlongAxis(world, i, j, k, 0) && !GetExtendsAlongAxis(world, i, j, k, 2))
		{
			if (world.getBlockId(i, j - 1, k) != stemBlock &&
					world.getBlockId(i, j - 1, k) != this.blockID )
			{
				int meta = world.getBlockMetadata(i, j, k);
				
				//convert to rope if the block below isn't a stem
				world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.fenceRope.blockID, meta);
				
				//FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, FCBetterThanWolves.fcItemRope.itemID,0);
			}
		}
	}
    
	@Override
    public void OnDestroyedByFire( World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread )
    {
		super.OnDestroyedByFire( world, i, j, k, iFireAge, bForcedFireSpread );
		
		GenerateAshOnBurn( world, i, j, k );		
    }
	
	protected void GenerateAshOnBurn( World world, int i, int j, int k )
	{
		for ( int iTempJ = j; iTempJ > 0; iTempJ-- )
		{
			if ( FCBlockAshGroundCover.CanAshReplaceBlock( world, i, iTempJ, k ) )
			{
		    	int iBlockBelowID = world.getBlockId( i, iTempJ - 1, k );
		    	Block blockBelow = Block.blocksList[iBlockBelowID];
		    	
		    	if ( blockBelow != null && blockBelow.CanGroundCoverRestOnBlock( world, i, iTempJ - 1, k ) )
		    	{
		    		world.setBlockWithNotify( i, iTempJ, k, FCBetterThanWolves.fcBlockAshGroundCover.blockID );
		    		
		    		break;
		    	}
			}
			else if ( world.getBlockId( i, iTempJ, k ) != Block.fire.blockID )
			{
				break;
			}
		}
	}
	
//	@Override
//	public MovingObjectPosition collisionRayTrace(World world, int i, int j, int k, Vec3 startRay, Vec3 endRay) {
//		return CollisionRayTraceVsBlockBounds( world, i, j, k, startRay, endRay );
//	}
//	
//	public MovingObjectPosition MouseOverRayTrace( World world, int i, int j, int k, 
//			Vec3 startRay, Vec3 endRay )
//	{
//		return collisionRayTrace( world, i, j, k, startRay, endRay );
//	}
//	
//	@Override
//	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
//		
//		return GetFixedBlockBoundsFromPool();
//	}
//	
//	@Override
//	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
//		return GetFixedBlockBoundsFromPool();
//	}
//	
	
    @Override
    public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity )
    {
    	// don't slow down movement in air as it affects the ability of entities to jump up blocks
    	
    	if ( entity.IsAffectedByMovementModifiers() && entity.onGround )
    	{
	        entity.motionX *= 0.01D;
	        entity.motionZ *= 0.01D;
    	}
    }
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(0,0,0,1,1,1);
	}

	@Override
    public boolean isOpaqueCube()
    {
        return !( Block.leaves.graphicsLevel );
    }
    
    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	//----------- Client Side Functionality -----------//
	protected Icon iconLowDetail;
	protected Icon hangingVines;
	protected Icon rope;
	protected Icon leaves;
    
	
	private boolean secondPass;
	
	@Override
    public void registerIcons( IconRegister register )
    {
		leaves = register.registerIcon( "SCBlockGrapeLeaves_" + type );
        iconLowDetail = register.registerIcon( "leaves_opaque" );    
        hangingVines = register.registerIcon("SCBlockGrapeDropLeaves_" + type);
        blockIcon = rope = register.registerIcon("fcBlockRope_side");
    }
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
		
		secondPass = true;
		renderer.setOverrideBlockTexture(leaves);
		renderer.setRenderBounds(0, 0, 0, 1, 1, 1);
		renderer.renderStandardBlock(this, i, j, k);
		renderer.clearOverrideBlockTexture();
		secondPass = false;
		
		//Drop Leaves
		int blockBelow = renderer.blockAccess.getBlockId(i, j - 1, k);
		Block below = Block.blocksList[blockBelow];
		
			
		if (!(below instanceof SCBlockGrapeLeaves) || below instanceof SCBlockGrapeStem)
		{
			SCUtilsRender.renderBlockCropsWithAdjustmentWithTexture(renderer, this, i, j - 1, k, hangingVines, 1/16F);
		}		
	}
	
    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
    	if (!secondPass)
    	{
    		return super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    	}
    	
        int id = par1IBlockAccess.getBlockId(par2, par3, par4);
        Block block = Block.blocksList[id];
        
        return !Block.leaves.graphicsLevel && block instanceof SCBlockGrapeLeaves ? false : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
    }
	
    @Override
    public void randomDisplayTick( World world, int i, int j, int k, Random rand )
    {
        if ( world.IsRainingAtPos( i, j + 1, k ) && 
        	!world.doesBlockHaveSolidTopSurface( i, j - 1, k ) && 
        	rand.nextInt(15) == 1 )
        {
            world.spawnParticle( "dripWater",  
            	i + rand.nextDouble(), j - 0.05D, k + rand.nextDouble(), 
            	0D, 0D, 0D );
        }
    }

}
