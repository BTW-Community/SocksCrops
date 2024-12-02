package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;

import java.util.ArrayList;
import java.util.Random;

public class BerryBushWorldGen {

    /** The ID of the plant block used in this plant generator. */
    private Block plantBlock;

    public BerryBushWorldGen(Block berryBush)
    {
        this.plantBlock = berryBush;
    }

    public boolean generate(World par1World, Random rand, int x, int y, int z)
    {
        for (int var6 = 0; var6 < 6; ++var6)
        {
            if (rand.nextInt(3) == 0) continue;

            int plantX = x + rand.nextInt(4) - rand.nextInt(4);
            int plantY = y;
            int plantZ = z + rand.nextInt(4) - rand.nextInt(4);

            if (par1World.isAirBlock(plantX, plantY, plantZ))
            {
                int randomGrowthStage = 2 + rand.nextInt(4); // 2, 3, 4 or 5

                if (this.plantBlock.canBlockStay(par1World, plantX, plantY, plantZ)) {
                    par1World.setBlock(plantX, plantY, plantZ, this.plantBlock.blockID, randomGrowthStage, 2);
                }
            }
        }

        return true;
    }
}