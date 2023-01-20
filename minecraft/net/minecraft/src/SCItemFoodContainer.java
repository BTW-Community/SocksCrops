//SCADDON: Copied FCMOD FCItemSoup

package net.minecraft.src;

public class SCItemFoodContainer extends SCItemFood
{
	private Item ejectedItem;
	
    public SCItemFoodContainer( int iItemID, Item ejectedItem, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName )
    {
        super( iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName );  
        this.ejectedItem = ejectedItem;
    }
    
    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        super.onEaten( stack, world, player );

        ItemStack bowlStack = new ItemStack( ejectedItem );
        
        if ( !player.inventory.addItemStackToInventory( bowlStack ) )
        {
            player.dropPlayerItem( bowlStack );
        }
        
        return stack;
    }
}
