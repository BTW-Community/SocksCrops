package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenRocks2 extends WorldGenerator
{
	private static ArrayList<Integer> validBiomeList = new ArrayList();

	private Block rock;

	
	public static boolean isBiomeValid(int biomeID) {
		return validBiomeList.contains(biomeID);
	}
    public SCWorldGenRocks2(Block rock, ArrayList<Integer> validBiomeList)
    {
        this.validBiomeList = validBiomeList;
        this.rock = rock;
    }
	
	
    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( par3, par5);
    	
        for (int var6 = 0; var6 < 64; ++var6)
        {
        	//Block rock = SCDefs.rocks;
        	
        	boolean isMossy = par2Random.nextInt(4) == 0;
            boolean isLarge = par2Random.nextInt(2) == 0;
            int type = 0;
            
            if (isLarge) type += 8;
        	if (isMossy) type += 2;
        	
            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);        
            

            if (par1World.isAirBlock(var7, var8, var9) && (!par1World.provider.hasNoSky || (var8 < 127)) && 
            	rock.canPlaceBlockAt(par1World, var7, var8, var9))
            {
            	par1World.setBlock(var7, var8, var9, rock.blockID, type, 2);
//            	if (var8 > 60 )
//            	{            		 
//            		if (isDesertBiome(biome))
//            		{
//            			if (currentBiome == BiomeGenBase.desertHills && var8 >= 70)
//            			{
//            				rock = SCDefs.rocksSandstone;  
//                			par1World.setBlock(var7, var8, var9, rock.blockID, type, 2);
//            			}
//            			else {
//            				rock = SCDefs.rocksSandstone;  
//                			par1World.setBlock(var7, var8, var9, rock.blockID, type, 2);
//            			}
//            			
//            		}   
//            		else if (isMountainBiome(biome) && var8 < 75)
//            		{
//            			rock = SCDefs.rocks;  
//            			par1World.setBlock(var7, var8, var9, rock.blockID, type, 2);
//            		}
//            	}
//            	else
//            	{
//            		if (var8 < 20) rock = SCDefs.rocksStrata2;
//            		else if (var8 < 40) rock = SCDefs.rocksStrata1;
//            		else rock = SCDefs.rocks;
//            		
//            		par1World.setBlock(var7, var8, var9, rock.blockID, type, 2);
//            	}               
            }
        }

        return true;
    }
}
