package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesBranch extends SCBlockFruitTreesLog {

	protected SCBlockFruitTreesBranch(int iBlockID) {
		super(iBlockID);
		setCreativeTab(null);
		InitBlockBounds(6/16F, 0F, 6/16F, 10/16F, 1F, 10/16F);
		setUnlocalizedName( "SCBlockFruitTreeLogBranch" );   
	}
	
	private float getSaplingDropChance()
	{
		return 1/16F;
	}
	
	@Override
	public int idDropped(int par1, Random rand, int par3)
	{
		if (rand.nextFloat() <= getSaplingDropChance())
		{
			return SCDefs.fruitTreeSapling.blockID;
		}
		else return 0;
	}
	
	//Copied from Block as Branches should just burn up
	public void OnDestroyedByFire( World world, int i, int j, int k, int iFireAge, boolean bForcedFireSpread )
	{
		if ( bForcedFireSpread || ( world.rand.nextInt( iFireAge + 10 ) < 5 && 
				!world.IsRainingAtPos( i, j, k ) ) )
		{
			int iNewFireMetadata = iFireAge + world.rand.nextInt( 5 ) / 4;

			if ( iNewFireMetadata > 15 )
			{
				iNewFireMetadata = 15;
			}

			world.setBlockAndMetadataWithNotify( i, j, k, Block.fire.blockID, iNewFireMetadata );
		}
		else
		{
			world.setBlockWithNotify( i, j, k, 0 );
		}
	} 
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		float minx = 0;
		float miny = 0;
		float minz = 0;
		float maxx = 1;
		float maxy = 1;
		float maxz = 1;
		
		int meta = blockAccess.getBlockMetadata(i, j, k) ;
		
		if (meta <= 3 || meta > 11 )
		{
			minx = 6/16F;
			maxx = 10/16F;
			minz = 6/16F;
			maxz = 10/16F;
		}
		else if (meta > 3 && meta <= 7)
		{
			minx = 0/16F;
			maxx = 16/16F;
			minz = 6/16F;
			maxz = 10/16F;
			miny = 6/16F;
			maxy = 10/16F;
		}
		else if (meta > 7 && meta <= 12)
		{
			minx = 6/16F;
			maxx = 10/16F;
			minz = 0/16F;
			maxz = 16/16F;
			miny = 6/16F;
			maxy = 10/16F;
		}
		
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
	
    public static final String[] branchTopTextureTypes = new String[] {"SCBlockFruitTreeLogBranchTop_apple", "SCBlockFruitTreeLogBranchTop_cherry", "SCBlockFruitTreeLogBranchTop_lemon", "SCBlockFruitTreeLogBranchTop_olive"};
    public static final String[] branchSideTextureTypes = new String[] {"SCBlockFruitTreeLogBranchSide_apple", "SCBlockFruitTreeLogBranchSide_cherry", "SCBlockFruitTreeLogBranchSide_lemon", "SCBlockFruitTreeLogBranchSide_olive"};
    
    protected Icon[] branchIconArray;
    protected Icon[] branchTopArray;
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {    	
    	int var3 = iMetadata & 12;
	 	int var4 = iMetadata & 3;
     	return var3 == 0 && (iSide == 1 || iSide == 0) ? this.branchTopArray[var4] : (var3 == 4 && (iSide == 5 || iSide == 4) ? this.branchTopArray[var4] : (var3 == 8 && (iSide == 2 || iSide == 3) ? this.branchTopArray[var4] : this.branchIconArray[var4]));
    } 
	
    @Override
    public void registerIcons( IconRegister iconRegister )
    {   	
    	branchTopArray = new Icon[branchTopTextureTypes.length];
    	
        for (int iTextureID = 0; iTextureID < branchTopArray.length; iTextureID++ )
        {
        	branchTopArray[iTextureID] = iconRegister.registerIcon(branchTopTextureTypes[iTextureID]);
        }
    	
        branchIconArray = new Icon[branchSideTextureTypes.length];

        for (int iTextureID = 0; iTextureID < branchIconArray.length; iTextureID++ )
        {
        	branchIconArray[iTextureID] = iconRegister.registerIcon(branchSideTextureTypes[iTextureID]);
        }
    }
    
	@Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	if (meta <= 3 || meta > 11)
    	{
    		renderer.setRenderBounds( 6/16D, 0D, 6/16D, 10/16D, 1D, 10/16D );
    	}
    	else if (meta <= 7)
    	{
    		renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
    	}
    	else if (meta <= 11)
    	{
    		renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 16/16D );
    	}
    	
    	renderer.renderBlockLog( this, i, j, k );
    	
    	int blockMinI = renderer.blockAccess.getBlockId(i - 1, j, k);
    	int blockMaxI = renderer.blockAccess.getBlockId(i + 1, j, k);
    	int blockMinJ = renderer.blockAccess.getBlockId(i, j - 1, k);
    	int blockMaxJ = renderer.blockAccess.getBlockId(i, j + 1, k);    	
    	int blockMinK = renderer.blockAccess.getBlockId(i, j, k - 1);
    	int blockMaxK = renderer.blockAccess.getBlockId(i, j, k + 1);
    	int metaMinI = renderer.blockAccess.getBlockMetadata(i - 1, j, k);
    	int metaMaxI = renderer.blockAccess.getBlockMetadata(i + 1, j, k);
    	int metaMinJ = renderer.blockAccess.getBlockMetadata(i, j - 1, k);
    	int metaMaxJ = renderer.blockAccess.getBlockMetadata(i, j + 1, k);    	
    	int metaMinK = renderer.blockAccess.getBlockMetadata(i, j, k - 1);
    	int metaMaxK = renderer.blockAccess.getBlockMetadata(i, j, k + 1);
    	
    	if (!GetIsStump(meta))
    	{
        	
        	renderLeaves(renderer,
        			i, j, k,
        			blockMinI, blockMaxI,
        			blockMinJ, blockMaxJ,
        			blockMinK, blockMaxK,
        			metaMinI, metaMaxI,
        			metaMinJ, metaMaxJ,
        			metaMinK, metaMaxK,
        			meta);
        	
        	renderBranches(renderer, this.blockID,
        			i, j, k,
        			blockMinI, blockMaxI,
        			blockMinJ, blockMaxJ,
        			blockMinK, blockMaxK,
        			metaMinI, metaMaxI,
        			metaMinJ, metaMaxJ,
        			metaMinK, metaMaxK,
        			meta);
    	}

    	return true;
    }
	
}
