package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockFruitTreesLeavesBase extends FCBlockLeaves {
    
	final static int APPLE = 0;
	final static int CHERRY = 1;
	final static int LEMON = 2;
	final static int OLIVE = 3;
	
	public static final String[] LEAF_TYPES = new String[] {"apple", "cherry", "lemon", "olive"};
    public static final String[] leafTextures = new String[] {
    		"SCBlockFruitTreeLeaves_" + LEAF_TYPES[APPLE],
    		"SCBlockFruitTreeLeaves_" + LEAF_TYPES[CHERRY],
    		"SCBlockFruitTreeLeaves_" + LEAF_TYPES[LEMON],
    		"SCBlockFruitTreeLeaves_" + LEAF_TYPES[OLIVE]
    		};
	
    protected Icon[] leafIcons = new Icon[4];

    
	protected SCBlockFruitTreesLeavesBase(int iBlockID) {
		super(iBlockID);
		
        setHardness( 0.2F );    	
        SetAxesEffectiveOn( true );        
        SetBuoyancy( 1F );
        
        setLightOpacity( 1 );
        
		SetFireProperties( FCEnumFlammability.LEAVES );
		
        setStepSound( soundGrassFootstep );
		
		setCreativeTab( CreativeTabs.tabDecorations );
	}
	
	@Override
    public abstract int idDropped( int iMetadata, Random random, int iFortuneModifier );
	
	@Override
    public abstract int damageDropped( int iMetaData );
	
	
    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier )
    {
    	// override of parent function to get rid of apple drop on oak
    	
        if ( !world.isRemote )
        {
            int iChanceOfSaplingDrop = 1;

            if ( world.rand.nextInt( iChanceOfSaplingDrop ) == 0 )
            {
                int iIdDropped = idDropped( iMetadata, world.rand, iFortuneModifier );
                
                if (iIdDropped > 0)
                {
                	dropBlockAsItem_do( world, i, j, k, new ItemStack( iIdDropped, 1, damageDropped( iMetadata ) ) );
                }
                
            }            
        }
    }

	protected void UpdateAdjacentTreeBlockArray( World world, int i, int j, int k )
    {
        for ( int iTempIOffset = -m_iAdjacentTreeBlockSearchDist; iTempIOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempIOffset )
        {
            for ( int iTempJOffset = -m_iAdjacentTreeBlockSearchDist; iTempJOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempJOffset )
            {
                for ( int iTempKOffset = -m_iAdjacentTreeBlockSearchDist; iTempKOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempKOffset )
                {
                    int iTempBlockID = world.getBlockId( i + iTempIOffset, j + iTempJOffset, k + iTempKOffset );

                    if ( Block.blocksList[iTempBlockID] instanceof SCBlockFruitTreesLogBase && !((SCBlockFruitTreesLog)(SCDefs.fruitLog)).IsDeadStump( world, i + iTempIOffset, j + iTempJOffset, k + iTempKOffset ) )
                    {
                    	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] = 0;
                    }
                    else if (Block.blocksList[iTempBlockID] instanceof SCBlockFruitTreesLeavesBase)
                    {
                    	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] = -2;
                    }
                    else
                    {
                    	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] = -1;
                    }
                }
            }
        }

        for ( int iTempValue = 1; iTempValue <= 4; ++iTempValue)
        {
            for ( int iTempIOffset = -m_iAdjacentTreeBlockSearchDist; iTempIOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempIOffset)
            {
                for ( int iTempJOffset = -m_iAdjacentTreeBlockSearchDist; iTempJOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempJOffset)
                {
                    for ( int iTempKOffset = -m_iAdjacentTreeBlockSearchDist; iTempKOffset <= m_iAdjacentTreeBlockSearchDist; ++iTempKOffset)
                    {
                        if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] == iTempValue - 1 )
                        {
                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf - 1][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf - 1][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] = iTempValue;
                            }

                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf + 1][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf + 1][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf] = iTempValue;
                            }
                            
                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf - 1][iTempKOffset + iArrayWidthHalf] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf - 1][iTempKOffset + iArrayWidthHalf] = iTempValue;
                            }

                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf + 1][iTempKOffset + iArrayWidthHalf] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf + 1][iTempKOffset + iArrayWidthHalf] = iTempValue;
                            }

                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf - 1] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf - 1] = iTempValue;
                            }

                            if ( m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf + 1] == -2 )
                            {
                            	m_iAdjacentTreeBlocks[iTempIOffset + iArrayWidthHalf][iTempJOffset + iArrayWidthHalf][iTempKOffset + iArrayWidthHalf + 1] = iTempValue;
                            }
                        }
                    }
                }
            }
        }
    }
	
	@Override
    public void harvestBlock( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
        if( !world.isRemote && player.getCurrentEquippedItem() != null && 
    		player.getCurrentEquippedItem().getItem() instanceof FCItemShears)
        {
            dropBlockAsItem_do( world, i, j, k, new ItemStack( SCDefs.fruitLeaves, 1, iMetadata & 3 ) );
            
            player.getCurrentEquippedItem().damageItem( 1, player );
        } 
    }
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
        int l = 1;
        int i1 = l + 1;
        
        if(world.checkChunksExist(i - i1, j - i1, k - i1, i + i1, j + i1, k + i1))
        {
            for(int j1 = -l; j1 <= l; j1++)
            {
                for(int k1 = -l; k1 <= l; k1++)
                {
                    for(int l1 = -l; l1 <= l; l1++)
                    {
                        int i2 = world.getBlockId(i + j1, j + k1, k + l1);
                        
                        if ( Block.blocksList[i2] instanceof SCBlockFruitTreesLeavesBase )
                        {
                            int j2 = world.getBlockMetadata(i + j1, j + k1, k + l1);
                            world.setBlockMetadata(i + j1, j + k1, k + l1, j2 | 8);
                        }
                    }
                }
            }
        }
        
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );
    }
	
	@Override
    public boolean isOpaqueCube()
    {
        return false; //!( Block.leaves.graphicsLevel );
    }
	
	@Override
	public boolean CanRotateOnTurntable( IBlockAccess iBlockAccess, int i, int j, int k )
	{
		return false;
	}
	
	@Override
	public boolean CanTransmitRotationHorizontallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
	@Override
	public boolean CanTransmitRotationVerticallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}
	
    public int getBlockColor()
    {
    	return 0xffffff;
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    public int getRenderColor(int par1)
    {
    	return 0xffffff;
    }

    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	return 0xffffff;
    }

}
