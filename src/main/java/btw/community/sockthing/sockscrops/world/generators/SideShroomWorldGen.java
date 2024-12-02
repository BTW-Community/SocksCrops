package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class SideShroomWorldGen extends WorldGenerator
{

    public SideShroomWorldGen()
    {

    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {

        BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );

        for (int i = 0; i < 8; ++i)
        {

            int xPos = x; // + rand.nextInt(4) - rand.nextInt(4);
            int yPos = y + rand.nextInt(4) - rand.nextInt(4);
            int zPos = z; // + rand.nextInt(4) - rand.nextInt(4);

            int randDir = rand.nextInt(4);
            int randType = rand.nextInt(2);

            if (world.getBlockId(xPos, yPos, zPos) == Block.wood.blockID)
            {

                int type = 0;
                int meta = world.getBlockMetadata(xPos, yPos, zPos);
                int woodType = meta & 3;
                boolean isStump = (meta & 12) == 12;

                if (woodType > 2 || isStump) continue;

                if ( woodType == 0 || woodType == 2) { // oak or birch
                    if (randType == 0)
                        type = 0;
                    else
                        type = 4;
                }
                else //if (woodType == 1) Spruce
                {
                    if (randType == 0)
                        type = 8;
                    else
                        type = 12;
                }

                if ( randDir == 0 && world.getBlockId(xPos, yPos, zPos - 1) == 0)
                {
                    world.setBlockAndMetadata(xPos, yPos, zPos - 1, SCBlocks.sideShroom.blockID, 0 + type);
                    //System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( randDir == 2 && world.getBlockId(xPos, yPos, zPos + 1) == 0)
                {
                    world.setBlockAndMetadata(xPos, yPos, zPos + 1, SCBlocks.sideShroom.blockID, 2 + type);
                    //System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( randDir == 1 && world.getBlockId(xPos + 1, yPos, zPos) == 0)
                {
                    world.setBlockAndMetadata(xPos + 1,yPos, zPos, SCBlocks.sideShroom.blockID, 1 + type);
                    //System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( randDir == 3 && world.getBlockId(xPos - 1, yPos, zPos) == 0)
                {
                    world.setBlockAndMetadata(xPos - 1,yPos, zPos, SCBlocks.sideShroom.blockID, 3 + type);
                    //System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
            }
        }

        return true;
    }
}
