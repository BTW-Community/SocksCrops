package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.world.BBBiomeIDs;
import btw.community.sockthing.sockscrops.world.BTABiomeIDs;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class CloverWorldGen extends WorldGenerator
{
    private final float flowerChance;
    private final int tries;
    /** The ID of the plant block used in this plant generator. */
    private int blockID;
    private int metadata;

    public CloverWorldGen(int tries, int blockID, int metadata, float flowerChance)
    {
        this.blockID = blockID;
        this.metadata = metadata;
        this.flowerChance = flowerChance;
        this.tries = tries;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int flowerColor = par2Random.nextInt(3) + 1;

        for (int var6 = 0; var6 < this.tries; ++var6)
        {
            int var7 = par3 + par2Random.nextInt(6) - par2Random.nextInt(6);
            int var8 = par4 + par2Random.nextInt(2) - par2Random.nextInt(2);
            int var9 = par5 + par2Random.nextInt(6) - par2Random.nextInt(6);

            int amount = par2Random.nextInt(4) * 4;
            int dir = par2Random.nextInt(4);

            if (par1World.isAirBlock(var7, var8, var9) && Block.blocksList[this.blockID].canBlockStay(par1World, var7, var8, var9))
            {
                if (isSwamp(par1World.getBiomeGenForCoords(par3, par5).biomeID) ){
                    flowerColor = 1;
                }

                if (par2Random.nextFloat() < flowerChance){
                    amount = Math.max(amount, 4);
                    par1World.setBlock(var7, var8, var9, this.blockID + flowerColor, dir + amount, 2);
                }
                else par1World.setBlock(var7, var8, var9, this.blockID, dir + amount, 2);
            }
        }

        return true;
    }

    private boolean isSwamp(int biomeID) {
        if (biomeID == BiomeGenBase.swampland.biomeID) return true;
        if (biomeID == BTABiomeIDs.SWAMP_ID) return true;
        if (biomeID == BBBiomeIDs.WILLOW_GROVE_ID) return true;

        return false;
    }
}
