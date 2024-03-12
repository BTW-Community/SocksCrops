package net.minecraft.src;

import java.awt.Color;
import java.util.List;
import java.util.Random;

public class SCBlockJuicer extends FCBlockMillStone {
	
	public static final SCModelBlockJuicer m_model = new SCModelBlockJuicer();
	
	protected SCBlockJuicer(int par1) {
		super(par1);
		setUnlocalizedName("SCBlockJuicer");
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCTileEntityJuicer();
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {		
		int iState = this.GetCurrentGrindingType( world, i, j, k );
		
        SCTileEntityJuicer tileEntity = (SCTileEntityJuicer)world.getBlockTileEntity( i, j, k );
        
		if ( iState == m_iContentsNothing )
		{
	    	ItemStack heldStack = player.getCurrentEquippedItem();

	    	if ( heldStack != null )
	    	{
				if ( !world.isRemote )
				{
			        world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
			        
					tileEntity.AttemptToAddSingleItemFromStack( heldStack );
				}
				else
				{				
					heldStack.stackSize--;
				}
	    	}
		}
		else if ( !world.isRemote )
    	{
	        world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
	        
			tileEntity.EjectContents( iFacing );
    	}
		
		return true;		
    }
	
	public int GetCurrentGrindingType( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return GetCurrentGrindingType( blockAccess.getBlockMetadata( i, j, k ) );
    }
    
    public int GetCurrentGrindingType( int iMetadata )
    {
    	return ( iMetadata & 14 ) >> 1;
    }
    
    public void SetCurrentGrindingType( World world, int i, int j, int k, int iGrindingType )
    {
    	int iMetadata = SetCurrentGrindingType( world.getBlockMetadata( i, j, k ), iGrindingType );
    	
		world.setBlockMetadataWithClient( i, j, k, iMetadata );
    }
    
    public int SetCurrentGrindingType( int iMetadata, int iGrindingType )
    {
    	iMetadata &= ~14; // filter out old state
    	
    	return iMetadata | ( iGrindingType << 1 );    	
    }
    
    @Override
    public void randomDisplayTick( World world, int i, int j, int k, Random random )
    {
    	super.randomDisplayTick(world, i, j, k, random);

    	if ( GetIsMechanicalOn( world, i, j, k ) )
    	{
	        int iCurrentGrindingType = GetCurrentGrindingType( world, i, j, k );
	        
	    	EmitJuicingParticles(world, i, j, k, iCurrentGrindingType, random);	                   
    	}
    }
    
	public void EmitJuicingParticles( World world, int i, int j, int k, int iGrindType, Random rand )
    {
    	ItemStack stackJuicing = ((SCTileEntityJuicer) world.getBlockTileEntity(i, j, k)).m_stackMilling;
    	ItemStack lastStack = ((SCTileEntityJuicer) world.getBlockTileEntity(i, j, k)).lastStackMilled;
    	int timesMilled = ((SCTileEntityJuicer) world.getBlockTileEntity(i, j, k)).getTimesMilled();
    	
    	if (timesMilled > 0 && lastStack != null) // iGrindType == m_iContentsNormalGrinding && 
    	{
        	double randomShiftX = (rand.nextDouble() * 0.125D);
        	double randomShiftZ = (rand.nextDouble() * 0.125D);
        	
        	//int type = getTypeForParticles(((SCTileEntityJuicer) world.getBlockTileEntity(i, j, k)));
        	int color = SCUtilsLiquids.getColorFromFruit(lastStack);
        	world.spawnParticle( "juice_" + String.valueOf(color),
                	i + randomShiftX + 0.5D, j - 0.01D, k + randomShiftZ + 0.5D,
                	0D, 0D, 0D );
    	}   	
    }	
	


	private int getTypeForParticles(SCTileEntityJuicer juicer) {

		ItemStack stackJuicing = juicer.m_stackMilling;
		
		if (SCCraftingManagerJuicer.getInstance().GetValidCraftingIngrediants(juicer) != null)
		{
			List<ItemStack> inputList = SCCraftingManagerJuicer.getInstance().GetValidCraftingIngrediants(juicer);
			
			if (juicer.validFruits.containsKey(inputList.get(0)))
			{
				
			}
		}
		
		return 0;
	}

	@Override
    public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
    {
    	FCUtilsRayTraceVsComplexBlock rayTrace = new FCUtilsRayTraceVsComplexBlock( world, i, j, k, startRay, endRay );
    	
    	m_model.AddToRayTrace( rayTrace );
    	
    	rayTrace.AddBoxWithLocalCoordsToIntersectionList( m_model.m_boxBase );    	
		
        return rayTrace.GetFirstIntersection();
    }
	
	@Override
	public void Overpower( World world, int i, int j, int k )
	{
		//BreakMillStone( world, i, j, k );
	}
	
	@Override
    public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
    {
        return ((SCTileEntityJuicer) par1World.getBlockTileEntity(par2, par3, par4)).m_stackMilling == null ? 0 : 15;
    }
	
//----------- Client Side Functionality -----------//
    
    private Icon[] m_iconsBySide = new Icon[6];
    private Icon[] m_iconsBySideFull = new Icon[6];
    private Icon[] m_iconsBySideOn = new Icon[6];
    private Icon[] m_iconsBySideOnFull = new Icon[6];
    
    private boolean m_bRenderingBase = false;

	@Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = register.registerIcon( "stone" ); // for hit effects
        
        m_iconsBySideFull[0] = m_iconsBySide[0] = register.registerIcon( "fcBlockMillStone_bottom" );
        
        m_iconsBySideFull[1] = m_iconsBySide[1] = register.registerIcon( "fcBlockMillStone_top" );
        
        m_iconsBySideOn[0] = m_iconsBySideOnFull[0] = register.registerIcon( "fcBlockMillStone_bottom_on" );
    	m_iconsBySideOn[1] = m_iconsBySideOnFull[1] = register.registerIcon( "fcBlockMillStone_top_on" );
    		
        Icon sideIcon = register.registerIcon( "fcBlockMillStone_side" );
        Icon sideIconFull = register.registerIcon( "fcBlockMillStone_side_full" );
        
        Icon sideIconOn = register.registerIcon( "fcBlockMillStone_side_on" );
        Icon sideIconOnFull = register.registerIcon( "fcBlockMillStone_side_on_full" );
        
        for ( int iTempSide = 2; iTempSide <= 5; iTempSide++ )
        {
        	m_iconsBySide[iTempSide] = sideIcon;
        	m_iconsBySideFull[iTempSide] = sideIconFull;
        	
        	m_iconsBySideOn[iTempSide] = sideIconOn;
        	m_iconsBySideOnFull[iTempSide] = sideIconOnFull;
        }
    }
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
		if ( m_bRenderingBase )
		{
			return m_iconsBySide[iSide];
		}
		else if ( GetIsMechanicalOn( iMetadata ) )
		{
			if ( GetCurrentGrindingType( iMetadata ) == m_iContentsNothing )
			{
				return m_iconsBySideOn[iSide];
			}
			else
			{
				return m_iconsBySideOnFull[iSide];
			}
		}
		else
		{
			if ( GetCurrentGrindingType( iMetadata ) == m_iContentsNothing )
			{
				return m_iconsBySide[iSide];
			}
			else
			{
				return m_iconsBySideFull[iSide];
			}
		}
    }
	
	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	m_bRenderingBase = true;
    	
        m_model.m_boxBase.RenderAsBlock( renderBlocks, this, i, j, k );
        
    	m_bRenderingBase = false;
    	
    	return m_model.RenderAsBlock( renderBlocks, this, i, j, k );
    }
    
    @Override
    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
    {
    	m_bRenderingBase = true;
    	
        m_model.m_boxBase.RenderAsItemBlock( renderBlocks, this, iItemDamage );
        
    	m_bRenderingBase = false;
    	
    	m_model.RenderAsItemBlock( renderBlocks, this, iItemDamage );
    }
    
    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, int i, int j, int k )
    {
		AxisAlignedBB transformedBox = m_model.m_boxSelection.MakeTemporaryCopy();
		
		transformedBox.offset( i, j, k );
		
		return transformedBox;		
    }

}
