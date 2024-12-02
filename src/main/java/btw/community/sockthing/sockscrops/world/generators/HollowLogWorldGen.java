package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.world.BBBiomeIDs;
import btw.community.sockthing.sockscrops.world.WorldDecorator;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Arrays;
import java.util.Random;

public class HollowLogWorldGen extends WorldGenerator {

    private BiomeGenBase biome;
    private int[] validBiomes;

    public HollowLogWorldGen(BiomeGenBase biome, int[] validBiomes)
    {
        this.biome = biome;
        this.validBiomes = validBiomes;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        if (!world.checkChunksExist(x - 16, y - 16, z - 16, x + 16, y + 16, z + 16)) {
            //Maybe prevents erros of already decorating
            return false;
        }

        int numberPlaced = 0;

        for (int i = 0; i < 8; ++i) {

            //System.out.println("Trying to place fallen logs...");
            int xPos = x + rand.nextInt(8) - rand.nextInt(8);
            int yPos = y + rand.nextInt(4) - rand.nextInt(4);
            int zPos = z + rand.nextInt(8) - rand.nextInt(8);

            int dir = rand.nextInt(2);

            boolean shouldTryPlacing = true;

            for (int tempX = -1; tempX <= 1 && shouldTryPlacing; tempX++) {
                for (int tempZ = -1; tempZ <= 1; tempZ++) {
                    if (!hasSpace(world, xPos + tempX, yPos, zPos + tempZ)) {
                        shouldTryPlacing = false;
                        break;
                    }
                }
            }

            if (!shouldTryPlacing) {
                //System.out.println("gen failed: not enough space");
                shouldTryPlacing = true;
                continue;
            };

            if (world.getBlockId(xPos, yPos - 1, zPos) == Block.grass.blockID
                    || world.getBlockId(xPos, yPos - 1, zPos) == Block.dirt.blockID )
            {
                int mushroomType = rand.nextInt(4) * 4;
                int treeID = SCBlocks.hollowLog.blockID;
                int treeType = 0;

                if (Arrays.equals(validBiomes, WorldDecorator.HOLLOW_LOG_OAK_BIOMES) && WorldDecorator.isValidBiome(biome.biomeID, WorldDecorator.HOLLOW_LOG_OAK_BIOMES))
                {
                    treeType = 0;
                }
                else if (Arrays.equals(validBiomes, WorldDecorator.HOLLOW_LOG_SPRUCE_BIOMES) && WorldDecorator.isValidBiome(biome.biomeID, WorldDecorator.HOLLOW_LOG_SPRUCE_BIOMES))
                {
                    treeType = 1;
                }
                else if (Arrays.equals(validBiomes, WorldDecorator.HOLLOW_LOG_BIRCH_BIOMES) && WorldDecorator.isValidBiome(biome.biomeID, WorldDecorator.HOLLOW_LOG_BIRCH_BIOMES))
                {
                    treeType = 2;
                }
                else if (Arrays.equals(validBiomes, WorldDecorator.HOLLOW_LOG_JUNGLE_BIOMES) && WorldDecorator.isValidBiome(biome.biomeID, WorldDecorator.HOLLOW_LOG_JUNGLE_BIOMES))
                {
                    treeType = 3;
                }
                else {
                    if (Arrays.equals(validBiomes, WorldDecorator.HOLLOW_LOG_BTA_BIOMES) && WorldDecorator.isValidBiome(biome.biomeID, WorldDecorator.HOLLOW_LOG_BTA_BIOMES)){
                        if (biome.biomeID == BBBiomeIDs.CHERRY_BLOSSOM_GROVE_ID || biome.biomeID == BBBiomeIDs.CHERRY_BLOSSOM_GROVE_HILLS_ID){
                            //Cherry
                            treeID = SCBlocks.decoHollowLog3.blockID;
                            treeType = 0;
                        }
                        else if (biome.biomeID == BBBiomeIDs.SEASONAL_FOREST_ID || biome.biomeID == BBBiomeIDs.SEASONAL_FOREST_HILLS_ID){
                            //Dark Oak
                            treeID = SCBlocks.decoHollowLog.blockID;
                            treeType = 0;
                        }
                        else if (biome.biomeID == BBBiomeIDs.SHIELD_ID){
                            if (rand.nextInt(3) == 0){
                                //Dark Oak
                                treeID = SCBlocks.decoHollowLog.blockID;
                                treeType = 0;
                            }
                            else {
                                //Fir & Aspen
                                treeID = SCBlocks.decoHollowLog2.blockID;
                                treeType = rand.nextInt(2) + 1;
                            }
                        }
                        else if (biome.biomeID == BBBiomeIDs.FROZEN_SPRINGS_ID){
                            if (rand.nextInt(2) == 0){
                                //Birch
                                treeID = SCBlocks.hollowLog.blockID;
                                treeType = 2;
                            }
                            else {
                                //Cherry
                                treeID = SCBlocks.decoHollowLog3.blockID;
                                treeType = 0;
                            }
                        }
                        else if (biome.biomeID == BBBiomeIDs.SNOWY_MAPLE_WOODS_ID
                                || biome.biomeID == BBBiomeIDs.SNOWY_MAPLE_WOODS_HILLS_ID){
                            //Dark Oak
                            treeID = SCBlocks.decoHollowLog.blockID;
                            treeType = 0;
                        }
                        else if (biome.biomeID == BBBiomeIDs.CONIFEROUS_FOREST_ID
                                || biome.biomeID == BBBiomeIDs.CONIFEROUS_FOREST_CLEARING_ID){
                            //Fir
                            treeID = SCBlocks.decoHollowLog2.blockID;
                            treeType = 1;
                        }
                        else if (biome.biomeID == BBBiomeIDs.MYSTIC_Valley_ID){
                            //Cherry
                            treeID = SCBlocks.decoHollowLog3.blockID;
                            treeType = 0;
                        }
                        else if (biome.biomeID == BBBiomeIDs.DARK_FOREST_ID){
                            //Dark Oak
                            treeID = SCBlocks.decoHollowLog.blockID;
                            treeType = 0;
                        }
                    }
                    else return false;
                }

                if (dir == 0)
                {
                    if (placeBlock(world, xPos - 1, yPos, zPos, treeID, 4 + treeType)){
                        placeMossCarpet(world, xPos - 1, yPos + 1, zPos, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos - 1, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 0 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos - 1, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 2 + mushroomType);
                    }

                    if (placeBlock(world, xPos, yPos, zPos, treeID, 4 + treeType)){
                        placeMossCarpet(world, xPos, yPos + 1, zPos, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 0 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 2 + mushroomType);
                    }

                    if (placeBlock(world, xPos + 1, yPos, zPos, treeID, 4 + treeType)){
                        placeMossCarpet(world, xPos + 1, yPos + 1, zPos, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos + 1, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 0 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos + 1, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 2 + mushroomType);
                    }
                }
                else {
                    if (placeBlock(world, xPos, yPos, zPos - 1, treeID, 8 + treeType)){
                        placeMossCarpet(world, xPos, yPos + 1, zPos - 1, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos - 1, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 3 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos + 1, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 1 + mushroomType);
                    }

                    if (placeBlock(world, xPos, yPos, zPos, treeID, 8 + treeType)){
                        placeMossCarpet(world, xPos, yPos + 1, zPos, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos - 1, yPos, zPos, SCBlocks.sideShroom.blockID, 3 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos + 1, yPos, zPos, SCBlocks.sideShroom.blockID, 1 + mushroomType);
                    }

                    if (placeBlock(world, xPos, yPos, zPos + 1, treeID, 8 + treeType)){
                        placeMossCarpet(world, xPos, yPos + 1, zPos + 1, rand);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos - 1, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 3 + mushroomType);
                        if (rand.nextInt(2) == 0) placeBlock(world, xPos + 1, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 1 + mushroomType);
                    }
                }

                //gen clover around
                WorldGenerator genClover = new CloverWorldGen(64, SCBlocks.clover.blockID, 0, 0F);
                genClover.generate(world, rand, xPos, yPos, zPos);

            }
            else {
                //System.out.println("gen failed: block below isn't grass");
            }

            numberPlaced++;
            if (numberPlaced > 1) return false;
            //System.out.println("gen success: " + numberPlaced);
        }

        //System.out.println("fallen logs gen - done");

        return true;

    }

    private void placeMossCarpet(World world, int xPos, int yPos, int zPos, Random rand) {
        if (rand.nextFloat() <= 0.25F){
            placeBlock(world, xPos, yPos, zPos, SCBlocks.clover.blockID + rand.nextInt(4), rand.nextInt(16));
            placeBlock(world, xPos, yPos + 1, zPos, SCBlocks.mossCarpet.blockID, 0);
        }
        else placeBlock(world, xPos, yPos, zPos, SCBlocks.mossCarpet.blockID, 0);
    }

    private boolean hasSpace(World world, int xPos, int yPos, int zPos) {
        return world.getBlockId(xPos,yPos,zPos) == Block.snow.blockID
                || world.isAirBlock(xPos,yPos,zPos)
                || (Block.blocksList[world.getBlockId(xPos, yPos, zPos)].isReplaceableVegetation(world, xPos, yPos, zPos)
        );
    }

    private boolean placeBlock(World world, int xPos, int yPos, int zPos, int blockToPlaceID, int blockToPlaceMeta) {
        if (hasSpace(world, xPos, yPos, zPos))
        {
            world.setBlock(xPos, yPos, zPos, blockToPlaceID, blockToPlaceMeta, 2);
            return true;
        }

        return false;

    }
}
