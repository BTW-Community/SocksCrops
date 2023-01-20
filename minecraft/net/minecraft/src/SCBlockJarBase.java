package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public abstract class SCBlockJarBase extends BlockContainer {

	protected SCBlockJarBase(int blockID) {
		super(blockID, Material.glass);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
	 * FCMOD: 3 = can be piston shoveled, but free otherwise 
     */
	@Override
	public int getMobilityFlag() {

		return 1;
	}
	
	@Override
	public void OnBrokenByPistonPush(World world, int i, int j, int k, int iMetadata) {
		
		onBlockHarvested(world, i, j, k, iMetadata, null);
	}
	
	// --- Block --- //	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public int damageDropped(int par1) {
		return 0;
	}
	
	// --- BlockContainer --- //	
	@Override
	public abstract TileEntity createNewTileEntity(World world);

	// --- Animals --- //	
	@Override
	public boolean startlesAnimalsWhenPlaced(World world, int x, int y, int z) {
		return false;
	}
	
	// --- Hopper --- //	
	@Override
	public boolean DoesBlockHopperInsert(World world, int i, int j, int k) {
		// WATCH OUT! This method is inverted
		int meta = world.getBlockMetadata(i, j, k);

		return isSideways(meta); //only inserts if it's NOT sideways
	}
	
	// --- Block Placement --- //
	@Override
    public boolean canPlaceBlockAt( World world, int x, int y, int z )
    { 
		if ( hasAttachableBlockAbove(world, x, y, z))
		{
			return super.canPlaceBlockAt( world, x, y, z );
		}
		else if ( FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ) )
		{
			return super.canPlaceBlockAt( world, x, y, z );
		}
		else return false;
    }
	
	protected void removeJar(World world, int x, int y, int z, ItemStack storageStack) {
		ItemStack emptyJar = new ItemStack(this, 1, 0);

		FCUtilsItem.DropStackAsIfBlockHarvested(world, x, y, z, emptyJar);
		
		if (storageStack != null)
			FCUtilsItem.DropStackAsIfBlockHarvested(world, x, y, z, storageStack);
		
		world.setBlockToAir(x, y, z);
		world.removeBlockTileEntity(x, y, z);
	}
	
	// --- Attachable Blocks --- //
	
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
	
    public static boolean hasAttachableBlockAbove(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int blockAbove = blockAccess.getBlockId(i, j + 1, k);

		return isAttachableBlock(blockAbove);
    }
    

    // --- Direction --- //
    
	//Directions
	protected final int SOUTH = 0;
	protected final int NORTH = 2;
	protected final int WEST = 1;
	protected final int EAST = 3;
	
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
    
    @Override
    public int SetFacing(int iMetadata, int iFacing) {

    	return iMetadata;
    }
    
    @Override
    public int GetFacing(IBlockAccess blockAccess, int i, int j, int k) {
    	return getDirection(blockAccess.getBlockMetadata(i, j, k));
    }
    
	protected void setRotation(World world, int i, int j, int k, EntityLiving player) {
		int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		
		if (player.isSneaking())
		{
			if (hasAttachableBlockAbove(world, i, j, k) && !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ))
			{
				world.setBlockMetadataWithNotify(i, j, k, playerRotation);
			}
			else world.setBlockMetadataWithNotify(i, j, k, playerRotation + 4);
		}
		else world.setBlockMetadataWithNotify(i, j, k, playerRotation);
	}
    
    // --- Turntable --- //
    
//  @Override
//  public boolean CanRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k)
//  {
//  	return true;
//  }
    
//	@Override
//	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
//	{
//		int iDirection = iMetadata;
//		
//		if ( iDirection == 0 )
//		{
//			iDirection = 3;
//		}
//		else if ( iDirection == 3 )
//		{
//			iDirection = 2;
//		}
//		else if ( iDirection == 2 )
//		{
//			iDirection = 1;
//		}
//		else if ( iDirection == 1 )
//		{
//			iDirection = 0;
//		}
//		
//		iMetadata = iDirection;
//		
//		return iMetadata;
//	}
    
	//----------- Client Side Functionality -----------//
	
	 /**
   * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
   * coordinates.  Args: blockAccess, x, y, z, side
   */
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i, int j, int k, int side)
	{
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
		    
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	// --- Bounds --- //
	
	protected float jarWidth = 8/16F;
	protected float jarHeight = 10/16F;
	
	protected float corkWidth = 6/16F;
	protected float corkHeight = 2/16F;
	
	protected float contentsWidth = 6/16F;
	protected float contentsHeight = 8/16F;
	
	protected float mindTheGap = 0.001F;
	
	public AxisAlignedBB GetBlockBoundsFromPoolForItemRender( int iItemDamage )
	{
		return getBounds(4, 0, 11, 4);
	}
	
	protected AxisAlignedBB getBounds(float halfWidth, float minY, float maxY, float halfDepth)
	{
		float centerX = 8/16F;
		float centerZ = 8/16F;
		
		return new AxisAlignedBB(
			centerX - halfWidth, minY, centerZ - halfDepth,
			centerX + halfWidth, maxY, centerZ + halfDepth);
	}
	
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
	
    public static boolean isFullBlock(int meta)
    {
    	if (meta < 8) return false;
    	else return true;
    }
		
	// --- Render --- //
	
	protected Icon glassSideShifted;
	
	protected Icon glassSide;
	private Icon glassSideTop;
	protected Icon glassTop;
	protected Icon glassBottom;
	
	protected Icon glassSideSideways;
	protected Icon glassSideSidewaysMirrored;
	protected Icon glassSideTopSideways;
	protected Icon glassTopSideways;
	protected Icon glassBottomSideways;
	
	protected Icon cork;
	protected Icon corkSideways;
	
	protected Icon gravel;	//for unknown contents
	protected Icon labelBlank;

	protected Icon[] contentsIcon = new Icon[64];
	protected Icon[] labelIcon = new Icon[64];
	
	protected Icon[] dyeIcon = new Icon[32];
	protected Icon[] dyeLabelIcon = new Icon[32];
	
	@Override
	public void registerIcons(IconRegister register) {
		glassSide = register.registerIcon("SCBlockJar_glassSide");
		glassSideShifted= register.registerIcon("SCBlockJar_glassSide_shifted");
		glassTop = register.registerIcon("SCBlockJar_glassTop");
		glassBottom = register.registerIcon("SCBlockJar_glassBottom");
		
		glassSideSideways = register.registerIcon("SCBlockJar_glassSide_sideways");
		glassSideSidewaysMirrored = register.registerIcon("SCBlockJar_glassSideMirrored_sideways");
		glassSideTopSideways = register.registerIcon("SCBlockJar_glassSideTop_sideways");
		glassTopSideways = register.registerIcon("SCBlockJar_glassTop_sideways");
		glassBottomSideways = register.registerIcon("SCBlockJar_glassBottom_sideways");
		
		cork = register.registerIcon("SCBlockJar_cork");
		corkSideways = register.registerIcon("SCBlockJar_cork_sideways");
		
		labelBlank = register.registerIcon("SCBlockJarLabel_blank");
		gravel = register.registerIcon("gravel");
	}
	/**
	 * Returns the index of the content in the validItemList, 
	 * Used for setting icons in registerIcons.
	*/
	protected abstract int getIndex(int seedType);

	protected abstract Icon getContentIcon(int seedType, int itemDamage);
	
	protected abstract Icon getLabelIcon(int seedType, int itemDamage);
	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAcces, int x, int y, int z, int side)
	{
		int meta = blockAcces.getBlockMetadata(x, y, z);
		int dir = getDirection(meta);
		
		
		if (!isSideways(meta))
		{			
			switch (side) {
			case 0:
				return glassBottom;
				
			case 1:
				return glassTop;
				
			default:
				if (hasAttachableBlockAbove(blockAcces, x, y, z))
				{
					return glassSideShifted;
				}
				else return glassSide;
			}			
		}
		else //isSideways
		{
			if (dir == SOUTH)
			{
				switch (side) {
				case 0:	
				case 1:
					return glassSideTopSideways;
					
				case 2:
					return glassBottomSideways;
				case 3:
					return glassTopSideways;
					
				case 4:
					return glassSideSideways;
				case 5:
					return glassSideSidewaysMirrored;
					
				default:
					return gravel;
				}
			}
			else if (dir == NORTH)
			{
				switch (side) {
				case 0:	
				case 1:
					return glassSideTopSideways;
					
				case 2:
					return glassTopSideways;
				case 3:
					return glassBottomSideways;
					
				case 4:
					return glassSideSidewaysMirrored;
				case 5:
					return glassSideSideways;
					
				default:
					return gravel;
				}
			}
			else if (dir == WEST)
			{
				switch (side) {
				case 0:	
				case 1:
					return glassSideTopSideways;
					
				case 2:
					return glassSideSideways;
				case 3:
					return glassSideSidewaysMirrored;
					
				case 4:
					return glassTopSideways;
				case 5:
					return glassBottomSideways;
					
				default:
					return gravel;
				}
			}
			else //EAST
			{
				switch (side) {
				case 0:	
				case 1:
					return glassSideTopSideways;
					
				case 2:
					return glassSideSidewaysMirrored;
				case 3:
					return glassSideSideways;
					
				case 4:
					return glassBottomSideways;
				case 5:
					return glassTopSideways;
					
				default:
					return gravel;
				}
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
	
	protected void renderJar(RenderBlocks renderer, int x, int y, int z, boolean hasLabel, int meta, int dir, ItemStack storageStack, int contentType) {
		float shiftUp = 0;
		
		if (hasAttachableBlockAbove(renderer.blockAccess, x, y, z)) shiftUp = 5/16F;

		renderCork(renderer, x, y, z, meta, dir, shiftUp);		
		
		renderGlass(renderer, x, y, z, meta, dir, shiftUp);

		if (hasLabel) renderLabel(renderer, x, y, z, meta, dir, shiftUp, storageStack, contentType);
		
		
	}
	
	private void renderGlass(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp)
	{
		if (!isSideways(meta))
		{			
			renderer.setRenderBounds(getBounds(jarWidth/2, mindTheGap + shiftUp, jarHeight + shiftUp, jarWidth/2));
			renderer.renderStandardBlock(this, x, y, z);
		}
		else
		{
			if (dir == SOUTH)
			{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
				
				renderer.SetUvRotateTop(3);
				renderer.SetUvRotateBottom(3);
			}
			else if (dir == NORTH)
			{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
			}
			else if (dir == WEST)
			{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
				
				renderer.SetUvRotateTop(2);
				renderer.SetUvRotateBottom(1);
			}
			else // if (dir == EAST)
			{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
				
				renderer.SetUvRotateTop(1);
				renderer.SetUvRotateBottom(2);
			}

			
			renderer.renderStandardBlock(this, x, y, z);
			
			renderer.ClearUvRotation();
		}
	}

	protected void renderContents(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp, ItemStack storageStack)
	{		
		int contentType = 0;
		int contentMeta = 0;
		
		if (storageStack != null)
		{
			contentType = storageStack.itemID;
			contentMeta = storageStack.getItemDamage();
		
		}
		
		float fillHeight = 0;
		
		if (storageStack != null)
		{
			fillHeight = storageStack.stackSize;
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
			
			if (dir == SOUTH || dir == NORTH)
			{
				renderer.setRenderBounds(
						0.5F - contentsWidth/2, mindTheGap*2, 0.5F - contentsHeight/2,
						0.5F + contentsWidth/2, fillLevel, 0.5F + contentsHeight/2);
			}
			else //if (dir == WEST || dir == EAST)
			{
				renderer.setRenderBounds(
						0.5F - contentsHeight/2, mindTheGap*2, 0.5F - contentsWidth/2,
						0.5F + contentsHeight/2, fillLevel, 0.5F + contentsWidth/2);
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
			if (dir == SOUTH)
			{
				renderer.setRenderBounds(
						8/16F - corkWidth/2, 1/16F, 13/16F - corkHeight/2,
						8/16F + corkWidth/2, 1/16F + corkWidth, 13/16F + corkHeight/2);
			}
			else if (dir == NORTH)
			{
				renderer.setRenderBounds(
						0.5F - corkWidth/2, 1/16F, 3/16F - corkHeight/2,
						0.5F + corkWidth/2, 1/16F + corkWidth, 3/16F + corkHeight/2);
			}
			else if (dir == WEST)
			{
				renderer.setRenderBounds(
						3/16F - corkHeight/2, 1/16F, 8/16F - corkWidth/2,
						3/16F + corkHeight/2, 1/16F + corkWidth, 8/16F + corkWidth/2);
			}
			else //if (dir == EAST)
			{
				renderer.setRenderBounds(
						13/16F - corkHeight/2, 1/16F, 0.5F - corkWidth/2,
						13/16F + corkHeight/2, 1/16F + corkWidth, 0.5F + corkWidth/2);
			}

			FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, corkSideways);
		}
	}
	
	private void renderLabel(RenderBlocks renderer, int x, int y, int z, int meta, int dir, float shiftUp, ItemStack storageStack, int contentType)
	{
		int itemDamage = 0;
		
		if (storageStack != null)
		{
			itemDamage = storageStack.getItemDamage();
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
    		else if (dir == NORTH)
    		{
    			renderer.renderFaceZNeg(this, x, y + shiftUp, z - mindTheGap, labelIcon);
    		}
    		else if (dir == WEST)
    		{
    			renderer.renderFaceXNeg(this, x  - mindTheGap, y + shiftUp, z, labelIcon);
    		}
    		else // if (dir == EAST)
    		{
    			renderer.renderFaceXPos(this, x  + mindTheGap, y + shiftUp, z , labelIcon);
    		}
        }
        else
        {
        	renderer.setRenderBounds(0,0,0,1,jarWidth,1);
			
        	if (dir == SOUTH)
        	{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
        		
        		renderer.SetUvRotateTop(3);
        		renderer.renderFaceYPos(this, x, y + mindTheGap, z + 3/16D, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else if (dir == NORTH)
        	{
				renderer.setRenderBounds(
						0.5F - jarWidth/2, mindTheGap, 0.5F - jarHeight/2,
						0.5F + jarWidth/2, jarWidth, 0.5F + jarHeight/2);
        		
        		renderer.SetUvRotateTop(0);
        		renderer.renderFaceYPos(this, x, y + mindTheGap, z - 3/16D, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else if (dir == WEST)
        	{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
        		
        		renderer.SetUvRotateTop(2);
        		renderer.renderFaceYPos(this, x - 3/16D, y + mindTheGap, z, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        	else // if (dir == EAST)
        	{
				renderer.setRenderBounds(
						0.5F - jarHeight/2, mindTheGap, 0.5F - jarWidth/2,
						0.5F + jarHeight/2, jarWidth, 0.5F + jarWidth/2);
        		
        		renderer.SetUvRotateTop(1);
        		renderer.renderFaceYPos(this, x + 3/16D, y + mindTheGap, z, labelIcon);
        		
        		renderer.ClearUvRotation();
        	}
        }
	}
		
	// --- BLOCK AND ITEM DATA CONVERSION --- //
	
	/**
	 * 
	 * @param label
	 * @param fill
	 * @param damage
	 * @param typeID
	 * @return damage value, combining the input par
	 */
    public static int dataToDamage(boolean label, int fill, int damage , int typeID)
    {
    	int labelVal;
    	
    	// set first bit for label
    	if (label)
    	{
    		labelVal = 1;
    	}
    	else labelVal = 0;
    	
		// shift fill bits and set them
		int fillVal = fill << 1; // 0-7

		// shift damage bits and set them
		int damageVal = damage << 4; // 0-31

		// shift type bits and set them
		int typeVal = typeID << 9; // 0-63

		// set jars damage by ORing the different shifted bit together
		return labelVal | fillVal | damageVal | typeVal;

    }

    /**
     * 
     * @param jarDamageVal
     * @return int[] where 0 = label, 1 = fill, 2 = damage, 3 = type 
     */
    public static int[] damageToData( int jarDamageVal )
    {
    	//shift type bits and return the value 
    	int newtype = jarDamageVal >> 9; 

    	//shift damage bits by 4 (skipping fill and label bits
    	int shiftdamage = jarDamageVal >> 4;

    	// AND check if damage bits are 1 or 0
    	int newdamage = shiftdamage & 31;
    	
    	//shift fill bits by one
    	int shiftfill = jarDamageVal >> 1; 

    	// AND check if fill bits are 1 or 0
    	int newfill = shiftfill & 7;

    	// AND check to see if label bit is 1 or 0
    	int newlabel = jarDamageVal & 1; 
    	
    	return new int[] {newlabel, newfill, newdamage, newtype};
    }
}
