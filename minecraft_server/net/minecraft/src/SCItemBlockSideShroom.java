package net.minecraft.src;

import java.util.List;

public class SCItemBlockSideShroom extends ItemMultiTextureTile {

	public SCItemBlockSideShroom(int par1, Block par2Block, String[] par3ArrayOfStr) {
		super(par1, par2Block, par3ArrayOfStr);
		hasSubtypes = true;
		
        SetBuoyant();
        SetBellowsBlowDistance( 2 );
    	SetFurnaceBurnTime( FCEnumFurnaceBurnTime.SMALL_FUEL );
		SetFilterableProperties( m_iFilterable_Small | m_iFilterable_Thin );
	}

	public static String[] names = {
			"brown","brown","brown","brown",
			"white","white","white","white",
			"red","red","red","red",
			"black","black","black","black"
			};
	
	private String[] types = {"brown","white","red","black"};

	private Icon[] shroom = new Icon[4];

	
}
