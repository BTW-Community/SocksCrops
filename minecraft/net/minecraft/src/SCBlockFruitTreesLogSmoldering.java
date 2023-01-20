package net.minecraft.src;

public class SCBlockFruitTreesLogSmoldering extends FCBlockLogSmouldering {

	protected SCBlockFruitTreesLogSmoldering(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName( "fcBlockLogSmouldering" );
	}
	protected boolean IsSupportedBySolidBlocks( World world, int i, int j, int k )
	{
		Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];

		return blockBelow != null && ( blockBelow.HasLargeCenterHardPointToFacing( world, i, j - 1, k, 1, false ) || blockBelow instanceof SCBlockFruitTreesLog );
	}
	
	public boolean GetIsStump( int iMetadata )
	{
		return ( iMetadata & 8 ) != 0;
	}

	public static int setIsStump( int iMetadata, boolean bStump )
	{
		if ( bStump )
		{
			iMetadata |= 8;
		}
		else
		{
			iMetadata &= ~8;
		}

		return iMetadata;
	}
	
	@Override
	public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
	{
		if ( GetIsStump( world.getBlockMetadata( i, j, k ) ) )
		{
			world.setBlockWithNotify( i, j, k, SCDefs.fruitStumpCharred.blockID );

			return true;
		}   

		return false;
	}
	
	private void ConvertToCinders( World world, int i, int j, int k )
	{
		if ( GetIsStump( world, i, j, k ) )
		{
			int iNewMetadata = SCBlockFruitTreesLogCincers.setIsStump( 0, true );

			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.fruitLogCinders.blockID, iNewMetadata );						
		}
		else
		{
			world.setBlockWithNotify( i, j, k, SCDefs.fruitLogCinders.blockID );
		}
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
	
	private Icon m_iconEmbers;

	@Override
	public void registerIcons( IconRegister register )
	{
		super.registerIcons( register );

		m_iconEmbers = register.registerIcon( "fcOverlayLogEmbers" );
	}

	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		float minx = 0;
		float miny = 0;
		float minz = 0;
		float maxx = 1;
		float maxy = 1;
		float maxz = 1;
		
		int meta = blockAccess.getBlockMetadata(i, j, k) ;
		
		minx = 4/16F;
		maxx = 12/16F;
		minz = 4/16F;
		maxz = 12/16F;
		
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
	
	@Override
	public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
	{
//		renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );
		renderer.setRenderBounds( 4/16D, 0D, 4/16D, 12/16D, 1D, 12/16D );

		renderer.renderBlockLog( this, i, j, k );
		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
    	int blockMinI = renderer.blockAccess.getBlockId(i - 1, j, k);
    	int blockMaxI = renderer.blockAccess.getBlockId(i + 1, j, k);
    	int blockMinJ = renderer.blockAccess.getBlockId(i, j - 1, k);
    	int blockMaxJ = renderer.blockAccess.getBlockId(i, j + 1, k);    	
    	int blockMinK = renderer.blockAccess.getBlockId(i, j, k - 1);
    	int blockMaxK = renderer.blockAccess.getBlockId(i, j, k + 1);
    	int metaMinI = renderer.blockAccess.getBlockMetadata(i - 1, j, k);
    	int metaMaxI = renderer.blockAccess.getBlockMetadata(i + 1, j, k);
    	int metaMinJ = renderer.blockAccess.getBlockMetadata(i, j - 1, k);
    	int metaMaxJ = renderer.blockAccess.getBlockMetadata(i, j + 1, k);    	
    	int metaMinK = renderer.blockAccess.getBlockMetadata(i, j, k - 1);
    	int metaMaxK = renderer.blockAccess.getBlockMetadata(i, j, k + 1);
    	
    	if (true)
    	{
        	        	
        	renderBranches(renderer, SCDefs.fruitBranch.blockID,
        			i, j, k,
        			blockMinI, blockMaxI,
        			blockMinJ, blockMaxJ,
        			blockMinK, blockMaxK,
        			metaMinI, metaMaxI,
        			metaMinJ, metaMaxJ,
        			metaMinK, metaMaxK,
        			meta);
    	}
    	
    	return true;
	}

	 protected void renderBranches(RenderBlocks renderer, int blockID, int i, int j, int k,
	    		int blockMinI, int blockMaxI,
	    		int blockMinJ, int blockMaxJ,
	    		int blockMinK, int blockMaxK,
	    		int metaMinI, int metaMaxI,
	    		int metaMinJ, int metaMaxJ,
				int metaMinK, int metaMaxK,
				int meta)
	    {
		 if (blockMinI == blockID)
 		{
 			renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D );
 			renderer.renderBlockLog(this, i, j, k);
 		}
 		
 		if (blockMaxI == blockID )
 		{
 			renderer.setRenderBounds(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
 			renderer.renderBlockLog(this, i, j, k);
 		}
 		
 		if (blockMinK == blockID )
 		{
 			renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D );
 			renderer.renderBlockLog(this, i, j, k);
 		}
 		
 		if (blockMaxK == blockID )
 		{
 			renderer.setRenderBounds( 6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D );
 			renderer.renderBlockLog(this, i, j, k);
 		}
		}
	    

	@Override
	public void RenderBlockSecondPass( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
	{
		FCClientUtilsRender.RenderBlockFullBrightWithTexture( renderBlocks, renderBlocks.blockAccess, i, j, k, m_iconEmbers );
	}

	@Override
	public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
	{
		renderBlocks.renderBlockAsItemVanilla( this, iItemDamage, fBrightness );

		FCClientUtilsRender.RenderInvBlockFullBrightWithTexture( renderBlocks, this, -0.5F, -0.5F, -0.5F, m_iconEmbers );        
	}

	@Override
	public void RenderFallingBlock( RenderBlocks renderBlocks, int i, int j, int k, int iMetadata )
	{
		renderBlocks.SetRenderAllFaces( true );

//		renderBlocks.setRenderBounds( GetFixedBlockBoundsFromPool() );
		renderBlocks.setRenderBounds( 4/16D, 0D, 4/16D, 12/16D, 1D, 12/16D );

		renderBlocks.renderStandardBlock( this, i, j, k );

		FCClientUtilsRender.RenderBlockFullBrightWithTexture( renderBlocks, renderBlocks.blockAccess, i, j, k, m_iconEmbers );

		renderBlocks.SetRenderAllFaces( false );
	}
}
