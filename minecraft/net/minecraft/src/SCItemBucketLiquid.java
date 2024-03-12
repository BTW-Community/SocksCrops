package net.minecraft.src;

import java.util.List;

public class SCItemBucketLiquid extends FCItemBucketDrinkable {

	public SCItemBucketLiquid(int iItemID, int iHungerHealed, float fSaturationModifier, String name) {
		super(iItemID, iHungerHealed, fSaturationModifier);
		setUnlocalizedName(name);
		setHasSubtypes(true);
	}
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		
		//i starts at 3 to not list water, milk and chocolate milk
		for (int i=3; i < SCUtilsLiquids.namesList.size(); i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}
	
	@Override
	public int GetBlockIDToPlace( int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
    	return SCDefs.bucketBlocks[iItemDamage].blockID;
		//return getBlockID();
    }
	
	@Override
	public int getBlockID() {
		return -1;
	}
	
	@Override
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 1, SCUtilsLiquids.namesList.size());
        return super.getUnlocalizedName() + "." + SCUtilsLiquids.namesList.get(var2);
    }
	
    public int getColorFromDamage(int type)
    {
    	return SCUtilsLiquids.colorList.get(type);
    	//PotionHelper.func_77915_a(par1, false);
    }

    public int getColorFromItemStack(ItemStack itemStack, int pass)
    {
        return pass > 0 ? this.getColorFromDamage(itemStack.getItemDamage()) : 16777215;
    }
	
	private Icon bucket;
	private Icon contents;
	
	@Override
	public void registerIcons(IconRegister register) {
		this.itemIcon = bucket = register.registerIcon("bucket"); //TODO: Replace with seperate SC Texture 
        contents = register.registerIcon("SCItemBucket_contents");// par1IconRegister.registerIcon("SCItemBucket_contents");
	}
	
    /**
     * 
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 0 ? this.bucket : this.contents;// .getIconFromDamageForRenderPass(damage, pass);
    }
    
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }



}
