package net.minecraft.src;

import java.util.Random;

public class SCBlockPieCooked extends SCBlockPieBase {

	public static final int pumpkin = 0;
	public static final int sweetberry = 4;
	public static final int blueberry = 8;
	
	protected SCBlockPieCooked(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockPieCooked");
	}
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{
		if (GetEatState(meta) == 0)
		{
			if (getType(meta) == pumpkin) return Item.pumpkinPie.itemID;
			else if (getType(meta) == sweetberry) return SCDefs.sweetberryPieCooked.itemID;
			else if (getType(meta) == blueberry) return SCDefs.blueberryPieCooked.itemID;
			else return 0;
		}
		return 0;
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
    
    protected void cutPie(World world, int i, int j, int k, EntityPlayer player)
    {
        int iEatState = GetEatState( world, i, j, k ) + 1; 

    	int sliceID = 0;
    	int meta =  world.getBlockMetadata(i, j, k);
    	
    	sliceID = getSliceItem(sliceID, meta);
    	
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

	protected int getSliceItem(int sliceID, int meta) {
		if (getType(meta) == pumpkin)
    		sliceID = SCDefs.pumpkinPieSlice.itemID;
    	
    	else if (getType(meta) == sweetberry)
    		sliceID = SCDefs.sweetberryPieSlice.itemID;
    	
    	else if (getType(meta) == blueberry)
    		sliceID = SCDefs.blueberryPieSlice.itemID;
		return sliceID;
	}

	protected void EatCakeSliceLocal( World world, int i, int j, int k, EntityPlayer player )
    {
    	// this function is necessary due to eatCakeSlice() in parent being private
    	
        if ( player.canEat( true ) || player.capabilities.isCreativeMode )
        {
        	// food value adjusted for increased hunger meter resolution
        	
            player.getFoodStats().addStats( 3, 1.25F);
            
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
    	if (meta < 4) return pumpkin; //Pumpkin
    	else if (meta >= 4 && meta < 8) return sweetberry; //Sweetberry
    	else if (meta >= 8 && meta < 12) return blueberry; //Blueberry
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

	
	//----------- Client Side Functionality -----------//
    protected double border = 2/16D;
    protected double height = 4/16D;
    protected double width = 12/16D;
}
