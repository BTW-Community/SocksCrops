package net.minecraft.src;

public class SCItemBamboo extends Item {

	protected SCItemBamboo(int par1) {
		super(par1);
		
    	SetBuoyant();
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING ); // this also allows the item to be valid fuel
    	SetIncineratedInCrucible();
    	SetFilterableProperties( m_iFilterable_Narrow );
    	
    	setCreativeTab(CreativeTabs.tabMaterials);
    	setUnlocalizedName("SCItemBamboo");
	}
	

}
