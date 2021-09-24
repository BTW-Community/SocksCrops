package net.minecraft.src;

import java.util.Random;

public class SocksCropsBlockBushBerry extends BlockCrops {
	
	private Icon[] iconArray = new Icon[5];
	
    protected SocksCropsBlockBushBerry(int iBlockID)
    {
        super(iBlockID);
        this.setHardness( 0.2F );
        this.SetAxesEffectiveOn( true );        
        this.SetBuoyant();        
        this.SetFireProperties( FCEnumFlammability.CROPS );
        float var3 = 0.2F;
        this.InitBlockBounds((double)(0.5F - var3), 0.0D, (double)(0.5F - var3), (double)(0.5F + var3), (double)(var3 * 3.0F), (double)(0.5F + var3));
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setUnlocalizedName( "SocksCropsBlockBushBerry" );
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int iSide, int iMetadata)
    {
    	return iconArray[iMetadata];
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons( IconRegister register )
    {
    	iconArray[0] = register.registerIcon( "SocksCropsBlockBushBerry_0" );
    	iconArray[1] = register.registerIcon( "SocksCropsBlockBushBerry_1" );
    	iconArray[2] = register.registerIcon( "SocksCropsBlockBushBerry_2" );
    	iconArray[3] = register.registerIcon( "SocksCropsBlockBushBerry_3" );
    	iconArray[4] = register.registerIcon( "SocksCropsBlockBushBerry_4" );

    }
    
    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 6;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier)
    {
    	 if ( iMetadata >= 4 )
         {
             return getCropItem();
             
         }
         
         return 0;
    }
    
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	ItemStack playerEquippedItem = player.getCurrentEquippedItem();
    	
    	if ( playerEquippedItem != null )
    	{
    		return false;
    	}
    	if ( world.getBlockMetadata( i, j, k ) < 4)
    	{
    		return false;
    	}
        if ( !world.isRemote ) // must have empty hand to rc
        {
	        // click sound	        
            world.playAuxSFX( 2001, i, j, k, blockID );
            FCUtilsItem.DropStackAsIfBlockHarvested( world, i, j, k, 
            		new ItemStack( getCropItem(), 1, 0 ) );
            world.setBlock(i, j, k, SocksCropsDefs.berryBush.blockID);
            world.setBlockMetadata(i, j, k, 1);
            
        }
        
        return true;
    }
    
    /**
     * Generate a crop produce ItemStack for this crop.
     */
    
    protected int getCropItem()
    {
        return SocksCropsDefs.berries.itemID;
    }
    
    protected int getSeedItem()
    {
        return SocksCropsDefs.berrySapling.itemID;
    }
        
    @Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.2F;
    }
    
    @Override
    public void DropSeeds( World world, int i, int j, int k, int iMetadata, 
    	float fChance, int iFortuneModifier)
    {
             this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.getSeedItem(), 1, 0));
    }
    
    @Override
    public void harvestBlock( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	
		
        if( !world.isRemote )
        {
        	// kill the plant if not harvested by shears
        	
        	if ( player.getCurrentEquippedItem() == null ||
        		player.getCurrentEquippedItem().itemID != Item.shears.itemID )
        	{   
        		if ( GetGrowthLevel( world, i, j, k ) == 4 )
	            {
        			FCUtilsItem.DropStackAsIfBlockHarvested(world,i,j,k, new ItemStack(this.getCropItem(), 1, 0));
        			world.setBlockToAir( i, j , k );
	            }
        	}
        	else if (player.getCurrentEquippedItem().itemID == Item.shears.itemID )
            {        		
        		if ( GetGrowthLevel( world, i, j, k ) >= 4 )
	            {
        			this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.getCropItem(), 1, 0));
	            }
        		else {
        			this.dropBlockAsItem_do(world, i, j, k, new ItemStack(this.getSeedItem(), 1, 0));
            		world.setBlockToAir( i, j , k );            		
        		}
            }
        }
        
    }
    
    @Override
    protected void AttemptToGrow( World world, int i, int j, int k, Random rand )
    {
    	if((world.getBlockLightValue( i, j, k ) >= 10 ||
    		world.getBlockId( i, j + 1, k ) == FCBetterThanWolves.fcLightBulbOn.blockID || 
        	world.getBlockId( i, j + 2, k ) == FCBetterThanWolves.fcLightBulbOn.blockID ) )
            {
                Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];            
            	
                if ( blockBelow != null )
                {
                    int iMetadata = world.getBlockMetadata( i, j, k );
                    
    	            if ( GetGrowthLevel( world, i, j, k ) < 4 )
    	            {
    	            	float fChanceOfGrowth = GetBaseGrowthChance( world, i, j, k );
    	            	
    	                if ( rand.nextFloat() <= fChanceOfGrowth )
    	                {
    		            	IncrementGrowthLevel( world, i, j, k );
    	                }
    	            }
    	        }
            }
        }
    
    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.getSeedItem();
    }

    /**
    public boolean CanBeGrazedOn(IBlockAccess var1, int var2, int var3, int var4, EntityAnimal var5)
    {
        return true;
    }

    public void OnGrazed(World var1, int var2, int var3, int var4, EntityAnimal var5)
    {
        this.dropBlockAsItem(var1, var2, var3, var4, var1.getBlockMetadata(var2, var3, var4), 0);
        super.OnGrazed(var1, var2, var3, var4, var5);
    }
     */
    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    protected boolean CanGrowOnBlock(World var1, int var2, int var3, int var4)
    {
        Block var5 = Block.blocksList[var1.getBlockId(var2, var3, var4)];
        return var5 != null && var5.CanReedsGrowOnBlock(var1, var2, var3, var4);
    }

    public boolean CanWeedsGrowInBlock(IBlockAccess var1, int var2, int var3, int var4)
    {
        return false;
    }
    
}
