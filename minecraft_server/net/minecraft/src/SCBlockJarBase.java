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
