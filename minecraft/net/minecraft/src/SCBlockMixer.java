package net.minecraft.src;

import java.util.Random;

public class SCBlockMixer extends BlockContainer implements FCIBlockMechanical {

	protected SCBlockMixer(int blockID) {
		super(blockID, Material.wood);
		setUnlocalizedName("SCBlockMixer");
		setCreativeTab(CreativeTabs.tabRedstone);
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
    	boolean bReceivingPower = IsInputtingMechanicalPower( world, i, j, k );
    	boolean bOn = GetIsMechanicalOn( world, i, j, k );
    	
    	if ( bOn != bReceivingPower )
    	{
    		SetIsMechanicalOn( world, i, j, k, bReceivingPower );
    	}
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
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		return true;
    }
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityMixer();
	}

	@Override
	public boolean CanOutputMechanicalPower() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean CanInputMechanicalPower() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
    public boolean IsInputtingMechanicalPower( World world, int i, int j, int k )
    {
    	return FCUtilsMechPower.IsBlockPoweredByAxle( world, i, j, k, this ); //|| 
			//FCUtilsMechPower.IsBlockPoweredByHandCrank( world, i, j, k );  	
    }

	@Override
	public boolean IsOutputtingMechanicalPower(World world, int i, int j, int k) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public boolean CanInputAxlePowerToFacing( World world, int i, int j, int k, int iFacing )
	{
		return iFacing == 1;
	}

	@Override
	public void Overpower(World world, int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}
	
//------------- Class Specific Methods ------------//
    
    public boolean GetIsMechanicalOn( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetIsMechanicalOn( blockAccess.getBlockMetadata( i, j, k ) );    
	}
    
    public boolean GetIsMechanicalOn( int iMetadata )
    {
    	return ( iMetadata & 1 ) > 0;    
    }
    
    public void SetIsMechanicalOn( World world, int i, int j, int k, boolean bOn )
    {
    	int iMetadata = SetIsMechanicalOn( world.getBlockMetadata( i, j, k ), bOn );
    	
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
    }
    
    public int SetIsMechanicalOn( int iMetadata, boolean bOn )
    {
    	if ( bOn )
    	{
            return iMetadata | 1;
    	}
    	
    	return iMetadata & (~1);
    }
	
	private Icon wood;
	private Icon steel;
	
    private Icon[] m_iconsBySide = new Icon[6];
    private Icon[] m_iconsBySideOn = new Icon[6];
	
	@Override
	public void registerIcons(IconRegister register) {

		steel = register.registerIcon("fcBlockAnvil");
		
        blockIcon = register.registerIcon( "wood" ); // for hit effects
        
        m_iconsBySide[0] = register.registerIcon( "SCBlockMixer_bottom" );
        
        m_iconsBySide[1] = register.registerIcon( "SCBlockMixer_top" );
        
        m_iconsBySideOn[0] = register.registerIcon( "SCBlockMixer_bottom" );
    	m_iconsBySideOn[1] = register.registerIcon( "SCBlockMixer_top" );
    		
        Icon sideIcon = register.registerIcon( "SCBlockMixer_side_off" );
        Icon sideIconOn = register.registerIcon( "SCBlockMixer_side_on" );

        for ( int iTempSide = 2; iTempSide <= 5; iTempSide++ )
        {
        	m_iconsBySide[iTempSide] = sideIcon;

        	m_iconsBySideOn[iTempSide] = sideIconOn;
        }
	}
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
		if ( GetIsMechanicalOn( iMetadata ) )
		{
			return m_iconsBySideOn[iSide];
		}
		else
		{
			return m_iconsBySide[iSide];
		}
    }
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{		
		renderer.setRenderBounds(0, 2/16D, 0, 1, 7/16D, 1);
		renderer.renderStandardBlock(this, i, j, k);
		
		renderer.setRenderBounds(2/16D, 7/16D, 2/16D, 14/16D, 11/16D, 14/16D);
		renderer.renderStandardBlock(this, i, j, k);
		
		renderer.setRenderBounds(0, 11/16D, 0, 1, 16/16D, 1);
		renderer.renderStandardBlock(this, i, j, k);
		//FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, i, j, k, wood);
		
		renderer.setRenderBounds(5/16D, 0/16D, 5/16D, 11/16D, 2/16D, 11/16D);
		FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, i, j, k, steel);
		
		//renderer.setRenderBounds(5/16D, 14/16D, 5/16D, 11/16D, 16/16D, 11/16D);
		//FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, i, j, k, steel);
		
		return true;
	}
	
}
