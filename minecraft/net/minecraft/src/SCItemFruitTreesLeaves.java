package net.minecraft.src;

public class SCItemFruitTreesLeaves extends ItemBlock
{
	private Block block;
	
    public SCItemFruitTreesLeaves( int iItemID, Block block )
    {
        super( iItemID );
        
        this.block = block;
        
        setMaxDamage( 0 );
        setHasSubtypes( true );
    }


    @Override
    public int getMetadata( int iItemDamage )
    {
        return iItemDamage | 4;
    }

	//----------- Client Side Functionality -----------//

    @Override
    public Icon getIconFromDamage( int iItemDamage )
    {
        return block.getIcon( 0, iItemDamage );
    }

    @Override
    public int getColorFromItemStack( ItemStack itemStack, int iLayer )
    {
    	return 0x000000;
    }

    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        int var2 = par1ItemStack.getItemDamage();

        if (var2 < 0 || var2 >= SCBlockFruitTreesLeaves.LEAF_TYPES.length)
        {
            var2 = 0;
        }

        return super.getUnlocalizedName() + "." + SCBlockFruitTreesLeaves.LEAF_TYPES[var2];
    }
}
