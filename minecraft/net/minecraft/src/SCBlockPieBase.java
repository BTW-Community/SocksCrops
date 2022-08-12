package net.minecraft.src;

public abstract class SCBlockPieBase extends Block {

	public static final float pieHeight = ( 4F / 16F );
	public static final float pieWidth = ( 12F / 16F );
	public static final float pieHalfWidth = ( pieWidth / 2F );
	public static final float pieLength = ( 12F / 16F );
	public static final float pieHalfLength = ( pieLength / 2F );
	
	protected SCBlockPieBase(int blockID) {
		super(blockID, Material.cake);
		
		setHardness( 0.6F );
        SetShovelsEffectiveOn( true );
        
        setStepSound( FCBetterThanWolves.fcStepSoundSquish );
	}
	
	@Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
		return AxisAlignedBB.getAABBPool().getAABB(         	
        		( 0.5F - pieHalfWidth ), 0.0F, ( 0.5F - pieHalfLength ), 
        		( 0.5F + pieHalfWidth ), pieHeight, ( 0.5F + pieHalfLength ) );
    }
	
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( )
    {
		return AxisAlignedBB.getAABBPool().getAABB(         	
        		( 0.5F - pieHalfWidth ), 0.0F, ( 0.5F - pieHalfLength ), 
        		( 0.5F + pieHalfWidth ), pieHeight, ( 0.5F + pieHalfLength ) );
    }
	
    @Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		return m_currentBlockRenderer.ShouldSideBeRenderedBasedOnCurrentBounds( iNeighborI, iNeighborJ, iNeighborK, iSide );
    }	
    
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState());
		renderer.renderStandardBlock(this, i, j, k);
		return true;
	}
	
    
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
    	renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState());
    	FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
    }


}
