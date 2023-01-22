package net.minecraft.src;

import java.util.List;

public class SCBlockFruitTreesLeavesFlowering extends SCBlockFruitTreesLeavesGrowing {

	private Item fruitDropped;
	private int type;
	
	public static final String[] flowerTextures = new String[] {
			"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[APPLE],
			"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[CHERRY],
			"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[LEMON],
			"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[OLIVE]
			};
	
	protected SCBlockFruitTreesLeavesFlowering(int blockID, Item fruitDropped, int type)
	{
		super(blockID);
		
		setUnlocalizedName( "SCBlockFruitTreesLeavesFlowering" );
		
		this.fruitDropped = fruitDropped;
		this.type = type;
	}
	
	@Override
	protected float getFruitGrowChance() {
		
		return 0.33F;
	}
	
	@Override
	protected float getGrowthChance() {
		return 1/16F;
	}

	@Override
	protected float getFruitDropChance() {
		return 0.1F;
	}

	@Override
	protected int fruitDropped()
	{
		return fruitDropped.itemID;
	}
	
	@Override
	protected String getFlowerTexture()
	{			
		return flowerTextures[type];
	}

	@Override
	protected String getLeavesTexture() {
		return leafTextures[type];
	}

	@Override
	protected String getFruitTextureName() {
		return LEAF_TYPES[type];
	}

	//Alternative fruit colors
	@Override
	protected boolean hasAltTexture() {
		return type == OLIVE;
	}
	
	//Fruit sizes
	private int[] apple = { 2, 4, 6 };
	private int[] cherry = { 1, 2, 3 };
	private int[] lemon = { 2, 3, 5 };
	private int[] olive = { 1, 2, 2 };
 	
	private int[] maxShiftFruits = { 4, 2, 5, 2};
	
	@Override
	protected int getFruitSize(int growthLevel) {
		switch (type) {
		default:
		case APPLE:
			return apple[growthLevel];
		case CHERRY:
			return cherry[growthLevel];
		case LEMON:
			return lemon[growthLevel];
		case OLIVE:
			return olive[growthLevel];
		}
	}
	
	@Override
	protected int getMaxShift() {
		return maxShiftFruits[type];
	}

	@Override
	protected boolean getGrowInPairs() {
		switch (type) {
		default:
		case APPLE:
			return false;
		case CHERRY:
			return true;
		case LEMON:
			return false;
		case OLIVE:
			return true;
		}
	}

	@Override
	protected int getNumberOfFruits() {
		switch (type) {
		default:
		case APPLE:
			return 1;
		case CHERRY:
			return 2;
		case LEMON:
			return 1;
		case OLIVE:
			return 2;
		}
	}


	@Override
	protected int getFruitType() {
		// TODO Auto-generated method stub
		return type;

	}

}
