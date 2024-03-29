package net.minecraft.src;

public class SCBlockMoss extends Block {

	protected SCBlockMoss(int blockID)
	{
		super(blockID, Material.sand);
		SetFireProperties( FCEnumFlammability.LEAVES );
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockMoss");
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	@Override
	public boolean CanBePistonShoveled(World world, int i, int j, int k) {
		return true;
	}
	
	@Override
	public boolean CanBlockBePushedByPiston(World world, int i, int j, int k, int iToFacing) {
		return true;
	}
	
}
