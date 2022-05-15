package net.minecraft.src;

import java.util.List;

public class SCItemRocks extends FCItemPlacesAsBlock {
	
	private final String[] field_82804_b;
	private Block block;
	
	public SCItemRocks(int par1, Block par2Block, String name, String[] par3ArrayOfStr) {
		super(par1);
		this.block = par2Block;
		this.field_82804_b = par3ArrayOfStr;
		this.setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHasSubtypes(true);
	}
	

    private Icon stoneMedium;
    private Icon stoneSmall;
    private Icon sandstoneMedium;
    private Icon sandstoneSmall;
    
    private Icon mossMedium;
    private Icon mossSmall;
    
    @Override
    public void registerIcons(IconRegister register) {
    	stoneMedium = register.registerIcon("SCItemRocks_stoneMedium");
    	stoneSmall = register.registerIcon("SCItemRocks_stoneSmall");
    	sandstoneMedium = register.registerIcon("SCItemRocks_sandstoneMedium");
    	sandstoneSmall = register.registerIcon("SCItemRocks_sandstoneSmall");
    	
    	mossMedium = register.registerIcon("SCItemRocksOverlay_mossMedium");
    	mossSmall = register.registerIcon("SCItemRocksOverlay_mossSmall");
    	
    	if (block == SCDefs.rocksSandstone)
    	{
    		stoneMedium = sandstoneMedium;
    		stoneSmall = sandstoneSmall;
    	}
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
    		if (damage == 8) return mossSmall;
    		else if (damage == 9) return mossMedium;
    		else if (damage == 1) return stoneMedium;
    		else return stoneSmall;
    	else
    	{
    		if (damage == 1) return stoneMedium;
    		else if (damage == 9) return stoneMedium;
    		else return stoneSmall;
    	}
    }
    
    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int damage)
    {
    	if (damage == 0) {
    		return sandstoneSmall;
    	}
    	else if (damage == 1) {
    		return sandstoneSmall;
    	}
        return stoneMedium;
    }
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		for (int i = 0; i < 2; i++) {
			par3List.add(new ItemStack(par1, 1, i));
			par3List.add(new ItemStack(par1, 1, i + 8));
		}
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

        if (var2 < 0 || var2 >= this.field_82804_b.length)
        {
            var2 = 0;
        }

        return super.getUnlocalizedName() + "." + this.field_82804_b[var2];
    }
    
}
