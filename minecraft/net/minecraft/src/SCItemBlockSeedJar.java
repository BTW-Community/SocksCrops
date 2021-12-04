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

		list.add( "ID: " + this.getContentsData(stack, player.worldObj) );
		list.add( "Name: " + "Seed Jar");
		
	}
	
    public int getContentsData(ItemStack stack, World world)
    {        
    	if (stack.stackTagCompound != null && stack.stackTagCompound.hasKey("tag"))
    	{
    		stack.stackTagCompound.getInteger("id");
    	}
    	
    	return 0;
    }
	
	

}
