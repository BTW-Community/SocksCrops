package net.minecraft.src;

public class SCItemGrape extends FCItemFood {
	
	public SCItemGrape(int blockID) {
		//iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName, bZombiesConsume
		super(blockID, 2, 0.1F, false, "SCItemGrapes");
		this.setCreativeTab(CreativeTabs.tabFood);
	}

	//Icon Stuff
	public static final String[] eatingProgressArray = new String[] {"apple", "apple_1" , "apple_2", "apple_3", "apple_4"};
    private Icon[] iconArray;
	
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.iconArray = new Icon[eatingProgressArray.length];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(eatingProgressArray[var2]);
        }
    }
    
    @Override
    public Icon getIconFromDamage(int par1) {
    	return this.iconArray[par1];
    }
}