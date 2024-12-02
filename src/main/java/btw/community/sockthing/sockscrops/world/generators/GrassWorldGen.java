package btw.community.sockthing.sockscrops.world.generators;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class GrassWorldGen extends WorldGenerator {
    /** Stores ID for WorldGenTallGrass */
    private int tallGrassID;
    private int tallGrassMetadata;
    private int[][] blocks;

    public GrassWorldGen(int blockID, int metadata, int[][] blocks)
    {
        this.tallGrassID = blockID;
        this.tallGrassMetadata = metadata;
        this.blocks = blocks;
    }

    public GrassWorldGen(int[][] blocks)
    {
        this.blocks = blocks;
    }

    public boolean generate(World world, Random rand, int x, int y, int z)
    {
        int var11;

        for (boolean var6 = false; ((var11 = world.getBlockId(x, y, z)) == 0 || var11 == Block.leaves.blockID) && y > 0; --y)
        {
            ;
        }

        for (int var7 = 0; var7 < 128; ++var7)
        {
            int xPos = x + rand.nextInt(8) - rand.nextInt(8);
            int yPos = y + rand.nextInt(4) - rand.nextInt(4);
            int zPos = z + rand.nextInt(8) - rand.nextInt(8);

            if (world.isAirBlock(xPos, yPos, zPos) && Block.blocksList[this.blocks[0][0]].canBlockStay(world, xPos, yPos, zPos))
            {
                if (rand.nextInt(4) == 0)
                {
                    world.setBlock(xPos, yPos, zPos, this.blocks[1][0], this.blocks[1][1], 2);
                }
                else world.setBlock(xPos, yPos, zPos, this.blocks[0][0], this.blocks[0][1], 2);
            }
        }

        return true;
    }
}
