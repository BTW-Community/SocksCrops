package net.minecraft.src;

import java.util.Random;

public class SocksCropsBlockSalad extends BlockCrops
{
    private Icon[] iconArray;

	public SocksCropsBlockSalad(int var1)
    {
        super(var1);
        
    }

	public float GetBaseGrowthChance(World var1, int var2, int var3, int var4)
    {
        return 20.1F;
    }

    protected void IncrementGrowthLevel(World var1, int var2, int var3, int var4)
    {
        int var5 = this.GetGrowthLevel(var1, var2, var3, var4) + 1;

        if (var5 != 7 && (var5 & 1) != 0)
        {
            this.SetGrowthLevelNoNotify(var1, var2, var3, var4, var5);
        }
        else
        {
            super.IncrementGrowthLevel(var1, var2, var3, var4);
        }
    }

    public ItemStack GetStackRetrievedByBlockDispenser(World var1, int var2, int var3, int var4)
    {
        return var1.getBlockMetadata(var2, var3, var4) >= 7 ? super.GetStackRetrievedByBlockDispenser(var1, var2, var3, var4) : null;
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        if (par2 < 7)
        {
            if (par2 == 6)
            {
                par2 = 5;
            }

            return this.iconArray[par2 >> 1];
        }
        else
        {
            return this.iconArray[3];
        }
    }

    /**
     * Generate a seed ItemStack for this crop.
     */
    protected int getSeedItem()
    {
        return SocksCropsDefs.saladSeeds.itemID;
    }

    /**
     * Generate a crop produce ItemStack for this crop.
     */
    protected int getCropItem()
    {
        return SocksCropsDefs.salad.itemID;
    }
    
    public boolean CanWeedsGrowInBlock(IBlockAccess var1, int var2, int var3, int var4)
    {
        return true;
    }
    
    /**
     * Returns the quantity of items to drop on block destruction.
     */
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[4];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon("SocksCropsBlockSalad_" + var2);
        }
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	
    	renderer.renderBlockSalad(this, i, j, k);
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );

    	return true;
    }  
   
}
