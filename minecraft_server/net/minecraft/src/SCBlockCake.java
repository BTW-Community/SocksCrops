package net.minecraft.src;

import java.util.Random;

public class SCBlockCake extends SCBlockCakeBase {

	protected SCBlockCake(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return Item.cake.itemID;
	}
	
	@Override
	protected int getSliceItem() {
		return SCDefs.cakeSlice.itemID;
	}


}
