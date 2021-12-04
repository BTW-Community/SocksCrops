package net.minecraft.src;

import java.util.List;

public class SCItemBlockSeedJar extends ItemBlock {

	public SCItemBlockSeedJar(int itemID) {
		super(itemID);
		this.setMaxStackSize(1);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{
		list.add( "Name: " + "Seed Jar");
		list.add( "Seed: " + getSeedTypeData(stack, player.worldObj) );
		list.add( "Count: " + getCountData(stack, player.worldObj) );
		list.add( "Damage: " + getDamageData(stack, player.worldObj) );
		list.add( "Label: " + getLabelData(stack, player.worldObj) );
		
		
	}
		private String getSeedTypeData(ItemStack stack,  World worldObj)
	{
		if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("scSeedType"))
    	{
			int seedType = stack.stackTagCompound.getInteger("scSeedType");
			
			if (seedType == 1) return "Pumpkin";
			if (seedType == 2) return "Melon";
			if (seedType == 3) return "Netherwart";
			if (seedType == 4) return "Hemp";
			if (seedType == 5) return "Wheat";
			if (seedType == 6) return "Carrot";
			else return "N/A"; 
    	}
		return "N/A";
	}


	
    private boolean getLabelData(ItemStack stack, World worldObj)
    {
    	if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("scHasLabel"))
    	{
    		return stack.stackTagCompound.getBoolean("scHasLabel");
    	}
		return false;
	}

	private int getCountData(ItemStack stack, World worldObj)
    {
    	if (stack.stackTagCompound != null  && stack.stackTagCompound.hasKey("Count"))
    	{
    		return stack.stackTagCompound.getInteger("Count");
    	}
		
		return 0;
	}
    
    private int getDamageData(ItemStack stack, World worldObj)
    {
    	if (stack.stackTagCompound != null  && stack.stackTagCompound.getCompoundTag("tag").hasKey("Damage"))
    	{
    		return stack.stackTagCompound.getInteger("Damage");
    	}
		
		return 0;
	}

	private String stringForID(int contentsData, ItemStack stack)
    {
    	int seedjar[] = SCTileEntitySeedJar.validIDs;
    	for (int i = 0; i < seedjar.length; i++) {
			if (contentsData == seedjar[i])
			{
				return stack.getDisplayName();
			}
			
		}
    	return "N/A";
	}

	public int getContentsData(ItemStack stack, World world)
    {        
    	if (stack.stackTagCompound != null  && stack.stackTagCompound.getCompoundTag("tag").hasKey("id"))
    	{
    		return stack.stackTagCompound.getCompoundTag("tag").getInteger("id");
    	}
    	
    	return 0;
    }
	
	

}
