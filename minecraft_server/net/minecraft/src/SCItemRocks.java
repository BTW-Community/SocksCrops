package net.minecraft.src;

import java.util.List;

public class SCItemRocks extends FCItemPlacesAsBlock {
	
	public static String[] rockTypes = new String[]{
			"smallRock", "largeRock",
			"smallRockMossy", "largeRockMossy"
			
		};
	public String[] rockTextures;
	private Block placedBlock;
	
	public SCItemRocks(int par1, Block placedBlock, String name, String[] rockTextures) {
		super(par1);
		this.placedBlock = placedBlock;
		this.rockTextures = rockTextures;
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHasSubtypes(true);
	}

    private Icon stoneMedium;
    private Icon stoneSmall;
    
    private Icon mossMedium;
    private Icon mossSmall;

    

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int damage)
    {
    	if (damage == 0) {
    		return stoneSmall;
    	}
        return stoneMedium;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

//        if (var2 < 0 || var2 >= this.rockTypes.length)
//        {
//            var2 = 0;
//        }

        return super.getUnlocalizedName() + "." + this.rockTypes[var2];
    }
    
}
