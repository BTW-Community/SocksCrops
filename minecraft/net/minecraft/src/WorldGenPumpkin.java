//SCADDON: Changed All
package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class WorldGenPumpkin extends WorldGenerator {
	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();

	public static boolean isBiomeValid(BiomeGenBase biome) {
		return validBiomeList.contains(biome);
	}

	public static void addBiomeToGenerator(BiomeGenBase biome) {
		validBiomeList.add(biome);
	}
	
	private int getVine(World world, int x, int z) {
		if ( CheckIfFresh(world, x, z) )
		{
			return SCDefs.pumpkinVineFlowering.blockID;
		}
		else return SCDefs.gourdVineDead.blockID;
	}

	int placedVinesCount = 0;

	public boolean generate(World world, Random rand, int x, int y, int z) {

		boolean bIsValidBiome = isBiomeValid(world.getBiomeGenForCoords(x, z));
		int iPlacedPumpkinCount = 0;

		boolean bIsFresh = CheckIfFresh(world, x, z);

		for (int var6 = 0; var6 < 64; ++var6) {
			int posX = x + rand.nextInt(8) - rand.nextInt(8);
			int posY = y + rand.nextInt(4) - rand.nextInt(4);
			int posZ = z + rand.nextInt(8) - rand.nextInt(8);

			if (canPlace(world, posX, posY, posZ)) {

				int iFacing = rand.nextInt(4); // must be done regardless of whether the block is placed to avoid disrupting the random number generator

				if (!bIsValidBiome)
					return false;
				
				if (iPlacedPumpkinCount < 8 )
				{
					int r = rand.nextInt(5);
	        		
					if ( bIsFresh )
	        		{
	        			switch (r) {
						case 0:
							world.setBlock(posX, posY, posZ, SCDefs.pumpkinOrange.blockID, iFacing + 12, 2);
							break;
						
						case 1:
							world.setBlock(posX, posY, posZ, SCDefs.pumpkinGreen.blockID, iFacing + 12, 2);
							break;
						
						case 2:
							world.setBlock(posX, posY, posZ, SCDefs.pumpkinYellow.blockID, iFacing + 12, 2);
							break;
						
						case 3:
							world.setBlock(posX, posY, posZ, SCDefs.pumpkinWhite.blockID, iFacing + 12, 2);
							break;

						default:
							world.setBlock(posX, posY, posZ, SCDefs.pumpkinOrange.blockID, iFacing + 12, 2);
							break;
						}
	        			
	        			
	        		}
	        		else world.setBlock(posX, posY, posZ, SCDefs.pumpkinCarvedDead.blockID, iFacing + (r * 4), 2);
	        		
	        		iPlacedPumpkinCount++;
					// grow fist vine
					growVineAdjacent(world, posX, posY, posZ, rand, Direction.rotateOpposite[iFacing]);

					// reset counter so other vines have a length too, not just the first try
					placedVinesCount = 0;
				}			

			}
		}

		return true;
	}

	private boolean canPlace(World world, int var7, int var8, int var9) {
		if (world.isAirBlock(var7, var8, var9) && world.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID) {
			return true;
		}

		else if (world.getBlockId(var7, var8, var9) == Block.tallGrass.blockID
				&& world.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID) {
			return true;
		}

		return false;
	}

	// Thanks to Hiracho for help with this method
	// Modified from what I'm using in SCBlockGourdVine
	protected void attemptToGrowVine(World world, int i, int j, int k, Random random, int dir) {
		int sideA = dir;
		int sideB = Direction.rotateLeft[sideA];
		int sideC = Direction.rotateOpposite[sideB];

		int sideFinal;
		float randomFloat = random.nextFloat();

		if (randomFloat < 0.50) {
			sideFinal = sideA;
		} else if (randomFloat < 0.50 + 0.25) {
			sideFinal = sideB;
		} else {
			sideFinal = sideC;
		}

		int offsetI = Direction.offsetX[sideFinal];
		int offsetK = Direction.offsetZ[sideFinal];

		int finalI = i + offsetI;
		int finalK = k + offsetK;

		placedVinesCount++;
		if (placedVinesCount < 3) {
			if (canPlace(world, finalI, j, finalK)) {
				world.setBlock(finalI, j, finalK, getVine(world, finalI, finalK), sideFinal + 12, 2);
				attemptToGrowVine(world, finalI, j, finalK, random, sideFinal);
			}

		} else {

			if (canPlace(world, finalI, j, finalK)) {
				//orld.setBlock(finalI, j, finalK, SCDefs.gourdStemDead.blockID, 15, 2);
			}
		}

	}

	private void growVineAdjacent(World world, int i, int j, int k, Random random, int targetDirection) {
		//int targetDirection = random.nextInt(4);

		int directionI = Direction.offsetX[targetDirection];
		int directionK = Direction.offsetZ[targetDirection];

		int finalI = i + directionI;
		int finalK = k + directionK;

		if (canPlace(world, finalI, j, finalK)) {
			world.setBlockAndMetadataWithNotify(finalI, j, finalK, getVine(world, finalI, finalK), targetDirection + 12);

			attemptToGrowVine(world, finalI, j, finalK, random, targetDirection);
		}

	}

	// FCMOD: Added
	private final static double m_dDistForFreshPumpkins = 2500D;
	private final static double m_dDistSquaredForFreshPumpkins = (m_dDistForFreshPumpkins * m_dDistForFreshPumpkins);

	public boolean CheckIfFresh(World world, int i, int k) {
		int iSpawnX = world.getWorldInfo().getSpawnX();
		int iSpawnZ = world.getWorldInfo().getSpawnZ();

		double dDeltaX = (double) (iSpawnX - i);
		double dDeltaZ = (double) (iSpawnZ - k);

		double dDistSqFromSpawn = dDeltaX * dDeltaX + dDeltaZ * dDeltaZ;

		return dDistSqFromSpawn > m_dDistSquaredForFreshPumpkins;
	}
	// END FCMOD
}
// --- Old WorldGenPumpkin --- //

//package net.minecraft.src;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class WorldGenPumpkin extends WorldGenerator
//{
//	private static ArrayList<BiomeGenBase> validBiomeList = new ArrayList();
//	
//	public static boolean isBiomeValid(BiomeGenBase biome) {
//		return validBiomeList.contains(biome);
//	}
//	
//	public static void addBiomeToGenerator(BiomeGenBase biome) {
//		validBiomeList.add(biome);
//	}
//	
//    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
//    {
//    	// FCMOD: Added
//        boolean bIsValidBiome = isBiomeValid(par1World.getBiomeGenForCoords(par3, par5));
//        int iPlacedPumpkinCount = 0;
//       
//        boolean bIsFresh = CheckIfFresh( par1World, par3, par5 );
//        // END FCMOD
//
//        for (int var6 = 0; var6 < 64; ++var6)
//        {
//            int var7 = par3 + par2Random.nextInt(8) - par2Random.nextInt(8);
//            int var8 = par4 + par2Random.nextInt(4) - par2Random.nextInt(4);
//            int var9 = par5 + par2Random.nextInt(8) - par2Random.nextInt(8);
//
//            if (par1World.isAirBlock(var7, var8, var9) && par1World.getBlockId(var7, var8 - 1, var9) == Block.grass.blockID && Block.pumpkin.canPlaceBlockAt(par1World, var7, var8, var9))
//            {
//            	// FCMOD: Changed
//            	/*
//                par1World.setBlock(var7, var8, var9, Block.pumpkin.blockID, par2Random.nextInt(4), 2);
//                */
//            	int iFacing = par2Random.nextInt( 4 ); // must be done regardless of whether the block is placed to avoid disrupting the random number generator
//            	
//            	if ( bIsValidBiome && iPlacedPumpkinCount < 3 )
//            	{
//            		if ( bIsFresh )
//            		{
//            			par1World.setBlock(var7, var8, var9, FCBetterThanWolves.fcBlockPumpkinFresh.blockID, iFacing, 2);
//            		}
//            		else
//            		{
//            			par1World.setBlock(var7, var8, var9, Block.pumpkin.blockID, iFacing, 2);
//            		}
//                    
//                    iPlacedPumpkinCount++;
//            	}
//            	// END FCMOD            		
//            }
//        }
//
//        return true;
//    }
//    
//	// FCMOD: Added
//    private final static double m_dDistForFreshPumpkins = 2500D;
//    private final static double m_dDistSquaredForFreshPumpkins = ( m_dDistForFreshPumpkins * m_dDistForFreshPumpkins );
//    
//    public boolean CheckIfFresh( World world, int i, int k )
//    {
//    	int iSpawnX = world.getWorldInfo().getSpawnX();
//    	int iSpawnZ = world.getWorldInfo().getSpawnZ();
//    	
//    	double dDeltaX = (double)( iSpawnX - i );
//    	double dDeltaZ = (double)( iSpawnZ - k );
//    	
//    	double dDistSqFromSpawn = dDeltaX * dDeltaX + dDeltaZ * dDeltaZ;
//    	
//    	return dDistSqFromSpawn > m_dDistSquaredForFreshPumpkins;
//    }    
//    // END FCMOD	
//}