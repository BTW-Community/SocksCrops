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


	@Override
	protected void registerBurgerIcons(IconRegister register) {
		//burger
		incompleteBurgerIcon[1] = register.registerIcon("SCItemBurger_incomplete_1");

		incompleteBurgerIcon[2] = register.registerIcon("SCItemBurger_incomplete_2");
		burgerIcon[2] =  register.registerIcon("SCItemBurger_2");
		
		incompleteBurgerIcon[3] = register.registerIcon("SCItemBurger_incomplete_3");
		burgerIcon[3] =  register.registerIcon("SCItemBurger_3");
		
		incompleteBurgerIcon[10] = register.registerIcon("SCItemBurger_incomplete_10");
		burgerIcon[10] =  register.registerIcon("SCItemBurger_10");
		
		incompleteBurgerIcon[11] = register.registerIcon("SCItemBurger_incomplete_11");
		burgerIcon[11] =  register.registerIcon("SCItemBurger_11");
		
		incompleteBurgerIcon[18] = register.registerIcon("SCItemBurger_incomplete_18");
		burgerIcon[18] =  register.registerIcon("SCItemBurger_18");
		
		incompleteBurgerIcon[19] = register.registerIcon("SCItemBurger_incomplete_19");
		burgerIcon[19] =  register.registerIcon("SCItemBurger_19");
		
		incompleteBurgerIcon[26] = register.registerIcon("SCItemBurger_incomplete_26");
		burgerIcon[26] =  register.registerIcon("SCItemBurger_26");
		
		incompleteBurgerIcon[27] = register.registerIcon("SCItemBurger_incomplete_27");
		burgerIcon[27] =  register.registerIcon("SCItemBurger_27");
		
		incompleteBurgerIcon[34] = register.registerIcon("SCItemBurger_incomplete_34");
		burgerIcon[34] =  register.registerIcon("SCItemBurger_34");
		
		incompleteBurgerIcon[35] = register.registerIcon("SCItemBurger_incomplete_35");
		burgerIcon[35] =  register.registerIcon("SCItemBurger_35");
		
		incompleteBurgerIcon[42] = register.registerIcon("SCItemBurger_incomplete_42");
		burgerIcon[42] =  register.registerIcon("SCItemBurger_42");
		
		incompleteBurgerIcon[43] = register.registerIcon("SCItemBurger_incomplete_43");
		burgerIcon[43] =  register.registerIcon("SCItemBurger_43");
		
		incompleteBurgerIcon[50] = register.registerIcon("SCItemBurger_incomplete_50");
		burgerIcon[50] =  register.registerIcon("SCItemBurger_50");
		
		incompleteBurgerIcon[51] = register.registerIcon("SCItemBurger_incomplete_51");
		burgerIcon[51] =  register.registerIcon("SCItemBurger_51");
		
		incompleteBurgerIcon[58] = register.registerIcon("SCItemBurger_incomplete_58");
		burgerIcon[58] =  register.registerIcon("SCItemBurger_58");
		
		incompleteBurgerIcon[59] = register.registerIcon("SCItemBurger_incomplete_59");
		burgerIcon[59] =  register.registerIcon("SCItemBurger_59");
		
		incompleteBurgerIcon[66] = register.registerIcon("SCItemBurger_incomplete_66");
		burgerIcon[66] =  register.registerIcon("SCItemBurger_66");
		
		incompleteBurgerIcon[67] = register.registerIcon("SCItemBurger_incomplete_67");
		burgerIcon[67] =  register.registerIcon("SCItemBurger_67");
		
		incompleteBurgerIcon[74] = register.registerIcon("SCItemBurger_incomplete_74");
		burgerIcon[74] =  register.registerIcon("SCItemBurger_74");
		
		incompleteBurgerIcon[75] = register.registerIcon("SCItemBurger_incomplete_75");
		burgerIcon[75] =  register.registerIcon("SCItemBurger_75");
		
		incompleteBurgerIcon[82] = register.registerIcon("SCItemBurger_incomplete_82");
		burgerIcon[82] =  register.registerIcon("SCItemBurger_82");
		
		incompleteBurgerIcon[83] = register.registerIcon("SCItemBurger_incomplete_83");
		burgerIcon[83] =  register.registerIcon("SCItemBurger_83");
		
		incompleteBurgerIcon[90] = register.registerIcon("SCItemBurger_incomplete_90");
		burgerIcon[90] =  register.registerIcon("SCItemBurger_90");
		
		incompleteBurgerIcon[91] = register.registerIcon("SCItemBurger_incomplete_91");
		burgerIcon[91] =  register.registerIcon("SCItemBurger_91");
		
		incompleteBurgerIcon[98] = register.registerIcon("SCItemBurger_incomplete_98");
		burgerIcon[98] =  register.registerIcon("SCItemBurger_98");
		
		incompleteBurgerIcon[99] = register.registerIcon("SCItemBurger_incomplete_99");
		burgerIcon[99] =  register.registerIcon("SCItemBurger_99");
		
		incompleteBurgerIcon[106] = register.registerIcon("SCItemBurger_incomplete_106");
		burgerIcon[106] =  register.registerIcon("SCItemBurger_106");
		
		incompleteBurgerIcon[107] = register.registerIcon("SCItemBurger_incomplete_107");
		burgerIcon[107] =  register.registerIcon("SCItemBurger_107");
	
		incompleteBurgerIcon[114] = register.registerIcon("SCItemBurger_incomplete_114");
		burgerIcon[114] =  register.registerIcon("SCItemBurger_114");
		
		incompleteBurgerIcon[115] = register.registerIcon("SCItemBurger_incomplete_115");
		burgerIcon[115] =  register.registerIcon("SCItemBurger_115");
		
		incompleteBurgerIcon[122] = register.registerIcon("SCItemBurger_incomplete_122");
		burgerIcon[122] =  register.registerIcon("SCItemBurger_122");
		
		incompleteBurgerIcon[123] = register.registerIcon("SCItemBurger_incomplete_123");
		burgerIcon[123] =  register.registerIcon("SCItemBurger_123");
	}



}
