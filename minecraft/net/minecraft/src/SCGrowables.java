package net.minecraft.src;

public class SCGrowables {

	int inputItem;
	int[] validMetadata;
	int blockToStore;
	int outputItem;
	int growthStages;
	float growthChance;
	
	/**
	 * 
	 * @param inputItem the held item that get's inserted
	 * @param validMetadata the metadata of the held item that's valid to be inserted
	 * @param blockToStore the block that should actually be stored
	 * @param outputItem the mature block/item
	 * @param growthStages the total amount of growthStages (maximum 8)
	 * @param growthChance the growthChance
	 */
	public SCGrowables(int inputItem, int[] validMetadata, int blockToStore, int outputItem, int growthStages, float growthChance)
	{
		this.inputItem = inputItem;
		this.validMetadata = validMetadata;
		this.blockToStore = blockToStore;
		this.outputItem = outputItem;
		this.growthStages = growthStages;
		this.growthChance = growthChance;
	}

}
