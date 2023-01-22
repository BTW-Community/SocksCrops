package net.minecraft.src;

import java.util.List;

public class SCBlockGroundFlowers extends BlockFlower {

	public static String[] flowerTypes = {"daisyWhite", "daisyBlue", "daisyPurple", "daisyPink" };
	
	protected SCBlockGroundFlowers(int blockID) {
		super(blockID);
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("SCBlockGroundFlowers");
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(
				0,0,0,
				1,1/16F,1);
	}

}
