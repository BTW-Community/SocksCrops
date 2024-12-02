package btw.community.sockthing.sockscrops.world.generators;

import btw.community.sockthing.sockscrops.block.blocks.DoubleTallPlantBlock;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;

import java.util.Random;

public class DoubleGrassWorldGen extends WorldGenerator {
    /**
     * Stores ID for WorldGenTallGrass
     */
    private int tallGrassID;
    private int tallGrassMetadata;
    private final int times;

    private static int[] biomes;
    private final float fernChance;

    public DoubleGrassWorldGen(int times, int id, int meta, int[] biomes, float fernChance) {
        this.tallGrassID = id;
        this.tallGrassMetadata = meta;
        this.times = times;
        this.fernChance = fernChance;

        DoubleGrassWorldGen.biomes = biomes;
    }

    public static boolean isBiomeValid(int biomeID) {
        for (int i = 0; i < biomes.length; i++) {
            if (biomes[i] == biomeID) return true;
        }
        return false;
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
        int var11;


        for (boolean var6 = false; ((var11 = par1World.getBlockId(par3, par4, par5)) == 0 || var11 == Block.leaves.blockID) && par4 > 0; --par4) {
        }

        for (int var7 = 0; var7 < this.times; ++var7) {
            int var8 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var9 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var10 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);

            BiomeGenBase currentBiome = par1World.getBiomeGenForCoords(var8, var10);

            boolean isValidBiome = isBiomeValid(currentBiome.biomeID);


            if (!isValidBiome) {
                // must occur after all random number generation to avoid messing up world gen

                continue;
            }

            if ((par1World.getBlockId(var8, var9, var10) == Block.snow.blockID && par1World.isAirBlock(var8, var9+1, var10)) || par1World.getBlockId(var8, var9 - 1, var10) != this.tallGrassID &&  par1World.isAirBlock(var8, var9, var10) && par1World.isAirBlock(var8, var9 + 1, var10)
                    && Block.blocksList[this.tallGrassID].canBlockStay(par1World, var8, var9, var10)) {

                if (par2Random.nextFloat() < this.fernChance){
                    this.tallGrassMetadata = DoubleTallPlantBlock.FERN;
                }

                par1World.setBlock(var8, var9, var10, this.tallGrassID, this.tallGrassMetadata, 2);
                par1World.setBlock(var8, var9 + 1, var10, this.tallGrassID, this.tallGrassMetadata + 8, 2);

                //if (this.tallGrassMetadata == DoubleTallGrassBlock.GRASS) System.out.println("GEN: GRASS");
                //if (this.tallGrassMetadata == DoubleTallGrassBlock.FERN) System.out.println("GEN: FERN");
            }
        }

        return true;
    }
}
