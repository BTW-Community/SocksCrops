package btw.community.sockthing.sockscrops.world.generators;

import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenWaterlily;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class WorldGenWaterPlants extends WorldGenerator
{
    private final Block plantBlock;
    private final int plantBlockMeta;
    private final boolean isDoubleTall;
    private final int count;

    public WorldGenWaterPlants(int count, Block plantBlock, int plantBlockMeta, boolean isDoubleTall){
        this.plantBlock = plantBlock;
        this.plantBlockMeta = plantBlockMeta;
        this.isDoubleTall = isDoubleTall;
        this.count = count;
    }
    
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        for (int var6 = 0; var6 < count; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            if (par1World.isAirBlock(var7, var8, var9) && plantBlock.canPlaceBlockAt(par1World, var7, var8, var9))
            {
                par1World.setBlock(var7, var8, var9, plantBlock.blockID, plantBlockMeta, 2);

                if (this.isDoubleTall)
                {
                    par1World.setBlock(var7, var8 + 1, var9, plantBlock.blockID, plantBlockMeta + 8, 2);
                }
            }
        }

        return true;
    }
}
