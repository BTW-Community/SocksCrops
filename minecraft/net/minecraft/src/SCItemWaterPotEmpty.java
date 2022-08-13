//SCADDON: Based on FCItemBucketEmpty
package net.minecraft.src;

public class SCItemWaterPotEmpty extends FCItemPlacesAsBlock {

	public SCItemWaterPotEmpty(int iItemID, int iBlockID, int iBlockMetadata) {
		super(iItemID, iBlockID, iBlockMetadata);

	}   
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {

		if (player.isSneaking()) {
			// override normal block place behavior so it goes to onItemRightClick()
			return false;
		}
		//place block
		 return super.onItemUse(stack, player, world, i, j, k, iFacing, fClickX, fClickY, fClickZ);
	}
	
    @Override
    public ItemStack onItemRightClick( ItemStack itemStack, World world, EntityPlayer player )
    {
    	MovingObjectPosition posClicked = 
    		FCUtilsMisc.GetMovingObjectPositionFromPlayerHitWaterAndLava( world, player, true );
        
        if ( posClicked != null && posClicked.typeOfHit == EnumMovingObjectType.TILE )
        {
            int i = posClicked.blockX;
            int j = posClicked.blockY;
            int k = posClicked.blockZ;
            
            int iBlockID = world.getBlockId( i, j, k );
            Block waterPot = SCDefs.waterPot;
            
            if ( world.getBlockMaterial( i, j, k ) == Material.water )
            {
    			if ( FCUtilsMisc.DoesWaterHaveValidSource( world, i, j, k, 128 ) )
				{        			
                    if ( --itemStack.stackSize <= 0 )
                    {
                    	return new ItemStack(waterPot);
                    }                    
                    else if ( !player.inventory.addItemStackToInventory( 
                    	new ItemStack( waterPot ) ) )
                    {
                    	player.dropPlayerItem( new ItemStack( waterPot, 1, 0 ) );
                    }	
				}
    			
                return itemStack;
            }                
            else if ( world.getBlockMaterial( i, j, k ) == Material.lava )
            {
            	player.dealFireDamage( 1 );

                world.playSoundEffect( (double)i  + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
            		"random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
                
            	if ( world.isRemote )
            	{
                    for(int l = 0; l < 8; l++)
                    {
                        world.spawnParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
                    }
            	}
                
                return itemStack;
            }
        } 
        
        return itemStack;
    }

}
