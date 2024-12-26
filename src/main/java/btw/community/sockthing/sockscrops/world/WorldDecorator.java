package btw.community.sockthing.sockscrops.world;

import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallFlowerBlock;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallPlantBlock;
import btw.community.sockthing.sockscrops.world.generators.*;
import btw.world.biome.BiomeDecoratorBase;
import net.minecraft.src.*;

import java.util.Random;

public class WorldDecorator {

    public static final int[] CLOVER_BIOMES = {
            BiomeGenBase.plains.biomeID,
            BiomeGenBase.forest.biomeID,
            BiomeGenBase.forestHills.biomeID,
            BiomeGenBase.taiga.biomeID,
            BiomeGenBase.taigaHills.biomeID,
            BiomeGenBase.swampland.biomeID,
            BiomeGenBase.icePlains.biomeID,

            BTABiomeIDs.PLAINS_ID,
            BTABiomeIDs.WOODS_ID,
            BTABiomeIDs.WOODS_HILLS_ID,
            BTABiomeIDs.TUNDRA_ID,
            BTABiomeIDs.ICY_PEAKS_FORESTED_ID,
            BTABiomeIDs.SWAMP_ID,
            BTABiomeIDs.SIBERIA_ID,

            BBBiomeIDs.GRASSLANDS_ID,
            BBBiomeIDs.FROZEN_SPRINGS_ID,
            BBBiomeIDs.ALPINE_ID,
            BBBiomeIDs.ALPINE_EDGE_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.SNOWY_CONIFEROUS_FOREST_ID,
            BBBiomeIDs.SNOWY_CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.BOREAL_FOREST_ID,
            BBBiomeIDs.BOREAL_FOREST_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.TEMPERATE_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.SNOWY_WOODS_ID,
            BBBiomeIDs.BIRCH_FOREST_ID,
            BBBiomeIDs.BIRCH_FOREST_HILLS_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID,
            BBBiomeIDs.WILLOW_GROVE_ID,

    };

    public static final int[] DOUBLE_GRASS_BIOMES = {
            BiomeIDs.RIVER_PLAINS_ID,
            BiomeIDs.RIVER_FOREST_ID,
            BiomeIDs.RIVER_TAIGA_ID,
            BiomeIDs.RIVER_JUNGLE_ID,

            //Doesn't gen with BTA/BB
    };

    public static final int[] SUNFLOWER_BIOMES = {
            BiomeGenBase.plains.biomeID,

            BTABiomeIDs.PLAINS_ID,
            BBBiomeIDs.GRASSLANDS_ID,
            BBBiomeIDs.FIELD_ID,
    };



    public static final int[] HOLLOW_LOG_OAK_BIOMES = {
            BiomeGenBase.forest.biomeID,
            BiomeGenBase.forestHills.biomeID,

            BTABiomeIDs.WOODS_ID,
            BTABiomeIDs.WOODS_HILLS_ID,

            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.SNOWY_WOODS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.MYSTIC_Valley_ID,
            BBBiomeIDs.FLORAL_FOREST_ID,
            BBBiomeIDs.OLD_GROWTH_WOODLAND_ID,
            BBBiomeIDs.OLD_GROWTH_WOODLAND_HILLS_ID,
            BBBiomeIDs.ICE_MARSH_ID,
    };

    public static final int[] HOLLOW_LOG_BIRCH_BIOMES = {
            BiomeGenBase.forest.biomeID,
            BiomeGenBase.forestHills.biomeID,

            BTABiomeIDs.WOODS_ID,
            BTABiomeIDs.WOODS_HILLS_ID,

            BBBiomeIDs.BIRCH_FOREST_ID,
            BBBiomeIDs.BIRCH_FOREST_HILLS_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID,
            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.FROZEN_SPRINGS_ID,
            BBBiomeIDs.MYSTIC_Valley_ID,
            BBBiomeIDs.FLORAL_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
    };

    public static final int[] HOLLOW_LOG_SPRUCE_BIOMES = {
            BiomeGenBase.taiga.biomeID,
            BiomeGenBase.taigaHills.biomeID,

            BTABiomeIDs.TUNDRA_ID,
            BTABiomeIDs.ICY_PEAKS_FORESTED_ID,

            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.BOREAL_FOREST_ID,
            BBBiomeIDs.BOREAL_FOREST_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.FLORAL_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
    };

    public static final int[] HOLLOW_LOG_JUNGLE_BIOMES = {
            BiomeGenBase.jungle.biomeID,
            BiomeGenBase.jungleHills.biomeID,

            BTABiomeIDs.JUNGLE_ID,
            BTABiomeIDs.JUNGLE_HILLS_ID,
            BTABiomeIDs.JUNGLE_EDGE_ID,
    };

    public static final int[] HOLLOW_LOG_BTA_BIOMES = {

            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.FROZEN_SPRINGS_ID,
            BBBiomeIDs.SNOWY_MAPLE_WOODS_ID,
            BBBiomeIDs.SNOWY_MAPLE_WOODS_HILLS_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.DARK_FOREST_ID,

    };

    public static final int[][] HOLLOW_LOG_BIOMES = {
            HOLLOW_LOG_OAK_BIOMES,
            HOLLOW_LOG_BIRCH_BIOMES,
            HOLLOW_LOG_SPRUCE_BIOMES,
            HOLLOW_LOG_JUNGLE_BIOMES,
            HOLLOW_LOG_BTA_BIOMES
    };

    public static final int[] SIDESHROOM_BIOMES = {
            BiomeGenBase.forest.biomeID,
            BiomeGenBase.forestHills.biomeID,
            BiomeGenBase.taiga.biomeID,
            BiomeGenBase.taigaHills.biomeID,
            BiomeGenBase.swampland.biomeID,

            BTABiomeIDs.WOODS_ID,
            BTABiomeIDs.WOODS_HILLS_ID,
            BTABiomeIDs.TUNDRA_ID,
            BTABiomeIDs.ICY_PEAKS_FORESTED_ID,
            BTABiomeIDs.SWAMP_ID,

            BBBiomeIDs.FUNGAL_FOREST_ID,
            BBBiomeIDs.FUNGAL_FOREST_FLAT_ID,
            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.BOREAL_FOREST_ID,
            BBBiomeIDs.BOREAL_FOREST_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.FLORAL_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.DARK_FOREST_ID,
            BBBiomeIDs.BIRCH_FOREST_ID,
            BBBiomeIDs.BIRCH_FOREST_HILLS_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID,
    };



    public static final int[] SWEET_BERRY_BIOMES = {
            BiomeGenBase.taiga.biomeID,
            BiomeGenBase.taigaHills.biomeID,
            BiomeGenBase.icePlains.biomeID,

            BTABiomeIDs.TUNDRA_ID,
            BTABiomeIDs.ICY_PEAKS_FORESTED_ID,
            BTABiomeIDs.SIBERIA_ID,

            BBBiomeIDs.FROZEN_SPRINGS_ID,
            BBBiomeIDs.ALPINE_ID,
            BBBiomeIDs.ALPINE_EDGE_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_ID,
            BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.SNOWY_CONIFEROUS_FOREST_ID,
            BBBiomeIDs.SNOWY_CONIFEROUS_FOREST_CLEARING_ID,
            BBBiomeIDs.BOREAL_FOREST_ID,
            BBBiomeIDs.BOREAL_FOREST_HILLS_ID,
            BBBiomeIDs.SHIELD_ID,
            BBBiomeIDs.TEMPERATE_FOREST_ID,
    };

    public static final int[] BLUE_BERRY_BIOMES = {
            BiomeGenBase.forest.biomeID,
            BiomeGenBase.forestHills.biomeID,

            BTABiomeIDs.WOODS_ID,
            BTABiomeIDs.WOODS_HILLS_ID,
            BTABiomeIDs.ARID_FOREST_ID,

            BBBiomeIDs.BIRCH_FOREST_ID,
            BBBiomeIDs.BIRCH_FOREST_HILLS_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID,
            BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID,
            BBBiomeIDs.SEASONAL_FOREST_ID,
            BBBiomeIDs.SEASONAL_FOREST_HILLS_ID,
            BBBiomeIDs.SNOWY_WOODS_ID,
            BBBiomeIDs.SNOWY_MAPLE_WOODS_ID,
            BBBiomeIDs.SNOWY_MAPLE_WOODS_HILLS_ID,
    };

    public static final int[] LILY_BIOMES = {
            BiomeGenBase.swampland.biomeID,
            BiomeIDs.RIVER_SWAMP_ID,
    };

    public static final int[] WATER_GRASS_BIOMES = {
            BiomeGenBase.swampland.biomeID,
            BiomeGenBase.jungle.biomeID,
            BiomeGenBase.jungleHills.biomeID,

            BiomeIDs.RIVER_SWAMP_ID,
            BiomeIDs.RIVER_JUNGLE_ID,
    };

    public static final int[] BAMBOO_BIOMES = {
            BiomeGenBase.jungleHills.biomeID,

            BTABiomeIDs.JUNGLE_HILLS_ID,
    };

    public static boolean isValidBiome(int biomeID, int[] biomeIDs){
        for (int i = 0; i < biomeIDs.length; i++) {
            if (biomeID == biomeIDs[i]) return true;
        }

        return false;
    }

    public static void decorateWorld(BiomeDecoratorBase decorator, World world, Random rand, int x, int z, BiomeGenBase biome) {

        genHollowLog(world, rand, x, z, biome);
        if (!SocksCropsAddon.isDecoInstalled()) {
            genTallGrassFern(world, rand, x, z, biome);
        }
        genSunflowers(world, rand, x, z, biome);
        genSideShroom(world, rand, x, z, biome);
        genClover(world, rand, x, z, biome);
        genBerryBush(world, rand, x, z, biome);
        genBamboo(world, rand, x, z, biome);

        genLilyRose(world, rand, x, z, biome);
        genWaterPlants(world, rand, x, z, biome);
    }

    private static void genWaterPlants(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (isValidBiome(biome.biomeID, WATER_GRASS_BIOMES) && random.nextFloat() <= 1/4F){
            for (i = 0; i < 4; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 62;
                zPos = z + random.nextInt(16) + 8;

                new WorldGenWaterPlants(8, SCBlocks.tallWaterPlant, 1, true).generate(world, random, xPos, yPos, zPos);
            }

            for (i = 0; i < 4; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 62;
                zPos = z + random.nextInt(16) + 8;
                int type = random.nextInt(2);
                new WorldGenWaterPlants(16, SCBlocks.shortWaterPlant, type, false).generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    private static void genLilyRose(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (isValidBiome(biome.biomeID, LILY_BIOMES) && random.nextFloat() <= 1/8F){
            for (i = 0; i < 4; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 62;
                zPos = z + random.nextInt(16) + 8;
                int type = random.nextInt(2);
                new WorldGenWaterPlants(5, SCBlocks.flowerLily, type, false).generate(world, random, xPos, yPos, zPos);
            }
        }
    }


    private static void genClover(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        //Clover
        for (i = 0; i < 12; ++i)
        {
            xPos = x + random.nextInt(16) + 8;
            yPos = 60 + random.nextInt(64);
            zPos = z + random.nextInt(16) + 8;

            if (isValidBiome(biome.biomeID, CLOVER_BIOMES)){
                float flowerChance = 0.25F;
                if (biome.biomeID == BiomeGenBase.plains.biomeID) flowerChance = 0F;

                WorldGenerator gen = new CloverWorldGen(48, SCBlocks.clover.blockID, 0, flowerChance);
                gen.generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    private static void genSideShroom(World world, Random random, int x, int z,BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (isValidBiome(biome.biomeID, SIDESHROOM_BIOMES)){
            for (i = 0; i < 64; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 60 + random.nextInt(64);
                zPos = z + random.nextInt(16) + 8;

                new SideShroomWorldGen().generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    private static void genHollowLog(World world, Random random, int x, int z, BiomeGenBase biome) {

        for (int i = 0; i < HOLLOW_LOG_BIOMES.length; i++) {
            int xPos = x + random.nextInt(16) + 8;
            int zPos = z + random.nextInt(16) + 8;
            int yPos = world.getHeightValue(xPos, zPos) + 1;

            WorldGenerator generator = new HollowLogWorldGen(biome, HOLLOW_LOG_BIOMES[i]);
            generator.generate(world, random, xPos, yPos, zPos);
        }
    }

    private static void genTallGrassFern(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        // Tall Grass
        for (i = 0; i < 16; ++i) {
            xPos = x + random.nextInt(16) + 8;
            yPos = 60 + random.nextInt(64);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 = new DoubleGrassWorldGen(16, SCBlocks.doubleTallPlant.blockID,
                    DoubleTallPlantBlock.GRASS, DOUBLE_GRASS_BIOMES, 0.25F);
            var6.generate(world, random, xPos, yPos, zPos);
        }
    }

    private static void genSunflowers(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (random.nextInt(3 * 128) == 0) {
            for (i = 0; i < 16; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 60 + random.nextInt(64);
                zPos = z + random.nextInt(16) + 8;
                WorldGenerator var6 = new DoubleGrassWorldGen(16, SCBlocks.tallFlower.blockID,
                        DoubleTallFlowerBlock.SUNFLOWER, SUNFLOWER_BIOMES, 0.0F);
                var6.generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    protected static void genBerryBush(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (isValidBiome(biome.biomeID, BLUE_BERRY_BIOMES)){
            for (i = 0; i < 4; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 60 + random.nextInt(64);
                zPos = z + random.nextInt(16) + 8;
                new BerryBushWorldGen(SCBlocks.blueberryBush).generate(world, random, xPos, yPos, zPos);
            }
        }
        else if (isValidBiome(biome.biomeID, SWEET_BERRY_BIOMES)){
            int count = 4;
            if (biome.biomeID == BiomeGenBase.icePlains.biomeID || biome.biomeID == BTABiomeIDs.SIBERIA_ID) count = 1;
            for (i = 0; i < count; ++i) {
                xPos = x + random.nextInt(16) + 8;
                yPos = 60 + random.nextInt(64);
                zPos = z + random.nextInt(16) + 8;
                new BerryBushWorldGen(SCBlocks.sweetberryBush).generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    private static void genBamboo(World world, Random random, int x, int z, BiomeGenBase biome) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        if (random.nextInt(32) == 0) {
            for (i = 0; i < 64; ++i) {
                xPos = x + random.nextInt(16) + 4;
                yPos = 60 + random.nextInt(64);
                zPos = z + random.nextInt(16) + 4;
                WorldGenerator var6 = new BambooWorldGen(BAMBOO_BIOMES);
                var6.generate(world, random, xPos, yPos, zPos);
            }
        }
    }

    public static void debugRivers(World world, int x, int z) {

        int i;
        int xPos;
        int yPos;
        int zPos;

        for (i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                xPos = x + i;
                zPos = z + j;

                BiomeGenBase currentBiome = world.getBiomeGenForCoords(xPos, zPos);
                Block block = Block.blockLapis;

                if (currentBiome == BiomeGenBase.ocean) {
                    block = Block.glass;

                    world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
                }

                if (currentBiome instanceof BiomeGenRiver) {
                    if (currentBiome.biomeID == BiomeIDs.RIVER_DESERT_ID) block = Block.blockGold;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_XHILLS_ID) block = Block.blockEmerald;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_FOREST_ID) block = Block.blockIron;

                    if (currentBiome.biomeID == BiomeGenBase.frozenRiver.biomeID) block = Block.glass;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_SWAMP_ID) block = Block.brick;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_TAIGA_ID) block = Block.blockDiamond;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_JUNGLE_ID) block = Block.blockRedstone;

                    if (currentBiome.biomeID == BiomeIDs.RIVER_PLAINS_ID) block = Block.blockNetherQuartz;

                    world.setBlock(xPos, 80, zPos, block.blockID, 0, 2);
                } else if (currentBiome instanceof BiomeGenBeach) {
                    block = Block.whiteStone;

                    if (currentBiome.biomeID == BiomeIDs.BEACH_DESERT_ID) block = Block.blockGold;

                    if (currentBiome.biomeID == BiomeIDs.BEACH_FOREST_ID) block = Block.blockIron;

                    if (currentBiome.biomeID == BiomeIDs.BEACH_TAIGA_ID) block = Block.blockDiamond;

                    if (currentBiome.biomeID == BiomeIDs.BEACH_JUNGLE_ID) block = Block.blockRedstone;

                    if (currentBiome.biomeID == BiomeIDs.BEACH_PLAINS_ID) block = Block.blockNetherQuartz;

                    world.setBlock(xPos, 79, zPos, block.blockID, 0, 2);
                }
            }
        }
    }
}
