package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenRocks {
	
	private int count;
	
    public SCWorldGenRocks(int count) {
    	this.count = count;
    }
	
	private static ArrayList<BiomeGenBase> validBiome = new ArrayList();
	private static ArrayList<BiomeGenBase> desertBiome = new ArrayList();
	
	public static boolean isValidBiome(BiomeGenBase biome) {
		return validBiome.contains(biome);
	}
	
	public static boolean isDesertBiome(BiomeGenBase biome) {
		return desertBiome.contains(biome);
	}

	static {
//		desertBiome.add(BiomeGenBase.desert);
//		desertBiome.add(BiomeGenBase.desertHills);
		desertBiome.add(BiomeGenBase.riverDesert);
		desertBiome.add(BiomeGenBase.beachDesert);
	}
	
	static {
		validBiome.add(BiomeGenBase.plains);
		validBiome.add(BiomeGenBase.extremeHills);
	}
	
	public boolean generate(World world, Random rand, int x, int y, int z)
    {
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
        boolean isValidBiome = isValidBiome(currentBiome);

        boolean isDesert = isDesertBiome(currentBiome);
        Block rock = SCDefs.rocks;
        
        for (int var6 = 0; var6 < count; ++var6)
        {           
            boolean isMossy = rand.nextInt(2) == 0;
            boolean isLarge = rand.nextInt(2) == 0;
            int type = 0;
        	
            int rockX = x + rand.nextInt(4) - rand.nextInt(4);
            int rockY = y + rand.nextInt(2) - rand.nextInt(2);
            int rockZ = z + rand.nextInt(4) - rand.nextInt(4);

            if (world.isAirBlock(rockX, rockY, rockZ) && rockY < 127)
            {                                
//                if ( !isValidBiome )
//                {
//                	// must occur after all random number generation to avoid messing up world gen
//                	continue;
//                }
                
                if (rock.canPlaceBlockAt(world, rockX, rockY, rockZ))
                {  
                	type = 0;
                	
                	if (isLarge) type += 8;
                	if (isMossy) type += 2;
                	
                	System.out.println("isLarge: " + isLarge);
                	System.out.println("isMossy: " + isMossy);
                	System.out.println("type: " + type);
                	
                	if (isDesert) 
                	{
                    	if (rockY > 60) 
                    	{
                			world.setBlock(rockX, rockY, rockZ, SCDefs.rocksSandstone.blockID, type, 2);
                			
                			System.out.println("desert: success at: " + rockX + " " + rockY + " " + rockZ);
                    	}
                	}
                	else
                	{
                    	if (rockY < 20) 
                    	{
                    		
                    		world.setBlock(rockX, rockY, rockZ, SCDefs.rocksStrata2.blockID, type, 2);
                    		System.out.println("strata2: success at: " + rockX + " " + rockY + " " + rockZ);
                    	}	
                    	else if (rockY < 40) 
                    	{
                    		
                    		world.setBlock(rockX, rockY, rockZ, SCDefs.rocksStrata2.blockID, type, 2);
                    		System.out.println("strata1: success at: " + rockX + " " + rockY + " " + rockZ);
                    	}
                    	
                    	else if (rockY < 60) 
                    	{
                    		
                    		world.setBlock(rockX, rockY, rockZ, SCDefs.rocks.blockID, type, 2);
                    		System.out.println("strata0: success at: " + rockX + " " + rockY + " " + rockZ);
                    	}
                	}

                	
                	
                }
            }
        }
        
        return true;
    }
}