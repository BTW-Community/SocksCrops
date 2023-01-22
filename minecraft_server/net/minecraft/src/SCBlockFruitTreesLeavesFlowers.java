package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesLeavesFlowers extends SCBlockFruitTreesLeavesBase {

    public static final String[] flowerTextures = new String[] {
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[APPLE],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[CHERRY],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[LEMON],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[OLIVE]
    		};

	
	protected SCBlockFruitTreesLeavesFlowers(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFruitLeavesFlower");
	}

	@Override
	public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
		return 0;
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}

}
