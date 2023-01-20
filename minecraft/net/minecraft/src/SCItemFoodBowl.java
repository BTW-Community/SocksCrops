package net.minecraft.src;

public class SCItemFoodBowl extends SCItemFood {

	private Item returnItem;
	
	public SCItemFoodBowl(int iItemID, Item returnItem, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat,	String sItemName) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName);
		
		this.returnItem = returnItem;
	}
	
	public SCItemFoodBowl(int iItemID, Item returnItem, int iHungerHealed, float fSaturationModifier, String sItemName) {
		super(iItemID, iHungerHealed, fSaturationModifier, false, sItemName);
		
		this.returnItem = returnItem;
	}
	
    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        super.onEaten( stack, world, player );

        ItemStack bowlStack = new ItemStack( returnItem );
        
        if ( !player.inventory.addItemStackToInventory( bowlStack ) )
        {
            player.dropPlayerItem( bowlStack );
        }
        
        return stack;
    }
}
