// FCMOD

package net.minecraft.src;

import java.util.Random;

public class FCBlockGrass extends BlockGrass {
	public static final int SPREAD_LIGHT_LEVEL = 9; // 7 previously, 4 vanilla
	public static final int SURVIVE_LIGHT_LEVEL = 9; // 4 previously

	public static final float GROWTH_CHANCE = 0.8F;
	public static final int SELF_GROWTH_CHANCE = 12;

	protected FCBlockGrass(int blockID) {
		super(blockID);

		setHardness(0.6F);
		SetShovelsEffectiveOn();
		SetHoesEffectiveOn();

		setStepSound(soundGrassFootstep);

		setUnlocalizedName("grass");    	
	}
	
	//SCADDON: Added
	@Override
	public void NotifyOfFullStagePlantGrowthOn(World world, int i, int j, int k, Block plantBlock) {

		world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.grassNutrition.blockID, 2);

	}

	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		return 1F;
	}
	//END SCADDON
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		if (!canGrassSurviveAtLocation(world, x, y, z)) {
			// convert back to dirt in low light
			world.setBlockWithNotify(x, y, z, Block.dirt.blockID);
		}
		else if (canGrassSpreadFromLocation(world, x, y, z)) {
			if (rand.nextFloat() <= GROWTH_CHANCE) {
				checkForGrassSpreadFromLocation(world, x, y, z);
			}

			if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
				this.setFullyGrown(world, x, y, z);
			}
		}
	}

	@Override
	public int idDropped(int metadata, Random rand, int fortuneModifier) {
		return FCBetterThanWolves.fcBlockDirtLoose.blockID;
	}

	@Override
	public boolean DropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float chanceOfDrop) {
		DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, chanceOfDrop);

		return true;
	}

	@Override
	public void OnBlockDestroyedWithImproperTool(World world, EntityPlayer player, int x, int y, int z, int metadata) {
		super.OnBlockDestroyedWithImproperTool(world, player, x, y, z, metadata);

		OnDirtDugWithImproperTool(world, x, y, z);    	
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion) {
		super.onBlockDestroyedByExplosion(world, x, y, z, explosion);

		OnDirtDugWithImproperTool(world, x, y, z);
	}

	@Override
	protected void OnNeighborDirtDugWithImproperTool(World world, int x, int y, int z, int toFacing) {
		// only disrupt grass/mycelium when block below is dug out

		if (toFacing == 0) {
			world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID);
		}    		
	}

	@Override
	public boolean CanBePistonShoveled(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean CanBeGrazedOn(IBlockAccess blockAccess, int x, int y, int z, EntityAnimal animal) {
		return !isSparse(blockAccess, x, y, z) || animal.IsStarving() || animal.GetDisruptsEarthOnGraze();
	}

	@Override
	public void OnGrazed(World world, int x, int y, int z, EntityAnimal animal) {
		if (!animal.GetDisruptsEarthOnGraze()) {
			if (isSparse(world, x, y, z)) {
				world.setBlockWithNotify(x, y, z, Block.dirt.blockID);
			}
			else {
				setSparse(world, x, y, z);
			}
		}
		else {
			world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID);
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}

	@Override
	public void OnVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal) {
		if (animal.GetDisruptsEarthOnGraze()) {
			world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID);
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}

	@Override
	public boolean CanReedsGrowOnBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean CanSaplingsGrowOnBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean CanWildVegetationGrowOnBlock(World world, int x, int y, int z) {
		return true;
	}

	@Override
	public boolean GetCanBlightSpreadToBlock(World world, int x, int y, int z, int blightLevel) {
		return true;
	}

	@Override
	public boolean CanConvertBlock(ItemStack stack, World world, int x, int y, int z) {
		return stack != null && stack.getItem() instanceof FCItemHoe;
	}

	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide) {
		world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID);

		if (!world.isRemote) {
			if (world.rand.nextInt(25) == 0) {
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(FCBetterThanWolves.fcItemHempSeeds), fromSide);
			}
		}

		return true;
	}

	@Override
	public boolean GetCanGrassSpreadToBlock(World world, int x, int y, int z) {
		return isSparse(world, x, y, z);
	}

	@Override
	public boolean SpreadGrassToBlock(World world, int x, int y, int z) {
		if (isSparse(world, x, y, z)) {
			setFullyGrown(world, x, y, z);
			return true;
		}
		else {
			return false;
		}
	}

	//------------- Class Specific Methods ------------//

	public static boolean canGrassSurviveAtLocation(World world, int x, int y, int z) {
		int blockAboveID = world.getBlockId(x, y + 1, z);
		Block blockAbove = Block.blocksList[blockAboveID];

		int blockAboveMaxNaturalLight = world.GetBlockNaturalLightValueMaximum(x, y + 1, z);

		int blockAboveCurrentNaturalLight = blockAboveMaxNaturalLight - world.skylightSubtracted;

		if (blockAboveMaxNaturalLight < SURVIVE_LIGHT_LEVEL || Block.lightOpacity[blockAboveID] > 2 ||
				(blockAbove != null && !blockAbove.GetCanGrassGrowUnderBlock(world, x, y + 1, z, false)))
		{
			return false;
		}

		return true;
	}

	public static boolean canGrassSpreadFromLocation(World world, int x, int y, int z) {
		int blockAboveID = world.getBlockId(x, y + 1, z);
		Block blockAbove = Block.blocksList[blockAboveID];

		int blockAboveMaxNaturalLight = world.GetBlockNaturalLightValueMaximum(x, y + 1, z);

		int blockAboveCurrentNaturalLight = blockAboveMaxNaturalLight - world.skylightSubtracted;
		int blockAboveCurrentBlockLight = world.getBlockLightValue(x, y + 1, z);

		int currentLight = Math.max(blockAboveCurrentNaturalLight, blockAboveCurrentBlockLight);

		return currentLight >= SPREAD_LIGHT_LEVEL;
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

		if (canGrassSurviveAtLocation(world, x, y, z)) {
			if (targetBlock.GetCanGrassSpreadToBlock(world, x, y, z) &&
					Block.lightOpacity[world.getBlockId(x, y + 1, z)] <= 2 &&
					!FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z))    		
			{
				return targetBlock.SpreadGrassToBlock(world, x, y, z);
			}
		}

		return false;
	}

	public boolean isSparse(IBlockAccess blockAccess, int x, int y, int z) {
		return isSparse(blockAccess.getBlockMetadata(x, y, z));
	}

	public boolean isSparse(int metadata) {
		return metadata == 1;
	}

	public void setSparse(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, 1);
	}

	public void setFullyGrown(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, 0);
	}
}