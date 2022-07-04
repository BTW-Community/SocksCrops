package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockFishTrap extends BlockContainer {

	protected SCBlockFishTrap(int blockID) {
		super(blockID, Material.wood);
        SetBuoyant();
		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("SCBlockFishTrap");
	}
	
	@Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
		if ( !world.isRemote )
		{
			ItemStack heldStack = player.getCurrentEquippedItem();
			SCTileEntityFishTrap fishTrap = (SCTileEntityFishTrap)world.getBlockTileEntity( i, j, k );
			
			if (heldStack != null && !fishTrap.isBaited() && !fishTrap.hasFish() && fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.IsFishingBait(heldStack))
			{				
				fishTrap.setBaited(true);
				world.setBlockMetadata(i, j, k, 1);
				fishTrap.markBlockForUpdate();
				heldStack.stackSize--;
				return true;
			}
			else if(fishTrap.hasFish() && ( heldStack == null || heldStack.itemID ==fishTrap.getFishStack().itemID ) )
			{
				fishTrap.setHasFish(false);
				
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, fishTrap.getFishStack(), iFacing);
				fishTrap.setFishStack(null);
				fishTrap.markBlockForUpdate();
				return true;
			}
			else if (heldStack != null && !fishTrap.isBaited() && !fishTrap.hasFish() && !fishTrap.isBodyOfWaterLargeEnoughForFishing() && fishTrap.IsFishingBait(heldStack))
			{
				player.addChatMessage("There doesn't seem to be enough Water around the Fish Trap");
				return true;
			}
		}
		return false;
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SCTileEntityFishTrap();
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
}
