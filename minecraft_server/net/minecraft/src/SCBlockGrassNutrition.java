package net.minecraft.src;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SCBlockGrassNutrition extends FCBlockGrass
{
	public static String[] nutritionLevelNames = new String[] {
			"grass0", "grass0_sparse",
			"grass1", "grass1_sparse",
			"grass2", "grass2_sparse",
			"grass3", "grass3_sparse"
	};
    
	public static final float NUTRITION_GROWTH_CHANCE = 0.4F;
	
    public SCBlockGrassNutrition( int iBlockID )
    {
    	super( iBlockID );
    	
    	setHardness( 0.6F );
    	SetShovelsEffectiveOn();
    	SetHoesEffectiveOn();
    	
    	setStepSound(soundGrassFootstep);
    	
    	setUnlocalizedName("SCBlockGrassNutrition");
    }
    
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (nutritionLevel < 3) //ie not full
		{
			attemptToIncreaseNutrition(world, x, y, z, rand);
		}
		
	}
    
    private void attemptToIncreaseNutrition(World world, int x, int y, int z, Random rand)
    {
        int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
        int meta = world.getBlockMetadata(x, y, z);
		
		if ( rand.nextFloat() <= 0.1F)
		{
	        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 ) // at night
	        {
	        	world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, meta - 2);
	        }
		}
	}
    
	@Override
	public int idDropped(int metadata, Random rand, int fortuneModifier)
	{
		return FCBetterThanWolves.fcBlockDirtLoose.blockID;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int nutritionLevel = getNutritionLevel(meta);
		
		if (nutritionLevel == 3) return 0;
		if (nutritionLevel == 2) return 1;
		if (nutritionLevel == 1) return 2;
		else return 3;
	}
	
	@Override
	public boolean DropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float chanceOfDrop)
	{
		//TODO: change the dropped item. Probably to coarse dirt piles
		DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, chanceOfDrop);

		return true;
	}
	
	@Override
	protected void OnNeighborDirtDugWithImproperTool(World world, int x, int y, int z, int toFacing)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		// only disrupt grass/mycelium when block below is dug out
		if (toFacing == 0)
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
		}    		
	}
    
	@Override
	public void OnGrazed(World world, int x, int y, int z, EntityAnimal animal)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (!animal.GetDisruptsEarthOnGraze())
		{
			if (isSparse(world, x, y, z))
			{
				if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 0);
				if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 1);
				if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 2);
				if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 3);
			}
			else
			{
				setSparse(world, x, y, z);
			}
		}
		else
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
			
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}

	@Override
	public void OnVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (animal.GetDisruptsEarthOnGraze())
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
			
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}
	
	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
		if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
		if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
		if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);

		//only drop hemp seeds at full Nutrition
		if (!world.isRemote && nutritionLevel == 3)
		{
			if (world.rand.nextInt(25) == 0) {
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(FCBetterThanWolves.fcItemHempSeeds), fromSide);
			}
		}

		return true;
	}
	
    /**
     * 3 = 100% Nutrition (meta 0 & 1)
     * 2 = 66% Nutrition (meta 2 & 3)
     * 1 = 33% Nutrition (meta 4 & 5)
     * 0 = 0% Nutrition (meta 6 & 7)
     */
    private int getNutritionLevel( World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	return getNutritionLevel(meta);
	}
    
    private int getNutritionLevel( int meta)
    {
    	int nutritionLevel;    	
    	if (meta == 0 || meta == 1) return nutritionLevel = 3;
    	else if (meta == 2 || meta == 3) return nutritionLevel = 2;
    	else if (meta == 4 || meta == 5) return nutritionLevel = 1;
    	else return nutritionLevel = 0;
	}
    

    @Override
	public boolean isSparse(int meta) {
		return meta == 1 || meta == 3 || meta == 5 || meta == 7;
	}
    
    @Override
	public boolean isSparse(IBlockAccess blockAccess, int x, int y, int z) {
		return isSparse(blockAccess.getBlockMetadata(x, y, z));
	}
	
    @Override
	public void setSparse(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, meta + 1);
	}
    
    @Override
	public void setFullyGrown(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, meta - 1);
	}
    

	public void getSubBlocks(int par1, CreativeTabs tab, List list)
    {
//        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 4));
        list.add(new ItemStack(par1, 1, 6));
        
        //Sparse
//        list.add(new ItemStack(par1, 1, 1));
//        list.add(new ItemStack(par1, 1, 3));
//        list.add(new ItemStack(par1, 1, 5));
//        list.add(new ItemStack(par1, 1, 7));
    }

}