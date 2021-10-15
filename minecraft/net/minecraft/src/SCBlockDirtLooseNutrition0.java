package net.minecraft.src;

import java.util.Random;

public class SCBlockDirtLooseNutrition0 extends SCBlockDirtLooseBase {

	public SCBlockDirtLooseNutrition0(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlocksDirtLooseDry_3");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition0.blockID );

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
		}
    	
    	return true;
    }

}
