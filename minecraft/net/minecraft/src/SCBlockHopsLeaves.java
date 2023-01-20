package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockHopsLeaves extends FCBlockCrops implements SCIRope {

	protected SCBlockHopsLeaves(int iBlockID) {
		super(iBlockID);
		
        setHardness( 0F );
        SetBuoyant();
		SetFireProperties( FCEnumFlammability.CROPS );
        
        InitBlockBounds( 0.5D - m_dBoundsHalfWidth, 0D, 0.5D - m_dBoundsHalfWidth, 
        	0.5D + m_dBoundsHalfWidth, 1D, 0.5D + m_dBoundsHalfWidth );
        
        setStepSound( soundGrassFootstep );
        
        setTickRandomly( true );
        
        disableStats();
	}
	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
		
		ItemStack stack = player.getCurrentEquippedItem();
		
		if ( stack != null )
    	{
    		Item item = stack.getItem();
    		
    		if ( item instanceof FCItemShears)
			{
    			if (GetGrowthLevel(world, i, j, k) == 7)
    			{
	    			world.setBlockAndMetadata(i, j, k, SCDefs.fenceRope.blockID, 2);
	    			
	    			player.playSound("mob.sheep.shear", 1.0F, 1.0F);
	    			
	    			if (!world.isRemote)
	    			{
	    				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCDefs.hops), iFacing);
	    			}
	    			
	    			
	    			return true;
    			}
    			else {
	    			world.setBlockAndMetadata(i, j, k, SCDefs.fenceRope.blockID, 2);
	    			
	    			player.playSound("mob.sheep.shear", 1.0F, 1.0F);

	    			return true;
    			}

    		}
    	}

		    	
    	return false;
	}
	
	public float GetBaseGrowthChance(World world, int i, int j, int k) {
		return 1F;
	}
	
	public float getBaseFlowerChance(World world, int i, int j, int k) {
		return 0.5F;
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1)
	        {
	        	if (!IsFullyGrown( world, i, j, k ) )
	        	{
	        		AttemptToGrow( world, i, j, k, rand );
	        		
		        	if (GetGrowthLevel(world, i, j, k) >= 4 )
		        	{
		        		int metaAbove = world.getBlockMetadata(i, j + 1, k);
		        		
		        		int IDAbove = world.getBlockId(i, j + 1, k);
		        		Block block = Block.blocksList[IDAbove];
		        		
		        		if (block == SCDefs.fenceRope && !((SCBlockFenceRope)block).GetExtendsAlongAxis(world, i, j + 1, k, 0) && !((SCBlockFenceRope)block).GetExtendsAlongAxis(world, i, j + 1, k, 2))
		        		{
		        			AttemptToGrowAbove( world, i, j, k, rand );	
		        		}
		        		
		        	}
	        	}
	        	
	        	if (GetGrowthLevel(world, i, j, k) == 6)
	        	{
	        		attemptToFlower( world, i, j, k, rand );
	        	}
	        }
        }
    }
    
    private void attemptToFlower(World world, int i, int j, int k, Random rand) {
    	if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
    	    {
    	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
    	        
    	        if ( CanGrowOnBlock(world, i, j-1, k) )
    	        {
    	    		float flowerChance = getBaseFlowerChance( world, i, j, k );
    	    		
    	            if ( rand.nextFloat() <= flowerChance )
    	            {
    	            	int iGrowthLevel = GetGrowthLevel( world, i, j, k ) + 1;
    	            	
    	                SetGrowthLevel( world, i, j, k, iGrowthLevel );
    	            }
    	        }
    	    }
	}

	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) > 6;
    }
    
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 7;
    }
    
    protected void AttemptToGrow( World world, int i, int j, int k, Random rand )
    {
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0)
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( CanGrowOnBlock(world, i, j-1, k) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	            	IncrementGrowthLevel( world, i, j, k );
	            }
	        }
	    }
    }
    
    protected void AttemptToGrowAbove( World world, int i, int j, int k, Random rand )
    {
    	if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
	        
	        if ( blockBelow != null && blockBelow.blockID == this.blockID || blockBelow.blockID == SCDefs.hopsCrop.blockID)
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	    		boolean isRope = blockAbove instanceof SCBlockFenceRope;
	    		boolean isOnlyVerticalRope = ((SCBlockFenceRope)blockAbove).GetExtendsAlongAxis(world, i, j, k, 0) && !((SCBlockFenceRope)blockAbove).GetExtendsAlongAxis(world, i, j, k, 1) && !((SCBlockFenceRope)blockAbove).GetExtendsAlongAxis(world, i, j, k, 2);
	    		
	    		
	            if (isRope && isOnlyVerticalRope && rand.nextFloat() <= fGrowthChance )
	            {
	            	world.setBlockAndMetadataWithNotify(i, j + 1, k, this.blockID, 0);
	            }
	        }
	    }
    }
    
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetFixedBlockBoundsFromPool();
	}
    
	@Override
	protected int GetCropItemID() {
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j - 1, k )];
    	
        return super.canPlaceBlockAt( world, i, j, k ) && blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j - 1, k );
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j - 1, k )];
    	Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
    	
    	if (blockAbove == null)
    	{
    		return false;
    	}
    	
        return CanGrowOnBlock( world, i, j - 1, k ) || (blockOn instanceof SCBlockFenceRope || blockAbove instanceof SCBlockFenceRope || blockOn instanceof SCBlockHopsLeaves || blockAbove instanceof SCBlockHopsLeaves); 
    }
    
    @Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && (blockOn.blockID == SCDefs.hopsCrop.blockID || blockOn.blockID == this.blockID);
    }
    
	
	@Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested( world, i, j, k, FCBetterThanWolves.fcItemRope.itemID, 0 );
        }
    }
    
    private Icon[] leaves = new Icon[8];
    private Icon flower;
    private Icon rope;
	private boolean secondPass;
    
    @Override
    public void registerIcons(IconRegister register) {
    	for (int i = 0; i < 7; i++) {
    		leaves[i] = register.registerIcon("SCBlockHopsLeaves_" + i);
		}
    	leaves[7] = leaves[6];
    	flower = register.registerIcon("SCBlockHopsLeaves_flower");
		
		blockIcon = leaves[6];
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
    	
    	if (secondPass)
    	{
    		return flower;
    	}
    	
    	return leaves[meta];
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	RenderCrops( renderer, i, j, k );
    	
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
    	int blockBelow = renderer.blockAccess.getBlockId(i, j - 1, k);
    	
    	if ( !(Block.blocksList[blockBelow] instanceof FCBlockFarmlandBase) )
		{
			SCBlockFenceRope.SetRenderBoundsForAxis( renderer, 1 );
			
			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
		}
    	else if ( Block.blocksList[blockBelow] instanceof FCBlockFarmlandBase )
    	{
    		renderer.renderCrossedSquares(deadBush, i, j, k);
    	}
    	
    	return true;
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	secondPass = true;
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	if (IsFullyGrown(meta))
    	{
        	RenderCrops( renderer, i, j, k );
    	}
    	secondPass = false;
    }
    
    protected void RenderCrops( RenderBlocks renderer, int i, int j, int k )
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
//        if ( blockBelow != null )
//        {
//        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
//        		renderer.blockAccess, i, j - 1, k );
//        }
        
        RenderCrossHatch( renderer, i, j, k, getBlockTexture( renderer.blockAccess, i, j, k, 0 ), 
        	4D / 16D, dVerticalOffset );
        
        
    }

	private AxisAlignedBB getBounds(int meta) {
		int width = 4;

		
		AxisAlignedBB box = new AxisAlignedBB(
				0.5D - width/16D ,0D, 0.5D - width/16D,
				0.5D + width/16D ,0.5D, 0.5D + width/16D);
		return box;
	}

	@Override
	public boolean HasValidAttachmentPointToFacing(World world, int i, int j, int k, int iFacing) {
		if (iFacing == 0 || iFacing == 1) return true;
		else return false;
	}

}
