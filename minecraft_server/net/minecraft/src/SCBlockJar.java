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
