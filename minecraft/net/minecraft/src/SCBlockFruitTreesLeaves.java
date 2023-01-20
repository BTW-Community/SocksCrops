package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesLeaves extends SCBlockFruitTreesLeavesBase {

	protected SCBlockFruitTreesLeaves(int par1) {
		super(par1);
        setUnlocalizedName( "SCBlockFruitLeaves" );        
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
