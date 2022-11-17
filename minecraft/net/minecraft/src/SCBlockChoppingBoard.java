package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCBlockChoppingBoard extends BlockContainer {

	private static ArrayList<Item> specialRenderItems = new ArrayList();
	
	static
	{
		//specialRenderItems.add(Item.cake);
	}
	
	protected SCBlockChoppingBoard(int blockID) {
		super(blockID, Material.wood);
		
        setHardness( 0.5F );        
		
        setStepSound( soundWoodFootstep );   
        
        SetFireProperties( FCEnumFlammability.PLANKS );
        
//        setCreativeTab( CreativeTabs.tabDecorations );
        
        setTickRandomly(true);
        
        setUnlocalizedName( "SCBlockChoppingBoard" );
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityChoppingBoard();
	}
	
	
    private boolean hasSpecialRenderer(Item item) {
		return specialRenderItems.contains(item);
	}
	
    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	SCTileEntityChoppingBoard choppingBoard = (SCTileEntityChoppingBoard)world.getBlockTileEntity( i, j, k );

    	ItemStack knifeStack = choppingBoard.getKnifeStack();
    	ItemStack cuttingStack = choppingBoard.getCuttingStack();

    	ItemStack heldStack = player.getCurrentEquippedItem();

    	if (choppingBoard != null)
    	{
    		if (heldStack != null)
    		{
    			if ( knifeStack == null )
    			{
    				ItemStack stackToStore = heldStack.splitStack(1);

    				choppingBoard.setInventorySlotContents(0, stackToStore);

    				if (!world.isRemote)
    				{  
    					world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );	
    				}

    				return true;
    			}
    			else 
    			{
    				//knifeStack has item

    				//Item itemOnBoard = choppingBoard.getKnifeStack().getItem();
    				ItemStack onBoardStack = choppingBoard.getKnifeStack();

    				SCCraftingManagerChoppingBoardFilterRecipe recipe = onBoardStack == null ?
    						null : SCCraftingManagerChoppingBoardFilter.instance.getRecipe(heldStack, onBoardStack);

    				boolean recipe3 = SCCraftingManagerChoppingBoardFilter.instance.hasRecipe( onBoardStack );

    				if (recipe != null) {

    					ItemStack[] output = recipe.getBoardOutput();

    					world.playAuxSFX( SCCustomAuxFX.choppingBoardAuxFXID, i, j, k, 0);

    					assert( output != null && output.length > 0 );

    					for ( int listIndex = 0; listIndex < output.length; listIndex++ )
    					{
    						ItemStack cutStack = output[listIndex].copy();

    						if ( cutStack != null )
    						{
    							if (recipe3 == true)
    							{
    								System.out.println("Recipe2 is NOT null?" + recipe3);
    								choppingBoard.setKnifeStack(cutStack);
    							}
    							
    							
    							
    							if (recipe3 == false) {

    								System.out.println("Recipe2 is null?" + recipe3);
    								if (!world.isRemote)
    								{   
    									world.playSoundAtEntity( player, this.stepSound.getStepSound(), this.stepSound.getVolume() * 0.2F, this.stepSound.getPitch() * 3F );

    									int dir = getDirection(world.getBlockMetadata(i, j, k));
    									dir = Direction.rotateOpposite[dir];
    									int facing = Direction.directionToFacing[dir];


    									FCUtilsItem.EjectStackFromBlockTowardsFacing( world, i, j, k, cutStack, facing );

    								}

    								player.playSound( "mob.zombie.wood", 0.05F, 2.5F * 10 );
    							}

    						}
    					}

//    					ItemStack arrow = GetFirstArrowStackInHotbar(player, recipe.getStackOnBoard() );
//
//    					if (arrow != null)
//    					{        	            	        	            	
//    						ItemStack stackToStore;
//
//    						if (arrow.stackSize > 1)
//    						{
//    							stackToStore = arrow.splitStack(1);
//    							choppingBoard.setInventorySlotContents(0, stackToStore);
//    						}
//    						else
//    						{
//    							player.inventory.consumeInventoryItem( arrow.itemID );
//    							choppingBoard.setInventorySlotContents(0, arrow);
//    						}
//
//    						if (!world.isRemote)
//    						{  
//    							world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );	
//    						}
//
//    					}
//    					else choppingBoard.setKnifeStack(null);

    					if ( heldStack.getItem() instanceof FCItemTool )
    					{
    						heldStack.attemptDamageItem(1, world.rand);

    						int maxDamage = heldStack.getMaxDamage();
    						if (heldStack.getItemDamage() >= maxDamage)
    						{
    							//break tool
    							heldStack.stackSize = 0;
    							player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );            	            	
    						}


    					}


    					return true;   					
    				}

    			}

    		}
    		else
    		{
    			if ( knifeStack != null )
    			{
    				//hand is empty	    			
    				FCUtilsItem.GivePlayerStackOrEject( player, knifeStack, i, j, k );

    				choppingBoard.setKnifeStack(null);

    				if (!world.isRemote)
    				{  
    					world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );	
    				}

    				return true;

    			}
    		}
    	}

    	return false;

    }



	private ItemStack GetFirstArrowStackInHotbar(EntityPlayer player, ItemStack itemOnBoard) {
    	for ( int i = 0; i < 9; i++ )
    	{
    		ItemStack tempStack = player.inventory.getStackInSlot( i );
    		
    		if ( tempStack != null && tempStack.itemID == itemOnBoard.itemID && tempStack.getItemDamage() == itemOnBoard.getItemDamage())
    		{
    			return tempStack;
    		}
    	}
    	
    	return null;
	}
    
	@Override
	public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity )
	{
		// don't collect items on the client, as it's dependent on the state of the inventory

		if ( !world.isRemote )
		{
			if ( entity instanceof EntityItem )
			{
				OnEntityItemCollidedWithBlock( world, i, j, k, (EntityItem)entity );
			}
		}		
	}
	
	private void OnEntityItemCollidedWithBlock(World world, int i, int j, int k, EntityItem entityItem)
	{
		float collisionBoxHeight = 2/16F;
		
		List collisionList = null;

		// check for items within the collection zone

		collisionList = world.getEntitiesWithinAABB(EntityItem.class,
				AxisAlignedBB.getAABBPool().getAABB((float) i, (float) j + collisionBoxHeight, (float) k,
						(float) (i + 1), (float) j + collisionBoxHeight + 0.05F, (float) (k + 1)));

		if (collisionList != null && collisionList.size() > 0)
		{
			TileEntity tileEnt = world.getBlockTileEntity(i, j, k);

			if (!(tileEnt instanceof IInventory)) {
				return;
			}
			
			IInventory inventoryEntity = (IInventory) tileEnt;
			
			

			for (int listIndex = 0; listIndex < collisionList.size(); listIndex++) {
				EntityItem targetEntityItem = (EntityItem) collisionList.get(listIndex);
				
				if ( !inventoryEntity.isStackValidForSlot(0, targetEntityItem.getEntityItem() ))
				{
					return;
				}
				
				if (!targetEntityItem.isDead) {
					if (FCUtilsInventory.AddItemStackToInventory(inventoryEntity, targetEntityItem.getEntityItem())) {
						world.playSoundEffect((double) i + 0.5D, (double) j + 0.5D, (double) k + 0.5D, "random.pop",
								0.25F, ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);

						targetEntityItem.setDead();
					}
				}
			}
		}
	}
	
	//Cutting by having the tool on the board
//	@Override
//    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
//    {
//		SCTileEntityChoppingBoard choppingBoard = (SCTileEntityChoppingBoard)world.getBlockTileEntity( i, j, k );
//        
//        ItemStack knifeStack = choppingBoard.getKnifeStack();
//        ItemStack cuttingStack = choppingBoard.getCuttingStack();
//		
//    	ItemStack heldStack = player.getCurrentEquippedItem();
//    	
//    	if (choppingBoard != null)
//    	{
//    		if (heldStack != null)
//    		{
//    			if ( knifeStack == null )
//    			{
//			        ItemStack stackToStore = heldStack.splitStack(1);
//			        
//			        choppingBoard.setInventorySlotContents(0, stackToStore);
//			        
//			        return true;
//    			}
//    			else 
//    			{
//    				//knifeStack != null
//    				
//    				Item filterItem = choppingBoard.getKnifeStack().getItem();
//    				
//    				SCCraftingManagerChoppingBoardFilterRecipe recipe = filterItem == null ?
//							null : SCCraftingManagerChoppingBoardFilter.instance.getRecipe(heldStack, new ItemStack(filterItem));
//    				
//    				if (recipe != null) {
//        				if (cuttingStack == null)
//        				{
//        			        ItemStack stackToCut = heldStack.splitStack(1);
//        			        
//        			        choppingBoard.setInventorySlotContents(1, stackToCut);
//        			        
//        			        return true;
//        				}    					
//    				}	
//    			}
//    			
//    		}
//    		else
//    		{
//    			if ( knifeStack != null )
//    			{
//	    			//hand is empty	    			
//    		        FCUtilsItem.GivePlayerStackOrEject( player, knifeStack, i, j, k );
//    		        
//    		        choppingBoard.setKnifeStack(null);
//    		        
//    		        return true;
//			        
//    			}
//    		}
//    	}
//    	
//		return false;
//    }
	


	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
        FCUtilsInventory.EjectInventoryContents( world, i, j, k, (IInventory)world.getBlockTileEntity( i, j, k ) );

        super.breakBlock( world, i, j, k, iBlockID, iMetadata );
    }
	
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entity, ItemStack stack)
    {
        int setMeta = ((MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3)) % 4;
        world.setBlockMetadataWithNotify(i, j, k, setMeta, 3);

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
	
    public static int getDirection(int metadata)
    {
        return (metadata & 3);
    }
	
    //------------- Render ------------//
    
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
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide)
	{
		return true;
	}
    
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
    	
    	
    	SCTileEntityChoppingBoard choppingBoard = (SCTileEntityChoppingBoard)renderBlocks.blockAccess.getBlockTileEntity( i, j, k );    	
    	ItemStack itemStack = choppingBoard.getKnifeStack();
    	Item item;
    	
    	choppingBoard.setItemHasSpecialRenderer(false);
    	
    	if (itemStack != null)
    	{
    		item = itemStack.getItem();
    		
    		if (hasSpecialRenderer(item) )
            {
            	choppingBoard.setItemHasSpecialRenderer(true);

            	//renderBlocks.setRenderBounds(0.25,0,0.25,0.75,0.5,0.75);
            	//renderBlocks.renderStandardBlock(cake, i, j, k);
            }
    	}

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
