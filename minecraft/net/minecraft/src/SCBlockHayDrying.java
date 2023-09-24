package net.minecraft.src;

import java.util.Random;

public class SCBlockHayDrying extends BlockContainer {

	private String name;
	
	protected SCBlockHayDrying(int par1, String name) {
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
    public TileEntity createNewTileEntity( World world )
    {
        return new SCTileEntityHayDrying();
    }
	
	@Override
    public int idDropped( int iMetadata, Random random, int iFortuneModifier )
    {
		return SCDefs.hay.itemID;
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
	
	//------------- Class Specific Methods ------------//
	
	public void OnFinishedCooking( World world, int i, int j, int k )
	{
		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.driedHay.blockID, 0 ); 
	}
	
	public void SetCookLevel( World world, int i, int j, int k, int iCookLevel )
	{
		int iMetadata = SetCookLevel( world.getBlockMetadata( i, j, k ), iCookLevel );
		
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
	}
	
	public int SetCookLevel( int iMetadata, int iCookLevel )
	{
		iMetadata &= 1; // filter out old state
		
		iMetadata |= iCookLevel << 1;
	
		return iMetadata;
	}
    
	public int GetCookLevel( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetCookLevel( blockAccess.getBlockMetadata( i, j, k ) );
	}
	
	public int GetCookLevel( int iMetadata )
	{
		return iMetadata >> 1;
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
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{		
		int cookLevel = GetCookLevel(blockAccess, x, y, z);
		
		if ( cookLevel < 8)
		{
			switch (cookLevel) {
			case 0:
				return 0xaeff99;
			case 1:
				return 0xbbffa8;
			case 2:
				return 0xc6ffb7;
			case 3:
				return 0xd2ffc5;
			case 4:
				return 0xddffd4;
			case 5:
				return 0xe7ffe1;
			case 6:
				return 0xf1ffed;
			case 7:
				return 0xffffff;

			default:
				return 0xffffff;
			}
		}		
		else return 0xffffff;
	}
	
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
