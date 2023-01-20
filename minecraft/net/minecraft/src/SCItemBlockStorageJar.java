package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCItemBlockStorageJar extends ItemBlock {
        
	protected int m_iPlacedBlockID;

	public static final int OAK = 0;
	public static final int SPRUCE = 1;
	public static final int BIRCH = 2;
	public static final int JUNGLE = 3;
	public static final int BLOOD = 4;
	
	public SCItemBlockStorageJar(int par1, Block placedBlock) {
		super(par1);
		this.setMaxStackSize(1);
		m_iPlacedBlockID = placedBlock.blockID;
	}
	
    @Override
	public boolean OnItemUsedByBlockDispenser( ItemStack stack, World world, int i, int j, int k, int iFacing )
	{
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k, iFacing );
        Block placedBlock = Block.blocksList[m_iPlacedBlockID]; 
            	
        if ( world.isAirBlock( targetPos.i, targetPos.j, targetPos.k ) && placedBlock != null && 
        	placedBlock.canPlaceBlockAt( world, targetPos.i, targetPos.j, targetPos.k ) )
        {
            placeBlock(world, targetPos.i, targetPos.j, targetPos.k, stack, iFacing);
            
            world.playSoundEffect( targetPos.i + 0.5D, targetPos.j + 0.5D, targetPos.k + 0.5D, 
            	Block.soundGrassFootstep.getPlaceSound(), 
            	( Block.soundGrassFootstep.getPlaceVolume() + 1F ) / 2F, 
            	Block.soundGrassFootstep.getPlacePitch() * 0.8F );
            
            return true;
        }
        
        return false;
	}
    
	private void placeBlock(World world, int i, int j, int k, ItemStack itemStack, int iFacing) {
		

		world.setBlock(i, j, k, m_iPlacedBlockID);
		
		SCTileEntityStorageJar storageJar = (SCTileEntityStorageJar)( world.getBlockTileEntity(i, j, k) );
		
		if ( storageJar != null )
    	{			
			//NBTTagCompound newTag = new NBTTagCompound("storageStack");
			//ItemStack newStack = new ItemStack(this, 1, itemStack.getItemDamage());
			
			if ( itemStack.hasTagCompound() )
			{
				int id = 0;
				int count = 0; 
				int damage = 0;
				int seedDamage = 0;
				
				int corkType = 0;				
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
				
				if (itemStack.stackTagCompound.hasKey("corkType") )
				{
					corkType = itemStack.stackTagCompound.getInteger("corkType");
					storageJar.setCorkType(corkType);
				}
				
				
//				if (itemStack.stackTagCompound.hasKey("hasLabel") )
//				{
//					label = itemStack.stackTagCompound.getBoolean("hasLabel");
//					tileEntity.setLabel(true);
//				}
//				else tileEntity.setLabel(false);
				
				
				
				storageJar.setInventorySlotContents(0, new ItemStack(id, count, damage));
				
				storageJar.getStorageStack().setItemDamage(seedDamage);
				
			}
			
			
			
			int labelDamage = SCBlockStorageJar.damageToData( itemStack.getItemDamage() )[0];
			
			if (labelDamage == 1)
			{
				storageJar.setLabel(true);
			}
			else storageJar.setLabel(false);
			
			//int dir = Direction.facingToDirection[iFacing];
			//int dir = SCBlockStorageJar.getDirection(iFacing);
			
			//world.setBlockMetadata(i, j, k, dir);
			
    	}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
	{			
		String contains = StatCollector.translateToLocal( "SCItemBlockStorageJar.contains" );
		String empty = StatCollector.translateToLocal( "SCItemBlockStorageJar.empty" );
		String hasLabel = StatCollector.translateToLocal( "SCItemBlockStorageJar.hasLabel" );
		
		if (stack.stackTagCompound != null)
		{

			int count = getCount(stack);
			String type = getName(stack);
			
			if (contains != null)
			{
				list.add(contains + " " + count + " " + type);
			}
			else list.add("Contains " + count + " " + type);
			
		}
		else
		{
			if (empty != null)
			{
				list.add(empty);
			}
			else list.add("Empty");
		}
		
		if (getLabel(stack) == 1)
		{
			list.add(hasLabel);
		}
		
		
		//DEBUG
		//list.add("Damage: " + stack.getItemDamage());
	}
	
	private int getLabel(ItemStack stack)
    {
    	int itemDamage = stack.getItemDamage();
    	
    	return SCBlockStorageJar.damageToData(itemDamage)[0];
	}
	
	private int getCount(ItemStack stack)
    {
    	if (stack.stackTagCompound != null  && stack.stackTagCompound.hasKey("Count"))
    	{
    		return stack.stackTagCompound.getInteger("Count");
    	}
		
    	else return 0;
	}
	
	private String getName(ItemStack stack)
	{
		if (stack.stackTagCompound != null)
    	{
			int seedType = 0;
			int seedDamage = 0;
			
			if (stack.stackTagCompound.hasKey("seedType"))
			{
				seedType = stack.stackTagCompound.getInteger("seedType");
			}

			if (stack.stackTagCompound.hasKey("seedDamage") )
			{
				seedDamage = stack.stackTagCompound.getInteger("seedDamage");
			}
			
			
			return new ItemStack(seedType, 1, seedDamage).getDisplayName();

    	}
		
		return "N/A";
	}

}
