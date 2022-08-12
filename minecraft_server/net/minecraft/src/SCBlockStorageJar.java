package net.minecraft.src;

import java.util.ArrayList;
import java.util.Random;

public class SCBlockStorageJar extends BlockContainer {
	
	private ArrayList<Integer> validItemList = SCTileEntityStorageJar.getValidItemList();
	
	protected SCBlockStorageJar(int blockID) {
		super(blockID, Material.glass);
		
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("SCBlockStorageJar");
	}
	
	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityStorageJar();
	}

//	@Override
//	public int getLightValue(IBlockAccess blockAccess, int x, int y, int z)
//	{
//		SCTileEntityStorageJar jar = (SCTileEntityStorageJar)blockAccess.getBlockTileEntity( x, y, z );
//		
//		ItemStack stack = jar.getStorageStack();
//		
//		if (stack != null)
//		{
//			int stackID = stack.itemID;
//			int stackSize = stack.stackSize;
//			
//			if (stackID == Item.lightStoneDust.itemID)
//			{
//				if (stackSize <= 8) return 2;
//				else if (stackSize <= 16) return 3;
//				else if (stackSize <= 24) return 4;
//				else if (stackSize <= 32) return 5;
//				else if (stackSize <= 40) return 6;
//				else if (stackSize <= 48) return 7;
//				else if (stackSize <= 56) return 8;
//				else if (stackSize <= 64) return 9;
//			}
//			else if (stackID == Item.redstone.itemID)
//			{
//				return 5;
//			}
//		}
//
//		return super.getLightValue(blockAccess, x, y, z);
//	}
	
	@Override
	public int quantityDropped(Random random) {
		return 0;
	}
	
	@Override
	public int damageDropped(int par1) {
		return 0;
	}	
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntityStorageJar jar = (SCTileEntityStorageJar)world.getBlockTileEntity( i, j, k );
		ItemStack storageStack = jar.getStorageStack();
//        ArrayList<Integer> validItemList = jar.getValidItemList();
		
		if ( world.isRemote )
        {
        	//jar.AttemptToAddToStorageFromStack( heldStack );
            return true;
        }
		else {
	    	if ( heldStack != null )
        	{
	    		if ( !jar.hasLabel() && heldStack.itemID == Item.paper.itemID )
	    		{
	    			jar.setLabel( true );
	    			
	    			return true;
	    		}   			
	    		
	    		if ( validItemList.contains( heldStack.itemID ) )
	    		{
	    			if (!player.isSneaking())
	    			{
	    				
	    			}
	    			
	    			if (storageStack == null )
	    			{
	    				jar.setSeedType( heldStack.itemID );
	    				
		    			if (!player.isSneaking())
		    			{
			    			
			    			FCUtilsInventory.AddItemStackToInventory(jar , heldStack);
			    			
			    			jar.markBlockForUpdate();
			    			world.markBlockForRenderUpdate(i, j, k);
		    			}
		    			else 
		    			{
		    				ItemStack splitStack = heldStack.splitStack(1);
		    				
			    			FCUtilsInventory.AddItemStackToInventory(jar , splitStack);
			    			
			    			jar.markBlockForUpdate();
			    			world.markBlockForRenderUpdate(i, j, k);
		    			}
	
	    			}
	    			else if (storageStack != null && storageStack.itemID == heldStack.itemID )
	    			{
	    				if (storageStack.stackSize < 64 && (storageStack.stackSize + heldStack.stackSize) <= 64)
						{	 
			    			
							int newStackSize = storageStack.stackSize + heldStack.stackSize;
							storageStack.stackSize = newStackSize;
							
							heldStack.stackSize = 0;
							
							jar.markBlockForUpdate();
							world.markBlockForRenderUpdate(i, j, k);
							
							return true;
						}
	    			}
	    			
	    			return true;
	    			
	    		}
        	}
	    	else if (heldStack == null || heldStack.itemID == jar.getStorageStack().itemID )
	    	{
	    		//jar.ejectStorageContents(iFacing);
	    		
	    		if (storageStack != null )
	    		{
		    		if (!player.isSneaking())
		    		{

		    			jar.ejectStorageContents(iFacing);
			    		
			    		jar.setSeedType(0);
			    		
			    		jar.onInventoryChanged();
		    		}
		    		else
		    		{
		    			
		    		}

		    		
		    		return true;
	    		}	    		

	    	}
		}
		return false;
    }
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
		SCTileEntityStorageJar tileEntity = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
		
    	if ( tileEntity != null )
    	{
    		dropItemBlock(world, i, j, k, tileEntity, newStack);
    	}
    	
        super.breakBlock( world, i, j, k, iBlockID, iMetadata );
    }
	
    private void dropItemBlock(World world, int i, int j, int k, SCTileEntityStorageJar tileEntity, ItemStack newStack)
    {
        if ( tileEntity.getStackInSlot(0) != null)
        {
        	ItemStack oldStack = tileEntity.getStorageStack();
        	
            NBTTagCompound newTag = new NBTTagCompound();
            
            newStack.setTagCompound(newTag);
            newStack.getTagCompound().setInteger( "id", oldStack.itemID );
            newStack.getTagCompound().setInteger( "Count", oldStack.stackSize );
            newStack.getTagCompound().setInteger( "Damage",  this.getDamageValue(world, i, j, k) );
            
            newStack.getTagCompound().setInteger( "seedType", tileEntity.getSeedType() );
            newStack.getTagCompound().setBoolean( "hasLabel", tileEntity.hasLabel() );
            newStack.getTagCompound().setInteger( "seedDamage", oldStack.getItemDamage());
        	
        }
		
        this.dropBlockAsItem_do(world, i, j, k, newStack);  
		
	}

	public int getDamageValue(World world, int i, int j, int k)
    {
    	SCTileEntityStorageJar jar = (SCTileEntityStorageJar) world.getBlockTileEntity(i, j, k);
    	ItemStack storageStack;
    	int stackSize = 0;
    	int contentHeight = 0;
    	
    	if (jar.getStorageStack() != null)
		{
    		storageStack = jar.getStorageStack();
    		
    		stackSize = storageStack.stackSize;
    		
    	    if (stackSize > 7)
    	    {
    	        contentHeight = ( stackSize/8 ) - 1;
    	    }
    	    else
    	    	contentHeight = ( stackSize/8 ) + 1;

//			if (stackSize <= 8)
//			{
//				contentHeight = Math.ceil( stackSize/8 ) + 1;
//			}
//			else contentHeight = Math.ceil( stackSize/8 );
//        	
			int seedID = jar.getStackInSlot(0).itemID;
			int seedTypeForItemRender = jar.getSeedTypeForItemRender(seedID);
			
//			if (storageStack.itemID == Item.dyePowder.itemID)
//			{
//				int dyeDamage = storageStack.getItemDamage();
//				
//				seedTypeForItemRender = seedTypeForItemRender + dyeDamage;
//			}

			return  dataToDamage(jar.hasLabel(), contentHeight, storageStack.getItemDamage(), seedTypeForItemRender);
		}
    	
    	else return  dataToDamage(jar.hasLabel(), 0, 0, 0);
    }

    
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack itemStack)
	{
		SCTileEntityStorageJar tileEntity = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
	
		int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		world.setBlockMetadataWithNotify(i, j, k, playerRotation);
		
		
		if ( tileEntity != null )
    	{
			
			hasAttachableBlockAbove(world, i, j, k);
			
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
					tileEntity.setSeedType(seedType);	
				}
				
				if (itemStack.stackTagCompound.hasKey("seedDamage") )
				{
					seedDamage = itemStack.stackTagCompound.getInteger("seedDamage");
					tileEntity.setSeedType(seedType);
				}
				
				
//				if (itemStack.stackTagCompound.hasKey("hasLabel") )
//				{
//					label = itemStack.stackTagCompound.getBoolean("hasLabel");
//					tileEntity.setLabel(true);
//				}
//				else tileEntity.setLabel(false);
				
				
				
				tileEntity.setInventorySlotContents(0, new ItemStack(id, count, damage));
				
				tileEntity.getStorageStack().setItemDamage(seedDamage);
				
			}
			
			int labelDamage = damageToData( itemStack.getItemDamage() )[0];
			
			if (labelDamage == 1)
			{
				tileEntity.setLabel(true);
			}
			else tileEntity.setLabel(false);
			
    	}
	}

	
	@Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    { 
		int id = world.getBlockId(i, j +1, k); 
		if ( !isAttachableBlock(id) ) 
		{
			if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) ) return false; 
			else return super.canPlaceBlockAt( world, i, j, k );
		}
		else return super.canPlaceBlockAt( world, i, j, k );
    }
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
	{
        if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
        {
    		SCTileEntityStorageJar tileEntity = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
    		ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
        	
        	//dropItemBlock(world, i, j, k, tileEntity, newStack);
        	world.setBlockToAir(i, j, k);
        	world.removeBlockTileEntity(i, j, k);
        	
        }
        else
        {
        	hasAttachableBlockAbove(world, i, j, k);
        }

		
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
	
    private boolean hasAttachableBlockAbove(World world, int i, int j, int k)
    {
    	int blockAbove = world.getBlockId(i, j + 1, k);
		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta <= 3)
		{
    		if ( isAttachableBlock(blockAbove))
    		{
    			world.setBlockMetadataWithNotify(i, j, k, meta + 4);
    			return true;
    		}
		}
		else
		{
    		if (!isAttachableBlock(blockAbove))
    		{
    			world.setBlockMetadataWithNotify(i, j, k, meta - 4);
    			return false;
    		}
		}
		
		return false;
		
	}

    @Override
    public boolean CanRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k)
    {
    	return true;
    }
    
    @Override
    public int SetFacing(int iMetadata, int iFacing) {

    	return iMetadata;
    }
    
    @Override
    public int GetFacing(IBlockAccess blockAccess, int i, int j, int k) {
    	return getDirection(blockAccess.getBlockMetadata(i, j, k));
    }
    
	@Override
	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
	{
		int iDirection = iMetadata;
		
		if ( iDirection == 0 )
		{
			iDirection = 3;
		}
		else if ( iDirection == 3 )
		{
			iDirection = 2;
		}
		else if ( iDirection == 2 )
		{
			iDirection = 1;
		}
		else if ( iDirection == 1 )
		{
			iDirection = 0;
		}
		
		iMetadata = iDirection;
		
		return iMetadata;
	}
    
	/**
     * Returns the orentation value from the specified metadata
     */
    public static int getDirection(int meta)
    {
        return meta & 3;
    }
	
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
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		int meta = blockAccess.getBlockMetadata(i, j, k);

		if (meta > 3) return getBounds(4, 0 + moveUp, 16 + moveUp, 4);
		
		else return getBounds(4, 0, 11, 4);
	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolForItemRender( int iItemDamage )
	{
		return getBounds(4, 0, 11, 4);
	}
	
	private AxisAlignedBB getBounds(double i, double minJ, double maxJ, double k)
	{
   	
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			8/16D - i/16D, minJ/16D, 8/16D - k/16D, 
			8/16D + i/16D, maxJ/16D, 8/16D + k/16D);
		
		return box;
	}

	private int moveUp = 0;
	
	
	
	/**
	 * 
	 * @param label
	 * @param fill
	 * @param damage
	 * @param typeID
	 * @return damage value, combining the input par
	 */
    public static int dataToDamage(boolean label, int fill, int damage, int typeID)
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

    	
//        int iLabel;
//        
//        if (label)
//        {
//            iLabel = 1;
//        }
//        else iLabel = 0;
//        
//        int fillDamage = fill *10;
//        int typeDamage = type *100;
//        
//        System.out.println("iDamage = " + (iLabel + fillDamage + typeDamage) );
//        
//        return iLabel + fillDamage + typeDamage;
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

    	
    	
//		int label=(n/1)%10;
//		int fill= (n/10)%10;
//		int type = (n/100)%10;
//    	int typeExtended = (n/1000)%10;
//   	 	int typeExtendedPlus = (n/10000)%10;
//    
//   	 	int content = typeExtendedPlus*100 + typeExtended*10 + type;
//    
////    	boolean hasLabel = label == 1;
////    	boolean empty = fill == 0;
//    	
//    	int[] intArray;
//    	
//    	return intArray = new int[] {content, fill, label};
    }
    


}
