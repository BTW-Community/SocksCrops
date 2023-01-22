package net.minecraft.src;

import java.util.List;

public class SCItemBurgerFish extends SCItemBurgerBase {

	public SCItemBurgerFish(int iItemID, Item proteinItem, int proteinHungerHealed, float proteinSaturationModifier, boolean bWolfMeat,
			String sItemName, boolean completed) {
		super(iItemID, proteinItem, proteinHungerHealed, proteinSaturationModifier, bWolfMeat, sItemName, completed);
	}
	
	@Override
	public void getSubItems(int id, CreativeTabs creativeTab, List list) {
		if (completed)
		{
			//No Lettuce
			list.add(new ItemStack(id, 1, 2));
			list.add(new ItemStack(id, 1, 34));
			list.add(new ItemStack(id, 1, 66));
			list.add(new ItemStack(id, 1, 98));
			
			//Bacon
			list.add(new ItemStack(id, 1, 18));
			list.add(new ItemStack(id, 1, 50));
			list.add(new ItemStack(id, 1, 82));
			list.add(new ItemStack(id, 1, 114));
			
			//Lettuce
			list.add(new ItemStack(id, 1, 3));
			list.add(new ItemStack(id, 1, 35));
			list.add(new ItemStack(id, 1, 67));
			list.add(new ItemStack(id, 1, 99));
	
			//LettuceBacon
			list.add(new ItemStack(id, 1, 19));
			list.add(new ItemStack(id, 1, 51));
			list.add(new ItemStack(id, 1, 83));
			list.add(new ItemStack(id, 1, 115));
		}
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		float saturation = getSaturationModifier();
		
		return saturation;
	}

	@Override
	public int GetHungerRestored(ItemStack stack) {
		int healAmount = getHealAmount();
		
		return healAmount;
	}
	

}
