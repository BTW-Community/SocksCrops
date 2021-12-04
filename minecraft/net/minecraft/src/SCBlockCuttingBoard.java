package net.minecraft.src;

public class SCBlockCuttingBoard extends BlockContainer {
	
	public static boolean m_bCampfireChangingState = false; // temporarily true when block is being changed from one block ID to another
	
	public static final int m_iContentsNothing = 0;
	public static final int m_iContentsNormalCutting = 1;
	public static final int m_iContentsJammed = 4;
	
	public SCBlockCuttingBoard( int iBlockID )
    {
        super( iBlockID, Material.circuits );
        
        setHardness( 0.5F );        
		
        setStepSound( soundWoodFootstep );        
        
        setUnlocalizedName( "SCBlockCuttingBoard" );
    }
	
    @Override
    public int tickRate( World world )
    {
    	return 10;
    }
    
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityCuttingBoard();
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {		
		SCTileEntityCuttingBoard cuttingBoard = (SCTileEntityCuttingBoard)world.getBlockTileEntity( i, j, k );
		ItemStack heldStack = player.getCurrentEquippedItem();
		
		if ( cuttingBoard.GetStorageStack() == null )
		{
	    	
	    	if ( heldStack != null && heldStack.itemID != this.blockID ) //dont allow inception pls
	    	{
				if ( !world.isRemote )
				{
			        world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
			        
			        cuttingBoard.AttemptToAddSingleItemToStorageFromStack( heldStack );
			        
			        //System.out.println(cuttingBoard.GetStorageStack());
				}
				else
				{				
					heldStack.stackSize--;
				}
	    	}
		}
		else if ( heldStack != null && cuttingBoard.GetStorageStack() != null)
    	{
			if ( !world.isRemote )
			{
		        world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
		        
		        //System.out.println("before" + cuttingBoard.GetCutStack());
		        
		        cuttingBoard.AttemptToAddSingleItemToCuttingFromStack( heldStack );
		        
		        //System.out.println("after" + cuttingBoard.GetCutStack());
			}
			else
			{				
				heldStack.stackSize--;
			}
	        
    	}
		else if ( cuttingBoard.GetStorageStack() != null && !world.isRemote )
		{
        	world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
 	        
 	        cuttingBoard.EjectStorageContents( iFacing );
		}
		
		return true;		
    }
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entity, ItemStack stack)
    {
        int setMeta = ((MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3)) % 4;
        world.setBlockMetadataWithNotify(i, j, k, setMeta, 3);

    }
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
		SCTileEntityCuttingBoard tileEntity = (SCTileEntityCuttingBoard)world.getBlockTileEntity( i, j, k );
        
        tileEntity.EjectContents();
        
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );	        
    }
	
    @Override
    public void OnCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	if ( !world.isRemote )
    	{
    		DropItemsIndividualy( world, i, j, k, SCDefs.cuttingBoard.blockID, 1, 0, 1.0F );
    	}
    }  
    	
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
        return SetFacing( iMetadata, iFacing ); 
    }
	
	@Override
    public int PreBlockPlacedBy( World world, int i, int j, int k, int iMetadata, EntityLiving entity ) 
	{
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( entity );

		return SetFacing( iMetadata, iFacing );
	}
	
	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
		{
			return false;
		}
		
        return super.canPlaceBlockAt( world, i, j, k );
    }
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {    	
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
        {
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            
            world.setBlockToAir( i, j, k );
        }
    }
	
    @Override
    public boolean CanBeCrushedByFallingEntity( World world, int i, int j, int k, EntityFallingSand entity )
    {
    	return true;
    }

	@Override
	public int GetFacing( int metadata )
	{
		return getDirection(metadata)  + 2;
	}
	

	public static int GetFacingPLS( int metadata )
	{
		return getDirection(metadata)  + 2;
	}
	
	/**
     * Returns the orentation value from the specified metadata // From BlockDirectional
     */
    public static int getDirection(int metadata)
    {
        return (metadata & 3);
    }

    @Override
	public boolean CanRotateOnTurntable( IBlockAccess iBlockAccess, int i, int j, int k )
	{
		return true;
	}
    
    @Override
	public boolean GetPreventsFluidFlow( World world, int i, int j, int k, Block fluidBlock )
	{
        return false;
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
	
	//----------- Client Side Functionality -----------//

	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		int dir = getDirection(blockAccess.getBlockMetadata(i, j, k));
		
		if (dir == 0 || dir == 2)
		{
			return getBoardBounds(7, 2, 6);
		}
		else return getBoardBounds(6, 2, 7);
	}
	
	private AxisAlignedBB getBoardBounds(int i, int j, int k)
	{
    	
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			8/16D - i/16D, 0.0D, 8/16D - k/16D, 
			8/16D + i/16D, j /16D, 8/16D + k/16D);
		
		return box;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = register.registerIcon("wood_spruce");
	}
	
	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
		renderBlocks.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderBlocks.blockAccess, i, j, k));
		
		setTextureRotation(renderBlocks, i, j, k);
    	FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, blockIcon);
    	
    	renderBlocks.ClearUvRotation();
    	return true;
    }

	private void setTextureRotation(RenderBlocks renderBlocks, int i, int j, int k) {
		int dir = getDirection(renderBlocks.blockAccess.getBlockMetadata(i, j, k));
		
		if (dir == 2) {
			renderBlocks.SetUvRotateTop(3);
			renderBlocks.SetUvRotateBottom(3);
		}
		else if (dir == 1) {
			renderBlocks.SetUvRotateTop(1);
			renderBlocks.SetUvRotateBottom(1);
		}
		else if (dir == 3) {
			renderBlocks.SetUvRotateTop(2);
			renderBlocks.SetUvRotateBottom(2);
		}

	}


}
