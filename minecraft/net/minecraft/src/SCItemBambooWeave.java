package net.minecraft.src;

public class SCItemBambooWeave extends Item {

	protected SCItemBambooWeave(int id) {
		super(id);
		
		SetBuoyant();
		SetBellowsBlowDistance( 2 );
		SetIncineratedInCrucible();
		SetFurnaceBurnTime( FCEnumFurnaceBurnTime.WICKER_PIECE );
		SetFilterableProperties( Item.m_iFilterable_Thin );

		setUnlocalizedName("SCItemBambooWeave");

		setCreativeTab( CreativeTabs.tabMaterials );    	
	}

	@Override
	public boolean GetCanBeFedDirectlyIntoCampfire( int iItemDamage )
	{
		// so that it doesn't accidentally go into a fire after basket weaving

		return false;
	}

	@Override
	public boolean GetCanBeFedDirectlyIntoBrickOven( int iItemDamage )
	{
		return false;
	}

}
