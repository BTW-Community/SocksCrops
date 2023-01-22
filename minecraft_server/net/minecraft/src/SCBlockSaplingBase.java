package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockSaplingBase extends FCBlockSaplingLegacy {

	protected SCBlockSaplingBase(int iBlockID, String name) {
		super(iBlockID);
		setUnlocalizedName(name);
	}
	
	protected abstract float getGrowthChance();
	
	public void attemptToGrow(World world, int x, int y, int z, Random rand) {
		float growthChance = getGrowthChance();

		int blockIDBelow = world.getBlockId(x, y - 1, z);
		Block blockBelow = Block.blocksList[blockIDBelow];

		if (blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
			growthChance *= blockBelow.GetPlantGrowthOnMultiplier(world, x, y - 1, z, this);
		}

		int metadata = world.getBlockMetadata(x, y, z);
		int growthStage = (metadata & (~3)) >> 2;

		if (growthStage == 3) {
			growthChance /= 2;
		}

		if (rand.nextFloat() <= growthChance) {
			if (growthStage < 3) {
				growthStage++;
				metadata = (metadata & 3) | (growthStage << 2);

				world.setBlockMetadataWithNotify(x, y, z, metadata);

				if (growthStage == 3) {
					blockBelow.NotifyOfFullStagePlantGrowthOn(world, x, y - 1, z, this);
				}
			}
			else {
//				if (!(blockBelow instanceof FCBlockPlanterBase) || blockBelow.GetIsFertilizedForPlantGrowth(world, x, y - 1, z)) {
//					growTree(world, x, y, z, rand);
//				}
				
				growTree(world, x, y, z, rand);
			}
		}
	}
	protected abstract boolean generateTree(World world, Random random, int x, int y, int z, int treeType);
	protected abstract float getBigTreeGrowthChance();
	
	protected abstract boolean grows2x2Tree(int treeType);
	protected boolean generateBigTree(World world, Random random, int x, int y, int z, int treeType) {
		boolean success = false;
		
		if (treeType == 0)
		{
			
			FCUtilsGenBigTree bigTree = new FCUtilsGenBigTree(true);

			success = bigTree.generate(world, random, x, y, z);
		}
		
		return success;	
	}
	
	protected boolean generateTrees(World world, Random random, int x, int y, int z, int xOffset, int zOffset, int treeType)
	{
		boolean success = false;
		

		
		return success;
	}
	
	

	

	@Override
	public void growTree(World world, int x, int y, int z, Random random) {
		int treeType = world.getBlockMetadata(x, y, z) & 3;
		boolean success = false;

		int xOffset = 0;
		int zOffset = 0;

		boolean generatedHuge = false;

		boolean planter = Block.blocksList[world.getBlockId(x, y - 1, z)] instanceof FCBlockPlanterBase;

		if (!grows2x2Tree(treeType)) {
			world.setBlock(x, y, z, 0);
		}

		//Grow Trees
		if (grows2x2Tree(treeType))
		{
			success = generate2x2Tree(world, random, x, y, z, xOffset, zOffset, treeType);
			generatedHuge = true;
		}
		else
		{
			if (random.nextFloat() <= getBigTreeGrowthChance())
			{
				success = generateBigTree(world, random, x, y, z, treeType);
			}
			else {
				System.out.println("trying to grow tree");
				success = generateTree(world, random, x, y, z, treeType);
			}
		}

		if (!success) {
			// restore saplings at full growth

			int saplingMetadata = treeType + (3 << 2);

			if (generatedHuge) {
				// replace all the saplings if a huge tree was grown

				world.setBlockAndMetadata(x + xOffset, y, z + zOffset, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset + 1, y, z + zOffset, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset, y, z + zOffset + 1, blockID, saplingMetadata);
				world.setBlockAndMetadata(x + xOffset + 1, y, z + zOffset + 1, blockID, saplingMetadata);
			}
			else {
				world.setBlockAndMetadata(x, y, z, blockID, saplingMetadata);
			}
		}
		else if (planter) {
			world.setBlockMetadata(x, y, z, treeType);

			//Block break sfx
			world.playAuxSFX(2001, x, y - 1, z, FCBetterThanWolves.fcBlockPlanterSoil.blockID);

			world.setBlockAndMetadata(x, y - 1, z, Block.wood.blockID, treeType | 12);
		}
	}

	private boolean generate2x2Tree(World world, Random random, int x, int y, int z, int xOffset, int zOffset, int treeType) {
		boolean success = false;
		boolean generatedHuge = false;
		
		do {
			if (xOffset < -1) {
				break;
			}

			zOffset = 0;

			do {
				if (zOffset < -1) {
					break;
				}

				if (isSameSapling(world, x + xOffset, y, z + zOffset, 3) && 
						isSameSapling(world, x + xOffset + 1, y, z + zOffset, 3) && 
						isSameSapling(world, x + xOffset, y, z + zOffset + 1, 3) && 
						isSameSapling(world, x + xOffset + 1, y, z + zOffset + 1, 3))
				{
					if (GetSaplingGrowthStage(world, x + xOffset, y, z + zOffset) == 3 && 
							GetSaplingGrowthStage(world, x + xOffset + 1, y, z + zOffset) == 3 && 
							GetSaplingGrowthStage(world, x + xOffset, y, z + zOffset + 1) == 3 && 
							GetSaplingGrowthStage(world, x + xOffset + 1, y, z + zOffset + 1) == 3)
					{
						// clear all 4 saplings that make up the huge tree

						world.setBlock(x + xOffset, y, z + zOffset, 0);
						world.setBlock(x + xOffset + 1, y, z + zOffset, 0);
						world.setBlock(x + xOffset, y, z + zOffset + 1, 0);
						world.setBlock(x + xOffset + 1, y, z + zOffset + 1, 0);

						FCUtilsGenHugeTree hugeTree = new FCUtilsGenHugeTree(true, 10 + random.nextInt(20), 3, 3);
						success = hugeTree.generate(world, random, x + xOffset, y, z + zOffset);

						generatedHuge = true;
					}
					else {
						// we have 4 saplings, but they aren't all fully grown.  Bomb out entirely
						return false;
					}
				}

				zOffset--;
			}
			while (true);

			if (generatedHuge) {
				break;
			}

			xOffset--;
		}
		while (true);

		if (!generatedHuge) {
			xOffset = zOffset = 0;
			world.setBlock(x, y, z, 0);

			success = generateTree(world, random, x, y, z, treeType); //FCUtilsTrees.GenerateTrees(world, random, x, y, z, 4 + random.nextInt(7), 3, 3, false);
		}
		
		return success;
	}	
}
