package btw.community.sockthing.sockscrops.world;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallGrassBlock;
import btw.world.biome.BiomeDecoratorBase;
import net.minecraft.src.*;

import java.util.Random;

public class WorldDecorator {

    private static final int[] DOUBLE_GRASS_BIOMES = {
            BiomeIDs.RIVER_PLAINS_ID,
            BiomeIDs.RIVER_FOREST_ID,
            BiomeIDs.RIVER_TAIGA_ID,
            BiomeIDs.RIVER_JUNGLE_ID,
    };

    private static final int[] DOUBLE_FERN_BIOMES = {
            BiomeIDs.RIVER_TAIGA_ID,
            BiomeIDs.RIVER_JUNGLE_ID,
    };


    public static void decorateWorld(BiomeDecoratorBase decorator, World world, Random rand, int x, int z, BiomeGenBase biome) {
        genTallGrassFern(world, rand, x, z);
    }

    protected static void genTallGrassFern(World world, Random random, int x, int z) {
        int i;
        int xPos;
        int yPos;
        int zPos;

        // Tall Grass
        for (i = 0; i < 16; ++i) {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 = new DoubleGrassWorldGen(16, SCBlocks.doubleTallGrass.blockID,
                    DoubleTallGrassBlock.GRASS, DOUBLE_GRASS_BIOMES);
            var6.generate(world, random, xPos, yPos, zPos);
        }

        // Tall Fern
        for (i = 0; i < 16; ++i) {
            xPos = x + random.nextInt(16) + 8;
            yPos = random.nextInt(128);
            zPos = z + random.nextInt(16) + 8;
            WorldGenerator var6 = new DoubleGrassWorldGen(8, SCBlocks.doubleTallGrass.blockID,
                    DoubleTallGrassBlock.FERN, DOUBLE_FERN_BIOMES);
            var6.generate(world, random, xPos, yPos, zPos);
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
