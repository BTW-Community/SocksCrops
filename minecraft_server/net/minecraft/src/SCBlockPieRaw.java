package net.minecraft.src;

import java.util.Random;

public class SCBlockPieRaw extends SCBlockPieBase {

	public static final int PUMPKIN = 0;
	public static final int SWEETBERRY = 1;
	public static final int BLUEBERRY = 2;
	
	protected SCBlockPieRaw(int blockID)
	{
		super(blockID);
		setUnlocalizedName("SCBlockPieRaw");
	}
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier)
	{
		if (meta == PUMPKIN) return FCBetterThanWolves.fcItemPastryUncookedPumpkinPie.itemID;
		else if (meta == SWEETBERRY) return SCDefs.sweetberryPieRaw.itemID;
		else if (meta == BLUEBERRY) return SCDefs.blueberryPieRaw.itemID;
		else return 0;
	}
		
	protected Icon rawPastry;
	protected Icon[] pieTop = new Icon[16];

}
