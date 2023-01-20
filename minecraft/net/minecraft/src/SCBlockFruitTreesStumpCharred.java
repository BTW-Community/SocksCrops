package net.minecraft.src;

public class SCBlockFruitTreesStumpCharred extends FCBlockStumpCharred {

	public SCBlockFruitTreesStumpCharred(int iBlockID) {
		super(iBlockID);
		
		setUnlocalizedName( "fcBlockStumpCharred" );
	}
	
    @Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return true;
    }
    
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	int iOldMetadata = world.getBlockMetadata( i, j, k );
    	int iDamageLevel = GetDamageLevel( iOldMetadata );
    	
    	if ( iDamageLevel < 3 )
    	{
    		iDamageLevel++;

    		SetDamageLevel( world, i, j, k, iDamageLevel );
    		
    		return true;
    	}
        
    	return false;
    }
    
    private FCModelBlock m_blockModelsNarrowOneSide[];

    private final static float m_fRimWidth = ( 4F / 16F );
    
    private final static float m_fLayerHeight = ( 2F / 16F );    
    private final static float m_fFirstLayerHeight = ( 3F / 16F );    
    private final static float m_fLayerWidthGap = ( 1F / 16F );
    
    private final static float logWidth = ( 8F / 16F );
    
    
    
    public FCModelBlock GetCurrentModelForBlock( IBlockAccess blockAccess, int i, int j, int k )
    {
    	int iDamageLevel = GetDamageLevel( blockAccess, i, j, k );
    	
		return m_blockModelsNarrowOneSide[iDamageLevel];
    }
    
    protected void InitModels()
    {
        m_blockModelsNarrowOneSide = new FCModelBlock[4];

        // center colum
        
        for ( int iTempIndex = 0; iTempIndex < 4; iTempIndex++ )
        {
        	FCModelBlock tempNarrowOneSide = m_blockModelsNarrowOneSide[iTempIndex] = new FCModelBlock();
        	
            float fCenterColumnWidthGap = m_fRimWidth + ( m_fLayerWidthGap * iTempIndex );
            float fCenterColumnHeightGap = 0F;
            
            if ( iTempIndex > 0 )
            {
            	fCenterColumnHeightGap = m_fFirstLayerHeight + ( m_fLayerHeight * ( iTempIndex - 1 ) );
            }

            tempNarrowOneSide.AddBox( fCenterColumnWidthGap, fCenterColumnHeightGap, fCenterColumnWidthGap, 
            	1F - fCenterColumnWidthGap, 1F, 1F - fCenterColumnWidthGap );
        }
        
        // first layer
        
        for ( int iTempIndex = 1; iTempIndex < 4; iTempIndex++ )
        {
	        m_blockModelsNarrowOneSide[iTempIndex].AddBox( m_fRimWidth, 0, m_fRimWidth, 1F - m_fRimWidth, m_fFirstLayerHeight, 1F - m_fRimWidth );
        }
        
        // second layer 
        
        float fWidthGap = m_fRimWidth + m_fLayerWidthGap;
        float fHeightGap = m_fFirstLayerHeight;
        
        for ( int iTempIndex = 2; iTempIndex < 4; iTempIndex++ )
        {
	        m_blockModelsNarrowOneSide[iTempIndex].AddBox( fWidthGap, fHeightGap, fWidthGap, 1F - fWidthGap, fHeightGap + m_fLayerHeight, 1F - fWidthGap );
        }
        
    	// third layer
        
        fWidthGap = m_fRimWidth + ( m_fLayerWidthGap * 2 );
        fHeightGap = m_fFirstLayerHeight + m_fLayerHeight;
        
        m_blockModelsNarrowOneSide[3].AddBox( fWidthGap, fHeightGap, fWidthGap, 1F - fWidthGap, fHeightGap + m_fLayerHeight, 1F - fWidthGap );
    }
    
	//----------- Client Side Functionality -----------//
    
    @Override
    public boolean isOpaqueCube() {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
    		int iSide) {
    	// TODO Auto-generated method stub
    	return true;
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	return GetCurrentModelForBlock( renderBlocks.blockAccess, i, j, k ).RenderAsBlock( 
    		renderBlocks, this, i, j, k );
    }

    @Override
    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
    {
    	m_blockModelsNarrowOneSide[iItemDamage].RenderAsItemBlock( renderBlocks, this, iItemDamage );
    }
    
}
