package net.minecraft.src;

import java.util.Random;

public class SCBlockPieCooked extends SCBlockPieBase {

	public static final int subtypePumpkin = 0;
	public static final int subtypeSweetberry = 4;
	public static final int subtypeBlueberry = 8;
	
	protected SCBlockPieCooked(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockPieCooked");
	}
	
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {        
    	ItemStack heldStack = player.getHeldItem();
        
        if (heldStack == null)
        {
        	 EatCakeSliceLocal( world, i, j, k, player );
        	 return true;
        }
        else if (heldStack.getItem() instanceof SCItemKnife)
        {
        	cutPie(world, i, j, k, player);
        	world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.5F, this.stepSound.getPitch() * 0.3F );
        	
            heldStack.func_96631_a(1, world.rand); //attemptDamageItem
            
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
    
    private void cutPie(World world, int i, int j, int k, EntityPlayer player)
    {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

    	int sliceID = 0;
    	int meta =  world.getBlockMetadata(i, j, k);
    	
    	if (getType(meta) == subtypePumpkin)
    		sliceID = SCDefs.pumpkinPieSlice.itemID;
    	
    	else if (getType(meta) == subtypeSweetberry)
    		sliceID = SCDefs.sweetberryPieSlice.itemID;
    	
    	else if (getType(meta) == subtypeBlueberry)
    		sliceID = SCDefs.blueberryPieSlice.itemID;
    	
        if(!world.isRemote)
        {
        	FCUtilsItem.DropSingleItemAsIfBlockHarvested(world, i, j, k, sliceID, 0);
        }
        
        if ( iEatState >= 4 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            SetEatState( world, i, j, k, iEatState );

        }
	}

	protected void EatCakeSliceLocal( World world, int i, int j, int k, EntityPlayer player )
    {
    	// this function is necessary due to eatCakeSlice() in parent being private
    	
        if ( player.canEat( true ) || player.capabilities.isCreativeMode )
        {
        	// food value adjusted for increased hunger meter resolution
        	
//            player.getFoodStats().addStats( 2, 2.5F );
            player.getFoodStats().addStats( 1, 4F );
            
            int newEatState = GetEatState( world, i, j, k ) + 1 ;

            if ( newEatState >= 4 )
            {
                world.setBlockWithNotify( i, j, k, 0 );
            }
            else
            {
                SetEatState( world, i, j, k, newEatState );
            }
        }
    	else
    	{
    		player.OnCantConsume();
    	}
    }
    
    public int GetEatState( int meta )
    {
    	return meta & 3;
    }
    
    
    public int getType( int meta )
    {
    	if (meta < 4) return subtypePumpkin; //Pumpkin
    	else if (meta >= 4 && meta < 8) return subtypeSweetberry; //Sweetberry
    	else if (meta >= 8 && meta < 12) return subtypeBlueberry; //Blueberry
    	else return 12;
    }
    
    public int getTypeForRender( int meta )
    {
    	if (meta < 4) return 0; //Pumpkin
    	else if (meta < 8) return 1; //Sweetberry
    	else if (meta < 12) return 2; //Blueberry
    	else return 3;
    }
    
    public int GetEatState( IBlockAccess iBlockAccess, int i, int j, int k )
    {
    	return GetEatState( iBlockAccess.getBlockMetadata( i, j, k ) );
    }
    
    public void SetEatState( World world, int i, int j, int k, int newState )
    {
    	int iMetaData = world.getBlockMetadata( i, j, k );
    	
		iMetaData = iMetaData + 1;
		
        world.setBlockMetadataWithNotify( i, j, k, iMetaData );
    }
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier) {
		
		if (GetEatState(meta) == 0)
			if (getType(meta) == subtypePumpkin) return Item.pumpkinPie.itemID;
			else if (getType(meta) == subtypeSweetberry) return SCDefs.sweetberryPieCooked.itemID;
			else if (getType(meta) == subtypeBlueberry) return SCDefs.blueberryPieCooked.itemID;
			else return 0;
		else return 0;
	}
}
