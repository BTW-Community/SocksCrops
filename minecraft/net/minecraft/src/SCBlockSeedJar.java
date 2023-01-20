package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCBlockSeedJar extends SCBlockJarBase {
	
	private ArrayList<Integer> validSeedList = SCTileEntitySeedJar.getValidSeedList();
	
	protected SCBlockSeedJar(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockSeedJar");
		setCreativeTab(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new SCTileEntitySeedJar();
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float xClick, float yClick, float zClick)
	{
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) world.getBlockTileEntity(x, y, z);
		int contentType = seedJar.getSeedType();
		ItemStack storageStack = seedJar.getStorageStack();

		if (seedJar != null)
		{
			// Player is holding something
			if (heldStack != null)
			{
				// Is not a seed item
				if (!seedJar.isStackSeedItem(heldStack))
				{
					//and is a valid storage item
					if (SCTileEntityStorageJar.isStackStorageItem(heldStack))
					{
						//Convert to storageJar
						world.setBlockAndMetadata(x, y, z, SCDefs.storageJar.blockID, world.getBlockMetadata(x, y, z));
						
						SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) world.getBlockTileEntity(x, y, z);
						storageJar.setLabel(seedJar.hasLabel());
						
						int blockID = world.getBlockId(x, y, z);
						return blocksList[SCDefs.storageJar.blockID].onBlockActivated(world, x, y, z, player, facing, xClick, yClick, zClick);	
					}
				}
				
				// Jar doesn't have a label and player is holding paper
				if (!seedJar.hasLabel() && heldStack.itemID == Item.paper.itemID)
				{
					seedJar.setLabel(true);
					seedJar.markBlockForUpdate();

					if (!player.capabilities.isCreativeMode)
					{
						heldStack.stackSize--;
					}

					return true;
				}
				// Remove label by using soap
				else if (seedJar.hasLabel() && heldStack.itemID == FCBetterThanWolves.fcItemSoap.itemID)
				{
					seedJar.setLabel(false);
					seedJar.markBlockForUpdate();

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
					if (validSeedList.contains(heldStack.itemID))
					{
						// only allow one type in the jar
						if (contentType == 0 || contentType == heldStack.itemID)
						{
							FCUtilsInventory.AddItemStackToInventory(seedJar, heldStack);
							
							//TODO: FIX since you can overfill. Change to remove only number of items that is space for
							if ( storageStack != null &&
								 storageStack.stackSize >= storageStack.getMaxStackSize() &&
								 storageStack.stackSize + heldStack.stackSize <= seedJar.getInventoryStackLimit() )
							{
								storageStack.stackSize += heldStack.stackSize;
								heldStack.stackSize -= heldStack.stackSize;
							}
							
							seedJar.onInventoryChanged();

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
						
						seedJar.onInventoryChanged();
						
						return true;
					}
				}
				else //eject all
				{
					if (!world.isRemote) seedJar.ejectStorageContents(facing);

					seedJar.onInventoryChanged();
					return true;
				}
			}
		}

		return false;
	}
	
	public ItemStack GetStackRetrievedByBlockDispenser( World world, int i, int j, int k )
	{
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack storageStack = seedJar.getStorageStack();
		ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
		
    	if ( seedJar != null )
    	{
            if (  storageStack != null)
            {
                NBTTagCompound newTag = new NBTTagCompound();
                
                newStack.setTagCompound(newTag);
                newStack.getTagCompound().setInteger( "id", storageStack.itemID );
                newStack.getTagCompound().setInteger( "Count", storageStack.stackSize );
                newStack.getTagCompound().setInteger( "Damage",  this.getDamageValue(world, i, j, k) );
                
                newStack.getTagCompound().setInteger( "seedType", seedJar.getSeedType() );
                newStack.getTagCompound().setBoolean( "hasLabel", seedJar.hasLabel() );
                newStack.getTagCompound().setInteger( "seedDamage", storageStack.getItemDamage());
            	
            }
    	}
    	
    	return newStack;
	}

	
	@Override
	public void onBlockHarvested(World world, int i, int j, int k, int par5, EntityPlayer player) {
		
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack newStack = new ItemStack(SCDefs.seedJar.blockID, 1, this.getDamageValue(world, i, j, k));
		
    	if ( seedJar != null )
    	{
            if ( seedJar.getStackInSlot(0) != null)
            {
            	ItemStack oldStack = seedJar.getStorageStack();
            	
                NBTTagCompound newTag = new NBTTagCompound();
                
                newStack.setTagCompound(newTag);
                newStack.getTagCompound().setInteger( "id", oldStack.itemID );
                newStack.getTagCompound().setInteger( "Count", oldStack.stackSize );
                newStack.getTagCompound().setInteger( "Damage",  this.getDamageValue(world, i, j, k) );
                
                newStack.getTagCompound().setInteger( "seedType", seedJar.getSeedType() );
                newStack.getTagCompound().setBoolean( "hasLabel", seedJar.hasLabel() );
                newStack.getTagCompound().setInteger( "seedDamage", oldStack.getItemDamage());
            	
            }
    	}
    	
    	//if (!player.capabilities.isCreativeMode)
    	this.dropBlockAsItem_do(world, i, j, k, newStack);
	}

	@Override
	public int getDamageValue(World world, int i, int j, int k)
    {
    	SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) world.getBlockTileEntity(i, j, k);

    	ItemStack storageStack = seedJar.getStorageStack();
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
			int seedTypeForItemRender = seedJar.getSeedTypeForItemRender(seedID);

			return  dataToDamage(seedJar.hasLabel(), contentHeight, storageStack.getItemDamage(), seedTypeForItemRender);
		}
    	
    	else return  dataToDamage(seedJar.hasLabel(), 0, 0, 0);
    }
    
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack itemStack)
	{
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) world.getBlockTileEntity(i, j, k);

		setRotation(world, i, j, k, player);
		
		if ( seedJar != null )
    	{
			
			if (hasAttachableBlockAbove(world, i, j, k))
			{
				seedJar.setHasAttachableBlockAbove(true);
			}
			else seedJar.setHasAttachableBlockAbove(false);
			
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
					seedJar.setSeedType(seedType);	
				}
				
				if (itemStack.stackTagCompound.hasKey("seedDamage") )
				{
					seedDamage = itemStack.stackTagCompound.getInteger("seedDamage");
					seedJar.setSeedType(seedType);
				}
				
				
//				if (itemStack.stackTagCompound.hasKey("hasLabel") )
//				{
//					label = itemStack.stackTagCompound.getBoolean("hasLabel");
//					tileEntity.setLabel(true);
//				}
//				else tileEntity.setLabel(false);
				
				
				
				seedJar.setInventorySlotContents(0, new ItemStack(id, count, damage));
				
				if (seedJar.getStorageStack() != null)
				{
					seedJar.getStorageStack().setItemDamage(seedDamage);
				}
				
			}
			
			int labelDamage = damageToData( itemStack.getItemDamage() )[0];
			
			if (labelDamage == 1)
			{
				seedJar.setLabel(true);
			}
			else seedJar.setLabel(false);
			
    	}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int iNeighborBlockID)
	{
		 if (!hasAttachableBlockAbove(world, x, y, z) && !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true ))
        {
    		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar)( world.getBlockTileEntity(x, y, z) );
    		ItemStack storageStack = seedJar.getStorageStack();    	
    		
    		removeJar(world, x, y, z, storageStack);        	
        }
	}

	//----------- Client Side Functionality -----------//

	@Override
	public void registerIcons(IconRegister register) {

		//Jar textures
		super.registerIcons(register);
		
		String contents = "SCBlockJarContents_";
		String label = "SCBlockJarLabel_";
		
		//CROPS
		contentsIcon[getIndex(Item.melonSeeds.itemID)] = register.registerIcon(contents + "melonSeeds");
		contentsIcon[getIndex(Item.pumpkinSeeds.itemID)] = register.registerIcon(contents + "pumpkinSeeds");
		contentsIcon[getIndex(Item.netherStalkSeeds.itemID)] = register.registerIcon(contents + "netherwart");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemHempSeeds.itemID)] = register.registerIcon(contents + "hempSeeds");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemWheatSeeds.itemID)] = register.registerIcon(contents + "wheatSeeds");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemCarrotSeeds.itemID)] = register.registerIcon(contents + "carrotSeeds");

		labelIcon[getIndex(Item.melonSeeds.itemID)] = register.registerIcon(label + "melonSeeds");
		labelIcon[getIndex(Item.pumpkinSeeds.itemID)] = register.registerIcon(label + "pumpkinSeeds");
		labelIcon[getIndex(Item.netherStalkSeeds.itemID)] = register.registerIcon(label + "netherwart");
		labelIcon[getIndex(FCBetterThanWolves.fcItemHempSeeds.itemID)] = register.registerIcon(label + "hempSeeds");
		labelIcon[getIndex(FCBetterThanWolves.fcItemWheatSeeds.itemID)] = register.registerIcon(label + "wheatSeeds");
		labelIcon[getIndex(FCBetterThanWolves.fcItemCarrotSeeds.itemID)] = register.registerIcon(label + "carrotSeeds");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemCocoaBeans.itemID)] = register.registerIcon(contents + "cocoaBeans");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemMelonMashed.itemID)] = register.registerIcon(contents + "melonMash");
		contentsIcon[getIndex(Item.cookie.itemID)] = register.registerIcon(contents + "cookie");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemChickenFeed.itemID)] = register.registerIcon(contents + "chickenFeed");
		
		labelIcon[getIndex(FCBetterThanWolves.fcItemCocoaBeans.itemID)] = register.registerIcon(label + "cocoaBeans");
		labelIcon[getIndex(FCBetterThanWolves.fcItemMelonMashed.itemID)] = register.registerIcon(label + "melonMash");
		labelIcon[getIndex(Item.cookie.itemID)] = register.registerIcon(label + "cookie");
		labelIcon[getIndex(FCBetterThanWolves.fcItemChickenFeed.itemID)] = register.registerIcon(label + "chickenFeed");
		
		//SC
		contentsIcon[getIndex(SCDefs.wildCarrotSeeds.itemID)] = register.registerIcon(contents + "wildCarrotSeeds");
		labelIcon[getIndex(SCDefs.wildCarrotSeeds.itemID)] = register.registerIcon(label + "wildCarrotSeeds");
		
		contentsIcon[getIndex(SCDefs.wildOnionSeeds.itemID)] = register.registerIcon(contents + "wildOnionSeeds");
		labelIcon[getIndex(SCDefs.wildOnionSeeds.itemID)] = register.registerIcon(label + "wildOnionSeeds");
		
		contentsIcon[getIndex(SCDefs.wildLettuceSeeds.itemID)] = register.registerIcon(contents + "wildLettuceSeeds");
		labelIcon[getIndex(SCDefs.wildLettuceSeeds.itemID)] = register.registerIcon(label + "wildLettuceSeeds");
		
		contentsIcon[getIndex(SCDefs.hopSeeds.itemID)] = register.registerIcon(contents + "hopSeeds");
		labelIcon[getIndex(SCDefs.hopSeeds.itemID)] = register.registerIcon(label + "hopSeeds");
		
		contentsIcon[getIndex(SCDefs.redGrapeSeeds.itemID)] = register.registerIcon(contents + "redGrapeSeeds");
		labelIcon[getIndex(SCDefs.redGrapeSeeds.itemID)] = register.registerIcon(label + "redGrapeSeeds");
		
		contentsIcon[getIndex(SCDefs.whiteGrapeSeeds.itemID)] = register.registerIcon(contents + "whiteGrapeSeeds");
		labelIcon[getIndex(SCDefs.whiteGrapeSeeds.itemID)] = register.registerIcon(label + "whiteGrapeSeeds");
		
		contentsIcon[getIndex(SCDefs.tomatoSeeds.itemID)] = register.registerIcon(contents + "tomatoSeeds");
		labelIcon[getIndex(SCDefs.tomatoSeeds.itemID)] = register.registerIcon(label + "tomatoSeeds");
		
		contentsIcon[getIndex(SCDefs.rice.itemID)] = register.registerIcon(contents + "rice");
		labelIcon[getIndex(SCDefs.rice.itemID)] = register.registerIcon(label + "rice");
		
		contentsIcon[getIndex(SCDefs.sunflowerSeeds.itemID)] = register.registerIcon(contents + "sunflowerSeeds");
		labelIcon[getIndex(SCDefs.sunflowerSeeds.itemID)] = register.registerIcon(label + "sunflowerSeeds");
		
		contentsIcon[getIndex(SCDefs.appleSeeds.itemID)] = register.registerIcon(contents + "appleSeeds");
		labelIcon[getIndex(SCDefs.appleSeeds.itemID)] = register.registerIcon(label + "appleSeeds");
		
		contentsIcon[getIndex(SCDefs.cherrySeeds.itemID)] = register.registerIcon(contents + "cherrySeeds");
		labelIcon[getIndex(SCDefs.cherrySeeds.itemID)] = register.registerIcon(label + "cherrySeeds");
		
		contentsIcon[getIndex(SCDefs.lemonSeeds.itemID)] = register.registerIcon(contents + "lemonSeeds");
		labelIcon[getIndex(SCDefs.lemonSeeds.itemID)] = register.registerIcon(label + "lemonSeeds");
		
		contentsIcon[getIndex(SCDefs.oliveSeeds.itemID)] = register.registerIcon(contents + "oliveSeeds");
		labelIcon[getIndex(SCDefs.oliveSeeds.itemID)] = register.registerIcon(label + "oliveSeeds");
		
		contentsIcon[getIndex(SCDefs.grassSeeds.itemID)] = register.registerIcon(contents + "grassSeeds");
		labelIcon[getIndex(SCDefs.grassSeeds.itemID)] = register.registerIcon(label + "grassSeeds");
	}
		
	@Override
	protected int getIndex(int seedType)
	{	
		return this.validSeedList.indexOf(seedType);
		
	}
	
	@Override
	protected Icon getContentIcon(int seedType, int itemDamage)
	{	
		if ( validSeedList.contains(seedType) )
		{
			return contentsIcon[validSeedList.indexOf(seedType)];
		}
		else return gravel;
		
	}
	
	@Override
	protected Icon getLabelIcon(int seedType, int itemDamage)
	{		
		if ( validSeedList.contains(seedType) )
		{
			return labelIcon[validSeedList.indexOf(seedType)]; 
		}
		else return labelBlank;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
		
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar) renderer.blockAccess.getBlockTileEntity(x, y, z);
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
		float shiftUp = 0;
		ItemStack storageStack = seedJar.getStorageStack();
		
		if (hasAttachableBlockAbove(renderer.blockAccess, x, y, z)) shiftUp = 5/16F;

		
		renderJar(renderer, x, y, z, 
				seedJar.hasLabel(),
				meta,
				getDirection(meta),
				seedJar.getStorageStack(),
				seedJar.getSeedType() );
		
		renderContents(renderer, x, y, z, meta, getDirection(meta), shiftUp, seedJar.getStorageStack());
//		if(storageStack != null && storageStack.itemID != Item.cookie.itemID )renderContents(renderer, x, y, z, meta, getDirection(meta), shiftUp, seedJar.getStorageStack());
		
		return true;
	}

	@Override
	public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness)
	{
		int type = damageToData(iItemDamage)[3];
		int damage = damageToData(iItemDamage)[2];
		int fill = damageToData(iItemDamage)[1];
		int label = damageToData(iItemDamage)[0];
		int seedID;
		
		if (type >= validSeedList.size())
		{
			seedID = 0;
		}
		else seedID = validSeedList.get(type);
		
		
		//Jar
		renderBlocks.setRenderBounds( getBounds(jarWidth/2, mindTheGap, jarHeight, jarWidth/2) );
		FCClientUtilsRender.RenderInvBlockWithMetadata(renderBlocks, this, -0.5F, -0.5F, -0.5F, iItemDamage);
		
		//Cork
		float corkY = jarHeight - 1/16F;
		renderBlocks.setRenderBounds( getBounds(corkWidth/2, corkY, corkY + corkHeight, corkWidth/2) );
		FCClientUtilsRender.RenderInvBlockWithTexture( renderBlocks, this, -0.5F, -0.5F, -0.5F, cork );
		
		//Contents
		if (fill > 0) {
			
			renderBlocks.setRenderBounds( getBounds(contentsWidth/2, mindTheGap*2, fill/16F, contentsWidth/2) );
			FCClientUtilsRender.RenderInvBlockWithTexture( renderBlocks, this, -0.5F, -0.5F, -0.5F,  getContentIcon( seedID, damage ) );
		}
		
		//label
		if (label == 1) 
		{
			renderBlocks.setRenderBounds( getBounds(mindTheGap, 0, jarHeight, jarWidth/2) );
			if (fill == 0) //ie empty
			{
				FCClientUtilsRender.RenderInvBlockWithTexture( renderBlocks, this, -0.5F + 4/16F, -0.5F, -0.5F, labelBlank );
			}
			else FCClientUtilsRender.RenderInvBlockWithTexture( renderBlocks, this, -0.5F + 4/16F, -0.5F, -0.5F, getLabelIcon( seedID, damage) );
		}
	}
}
