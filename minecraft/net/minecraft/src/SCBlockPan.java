//SCADDON: copied form BlockSkull

package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPan extends BlockContainer
{

	
    protected SCBlockPan(int par1)
    {
        super(par1, Material.iron);
        
        setHardness( 1F );
        
        InitBlockBounds( 3/16D, 0D, 3/16D, 1D - 3/16D, 3/16D, 1D - 3/16D );
        
        setStepSound( soundMetalFootstep );
        
        setUnlocalizedName("SCBlockPan");
    }
    
    @Override
    public void randomDisplayTick(World world, int i, int j, int k, Random rand)
    {
        SCTileEntityPan pan = (SCTileEntityPan)world.getBlockTileEntity( i, j, k );
        
        if ( pan.isFoodBurning() )
        {
            for ( int iTempCount = 0; iTempCount < 1; ++iTempCount )
            {
                double xPos = i + 0.375F + rand.nextFloat() * 0.25F;
                double yPos = j - 0.5F + rand.nextFloat() * 0.5F;
                double zPos = k + 0.375F + rand.nextFloat() * 0.25F;
                
                world.spawnParticle( "largesmoke", xPos, yPos, zPos, 0D, 0D, 0D );
                
//    	        if ( rand.nextInt(2) == 0 )
//    	        {
//    	        	float volume = 0.75F + rand.nextFloat();
//    	        	
//    	        	float pitch = rand.nextFloat() * 0.5F + 0.5F;
//    	        	
//    	            playSound(world, i, j, k, rand, volume, pitch, "fire.fire");
//    	        }	
            }
        }
        else if ( pan.isFoodCooking() )
        {
            for ( int iTempCount = 0; iTempCount < 1; ++iTempCount )
            {
                double xPos = i + 0.375F + rand.nextFloat() * 0.25F;
                double yPos = j - 0.5F + rand.nextFloat() * 0.5F;
                double zPos = k + 0.375F + rand.nextFloat() * 0.25F;
                
                world.spawnParticle( "fcwhitesmoke", xPos, yPos, zPos, 0D, 0D, 0D );
            }
            
  	        if ( rand.nextInt(1) == 0 )
	        {
  	        	float volume = 0.05F * rand.nextFloat();

  	        	float pitch = rand.nextFloat() * 2F;
  	        	
  	        	playSound(world, i, j, k, rand, volume, pitch, "random.fizz");
	        }

        } 
    }
    
    private void playSound(World world, int i, int j, int k, Random rand, float volume, float pitch, String sound)
    {
    	world.playSound( i + 0.5D, j - 6/16D, k + 0.5D, sound,
            	volume, pitch, false );
		
	}

	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
    	SCTileEntityPan pan = (SCTileEntityPan)world.getBlockTileEntity( i, j, k );

    	ItemStack cookStack = pan.getCookStack();

    	ItemStack heldStack = player.getCurrentEquippedItem();

    	if (pan != null)
    	{
    		if (heldStack != null)
    		{
    			if ( cookStack == null && isValidCookItem( heldStack ) )
				{
					pan.setCookStack( heldStack );
					
					heldStack.stackSize--;
					
					if (!world.isRemote)
    				{  
    					world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );	
    				}
	    			
	    			return true;
				}

    		}
    		else
    		{
    			if ( cookStack != null )
    			{
    				//hand is empty	    			
    				FCUtilsItem.GivePlayerStackOrEject( player, cookStack, i, j, k );

    				pan.setCookStack(null);

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
    
	public boolean isValidCookItem( ItemStack stack )
	{
		if ( SCCraftingManagerPanCooking.instance.getRecipe( stack ) != null )
		{
			return true;
		}
		
		return false;
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
		SCTileEntityPan pan = (SCTileEntityPan)world.getBlockTileEntity( i, j, k );
		
		float collisionBoxHeight = 3/16F;

		List collisionList = null;

		// check for items within the collection zone

		collisionList = world.getEntitiesWithinAABB(EntityItem.class,
				AxisAlignedBB.getAABBPool().getAABB((float) i, (float) j + collisionBoxHeight, (float) k,
						(float) (i + 1), (float) j + collisionBoxHeight + 0.05F, (float) (k + 1)));

		if (collisionList != null && collisionList.size() > 0)
		{
			TileEntity tileEnt = world.getBlockTileEntity(i, j, k);
			
			if (!(tileEnt instanceof IInventory) || pan.getCookStack() != null  ) {
				return;
			}
			
			if (tileEnt.getBlockMetadata() == 2)
			{
				collisionBoxHeight = -1F + 11/16F;
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

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return -1;
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
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
	@Override
    public void setBlockBoundsBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
    {
    	// override to deprecate parent
    }
	
	 @Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
	    int meta = blockAccess.getBlockMetadata(i, j, k);
	
	    switch ( meta )
	    {
	        case 2:
	        	
	        	return AxisAlignedBB.getAABBPool().getAABB( 
	        			3/16D, 0D - 8/16D, 3/16D, 1D - 3/16D, 0D - 8/16D + 3/16D, 1D - 3/16D );

	        default:
	        	
	        	return AxisAlignedBB.getAABBPool().getAABB( 
	        			3/16D, 0D, 3/16D, 1D - 3/16D, 3/16D, 1D - 3/16D );
	    }
	}	

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return GetBlockBoundsFromPoolBasedOnState( world, i, j, k ).offset( i, j, k );
	}
	
    @Override
	public boolean IsBlockRestingOnThatBelow( IBlockAccess blockAccess, int i, int j, int k )
	{
        int iMetadata = blockAccess.getBlockMetadata( i, j, k );
        
        return iMetadata == 1 || iMetadata == 2;
	}
    
    @Override
	public boolean CanRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
    
    @Override
	public boolean RotateAroundJAxis( World world, int i, int j, int k, boolean bReverse )
	{
        TileEntity tileEnt = world.getBlockTileEntity( i, j, k );
    	
        if ( tileEnt != null && tileEnt instanceof SCTileEntityPan)
        {        	
        	SCTileEntityPan skullEnt = (SCTileEntityPan)tileEnt;
        	
        	int iSkullFacing = skullEnt.GetSkullRotationServerSafe();
        	
        	if ( bReverse )
        	{
        		iSkullFacing += 2;
        		
        		if ( iSkullFacing > 7 )
        		{
        			iSkullFacing -= 8;
        		}
        	}
        	else
        	{
        		iSkullFacing -= 2;
        		
        		if ( iSkullFacing < 0 )
        		{
        			iSkullFacing += 8;
        		}
        	}
        	
        	skullEnt.setSkullRotation( iSkullFacing );
        	
	        world.markBlockForUpdate( i, j, k );
	        
	        return true;
        }
        
        return false;
	}

    
    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
    {
        int var6 = par1World.getBlockId(par2, par3 + 1, par4);
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        
       	if (var6 == 0 || blocksList[var6].blockMaterial.isReplaceable())
       	{
       		return true;
       	}
       	if (te != null && te instanceof FCTileEntityCampfire)
       	{
       		return true;
		}
       	else return false;
    }

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new SCTileEntityPan();
    }

    /**
     * only called by clickMiddleMouseButton , and passed to inventory.setCurrentItem (along with isCreative)
     */
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return this.blockID;
    }

    /**
     * Get the block's damage value (for use with pick block).
     */
    public int getDamageValue(World par1World, int par2, int par3, int par4)
    {
    	return super.getDamageValue(par1World, par2, par3, par4);
    }

    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return 0;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {}

    /**
     * Called when the block is attempted to be harvested
     */
    public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
    {
        if (par6EntityPlayer.capabilities.isCreativeMode)
        {
            par5 |= 8;
            par1World.setBlockMetadataWithNotify(par2, par3, par4, par5, 4);
        }

        super.onBlockHarvested(par1World, par2, par3, par4, par5, par6EntityPlayer);
    }

    /**
     * ejects contained items into the world, and notifies neighbours of an update, as appropriate
     */
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if (!par1World.isRemote)
        {
            if ((par6 & 8) == 0)
            {
                ItemStack var7 = new ItemStack(Item.skull.itemID, 1, this.getDamageValue(par1World, par2, par3, par4));
                SCTileEntityPan var8 = (SCTileEntityPan)par1World.getBlockTileEntity(par2, par3, par4);

                this.dropBlockAsItem_do(par1World, par2, par3, par4, var7);
            }

            super.breakBlock(par1World, par2, par3, par4, par5, par6);
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.blockID;
    }


    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister par1IconRegister) {}

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
        return Block.cauldron.getBlockTextureFromSide(par1);
    }
    
    @Override
    public ItemStack GetStackRetrievedByBlockDispenser( World world, int i, int j, int k )
    {
		// don't allow skulls to be retrieved because their code is a mess
		
    	return null;
    }
    
	//----------- Client Side Functionality -----------//
    
    @Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	return false;
    }   
}
