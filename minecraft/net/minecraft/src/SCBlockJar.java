package net.minecraft.src;

import java.util.ArrayList;

public class SCBlockJar extends BlockContainer {

	//Directions
	private final int SOUTH = 0;
	private final int NORTH = 2;
	private final int WEST = 1;
	private final int EAST = 3;

	protected SCBlockJar(int par1) {
		super(par1, Material.glass);
		
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("SCBlockJar");
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int iNeighborBlockID)
	{
        if (!hasAttachableBlockAbove(world, x, y, z) && !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ) )
        {
    		SCTileEntityJar jar = (SCTileEntityJar)( world.getBlockTileEntity(x, y, z) );
    		ItemStack stack = new ItemStack(this, 1, this.getDamageValue(world, x, y, z));
    		int meta = world.getBlockMetadata(x, y, z);
    		int dir = getDirection(meta);
    		boolean isSideways = isSideways(meta);
    		
    		//drop jar with meta 0 and chance 0
    		dropBlockAsItem( world, x, y, z, 0, 0 );
    		
    		if (isSideways)
    		{
    			ejectAllContents(Direction.directionToFacing[dir], jar);
    		}
    		else ejectAllContents(1, jar);
    		
        	world.setBlockToAir(x, y, z);
        	world.removeBlockTileEntity(x, y, z);
        	
        }

	}
	
	@Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int facing, float xClick, float yClick, float zClick )
    {
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntityJar jar = (SCTileEntityJar) world.getBlockTileEntity(x, y, z);
		int contentType = jar.getContentType();
		
		if (jar != null)
		{
			//Player is holding something
			if (heldStack != null)
			{
				//Jar doesn't have a label and player is holding paper
				if (!jar.hasLabel() && heldStack.itemID == Item.paper.itemID)
				{
					jar.setHasLabel(true);
					jar.markBlockForUpdate();
					
					if (!player.capabilities.isCreativeMode)
					{
						heldStack.stackSize--;
					}
					
					return true;
				}
				else //try add item to inventory
				{
		    		if ( validItemList.contains( heldStack.itemID ) )
		    		{
						//only allow one type in the jar
						if (contentType == 0 || contentType == heldStack.itemID)
						{
							FCUtilsInventory.AddItemStackToInventory(jar, heldStack);
							jar.onInventoryChanged();
							
							return true;
						}
		    		}
				}
				

				
			}
			else if (heldStack == null) //Empty hand
			{
				if (player.isSneaking())
				{
					//Eject only single item
				}
				else
				{
					ejectAllContents(facing, jar);
					
					FCTileEntityHopper hopper = (FCTileEntityHopper) world.getBlockTileEntity(x, y + 1, z);
					if (hopper != null)
					{
						hopper.AttemptToEjectStackFromInv();
					}
				}
				jar.onInventoryChanged();
				

				
				return true;
			}
			
			
		}

		return false;

	
	}

	private void ejectAllContents(int facing, SCTileEntityJar jar) {
		for (int slot = 0; slot < jar.getSizeInventory(); slot++) {
			jar.ejectContentsInSlotTowardsFacing(slot, facing);
		}
	}
	
	@Override
    public boolean canPlaceBlockAt( World world, int x, int y, int z )
    { 
		if ( hasAttachableBlockAbove(world, x, y, z) ) 
		{
			return super.canPlaceBlockAt( world, x, y, z );
		}
		else
		{
			if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ) )
			{
				return false; 	
			}
			else return super.canPlaceBlockAt( world, x, y, z );
		}
    }
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack itemStack)
	{	
		int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		
		if (player.isSneaking())
		{
			if (hasAttachableBlockAbove(world, x, y, z) && !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ))
			{
				world.setBlockMetadataWithNotify(x, y, z, playerRotation);
			}
			else world.setBlockMetadataWithNotify(x, y, z, playerRotation + 4);	
		}
		else world.setBlockMetadataWithNotify(x, y, z, playerRotation);	
	}
	
	
	// --- Attachable Blocks --- //
	
    public static boolean hasAttachableBlockAbove(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int blockAbove = blockAccess.getBlockId(i, j + 1, k);

		return isAttachableBlock(blockAbove);
    }
	
	private static ArrayList<Integer> attachableBlocks = new ArrayList<Integer>();
	
	public static void addAttachableBlock(int id)
	{
		attachableBlocks.add(id);
	}
	
	public static boolean isAttachableBlock(int id)
	{
		return attachableBlocks.contains(id);
	}
	
	static {
		addAttachableBlock(FCBetterThanWolves.fcHopper.blockID);
		addAttachableBlock(Block.fence.blockID);
	}
	
	// --- BlockContainer --- //
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCTileEntityJar();
	}
	
	// --- Direction --- //
	/**
     * Returns the orentation value from the specified metadata
     */
    public static int getDirection(int meta)
    {
        return meta & 3;
    }
    
    public static boolean isSideways(int meta)
    {
    	if (meta < 4) return false;
    	else return true;
    }
	
	// --- Render related --- //
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		return true;
	}
	
	// --- Bounds ---//
	
	
	private float jarWidth = 8/16F;
	private float jarHeight = 10/16F;
	
	private float corkWidth = 6/16F;
	private float corkHeight = 2/16F;
	
	private float contentsWidth = 6/16F;
	private float contentsHeight = 8/16F;
	
	private float mindTheGap = 0.001F;

	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
	{
		int meta = blockAccess.getBlockMetadata(x, y, z);
		int dir = getDirection(meta);
		
		float shiftUp = 0;
		
		if (hasAttachableBlockAbove(blockAccess, x, y, z))
		{
			shiftUp = 5/16F;
		}	
		
		if (isSideways(meta))
		{
			if (dir == WEST || dir == EAST)
			{
				return new AxisAlignedBB(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
			}
			else
			{
				return new AxisAlignedBB(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
			}
		}
		else return getBounds(jarWidth/2, 0 + shiftUp, jarHeight + shiftUp, jarWidth/2);
	}
	
	private AxisAlignedBB getBounds(float halfWidth, float minY, float maxY, float halfDepth)
	{
		float centerX = 8/16F;
		float centerZ = 8/16F;
		
		return new AxisAlignedBB(
			centerX - halfWidth, minY, centerZ - halfDepth,
			centerX + halfWidth, maxY, centerZ + halfDepth);
	}
	
	// --- Client Side --- //
	
	private Icon glassSide;
	private Icon glassSideShifted;
	private Icon glassTop;
	
	private Icon glassSideSideways;
	private Icon glassSideSidewaysOpposite;
	private Icon glassSideTopSideways;
	private Icon glassTopSideways;
	
	private Icon cork;
	private Icon corkSideways;
	
	private Icon gravel;	//for unknown contents
	private Icon labelBlank;
	
	protected Icon[] contentsIcon = new Icon[64];
	protected Icon[] labelIcon = new Icon[64];
	
	protected Icon[] dyeIcon = new Icon[32];
	protected Icon[] dyeLabelIcon = new Icon[32];
	
	@Override
	public void registerIcons(IconRegister register) {
		
		glassSide = register.registerIcon("SCBlockJar_side");
		glassSideShifted= register.registerIcon("SCBlockJar_sideTop");
		glassTop = register.registerIcon("SCBlockJar_top");
		
		glassSideSideways = register.registerIcon("SCBlockJar_sideway_side");
		glassSideSidewaysOpposite = register.registerIcon("SCBlockJar_sideway_side_other");
		glassSideTopSideways = register.registerIcon("SCBlockJar_sideway_topSide");
		glassTopSideways = register.registerIcon("SCBlockJar_sideway_top");
		
		cork = register.registerIcon("SCBlockJar_cork");
		corkSideways = register.registerIcon("SCBlockJar_sideway_cork");
		
		labelBlank = register.registerIcon("SCBlockJarLabel_blank");
		gravel = register.registerIcon("gravel");
		
		//CROPS
		contentsIcon[1] = register.registerIcon("SCBlockJarContents_melon");
		contentsIcon[2] = register.registerIcon("SCBlockJarContents_pumpkin");
		contentsIcon[3] = register.registerIcon("SCBlockJarContents_netherwart");
		contentsIcon[4] = register.registerIcon("SCBlockJarContents_hemp");
		contentsIcon[5] = register.registerIcon("SCBlockJarContents_wheat");
		contentsIcon[6] = register.registerIcon("SCBlockJarContents_carrot");

		labelIcon[1] = register.registerIcon("SCBlockJarLabel_melon");
		labelIcon[2] = register.registerIcon("SCBlockJarLabel_pumpkin");
		labelIcon[3] = register.registerIcon("SCBlockJarLabel_netherwart");
		labelIcon[4] = register.registerIcon("SCBlockJarLabel_hemp");
		labelIcon[5] = register.registerIcon("SCBlockJarLabel_wheat");
		labelIcon[6] = register.registerIcon("SCBlockJarLabel_carrot");
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAcces, int x, int y, int z, int side)
	{
		int meta = blockAcces.getBlockMetadata(x, y, z);
		int dir = getDirection(meta);
		
		if (isSideways(meta))
		{
			if (dir == SOUTH || dir == NORTH)
			{
//				if (side == 2 && dir == SOUTH) return gravel; //bottom
//				else if (side == 3 && dir == NORTH) return gravel; //bottom
				
				if (side == 0 || side == 1)
				{
					return glassSideTopSideways;
				}
				else if (side == 2 || side == 3)
				{
					return glassTopSideways;
				}
				else
				{
					if (side == 4 && dir == NORTH) return glassSideSidewaysOpposite;
					else if (side == 5 && dir == SOUTH) return glassSideSidewaysOpposite;
					else return glassSideSideways;
				}
			}
			else if (dir == WEST || dir == EAST)
			{				
//				if (side == 4 && dir == EAST) return gravel; //bottom
//				else if (side == 5 && dir == WEST) return gravel; //bottom
				
				if (side == 0 || side == 1)
				{
					
					return glassSideTopSideways;
				}
				else if (side == 2 || side == 3)
				{
					if (side == 2 && dir == EAST) return glassSideSidewaysOpposite;
					else if (side == 3 && dir == WEST) return glassSideSidewaysOpposite;
					else return glassSideSideways;
				}
				else
				{
					return glassTopSideways;
				}
			}
			
			return gravel;

		}
		else //standing
		{
			if (side == 0 || side == 1)
			{
				return glassTop;
			}
			else
			{
				if (hasAttachableBlockAbove(blockAcces, x, y, z))
				{
					return glassSideShifted;
				}
				else return glassSide;
			}
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{		
		if (side == 0 || side == 1)
		{
			return glassTop;
		}
		else
		{
			return glassSide;
		}
	}
	
	private Icon getContentIcon(int contentType, int itemDamage)
	{
		if ( validItemList.contains(contentType) )
		{
			if (contentType == Item.dyePowder.itemID)
			{
				return dyeIcon[itemDamage];
			}
			else return contentsIcon[validItemList.indexOf(contentType)];
		}
		else return gravel;
	}
	
	private Icon getLabelIcon(int contentType, int itemDamage)
	{		
		if ( validItemList.contains(contentType) )
		{
			if (contentType == Item.dyePowder.itemID)
			{
				return dyeLabelIcon[itemDamage];
			}
			else return labelIcon[validItemList.indexOf(contentType)];
		}
		else return labelBlank;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
		
		SCTileEntityJar jar = (SCTileEntityJar) renderer.blockAccess.getBlockTileEntity(x, y, z);
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
		int dir = getDirection(meta);
		float shiftUp = 0;
		
		if (hasAttachableBlockAbove(renderer.blockAccess, x, y, z))
		{
			shiftUp = 5/16F;
		}		
		
		renderCork(renderer, x, y, z, meta, dir, shiftUp);
		
		
		renderJar(renderer, x, y, z, meta, dir, shiftUp);

		if (jar.hasLabel()) renderLabel(renderer, x, y, z, meta, dir, shiftUp);
		
		renderContents(renderer, x, y, z, meta, dir, shiftUp);
		
		return true;
	}

	private void renderJar(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp)
	{
		if (!isSideways(meta))
		{			
			renderer.setRenderBounds(getBounds(jarWidth/2, mindTheGap + shiftUp, jarHeight + shiftUp, jarWidth/2));
			renderer.renderStandardBlock(this, x, y, z);
		}
		else
		{
			if (dir == WEST || dir == EAST)
			{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
				
				if (dir == WEST)
				{
					renderer.SetUvRotateTop(3);
					renderer.SetUvRotateBottom(4);					
				}
				else if (dir == EAST)
				{
					renderer.SetUvRotateTop(4);
					renderer.SetUvRotateBottom(3);
				}
			}
			else
			{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
				
				if (dir == SOUTH)
				{
					renderer.SetUvRotateTop(1);
					renderer.SetUvRotateBottom(2);
					
					
				}
				else if (dir == NORTH)
				{
					renderer.SetUvRotateTop(2);
					renderer.SetUvRotateBottom(1);
				}
			}

			renderer.renderStandardBlock(this, x, y, z);
			
			renderer.ClearUvRotation();
		}
	}

	private void renderContents(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp)
	{		
		SCTileEntityJar jar = (SCTileEntityJar) renderer.blockAccess.getBlockTileEntity(x, y, z);
		int contentType = 0;
		int contentMeta = 0;
		
		if (jar.getStackInSlot(0) != null)
		{
			contentType = jar.getStackInSlot(0).itemID;
			contentMeta = jar.getStackInSlot(0).getItemDamage();
		
		}
		
		float fillHeight = 0;
		
		for (int slot = 0; slot < 4; slot++)
		{
			if (jar.getStackInSlot(slot) != null)
			{
				fillHeight += jar.getStackInSlot(slot).stackSize;
			}
		}
		
		if (!isSideways(meta))
		{
			fillHeight /= 8;		
			float fillLevel = MathHelper.ceiling_float_int(fillHeight)/16F;
			
			if (!(fillLevel > 0))
			{
				return; //ie. don't render the Contents
			}
			
			renderer.setRenderBounds(getBounds(contentsWidth/2, mindTheGap*2  + shiftUp, fillLevel + shiftUp, contentsWidth/2));
			FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, getContentIcon(contentType, contentMeta));
		}
		else
		{
			fillHeight /= 12;		
			float fillLevel = MathHelper.ceiling_float_int(fillHeight)/16F;
			
			if (!(fillLevel > 0))
			{
				return; //ie. don't render the Contents
			}
			
			if (dir == WEST || dir == EAST)
			{
				renderer.setRenderBounds(
						0.5F - contentsHeight/2, mindTheGap*2, 0.5F - contentsWidth/2,
						0.5F + contentsHeight/2, fillLevel, 0.5F + contentsWidth/2);
			}
			else
			{
				renderer.setRenderBounds(
						0.5F - contentsWidth/2, mindTheGap*2, 0.5F - contentsHeight/2,
						0.5F + contentsWidth/2, fillLevel, 0.5F + contentsHeight/2);
			}
			

			FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, getContentIcon(contentType, contentMeta));
		}
	}

	private void renderCork(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp)
	{
		if (!isSideways(meta))
		{
			float corkY = jarHeight - 1/16F;
			renderer.setRenderBounds( getBounds(corkWidth/2, corkY + shiftUp, corkY + corkHeight + shiftUp, corkWidth/2) );
			FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, cork);
		}
		else
		{
			if (dir == EAST)
			{
				renderer.setRenderBounds(
						13/16F - corkHeight/2, 1/16F, 0.5F - corkWidth/2,
						13/16F + corkHeight/2, 1/16F + corkWidth, 0.5F + corkWidth/2);
			}
			else if (dir == WEST)
			{
				renderer.setRenderBounds(
						3/16F - corkHeight/2, 1/16F, 8/16F - corkWidth/2,
						3/16F + corkHeight/2, 1/16F + corkWidth, 8/16F + corkWidth/2);
			}
			else if (dir == SOUTH)
			{
				renderer.setRenderBounds(
						8/16F - corkWidth/2, 1/16F, 13/16F - corkHeight/2,
						8/16F + corkWidth/2, 1/16F + corkWidth, 13/16F + corkHeight/2);
			}
			else //NORTH
			{
				renderer.setRenderBounds(
						0.5F - corkWidth/2, 1/16F, 3/16F - corkHeight/2,
						0.5F + corkWidth/2, 1/16F + corkWidth, 3/16F + corkHeight/2);
			}
			
			FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, corkSideways);
		}
	}

	private void renderLabel(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp)
	{
		SCTileEntityJar jar = (SCTileEntityJar) renderer.blockAccess.getBlockTileEntity(x, y, z);
		
		int contentType = jar.getContentType();
		int itemDamage = 0;
		
		if (jar.getStackInSlot(0) != null)
		{
			itemDamage = jar.getStackInSlot(0).getItemDamage();
		}
		
		Icon labelIcon = getLabelIcon(contentType, itemDamage);
		
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        tess.setColorOpaque_F(1F, 1F, 1F);
		
        if (!isSideways(meta))
        {
        	renderer.setRenderBounds(getBounds(jarWidth/2, 0, 1, jarWidth/2));
        	
    		if (dir == SOUTH)
    		{
    			renderer.renderFaceZPos(this, x, y + shiftUp, z  + mindTheGap, labelIcon);
    		}
    		else if (dir == WEST)
    		{
    			renderer.renderFaceXNeg(this, x  - mindTheGap, y + shiftUp, z, labelIcon);
    		}
    		else if (dir == NORTH)
    		{
    			renderer.renderFaceZNeg(this, x, y + shiftUp, z - mindTheGap, labelIcon);
    		}
    		else if (dir == EAST)
    		{
    			renderer.renderFaceXPos(this, x  + mindTheGap, y + shiftUp, z , labelIcon);
    		}
        }
        else
        {
        	
        	if (dir == WEST || dir == EAST)
			{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
			}
			else
			{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
			}
        	
        	renderer.setRenderBounds(0,0,0,1,jarWidth,1);
			
        	if (dir == SOUTH)
        	{
        		renderer.SetUvRotateTop(3);
        		renderer.renderFaceYPos(this, x, y + mindTheGap, z + 3/16D, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else if (dir == WEST)
        	{
        		renderer.SetUvRotateTop(2);
        		renderer.renderFaceYPos(this, x - 3/16D, y + mindTheGap, z, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else if (dir == NORTH)
        	{
        		renderer.SetUvRotateTop(0);
        		renderer.renderFaceYPos(this, x, y + mindTheGap, z - 3/16D, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else if (dir == EAST)
        	{
        		renderer.SetUvRotateTop(1);
        		renderer.renderFaceYPos(this, x + 3/16D, y + mindTheGap, z, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        }
	}
	
	
	// --- Valid Items --- //
	
	private static ArrayList<Integer> validItemList = new ArrayList<Integer>();
	
	protected static ArrayList<Integer> getValidItemList() {
		return validItemList;
	}
	
	public void addValidItemToStore(int itemID)
	{
		validItemList.add(itemID);
	}
	
	static
    {
    	validItemList.add( Item.dyePowder.itemID );
    	
    	//CROPS
    	validItemList.add( Item.melonSeeds.itemID );
    	validItemList.add( Item.pumpkinSeeds.itemID );    	
    	validItemList.add( Item.netherStalkSeeds.itemID );
    	
    	validItemList.add( FCBetterThanWolves.fcItemHempSeeds.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemWheatSeeds.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemCarrotSeeds.itemID );
    	
    	//BREWING
    	validItemList.add( Item.spiderEye.itemID );
    	validItemList.add( Item.fermentedSpiderEye.itemID );
    	validItemList.add( Item.magmaCream.itemID );
    	validItemList.add( Item.ghastTear.itemID );
    	validItemList.add( Item.blazePowder.itemID );
    	validItemList.add( Item.gunpowder.itemID );
    	
    	validItemList.add( FCBetterThanWolves.fcItemMushroomRed.itemID );    	
    	validItemList.add( FCBetterThanWolves.fcItemMysteriousGland.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemWitchWart.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemSoulFlux.itemID );
    	
    	//BAIT
    	validItemList.add(Item.rottenFlesh.itemID);
    	validItemList.add(FCBetterThanWolves.fcItemBatWing.itemID);
    	validItemList.add(FCBetterThanWolves.fcItemCreeperOysters.itemID);
    	
    	//OTHER
    	validItemList.add( FCBetterThanWolves.fcItemNitre.itemID);
    	validItemList.add( FCBetterThanWolves.fcItemMushroomBrown.itemID );  
    	validItemList.add( Item.slimeBall.itemID );    

    	//OTHER DUSTS/POWDERS
    	validItemList.add( Item.redstone.itemID );
    	validItemList.add( Item.lightStoneDust.itemID );
    	validItemList.add( Item.sugar.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemFlour.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemCocoaBeans.itemID );
    	validItemList.add( FCBetterThanWolves.fcItemChickenFeed.itemID );
    	
    	//SC
//    	validItemList.add( SCDefs.wildCarrotSeeds.itemID);
//    	validItemList.add( SCDefs.wildCarrotCropSapling.blockID);
    	
    	if( SCDecoIntegration.isDecoInstalled() )
    	{
    		validItemList.add( SCDecoIntegration.fertilizer.itemID );
    		validItemList.add( SCDecoIntegration.amethystShard.itemID );
    	}
    }
}
