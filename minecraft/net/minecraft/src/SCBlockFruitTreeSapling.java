package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreeSapling extends FCBlockSaplingLegacy {

	public static final String[] WOOD_TYPES = new String[] {
			"apple", "cherry", "lemon", "olive",
			"apple", "cherry", "lemon", "olive", 
			"apple", "cherry", "lemon", "olive", 
			"appleMature", "cherryMature", "lemonMature", "oliveMature"	};
	
    protected SCBlockFruitTreeSapling(int iBlockID) {
		super(iBlockID);
		setStepSound(soundGrassFootstep);
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("SCBlockFruitSapling");

	}
    
    private float getGrowthChance() {
		return 1/16F;
	}
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
        checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy
        
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
            if ( world.getBlockLightValue( i, j + 1, k ) >= 9 && random.nextFloat() <= getGrowthChance() )
            {
                int iMetadata = world.getBlockMetadata( i, j, k );
                int iGrowthStage = ( iMetadata & (~3) ) >> 2;

                if ( iGrowthStage < 3 )
                {
                	iGrowthStage++;
                	iMetadata = ( iMetadata & 3 ) | ( iGrowthStage << 2 );
                	
                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );
                }
                else
                {
                	if (world.isAirBlock(i, j + 1, k))
                	{
                		growTree( world, i, j, k, random );
                	}
                }
            }
        }
    }
    

	@Override
    public void growTree( World world, int i, int j, int k, Random random )
    {    	
        int iTreeType = world.getBlockMetadata(i, j, k) & 3;
        boolean bSuccess = false;
        
    	int iBlockBelowID = world.getBlockId( i, j - 1, k );
    	
    	if ( iBlockBelowID == FCBetterThanWolves.fcBlockAestheticOpaqueEarth.blockID )
    	{
    		int iBlockBelowMetadata = world.getBlockMetadata( i, j - 1, k );
    		
    		if ( ((FCBlockAestheticOpaqueEarth)FCBetterThanWolves.fcBlockAestheticOpaqueEarth).IsBlightFromMetadata( iBlockBelowMetadata ) )
    		{
    			// FCTODO
    			//bSuccess = GrowBlightTree();
    			
    			//return;
    		}
    	}
    	else if (iBlockBelowID == SCDefs.compostBlock.blockID)
    	{
    		world.setBlock(i, j - 1, k, FCBetterThanWolves.fcBlockDirtLoose.blockID);
    	}
    	
        world.setBlock( i, j, k, 0 );  
        
        world.setBlockAndMetadata(i, j, k, SCDefs.fruitStump.blockID, iTreeType);
        world.setBlockAndMetadata(i, j + 1, k, SCDefs.fruitLeaves.blockID, iTreeType);
    }

    
    //------------- Class Specific Methods ------------//
    
	public int getSaplingGrowthStage( int meta )
	{		
        int iGrowthStage = ( meta & (~3) ) >> 2;
    		
		return iGrowthStage;
	}
    
	//----------- Client Side Functionality -----------//
	
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
    	if (iSide == 0) return false;
    	else return super.shouldSideBeRendered(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
    }
	
	private boolean secondPass = false;
	private Icon[][] sapling = new Icon[4][4]; 
	private Icon[][] leaves = new Icon[4][4]; 
	private Icon[] top = new Icon[4];
	
    public static final String[] saplingTextureNames = new String[] { "SCBlockFruitTreeSapling_apple_", "SCBlockFruitTreeSapling_cherry_", "SCBlockFruitTreeSapling_lemon_", "SCBlockFruitTreeSapling_olive_" };
    public static final String[] leavesTextureNames = new String[] { "SCBlockFruitTreeLeavesSide_apple_", "SCBlockFruitTreeLeavesSide_cherry_", "SCBlockFruitTreeLeavesSide_lemon_", "SCBlockFruitTreeLeavesSide_olive_" };
    public static final String[] topTextureNames = new String[] { "SCBlockFruitTreeLeavesTop_apple", "SCBlockFruitTreeLeavesTop_cherry", "SCBlockFruitTreeLeavesTop_lemon", "SCBlockFruitTreeLeavesTop_olive" };
    
    public void registerIcons(IconRegister register )
    {
    	for ( int iTempSaplingType = 0; iTempSaplingType < 4; iTempSaplingType++ )
    	{
    		for ( int iTempGrowthStage = 0; iTempGrowthStage < 4; iTempGrowthStage++ )
    		{
    			sapling[iTempSaplingType][iTempGrowthStage] = register.registerIcon( saplingTextureNames[iTempSaplingType] + iTempGrowthStage );
    			
    			leaves[iTempSaplingType][iTempGrowthStage] = register.registerIcon( leavesTextureNames[iTempSaplingType] + iTempGrowthStage);    			
    		}
    		
    		top[iTempSaplingType] = register.registerIcon( topTextureNames[iTempSaplingType] );   		
    	}
    }

    
    public Icon getIcon( int iSide, int iMetadata )
    {
        int iSaplingType = iMetadata & 3;
        int iGrowthStage = ( iMetadata & (~3) ) >> 2;
    		
    	if (secondPass)
    	{
    		if (iSide <= 1) return top[iSaplingType];
    		else return leaves[iSaplingType][iGrowthStage];
    	}
    		
        return sapling[iSaplingType][iGrowthStage];
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult)
    {
    	secondPass = true;
    	
    	int iMetadata = renderer.blockAccess.getBlockMetadata(x, y, z);
        int iSaplingType = iMetadata & 3;
        int iGrowthStage = ( iMetadata & (~3) ) >> 2;
    		
    	renderer.setRenderBounds(getBoundsForGrowthStage(iGrowthStage));
    	
    	renderer.renderStandardBlock(this, x, y, z);
    	
    	renderer.setOverrideBlockTexture(sapling[iSaplingType][iGrowthStage]);
    	renderer.setRenderBounds(getStemBoundsForGrowthStage(iGrowthStage));
    	renderer.renderStandardBlock(this, x, y, z);
    	renderer.clearOverrideBlockTexture();
    	
    	
    	secondPass = false;
    }
    
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		int growthStage = ( blockAccess.getBlockMetadata(i, j, k) & (~3) ) >> 2;
		return getBoundsForGrowthStage(growthStage);
	}

	private AxisAlignedBB getBoundsForGrowthStage(int iGrowthStage) {
		if (iGrowthStage == 0)
		{
			return new AxisAlignedBB(
					3/16D, 0, 3/16D,
					13/16D, 10/16D, 13/16D);
		}
		else if (iGrowthStage == 1)
		{
			return new AxisAlignedBB(
					2/16D, 0/16D, 2/16D,
					14/16D, 12/16D, 14/16D);
		}
		else if (iGrowthStage == 2)
		{
			return new AxisAlignedBB(
					1/16D, 0/16D, 1/16D,
					15/16D, 14/16D, 15/16D);
		}
		else
		{
			return new AxisAlignedBB(
					0, 0, 0,
					1, 1, 1);
		}
				

	}
	
	private AxisAlignedBB getStemBoundsForGrowthStage(int iGrowthStage) {
		if (iGrowthStage == 0)
		{
			return new AxisAlignedBB(
					7/16D, 0, 7/16D,
					9/16D, 9/16D, 9/16D);
		}
		else if (iGrowthStage == 1)
		{
			return new AxisAlignedBB(
					7/16D, 0/16D, 7/16D,
					9/16D, 11/16D, 9/16D);
		}
		else if (iGrowthStage == 2)
		{
			return new AxisAlignedBB(
					7/16D, 0/16D, 7/16D,
					9/16D, 13/16D, 9/16D);
		}
		else
		{
			return new AxisAlignedBB(
					7/16D, 0/16D, 7/16D,
					9/16D, 15/16D, 9/16D);
		}
				

	}
}
