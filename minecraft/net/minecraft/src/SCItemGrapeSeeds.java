package net.minecraft.src;

public class SCItemGrapeSeeds extends Item {

	public SCItemGrapeSeeds(int itemID) {
		super(itemID); //Defined in SCDefs
    	SetAsBasicChickenFood();
    	
    	setUnlocalizedName( "SCItemGrapeSeeds" );
	}
	
	
	/**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack itemStack, EntityPlayer par2EntityPlayer, World world, int i, int j, int k, int par7, float par8, float par9, float par10)
    {
    	Block cropBlock = SCDefs.grapeCrop;
    	
        if ( cropBlock != null && cropBlock.canPlaceBlockAt( world, i, j + 1, k ) )
        {
            world.setBlockWithNotify( i, j + 1, k, cropBlock.blockID );
            
            world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, 
            	Block.soundGrassFootstep.getStepSound(), 
            	( Block.soundGrassFootstep.getVolume() + 1F ) / 2F, 
            	Block.soundGrassFootstep.getPitch() * 0.8F );
            
            itemStack.stackSize--;
                
        }
        return true;
    }
}
