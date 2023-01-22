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
                                par1World.setBlockMetadata(par2 + var9, par3 + var10, par4 + var11, var13 | 8, 4);
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
   
    
    private boolean isLeavesBlock(int blockID) {
    	return Block.blocksList[blockID] instanceof SCBlockFruitTreesLeavesBase;
	}

	
}
