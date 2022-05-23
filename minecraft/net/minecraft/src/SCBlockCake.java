package net.minecraft.src;

public class SCBlockCake extends FCBlockCake {

	protected SCBlockCake(int iBlockID) {
		super(iBlockID);
	}
	
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack heldStack = player.getHeldItem();
        
        if (heldStack == null)
        {
        	 super.onBlockActivated(world, i, j, k, player, iFacing, fXClick, fYClick, fZClick);
        	 return true;
        }
        else if (heldStack.getItem() instanceof SCItemKnife)
        {
        	cutCake(world, i, j, k, player);
        	world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );
        	
            heldStack.attemptDamageItem(1, world.rand);
            
            int maxDamage = heldStack.getMaxDamage();
            if (heldStack.getItemDamage() >= maxDamage)
            {
            	//break tool
            	heldStack.stackSize = 0;
            	player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );            	            	
            }
        	return true;
        }
        return false;
    }

	private void cutCake(World world, int i, int j, int k, EntityPlayer player) {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

        if ( iEatState >= 6 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );                
        }
        
        if(!world.isRemote)
        {
        	
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, SCDefs.cakeSlice.itemID, 0);
        }
		
	}

}
