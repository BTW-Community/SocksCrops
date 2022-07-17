package net.minecraft.src;

public class SCItemBlockBambooRoot extends ItemBlock {

	protected SCItemBlockBambooRoot(int id) {
		super(id);
		setUnlocalizedName("SCItemBlockBambooRoot_display");
	}
	
    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int meta)
    {
        return super.itemIcon != null ? this.itemIcon : Block.blocksList[itemID].getIcon(8, meta);
    }
    
   

}
