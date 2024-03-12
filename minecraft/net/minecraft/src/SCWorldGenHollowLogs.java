package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCWorldGenHollowLogs {

    public SCWorldGenHollowLogs() {}
	
	private static ArrayList<Integer> validBiomeList = new ArrayList();
	
	public static boolean isValidBiome(int biomeID) {
		return validBiomeList.contains(biomeID);
	}
	
	private int woodType;

    public SCWorldGenHollowLogs(int type, ArrayList<Integer> validBiomeList)
    {
    	this.woodType = type;
        this.validBiomeList = validBiomeList;
    }
	
	
	public boolean generate(World world, Random rand, int x, int y, int z)
    { 
		
    	BiomeGenBase currentBiome = world.getBiomeGenForCoords( x, z );
    	
        boolean isValidBiome = isValidBiome(currentBiome.biomeID);
        
        int plantX = x + rand.nextInt(4) - rand.nextInt(4);
        int plantY = y + rand.nextInt(4) - rand.nextInt(4);
        int plantZ = z + rand.nextInt(4) - rand.nextInt(4);
        
        int randomDir = rand.nextInt(2);
        int dir = getRandomDirection(randomDir);
        
        int oakOrBirch = rand.nextInt(3);
        boolean placeMoss = true;
        
        if ( !isValidBiome )
        {
        	// must occur after all random number generation to avoid messing up world gen
        	
        	return false;
        }
        Block hollowLog = SCDefs.hollowLog;

        //int type = setType(currentBiome);

        if ( hasEnoughSpaceAround(world, plantX, plantY, plantZ) && plantY > 60)
        {   
        	if ( woodType == oak)
            {
        		if (oakOrBirch > 0)
        		{
        			woodType = oak;
        		}
        		else woodType = birch;
        		
        		world.setBlock(plantX, plantY, plantZ, hollowLog.blockID, dir + woodType, 2);
        		
        		placeLogs(world, plantX, plantY, plantZ, hollowLog, dir, woodType, placeMoss);
            }
        	else 
        	{
        		world.setBlock(plantX, plantY, plantZ, hollowLog.blockID, dir + woodType, 2);
        		placeLogs(world, plantX, plantY, plantZ, hollowLog, dir, woodType, placeMoss);
        	}      	
        	
        	
        	
			if (world.isAirBlock(plantX + 1, plantY, plantZ))
			{
				world.setBlock(plantX + 1, plantY, plantZ, SCDefs.mossCarpet.blockID, 0, 2);
			}        	
        }

        return true;
    }

    private void placeLogs(World world, int plantX, int plantY, int plantZ, Block hollowLog, int dir, int type, boolean placeMoss)
    {
		if (dir == 4)
		{
			if (!world.isAirBlock(plantX + 1, plantY - 1, plantZ) )
			{
				world.setBlock(plantX + 1, plantY, plantZ, hollowLog.blockID, dir + type, 2);
				
				if (world.isAirBlock(plantX + 1, plantY + 1, plantZ) && placeMoss )
				{
					world.setBlock(plantX + 1, plantY + 1, plantZ, SCDefs.mossCarpet.blockID, 0, 2);
				}
			}
			
			if (!world.isAirBlock(plantX - 1, plantY - 1, plantZ) )
			{
				world.setBlock(plantX - 1, plantY, plantZ, hollowLog.blockID, dir + type, 2);
				
				if (world.isAirBlock(plantX - 1, plantY + 1, plantZ) && placeMoss)
				{
					world.setBlock(plantX - 1, plantY + 1, plantZ, SCDefs.mossCarpet.blockID, 0, 2);
				}
			}
			
		}
		else
		{
			if (!world.isAirBlock(plantX, plantY - 1, plantZ + 1) )
			{
				world.setBlock(plantX, plantY, plantZ  + 1, hollowLog.blockID, dir + type, 2);
				
				if (world.isAirBlock(plantX, plantY + 1, plantZ  + 1) && placeMoss)
				{
					world.setBlock(plantX, plantY+1, plantZ  + 1, SCDefs.mossCarpet.blockID, 0, 2);
				}
			}
			
			if (!world.isAirBlock(plantX, plantY - 1, plantZ  - 1) )
			{
				world.setBlock(plantX, plantY, plantZ - 1, hollowLog.blockID, dir + type, 2);
				
				if (world.isAirBlock(plantX, plantY + 1, plantZ  - 1) && placeMoss)
				{
					world.setBlock(plantX, plantY + 1, plantZ  - 1, SCDefs.mossCarpet.blockID, 0, 2);
				}
			}
		}
		
		String string = "ERROR";
		
		if (type == oak) string = "oak"; 
		if (type == birch) string = "birch"; 
		if (type == spruce) string = "spruce"; 
		if (type == jungle) string = "jungle"; 
		
		// System.out.println(string + " Hollow Logs Placed at: " + plantX + " , " + plantY + " , " + plantZ);
		
	}

	public static int oak = 0;
	public static int spruce = 1;
	public static int birch = 2;
	public static int jungle = 3;

	private boolean hasEnoughSpaceAround(World world, int x, int y, int z)
	{
		
		for (int i = -1; i <= 1; i++) {
			for (int k = -1; k <= 1; k++) {
				for (int j = 0; j <= 1; j++) {
					if (!canPlace(world, x+i, y+j, z+k))
					{
						return false;
					}
				}
			}
		}
		
		int blockBelow = world.getBlockId(x, y-1, z);
		
		if (blockBelow == Block.grass.blockID || blockBelow == Block.sand.blockID)
		{
			//System.out.println("Enough Space");
			return true;
		}
		else return false;
		
	}

	private int getRandomDirection(int randomDir) {
		if (randomDir == 0)
		{
			return 4;
		}
		else return 8;
	}
	
	private boolean canPlace(World world, int plantX, int plantY, int plantZ)
	{
		int blockID = world.getBlockId(plantX, plantY, plantZ);

		
		return world.isAirBlock(plantX, plantY, plantZ) || Block.blocksList[blockID].IsReplaceableVegetation(world, plantX, plantY, plantZ);
	}
	
}