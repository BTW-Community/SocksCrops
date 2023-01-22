package net.minecraft.src;

import java.util.List;
import java.util.Random;

public abstract class SCBlockBiomeGrassBase extends Block {

	public static final int SPREAD_LIGHT_LEVEL = 9; // 7 previously, 4 vanilla
	public static final int SURVIVE_LIGHT_LEVEL = 9; // 4 previously

	public static final float GROWTH_CHANCE = 0.8F;
	public static final int SELF_GROWTH_CHANCE = 12;

	protected static final int PLAINS = 0;
	protected static final int DESERT = 1;
	protected static final int XHILLS = 2;
	protected static final int FOREST = 3;
	protected static final int TAIGA = 4;
	protected static final int SWAMP = 5;
	protected static final int JUNGLE = 6;
	protected static final int MUSHROOM = 7;

	public static String[] biomeTypes = new String[] { 
			"plains", "desert", "xhills", "forest", "taiga", "swamp", "jungle", "mushroom",
			"plains", "desert", "xhills", "forest", "taiga", "swamp", "jungle", "mushroom" };

	protected SCBlockBiomeGrassBase(int blockID) {
		super(blockID, Material.grass);

		setHardness(0.6F);
		SetShovelsEffectiveOn();
		SetHoesEffectiveOn();

		setStepSound(soundGrassFootstep);

		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {

		if (!canGrassSurviveAtLocation(world, x, y, z)) {
			// convert back to compost or dirt in low light
			Block block = Block.blocksList[world.getBlockId(x, y, z)];

			if (block instanceof SCBlockBiomeGrassCompost) {
				world.setBlockWithNotify(x, y, z, SCDefs.compostBlock.blockID);
			} else if (block instanceof SCBlockBiomeGrassDirt) {
				world.setBlockWithNotify(x, y, z, Block.dirt.blockID);
			}
		} else if (canGrassSpreadFromLocation(world, x, y, z)) {
			if (rand.nextFloat() <= GROWTH_CHANCE) {
				checkForGrassSpreadFromLocation(world, x, y, z);
			}
		}
	}

	public static void checkForGrassSpreadFromLocation(World world, int x, int y, int z) {
		if (world.provider.dimensionId != 1 && !FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z)) {
			// check for grass spread
			int biomeType = world.getBlockMetadata(x, y, z) & 7;

			int i = x + world.rand.nextInt(3) - 1;
			int j = y + world.rand.nextInt(4) - 2;
			int k = z + world.rand.nextInt(3) - 1;

			Block targetBlock = Block.blocksList[world.getBlockId(i, j, k)];

			if (targetBlock != null) {
				attempToSpreadGrassToLocation(world, i, j, k, biomeType);
			}
		}
	}

	public static boolean attempToSpreadGrassToLocation(World world, int x, int y, int z, int biomeType) {
		int targetBlockID = world.getBlockId(x, y, z);
		Block targetBlock = Block.blocksList[targetBlockID];

		if (canGrassSurviveAtLocation(world, x, y, z) && targetBlock instanceof SCBlockCompost) {
			if (((SCBlockCompost) targetBlock).GetCanBiomeGrassSpreadToBlock(world, x, y, z)
					&& Block.lightOpacity[world.getBlockId(x, y + 1, z)] <= 2
					&& !FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z)) {
				return ((SCBlockCompost) targetBlock).SpreadBiomeGrassToBlock(world, x, y, z, biomeType);
			}
		}

		return false;
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

	public static boolean canGrassSurviveAtLocation(World world, int x, int y, int z) {
		int blockAboveID = world.getBlockId(x, y + 1, z);
		Block blockAbove = Block.blocksList[blockAboveID];

		int blockAboveMaxNaturalLight = world.GetBlockNaturalLightValueMaximum(x, y + 1, z);

		int blockAboveCurrentNaturalLight = blockAboveMaxNaturalLight - world.skylightSubtracted;

		if (blockAboveMaxNaturalLight < SURVIVE_LIGHT_LEVEL || Block.lightOpacity[blockAboveID] > 2
				|| (blockAbove != null && !blockAbove.GetCanGrassGrowUnderBlock(world, x, y + 1, z, false))) {
			return false;
		}

		return true;
	}

	public void setSparse(World world, int x, int y, int z) {
		world.setBlockMetadataWithNotify(x, y, z, 1);
	}

	public boolean isSparse(IBlockAccess blockAccess, int x, int y, int z) {
		return isSparse(blockAccess.getBlockMetadata(x, y, z));
	}

	public abstract boolean isSparse(int metadata);

	@Override
	public boolean CanBePistonShoveled(World world, int x, int y, int z) {
		return true;
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
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z,
						new ItemStack(FCBetterThanWolves.fcItemHempSeeds), fromSide);
			}
		}

		return true;
	}

	public abstract int getTypeForIcons(int meta);


	public static int getBiomeColor(int meta) {
		int plains = -7226023;
		int desert = -4212907;
		int xHills = -7686519;
		int forest = -8798118;
		int taiga = -8211053;
		int swamp = 6056270;
		int jungle = -11285961;
		int mushroom = -11155137;

		switch (meta & 7) {
		default:
		case PLAINS:
			return plains;
		case DESERT:
			return desert;
		case XHILLS:
			return xHills;
		case FOREST:
			return forest;
		case TAIGA:
			return taiga;
		case SWAMP:
			return swamp;
		case JUNGLE:
			return jungle;
		case MUSHROOM:
			return mushroom;

		}
	}

}
