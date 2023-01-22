package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitPieCooked extends SCBlockPieCooked {

	public static final int apple = 0;
	public static final int cherry = 4;
	public static final int lemon = 8;
	
	protected SCBlockFruitPieCooked(int blockID) {
		super(blockID);
	}

	@Override
	public int idDropped( int meta, Random random, int fortuneModifier) {
		
		if (GetEatState(meta) == 0)
			if (getType(meta) == apple) return SCDefs.applePieCooked.itemID;
			else if (getType(meta) == cherry) return SCDefs.cherryPieCooked.itemID;
			else if (getType(meta) == lemon) return SCDefs.lemonPieCooked.itemID;
			else return 0;
		else return 0;
	}
	
    public int getType( int meta )
    {
    	if (meta < 4) return apple; //Pumpkin
    	else if (meta >= 4 && meta < 8) return cherry; //Sweetberry
    	else if (meta >= 8 && meta < 12) return lemon; //Blueberry
    	else return 12;
    }
	
	protected int getSliceItem(int sliceID, int meta) {
		if (getType(meta) == apple)
    		sliceID = SCDefs.applePieSlice.itemID;
    	
    	else if (getType(meta) == cherry)
    		sliceID = SCDefs.cherryPieSlice.itemID;
    	
    	else if (getType(meta) == lemon)
    		sliceID = SCDefs.lemonPieSlice.itemID;
		return sliceID;
	}
}
