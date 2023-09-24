package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockFruitTreesStumpGrowing extends SCBlockFruitTreesLogBase {

	protected SCBlockFruitTreesStumpGrowing(int iBlockID) {
		super(iBlockID);
		setCreativeTab(null);
		setUnlocalizedName( "SCBlockFruitTreeLogStump" ); 
		setTickRandomly(true);
	}

	public float getGrowthChance() {
		return 1/16F;
	}
	
	@Override
    public boolean GetIsStump( int iMetadata )
    {
    	return true;
    }  
	
	private int getGrowthStage(int meta)
	{
		if (meta < 4) return 0;
		else if (meta >= 4 && meta < 8) return 1;
		else if (meta >= 8 && meta < 12) return 2;
		else return 3;

	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int fruitType = meta & 3;
		
		//Convert stump to non growing if the blocks above are not valid/ get removed
		
		if (world.getBlockId(x, y + 1, z) != SCDefs.fruitLog.blockID || world.getBlockId(x, y + 1, z) != SCDefs.fruitLeaves.blockID)
		{
			world.setBlockAndMetadata(x, y, z, SCDefs.fruitLog.blockID, fruitType + 12);
		}
	}

	private boolean hasValidBlocksAboveInRange(World world, int x, int y, int z, int blockID, int meta, int minY, int maxY )
	{
		int numBlocks = 0;
		
		for (int height = minY; height <= maxY; height++)
		{
			if (world.getBlockId(x,y + height, z) == blockID && (world.getBlockMetadata(x, y + height, z) & 3) == meta)
			{
				numBlocks++;
			}			
		}
		
		return numBlocks > 0;
	}
	
	private void placeBlocksAboveInCube(World world, int x, int y, int z, int blockID, int meta, int minX, int maxX, int minY, int maxY, int minZ, int maxZ)
	{
		placeBlocksAboveInCube(world, x, y, z, blockID, meta, minX, maxX, minY, maxY, minZ, maxZ, false, 0);
	}

	private void placeBlocksAboveInCube(World world, int x, int y, int z, int blockID, int meta, int minX, int maxX, int minY, int maxY, int minZ, int maxZ, boolean randomise, float chance )
	{			
		for (int i = x + minX ; i <= x + maxX; i++)
		{
			for (int j = y + minY ; j <= y + maxY; j++)
			{
				for (int k = z + minZ ; k <= z + maxZ; k++) 
				{
					if (world.isAirBlock(i, j, k))
					{
						if (randomise)
						{
							if (world.rand.nextFloat() <= chance)
							{
								world.setBlockAndMetadata(i, j, k, blockID, meta);
							}
						}
						else
						{
							world.setBlockAndMetadata(i, j, k, blockID, meta);
						}
					}
				}
			}
		}
	}
	
	private static void removeBlocksAboveInCube(World world, int x, int y, int z, int blockID, int meta, int minX, int maxX, int minY, int maxY, int minZ, int maxZ )
	{			
		for (int i = x + minX ; i <= x + maxX; i++)
		{
			for (int j = y + minY ; j <= y + maxY; j++)
			{
				for (int k = z + minZ ; k <= z + maxZ; k++) 
				{
					if (world.getBlockId(i, j, k) == blockID && (world.getBlockMetadata(i, j, k)&3) == meta)
					{
						world.setBlockToAir(i, j, k);
					}
				}
			}
		}
	}
	
	private boolean areBlocksAboveInCubeAir(World world, int x, int y, int z, int blockID, int meta, int minX, int maxX, int minY, int maxY, int minZ, int maxZ )
	{			
		for (int i = x + minX ; i <= x + maxX; i++)
		{
			for (int j = y + minY ; j <= y + maxY; j++)
			{
				for (int k = z + minZ ; k <= z + maxZ; k++) 
				{
					if (!world.isAirBlock(i, j, k))
					{
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		
		int meta = world.getBlockMetadata(x, y, z);
		int growthStage = getGrowthStage(meta);
		int fruitType = meta & 3;

		if (!FCUtilsTrees.CanSaplingGrowOnBlock(world, x, y - 1, z))
		{
			return;
		}
		
		System.out.println("Growthstage: " + growthStage);
		
		if (random.nextFloat() <= getGrowthChance())
		{			
			if (growthStage == 0) 
			{
				//Sapling already places a stump and leaf block above
				
				//validate if block above is leaf block
				if ( hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 1, 1) ) 
				{
					//world.setBlock(x, y + 1, z, 0);
					
					world.setBlockAndMetadata(x, y + 1, z, SCDefs.fruitLog.blockID, fruitType);
					//placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 0, 0, 1, 1, 0, 0);	
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 0, 0, 2, 2, 0, 0);
					
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, -1, 1, 1, 1,  0, 0);
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  0, 0, 1, 1, -1, 1);
					
					incrementGrowth(world, x, y, z, meta);					
				}
			}
			else if (growthStage == 1) 
			{				
				//validate if 1 blocks above are log and 2nd above is leaf
				if ( hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 1, 1) &&
					 hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 2, 2) ) 
				{
					//set leafblock to air
					world.setBlock(x, y + 2, z, 0);
					
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 0, 0, 2, 2, 0, 0);	
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 0, 0, 3, 3, 0, 0);
					
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, -1, 1, 2, 2,  0, 0);
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  0, 0, 2, 2, -1, 1);

					incrementGrowth(world, x, y, z, meta);
				}
			}
			else if (growthStage == 2) 
			{				
				//validate if 2 blocks above is log and 3rd above is leaf
				if ( hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 1, 2) &&
					 hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 3, 3) ) 
				{
					//set leafblock to air
					world.setBlock(x, y + 3, z, 0);
					
					removeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  -1, 1, 1, 1, -1, 1);
					
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 0, 0, 3, 3, 0, 0);	
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 0, 0, 4, 4, 0, 0);
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 0, 0, 5, 5, 0, 0, true, 0.5F);
										
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, -1, 1, 3, 4,  0, 0);
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  0, 0, 3, 4, -1, 1);
					
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  -1, 1, 2, 2, -1, 1, true, 0.5F);
					placeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  -1, 1, 3, 3, -1, 1, true, 0.75F);
					
					incrementGrowth(world, x, y, z, meta);
				}
			}
			else if (growthStage == 3) 
			{				
				//validate if 3 blocks above is log and 4th above is leaf
				if ( hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLog.blockID, fruitType, 1, 3) &&
					 hasValidBlocksAboveInRange(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType, 4, 4) ) 
				{
				
//					removeGrowingTree(world, x, y, z, fruitType);
					
					//growTree
					growTree(world, x, y, z, random, fruitType);
				}
			}
		}		
	}

	public static void removeGrowingTree(World world, int x, int y, int z, int fruitType) {
		//remove this block
		removeBlocksAboveInCube(world, x, y, z, SCDefs.fruitStump.blockID, fruitType,  0, 0, 0, 0, 0, 0);
		
		//remove the logs above
		removeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLog.blockID, fruitType,  0, 0, 1, 3, 0, 0);
		
		//remove all the leaves
		removeBlocksAboveInCube(world, x, y, z, SCDefs.fruitLeaves.blockID, fruitType,  -1, 1, 2, 4, -1, 1);
	}

	protected void incrementGrowth(World world, int x, int y, int z, int meta) {
		world.setBlockMetadata(x, y, z, meta + 4);
	}

	public boolean growTree(World world, int i, int j, int k, Random random, int iTreeType) {
		//int iTreeType = world.getBlockMetadata(i, j, k) & 3;
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
		
		if ( iTreeType == 1 ) // cherry
        {
			bSuccess = SCUtilsTrees.generateFruitTree(world, random, i, j, k,
					SCDefs.fruitLog.blockID, 1, SCDefs.fruitLog.blockID, 13, SCDefs.fruitLeavesCherry.blockID, 0, 4, 0.5F, iTreeType);
        } 
		else if ( iTreeType == 2 ) // lemon
        {
			bSuccess = SCUtilsTrees.generateFruitTree(world, random, i, j, k,
					SCDefs.fruitLog.blockID, 2, SCDefs.fruitLog.blockID, 14, SCDefs.fruitLeavesLemon.blockID, 0, 4, 0.5F, iTreeType);
        } 
		else if (iTreeType == 3) //olive
		{
			bSuccess = SCUtilsTrees.generateFruitTree(world, random, i, j, k,
					SCDefs.fruitLog.blockID, 3, SCDefs.fruitLog.blockID, 15, SCDefs.fruitLeavesOlive.blockID, 0, 4, 0.5F, iTreeType);
		}
		else //apple
		{
			bSuccess = SCUtilsTrees.generateFruitTree(world, random, i, j, k,
					SCDefs.fruitLog.blockID, 0, SCDefs.fruitLog.blockID, 12, SCDefs.fruitLeavesApple.blockID, 0, 4, 0.5F, iTreeType);
		}
        
		return bSuccess;

		
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
		
		minx = 4/16F;
		maxx = 12/16F;
		minz = 4/16F;
		maxz = 12/16F;
		
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
	
	//----------- Client Side Functionality -----------//
    
    public static final String[] stumpTopTextureTypes = new String[] {"SCBlockFruitTreeLogStumpTop_apple", "SCBlockFruitTreeLogStumpTop_cherry", "SCBlockFruitTreeLogStumpTop_lemon", "SCBlockFruitTreeLogStumpTop_olive"};
    public static final String[] stumpSideTextureTypes = new String[] {"SCBlockFruitTreeLogStumpSide_apple", "SCBlockFruitTreeLogStumpSide_cherry", "SCBlockFruitTreeLogStumpSide_lemon", "SCBlockFruitTreeLogStumpSide_olive"};

    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {    	
		if ( iSide > 1 )
		{
			return stumpIconArray[iMetadata & 3];
		}
		else
		{
			return stumpTopArray[iMetadata & 3];
		} 
	}    
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	renderer.setRenderBounds( 4/16D, 0D, 4/16D, 12/16D, 1D, 12/16D );
    	
    	renderer.renderStandardBlock( this, i, j, k );
    
    	return true;
    }
}
