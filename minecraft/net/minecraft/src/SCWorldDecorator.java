package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldDecorator {

	//BTA Biome ID's
	public static final int 
		SWAMP_ID = 50,
		SWAMP_RIVER_ID = 51,
		NETHER_WASTES_ID = 90,
		WOODS_ID = 100,
		DESERT_ID = 101,
		JUNGLE_ID = 113,
		MOUNTAINS_ID = 125,
		TUNDRA_ID = 132,
		ICY_PEAKS_ID = 134,
		SIBERIA_ID = 137,
		PLAINS_ID = 138,
		ARID_FOREST_ID = 142,
		WOODS_HILLS_ID = 150,
		DESERT_HILLS_ID = 151,
		JUNGLE_HILLS_ID = 157,
		ICY_PEAKS_FORESTED_ID = 165,
		DESERT_RIVER_ID = 200,
		JUNGLE_RIVER_ID = 207,
		RIVER_ID = 211,
		FROZEN_RIVER_ID = 212,
		MOUNTAIN_EDGE_ID = 231,
		ICY_PEAKS_EDGE_ID = 233,
		JUNGLE_EDGE_ID = 235,
		BEACH_ID = 241,
		FROZEN_BEACH_ID = 242;
	
	//BB Biome ID's
	public static final int
		// Nether
		// NETHER_WASTES_ID = 90,
		ASH_FIELDS_ID = 91,
		BASALT_DELTAS_ID = 92,
		SOUL_SAND_VALLEY_ID = 93,
		OBSIDIAN_GROVE_ID = 94,
		CRYSTAL_CAVERNS_ID = 95,
		PETRIFIED_FOREST_ID = 96,
		// Primary
		// WOODS_ID = 100,
		// DESERT_ID = 101,
		LUSH_DESERT_ID = 102,
		OASIS_ID = 103,
		SAVANNA_ID = 104,
		WETLANDS_ID = 105,
		BIRCH_FOREST_ID = 106,
		SNOWY_WOODS_ID = 107,
		STEPPE_ID = 108,
		WOODED_STEPPE_ID = 109,
		CHAPPARAL_ID = 110,
		ANCIENT_FOREST_ID = 111,
		TROPICS_ID = 112,
		// JUNGLE_ID = 113,
		ALPINE_ID = 114,
		ASPEN_GROVE_ID = 115,
		FUNGAL_FOREST_ID = 116,
		CONIFEROUS_FOREST_ID = 117,
		CONIFEROUS_FOREST_CLEARING_ID = 118,
		SNOWY_CONIFEROUS_FOREST_ID = 119,
		SNOWY_CONIFEROUS_FOREST_CLEARING_ID = 120,
		MYSTIC_Valley_ID = 121,
		RAINFOREST_ID = 122,
		MEADOW_ID = 123,
		ORCHARD_ID = 124,
		// MOUNTAINS_ID = 125,
		DUNES_ID = 126,
		HEATHLAND_ID = 127,
		HEATHLAND_WOODS_ID = 128,
		TEMPERATE_FOREST_ID = 129,
		VALLEY_MOUNTAINS_ID = 130,
		OLD_VALLEY_ID = 131,
		// TUNDRA_ID = 132,
		WILLOW_GROVE_ID = 133,
		// ICY_PEAKS_ID = 134,
		PATAGONIA_ID = 135,
		GRASSLANDS_ID = 136,
		// SIBERIA_ID = 137,
		// PLAINS_ID = 138,
		FROZEN_SPRINGS_ID = 139,
		MANGROVE_FOREST_ID = 140,
		BOREAL_FOREST_ID = 141,
		// ARID_FOREST_ID = 142,
		SHIELD_ID = 143,
		BRUSHLAND_ID = 144,
		HIGHLANDS_ID = 145,
		FLORAL_FOREST_ID = 146,

		// Sub biomes
		// WOODS_HILLS_ID = 150,
		// DESERT_HILLS_ID = 151,
		SAVANNA_HILLS_ID = 152,
		BIRCH_FOREST_HILLS_ID = 153,
		SNOWY_WOODS_HILLS = 154,
		CHAPPARAL_HILLS_ID = 155,
		ANCIENT_FOREST_HILLS_ID = 156,
		// JUNGLE_HILLS_ID = 157,
		FUNGAL_FOREST_FLAT_ID = 158,
		WETLANDS_HILLS_ID = 159,
		CHERRY_FOREST_HILLS_ID = 160,
		AUTUMN_FOREST_HILLS_ID = 161,
		VALLEY_ID = 162,
		ORCHARD_CLEARING_ID = 163,
		WILLOW_HILLS_ID = 164,
		// ICY_PEAKS_FORESTED_ID = 165,
		PATAGONIA_MOUNTAINS_ID = 166,
		GRASSLANDS_LAKE_ID = 167,
		FROZEN_SPRINGS_POND_ID = 168,
		MANGROVE_FOREST_ISLAND_ID = 169,
		BOREAL_FOREST_HILLS_ID = 170,
		SAVANNA_PLATEAU_ID = 171,
		FIR_CANYON_VALLEY_ID = 172,
		FLORAL_PLATEAU_ID = 173,

		// Deco only
		OUTBACK_ID = 180,
		CHERRY_FOREST_ID = 181,
		BADLANDS_ID = 182,
		BADLANDS_PLATEAU_ID = 183,
		AUTUMN_FOREST_ID = 184,
		IVORY_HILLS_ID = 185,
		HOT_SPRINGS_ID = 186,
		VOLCANIC_JUNGLE_ID = 187,
		FIR_CANYON_ID = 188,

		// Rivers
		// DESERT_RIVER_ID = 200,
		MYSTIC_RIVER_ID = 201,
		RAINFOREST_RIVER_ID = 202,
		OUTBACK_RIVER_ID = 203,
		BADLANDS_RIVER_ID = 204,
		TROPICS_RIVER_ID = 205,
		ORCHARD_RIVER_ID = 206,
		// JUNGLE_RIVER_ID = 207,
		WETLANDS_RIVER_ID = 208,
		WILLOW_GROVE_RIVER_ID = 209,
		PATAGONIA_RIVER_ID = 210,
		// RIVER_ID = 211,
		// FROZEN_RIVER_ID = 212,
		VOLCANIC_RIVER_ID = 213,

		// Edges
		ALPINE_EDGE_ID = 230,
		// MOUNTAIN_EDGE_ID = 231,
		BADLANDS_EDGE_ID = 232,
		// ICY_PEAKS_EDGE_ID = 233,
		HIGHLANDS_EDGE_OLD_ID = 234,
		// JUNGLE_EDGE_ID = 235,
		RAINFOREST_EDGE_ID = 236,
		TROPICS_EDGE_ID = 237,
		HOT_SPRINGS_EDGE_ID = 238,
		HIGHLANDS_EDGE_ID = 239,
		// Beaches
		RED_SAND_BEACH_ID = 240,
		// BEACH_ID = 241,
		// FROZEN_BEACH_ID = 242,
		VOLCANIC_BEACH_ID = 243,
		IVORY_BEACH_ID = 244;

	public static void decorateWorld(FCIBiomeDecorator decorator, World world, Random random, int x, int z,
			BiomeGenBase biome) {
		ArrayList<Integer> validBiomeList = new ArrayList();

		validBiomeList.add(BiomeGenBase.forest.biomeID);
		validBiomeList.add(BiomeGenBase.forestHills.biomeID);
		validBiomeList.add(BiomeGenBase.taiga.biomeID);
		validBiomeList.add(BiomeGenBase.taigaHills.biomeID);
		validBiomeList.add(BiomeGenBase.jungle.biomeID);
		validBiomeList.add(BiomeGenBase.jungleHills.biomeID);
		validBiomeList.add(WOODS_ID);
		validBiomeList.add(WOODS_HILLS_ID);
		validBiomeList.add(SIBERIA_ID);
		validBiomeList.add(JUNGLE_ID);
		validBiomeList.add(JUNGLE_HILLS_ID);
		validBiomeList.add(JUNGLE_EDGE_ID);
		if (validBiomeList.contains(biome.biomeID)) {
			genTallGrassFern(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Sunflower
		validBiomeList.add(BiomeGenBase.plains.biomeID);
		validBiomeList.add(PLAINS_ID);
		if (validBiomeList.contains(biome.biomeID)) {
			genSunflower(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Clover
		validBiomeList.add(BiomeGenBase.forest.biomeID);
		validBiomeList.add(BiomeGenBase.forestHills.biomeID);
		validBiomeList.add(WOODS_ID);
		validBiomeList.add(WOODS_HILLS_ID);
		if (validBiomeList.contains(biome.biomeID)) {
			System.out.println("GEN: CLOVER");

			genClover(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Flower lilys
		validBiomeList.add(BiomeGenBase.swampland.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genLily(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Bamboo
		validBiomeList.add(BiomeGenBase.jungleHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genBamboo(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Bushes
		validBiomeList.add(BiomeGenBase.forest.biomeID);
		validBiomeList.add(BiomeGenBase.forestHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genBerryBush(world, random, SCDefs.blueberryBush, x, z, validBiomeList);
		}
		validBiomeList.clear();

		validBiomeList.add(BiomeGenBase.taiga.biomeID);
		validBiomeList.add(BiomeGenBase.taigaHills.biomeID);
		validBiomeList.add(BiomeGenBase.icePlains.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genBerryBush(world, random, SCDefs.sweetberryBush, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Wild Crops
		validBiomeList.add(BiomeGenBase.plains.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			int[] cropTypes = { SCBlockWildFlowerCrop.CARROT, SCBlockWildFlowerCrop.ONION,
					SCBlockWildFlowerCrop.SALAD };
			int crop = cropTypes[random.nextInt(cropTypes.length)];
			genWildCrops(world, random, x, z, crop, validBiomeList);
		}
		validBiomeList.clear();

		validBiomeList.add(BiomeGenBase.extremeHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genWildCrops(world, random, x, z, SCBlockWildFlowerCrop.POTATO, validBiomeList);
		}
		validBiomeList.clear();

		// Hollow Logs
		validBiomeList.add(BiomeGenBase.forest.biomeID);
		validBiomeList.add(BiomeGenBase.forestHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genHollowLog(world, random, x, z, SCWorldGenHollowLogs.oak, validBiomeList);
		}
		validBiomeList.clear();

		validBiomeList.add(BiomeGenBase.taiga.biomeID);
		validBiomeList.add(BiomeGenBase.taigaHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genHollowLog(world, random, x, z, SCWorldGenHollowLogs.spruce, validBiomeList);
		}
		validBiomeList.clear();

		validBiomeList.add(BiomeGenBase.jungle.biomeID);
		validBiomeList.add(BiomeGenBase.jungleHills.biomeID);
		validBiomeList.add(BiomeGenBase.riverJungle.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genHollowLog(world, random, x, z, SCWorldGenHollowLogs.jungle, validBiomeList);
		}
		validBiomeList.clear();

		// SideShrooms
		validBiomeList.add(BiomeGenBase.forest.biomeID);
		validBiomeList.add(BiomeGenBase.forestHills.biomeID);
		validBiomeList.add(BiomeGenBase.taiga.biomeID);
		validBiomeList.add(BiomeGenBase.taigaHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genSideShroom(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Rice
		validBiomeList.add(BiomeGenBase.riverDesert.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genRice(world, random, x, z, validBiomeList);
		}
		validBiomeList.clear();

		// Coconut
		validBiomeList.add(BiomeGenBase.beachDesert.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			genCoconut(world, random, x, z);
		}
		validBiomeList.clear();

		// Rocks
		int minY = 0;
		int maxY = 128;
		validBiomeList.add(BiomeGenBase.plains.biomeID);
		validBiomeList.add(BiomeGenBase.desert.biomeID);
		validBiomeList.add(BiomeGenBase.extremeHills.biomeID);
		if (validBiomeList.contains(biome.biomeID)) {
			minY = 0;
			maxY = 20;
			genRocks(world, random, x, z, minY, maxY, SCDefs.rocksStrata2, validBiomeList);

			minY = 20;
			maxY = 40;
			genRocks(world, random, x, z, minY, maxY, SCDefs.rocksStrata1, validBiomeList);

			minY = 40;
			maxY = 60;
			genRocks(world, random, x, z, minY, maxY, SCDefs.rocks, validBiomeList);

		}
		validBiomeList.clear();

	}

	
	protected static void genRocks(World world, Random random, int x, int z, int minY, int maxY, Block rock,
			ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;

		for (i = 0; i < 8; ++i) {
			if (random.nextInt(4) == 0) {
				System.out.println("GEN: ROCKS");
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				if (yPos >= minY && yPos <= maxY) {
					new SCWorldGenRocks2(rock, biomes).generate(world, random, xPos, yPos, zPos);
				}

			}
		}
	}

	protected static void genCoconut(World world, Random random, int x, int z) {
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(12) == 0) {
			System.out.println("GEN: COCONUT");

			xPos = x + random.nextInt(16) + 8;
			zPos = z + random.nextInt(16) + 8;
			yPos = world.getHeightValue(xPos, zPos);

			new SCWorldGenPalmTreeSmall(false, true).generate(world, random, xPos, yPos, zPos);
		}
	}

	protected static void genRice(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(32) == 0) {
			System.out.println("GEN: RICE");
			for (i = 0; i < 8; ++i) {
				xPos = x + random.nextInt(16) + 8;
				zPos = z + random.nextInt(16) + 8;
				yPos = world.getHeightValue(xPos, zPos);

				new SCWorldGenRice(biomes).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genSideShroom(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(2) == 0) {
			System.out.println("GEN: SIDESHROOM");

			for (i = 0; i < 64; ++i) {
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				(new SCWorldGenSideShrooms(biomes)).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genHollowLog(World world, Random random, int x, int z, int type, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(2) == 0) {
			System.out.println("GEN: HOLLOW LOG");

			for (i = 0; i < 8; ++i) {
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenHollowLogs(type, biomes)).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genWildCrops(World world, Random random, int x, int z, int crop, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(32) == 0) {
			for (i = 0; i < 8; ++i) {
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				new SCWorldGenWildCrops(32, SCDefs.wildCropFlower, crop, biomes).generate(world, random, xPos, yPos,
						zPos);
			}
		}
	}

	protected static void genBerryBush(World world, Random random, Block berry, int x, int z,
			ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(3) == 0) {
			for (i = 0; i < 4; ++i) {
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenBerryBush(berry, biomes)).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genBamboo(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextFloat() <= 0.05F) {
			System.out.println("GEN: BAMBOO");

			for (i = 0; i < 32; ++i) {
				xPos = x + random.nextInt(16) + 4;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 4;
				(new SCWorldGenBamboo(biomes)).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genLily(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(4) == 0) {
			System.out.println("GEN: LILYS");

			for (i = 0; i < 1; ++i) {
				xPos = x + random.nextInt(16) + 8;
				zPos = z + random.nextInt(16) + 8;

				for (yPos = random.nextInt(128); yPos > 0 && world.getBlockId(xPos, yPos - 1, zPos) == 0; --yPos) {

				}

				(new SCWorldGenLilyRose(biomes)).generate(world, random, xPos, yPos, zPos);
			}
		}
	}

	protected static void genTallGrassFern(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		System.out.println("GEN: GRASS");
		// Tall Grass
		for (i = 0; i < 1; ++i) {
			xPos = x + random.nextInt(16) + 8;
			yPos = random.nextInt(128);
			zPos = z + random.nextInt(16) + 8;
			WorldGenerator var6 = new SCWorldGenDoubleGrass(16, SCDefs.doubleTallGrass.blockID,
					SCBlockDoubleTallGrass.GRASS, biomes);
			var6.generate(world, random, xPos, yPos, zPos);
		}

		// Tall Fern
		System.out.println("GEN: FERN");
		for (i = 0; i < 1; ++i) {
			xPos = x + random.nextInt(16) + 8;
			yPos = random.nextInt(128);
			zPos = z + random.nextInt(16) + 8;
			WorldGenerator var6 = new SCWorldGenDoubleGrass(8, SCDefs.doubleTallGrass.blockID,
					SCBlockDoubleTallGrass.FERN, biomes);
			var6.generate(world, random, xPos, yPos, zPos);
		}
	}

	protected static void genClover(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;

		System.out.println("GEN: CLOVER");

		for (i = 0; i < 2; ++i) {
			// Clover Variants
			if (random.nextInt(6) == 0) {
				int randomType = random.nextInt(3) + 1;

				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				(new SCWorldGenClover(SCDefs.clover.blockID, randomType, biomes)).generate(world, random, xPos, yPos,
						zPos);
			} else {
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				(new SCWorldGenClover(SCDefs.clover.blockID, 0, biomes)).generate(world, random, xPos, yPos, zPos);

			}
		}
	}

	protected static void genSunflower(World world, Random random, int x, int z, ArrayList<Integer> biomes) {
		int i;
		int xPos;
		int yPos;
		int zPos;
		if (random.nextInt(16) == 0) {
			System.out.println("GEN: SUNFLOWER");

			for (i = 0; i < 8; ++i) {
				xPos = x + random.nextInt(16) + 8;
				yPos = random.nextInt(128);
				zPos = z + random.nextInt(16) + 8;
				WorldGenerator var6 = new SCWorldGenSunflower(16, SCDefs.doubleTallGrass.blockID,
						SCBlockDoubleTallGrass.SUNFLOWER, biomes);
				var6.generate(world, random, xPos, yPos, zPos);
			}
		}
	}

}
