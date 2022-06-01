package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCItemBlockStorageJar extends ItemBlock {
        
	public SCItemBlockStorageJar(int par1) {
		super(par1);
		this.setMaxStackSize(1);
	}
		
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{			
		String contains = StatCollector.translateToLocal( "scItemBlockStorageJar.contains" );
		String empty = StatCollector.translateToLocal( "scItemBlockStorageJar.empty" );
		String hasLabel = StatCollector.translateToLocal( "scItemBlockStorageJar.hasLabel" );
		
		if (stack.stackTagCompound != null)
		{

			int count = getCount(stack);
			String type = getName(stack);
			
			if (contains != null)
			{
				list.add(contains + " " + count + " " + type);
			}
			else list.add("Contains " + count + " " + type);
			
		}
		else
		{
			if (empty != null)
			{
				list.add(empty);
			}
			else list.add("Empty");
		}
		
		if (getLabel(stack) == 1)
		{
			list.add(hasLabel);
		}
		
		
		//DEBUG
		//list.add("Damage: " + stack.getItemDamage());
	}
	
	private int getLabel(ItemStack stack)
    {
    	int itemDamage = stack.getItemDamage();
    	
    	return SCBlockStorageJar.damageToData(itemDamage)[0];
	}
	
	private int getCount(ItemStack stack)
    {
    	if (stack.stackTagCompound != null  && stack.stackTagCompound.hasKey("Count"))
    	{
    		return stack.stackTagCompound.getInteger("Count");
    	}
		
    	else return 0;
	}
	
	private String getName(ItemStack stack)
	{
		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("seedType"))
    	{
			int seedType = stack.stackTagCompound.getInteger("seedType");

			if (stack.stackTagCompound.hasKey("seedDamage") )
			{
				int seedDamage = stack.stackTagCompound.getInteger("seedDamage");
				ItemStack itemStack = new ItemStack(seedType, 1, seedDamage);
				
				return itemStack.getDisplayName();
			}

    	}
		
		return "N/A";

		
	}
	
}
