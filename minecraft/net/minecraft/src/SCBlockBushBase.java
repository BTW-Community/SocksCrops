package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockBushBase extends BlockFlower {

	protected SCBlockBushBase(int blockID)
	{
		super(blockID);
        this.setHardness( 0.1F );
        this.SetAxesEffectiveOn( true );        
        this.SetBuoyant();        
        this.SetFireProperties( FCEnumFlammability.CROPS );
        this.setTickRandomly(true);
        this.setStepSound(soundGrassFootstep);
        this.setCreativeTab(null);
	}
	
    private float getBaseGrowthChance()
    {
		return 0.05F;
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!world.isRemote && rand.nextFloat() <= getBaseGrowthChance() && world.getBlockMetadata(i, j, k) < 5)
		{
			world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) + 1);
		}

	}

	
//    protected void AttemptToGrow( World world, int i, int j, int k, Random rand )
//    {
//    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 && 
//    		GetGrowthLevel( world, i, j, k ) < 7 && 
//	    	world.getBlockLightValue( i, j + 1, k ) >= 9 )
//	    {
//	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
//	        
//	        if ( blockBelow != null && 
//	        	blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
//	        {
//	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
//	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
//	    		
//	            if ( rand.nextFloat() <= fGrowthChance )
//	            {
//	            	IncrementGrowthLevel( world, i, j, k );
//	            }
//	        }
//	    }
//    }
	
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack playerEquippedItem = player.getCurrentEquippedItem();
    	
        if ( !world.isRemote && world.getBlockMetadata(i, j, k) == 5 && (playerEquippedItem == null || playerEquippedItem.itemID == getBerryID() )) // must have empty hand to rc
        {        	
        	int amount = world.rand.nextInt(2) + 1 ;
	        // click sound	        
            world.playAuxSFX( 2001, i, j, k, this.blockID );
            FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, 
            		new ItemStack( getBerryID(), amount, 0 ) );
            world.setBlock(i, j, k, this.blockID);
            world.setBlockMetadataWithNotify(i, j, k, 1);
            
            return true;
        }
        
        return false;
    }
    
    @Override
    public void harvestBlock( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	int amount = world.rand.nextInt(2) + 1 ;
		
        if( !world.isRemote )
        {
        	// kill the plant if not harvested by shears
        	
        	if ( player.getCurrentEquippedItem() == null ||
        		player.getCurrentEquippedItem().itemID != Item.shears.itemID )
        	{   
        		if ( iMetadata == 5 )
	            {
        			FCUtilsItem.DropStackAsIfBlockHarvested(world,i,j,k, new ItemStack(this.getBerryID(), amount, 0));
        			world.setBlockToAir( i, j , k );
	            }
        	}
        	else if (player.getCurrentEquippedItem().itemID == Item.shears.itemID )
            {        		
        		if ( iMetadata == 5 )
	            {
        			this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.getBerryID(), amount, 0));
	            }
        		
        		this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.getSaplingID(), 1, 0));
        		world.setBlockToAir( i, j , k );    
            }
        }
        
    }
    
    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier)
    {         
         return 0;
    }
	

    
    protected abstract int getBerryID();
    
    protected abstract int getSaplingID();

    @Override
    public int idPicked(World world, int i, int j, int k)
    {
    	return getSaplingID();
    }
	
    @Override
    protected boolean CanGrowOnBlock(World var1, int var2, int var3, int var4)
    {
        Block var5 = Block.blocksList[var1.getBlockId(var2, var3, var4)];
        return var5 != null && var5.CanWildVegetationGrowOnBlock(var1, var2, var3, var4);
    }
    

}
