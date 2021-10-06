package net.minecraft.src;

import java.util.Random;

public class SCBlockFenceRope extends FCBlockStakeString implements SCIRope {
	
	static public final double height = ( 2D / 16D );
	static public final double halfHeight = ( height / 2D );
	
	static public final double selectionBoxHeight = ( 2D / 16D );
	static public final double selectionBoxHalfHeight = ( selectionBoxHeight / 2D );
	
	public SCBlockFenceRope(int iBlockID) {
		super(iBlockID);
		
		this.setUnlocalizedName( "fcBlockRope_side" );  
	}
	
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
        return FCBetterThanWolves.fcItemRope.itemID;
    }
	
	@Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
        if ( !world.isRemote )
        {
	        for ( int iAxis = 0; iAxis < 3; iAxis++ )
	        {
	        	if ( GetExtendsAlongAxisFromMetadata( iMetadata, iAxis ) )
	        	{
	        		FCUtilsItem.DropSingleItemAsIfBlockHarvested( world, i, j, k, idDropped( iMetadata, world.rand, iFortuneModifier ), damageDropped( iMetadata ) );
	        	}        	
	        }
        }
    }
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, 
    	int iFacing, float fXClick, float fYClick, float fZClick )
    {		
		return false;
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
		this.ValidateState( world, i, j, k );
    }
	
	public void ValidateState( World world, int i, int j, int k )
	{
		int iValidAxisCount = 0;
		
		for ( int iTempAxis = 0; iTempAxis < 3; iTempAxis++ )
		{
			if ( GetExtendsAlongAxis( world, i, j, k, iTempAxis ) )
			{
				if ( this.HasValidAttachmentPointsAlongAxis( world, i, j, k, iTempAxis ) )
				{
					iValidAxisCount++;
				}
				else
				{
					SetExtendsAlongAxis( world, i, j, k, iTempAxis, false );
					
	        		FCUtilsItem.DropSingleItemAsIfBlockHarvested( world, i, j, k, FCBetterThanWolves.fcItemRope.itemID, 0 );
				}
			}
		}
		
		if ( iValidAxisCount <= 0 )
		{
			// we no longer have any valid axis, destroy the block
			
			world.setBlockWithNotify( i, j, k, 0 );
		}
	}
	
	public boolean HasValidAttachmentPointsAlongAxis( World world, int i, int j, int k, int iAxis )
	{
		int iFacing1;
		int iFacing2;
		
		switch ( iAxis )
		{
			case 0: // i
				
				iFacing1 = 4;
				iFacing2 = 5;
				
				break;
				
			case 1: // j
				
				iFacing1 = 0;
				iFacing2 = 1;
				
				break;
				
			default: // 2 k
				
				iFacing1 = 2;
				iFacing2 = 3;
				
				break;				
		}
		
		return this.HasValidAttachmentPointToFacing( world, i, j, k, iFacing1 ) && HasValidAttachmentPointToFacing( world, i, j, k, iFacing2 );
	}
	
	public boolean HasValidAttachmentPointToFacing( World world, int i, int j, int k, int iFacing )
	{
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
		
		targetPos.AddFacingAsOffset( iFacing );
		
		int iTargetBlockID = world.getBlockId( targetPos.i, targetPos.j, targetPos.k );
		
		if ( iTargetBlockID == blockID )
		{
			if ( GetExtendsAlongFacing( world, targetPos.i, targetPos.j, targetPos.k, iFacing ) )
			{
				return true;
			}
		}
		else if ( iTargetBlockID == SCDefs.fence.blockID )
		{
			return true;
		}
		else if ( Block.blocksList[world.getBlockId(targetPos.i, targetPos.j, targetPos.k)] instanceof SCIRope )
		{
			return true;
		}
		return false;
	}
	
	// BOX
	
	@Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, int i, int j, int k )
    {
		double minXBox = (double)i + 0.5D - selectionBoxHalfHeight;
		double minYBox = (double)j + 0.5D - selectionBoxHalfHeight;
		double minZBox = (double)k + 0.5D - selectionBoxHalfHeight;
		double maxXBox = (double)i + 0.5D + selectionBoxHalfHeight;
		double maxYBox = (double)j + 0.5D + selectionBoxHalfHeight;
		double maxZBox = (double)k + 0.5D + selectionBoxHalfHeight;
		
		if ( GetExtendsAlongAxis( world, i, j, k, 0 ) )
		{
			minXBox = (double)i;
			maxXBox = (double)i + 1D;
		}
		
		if ( GetExtendsAlongAxis( world, i, j, k, 1 ) )
		{
			minYBox = (double)j;
			maxYBox = (double)j + 1D;
		}
		
		if ( GetExtendsAlongAxis( world, i, j, k, 2 ) )
		{
			minZBox = (double)k;
			maxZBox = (double)k + 1D;
		}
		
        return AxisAlignedBB.getAABBPool().getAABB( minXBox, minYBox, minZBox, maxXBox, maxYBox, maxZBox );
    }
	
	private static void GetBlockBoundsForAxis( int iAxis, Vec3 min, Vec3 max, double dHalfHeight )
	{
		if ( iAxis == 0 )
		{
			min.setComponents( 0F, 0.5F - dHalfHeight, 0.5F - dHalfHeight );
			max.setComponents( 1F, 0.5F + dHalfHeight, 0.5F + dHalfHeight );
		}
		else if ( iAxis == 1 )
		{
			min.setComponents( 0.5F - dHalfHeight, 0F, 0.5F - dHalfHeight ); 
			max.setComponents( 0.5F + dHalfHeight, 1F, 0.5F + dHalfHeight );
		}
		else // 2
		{
			min.setComponents( 0.5F - dHalfHeight, 0.5F - dHalfHeight, 0F ); 
    		max.setComponents( 0.5F + dHalfHeight, 0.5F + dHalfHeight, 1F );
		}
	}
	
	public static void SetRenderBoundsForAxis( RenderBlocks renderBlocks, int iAxis )
	{
    	Vec3 min = Vec3.createVectorHelper( 0, 0, 0 );
    	Vec3 max = Vec3.createVectorHelper( 0, 0, 0 );
    	
    	GetBlockBoundsForAxis( iAxis, min, max, halfHeight );
    	
    	renderBlocks.setRenderBounds( (float)min.xCoord, (float)min.yCoord, (float)min.zCoord, (float)max.xCoord, (float)max.yCoord, (float)max.zCoord );    	
	}
	
	public static AxisAlignedBB GetVerticalRopeBounds( double length)
	{
		AxisAlignedBB stemBox = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - 0.125/2, -0.25D-(length-1D), 0.5D - 0.125/2, 
    		0.5D + 0.125/2, 0.5D, 0.5D + 0.125/2 );
		
		return stemBox;
	}
	
	public static AxisAlignedBB GetRopeKnotBounds()
	{
		AxisAlignedBB knotBox = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - 0.125D, 0.25D + 0.125D, 
			0.5D - 0.125D, 
    		0.5D + 0.125D, 0.75D - 0.125D, 
    		0.5F + 0.125D );
		
		return knotBox;
	}
	
	//RENDER
	
    @Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	IBlockAccess blockAccess = renderBlocks.blockAccess;
    	
    	for ( int iAxis = 0; iAxis < 3; iAxis++ )
    	{
    		if ( this.GetExtendsAlongAxis( blockAccess, i, j, k, iAxis ) )
    		{
    			this.SetRenderBoundsForAxis( renderBlocks, iAxis );
    			
    			renderBlocks.renderStandardBlock( this, i, j, k );
    		}
    	}
    	
    	if (HasStemBelow(blockAccess, i, j, k)) {
			renderBlocks.setRenderBounds (this.GetRopeKnotBounds());
			renderBlocks.renderStandardBlock( this, i, j, k );
			
			renderBlocks.setRenderBounds( this.GetVerticalRopeBounds(1.0D));			
			renderBlocks.renderStandardBlock( this, i, j, k );
			
		}else if (HasStemBelow(blockAccess, i, j-1, k)) //twoblocksdown
		{
			renderBlocks.setRenderBounds (this.GetVerticalRopeBounds(2.0D));
			renderBlocks.renderStandardBlock( this, i, j, k );
			
			renderBlocks.setRenderBounds (this.GetRopeKnotBounds());
			renderBlocks.renderStandardBlock( this, i, j, k );

		}
    	
    	
    	return true;
    }
    
    @Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {	
        return true;
    }
    
    public static boolean HasStemBelow(IBlockAccess blockAccess, int i, int j, int k )
    {
      	int blockBelow = blockAccess.getBlockId(i, j - 1, k);
      	
    	if (blockBelow == SCDefs.grapeStem.blockID)
    	{
    		return true;
    	}
    	return false;
	}	
	
	
}
