package net.minecraft.src;

import java.util.Random;

public class SCBlockCropWildCarrot extends FCBlockCarrotBase {

	public SCBlockCropWildCarrot(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildCarrot");
	}
	
    @Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.33F;
    }

	@Override
	protected int GetCropItemID() {
		return SCDefs.wildCarrot.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildCarrotSeeds.itemID;
	}
	
	
    //------------- Class Specific Methods ------------//
	
    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata,	float fChance, int iFortuneModifier )
    {
    	
        if ( !world.isRemote && GetGrowthLevel(iMetadata) >= 6 )
        {
        	DropCrop(world, i, j, k, iMetadata);
        	
        	if ( GetGrowthLevel(iMetadata) == 7 )
        	{
        		DropSeeds( world, i, j, k, iMetadata );
        	}
        }
    }
    
    public void DropSeeds( World world, int i, int j, int k, int iMetadata )
    {
    	int iSeedItemID = GetSeedItemID();
    	
    	if ( iSeedItemID > 0 )
    	{
	        dropBlockAsItem_do( world, i, j, k, new ItemStack( iSeedItemID, 1, 0 ) );
    	}
    }
    
    public void DropCrop( World world, int i, int j, int k, int iMetadata )
    {
    	int iCropItemID = GetCropItemID();
    	
    	if ( iCropItemID > 0 )
    	{
	        dropBlockAsItem_do( world, i, j, k, new ItemStack( iCropItemID, 1, 0 ) );
    	}
    }
    
    @Override
    public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
    {
    	return 0;
    }
    
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) || world.getBlockId(i, j - 1, k) == Block.grass.blockID; 
    }
	
    @Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k );
    }
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	int iMetadata = world.getBlockMetadata(i, j, k);
    	
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
        	if ( world.provider.dimensionId != 1)
        	{        	
    	        if (GetGrowthLevel( iMetadata ) < 6 )
    	        {
    	        	AttemptToGrow( world, i, j, k, rand );
    	        	//System.out.println("Trying to grow: " + GetGrowthLevel( iMetadata ));
    	        	//System.out.println("meta: " + iMetadata );
    	        }
    	        else if (GetGrowthLevel( iMetadata ) == 6 )
    	        {
    	        	if (world.getBlockId(i, j + 2, k) == FCBetterThanWolves.fcLightBulbOn.blockID ||
    	        			world.getBlockId(i, j + 1, k) == FCBetterThanWolves.fcLightBulbOn.blockID)
    	        	{
    	        		//System.out.println("Trying to flower: " + GetGrowthLevel( iMetadata ));
    	        		//System.out.println("meta: " + iMetadata );
    	        		
    	        		AttemptToGrow( world, i, j, k, rand );
    	        		
    	        	}
    	        	
    	        }
        		
        	}

        }
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 6;
    }
	
	//------------ Client Side Functionality ----------//
    
    
	private Icon[] iconArray;
	
	public Icon getIcon(int par1, int meta)
    {
		int growthLevel = GetGrowthLevel(meta);
		
        if (growthLevel == 0) return this.iconArray[0];
        else if (growthLevel == 1) return this.iconArray[0];
        else if (growthLevel == 2) return this.iconArray[1];
        else if (growthLevel == 3) return this.iconArray[1];
        else if (growthLevel == 4) return this.iconArray[2];
        else if (growthLevel == 5) return this.iconArray[2];
        else if (growthLevel == 6) return this.iconArray[3];
        else if (growthLevel == 7) return this.iconArray[4];
        else return this.iconArray[3];
    }
	
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[5];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(this.getUnlocalizedName2() + "_" + var2);
        }
    }
    
	@Override
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, x, y, z));
        
        FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, x, y, z );
        
    	return renderer.renderCrossedSquares(this, x, y, z);
    }

}
