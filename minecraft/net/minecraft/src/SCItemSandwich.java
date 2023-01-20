package net.minecraft.src;

import java.util.List;

public class SCItemSandwich extends SCItemBurgerBase {

	public SCItemSandwich(int iItemID, Item proteinItem, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat,
			String sItemName, boolean completed) {
		super(iItemID, proteinItem, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName, completed);
		
	}
	
	@Override
	public int GetHungerRestored(ItemStack stack) {
		int healAmount = SCItemFood.sandwichHalfHungerHealed;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) healAmount += SCItemFood.lettuceLeafHungerHealed;
		if(contents[PROTEIN] == 1) healAmount += getHealAmount();
		if(contents[CHEESE] == 1) healAmount += 0; //TODO Cheese
		if(contents[EGG] == 1) healAmount += SCItemFood.m_iFriedEggHungerHealed;
		if(contents[BACON] == 1) healAmount += SCItemFood.baconCookedHungerHealed;
		if(contents[TOMATO] == 1) healAmount += SCItemFood.tomatoSliceHungerHealed;
		if(contents[ONION] == 1) healAmount += SCItemFood.onionSliceHungerHealed;

		if (completed) healAmount += SCItemFood.sandwichHalfHungerHealed;
		
		return healAmount;
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		float saturation = SCItemFood.sandwichHalfSaturationModifier;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[PROTEIN] == 1) saturation += getSaturationModifier();
		if(contents[CHEESE] == 1) saturation += SCItemFood.FATTY_SATURATION_MODIFIER;
		if(contents[EGG] == 1) saturation += SCItemFood.PROTEIN_SATURATION_MODIFIER;
		if(contents[BACON] == 1) saturation += SCItemFood.PROTEIN_SATURATION_MODIFIER;
		if(contents[TOMATO] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[ONION] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;

		if (completed) saturation += SCItemFood.sandwichHalfSaturationModifier;	
		
		return saturation;
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		if (completed)
		{
			list.add(new ItemStack(id, 1, 16));
			list.add(new ItemStack(id, 1, 24));
			list.add(new ItemStack(id, 1, 49));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		
		if (completed)
		{
			if (stack.getItemDamage() == 49)
			{
				return "item." + "SCItemSandwich_blt";
			}
		}
		
		return super.getUnlocalizedName(stack); // "item." + this.unlocalizedName;
	}
	
	@Override
	protected void registerBurgerIcons(IconRegister register) {
		incompleteBurgerIcon[2] = register.registerIcon("SCItemSandwich_incomplete_2");
//		burgerIcon[2] =  register.registerIcon("SCItemBurger_2"); //tasty Sandwich
		
		//Bacon 
		incompleteBurgerIcon[16] = register.registerIcon("SCItemSandwich_incomplete_16");
		burgerIcon[16] =  register.registerIcon("SCItemSandwich_16");
		
		//Egg and Bacon
		incompleteBurgerIcon[8] = register.registerIcon("SCItemSandwich_incomplete_8");
		
		incompleteBurgerIcon[24] = register.registerIcon("SCItemSandwich_incomplete_24");
		burgerIcon[24] =  register.registerIcon("SCItemSandwich_24");
		
		//BLT
		incompleteBurgerIcon[1] = register.registerIcon("SCItemSandwich_incomplete_1");
		incompleteBurgerIcon[17] = register.registerIcon("SCItemSandwich_incomplete_17");
		incompleteBurgerIcon[49] = register.registerIcon("SCItemSandwich_incomplete_49");
		burgerIcon[49] =  register.registerIcon("SCItemSandwich_49");
	}



}
