package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCBlockStorageJar extends SCBlockJarBase {
	
	private ArrayList<Integer> validItemList = SCTileEntityStorageJar.getValidItemList();
	
	protected SCBlockStorageJar(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockStorageJar");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCTileEntityStorageJar();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float xClick, float yClick, float zClick)
	{
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) world.getBlockTileEntity(x, y, z);
		int contentType = storageJar.getSeedType();
		ItemStack storageStack = storageJar.getStorageStack();

		if (storageJar != null)
		{
			// Player is holding something
			if (heldStack != null)
			{
				// Is not a storage item
				if (!storageJar.isStackStorageItem(heldStack))
				{
					//and is a valid seed item
					if (SCTileEntitySeedJar.isStackSeedItem(heldStack))
					{
						//Convert to seedJar
						world.setBlockAndMetadata(x, y, z, SCDefs.seedJar.blockID, world.getBlockMetadata(x, y, z));
						
						SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) world.getBlockTileEntity(x, y, z);
						seedJar.setLabel(storageJar.hasLabel());
						
						int blockID = world.getBlockId(x, y, z);
						return blocksList[SCDefs.seedJar.blockID].onBlockActivated(world, x, y, z, player, facing, xClick, yClick, zClick);	
					}
				}
				
				// Jar doesn't have a label and player is holding paper
				if (!storageJar.hasLabel() && heldStack.itemID == Item.paper.itemID)
				{
					storageJar.setLabel(true);
					storageJar.markBlockForUpdate();

					if (!player.capabilities.isCreativeMode)
					{
						heldStack.stackSize--;
					}

					return true;
				}
				// Remove label by using soap
				else if (storageJar.hasLabel() && heldStack.itemID == FCBetterThanWolves.fcItemSoap.itemID)
				{
					storageJar.setLabel(false);
					storageJar.markBlockForUpdate();

					if (!player.capabilities.isCreativeMode)
					{
						heldStack.stackSize--;
						ItemStack paper = new ItemStack(Item.paper);
						if (!world.isRemote) FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, paper, facing);
					}

					return true;
				}
				else // try add item to inventory
				{
					if (validItemList.contains(heldStack.itemID))
					{
						// only allow one type in the jar
						if (contentType == 0 || contentType == heldStack.itemID)
						{
							FCUtilsInventory.AddItemStackToInventory(storageJar, heldStack);
							
							//TODO: FIX since you can overfill. Change to remove only number of items that is space for
							if ( storageStack != null &&
								 storageStack.stackSize >= storageStack.getMaxStackSize() &&
								 storageStack.stackSize + heldStack.stackSize <= storageJar.getInventoryStackLimit() )
							{
								storageStack.stackSize += heldStack.stackSize;
								heldStack.stackSize -= heldStack.stackSize;
							}
							
							storageJar.onInventoryChanged();

							return true;
						}
					}
				}

			}
			else if (heldStack == null) // Empty hand
			{
				// Eject only single item
				if (player.isSneaking())
				{
					if (storageStack.stackSize > 0)
					{
						ItemStack ejectStack = storageStack.splitStack(1);
						
						if (!world.isRemote) FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, ejectStack, facing);
						
						storageJar.onInventoryChanged();
						
						return true;
					}
				}
				else //eject all
				{
					if (!world.isRemote) storageJar.ejectStorageContents(facing);

					storageJar.onInventoryChanged();
					return true;
				}
			}
		}

		return false;
	}
	
	public ItemStack GetStackRetrievedByBlockDispenser( World world, int i, int j, int k )
	{
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack storageStack = storageJar.getStorageStack();
		ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
		
    	if ( storageJar != null )
    	{
            if (  storageStack != null)
            {
                NBTTagCompound newTag = new NBTTagCompound();
                
                newStack.setTagCompound(newTag);
                newStack.getTagCompound().setInteger( "id", storageStack.itemID );
                newStack.getTagCompound().setInteger( "Count", storageStack.stackSize );
                newStack.getTagCompound().setInteger( "Damage",  this.getDamageValue(world, i, j, k) );
                
                newStack.getTagCompound().setInteger( "seedType", storageJar.getSeedType() );
                newStack.getTagCompound().setBoolean( "hasLabel", storageJar.hasLabel() );
                newStack.getTagCompound().setInteger( "seedDamage", storageStack.getItemDamage());
            	
            }
    	}
    	
    	return newStack;
	}

	
	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int par5, EntityPlayer player) {
		
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack storageStack = storageJar.getStorageStack();
		ItemStack newStack = new ItemStack(SCDefs.storageJar.blockID, 1, this.getDamageValue(world, i, j, k));
		
    	if ( storageJar != null )
    	{
            if ( storageStack != null)
            {
                NBTTagCompound newTag = new NBTTagCompound();
                
                newStack.setTagCompound(newTag);
                newStack.getTagCompound().setInteger( "id", storageStack.itemID );
                newStack.getTagCompound().setInteger( "Count", storageStack.stackSize );
                newStack.getTagCompound().setInteger( "Damage",  this.getDamageValue(world, i, j, k) );
                
                newStack.getTagCompound().setInteger( "seedType", storageJar.getSeedType() );
                newStack.getTagCompound().setBoolean( "hasLabel", storageJar.hasLabel() );
                newStack.getTagCompound().setInteger( "seedDamage", storageStack.getItemDamage());
            }
    	}
    	
    	this.dropBlockAsItem_do(world, i, j, k, newStack);
    		
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k)
    {
    	SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) world.getBlockTileEntity(i, j, k);
    	ItemStack storageStack = storageJar.getStorageStack();
    	
    	int stackSize = 0;
    	int contentHeight = 0;
    	
    	if (storageStack != null)
		{
    		stackSize = storageStack.stackSize;
    		
    	    if (stackSize != 0)
    	    {
    	    	if (stackSize == 64)
    	        {
    	    		contentHeight =(int) (Math.floor( stackSize/8 )) - 1;
    	        }
    	        else if (stackSize < 8)
    	        {
    	        	contentHeight =(int) (Math.floor( stackSize/8 )) + 1;
    	        }
    	    	else
    	    		contentHeight =(int) (Math.floor( stackSize/8 ));
    	    }
    	    
			int seedID = storageStack.itemID;
			int seedTypeForItemRender = storageJar.getSeedTypeForItemRender(seedID);

			return  dataToDamage(storageJar.hasLabel(), contentHeight, storageStack.getItemDamage(), seedTypeForItemRender);
		}
    	
    	else return  dataToDamage(storageJar.hasLabel(), 0, 0, 0);
    }
    
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack itemStack)
	{
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) world.getBlockTileEntity(i, j, k);
		
		setRotation(world, i, j, k, player);
				
		if ( storageJar != null )
    	{
			
			if (hasAttachableBlockAbove(world, i, j, k))
			{
				storageJar.setHasAttachableBlockAbove(true);
			}
			else storageJar.setHasAttachableBlockAbove(false);
			
			//NBTTagCompound newTag = new NBTTagCompound("storageStack");
			//ItemStack newStack = new ItemStack(this, 1, itemStack.getItemDamage());
			
			if ( itemStack.hasTagCompound() )
			{
				int id = 0;
				int count = 0; 
				int damage = 0;
				int seedDamage = 0;
				
				int seedType = 0;
				boolean label = false;
				
				
				if (itemStack.stackTagCompound.hasKey("id") )
				{
					id = itemStack.stackTagCompound.getInteger("id");
				}
				
				if (itemStack.stackTagCompound.hasKey("Count") )
				{
					count = itemStack.stackTagCompound.getInteger("Count");
					
				}
				
				if (itemStack.stackTagCompound.hasKey("Damage") )
				{
					damage = itemStack.stackTagCompound.getInteger("Damage");
					
				}
				
				if (itemStack.stackTagCompound.hasKey("seedType") )
				{
					seedType = itemStack.stackTagCompound.getInteger("seedType");
					storageJar.setSeedType(seedType);	
				}
				
				if (itemStack.stackTagCompound.hasKey("seedDamage") )
				{
					seedDamage = itemStack.stackTagCompound.getInteger("seedDamage");
					storageJar.setSeedType(seedType);
				}
				
				
//				if (itemStack.stackTagCompound.hasKey("hasLabel") )
//				{
//					label = itemStack.stackTagCompound.getBoolean("hasLabel");
//					tileEntity.setLabel(true);
//				}
//				else tileEntity.setLabel(false);
				
				
				
				storageJar.setInventorySlotContents(0, new ItemStack(id, count, damage));
				
				if (storageJar.getStorageStack() != null)
				{
					storageJar.getStorageStack().setItemDamage(seedDamage);
				}
				
				
			}
			
			int labelDamage = damageToData( itemStack.getItemDamage() )[0];
			
			if (labelDamage == 1)
			{
				storageJar.setLabel(true);
			}
			else storageJar.setLabel(false);
			
    	}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int iNeighborBlockID)
	{
        if (!hasAttachableBlockAbove(world, x, y, z) && !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ))
        {
    		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar)( world.getBlockTileEntity(x, y, z) );
    		ItemStack storageStack = storageJar.getStorageStack();    	
    		
    		removeJar(world, x, y, z, storageStack);        	
        }
	}

	//----------- Client Side Functionality -----------//

}
