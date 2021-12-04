package net.minecraft.src;

import java.util.Random;
import java.util.jar.JarEntry;

public class SCBlockSeedJar extends BlockContainer {

	protected SCBlockSeedJar(int blockID) {
		super(blockID, Material.glass);
		setCreativeTab(CreativeTabs.tabMisc);
		
		setUnlocalizedName("SCBlockSeedJar");

	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntitySeedJar();
	}
	
	@Override
    public int quantityDropped(Random random)
    {
        return 0;
    }
	
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		//from FCBlockVase
		
		ItemStack heldStack = player.getCurrentEquippedItem();
		SCTileEntitySeedJar jar = (SCTileEntitySeedJar)world.getBlockTileEntity( i, j, k );
		
        if ( world.isRemote )
        {
        	jar.AttemptToAddToStorageFromStack( heldStack );
            return true;
        } 
        else
        {
        	if ( heldStack != null )
        	{
        		
        		ItemStack storageStack = jar.GetStorageStack();
        		int[] validIDs = jar.validIDs;
        		
        		for (int type = 0; type < validIDs.length; type++)
        		{

        			if (heldStack.itemID == validIDs[type]) //is seed a validID && the jar is empty
        			{
        				jar.SetSeedType(type + 1);
        				
        				
        				FCUtilsInventory.AddItemStackToInventory(jar , heldStack);
        				return true;

        			}
        		}
        		
        		if (!jar.HasLabel() && heldStack.itemID == Item.paper.itemID) 
        		{
        			jar.applyLabel();
        			return true;
        		}
        	}
        }
        
        
        
    	return false;
    }
	
	private boolean doesJarHaveSpace(ItemStack storageStack, ItemStack heldStack)
	{
		int storageSize = storageStack.stackSize;
		int heldSize = heldStack.stackSize;
		
		if (storageSize + heldSize <= 64)
		{
			return true;
		}
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack itemStack)
	{
		SCTileEntitySeedJar tileEntity = (SCTileEntitySeedJar)( world.getBlockTileEntity(i, j, k) );
		
		if ( tileEntity != null )
    	{
			NBTTagCompound newTag = new NBTTagCompound("scStorageStack");
			ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
			
			if (itemStack.hasTagCompound())
			{
				int id = 0;
				int count = 0; 
				int damage = 0;
				
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
				
				if (itemStack.stackTagCompound.hasKey("scSeedType") )
				{
					itemStack.stackTagCompound.getInteger("scSeedType");
					tileEntity.SetSeedType(seedType);
					
				}
				
				if (itemStack.stackTagCompound.hasKey("scHasLabel") )
				{
					label = itemStack.stackTagCompound.getBoolean("scHasLabel");
					tileEntity.SetHasLabel(label);
					
				}
				
				tileEntity.setInventorySlotContents(0, new ItemStack(id, count, damage));
				
			}
			
    	}
	}
	
	@Override
    public void breakBlock( World world, int i, int j, int k, int iBlockID, int iMetadata )
    {
		SCTileEntitySeedJar tileEntity = (SCTileEntitySeedJar)( world.getBlockTileEntity(i, j, k) );
		ItemStack newStack = new ItemStack(this, 1, this.getDamageValue(world, i, j, k));
		
    	if ( tileEntity != null )
    	{

            if ( tileEntity.getStackInSlot(0) != null)
            {
            	ItemStack oldStack = tileEntity.getStackInSlot(0);
            	
                NBTTagCompound newTag = new NBTTagCompound();
                newStack.setTagCompound(newTag);
                newStack.getTagCompound().setInteger( "id", oldStack.itemID );
                newStack.getTagCompound().setInteger( "Count", oldStack.stackSize );
                newStack.getTagCompound().setInteger( "Damage", oldStack.getItemDamage() );
                
                newStack.getTagCompound().setInteger( "scSeedType", tileEntity.GetSeedType() );
                newStack.getTagCompound().setBoolean( "scHasLabel", tileEntity.HasLabel() );
            	
            }
    		
            this.dropBlockAsItem_do(world, i, j, k, newStack);  
    	}
    	
    	

        super.breakBlock( world, i, j, k, iBlockID, iMetadata );
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
	
	 /**
     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
     * coordinates.  Args: blockAccess, x, y, z, side
     */
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i, int j, int k, int side)
    {
    	return true;
    }

	//----------- Client Side Functionality -----------//
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return getBounds(4, 0, 11, 4);
	}
	
	private AxisAlignedBB getBounds(double i, double minJ, double maxJ, double k)
	{
    	
		AxisAlignedBB box = AxisAlignedBB.getAABBPool().getAABB( 
			8/16D - i/16D, minJ/16D, 8/16D - k/16D, 
			8/16D + i/16D, maxJ/16D, 8/16D + k/16D);
		
		return box;
	}
	
	private Icon glassSide;
	private Icon glassTop;
	private Icon cork;
	private Icon contentsPumpkin;
	private Icon contentsMelon;
	private Icon contentsNetherwart;
	private Icon contentsWheat;
	private Icon contentsHemp;
	private Icon contentsCarrot;
	private Icon gravel;
	
	private Icon labelPumpkin;
	private Icon labelMelon;
	private Icon labelNetherwart;
	private Icon labelWheat;
	private Icon labelHemp;
	private Icon labelCarrot;
	private Icon labelBlank;
	
	@Override
	public void registerIcons(IconRegister register) {
		glassSide = register.registerIcon("SCBlockJar_side");
		glassTop = register.registerIcon("SCBlockJar_top");
		cork = register.registerIcon("tree_top");
		
		contentsPumpkin = register.registerIcon("SCBlockJarContents_pumpkin");
		contentsMelon = register.registerIcon("SCBlockJarContents_melon");
		contentsNetherwart = register.registerIcon("SCBlockJarContents_netherwart");
		contentsWheat = register.registerIcon("SCBlockJarContents_wheat");
		contentsHemp = register.registerIcon("SCBlockJarContents_hemp");
		contentsCarrot = register.registerIcon("SCBlockJarContents_carrot");
		
		labelPumpkin = register.registerIcon("SCBlockJarLabel_pumpkin");
		labelMelon = register.registerIcon("SCBlockJarLabel_melon");
		labelNetherwart = register.registerIcon("SCBlockJarLabel_netherwart");
		labelWheat = register.registerIcon("SCBlockJarLabel_wheat");
		labelHemp = register.registerIcon("SCBlockJarLabel_hemp");
		labelCarrot = register.registerIcon("SCBlockJarLabel_carrot");
		labelBlank = register.registerIcon("SCBlockJarLabel_blank");

		gravel = register.registerIcon("gravel");
	}

	private Icon setContentIcon(int seedType, SCTileEntitySeedJar jar)
	{		
		if (seedType == 1) 
		{
			return contentsPumpkin;
		}
		else if (seedType == 2) 
		{
			return contentsMelon;
		}
		else if (seedType == 3) 
		{
			return contentsNetherwart;
		}
		else if (seedType == 4) 
		{
			return contentsHemp;
		}
		else if (seedType == 5) 
		{
			return contentsWheat;
		}
		else if (seedType == 6) 
		{
			return contentsCarrot;
		}
		else return gravel;
	}
	

	@Override
	public Icon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
		{
			return glassTop;
		}
		else return glassSide;
	}

	
	private Icon getLabelIcon(int seedType, SCTileEntitySeedJar jar)
	{
		if (seedType == 1) 
		{
			return labelPumpkin;
		}
		else if (seedType == 2) 
		{
			return labelMelon;
		}
		else if (seedType == 3) 
		{
			return labelNetherwart;
		}
		else if (seedType == 4) 
		{
			return labelHemp;
		}
		else if (seedType == 5) 
		{
			return labelWheat;
		}
		else if (seedType == 6) 
		{
			return labelCarrot;
		}
		else return labelBlank;
	}

	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
		
		//Cork
		renderBlocks.setRenderBounds(getBounds(3, 9, 11, 3));
		FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, cork);
		
		SCTileEntitySeedJar jar = (SCTileEntitySeedJar)renderBlocks.blockAccess.getBlockTileEntity( i, j, k );
		int seedType = jar.GetSeedType();
		int storageSize = 0;
		
		if (jar.getStackInSlot(0) != null) 
		{
			storageSize = jar.getStackInSlot(0).stackSize;
		}
		
		if (storageSize > 0)
		{
			double contentsHeight;
			
			if (storageSize < 8)
			{
				contentsHeight = Math.ceil( storageSize/8 ) + 1;
			}
			else contentsHeight = Math.ceil( storageSize/8 );
			
			//Contents
			renderBlocks.setRenderBounds(getBounds(3, 0, contentsHeight , 3));
			FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, setContentIcon(seedType, jar));

		}

		
    	return true;
    }
	
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult)
	{
		SCTileEntitySeedJar jar = (SCTileEntitySeedJar)renderBlocks.blockAccess.getBlockTileEntity( i, j, k );
		int seedType = jar.GetSeedType();

		
    	if ( bFirstPassResult)
    	{
    		
    		//Jar
    		renderBlocks.setRenderBounds(getBounds(4, 0.001, 10, 4));
    		renderBlocks.renderStandardBlock(this, i, j, k);
    		
    		if (jar.HasLabel())
    		{
        		//renderBlocks.setRenderBounds(getBounds(4, 0, 10, 4));
        		renderBlocks.renderFaceZPos(this, i, j, k, getLabelIcon(seedType, jar));
    		}
    	}
	}

}
