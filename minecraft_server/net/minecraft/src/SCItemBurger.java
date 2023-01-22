package net.minecraft.src;

import java.util.List;

public class SCItemBurger extends SCItemBurgerBase {
	
	public SCItemBurger(int iItemID, Item proteinItem, int proteinHungerHealed, float proteinSaturationModifier, boolean bWolfMeat, String sItemName,	boolean completed) {
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
			
			//egg
			list.add(new ItemStack(id, 1, 10));
			list.add(new ItemStack(id, 1, 42));
			list.add(new ItemStack(id, 1, 74));
			list.add(new ItemStack(id, 1, 106));
			
			//eggBacon
			list.add(new ItemStack(id, 1, 26));
			list.add(new ItemStack(id, 1, 58));
			list.add(new ItemStack(id, 1, 90));
			list.add(new ItemStack(id, 1, 122));
			
			//eggLettuce
			list.add(new ItemStack(id, 1, 11));
			list.add(new ItemStack(id, 1, 43));
			list.add(new ItemStack(id, 1, 75));
			list.add(new ItemStack(id, 1, 107));
			
			//eggLettuceBacon
			list.add(new ItemStack(id, 1, 27));
			list.add(new ItemStack(id, 1, 59));
			list.add(new ItemStack(id, 1, 91));
			list.add(new ItemStack(id, 1, 123));
		}
	}


}
