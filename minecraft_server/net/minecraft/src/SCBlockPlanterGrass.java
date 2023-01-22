package net.minecraft.src;

import java.util.Random;

public class SCBlockPlanterGrass extends SCBlockPlanterBase {

	public static final int SPREAD_LIGHT_LEVEL = 9; // 7 previously, 4 vanilla
	public static final int SURVIVE_LIGHT_LEVEL = 9; // 4 previously

	public static final float GROWTH_CHANCE = 0.8F;
	public static final int SELF_GROWTH_CHANCE = 12;
	
	protected SCBlockPlanterGrass(int iBlockID) {
		super(iBlockID);

		setUnlocalizedName("SCBlockPlanterGrass");
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		//TODO: Add Nutrition Based Multiplier
		
		if ( GetIsFertilizedForPlantGrowth( world, i, j, k ) )
		{
			return 2F;
		}

		return 1F;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (FCBlockGrass.canGrassSpreadFromLocation(world, x, y, z)) {
			if (rand.nextFloat() <= GROWTH_CHANCE) {
				checkForGrassSpreadFromLocation(world, x, y, z);
			}

			if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
				world.setBlockAndMetadata(x, y, z, this.blockID, 0);
			}
		}
		
		//Turn Grass to Dirt
		Block blockAbove = Block.blocksList[world.getBlockId(x, y + 1, z)];

		if (blockAbove != null)
		{
			boolean isFullBlock = blockAbove.IsNormalCube(world, x, y, z);
			
			if (isFullBlock)
			{
//				world.setBlockAndMetadata(x, y, z, SCDefs.planterDirt.blockID, 0); //TODO: uncomment when adding new planters
			}
		}
	}
	
	public static void checkForGrassSpreadFromLocation(World world, int x, int y, int z) {
		if (world.provider.dimensionId != 1 && !FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z)) {
			// check for grass spread

			int i = x + world.rand.nextInt(3) - 1;
			int j = y + world.rand.nextInt(4) - 2;
			int k = z + world.rand.nextInt(3) - 1;

			Block targetBlock = Block.blocksList[world.getBlockId(i, j, k)];

			if (targetBlock != null) {
				attempToSpreadGrassToLocation(world, i, j, k);
			}
		}
	}

	public static boolean attempToSpreadGrassToLocation(World world, int x, int y, int z) {
		int targetBlockID = world.getBlockId(x, y, z);
		Block targetBlock = Block.blocksList[targetBlockID];

		if (targetBlock.GetCanGrassSpreadToBlock(world, x, y, z) &&
				Block.lightOpacity[world.getBlockId(x, y + 1, z)] <= 2 &&
				!FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z))    		
		{
			return targetBlock.SpreadGrassToBlock(world, x, y, z);
		}

		return false;
	}
	
	private boolean isSparse(World world, int x, int y, int z) {
		return isSparse(world.getBlockMetadata(x, y, z));
	}
	
	public void setSparse(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, 7);
	}


	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide)
	{
		world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockPlanterSoil.blockID);		
		return true;
	}
	
	private int getNutritionLevel(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		return getNutritionLevel(meta);
	}
	
    private int getNutritionLevel( int meta)
    {    	
    	if ((meta & 3) == 0 ) return 3;
    	else if ((meta & 3) == 1 ) return 2;
    	else if ((meta & 3) == 2 ) return 1;

    	else return 0;
	}
	
	
	//------------ Client Side Functionality ----------//
    
    private Icon topGrass;
    private Icon topGrassSparse;
	private Icon topGrassSparseDirt;
    
    private Icon planter;

	private boolean grassPass = false;
	private boolean dirtPass = false;
	

	
    private boolean isSparse(int meta) {
		return meta > 7;
	}

}
