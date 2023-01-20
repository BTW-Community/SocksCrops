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
	public int idPicked(World world, int x, int y, int z) {
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (getType(meta) == apple) return SCDefs.applePieCooked.itemID;
		else if (getType(meta) == cherry) return SCDefs.cherryPieCooked.itemID;
		else if (getType(meta) == lemon) return SCDefs.lemonPieCooked.itemID;
		else return 0;
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
	
	@Override
    public void registerIcons( IconRegister register )
    {
		cookedPie = register.registerIcon( "SCBlockPieCooked" );
		
        cookedTop[0] = register.registerIcon( "SCBlockPieCookedTop_apple" );
        cookedTop[1] = register.registerIcon( "SCBlockPieCookedTop_cherry" );
        cookedTop[2] = register.registerIcon( "SCBlockPieCookedTop_lemon" );
        
        cookedCrustInside[2] = register.registerIcon( "SCBlockPieCookedOverlay_2" );
        cookedCrustInside[3] = register.registerIcon( "SCBlockPieCookedOverlay_3" );
        cookedCrustInside[1] = register.registerIcon( "SCBlockPieCookedOverlay_1" );
        cookedCrustInside[0] = register.registerIcon( "SCBlockPieCookedOverlay_0" );
       
        cookedPieInside[0] = register.registerIcon( "SCBlockPieCookedInside_apple" );
        cookedPieInside[1] = register.registerIcon( "SCBlockPieCookedInside_cherry" );
        cookedPieInside[2] = register.registerIcon( "SCBlockPieCookedInside_lemon" );
    }

}
