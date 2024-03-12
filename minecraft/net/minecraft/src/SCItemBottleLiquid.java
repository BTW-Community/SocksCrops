package net.minecraft.src;

import java.util.List;

public class SCItemBottleLiquid extends SCItemDrinkable {
		
	public SCItemBottleLiquid( int iItemID, int iHungerHealed, float fSaturationModifier, String name )
    {
        super( iItemID, Item.glassBottle, iHungerHealed, fSaturationModifier, name );
        setHasSubtypes(true);
    }
	
	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		
		for (int i=1; i < SCUtilsLiquids.namesList.size(); i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
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
        return pass > 0 ? 16777215 : this.getColorFromDamage(itemStack.getItemDamage());
    }
	
	private Icon bottle;
	private Icon contents;
	
	@Override
	public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = bottle = par1IconRegister.registerIcon("potion"); 
        contents = par1IconRegister.registerIcon("potion_contents");
	}
	
    /**
     * 
     * Gets an icon index based on an item's damage value and the given render pass
     */
    public Icon getIconFromDamageForRenderPass(int damage, int pass)
    {
        return pass == 0 ? this.contents : super.getIconFromDamageForRenderPass(damage, pass);
    }
    
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
}
