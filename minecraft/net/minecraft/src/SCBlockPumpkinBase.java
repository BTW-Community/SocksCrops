package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinBase extends BlockDirectional {

	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	
	protected SCBlockPumpkinBase(int iBlockID, int stemBlock, int vineBlock, int flowerBlock) {
		super(iBlockID, Material.pumpkin);
		setTickRandomly( true ); 
		setUnlocalizedName("fcBlockPumpkinFresh");
		setCreativeTab(CreativeTabs.tabDecorations);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        
	}
	
	public boolean isPossessed() 
	{
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{		
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
        	AttemptToGrow( world, i, j, k, random );

        	if ( hasPortalInRange(world, i, j, k) && !this.isPossessed())
            {
        		attemptToBePossessed(world, i, j, k, random);
            }
        }
	}
	
	private void attemptToBePossessed(World world, int i, int j, int k, Random random)
	{
		int id = world.getBlockId(i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
		
		if (random.nextInt(possesionChance()) == 0)
		{
			if (id == SCDefs.pumpkinFresh.blockID)
			{
				if (meta < 4)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 0 );
				}
				else if (meta >= 4 && meta < 8)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 1 );
				}
				else if (meta >= 8 && meta < 12)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 2 );
				}
				else if (meta >= 12 && meta <= 15)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 3 );
				}
			}
			else if (id == SCDefs.pumpkinGreenFresh.blockID)
			{
				if (meta < 4)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 4 );
				}
				else if (meta >= 4 && meta < 8)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 5 );
				}
				else if (meta >= 8 && meta < 12)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 6 );
				}
				else if (meta >= 12 && meta <= 15)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 7 );
				}
			}
			else if (id == SCDefs.pumpkinYellowFresh.blockID)
			{
				if (meta < 4)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 8 );
				}
				else if (meta >= 4 && meta < 8)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 9 );
				}
				else if (meta >= 8 && meta < 12)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 10 );
				}
				else if (meta >= 12 && meta <= 15)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 11 );
				}
			}
			else if (id == SCDefs.pumpkinWhiteFresh.blockID)
			{
				if (meta < 4)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 12 );
				}
				else if (meta >= 4 && meta < 8)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 13 );
				}
				else if (meta >= 8 && meta < 12)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 14 );
				}
				else if (meta >= 12 && meta <= 15)
				{
					world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, 15 );
				}
			}
			
			//world.playAuxSFX(k, k, i, j, k);
		}
		
	}
	protected int possesionChance()
    {
    	return 10;
    }
	protected int GetPortalRange()
    {
    	return 16;
    }
    
    protected boolean hasPortalInRange( World world, int i, int j, int k )
    {
    	int portalRange = GetPortalRange();
    	
        for ( int iTempI = i - portalRange; iTempI <= i + portalRange; iTempI++ )
        {
            for ( int iTempJ = j - portalRange; iTempJ <= j + portalRange; iTempJ++ )
            {
                for ( int iTempK = k - portalRange; iTempK <= k + portalRange; iTempK++ )
                {
                    if ( world.getBlockId( iTempI, iTempJ, iTempK ) == Block.portal.blockID )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

	public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.99F;
    }
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected int GetGrowthLevel( int meta) {
		
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
		
	}
	
	protected void AttemptToGrow( World world, int i, int j, int k, Random random ) 
    {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		int lightLevel = 9;
		int meta = world.getBlockMetadata(i, j, k);
		
    	if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&  
    		world.GetBlockNaturalLightValue( i, j + 1, k ) >= lightLevel )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && 
	        	blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j, k ) ||
	        	blockBelow.CanWildVegetationGrowOnBlock(world, i, j, k) || blockBelow.CanReedsGrowOnBlock(world, i, j, k) || blockBelow.blockID == Block.mycelium.blockID )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	    		
	    		
	    		System.out.println("pumpkin: growth chance: " + fGrowthChance);

	            if ( random.nextFloat() <= fGrowthChance ) 
				{
	            	if ( GrowthLevel < 3 )
	                {
	                	world.setBlockMetadataWithNotify( i, j, k, meta + 4 );
	                	System.out.println("pumpkin grow stage: "+ GrowthLevel);
	                }
	                else if (GrowthLevel == 3) {
	                	//this.placeFCPumpkin( world, i, j, k, random );
	                }
	            	
				}
	        }
	    }
    }
	

    private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		//super.RenderBlock(renderer, i, j, k);
		
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel(meta);
		
		if (growthLevel == 0) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 0 = 6x6x6
			renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 1){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 1 = 8x8x8
			renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 2){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 6/16D)); // stage 2 = 12x12x12
			renderer.renderStandardBlock( this, i, j, k );
		}else if (growthLevel == 3){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}
		
		
		this.renderVineConnector( renderer, i, j, k);

		return true;
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
	
	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;


        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;
        
        if (this.hasStemFacing(r, par2, par3, par4))
        {
        	this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);
        }
        
		return true;

    }
    
	private boolean hasStemFacing( RenderBlocks r, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    int dir = this.getDirection(r.blockAccess.getBlockMetadata(i, j, k));
	    	
	    if (dir == 0) {
	    	iTargetFacing = 2;
	    }
	    else if (dir == 1) {
	    	iTargetFacing = 5;
	    }
	    else if (dir == 2) {
	    	iTargetFacing = 3;
	    }
	    else if (dir == 3) {
	    	iTargetFacing = 4;
	    }
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = r.blockAccess.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == this.stemBlock || targetBlockID == this.vineBlock || targetBlockID == this.flowerBlock)
	    {	
	    	return true;
	    }
	    else return false;
	}

	/**
     * Utility function to draw crossed swuares
     */
    public void drawConnector(Block block, int meta, double x, double y, double z, float scale)
    {
        Tessellator tess = Tessellator.instance;
        int growthLevel = this.GetGrowthLevel(meta);
        
        Icon icon = connectorIcon[growthLevel];
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0.5D;// - var20;
        double maxZ = z + 0.5D + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0.5D;// - var20;
            maxX = x + 0.5D + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D - var20;
            maxZ = z + 1.5D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0.5D - var20;
            maxX = x + 1.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (2 * (double)scale) , minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
        }

    }
	
	public boolean GetExtendsAlongFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return GetExtendsAlongAxis( blockAccess, i, j, k, ConvertFacingToAxis( iFacing ) );
	}
	
	public boolean GetExtendsAlongAxis( IBlockAccess blockAccess, int i, int j, int k, int iAxis )
	{
		return GetExtendsAlongAxisFromMetadata( blockAccess.getBlockMetadata( i, j, k ), iAxis );		
	}
	
	public boolean GetExtendsAlongAxisFromMetadata( int iMetadata, int iAxis )
	{
		return ( iMetadata & ( 1 << iAxis ) ) > 0;

	}
	
	static public int ConvertFacingToAxis( int iFacing )
	{
		if ( iFacing == 4 || iFacing == 5 )
		{
			return 0;
		}
		else if ( iFacing == 0 || iFacing == 1 )
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	
	public AxisAlignedBB GetBoundsFromPoolForStringToFacing( int iFacing )
	{
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			0.4D, 0.5D, 0.4D, 
    		0.6D, 1.5D, 0.6D);
		
		box.TiltToFacingAlongJ( iFacing );
		
		return box;
	}
	
	
//----------- Client Side Functionality -----------//
    
    protected Icon m_IconTop;
    protected Icon[] m_iconArray;
    protected Icon[] m_iconTopArray;
    protected Icon[] connectorIcon;

    @Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "pumpkin_side" );
		
		m_IconTop = register.registerIcon( "pumpkin_top" );
		
		m_iconArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
        }
        
        m_iconTopArray = new Icon[4];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconTopArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
        }
        
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinConnector_" + iTempIndex );
        }
    }

    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	
    	if ( iSide == 1 || iSide == 0 )
    	{
    		return m_iconTopArray[growthLevel];
    	}
    	
    	return m_iconArray[growthLevel];
    }
    
}
