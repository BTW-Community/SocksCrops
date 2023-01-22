package net.minecraft.src;

import java.util.List;

public class SCItemCoconut extends FCItemPlacesAsBlock {

	public SCItemCoconut( int iItemID)
    {
    	super(iItemID, SCDefs.coconutSapling.blockID, 0, "SCItemCoconut");

    	setHasSubtypes(true);
    	setCreativeTab(CreativeTabs.tabFood);
    }

}
