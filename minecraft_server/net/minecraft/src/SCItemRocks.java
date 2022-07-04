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
