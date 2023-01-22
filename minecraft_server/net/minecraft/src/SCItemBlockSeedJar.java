package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCItemBlockSeedJar extends ItemBlock {
        
	protected int m_iPlacedBlockID;

	public SCItemBlockSeedJar(int par1, Block placedBlock) {
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
		
		SCTileEntitySeedJar seedJar = (SCTileEntitySeedJar)( world.getBlockTileEntity(i, j, k) );
		
		if ( seedJar != null )
    	{			
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
				
				seedJar.getStorageStack().setItemDamage(seedDamage);
				
			}
			
			
			
			int labelDamage = SCBlockSeedJar.damageToData( itemStack.getItemDamage() )[0];
			
			if (labelDamage == 1)
			{
				seedJar.setLabel(true);
			}
			else seedJar.setLabel(false);
			
			//int dir = Direction.facingToDirection[iFacing];
			//int dir = SCBlockStorageJar.getDirection(iFacing);
			
			//world.setBlockMetadata(i, j, k, dir);
			
    	}
	}

	
	
	private int getLabel(ItemStack stack)
    {
    	int itemDamage = stack.getItemDamage();
    	
    	return SCBlockSeedJar.damageToData(itemDamage)[0];
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
