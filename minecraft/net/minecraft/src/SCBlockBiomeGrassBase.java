package net.minecraft.src;

import java.util.List;
import java.util.Random;

import betterterrain.biome.BTABiome;
import betterterrain.biome.BTABiomeConfiguration;

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
		int plains = SCBiomeGrassColors.plains; //-7226023;
		int desert = SCBiomeGrassColors.desert; //-4212907;
		int xHills = SCBiomeGrassColors.extremeHills; //-7686519;
		int forest = SCBiomeGrassColors.forest; //-8798118;
		int taiga = SCBiomeGrassColors.taiga; //-8211053;
		int swamp = SCBiomeGrassColors.swampland; //6056270;
		int jungle = SCBiomeGrassColors.jungle; // -11285961;
		int mushroom = SCBiomeGrassColors.mushroomIsland; //-11155137;		
		
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

	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		BiomeGenBase currentBiome = blockAccess.getBiomeGenForCoords(x, z);
		int grassColor = currentBiome.getBiomeGrassColor();
		int meta = blockAccess.getBlockMetadata(x, y, z);

		// Grass color values

		boolean isPlains = currentBiome.biomeID == BiomeGenBase.plains.biomeID;
		boolean isDesert = currentBiome.biomeID == BiomeGenBase.desert.biomeID || currentBiome.biomeID == BiomeGenBase.desertHills.biomeID;
		boolean isXHills = currentBiome.biomeID == BiomeGenBase.extremeHills.biomeID || currentBiome.biomeID == BiomeGenBase.extremeHillsEdge.biomeID;
		boolean isForest = currentBiome.biomeID == BiomeGenBase.forest.biomeID || currentBiome.biomeID == BiomeGenBase.forestHills.biomeID;
		boolean isTaiga = currentBiome.biomeID == BiomeGenBase.taiga.biomeID || currentBiome.biomeID == BiomeGenBase.taigaHills.biomeID || currentBiome.biomeID == BiomeGenBase.icePlains.biomeID || currentBiome.biomeID == BiomeGenBase.iceMountains.biomeID;
		boolean isSwamp = currentBiome.biomeID == BiomeGenBase.swampland.biomeID;
		boolean isJungle = currentBiome.biomeID == BiomeGenBase.jungle.biomeID || currentBiome.biomeID == BiomeGenBase.jungleHills.biomeID;
		boolean isMushroom = currentBiome.biomeID == BiomeGenBase.mushroomIsland.biomeID || currentBiome.biomeID == BiomeGenBase.mushroomIslandShore.biomeID;

		// System.out.println(grassColor);

		if (!grassPass) {
			return 16777215;
		} else {
			return getBiomeColor(meta);
//	        int var5 = 0;
//	        int var6 = 0;
//	        int var7 = 0;
//
//	        for (int var8 = -1; var8 <= 1; ++var8)
//	        {
//	            for (int var9 = -1; var9 <= 1; ++var9)
//	            {
//	                int var10 = blockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor();
//	                var5 += (var10 & 16711680) >> 16;
//	                var6 += (var10 & 65280) >> 8;
//	                var7 += var10 & 255;
//	            }
//	        }
//
//	        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
		}
	}

	protected boolean grassPass;

	protected Icon[] dirt = new Icon[4];
	protected Icon[] grassTop = new Icon[4];
	protected Icon[] grassSide = new Icon[4];

	@Override
	public void registerIcons(IconRegister register) {
		grassTop[0] = register.registerIcon("SCBlockBiomeGrass_top_podzol");
		grassTop[1] = register.registerIcon("SCBlockBiomeGrass_top_dirt");
		grassTop[2] = register.registerIcon("fcBlockGrassSparse");
		grassTop[3] = register.registerIcon("grass_top");

		grassSide[0] = register.registerIcon("SCBlockBiomeGrass_side_podzol");
		grassSide[1] = register.registerIcon("SCBlockBiomeGrass_side_dirt");
		grassSide[2] = register.registerIcon("grass_side_overlay");
		grassSide[3] = register.registerIcon("grass_side_overlay");

		dirt[0] = register.registerIcon("SCBlockCompost");
		dirt[1] = register.registerIcon("SCBlockBiomeGrass_podzol");
		dirt[2] = register.registerIcon("SCBlockBiomeGrass_dirt");
		dirt[3] = register.registerIcon("dirt");
	}

	@Override
	public Icon getIcon(int side, int meta) {
		return dirt[getTypeForIcons(meta)];
	}

	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {

		int meta = blockAccess.getBlockMetadata(x, y, z);

		if (!grassPass) {
			return dirt[getTypeForIcons(meta)];
		} else {
			return getBlockTextureSecondPass(blockAccess, x, y, z, side);
		}
	}

	public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = blockAccess.getBlockMetadata(x, y, z);

		if (side == 1) {
			return grassTop[getTypeForIcons(meta)];
		} else
			return grassSide[getTypeForIcons(meta)];
	}

	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ,
			int side) {
		FCUtilsBlockPos pos = new FCUtilsBlockPos(neighborX, neighborY, neighborZ, Facing.oppositeSide[side]);

		if (!grassPass) {
			// Don't render dirt under normal grass
			if (side == 1) {
				// return false;
			}
		} else {
			// Bottom never has a second pass texture
			if (side == 0) {
				return false;
			}
			// Snow has its own texture and should not render the second pass
//			else if (side >= 2) {
//				return false;
//			}
		}

		Block neighborBlock = Block.blocksList[blockAccess.getBlockId(neighborX, neighborY, neighborZ)];

		if (neighborBlock != null) {
			return neighborBlock.ShouldRenderNeighborFullFaceSide(blockAccess, neighborX, neighborY, neighborZ, side);
		}

		return true;
	}

	@Override
	public boolean RenderBlock(RenderBlocks render, int x, int y, int z) {

		render.setRenderBounds(0, 0, 0, 1, 1, 1);
		return render.renderStandardBlock(this, x, y, z);
	}

	@Override
	public void RenderBlockSecondPass(RenderBlocks render, int x, int y, int z, boolean firstPassResult) {
		grassPass = true;
		render.setRenderBounds(0, 0, 0, 1, 1, 1);
		render.renderStandardBlock(this, x, y, z);
		grassPass = false;
	}

	@Override
	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {

		SCUtilsRender.renderBiomeGrassAsItem(renderBlocks, this, iItemDamage, fBrightness, getTypeForIcons(iItemDamage),
				grassTop, grassSide, dirt);
	}
}
