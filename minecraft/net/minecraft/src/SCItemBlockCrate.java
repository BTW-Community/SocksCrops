package net.minecraft.src;

public class SCItemBlockCrate extends ItemBlock {
	
	protected int placedBlockID;
	
	public SCItemBlockCrate(int iItemID, Block block) {
		super(iItemID);
		placedBlockID = block.blockID;
		setMaxStackSize(1);
	}
	
	public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int damage = par1ItemStack.getItemDamage();
        
        int wood = SCBlockCrate.damageToData(damage)[0];
		int type = SCBlockCrate.damageToData(damage)[2];

        return super.getUnlocalizedName() + "." + SCBlockCrate.woods[wood] + "." + SCBlockCrate.types[type];
    }
}
