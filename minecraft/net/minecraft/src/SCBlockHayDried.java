package net.minecraft.src;

import java.util.Random;

public class SCBlockHayDried extends Block {

	private String name;
	
	protected SCBlockHayDried(int par1, String name) {
		super(par1, Material.leaves);
		
		setStepSound(soundGrassFootstep);
		
        InitBlockBounds( 0F, 0F, 0F, 1F, 0.125F, 1F);
        
        setHardness( 0.05F );
        SetShovelsEffectiveOn();
        
        SetBuoyant();
        
        setLightOpacity( 0 );        
        
        setCreativeTab( CreativeTabs.tabDecorations );
        
        setUnlocalizedName(name);
        
        this.name = name;
	}
	
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		return FCBetterThanWolves.fcItemStraw.itemID;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return null;
	}
	
	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
		// don't allow drying bricks on leaves as it makes for really lame drying racks in trees 
		
		return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) && 
			world.getBlockId( i, j - 1, k ) != Block.leaves.blockID;
    }
    
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockWithNotify(i, j, k, 0);
    	}
    }

    @Override
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }
    
    @Override
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -14/16F;        
    }
    
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	//------------- Client ------------//
	
	private Icon sideIcon;
    private Icon topIcon;

    @Override
    public void registerIcons( IconRegister iconRegister )
    {
    	topIcon = iconRegister.registerIcon("SCBlockStrawBale_top");
    	sideIcon = iconRegister.registerIcon("SCBlockStrawBale_side");
    }
    
    @Override
    public Icon getIcon( int side, int meta )
    {
    	Icon icon = blockIcon;
    	
    	if (side < 2)
    	{
    		return topIcon;
    	}
    	else return sideIcon;
    	
    }
    
	@Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		if ( iSide == 0 )
		{
			return FCClientUtilsRender.ShouldRenderNeighborFullFaceSide( blockAccess,
				iNeighborI, iNeighborJ, iNeighborK, iSide );
		}
		
		return true;
    }
	
    @Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	IBlockAccess blockAccess = renderBlocks.blockAccess;
    	
    	if ( blockAccess.getBlockId( i, j - 1, k ) != 0 )
    	{	    	
	    	float fHeight = 2/16F;
	    	
	    	renderBlocks.setRenderBounds( 0F, 0F, 0F, 
	    		1F, fHeight, 1F );
	    
	    	FCClientUtilsRender.RenderStandardBlockWithTexture( renderBlocks, this, i, j, k, blockIcon );
    	}
    
    	return true;
    }	

}
