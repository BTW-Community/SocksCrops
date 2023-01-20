package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitPieRaw extends SCBlockPieRaw {

	public static int apple = 0;
	public static int cherry = 1;
	public static int lemon = 2;
	
	protected SCBlockFruitPieRaw(int blockID) {
		super(blockID);
	}
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier)
	{
		if (meta == apple) return SCDefs.applePieRaw.itemID;
		else if (meta == cherry) return SCDefs.cherryPieRaw.itemID;
		else if (meta == lemon) return SCDefs.lemonPieRaw.itemID;
		else return 0;
	}
	
	@Override
	public void registerIcons(IconRegister register)
	{
		blockIcon = rawPastry = register.registerIcon( "fcBlockPastryUncooked" );
		pieTop[0] = register.registerIcon( "SCBlockPieRawTop_apple" );
		pieTop[1] = register.registerIcon( "SCBlockPieRawTop_cherry" );
		pieTop[2] = register.registerIcon( "SCBlockPieRawTop_lemon" );
	}
	
	

}
