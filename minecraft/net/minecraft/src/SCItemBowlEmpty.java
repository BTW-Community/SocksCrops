package net.minecraft.src;

public class SCItemBowlEmpty extends Item {

	protected SCItemBowlEmpty(int par1) {
		super(par1);
		SetBuoyant();
		SetIncineratedInCrucible();
		setUnlocalizedName( "bowl" );
		setCreativeTab( CreativeTabs.tabMaterials );		
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
            
//            if ( world.getBlockMaterial( i, j, k ) == Material.water )
//            {
//    			if ( FCUtilsMisc.DoesWaterHaveValidSource( world, i, j, k, 128 ) )
//				{        			
//                    if ( --itemStack.stackSize <= 0 )
//                    {
//                    	return new ItemStack(SCDefs.bowlWater);
//                    }                    
//                    else if ( !player.inventory.addItemStackToInventory( 
//                    	new ItemStack( SCDefs.bowlWater ) ) )
//                    {
//                    	player.dropPlayerItem( new ItemStack( SCDefs.bowlWater.itemID, 1, 0 ) );
//                    }	
//				}
//    			
//                return itemStack;
//            }                
//            else if ( world.getBlockMaterial( i, j, k ) == Material.lava )
//            {
//            	player.dealFireDamage( 1 );
//
//                world.playSoundEffect( (double)i  + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
//            		"random.fizz", 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);
//                
//            	if ( world.isRemote )
//            	{
//                    for(int l = 0; l < 8; l++)
//                    {
//                        world.spawnParticle("largesmoke", (double)i + Math.random(), (double)j + Math.random(), (double)k + Math.random(), 0.0D, 0.0D, 0.0D);
//                    }
//            	}
//                
//                return itemStack;
//            }
        } 
        
        return itemStack;
    }

	//------------- Class Specific Methods ------------//
	
    private boolean IsPlayerClickingOnWaterOrLava( ItemStack stack, World world, EntityPlayer player )
    {
        MovingObjectPosition pos = FCUtilsMisc.GetMovingObjectPositionFromPlayerHitWaterAndLava( world, player, true );

        if ( pos != null )
        {
            if ( pos.typeOfHit == EnumMovingObjectType.TILE )
            {
                Material material = world.getBlockMaterial( pos.blockX, pos.blockY, pos.blockZ );
                
                if ( material == Material.water || material == Material.lava )
                {
                	return true;
                }
            }
        }
        
    	return false;
    }

}
