package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitSeedCrop extends FCBlockCrops {

	public static int apple = 0;
	public static int cherry = 4;
	public static int lemon = 8;
	public static int olive = 12;
	
	protected SCBlockFruitSeedCrop(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFruitTreeSeedling");
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 && !IsFullyGrown( world, i, j, k ) )
	        {
	        	AttemptToGrow( world, i, j, k, rand );
	        }
	        
        }
    }
    
    protected void AttemptToGrow(World world, int x, int y, int z, Random rand) {
    	if (GetWeedsGrowthLevel(world, x, y, z) == 0 && canGrowAtCurrentLightLevel(world, x, y, z)) {
	        Block blockBelow = Block.blocksList[world.getBlockId(x, y - 1, z)];
	        
	        if (blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn(world, x, y - 1, z)) {
	    		float fGrowthChance = GetBaseGrowthChance(world, x, y, z) *
	    			blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
	    		
	            if (rand.nextFloat() <= fGrowthChance) {
	            	IncrementGrowthLevel(world, x, y, z);
	            }
	        }
	    }
    }
	
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.05F;
    }
    
    private int getType(int meta) {
    	if (meta < 4) return 0;
    	else if (meta < 8 ) return 1;
    	else if (meta < 12) return 2;
    	else return 3;
	}
    
    @Override
    public int idPicked( World world, int i, int j, int k )
    {
    	int meta = world.getBlockMetadata(i, j, k);
    	int type = getType(meta);
    	
        switch (type) {
        default:
		case 0:
			return SCDefs.appleSeeds.itemID;
		case 1:
			return SCDefs.cherrySeeds.itemID;
		case 2:
			return SCDefs.lemonSeeds.itemID;
		case 3:
			return SCDefs.oliveSeeds.itemID;
		}
    }
    
	@Override
	protected int GetCropItemID() {
		return SCDefs.fruitTreeSapling.blockID;
	}
	
	@Override
	public int damageDropped(int meta) {
		return getType(meta);
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
    protected int GetGrowthLevel( int iMetadata )
    {
    	return iMetadata & 3;
    }
        
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 3;
    }
    
    protected boolean IsFullyGrown( World world, int i, int j, int k )
    {
    	return IsFullyGrown( world.getBlockMetadata( i, j, k ) );
    }
    
    protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
    	int iMetadata = world.getBlockMetadata( i, j, k );	
    	
    	world.setBlockMetadataWithNotify( i, j, k, iMetadata + 1);
        
        if ( IsFullyGrown( world, i, j, k ) )
        {
        	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
        	
        	if ( blockBelow != null )
        	{
        		blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
        	}
        }
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int side)
    {
    	if (secondPass && side == 0)
    	{
    		return false;
    	}
    	
    	return super.shouldSideBeRendered(blockAccess, iNeighborI, iNeighborJ, iNeighborK, side);    
    }
    
    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
    	int growthStage = GetGrowthLevel(blockAccess.getBlockMetadata(i, j, k));
    	
    	if (growthStage == 0)
    	{
			return new AxisAlignedBB(
					4/16D, 0, 4/16D,
					12/16D, 4/16D, 12/16D);
    	}
    	else if (growthStage == 1)
    	{
			return new AxisAlignedBB(
					5/16D, 0, 5/16D,
					11/16D, 6/16D, 11/16D);
    	}
    	else if (growthStage == 2)
    	{
			return new AxisAlignedBB(
					4/16D, 0, 4/16D,
					12/16D, 8/16D, 12/16D);
    	}
    	else
		{
			return new AxisAlignedBB(
					3/16D, 0, 3/16D,
					13/16D, 10/16D, 13/16D);
		}
    }
    
    boolean secondPass;
    
    private Icon[] appleIcon = new Icon[4];
    private Icon[] cherryIcon = new Icon[4];
    private Icon[] lemonIcon = new Icon[4];
    private Icon[] oliveIcon = new Icon[4];
    
    private Icon appleTop;
    private Icon cherryTop;
    private Icon lemonTop;
    private Icon oliveTop;
    
    private Icon[] appleSide = new Icon[4];
    private Icon[] cherrySide = new Icon[4];
    private Icon[] lemonSide = new Icon[4];
    private Icon[] oliveSide = new Icon[4];
    
    public String[] types = {"apple","cherry","lemon","olive"};
    
    @Override
    public void registerIcons(IconRegister register) {
    	for (int i = 0; i < types.length; i++) {
			appleIcon[i] = register.registerIcon("SCBlockFruitTreeSeedling_" + types[0] + "_" + i);
			cherryIcon[i] = register.registerIcon("SCBlockFruitTreeSeedling_" + types[1] + "_" + i);
			lemonIcon[i] = register.registerIcon("SCBlockFruitTreeSeedling_" + types[2] + "_" + i);
			oliveIcon[i] = register.registerIcon("SCBlockFruitTreeSeedling_" + types[3] + "_" + i);
			
			if (i > 0)
			{
				appleSide[i] = register.registerIcon("SCBlockFruitTreeSeedlingSide_" + types[0] + "_" + i);
				cherrySide[i] = register.registerIcon("SCBlockFruitTreeSeedlingSide_" + types[1] + "_" + i);
				lemonSide[i] = register.registerIcon("SCBlockFruitTreeSeedlingSide_" + types[2] + "_" + i);
				oliveSide[i] = register.registerIcon("SCBlockFruitTreeSeedlingSide_" + types[3] + "_" + i);
			}
		}
    	
        appleTop = register.registerIcon("SCBlockFruitTreeLeavesTop_apple");
        cherryTop = register.registerIcon("SCBlockFruitTreeLeavesTop_cherry");
        lemonTop = register.registerIcon("SCBlockFruitTreeLeavesTop_lemon");
        oliveTop = register.registerIcon("SCBlockFruitTreeLeavesTop_olive");
    }
    
    @Override
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
    	return getIcon(par1iBlockAccess.getBlockMetadata(par2, par3, par4),par5);
    }
    
    @Override
    public Icon getIcon(int meta, int side) {
    	
    	int type = getType(meta);
    	int growthStage = GetGrowthLevel(meta);
    	
    	if (!secondPass)
    	{
    		switch (type) {
			case 0:
				return appleIcon[growthStage];
			case 1:
				return cherryIcon[growthStage];
			case 2:
				return lemonIcon[growthStage];
			case 3:
				return oliveIcon[growthStage];
			}
    	}
    	else
    	{
    		if (side == 1)
        	{
        		switch (type) {
    			case 0:
    				return appleTop;
    			case 1:
    				return cherryTop;
    			case 2:
    				return lemonTop;
    			case 3:
    				return oliveTop;
    			}
        	}
        	else
        	{
        		switch (type) {
    			case 0:
    				return appleSide[growthStage];
    			case 1:
    				return cherrySide[growthStage];
    			case 2:
    				return lemonSide[growthStage];
    			case 3:
    				return oliveSide[growthStage];
    			}
        	}
    	}
    	
    	return blockIcon;
    	
    }
    
    protected void RenderCrops( RenderBlocks renderer, int i, int j, int k )
    {
        Tessellator tessellator = Tessellator.instance;
        
        tessellator.setBrightness( getMixedBrightnessForBlock( renderer.blockAccess, i, j, k ) );
        tessellator.setColorOpaque_F( 1F, 1F, 1F );
        
        double dVerticalOffset = 0D;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId( i, j - 1, k )];
        
        if ( blockBelow != null )
        {
        	dVerticalOffset = blockBelow.GroundCoverRestingOnVisualOffset( 
        		renderer.blockAccess, i, j - 1, k );
        }
        
        SCUtilsRender.renderCrossedSquaresWithVerticalOffset(renderer, this, i, j, k, getIcon(renderer.blockAccess.getBlockMetadata(i, j, k), 0 ), dVerticalOffset);
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	int growthStage = GetGrowthLevel(meta);
    	
    	secondPass = true;
    	
    	if (growthStage > 0)
    	{
        	renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));
        	renderer.renderStandardBlock(this, i, j, k);
    	}

    	secondPass = false;
    }

}
