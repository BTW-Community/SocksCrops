package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.ArrayList;
import java.util.Random;

public class BambooWorldGen extends WorldGenerator
{
    private final int[] biomes;

    public BambooWorldGen(int[] biomes)
    {
        this.biomes = biomes;
    }

    public boolean isBiomeValid(int biomeID) {
        for (int i = 0; i < biomes.length; i++) {
            if (biomes[i] == biomeID) return true;
        }
        return false;
    }

    public boolean generate(World world, Random random, int x, int y, int z)
    {
        BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );

        boolean isValidBiome = isBiomeValid(currentBiome.biomeID);

//        System.out.println("Bamboo start");

        for (int var6 = 0; var6 < 128; ++var6)
        {
            int plantX = x + random.nextInt(4) - random.nextInt(4);
            int plantY = y + random.nextInt(2) - random.nextInt(2);
            int plantZ = z + random.nextInt(4) - random.nextInt(4);

            if (world.isAirBlock(plantX, y, plantZ) && world.isAirBlock(plantX, y + 1, plantZ))
            {

                int metaHeight =  11 + random.nextInt(5);

                int height = metaHeight - random.nextInt(8); //2 + par2Random.nextInt(7) + par2Random.nextInt(8);

                if ( !isValidBiome )
                {
                    // must occur after all random number generation to avoid messing up world gen

                    continue;
                }

                for (int newHeight = 0; newHeight < height; ++newHeight)
                {
                    Block bambooBlock;

                    if (newHeight == 0) {
                        bambooBlock = SCBlocks.bambooRoot;
                    }
                    else {
                        bambooBlock = SCBlocks.bambooStalk;
                    }

                    if (world.isAirBlock(plantX, plantY + newHeight, plantZ) && bambooBlock.canBlockStay(world, plantX, plantY + newHeight, plantZ))
                    {
                        world.setBlock(plantX, plantY + newHeight, plantZ, bambooBlock.blockID, metaHeight, 2);
                    }
                    else
                    {
                        break;
                    }

                }


            }
        }

        return true;
    }

}