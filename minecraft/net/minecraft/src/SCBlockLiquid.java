package net.minecraft.src;

import java.util.List;

public class SCBlockLiquid extends FCBlockMilk {

	private int type;
	private String name;
	
	public SCBlockLiquid(int iBlockID, String name, int type) {
        super( iBlockID );
        
        InitBlockBounds( 0D, 0D, 0D, 1D, m_dHeight, 1D );
        
        setHardness( 0F );
        setResistance( 0F );
        
        setStepSound( FCBetterThanWolves.fcStepSoundSquish );
        
        setUnlocalizedName(name);
        
        //setCreativeTab(CreativeTabs.tabBlock);
        
        this.type = type;
        this.name = name;
	}
	
//    public void getSubBlocks(int id, CreativeTabs tab, List list)
//    {
//    	//i starts with 3 to not register water, milk and chocolate milk
//		for (int i = 3; i < SCUtilsLiquids.namesList.size(); i++)
//		{
//			list.add(new ItemStack(id, 1, type));
//		}
//    }
		
	@Override
	public Icon getIcon(int side, int meta) {
		
		return liquid;
	}

	private Icon liquid;
	
	@Override
	public void registerIcons(IconRegister register) {
		liquid = register.registerIcon(name + "_" + SCUtilsLiquids.namesList.get(type));
//		//i starts with 3 to not register water, milk and chocolate milk
//		for (int i = 3; i < SCUtilsLiquids.namesList.size(); i++)
//		{
//			liquids[i] = register.registerIcon(getUnlocalizedName().substring(5) + "_" + SCUtilsLiquids.namesList.get(i));
//		}

	}

}
