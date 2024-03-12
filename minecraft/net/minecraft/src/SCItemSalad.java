package net.minecraft.src;

import java.util.List;

public class SCItemSalad extends SCItemSaladBase {

	public SCItemSalad(int iItemID, boolean bWolfMeat, String sItemName, boolean completed) {
		super(iItemID, bWolfMeat, sItemName, completed);
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		if (completed)
		{
			list.add(new ItemStack(id, 1, 19));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if (stack.getItemDamage() == 19)
		{
			return "item." + "SCItemSalad_chickenCaesar";
		}
		
		return super.getUnlocalizedName(stack); // "item." + this.unlocalizedName;
	}
	
	@Override
	protected void registerBurgerIcons(IconRegister register) {
		
		incompleteBurgerIcon[1] = register.registerIcon("SCItemSalad_1");
		incompleteBurgerIcon[3] = register.registerIcon("SCItemSalad_3");
		
		burgerIcon[19] =  register.registerIcon("SCItemSalad_19");		

	}



}
