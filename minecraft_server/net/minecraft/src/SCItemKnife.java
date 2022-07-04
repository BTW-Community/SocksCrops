package net.minecraft.src;

public class SCItemKnife extends FCItemTool {

	protected SCItemKnife(int itemID, EnumToolMaterial material, String name) {
		super(itemID, 3, material);
		setUnlocalizedName(name);
	}
	
    @Override
    public float getStrVsBlock( ItemStack toolItemStack, World world, Block block, int i, int j, int k ) 
    {
    	int iToolLevel = toolMaterial.getHarvestLevel();
    	int iBlockToolLevel = block.GetEfficientToolLevel( world, i, j, k ); 
    	
    	if ( iBlockToolLevel > iToolLevel )
    	{
        	return 1.0F;
    	}

    	return super.getStrVsBlock( toolItemStack, world, block, i, j, k );
    }

    @Override
    public boolean IsEfficientVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	int iToolLevel = toolMaterial.getHarvestLevel();
    	int iBlockToolLevel = block.GetEfficientToolLevel( world, i, j, k ); 
    	
    	if ( iBlockToolLevel > iToolLevel )
    	{
        	return false;
    	}    	
    	
    	if ( block.GetIsProblemToRemove( world,  i, j, k ) )
    	{
    		// stumps and such
    		
			return false;
    	}
    	
    	return super.IsEfficientVsBlock( stack, world, block, i, j, k );
    }
    
	@Override
	protected boolean IsToolTypeEfficientVsBlockType(Block block)
	{
		return false;
	}
	
    @Override
    public boolean IsConsumedInCrafting()
    {
    	return true;
//    	return toolMaterial.getHarvestLevel() <= 2; // wood, stone, gold & iron
    }
    
    @Override
    public boolean IsDamagedInCrafting()
    {
    	return true;
//    	return toolMaterial.getHarvestLevel() <= 2; // wood, stone, gold & iron
    }
    
    @Override
    public void OnUsedInCrafting( EntityPlayer player, ItemStack outputStack )
    {
		PlayChopSoundOnPlayer( player );
    }
    
    @Override
    public void OnBrokenInCrafting( EntityPlayer player )
    {
    	PlayBreakSoundOnPlayer( player );
    }
    
    static public void PlayChopSoundOnPlayer( EntityPlayer player )
    {
		if ( player.m_iTimesCraftedThisTick == 0 )
		{
			// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
			
			//player.playSound( "mob.zombie.wood", 0.5F, 2.5F );
			player.playSound( "mob.sheep.shear", 0.5F, 2.5F );
		}
    }
    
    static public void PlayBreakSoundOnPlayer( EntityPlayer player )
    {
		// note: the playSound function for player both plays the sound locally on the client, and plays it remotely on the server without it being sent again to the same player
    	
		player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );
    }

}
