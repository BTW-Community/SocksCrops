package net.minecraft.src;

public class SCBlockCropWildCarrotHighYield extends SCBlockCropWildCarrot {

	public SCBlockCropWildCarrotHighYield(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildCarrotHighYield");
	}
	
	@Override
	protected int GetSeedItemID() {
		return 0; // SCDefs.wildCarrotCropSapling.blockID;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float fChance,
			int iFortuneModifier) {
		int growthLevel = GetGrowthLevel(meta);

		int iNumDropped = 0;

		if (growthLevel >= 6) {
			iNumDropped = 1;
			
			int random = world.rand.nextInt(4);
			
			if (random <= 1) iNumDropped = 2;
			else if (random == 2) iNumDropped = 3;
		}

		for (int iTempCount = 0; iTempCount < iNumDropped; ++iTempCount) {
			dropBlockAsItem_do(world, i, j, k, new ItemStack(GetCropItemID(), 1, 0));
		}
		
		if ( growthLevel == 7 )
    	{
    		DropSeeds( world, i, j, k, 0 );
    	}
	}
	
	//------------ Client Side Functionality ----------//
    
    
	private Icon[] iconArray;
	private Icon[] iconArrayCenter;
	
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
        else if (growthLevel == 7) return this.iconArray[3];
        else return this.iconArray[3];
    }
	
	public Icon getIconCenter(int par1, int meta)
    {
		int growthLevel = GetGrowthLevel(meta);
		
        if (growthLevel == 0) return this.iconArrayCenter[0];
        else if (growthLevel == 1) return this.iconArrayCenter[0];
        else if (growthLevel == 2) return this.iconArrayCenter[1];
        else if (growthLevel == 3) return this.iconArrayCenter[1];
        else if (growthLevel == 4) return this.iconArrayCenter[2];
        else if (growthLevel == 5) return this.iconArrayCenter[2];
        else if (growthLevel == 6) return this.iconArrayCenter[3];
        else if (growthLevel == 7) return this.iconArrayCenter[4];
        else return this.iconArrayCenter[3];
    }
	
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.iconArray = new Icon[4];
        this.iconArrayCenter = new Icon[5];

        for (int var2 = 0; var2 < this.iconArray.length; ++var2)
        {
            this.iconArray[var2] = par1IconRegister.registerIcon(this.getUnlocalizedName2() + "_" + var2);
           
        }
        
        for (int var2 = 0; var2 < this.iconArrayCenter.length; ++var2)
        {
            this.iconArrayCenter[var2] = par1IconRegister.registerIcon("SCBlockCropWildCarrot_" + var2);
           
        }
    }
	
	@Override
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, x, y, z));
        
        FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, x, y, z );
 
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        
        renderer.renderBlockCrops(this, x, y, z);
        
        renderer.setOverrideBlockTexture(getIconCenter(0, meta));
        renderer.renderCrossedSquares(this, x, y, z);
        renderer.clearOverrideBlockTexture();
        
    	return true;
    }
	

}
