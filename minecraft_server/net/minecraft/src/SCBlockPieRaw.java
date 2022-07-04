package net.minecraft.src;

import java.util.Random;

public class SCBlockPieRaw extends SCBlockPieBase {

	public static int subtypeSweetberry = 0;
	public static int subtypeBlueberry = 1;
	
	protected SCBlockPieRaw(int blockID)
	{
		super(blockID);
	}
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier)
	{
		if (meta == subtypeSweetberry) return SCDefs.sweetberryPieRaw.itemID;
		else if (meta == subtypeBlueberry) return SCDefs.blueberryPieRaw.itemID;
		else return 0;
	}

}
