package net.minecraft.src;

import java.util.Random;

public class SCBlockCakeChocolate extends SCBlockCakeBase {

	protected SCBlockCakeChocolate(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return SCDefs.chocolateCakeSlice.itemID;
	}

	@Override
	protected int getSliceItem() {
		return SCDefs.chocolateCakeSlice.itemID;
	}
	
}
