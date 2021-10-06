package net.minecraft.src;

public class SCBlockAppleCrop extends FCBlockCrops {

	protected SCBlockAppleCrop(int iBlockID) {
		super(iBlockID);
	}
	
	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.85F;
    }

	@Override
	protected int GetCropItemID() {
		return SCDefs.appleSapling.blockID;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}

	
	//Render
	
	private Icon[] m_iconArray;

    @Override
    public void registerIcons( IconRegister register )
    {
        m_iconArray = new Icon[8];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockAppleSaplingCrop_" + iTempIndex );
        }
        
        blockIcon = m_iconArray[7]; // for block hit effects and item render
   
    }

	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
    	int iGrowthLevel = GetGrowthLevel( blockAccess, i, j, k );
    	
    	iGrowthLevel = MathHelper.clamp_int( iGrowthLevel, 0, 7 );

        return m_iconArray[iGrowthLevel];
    }
	
	@Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
		IBlockAccess blockAccess = renderer.blockAccess;
		int iGrowthLevel = GetGrowthLevel( blockAccess, i, j, k );
		
    	renderer.renderCrossedSquares(this,i, j, k);
    	
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
		
    	return true;
    }

}
