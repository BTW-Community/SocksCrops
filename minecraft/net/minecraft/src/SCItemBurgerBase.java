package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public abstract class SCItemBurgerBase extends SCItemFood {

	protected boolean completed;
	protected Item proteinItem;
	
	public SCItemBurgerBase(int iItemID, Item proteinItem, int proteinHungerHealed, float proteinSaturationModifier, boolean bWolfMeat,
			String sItemName, boolean completed) {
		super(iItemID, proteinHungerHealed, proteinSaturationModifier, bWolfMeat, sItemName);
		
		hasSubtypes = true;
		
		this.completed = completed;
		this.proteinItem = proteinItem;
	}

	@Override
	public int GetHungerRestored(ItemStack stack) {
		int healAmount = SCItemFood.burgerBunHalfHungerHealed;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) healAmount += SCItemFood.lettuceLeafHungerHealed;
		if(contents[PROTEIN] == 1) healAmount += getHealAmount();
		if(contents[CHEESE] == 1) healAmount += 0; //TODO Cheese
		if(contents[EGG] == 1) healAmount += SCItemFood.m_iFriedEggHungerHealed;
		if(contents[BACON] == 1) healAmount += SCItemFood.baconCookedHungerHealed;
		if(contents[TOMATO] == 1) healAmount += SCItemFood.tomatoSliceHungerHealed;
		if(contents[ONION] == 1) healAmount += SCItemFood.onionSliceHungerHealed;

		if (completed) healAmount += SCItemFood.burgerBunHalfHungerHealed;
		
		return healAmount;
	}
	
	@Override
	public float getSaturationModifier(ItemStack stack) {
		float saturation = SCItemFood.burgerBunHalfSaturationModifier;
		
		int contents[] = getContentsFromDamage(stack.getItemDamage());
		
		if(contents[LETTUCE] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[PROTEIN] == 1) saturation += getSaturationModifier();
		if(contents[CHEESE] == 1) saturation += SCItemFood.FATTY_SATURATION_MODIFIER;
		if(contents[EGG] == 1) saturation += SCItemFood.PROTEIN_SATURATION_MODIFIER;
		if(contents[BACON] == 1) saturation += SCItemFood.PROTEIN_SATURATION_MODIFIER;
		if(contents[TOMATO] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;
		if(contents[ONION] == 1) saturation += SCItemFood.VEGS_SATURATION_MODIFIER;

		if (completed) saturation += SCItemFood.burgerBunHalfSaturationModifier;			
		
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
		if (!completed)
		{
			return incompleteBurgerIcon[damage];
		}
		else {
			return burgerIcon[damage];
		}
	}
	
	protected final int LETTUCE = 0;
	protected final int PROTEIN = 0;
	protected final int CHEESE = 0;
	protected final int EGG = 0;
	protected final int BACON = 0;
	protected final int TOMATO = 0;
	protected final int ONION = 0;
	
	@Override
	public void addInformation(ItemStack itemStack, EntityPlayer player, List infoList, boolean bAdvamcedToolTips)
	{

		String contains = StatCollector.translateToLocal( "SCItemBurger.ingredients" );
		int[] contents = getContentsFromDamage(itemStack.getItemDamage());

		Item[] burgers = new Item[7];
					
		if (contents[0] == 1) burgers[6] = SCDefs.wildLettuce;
		if (contents[1] == 1) burgers[5] = proteinItem;
		if (contents[2] == 1) burgers[4] = null; //TODO: Change to cheese
		if (contents[3] == 1) burgers[3] = FCBetterThanWolves.fcItemFriedEgg;
		if (contents[4] == 1) burgers[2] = SCDefs.baconCooked;
		if (contents[5] == 1) burgers[1] = SCDefs.tomatoSlice;
		if (contents[6] == 1) burgers[0] = SCDefs.wildOnionSlice;
		
		if (burgers.length == 0)
			return;
				
		infoList.add("§8" + contains + ":");
		
		for (Item ingredient : burgers)
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
		int patty = (damage >> 1) & 1;
		int cheese = (damage >> 2) & 1;
		int egg = (damage >> 3) & 1;
		int bacon = (damage >> 4) & 1;
		int tomato = (damage >> 5) & 1;
		int onion = (damage >> 6) & 1;
		
		return new int[] {salad, patty, cheese, egg, bacon, tomato, onion };
	}	
}
