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
	

	@Override
	public void registerBurgerIcons(IconRegister register) {
		//fish
		incompleteBurgerIcon[0] = register.registerIcon("SCItemBurgerFish_incomplete_2");
		burgerIcon[0] = register.registerIcon("SCItemBurgerFish_2");
		
		incompleteBurgerIcon[2] = register.registerIcon("SCItemBurgerFish_incomplete_2");
		burgerIcon[2] =  register.registerIcon("SCItemBurgerFish_2");
			
		incompleteBurgerIcon[3] = register.registerIcon("SCItemBurgerFish_incomplete_3");
		burgerIcon[3] =  register.registerIcon("SCItemBurgerFish_3");
		
		incompleteBurgerIcon[18] = register.registerIcon("SCItemBurgerFish_incomplete_18");
		burgerIcon[18] =  register.registerIcon("SCItemBurgerFish_18");
				
		incompleteBurgerIcon[19] = register.registerIcon("SCItemBurgerFish_incomplete_19");
		burgerIcon[19] =  register.registerIcon("SCItemBurgerFish_19");
				
		incompleteBurgerIcon[66] = register.registerIcon("SCItemBurgerFish_incomplete_66");
		burgerIcon[66] =  register.registerIcon("SCItemBurgerFish_66");
				
		incompleteBurgerIcon[67] = register.registerIcon("SCItemBurgerFish_incomplete_67");
		burgerIcon[67] =  register.registerIcon("SCItemBurgerFish_67");
				
		incompleteBurgerIcon[34] = register.registerIcon("SCItemBurgerFish_incomplete_34");
		burgerIcon[34] =  register.registerIcon("SCItemBurgerFish_34");
				
		incompleteBurgerIcon[35] = register.registerIcon("SCItemBurgerFish_incomplete_35");
		burgerIcon[35] =  register.registerIcon("SCItemBurgerFish_35");
				
		incompleteBurgerIcon[50] = register.registerIcon("SCItemBurgerFish_incomplete_50");
		burgerIcon[50] =  register.registerIcon("SCItemBurgerFish_50");
				
		incompleteBurgerIcon[51] = register.registerIcon("SCItemBurgerFish_incomplete_51");
		burgerIcon[51] =  register.registerIcon("SCItemBurgerFish_51");
				
		incompleteBurgerIcon[82] = register.registerIcon("SCItemBurgerFish_incomplete_82");
		burgerIcon[82] =  register.registerIcon("SCItemBurgerFish_82");
				
		incompleteBurgerIcon[83] = register.registerIcon("SCItemBurgerFish_incomplete_83");
		burgerIcon[83] =  register.registerIcon("SCItemBurgerFish_83");
				
		incompleteBurgerIcon[98] = register.registerIcon("SCItemBurgerFish_incomplete_98");
		burgerIcon[98] =  register.registerIcon("SCItemBurgerFish_98");
				
		incompleteBurgerIcon[99] = register.registerIcon("SCItemBurgerFish_incomplete_99");
		burgerIcon[99] =  register.registerIcon("SCItemBurgerFish_99");
				
		incompleteBurgerIcon[114] = register.registerIcon("SCItemBurgerFish_incomplete_114");
		burgerIcon[114] =  register.registerIcon("SCItemBurgerFish_114");
				
		incompleteBurgerIcon[115] = register.registerIcon("SCItemBurgerFish_incomplete_115");
		burgerIcon[115] =  register.registerIcon("SCItemBurgerFish_115");
	}


}
