// FCMOD

package net.minecraft.src;

public class SCItemDrinkable extends Item
{
	private int m_iHungerHealed;
	private float m_fSaturationModifier;
	
	private Item returnItem;
	
    public SCItemDrinkable( int iItemID, Item returnItem, int iHungerHealed, float fSaturationModifier, String name )
    {
        super( iItemID );
        
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabFood);
        
        setMaxStackSize(8);
        
    	m_iHungerHealed = iHungerHealed;
    	m_fSaturationModifier = fSaturationModifier;
    	
    	this.returnItem = returnItem;
    }
    
    @Override
    public int getMaxItemUseDuration( ItemStack stack )
    {
        return 32;
    }
    
    @Override
    public EnumAction getItemUseAction( ItemStack stack )
    {
        return EnumAction.drink;
    }
    
    @Override
    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {
    	if ( player.CanDrink() )
    	{
    		player.setItemInUse( stack, getMaxItemUseDuration( stack ) );
    	}
    	else
    	{
    		player.OnCantConsume();
    	}
        
        return stack;
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
    
	//------------- Class Specific Methods ------------//
	
	//----------- Client Side Functionality -----------//
}
