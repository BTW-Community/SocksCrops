package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenSideShrooms extends WorldGenerator
{
	
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
	
	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}
	
	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
	static {
		SCWorldGenSideShrooms.addBiomeToGenerator(BiomeGenBase.forest);
		SCWorldGenSideShrooms.addBiomeToGenerator(BiomeGenBase.forestHills);
		
		addBiomeToGenerator(BiomeGenBase.taiga);
		addBiomeToGenerator(BiomeGenBase.taigaHills);
	}
	
    public boolean generate(World par1World, Random par2Random, int x, int y, int z)
    {
    	
    	BiomeGenBase currentBiome = par1World.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isBiomeValid(currentBiome);
    	
        for (int var6 = 0; var6 < 4; ++var6)
        {
            int xPos = x; // + par2Random.nextInt(4) - par2Random.nextInt(4);
            int yPos = y + par2Random.nextInt(4) - par2Random.nextInt(4);
            int zPos = z; // + par2Random.nextInt(4) - par2Random.nextInt(4);
            
            if (par1World.getBlockId(xPos, yPos, zPos) == Block.wood.blockID)
            {
            	int dir = par2Random.nextInt(4);
            	
            	int type = 0;
            	int randType = par2Random.nextInt(4);
            	
            	if (randType == 0)
            		type = 0;
            	else if (randType == 1)
            		type = 4;
            	else if (randType == 2)
            		type = 8;
            	else 
            		type = 12;
            	
                if ( !isValidBiome )
                {
                	// must occur after all random number generation to avoid messing up world gen
                	
                	continue;
                }
                
                //System.out.println("Dir : " + dir);
                
                if ( dir == 0 && par1World.getBlockId(xPos, yPos, zPos - 1) == 0)
                {
                	par1World.setBlockAndMetadata(xPos, yPos, zPos - 1, SCDefs.sideShroom.blockID, 0 + type);
                	
                	System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( dir == 2 && par1World.getBlockId(xPos, yPos, zPos + 1) == 0)
                {
                	par1World.setBlockAndMetadata(xPos, yPos, zPos + 1, SCDefs.sideShroom.blockID, 2 + type);
                	System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( dir == 1 && par1World.getBlockId(xPos + 1, yPos, zPos) == 0)
                {
                	par1World.setBlockAndMetadata(xPos + 1,yPos, zPos, SCDefs.sideShroom.blockID, 1 + type);
                	System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
                else if ( dir == 3 && par1World.getBlockId(xPos - 1, yPos, zPos) == 0)
                {
                	par1World.setBlockAndMetadata(xPos - 1,yPos, zPos, SCDefs.sideShroom.blockID, 3 + type);
                	System.out.println("Success at: x = " + xPos + " y = " + yPos + " z = " + zPos);
                }
            }
        }

        return true;
    }
}
