package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public abstract class SCItemSaladBase extends SCItemFood {
	
	protected final int LETTUCE = 0;
	protected final int CHICKEN = 1;
	protected final int CARROT = 2;
	protected final int WILD_CARROT = 3;
	protected final int BREAD = 4;
	protected final int TOMATO = 5;
	protected final int MUSHROOM = 6;
	protected final int BACON = 7;
	protected final int ONION = 8;
	
	protected boolean completed;
	
	public SCItemSaladBase(int iItemID, boolean bWolfMeat, String sItemName, boolean completed) {
		super(iItemID, 0, 0, bWolfMeat, sItemName);
		
		hasSubtypes = true;
		this.completed = completed;
	}

	@Override
	public int GetHungerRestored(ItemStack stack) {
		int healAmount = 0;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) healAmount += SCItemFood.lettuceLeafHungerHealed;
		if(contents[CHICKEN] == 1) healAmount += SCItemFood.chickenDrumCookedHungerHealed;
		if(contents[CARROT] == 1) healAmount += SCItemFood.wildCarrotHungerHealed;
		if(contents[WILD_CARROT] == 1) healAmount += SCItemFood.wildCarrotHungerHealed;
		if(contents[BREAD] == 1) healAmount += SCItemFood.halfBreadHungerHealed;
		if(contents[TOMATO] == 1) healAmount += SCItemFood.tomatoHungerHealed;
		if(contents[MUSHROOM] == 1) healAmount += SCItemFood.mushroomHungerHealed;
		if(contents[BACON] == 1) healAmount += SCItemFood.baconCookedHungerHealed;
		if(contents[ONION] == 1) healAmount += SCItemFood.onionSliceHungerHealed;
		
		return healAmount;
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		float saturation = 0;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[CHICKEN] == 1) saturation += SCItemFood.m_fChickenSaturationModifier;
		if(contents[CARROT] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[WILD_CARROT] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[BREAD] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[TOMATO] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[MUSHROOM] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[BACON] == 1) saturation += SCItemFood.baconCookedSaturationModifier;
		if(contents[ONION] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;

		return saturation;
	}

	public abstract void getSubItems(int id, CreativeTabs creativeTab, List list);
	
	protected Icon[] incompleteBurgerIcon = new Icon[128];
	protected Icon[] burgerIcon = new Icon[128];
	
	@Override
	public void registerIcons(IconRegister register) {
		registerBurgerIcons(register);
	}
	
	protected abstract void registerBurgerIcons(IconRegister register);

	@Override
	public Icon getIconFromDamage(int damage) 
	{
		return burgerIcon[damage];
	}

	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bAdvamcedToolTips)
	{

		String contains = StatCollector.translateToLocal( "SCItemBurger.ingredients" );
		int[] contents = getContentsFromDamage(itemStack.getItemDamage());

		Item[] salad = new Item[9];
					
		if (contents[LETTUCE] == 1) salad[8] = SCDefs.wildLettuce;
		if (contents[CHICKEN] == 1) salad[7] = Item.chickenCooked;
		if (contents[CARROT] == 1) salad[6] = Item.carrot;
		if (contents[WILD_CARROT] == 1) salad[5] = SCDefs.wildCarrot;
		if (contents[BREAD] == 1) salad[4] = Item.bread;
		if (contents[TOMATO] == 1) salad[3] = SCDefs.tomato;
		if (contents[MUSHROOM] == 1) salad[2] = null;
		if (contents[BACON] == 1) salad[1] = SCDefs.baconCooked;
		if (contents[ONION] == 1) salad[0] = SCDefs.wildOnion;
		
		
		if (salad.length == 0)
			return;
				
		infoList.add("§8" + contains + ":");
		
		for (Item ingredient : salad)
		{
			if (ingredient != null)
			{
				String name = StatCollector.translateToLocal(ingredient.getUnlocalizedName() + ".name");
				infoList.add("§o" + name);
			}
		}
	}

	protected static int[] getContentsFromDamage(int damage)
	{
		int salad = damage & 1;
		int chicken = (damage >> 1) & 1;
		int carrot = (damage >> 2) & 1;
		int wildCarrot = (damage >> 3) & 1;
		int bread = (damage >> 4) & 1;
		int tomato = (damage >> 5) & 1;
		int mushroom = (damage >> 6) & 1;
		int bacon = (damage >> 7) & 1;
		int onion = (damage >> 8) & 1;
		
		return new int[] {salad, chicken, carrot, wildCarrot, bread, tomato, mushroom, bacon, onion };
	}	
}
