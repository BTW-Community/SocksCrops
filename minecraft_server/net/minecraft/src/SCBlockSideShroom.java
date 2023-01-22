package net.minecraft.src;

public class SCBlockSideShroom extends SCBlockSideShroomBase {

	protected SCBlockSideShroom(int par1) {
		super(par1);
		SetFurnaceBurnTime( FCEnumFurnaceBurnTime.KINDLING );
		setUnlocalizedName("SCBlockSideShroom");
		setCreativeTab(CreativeTabs.tabDecorations);
		setStepSound(soundGrassFootstep);

	}


}
