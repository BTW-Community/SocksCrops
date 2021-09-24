package net.minecraft.src;

import java.util.Random;

public class SocksCropsBlockRiceCrop extends BlockCrops
{
	private Icon[] iconArray = new Icon[8];

	public SocksCropsBlockRiceCrop(int var1)
    {
        super(var1);
        
    }
	
	/**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int var1, Random var2, int var3)
    {
        return var1 >= 7 ? this.getCropItem() : 0;
    }

	/**
     * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
     * blockID passed in. Args: blockID
     */
	@Override
    protected boolean CanGrowOnBlock(World var1, int var2, int var3, int var4)
    {
        
		
		return true;
    }
    
    /**
     * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
     */
	@Override
    public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return par3 < 256 ? par1World.getBlockMaterial(par2, par3 - 1, par4) == Material.water : false;
    }
	@Override
    public float GetBaseGrowthChance(World var1, int var2, int var3, int var4)
    {
        return 10.0F;
    }
	
	/**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!this.canBlockStay(par1World, par2, par3, par4) && par1World.getBlockId(par2, par3 - 1, par4) == Block.dirt.blockID)
        {
            par1World.destroyBlock(par2, par3, par4, true);
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
            	
                if ( blockBelow != null && this.GetWeedsGrowthLevel(world, i, j, k) == 0)
                {
                    int iMetadata = world.getBlockMetadata( i, j, k );
                    
    	            if ( GetGrowthLevel( world, i, j, k ) < 7 )
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
     * Generate a seed ItemStack for this crop.
     */
    protected int getSeedItem()
    {
        return SocksCropsDefs.rice.itemID;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    protected int getCropItem()
    {
        return SocksCropsDefs.rice.itemID;
    }
    
    @Override
    public boolean CanWeedsGrowInBlock(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return this.iconArray[var2];
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    @Override
    public void registerIcons( IconRegister register )
    {
    	iconArray[0] = register.registerIcon( "SocksCropsBlockRice_0" );
    	iconArray[1] = register.registerIcon( "SocksCropsBlockRice_1" );
    	iconArray[2] = register.registerIcon( "SocksCropsBlockRice_2" );
    	iconArray[3] = register.registerIcon( "SocksCropsBlockRice_3" );
    	iconArray[4] = register.registerIcon( "SocksCropsBlockRice_4" );
    	iconArray[5] = register.registerIcon( "SocksCropsBlockRice_5" );
    	iconArray[6] = register.registerIcon( "SocksCropsBlockRice_6" );
    	iconArray[7] = register.registerIcon( "SocksCropsBlockRice_7" );

    }
    
    public boolean RenderBlock(RenderBlocks var1, int var2, int var3, int var4)
    {
        var1.setRenderBounds(this.GetBlockBoundsFromPoolBasedOnState(var1.blockAccess, var2, var3, var4));
 
        FCBetterThanWolves.fcBlockWeeds.RenderWeeds(this, var1, var2, var3, var4);

        var1.renderBlockCrops(this, var2, var3, var4);

        var1.renderBlockCrops(SocksCropsDefs.riceRoots, var2, var3 - 1, var4);
        return true;
    }
}
