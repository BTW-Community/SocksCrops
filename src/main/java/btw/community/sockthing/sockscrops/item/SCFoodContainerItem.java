package btw.community.sockthing.sockscrops.item;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class SCFoodContainerItem extends SCFoodItem
{
    private Item ejectedItem;

    public SCFoodContainerItem(int iItemID, Item ejectedItem, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName )
    {
        super( iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName );
        this.ejectedItem = ejectedItem;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player )
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
