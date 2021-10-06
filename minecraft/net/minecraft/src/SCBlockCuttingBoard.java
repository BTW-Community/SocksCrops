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
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityCuttingBoard();
	}
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
		SCTileEntityCuttingBoard tileEntity = (SCTileEntityCuttingBoard)world.getBlockTileEntity( i, j, k );
        
        tileEntity.EjectContents();
        
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );	        
    }
	
	public boolean IsValidCuttingItem( ItemStack stack )
	{
//		if ( SCCraftingManagerCuttingBoard.instance.GetRecipeResult( stack.getItem().itemID ) != null )
//		{
//			return true;
//		}
		
		return false;
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		
		
		
		int iMetadata = world.getBlockMetadata( i, j, k );
		
		if ( GetHasContents( iMetadata ) )
		{
			if ( world.isRemote )
			{
				player.playSound( "step.wood", 
		    		0.5F + ( world.rand.nextFloat() * 0.25F ), 
		    		1F + ( world.rand.nextFloat() * 0.25F ) );
			}
			else
			{
				EjectStorageStack( world, i, j, k );
			}
			
			SetHasContents( world, i, j, k, false );			
			
			return true;
		}
		else 
    	{
	    	ItemStack heldStack = player.getCurrentEquippedItem();
	    	
			if ( heldStack != null )
			{
				if ( world.isRemote )
				{
					player.playSound( "step.wood", 
			    		0.5F + ( world.rand.nextFloat() * 0.25F ), 
			    		0.5F + ( world.rand.nextFloat() * 0.25F ) );
				}
				else
				{				
					SCTileEntityCuttingBoard tileEntity = (SCTileEntityCuttingBoard)world.getBlockTileEntity( i, j, k );
			        
		        	tileEntity.SetStorageStack( heldStack );
				}
				
    			heldStack.stackSize = 0;
    			
    			SetHasContents( world, i, j, k, true );			
    			
    			return true;
			}
			else if ( IsValidCuttingItem( heldStack ))
			{
				
				return true;
			}
    	}
		
		return false;
    }
	
	
	private void EjectStorageStack( World world, int i, int j, int k )
    {    	
        SCTileEntityCuttingBoard tileEntity = (SCTileEntityCuttingBoard)world.getBlockTileEntity( i, j, k );
        
        ItemStack storageStack = tileEntity.GetStorageStack();

        if ( storageStack != null )
        {
	        float xOffset = 0.5F;
	        float yOffset = 0.4F;
	        float zOffset = 0.5F;
	        
	        double xPos = (float)i + xOffset;
	        double yPos = (float)j + yOffset;
	        double zPos = (float)k + zOffset;
	        
            EntityItem entityitem = 
            	new EntityItem( world, xPos, yPos, zPos, storageStack );

            entityitem.motionY = 0.2D;
            
            double fFacingFactor = 0.15D;
            double fRandomFactor = 0.05D;
            
            int iFacing = GetFacing( world, i, j, k );

            if ( iFacing <= 3 )
            {
                entityitem.motionX = ( world.rand.nextDouble() * 2D - 1D ) * fRandomFactor;
                
                if ( iFacing == 2 )
                {
                	entityitem.motionZ = -fFacingFactor;
                }
                else // 3
                {
                	entityitem.motionZ = fFacingFactor;
                }
            }
            else
            {
                entityitem.motionZ = ( world.rand.nextDouble() * 2D - 1D )  * fRandomFactor;
                
	        	if ( iFacing == 4 )
	            {
	            	entityitem.motionX = -fFacingFactor;
	            }
	            else // 5
	            {
	            	entityitem.motionX = fFacingFactor;
	            }
            }
            
            entityitem.delayBeforeCanPickup = 10;
            
            world.spawnEntityInWorld( entityitem );
            
			tileEntity.SetStorageStack( null );
        }
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
    public int PreBlockPlacedBy( World world, int i, int j, int k, int iMetadata, EntityLiving entityBy ) 
	{
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( entityBy );

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
	public int SetFacing( int iMetadata, int iFacing )
	{
		iMetadata &= ~3; // filter out old facing
		
		iMetadata |= MathHelper.clamp_int( iFacing, 2, 5 ) - 2; // convert to flat facing
		
		return iMetadata;
	}
    
	@Override
	public int GetFacing( int metadata )
	{
		return getDirection(metadata);
	}
	
	/**
     * Returns the orentation value from the specified metadata // From BlockDirectional
     */
    public static int getDirection(int metadata)
    {
        return (metadata & 3) + 2;
    }
    
    public void SetHasContents( World world, int i, int j, int k, boolean bHasContents )
	{
		int iMetadata = SetHasContents( world.getBlockMetadata( i, j, k ), bHasContents );
		
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
	}
	
	public int SetHasContents( int iMetadata, boolean bHasContents )
	{
		if ( bHasContents )
		{
			iMetadata |= 4;
		}
		else
		{
			iMetadata &= (~4);
		}
		
		return iMetadata;
	}
	
	public boolean GetHasContents( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetHasContents( blockAccess.getBlockMetadata( i, j, k ) );
	}
	
	public boolean GetHasContents( int iMetadata )
	{
		return ( iMetadata & 4 ) != 0;
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

	
	private AxisAlignedBB GetPumpkinBounds()
	{
    	
		AxisAlignedBB pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
			8/16D - 7/16D, 0.0D, 8/16D - 6/16D, 
			8/16D + 7/16D, 2 /16D, 8/16D + 6/16D);
		
		return pumpkinBox;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = register.registerIcon("wood_spruce");
	}
	
	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
		renderBlocks.setRenderBounds(GetPumpkinBounds());
    	FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, blockIcon);
    	return true;
    }
	
	

}
