package net.minecraft.src;

public class SCBlockDirtLooseNutrition2 extends SCBlockDirtLooseBase {

	public SCBlockDirtLooseNutrition2(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlocksDirtLooseDry_1");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition2.blockID );

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
		}
    	
    	return true;
    }

}
