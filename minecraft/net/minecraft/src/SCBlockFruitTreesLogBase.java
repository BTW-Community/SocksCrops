package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockFruitTreesLogBase extends FCBlockLog {

	public static final String[] woodType = new String[] {"apple", "cherry", "lemon", "olive"};
	
	protected SCBlockFruitTreesLogBase(int iBlockID) {
		super(iBlockID);
		
	    setHardness( 1.25F ); // vanilla 2
	    setResistance( 3.33F );  // odd value to match vanilla resistance set through hardness of 2
        
		SetAxesEffectiveOn();
		
        SetBuoyant();
		
		SetFireProperties( FCEnumFlammability.LOGS );
		
		InitBlockBounds(4/16F, 0F, 4/16F, 12/16F, 1F, 12/16F);
		
        setStepSound( soundWoodFootstep );
	}
	
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        byte var7 = 4;
        int var8 = var7 + 1;

        if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
        {
            for (int var9 = -var7; var9 <= var7; ++var9)
            {
                for (int var10 = -var7; var10 <= var7; ++var10)
                {
                    for (int var11 = -var7; var11 <= var7; ++var11)
                    {
                        int var12 = par1World.getBlockId(par2 + var9, par3 + var10, par4 + var11);

                        if (Block.blocksList[var12] instanceof SCBlockFruitTreesLeavesBase)
                        {
                            int var13 = par1World.getBlockMetadata(par2 + var9, par3 + var10, par4 + var11);

                            if ((var13 & 8) == 0)
                            {
                                par1World.setBlockMetadataWithNotify(par2 + var9, par3 + var10, par4 + var11, var13 | 8, 4);
                            }
                        }
                    }
                }
            }
        }
    }
	

    @Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
    
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	return false;
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemSawDust.itemID, 4, 0, fChanceOfDrop );
		//DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemBark.itemID, 1, iMetadata & 3, fChanceOfDrop );
		
		return true;
	}
	
    @Override
    public int GetFurnaceBurnTime( int iItemDamage )
    {
    	return FCBlockPlanks.GetFurnaceBurnTimeByWoodType( iItemDamage ) * 2;
    }
	
    //------------- Class Specific Methods ------------//
	
	public void ConvertToSmouldering( World world, int i, int j, int k )
	{
		int meta = SCBlockFruitTreesLogSmoldering.setIsStump( 0, GetIsStump( world, i, j, k ) );
		
		if (world.getBlockId(i, j, k) != SCDefs.fruitBranch.blockID)
		{			
			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.fruitLogSmoldering.blockID, meta );
		}

	}
	
	@Override
    public boolean GetIsStump( int iMetadata )
    {
    	return ( iMetadata & 12 ) == 12;
    }   
	
    @Override
    public boolean GetIsStump( IBlockAccess blockAccess, int i, int j, int k )
    {
    	int iBlockID = blockAccess.getBlockId( i, j, k );
    	
    	if ( Block.blocksList[iBlockID] instanceof SCBlockFruitTreesLogBase )
    	{
    		int iMetadata = blockAccess.getBlockMetadata( i, j, k );
    		
    		if ( GetIsStump( iMetadata ) )
    		{
    			return true;
    		}
    	}
    	
    	return false;
    }
    
    public boolean IsDeadStump( World world, int i, int j, int k )
    {
    	if ( GetIsStump( world, i, j, k ) )
		{
    		int iBlockAboveID = world.getBlockMetadata( i, j + 1, k );
    		
        	if ( !(Block.blocksList[iBlockAboveID] instanceof SCBlockFruitTreesLogBase) )
        	{
        		return true;
        	}
		}
    	
    	return false;    	
    }

    public boolean IsWorkStumpItemConversionTool( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
    
//----------- Client Side Functionality -----------//
    
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
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
    	return true;
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
		
		if (meta <= 3 || meta > 11 )
		{
			minx = 4/16F;
			maxx = 12/16F;
			minz = 4/16F;
			maxz = 12/16F;
		}
		else if (meta > 3 && meta <= 7)
		{
			minx = 0/16F;
			maxx = 16/16F;
			minz = 4/16F;
			maxz = 12/16F;
			miny = 4/16F;
			maxy = 12/16F;
		}
		else if (meta > 7 && meta <= 12)
		{
			minx = 4/16F;
			maxx = 12/16F;
			minz = 0/16F;
			maxz = 16/16F;
			miny = 4/16F;
			maxy = 12/16F;
		}
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
    
    public static final String[] trunkTopTextureTypes = new String[] {"SCBlockFruitTreeLogTop_apple", "SCBlockFruitTreeLogTop_cherry", "SCBlockFruitTreeLogTop_lemon", "SCBlockFruitTreeLogTop_olive"};
    public static final String[] trunkSideTextureTypes = new String[] {"SCBlockFruitTreeLogSide_apple", "SCBlockFruitTreeLogSide_cherry", "SCBlockFruitTreeLogSide_lemon", "SCBlockFruitTreeLogSide_olive"};
    
    public static final String[] stumpTopTextureTypes = new String[] {"SCBlockFruitTreeLogStumpTop_apple", "SCBlockFruitTreeLogStumpTop_cherry", "SCBlockFruitTreeLogStumpTop_lemon", "SCBlockFruitTreeLogStumpTop_olive"};
    public static final String[] stumpSideTextureTypes = new String[] {"SCBlockFruitTreeLogStumpSide_apple", "SCBlockFruitTreeLogStumpSide_cherry", "SCBlockFruitTreeLogStumpSide_lemon", "SCBlockFruitTreeLogStumpSide_olive"};
   
    
    protected Icon[] trunkIconArray;
    protected Icon[] trunkTopArray;
    
    protected Icon[] stumpIconArray;
    protected Icon[] stumpTopArray;
    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {    	
    	if ( ( iMetadata & 12 ) == 12 )
    	{    		
    		if ( iSide > 1 )
    		{
    			return stumpIconArray[iMetadata & 3];
    		}
    		else
    		{
    			return stumpTopArray[iMetadata & 3];
    		}
    	}
    	
    	 int var3 = iMetadata & 12;
         int var4 = iMetadata & 3;
         return var3 == 0 && (iSide == 1 || iSide == 0) ? this.trunkTopArray[var4] : (var3 == 4 && (iSide == 5 || iSide == 4) ? this.trunkTopArray[var4] : (var3 == 8 && (iSide == 2 || iSide == 3) ? this.trunkTopArray[var4] : this.trunkIconArray[var4]));
    } 
    
    @Override
    public void registerIcons( IconRegister iconRegister )
    {
    	
    	stumpTopArray = new Icon[stumpTopTextureTypes.length];
    	
        for (int iTextureID = 0; iTextureID < stumpTopArray.length; iTextureID++ )
        {
        	stumpTopArray[iTextureID] = iconRegister.registerIcon(stumpTopTextureTypes[iTextureID]);
        }
    	
        stumpIconArray = new Icon[stumpSideTextureTypes.length];

        for (int iTextureID = 0; iTextureID < stumpIconArray.length; iTextureID++ )
        {
        	stumpIconArray[iTextureID] = iconRegister.registerIcon(stumpSideTextureTypes[iTextureID]);
        }
    	
    	
    	trunkTopArray = new Icon[trunkTopTextureTypes.length];
    	
        for (int iTextureID = 0; iTextureID < trunkTopArray.length; iTextureID++ )
        {
        	trunkTopArray[iTextureID] = iconRegister.registerIcon(trunkTopTextureTypes[iTextureID]);
        }
    	
    	trunkIconArray = new Icon[trunkSideTextureTypes.length];

        for (int iTextureID = 0; iTextureID < trunkIconArray.length; iTextureID++ )
        {
        	trunkIconArray[iTextureID] = iconRegister.registerIcon(trunkSideTextureTypes[iTextureID]);
        }
        
        super.registerIcons( iconRegister );
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	if (meta <= 3 || meta > 11)
    	{
    		renderer.setRenderBounds( 4/16D, 0D, 4/16D, 12/16D, 1D, 12/16D );
    	}
    	else if (meta <= 7)
    	{
    		renderer.setRenderBounds( 0/16D, 4/16D, 4/16D, 16/16D, 12/16D, 12/16D );
    	}
    	else if (meta <= 11)
    	{
    		renderer.setRenderBounds( 4/16D, 4/16D, 0/16D, 12/16D, 12/16D, 16/16D );
    	}
    	
    	renderer.renderBlockLog( this, i, j, k );
    	
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
    	
    	if (!GetIsStump(meta))
    	{
        	renderLogs(renderer, this.blockID,
        			i, j, k,
        			blockMinI, blockMaxI,
        			blockMinJ, blockMaxJ,
        			blockMinK, blockMaxK,
        			metaMinI, metaMaxI,
        			metaMinJ, metaMaxJ,
        			metaMinK, metaMaxK,
        			meta);
        	
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

    protected void renderLogs(RenderBlocks renderer, int blockID, int i, int j, int k,
    		int blockMinI, int blockMaxI,
    		int blockMinJ, int blockMaxJ,
    		int blockMinK, int blockMaxK,
    		int metaMinI, int metaMaxI,
    		int metaMinJ, int metaMaxJ,
			int metaMinK, int metaMaxK,
			int meta)
    {
    	if (meta <= 3)
    	{
    		if (blockMinI == blockID && metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 4/16D, 4/16D, 4/16D, 12/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxI == blockID && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(12/16D, 4/16D, 4/16D, 16/16D, 12/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinK == blockID && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 4/16D, 4/16D, 0/16D, 12/16D, 12/16D, 4/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxK == blockID && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 4/16D, 4/16D, 12/16D, 12/16D, 12/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    	}
    	else if (meta > 3 && meta < 8)
    	{
    		if (blockMinJ == blockID && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 4/16D, 0/16D, 4/16D, 12/16D, 4/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxJ == blockID && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 4/16D, 12/16D, 4/16D, 12/16D, 16/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinK == blockID && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 4/16D, 4/16D, 0/16D, 12/16D, 12/16D, 4/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxK == blockID && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 4/16D, 4/16D, 12/16D, 12/16D, 12/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
    	else if (meta >= 8 && meta < 12)
    	{
    		if (blockMinJ == blockID && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 4/16D, 0/16D, 4/16D, 12/16D, 4/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxJ == blockID && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 4/16D, 12/16D, 4/16D, 12/16D, 16/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinI == blockID&& metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 4/16D, 4/16D, 4/16D, 12/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxI == blockID && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(12/16D, 4/16D, 4/16D, 16/16D, 12/16D, 12/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
		
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
    	if (meta <= 3)
    	{
    		if (blockMinI == blockID && metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxI == blockID && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinK == blockID && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxK == blockID && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    	}
    	else if (meta > 3 && meta < 8)
    	{
    		if (blockMinJ == blockID && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 0/16D, 6/16D, 10/16D, 6/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxJ == blockID && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 10/16D, 6/16D, 10/16D, 16/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinK == blockID && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxK == blockID && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
    	else if (meta >= 8 && meta < 12)
    	{
    		if (blockMinJ == blockID && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 0/16D, 6/16D, 10/16D, 6/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxJ == blockID && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 10/16D, 6/16D, 10/16D, 16/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMinI == blockID&& metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (blockMaxI == blockID && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
		
	}
    
    private boolean isLeavesBlock(int blockID) {
    	return Block.blocksList[blockID] instanceof SCBlockFruitTreesLeavesBase;
	}
    
    protected void renderLeaves(RenderBlocks renderer, int i, int j, int k,
    		int blockMinI, int blockMaxI,
    		int blockMinJ, int blockMaxJ,
    		int blockMinK, int blockMaxK,
    		int metaMinI, int metaMaxI,
    		int metaMinJ, int metaMaxJ,
			int metaMinK, int metaMaxK,
			int meta)
    {
    	   	
    	
    	if (meta <= 3)
    	{
    		if (isLeavesBlock(blockMinI) ) //&& metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxI) ) // && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMinK) ) // && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxK) ) // && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    	}
    	else if (meta > 3 && meta < 8)
    	{
    		if (isLeavesBlock(blockMinJ) ) // && && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 0/16D, 6/16D, 10/16D, 6/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxJ)  ) // && && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 10/16D, 6/16D, 10/16D, 16/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMinK) ) // && && metaMinK > 7 && metaMinK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 0/16D, 10/16D, 10/16D, 6/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxK)) // && && metaMaxK > 7 && metaMaxK < 12)
    		{
    			renderer.setRenderBounds( 6/16D, 6/16D, 10/16D, 10/16D, 10/16D, 16/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
    	else if (meta >= 8 && meta < 12)
    	{
    		if (isLeavesBlock(blockMinJ)) // && && metaMinJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 0/16D, 6/16D, 10/16D, 6/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxJ) ) // && && metaMaxJ <= 3)
    		{
    			renderer.setRenderBounds( 6/16D, 10/16D, 6/16D, 10/16D, 16/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMinI) ) // && && metaMinI > 3 && metaMinI < 8)
    		{
    			renderer.setRenderBounds( 0/16D, 6/16D, 6/16D, 6/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    		
    		if (isLeavesBlock(blockMaxI)) // && && metaMaxI > 3 && metaMaxI < 8)
    		{
    			renderer.setRenderBounds(10/16D, 6/16D, 6/16D, 16/16D, 10/16D, 10/16D );
    			renderer.renderStandardBlock(this, i, j, k);
    		}
    	}
		
	}

	@Override
    public void RenderBlockSecondPass( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
    {
        RenderCookingByKilnOverlay( renderBlocks, i, j, k, bFirstPassResult );
    }
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
    	renderer.setRenderBounds( 4/16D, 0D, 4/16D, 12/16D, 1D, 12/16D );
    	super.RenderBlockAsItem(renderer, iItemDamage, fBrightness);
	}
	
}
