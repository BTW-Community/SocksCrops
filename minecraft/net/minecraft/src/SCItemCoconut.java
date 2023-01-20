package net.minecraft.src;

import java.util.List;

public class SCItemCoconut extends FCItemPlacesAsBlock {

	public SCItemCoconut( int iItemID)
    {
    	super(iItemID, SCDefs.coconutSapling.blockID, 0, "SCItemCoconut");

    	setHasSubtypes(true);
    	setCreativeTab(CreativeTabs.tabFood);
    }
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		 par3List.add(new ItemStack(par1, 1, 0));
		 par3List.add(new ItemStack(par1, 1, 12));
	}
	
	@Override
	public Icon getIconFromDamage(int damage)
	{
        if (damage < 4) return coconut[0];
        if (damage < 8) return coconut[1];
        if (damage < 12) return coconut[2];
        return coconut[3];
	}
	
    private Icon[] coconut = new Icon[4];
    
    @Override
    public void registerIcons(IconRegister register)
    {    
    	coconut[0] = register.registerIcon("SCItemCoconut");
    	
    	coconut[1] = register.registerIcon("SCBlockItemCoconutSapling_" + 0 );
    	coconut[2] = register.registerIcon("SCBlockItemCoconutSapling_" + 1 );
    	coconut[3] = register.registerIcon("SCBlockItemCoconutSapling_" + 2 );
    	
    }
}
