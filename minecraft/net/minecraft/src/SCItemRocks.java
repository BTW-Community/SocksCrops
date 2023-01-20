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
    
    @Override
    public void registerIcons(IconRegister register) {
    	stoneSmall = register.registerIcon(this.rockTextures[0]);
    	stoneMedium = register.registerIcon(this.rockTextures[1]);
    	
    	mossMedium = register.registerIcon("SCItemRocksMedium_mossOverlay");
    	mossSmall = register.registerIcon("SCItemRocksSmall_mossOverlay");
    }
    
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override    
    public Icon getIconFromDamageForRenderPass( int damage, int renderPass )
    {
    	if (renderPass == 1)
    		if (damage == 2) return mossSmall;
    		else if (damage == 3) return mossMedium;
    		else if (damage == 1) return stoneMedium;
    		else return stoneSmall;
    	else
    	{
    		if (damage == 1) return stoneMedium;
    		else if (damage == 3) return stoneMedium;
    		else return stoneSmall;
    	}
    }
    
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
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < 4; i++) {
			par3List.add(new ItemStack(par1, 1, i));

		}
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
