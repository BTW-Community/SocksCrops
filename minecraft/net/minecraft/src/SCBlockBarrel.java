package net.minecraft.src;

import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

public class SCBlockBarrel extends BlockContainer {
	
	public static final SCModelBlockBarrel model = new SCModelBlockBarrel();
	
	public static final double m_dCollisionBoxHeight = 1F;
	
	protected SCBlockBarrel(int blockID) {
		super(blockID, Material.wood);
		setUnlocalizedName("SCBlockBarrel");
	}
	
	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new SCTileEntityBarrel();
	}
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		return Item.cauldron.itemID;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return Item.cauldron.itemID;
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {		
		
		SCTileEntityBarrel barrel = (SCTileEntityBarrel)world.getBlockTileEntity( i, j, k );
        
    	ItemStack heldStack = player.getCurrentEquippedItem();

    	if ( heldStack != null )
		{
    		if (barrel.isValidLiquid(heldStack))
    		{
    			if (FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 9, 11) == 3) return false;
    			
    			ItemStack liquidToStore = SCUtilsLiquids.getLiquidBlockFromBottle(heldStack);
        		Item returnItem = Item.glassBottle;
        		int numberOfLiquidsToStore = 1;
        		if (liquidToStore == null)
        		{
        			//if not a bottle then get the bucket
        			liquidToStore = SCUtilsLiquids.getLiquidBlockFromBucket(heldStack);
        			returnItem = Item.bucketEmpty;
        			numberOfLiquidsToStore = 3;            			
        			if (liquidToStore == null)
            		{
        				//if not a bucket then just insert the liquid Block
        				liquidToStore = heldStack.copy();
            		}        			
        		}

        		for (int index = 0; index < numberOfLiquidsToStore; index++)
        		{
        			FCUtilsInventory.AddItemStackToInventoryInSlotRange(barrel, liquidToStore, 9, 11);
        		}
        		heldStack.stackSize--;
        		FCUtilsItem.GivePlayerStackOrEject(player, new ItemStack(returnItem));
        		

    		}
    		else if (heldStack.itemID == Item.glassBottle.itemID)
    		{
    			if (FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 9, 11) == 0) return false;
    			
    			ItemStack returnStack;
    			for (int index = 11; index > 8; index--)
    			{
    				if ( barrel.getStackInSlot(index) != null)
    				{
    					heldStack.stackSize--;
    					returnStack = SCUtilsLiquids.getBottleFromLiquidBlock(barrel.getStackInSlot(index)).copy();
    					FCUtilsItem.GivePlayerStackOrEject(player, returnStack.copy());
    					FCUtilsInventory.DecrStackSize(barrel, index, 1);
    					
    					break;
    				}
    			}	
		
    		}
    		else if (heldStack.itemID == Item.bucketEmpty.itemID)
    		{
    			if (FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 9, 11) != 3) return false;
    			
    			ItemStack returnStack = null;
    			for (int index = 11; index > 8; index--)
    			{
    				returnStack = SCUtilsLiquids.getBucketFromLiquidBlock(barrel.getStackInSlot(index)).copy();    					
					FCUtilsInventory.DecrStackSize(barrel, index, 1);
    			}
    			heldStack.stackSize--;
    			FCUtilsItem.GivePlayerStackOrEject(player, returnStack.copy());
    			
   
    			
    		}
    		else {			
    			if ( FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 0, 8) == 9) return false;
    			
    			int slot = FCUtilsInventory.GetFirstEmptyStack(barrel);
				FCUtilsInventory.AddItemStackToInventoryInSlotRange(barrel, heldStack, slot, slot);
			}
    		
    		if ( !world.isRemote ) world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
    		if ( !world.isRemote ) world.markBlockForRenderUpdate(i, j, k);
    		
    		return true;
		}
    	else {
    		ItemStack returnStack;
			for (int index = 8; index >= 0; index--)
			{
				if ( barrel.getStackInSlot(index) != null)
				{
					
					FCUtilsItem.GivePlayerStackOrEject(player, barrel.getStackInSlot(index).copy());
					FCUtilsInventory.DecrStackSize(barrel, index, 1);
					
					if ( !world.isRemote ) world.playAuxSFX( FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0 );
		    		world.markBlockForRenderUpdate(i, j, k);
					return true;
				}
			}		
    	}
    	
    	
		return false;		
    }
	
	@Override
    public void onEntityCollidedWithBlock( World world, int i, int j, int k, Entity entity )
    {
		if ( world.isRemote )
		{
			// don't collect items on the client, as it's dependent on the state of the inventory
			
			return;
		}
		
		// --- MIXER CODE --- //
		TileEntity te = world.getBlockTileEntity(i, j + 2, k);
		boolean hasMixer = te instanceof SCTileEntityMixer;
		
		if ( hasMixer )
		{
//			System.out.println("movecount: "+ ((SCTileEntityMixer)te).moveCount);
//			if (((SCTileEntityMixer)te).moveCount >= 50)
//			{
//				return; // don't collect items, when mixer whisk is extended more than 80%
//			}
			
			List collisionList = null;
	        
	        // check for items within the collection zone       
	        
	        collisionList = world.getEntitiesWithinAABB( EntityItem.class, 
	    		AxisAlignedBB.getAABBPool().getAABB( (float)i, (float)j + m_dCollisionBoxHeight, (float)k, 
					(float)(i + 1), (float)j + m_dCollisionBoxHeight + 0.05F, (float)(k + 1)) );

	    	if ( collisionList != null && collisionList.size() > 0 )
	    	{
	    		TileEntity tileEnt = world.getBlockTileEntity( i, j, k );
	    		
	    		if ( !( tileEnt instanceof IInventory ) )
	    		{
	    			return;
	    		}
	    	
	            IInventory inventoryEntity = (IInventory)tileEnt;
	            
	            for ( int listIndex = 0; listIndex < collisionList.size(); listIndex++ )
	            {
		    		EntityItem targetEntityItem = (EntityItem)collisionList.get( listIndex );
		    		
			        if ( !targetEntityItem.isDead )
			        {
			        	
	        			if ( FCUtilsInventory.AddItemStackToInventoryInSlotRange( inventoryEntity, targetEntityItem.getEntityItem(), 0, 8) )
	        			{
	        				
				            world.playSoundEffect( (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
				            		"random.pop", 0.25F, 
				            		((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
				            
				            targetEntityItem.setDead();			            
	        			}
			        }		        
	            }
	    	}
		}        
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

	@Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		return true;
    }
	
	public static boolean secondPass;

    private Icon barrelTop;
    private Icon barrelSide;
    private Icon barrelBottom;
    private Icon barrelInner;
    private Icon juice;
    private Icon water;
    private Icon milk;
    private Icon gravel;
    private Icon liquid;
    
    private boolean liquidPass = secondPass;
    
	@Override
	public void registerIcons(IconRegister register)
	{
        barrelTop = register.registerIcon( "cauldron_top" );
        blockIcon = barrelSide = register.registerIcon( "cauldron_side" );
        barrelBottom = register.registerIcon( "cauldron_bottom" );
        barrelInner = register.registerIcon( "cauldron_inner" );
        //juice = register.registerIcon("SCBlockLiquid_apple");
        water = register.registerIcon("water");
        liquid = register.registerIcon("SCBlockLiquid");        
        milk = register.registerIcon("fcBlockMilk");
        gravel = register.registerIcon("gravel");
	}
	
	private Icon getLiquidTypeIcon(RenderBlocks renderer, int i, int j, int k)
	{
		Icon liquidIcon = liquid;
		
		int slot = 9;
		if (((SCTileEntityBarrel) renderer.blockAccess.getBlockTileEntity(i, j, k)).getStackInSlot(slot) != null)
		{
			ItemStack liquid = ((SCTileEntityBarrel) renderer.blockAccess.getBlockTileEntity(i, j, k)).getStackInSlot(slot).copy();
			
			if (liquid.getItem() instanceof ItemBlock && Block.blocksList[liquid.itemID] != null)
			{
				return Block.blocksList[liquid.itemID].getIcon(1, liquid.getItemDamage());
			}

		}
		return liquidIcon;
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		if (side == 0) return barrelBottom;
		else if (side == 1) return barrelTop;
		else return barrelSide;
	}
	
	@Override
	public int getRenderColor(int par1) {
		// TODO Auto-generated method stub
		return super.getRenderColor(par1);
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		
		return super.colorMultiplier(blockAccess, x, y, z);
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		TileEntity te = renderer.blockAccess.getBlockTileEntity(i, j, k);
		SCTileEntityBarrel barrel = (SCTileEntityBarrel)te;
		
		renderer.setOverrideBlockTexture( barrelInner );
    	renderer.setRenderBounds(
    			2/16D, 3/16D, 2/16D,
    			14/16D, 4/16D, 14/16D);
    	renderer.renderStandardBlock(this, i, j, k);
    	renderer.clearOverrideBlockTexture();
		
		int items = FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 0, 8);
		
		if (items > 0)
		{
			renderer.setOverrideBlockTexture( gravel );
			
			int liquidCount = FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 9, 11);
	    	if (liquidCount > 0)
			{
		    	renderer.setRenderBounds(
		    			3/16D, 4/16D, 3/16D,
		    			13/16D, (items+6)/16D, 13/16D);
			}
	    	else {
		    	renderer.setRenderBounds(
		    			2/16D, 4/16D, 2/16D,
		    			14/16D, (items+6)/16D, 14/16D);
	    	}
	    	renderer.renderStandardBlock(this, i, j, k);
	    	renderer.clearOverrideBlockTexture();
		}    	
		return model.RenderAsBlock( renderer, this, i, j, k );
	}
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
		
		secondPass = true;
		
		TileEntity te = renderer.blockAccess.getBlockTileEntity(i, j, k);
		SCTileEntityBarrel barrel = (SCTileEntityBarrel)te;
		
		int liquidCount = FCUtilsInventory.GetNumOccupiedStacksInRange(barrel, 9, 11);

		if (liquidCount > 0)
		{
	    	renderer.setOverrideBlockTexture( getLiquidTypeIcon(renderer, i, j, k) );
	    	renderer.setRenderBounds(
	    			2/16D, 4/16D, 2/16D,
	    			14/16D, (3 + (liquidCount*4))/16D - 1/64D, 14/16D);
	    	renderer.renderStandardBlock(this, i, j, k);
	    	renderer.clearOverrideBlockTexture();
		}
		secondPass = false;
	}
	
	public boolean HasFilter( IBlockAccess iBlockAccess, int i, int j, int k )
	{	
		return ( iBlockAccess.getBlockMetadata( i, j, k ) == 1 );
	}

	public void SetHasFilter( World world, int i, int j, int k, boolean bOn )
	{
		int iMetaData = world.getBlockMetadata( i, j, k );

		if ( bOn )
		{
			iMetaData = 1;
		}
		else
		{
			iMetaData = 0;
		}

		world.setBlockMetadataWithNotifyNoClient( i, j, k, iMetaData );
	}
}
