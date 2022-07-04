//Based on: https://github.com/BTW-Community/Deco-Addon-2/blob/master/minecraft/net/minecraft/src/DecoBlockFlowerPot.java

package net.minecraft.src;

import java.util.Random;

public class SCBlockWaterPot extends BlockContainer {
	
	public static int empty = 0;
	public static int water = 8;
	
	public SCBlockWaterPot(int id) {
		super(id, Material.circuits);
        this.InitBlockBounds(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
		this.setHardness(0.0F);
		this.setStepSound(soundPowderFootstep);
		this.setUnlocalizedName("flowerPot");
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		int storedBlockID = potTile.getStoredBlockID();
		int growthStage = getGrowthStage(meta);
		
		if ( !hasWater(meta) )
		{
			return; // Don't do anything unless there is a light block above
		}
		
		if (rand.nextFloat() <= getGrowthChance(world, x,y,z) && potTile.hasItem() )
		{
			if(storedBlockID == Block.sapling.blockID)
			{
				if (growthStage < potTile.getGrowthStagesForBlock(storedBlockID) )
				{
					//System.out.println("Growing "+ meta);
					world.setBlockMetadataWithNotify(x, y, z, meta + 1);
					potTile.setStoredBlockMetadata(potTile.getStoredBlockMetadata() + 4);

					potTile.markBlockForRender();
					
					if (growthStage == potTile.getGrowthStagesForBlock(storedBlockID) - 1)
					{
						//System.out.println("clearing water "+ meta);
						world.setBlockMetadataWithNotify(x, y, z, meta - 8);
						potTile.markBlockForRender();
					}
				}
				

			}
			else
			{
				if (growthStage < potTile.getGrowthStagesForBlock(storedBlockID))
				{
					//System.out.println("Growing "+ meta);
					world.setBlockMetadataWithNotify(x, y, z, meta + 1);
					potTile.setStoredBlockMetadata(potTile.getStoredBlockMetadata() + 1);

					potTile.markBlockForRender();
					
					if (growthStage == potTile.getGrowthStagesForBlock(storedBlockID) - 1)
					{
						//System.out.println("clearing water "+ meta);
						world.setBlockMetadataWithNotify(x, y, z, meta - 8);
						potTile.markBlockForRender();
					}
				}
				

			}
			

			
		}
	}
	
	private float getGrowthChance(World world, int x, int y, int z) {
		
		if (world.getBlockId(x, y + 1, z) == FCBetterThanWolves.fcLightBulbOn.blockID || 
			world.getBlockId(x, y + 2, z) == FCBetterThanWolves.fcLightBulbOn.blockID )	
		{
			return 0.2F;
		}
		
		else return 0;
	}

	private boolean hasWater(int meta) {
		return meta > 7;

	}

    private int getGrowthStage(int meta) {
		    	
		return meta & 7;
	}


	/**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return super.canPlaceBlockAt(world, x, y, z) && FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        if (!FCUtilsWorld.DoesBlockHaveCenterHardpointToFacing(world, x, y - 1, z, 1))
        {
            this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World world, int x, int y, int z)
    {
//    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
//    	int blockInPotID = potTile.getStoredBlockID();
//    	
//    	if (blockInPotID == Block.mushroomBrown.blockID)
//    		return FCBetterThanWolves.fcItemMushroomBrown.itemID;
//    	else if (blockInPotID == Block.mushroomRed.blockID)
//    		return FCBetterThanWolves.fcItemMushroomRed.itemID;
//    	else
//    		return blockInPotID == 0 ? Item.flowerPot.itemID : blockInPotID;
    	
    	return SCDefs.waterPot.blockID;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World world, int x, int y, int z)
    {
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	int blockInPotMeta = potTile.getStoredBlockMetadata();
        return blockInPotMeta;
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World world, int x, int y, int z, int var5, int var6)
    {
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	int blockInPotID = potTile != null ? potTile.getStoredBlockID() : 0;

        if (blockInPotID != 0)
        {
        	if (!hasWater(world.getBlockMetadata(x, y, z)))
        	{
        		int blockInPotMeta = potTile.getStoredBlockMetadata();
                FCUtilsItem.EjectSingleItemWithRandomOffset(world, x, y, z, blockInPotID, blockInPotMeta);
        	}
        	
        }
        
        super.breakBlock(world, x, y, z, var5, var6);
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return SCDefs.waterPotEmpty.itemID;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new SCTileEntityWaterPot();
    }
    
    //When right clicked by a player
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
    	ItemStack handItemStack = player.getCurrentEquippedItem();
    	SCTileEntityWaterPot potTile = (SCTileEntityWaterPot) world.getBlockTileEntity(x, y, z);
    	
    	//Empty hand retrieves item
    	if (handItemStack == null) {
    		if (potTile.hasItem() && !hasWater(world.getBlockMetadata(x, y, z)))
    		{
        		potTile.retrieveItemFromPot(player);
        		world.setBlockMetadataWithNotify(x, y, z, empty);
        		return true;
    		}
    		else return false;
    	}
    	
    	int heldID = handItemStack.getItem().itemID;
    	int heldDamage = handItemStack.getItemDamage();
    	//If pot already has an item do nothing
    	if (potTile.hasItem()) {
    		return false;
    	}
    	//If item is placeable within the pot, place it
    	else if (potTile.isValidItemForPot(heldID, heldDamage) && hasWater(world.getBlockMetadata(x, y, z)))
    	{
    		potTile.placeItemInPot(heldID, heldDamage);
    		
    		//Decrements stack, unless in creative
    		if (!player.capabilities.isCreativeMode && --handItemStack.stackSize <= 0)
            {
    			player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
            }
    		
    		return true;
    	}
    	else {
    		if (heldID == Item.potion.itemID && heldDamage == 0 && world.getBlockMetadata(x, y, z) == empty) 
    		{
    			world.setBlockMetadataWithNotify(x, y, z, water);
    			
    			if (handItemStack.stackSize > 1)
    			{
    				--handItemStack.stackSize;
    			}
    			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
    			
    			player.inventory.addItemStackToInventory(new ItemStack(Item.glassBottle, 1));
    			
    			return true;
    		}
    		else if (heldID == FCBetterThanWolves.fcItemPileDirt.itemID)
    		{
    			world.setBlockWithNotify(x, y, z, SCDefs.flowerPot.blockID);
    			
    			if (handItemStack.stackSize > 1)
    			{
    				--handItemStack.stackSize;
    			}
    			else player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack)null);
    		}
    		return false;
    	}
    }

    public boolean CanGroundCoverRestOnBlock(World var1, int var2, int var3, int var4)
    {
        return var1.doesBlockHaveSolidTopSurface(var2, var3 - 1, var4);
    }

    public float GroundCoverRestingOnVisualOffset(IBlockAccess var1, int var2, int var3, int var4)
    {
        return -1.0F;
    }
    
}