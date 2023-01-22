package net.minecraft.src;

import java.util.Random;

public class SCBlockCakeCarrot extends SCBlockCakeBase {

	protected SCBlockCakeCarrot(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return SCDefs.carrotCakeItem.itemID;
	}

	@Override
	protected int getSliceItem() {
		return SCDefs.carrotCakeSlice.itemID;
	}
	


}
