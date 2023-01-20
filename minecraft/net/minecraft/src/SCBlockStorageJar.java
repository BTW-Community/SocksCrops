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

	@Override
	public void registerIcons(IconRegister register)
	{
		//Jar textures
		super.registerIcons(register);		
		
		//contentsIcon[0] kept clear for dyes
		
		String contents = "SCBlockJarContents_";
		String label = "SCBlockJarLabel_";
		
		//BREWING		
		contentsIcon[getIndex(Item.spiderEye.itemID)] = register.registerIcon(contents + "spiderEye");
		contentsIcon[getIndex(Item.fermentedSpiderEye.itemID)] = register.registerIcon(contents + "spiderEyeFermented");
		contentsIcon[getIndex(Item.magmaCream.itemID)] = register.registerIcon(contents + "magmaCream");
		contentsIcon[getIndex(Item.ghastTear.itemID)] = register.registerIcon(contents + "ghastTear");
		contentsIcon[getIndex(Item.blazePowder.itemID)] = register.registerIcon(contents + "blazePowder");
		contentsIcon[getIndex(Item.gunpowder.itemID)] = register.registerIcon(contents + "gunpowder");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemMushroomRed.itemID)] = register.registerIcon(contents + "mushroomRed");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemMysteriousGland.itemID)] = register.registerIcon(contents + "mysteriousGland");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemWitchWart.itemID)] = register.registerIcon(contents + "witchWart");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemSoulFlux.itemID)] = register.registerIcon(contents + "soulFlux");
		
		
		labelIcon[getIndex(Item.spiderEye.itemID)] = register.registerIcon(label + "spiderEye");
		labelIcon[getIndex(Item.fermentedSpiderEye.itemID)] = register.registerIcon(label + "spiderEyeFermented");
		labelIcon[getIndex(Item.magmaCream.itemID)] = register.registerIcon(label + "magmaCream");
		labelIcon[getIndex(Item.ghastTear.itemID)] = register.registerIcon(label + "ghastTear");
		labelIcon[getIndex(Item.blazePowder.itemID)] = register.registerIcon(label + "blazePowder");
		labelIcon[getIndex(Item.gunpowder.itemID)] = register.registerIcon(label + "gunpowder");
		labelIcon[getIndex(FCBetterThanWolves.fcItemMushroomRed.itemID)] = register.registerIcon(label + "mushroomRed");
		labelIcon[getIndex(FCBetterThanWolves.fcItemMysteriousGland.itemID)] = register.registerIcon(label + "mysteriousGland");
		labelIcon[getIndex(FCBetterThanWolves.fcItemWitchWart.itemID)] = register.registerIcon(label + "witchWart");
		labelIcon[getIndex(FCBetterThanWolves.fcItemSoulFlux.itemID)] = register.registerIcon(label + "soulFlux");
		
		//BAIT
		contentsIcon[getIndex(Item.rottenFlesh.itemID)] = register.registerIcon(contents + "rottenFlesh");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemBatWing.itemID)] = register.registerIcon(contents + "batwing");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemCreeperOysters.itemID)] = register.registerIcon(contents + "oysters");
		
		labelIcon[getIndex(Item.rottenFlesh.itemID)] = register.registerIcon(label + "rottenFlesh");
		labelIcon[getIndex(FCBetterThanWolves.fcItemBatWing.itemID)] = register.registerIcon(label + "batwing");
		labelIcon[getIndex(FCBetterThanWolves.fcItemCreeperOysters.itemID)] = register.registerIcon(label + "oysters");
		
		//OTHER
		contentsIcon[getIndex(FCBetterThanWolves.fcItemNitre.itemID)] = register.registerIcon(contents + "nitre");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemMushroomBrown.itemID)] = register.registerIcon(contents + "mushroomBrown");
		contentsIcon[getIndex(Item.slimeBall.itemID)] = register.registerIcon(contents + "slime");
		contentsIcon[getIndex(Item.redstone.itemID)] = register.registerIcon(contents + "redstone");
		contentsIcon[getIndex(Item.lightStoneDust.itemID)] = register.registerIcon(contents + "glowstone");
		contentsIcon[getIndex(Item.sugar.itemID)] = register.registerIcon(contents + "sugar");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemFlour.itemID)] = register.registerIcon(contents + "flour");
	
		labelIcon[getIndex(FCBetterThanWolves.fcItemNitre.itemID)] = register.registerIcon(label + "nitre");
		labelIcon[getIndex(FCBetterThanWolves.fcItemMushroomBrown.itemID)] = register.registerIcon(label + "mushroomBrown");
		labelIcon[getIndex(Item.slimeBall.itemID)]  = register.registerIcon(label + "slime");
		labelIcon[getIndex(Item.redstone.itemID)] = register.registerIcon(label + "redstone");
		labelIcon[getIndex(Item.lightStoneDust.itemID)] = register.registerIcon(label + "glowstone");
		labelIcon[getIndex(Item.sugar.itemID)] = register.registerIcon(label + "sugar");
		labelIcon[getIndex(FCBetterThanWolves.fcItemFlour.itemID)] = register.registerIcon(label + "flour");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemBloodMossSpores.itemID)] = register.registerIcon(contents + "spores");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemBrimstone.itemID)] = register.registerIcon(contents + "brimstone");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemPotash.itemID)] = register.registerIcon(contents + "potash");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemGlue.itemID)] = register.registerIcon(contents + "glue");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemMetalFragment.itemID)] = register.registerIcon(contents + "metalFragment");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemChunkIronOre.itemID)] = register.registerIcon(contents + "ironChunk");
		contentsIcon[getIndex(FCBetterThanWolves.fcItemChunkGoldOre.itemID)] = register.registerIcon(contents + "goldChunk");
		
		labelIcon[getIndex(FCBetterThanWolves.fcItemBloodMossSpores.itemID)] = register.registerIcon(label + "spores");
		labelIcon[getIndex(FCBetterThanWolves.fcItemBrimstone.itemID)] = register.registerIcon(label + "brimstone");
		labelIcon[getIndex(FCBetterThanWolves.fcItemPotash.itemID)] = register.registerIcon(label + "potash");
		labelIcon[getIndex(FCBetterThanWolves.fcItemGlue.itemID)] = register.registerIcon(label + "glue");
		labelIcon[getIndex(FCBetterThanWolves.fcItemMetalFragment.itemID)] = register.registerIcon(label + "metalFragment");
		labelIcon[getIndex(FCBetterThanWolves.fcItemChunkIronOre.itemID)] = register.registerIcon(label + "ironChunk");
		labelIcon[getIndex(FCBetterThanWolves.fcItemChunkGoldOre.itemID)] = register.registerIcon(label + "goldChunk");
		
		contentsIcon[getIndex(Item.fireballCharge.itemID)] = register.registerIcon(contents + "firecharge");		
		contentsIcon[getIndex(Item.netherQuartz.itemID)] = register.registerIcon(contents + "quartz");		
		contentsIcon[getIndex(Item.emerald.itemID)] = register.registerIcon(contents + "emerald");		
		contentsIcon[getIndex(Item.enderPearl.itemID)] = register.registerIcon(contents + "enderpearls");		
		contentsIcon[getIndex(Item.diamond.itemID)] = register.registerIcon(contents + "diamond");
		
		labelIcon[getIndex(Item.fireballCharge.itemID)] = register.registerIcon(label + "firecharge");
		labelIcon[getIndex(Item.netherQuartz.itemID)] = register.registerIcon(label + "quartz");
		labelIcon[getIndex(Item.emerald.itemID)] = register.registerIcon(label + "emerald");
		labelIcon[getIndex(Item.enderPearl.itemID)] = register.registerIcon(label + "enderpearls");
		labelIcon[getIndex(Item.diamond.itemID)] = register.registerIcon(label + "diamond");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemNuggetIron.itemID)] = register.registerIcon(contents + "ironNugget");
		labelIcon[getIndex(FCBetterThanWolves.fcItemNuggetIron.itemID)] = register.registerIcon(label + "ironNugget");
		
		contentsIcon[getIndex(Item.goldNugget.itemID)] = register.registerIcon(contents + "goldNugget");
		labelIcon[getIndex(Item.goldNugget.itemID)] = register.registerIcon(label + "goldNugget");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemTallow.itemID)] = register.registerIcon(contents + "tallow");
		labelIcon[getIndex(FCBetterThanWolves.fcItemTallow.itemID)] = register.registerIcon(label + "tallow");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemStrap.itemID)] = register.registerIcon(contents + "strap");
		labelIcon[getIndex(FCBetterThanWolves.fcItemStrap.itemID)] = register.registerIcon(label + "strap");
		
		contentsIcon[getIndex(FCBetterThanWolves.fcItemGear.itemID)] = register.registerIcon(contents + "gear");
		labelIcon[getIndex(FCBetterThanWolves.fcItemGear.itemID)] = register.registerIcon(label + "gear");
		
		contentsIcon[getIndex(Item.silk.itemID)] = register.registerIcon(contents + "string");
		labelIcon[getIndex(Item.silk.itemID)] = register.registerIcon(label + "string");
		
		
		//Deco
		if (SCDecoIntegration.isDecoInstalled())
		{
			contentsIcon[getIndex(SCDecoIntegration.fertilizer.itemID)] = register.registerIcon(contents + "decoFertilizer");
			contentsIcon[getIndex(SCDecoIntegration.amethystShard.itemID)] = register.registerIcon(contents + "decoShard");
			contentsIcon[getIndex(SCDecoIntegration.prismarineShard.itemID)] = register.registerIcon(contents + "decoPrismarineShard");
			contentsIcon[getIndex(SCDecoIntegration.prismarineCrystal.itemID)] = register.registerIcon(contents + "decoPrismarineCrystal");
			
			labelIcon[getIndex(SCDecoIntegration.fertilizer.itemID)] = register.registerIcon(label + "decoFertilizer");
			labelIcon[getIndex(SCDecoIntegration.amethystShard.itemID)] = register.registerIcon(label + "decoShard");
			labelIcon[getIndex(SCDecoIntegration.prismarineShard.itemID)] = register.registerIcon(label + "decoPrismarineShard");
			labelIcon[getIndex(SCDecoIntegration.prismarineCrystal.itemID)] = register.registerIcon(label + "decoPrismarineCrystal");
		}
	
		//Dyes
		for (int i = 0; i < dyeIcon.length; i++) {
			if (i >= 16)
			{
				dyeIcon[i] = register.registerIcon( contents + "dye" + (i - 16) );
			}
			else dyeIcon[i] = register.registerIcon( contents + "dye" + i );
		}
		
//		dyeIcon[16] = register.registerIcon(contents + "decoDye0");
//		dyeIcon[20] = register.registerIcon(contents + "decoDye4");
//		dyeIcon[31] = register.registerIcon(contents + "decoDye15");
		
		for (int i = 0; i < dyeLabelIcon.length; i++) {
			if (i >= 16)
			{
				dyeLabelIcon[i] = register.registerIcon( label + "dye" + (i - 16) );
			}
			else dyeLabelIcon[i] = register.registerIcon( label + "dye" + i );
		}
		dyeLabelIcon[16] = register.registerIcon(label + "decoDye0");
		dyeLabelIcon[20] = register.registerIcon(label + "decoDye4");
		dyeLabelIcon[31] = register.registerIcon(label + "decoDye15");

	}
	
	@Override
	protected int getIndex(int seedType)
	{	
		return this.validItemList.indexOf(seedType);
		
	}
	
	@Override
	protected Icon getContentIcon(int seedType, int itemDamage)
	{	
		if ( validItemList.contains(seedType) )
		{
			if (seedType == Item.dyePowder.itemID)
			{
				return dyeIcon[itemDamage];
			}
			else return contentsIcon[validItemList.indexOf(seedType)];
		}
		else return gravel;
		
	}
	
	@Override
	protected Icon getLabelIcon(int seedType, int itemDamage)
	{		
		if ( validItemList.contains(seedType) )
		{
			if (seedType == Item.dyePowder.itemID)
			{
				return dyeLabelIcon[itemDamage];
			}
			else return labelIcon[validItemList.indexOf(seedType)];
		}
		else return labelBlank;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
		
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar) renderer.blockAccess.getBlockTileEntity(x, y, z);
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
		float shiftUp = 0;
		
		if (hasAttachableBlockAbove(renderer.blockAccess, x, y, z)) shiftUp = 5/16F;

		
		renderJar(renderer, x, y, z, 
				storageJar.hasLabel(),
				meta,
				getDirection(meta),
				storageJar.getStorageStack(),
				storageJar.getSeedType() );
		
		renderContents(renderer, x, y, z, meta, getDirection(meta), shiftUp, storageJar.getStorageStack());
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
		
		if (type >= validItemList.size())
		{
			seedID = 0;
		}
		else seedID = validItemList.get(type);
		
		
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
