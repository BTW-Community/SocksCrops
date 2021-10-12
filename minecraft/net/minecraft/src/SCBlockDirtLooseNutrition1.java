package net.minecraft.src;

public class SCBlockDirtLooseNutrition1 extends SCBlockDirtLooseBase {

	public SCBlockDirtLooseNutrition1(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlocksDirtLooseDry_2");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition1.blockID );

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
		}
    	
    	return true;
    }

}
