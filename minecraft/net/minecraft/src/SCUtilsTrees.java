package net.minecraft.src;

import java.util.Random;

public class SCUtilsTrees {

		/** The minimum height of a generated tree. */
	private static int minTreeHeight = 6;

	/** True if this tree should grow Vines. */
	private static boolean vinesGrow = false;

	/** The metadata value of the wood to use in tree generation. */
	private static int metaWood = 3;

	/** The metadata value of the leaves to use in tree generation. */
	private static int metaLeaves = 3;
	
	private static boolean spawnCocoa = true;

	public static boolean generateCoconutTree(World world, Random rand, int x, int y, int z) {
		if (!Block.blocksList[world.getBlockId(x, y - 1, z)].CanSaplingsGrowOnBlock(world, x, y, z))
			return false;

		int treeHeight = minTreeHeight + rand.nextInt(3);
		//How many times the trunk shifts to the side, controls the angle of the trunk
		int trunkShifts = 1 + rand.nextInt(2);
		//Which direction the trunk leans
		int leanDirection = rand.nextInt(4);
		int leanX = 0;
		int leanZ = 0;
		int[] trunkShiftHeights;

		switch (leanDirection) {
		//North
		case 0:
			leanX = 0;
			leanZ = -1;
			break;
			//West
		case 1:
			leanX = -1;
			leanZ = 0;
			break;
			//South
		case 2:
			leanX = 0;
			leanZ = 1;
			break;
			//East
		case 3:
			leanX = 1;
			leanZ = 0;
			break;
		default:
		}
		
		if (!checkForValidTreeSpawn(world, x, y, z, treeHeight, trunkShifts, leanX, leanZ))
			return false;

		//Gets the heights at which to split the trunk
		if (trunkShifts == 1) {
			trunkShiftHeights = new int[2];

			if (treeHeight == 6) {
				trunkShiftHeights[0] = 3;
				trunkShiftHeights[1] = 3;
			}
			else if (treeHeight == 7) {
				trunkShiftHeights[0] = 4;
				trunkShiftHeights[1] = 3;
			}
			else if (treeHeight == 8) {
				trunkShiftHeights[0] = 4;
				trunkShiftHeights[1] = 4;
			}
		}
		else {
			trunkShiftHeights = new int[3];

			if (treeHeight == 6) {
				trunkShiftHeights[0] = 2;
				trunkShiftHeights[1] = 2;
				trunkShiftHeights[2] = 2;
			}
			else if (treeHeight == 7) {
				trunkShiftHeights[0] = 3;
				trunkShiftHeights[1] = 2;
				trunkShiftHeights[2] = 2;
			}
			else if (treeHeight == 8) {
				trunkShiftHeights[0] = 3;
				trunkShiftHeights[1] = 3;
				trunkShiftHeights[2] = 2;
			}
		}

		int height = y;

		for (int i = 0; i < trunkShifts + 1; i++) {
			for (int j = 0; j < trunkShiftHeights[i]; j++) {
				world.setBlockAndMetadata(x + leanX * i, height, z + leanZ * i, Block.wood.blockID, metaWood);

				//Coconut, generate only at the highest log under the leaves
				if (spawnCocoa && height - y == treeHeight - 2 ) {
					
					//try 4 times to spawn
					for (int k = 0; k < 4; k++)
					{
						int facing = rand.nextInt(4) + 2;
						
						FCUtilsBlockPos pos = new FCUtilsBlockPos(x + leanX * i, height, z + leanZ * i);
						pos.AddFacingAsOffset(facing);
						
						int meta = 0;
						
						if (facing - 2 == 0)
							meta = 2;
						
						if (facing - 2 == 1) {
							meta = 0;
						}
						else if (facing - 2 == 2) {
							meta = 1;
						}
						else if (facing - 2 == 3) {
							meta = 3;
						}
						
//						int randomGrowthStage = rand.nextInt(4) * 4; //results in 0, 4, 8, 12
//						meta = meta + randomGrowthStage;
						
						world.setBlockAndMetadata(pos.i, pos.j, pos.k, SCDefs.coconutPlant.blockID, meta);						
						
					}
					

				}
				
				height++;
			}
			

		}
		


		//Places a stump
		world.setBlockMetadataWithClient(x, y, z, metaWood | 12);

		generatePalmLeaves(world, x + leanX * trunkShifts, height, z + leanZ * trunkShifts);

		return true;
	}

	public static void generatePalmLeaves(World world, int x, int y, int z) {
		//Central leaves
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				world.setBlockAndMetadata(x + i, y, z+ j, Block.leaves.blockID, metaLeaves);
			}
		}
		
		//Lower leaves around log
		world.setBlockAndMetadata(x + 1, y - 1, z, Block.leaves.blockID, metaLeaves);
		world.setBlockAndMetadata(x, y - 1, z + 1, Block.leaves.blockID, metaLeaves);
		world.setBlockAndMetadata(x - 1, y - 1, z, Block.leaves.blockID, metaLeaves);
		world.setBlockAndMetadata(x, y - 1, z - 1, Block.leaves.blockID, metaLeaves);
		
		//Hanging leaves - uses metadata to not despawn
		for (int i = 2; i < 5; i++) {
			int j = 0;
			int k = 0;
			
			if (i == 3)
				k = 1;
			if (i == 4)
				j = 1;
			
			//Sides
			world.setBlockAndMetadata(x + i, y - j, z, Block.leaves.blockID, metaLeaves | 4);
			world.setBlockAndMetadata(x, y - j, z + i, Block.leaves.blockID, metaLeaves | 4);
			world.setBlockAndMetadata(x - i, y - j, z, Block.leaves.blockID, metaLeaves | 4);
			world.setBlockAndMetadata(x, y - j, z - i, Block.leaves.blockID, metaLeaves | 4);
			
			//Corners
			if (i < 4) {
				world.setBlockAndMetadata(x + i, y - k, z + i, Block.leaves.blockID, metaLeaves | 4);
				world.setBlockAndMetadata(x - i, y - k, z + i, Block.leaves.blockID, metaLeaves | 4);
				world.setBlockAndMetadata(x + i, y - k, z - i, Block.leaves.blockID, metaLeaves | 4);
				world.setBlockAndMetadata(x - i, y - k, z - i, Block.leaves.blockID, metaLeaves | 4);
			}
		}
	}
	
	//Checks to make sure the space is clear for a tree to spawn
	private static boolean checkForValidTreeSpawn(World world, int x, int y, int z, int treeHeight, int trunkShifts, int leanX, int leanZ) {
		leanX *= trunkShifts;
		leanZ *= trunkShifts;
		
		//Checks trunk
		
		
		//Checks leaves
		for (int i = -4 + leanX; i < 5 + leanZ; i++) {
			for (int j = -1; j < 1; j++) {
				for (int k = -4 + leanZ; k < 5 + leanZ; k++) {
					if (!world.isAirBlock(i + x, j + y + treeHeight, k + z)) {
						return false;
					}
				}
			}
		}
		
		return true;
	}

	public static boolean generateStandardTree(World world, Random rand, int x, int y, int z, int logID, int logMetadata, int stumpID, int stumpMetadata, int leafID,
            int leafMetadata, int baseHeight)
    {
        int treeHeight = rand.nextInt(3) + baseHeight;
        boolean shouldGrow = true;
        
        if (y >= 1 && y + treeHeight + 1 <= 256) {
            for (int j = y; j <= y + 1 + treeHeight; ++j) {
                byte widthForCheck = 1;
                
                if (j == y) {
                    widthForCheck = 0;
                }
                
                if (j >= y + 1 + treeHeight - 2) {
                    widthForCheck = 2;
                }
                
                for (int i = x - widthForCheck; i <= x + widthForCheck && shouldGrow; ++i) {
                    for (int k = z - widthForCheck; k <= z + widthForCheck && shouldGrow; ++k) {
                        if (j >= 0 && j < 256) {
                            int blockID = world.getBlockId(i, j, k);
                            
                            if (!world.isAirBlock(i, j, k) && blockID != logID && blockID != leafID) {
                                shouldGrow = false;
                            }
                        }
                        else {
                            shouldGrow = false;
                        }
                    }
                }
            }
            
            if (!shouldGrow) {
                return false;
            }
            else {
                int blockIDBelow = world.getBlockId(x, y - 1, z);
                
                if (FCUtilsTrees.CanSaplingGrowOnBlock(world, x, y - 1, z) && y < 256 - treeHeight - 1) {
                    if (blockIDBelow == Block.grass.blockID) {
                        world.setBlockWithNotify(x, y - 1, z, Block.dirt.blockID);
                    }
                    
                    byte canopyHeight = 3;
                    
                    for (int j = y - canopyHeight + treeHeight; j <= y + treeHeight; ++j) {
                        int offsetY = j - (y + treeHeight);
                        int leafSizeXZ = 1 - offsetY / 2;
                        
                        for (int i = x - leafSizeXZ; i <= x + leafSizeXZ; ++i) {
                            int offsetX = i - x;
                            
                            for (int k = z - leafSizeXZ; k <= z + leafSizeXZ; ++k) {
                                int offsetZ = k - z;
                                
                                if ((Math.abs(offsetX) != leafSizeXZ || Math.abs(offsetZ) != leafSizeXZ || rand.nextInt(2) != 0 && offsetY != 0) && !Block.opaqueCubeLookup[world.getBlockId(i, j, k)]) {
                                    world.setBlockAndMetadataWithNotify(i, j, k, leafID, leafMetadata);
                                }
                            }
                        }
                    }
                    
                    for (int j = 0; j < treeHeight; ++j) {
                        int blockID = world.getBlockId(x, y + j, z);
                        
                        if (world.isAirBlock(x, y + j, z) || blockID == leafID) {
                            world.setBlockAndMetadataWithNotify(x, y + j, z, logID, logMetadata);
                        }
                    }
                    
                    if (treeHeight > 2) {
                        int blockID = world.getBlockId(x, y, z);
                        
                        if (blockID == logID) {
                            int metadata = world.getBlockMetadata(x, y, z);
                            
                            if (metadata == logMetadata) {
                                world.setBlockAndMetadata(x, y, z, stumpID, stumpMetadata);
                            }
                        }
                    }
                    
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }
	
	public static boolean generateFruitTree(World world, Random rand, int x, int y, int z, int logID, int logMetadata, int stumpID, int stumpMetadata, int leafID,
            int leafMetadata, int baseHeight, float flowerChance)
    {
        int treeHeight = rand.nextInt(3) + baseHeight;
        boolean shouldGrow = true;
        
        if (y >= 1 && y + treeHeight + 1 <= 256) {
            for (int j = y; j <= y + 1 + treeHeight; ++j) {
                byte widthForCheck = 1;
                
                if (j == y) {
                    widthForCheck = 0;
                }
                
                if (j >= y + 1 + treeHeight - 2) {
                    widthForCheck = 2;
                }
                
                for (int i = x - widthForCheck; i <= x + widthForCheck && shouldGrow; ++i) {
                    for (int k = z - widthForCheck; k <= z + widthForCheck && shouldGrow; ++k) {
                        if (j >= 0 && j < 256) {
                            int blockID = world.getBlockId(i, j, k);
                            
                            if (!world.isAirBlock(i, j, k) && !(Block.blocksList[blockID] instanceof SCBlockFruitTreesLogBase) && !(Block.blocksList[blockID] instanceof SCBlockFruitTreesLeavesBase)) {
                                shouldGrow = false;
                            }
                        }
                        else {
                            shouldGrow = false;
                        }
                    }
                }
            }
            
            if (!shouldGrow) {
                return false;
            }
            else {
                int blockIDBelow = world.getBlockId(x, y - 1, z);
                
                boolean var7 = true;
                
                if (FCUtilsTrees.CanSaplingGrowOnBlock(world, x, y - 1, z) && y < 256 - treeHeight - 1) {
//                    if (blockIDBelow == Block.grass.blockID) {
//                        world.setBlockWithNotify(x, y - 1, z, Block.dirt.blockID);
//                    }
                    
                    byte canopyHeight = 3;
                    
                    for (int j = y - canopyHeight + treeHeight; j <= y + treeHeight; ++j) {
                        int offsetY = j - (y + treeHeight);
                        int leafSizeXZ = 1 - offsetY / 2;
                        
                        for (int i = x - leafSizeXZ; i <= x + leafSizeXZ; ++i) {
                            int offsetX = i - x;
                            
                            for (int k = z - leafSizeXZ; k <= z + leafSizeXZ; ++k) {
                                int offsetZ = k - z;
                                
                                if ((Math.abs(offsetX) != leafSizeXZ || Math.abs(offsetZ) != leafSizeXZ || rand.nextInt(2) != 0 && offsetY != 0) && !Block.opaqueCubeLookup[world.getBlockId(i, j, k)]) {

                                	if(rand.nextFloat() <= flowerChance)
									{
                                		world.setBlockAndMetadataWithNotify(i, j, k, leafID, leafMetadata);
									}
                                	else world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.fruitLeaves.blockID, logMetadata);
                                }
                            }
                        }
                    }
                    
                    
                    for (int j = treeHeight - 3; j < treeHeight - 1; ++j) {
                        int blockMinX = world.getBlockId(x - 1, y + j, z);
                        int blockMaxX = world.getBlockId(x + 1, y + j, z);
                        int blockMinZ = world.getBlockId(x, y + j, z - 1);
                        int blockMaxZ = world.getBlockId(x, y + j, z + 1);
                        
                        if (blockMinX == 0 || (Block.blocksList[blockMinX] instanceof SCBlockFruitTreesLeavesBase ) )
                        {
                        	if (logMetadata == 0)
                        	{
                        		world.setBlockAndMetadataWithNotify(x - 1, y + j, z, SCDefs.fruitBranch.blockID, 4);
                        	}
                        	else if (logMetadata == 1)
                        	{
                        		world.setBlockAndMetadataWithNotify(x - 1, y + j, z, SCDefs.fruitBranch.blockID, 5);
                        	}
                        	else if (logMetadata == 2)
                        	{
                        		world.setBlockAndMetadataWithNotify(x - 1, y + j, z, SCDefs.fruitBranch.blockID, 6);
                        	}
                        	else if (logMetadata == 3)
                        	{
                        		world.setBlockAndMetadataWithNotify(x - 1, y + j, z, SCDefs.fruitBranch.blockID, 7);
                        	}
                        	
                        }
                        
                        if (blockMaxX == 0 || (Block.blocksList[blockMaxX] instanceof SCBlockFruitTreesLeavesBase ) )
                        {
                        	if (logMetadata == 0)
                        	{
                        		world.setBlockAndMetadataWithNotify(x + 1, y + j, z, SCDefs.fruitBranch.blockID, 4);
                        	}
                        	else if (logMetadata == 1)
                        	{
                        		world.setBlockAndMetadataWithNotify(x + 1, y + j, z, SCDefs.fruitBranch.blockID, 5);
                        	}
                        	else if (logMetadata == 2)
                        	{
                        		world.setBlockAndMetadataWithNotify(x + 1, y + j, z, SCDefs.fruitBranch.blockID, 6);
                        	}
                        	else if (logMetadata == 3)
                        	{
                        		world.setBlockAndMetadataWithNotify(x + 1, y + j, z, SCDefs.fruitBranch.blockID, 7);
                        	}
                        }
                        
                        if (blockMinZ == 0 || (Block.blocksList[blockMinZ] instanceof SCBlockFruitTreesLeavesBase ) )
                        {
                        	if (logMetadata == 0)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z - 1, SCDefs.fruitBranch.blockID, 8);
                        	}
                        	else if (logMetadata == 1)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z - 1, SCDefs.fruitBranch.blockID, 9);
                        	}
                        	else if (logMetadata == 2)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z - 1, SCDefs.fruitBranch.blockID, 10);
                        	}
                        	else if (logMetadata == 3)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z - 1, SCDefs.fruitBranch.blockID,11);
                        	}
                        }
                        
                        if (blockMaxZ == 0 || (Block.blocksList[blockMaxZ] instanceof SCBlockFruitTreesLeavesBase ) )
                        {
                        	if (logMetadata == 0)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z + 1, SCDefs.fruitBranch.blockID, 8);
                        	}
                        	else if (logMetadata == 1)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z + 1, SCDefs.fruitBranch.blockID, 9);
                        	}
                        	else if (logMetadata == 2)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z + 1, SCDefs.fruitBranch.blockID, 10);
                        	}
                        	else if (logMetadata == 3)
                        	{
                        		world.setBlockAndMetadataWithNotify(x, y + j, z + 1, SCDefs.fruitBranch.blockID,11);
                        	}
                        }
                    }
                    
                    for (int j = 0; j < treeHeight; ++j) {
                        int blockID = world.getBlockId(x, y + j, z);
                        
                        if (world.isAirBlock(x, y + j, z) || (Block.blocksList[blockID] instanceof SCBlockFruitTreesLeavesBase ) ) {
                            world.setBlockAndMetadataWithNotify(x, y + j, z, logID, logMetadata);
                        }
                    }
                    
                    if (treeHeight > 2) {
                        int blockID = world.getBlockId(x, y, z);
                        
                        if (blockID == logID) {
                            int metadata = world.getBlockMetadata(x, y, z);
                            
                            if (metadata == logMetadata) {
                                world.setBlockAndMetadata(x, y, z, stumpID, stumpMetadata);
                            }
                        }
                    }
                    
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }
}