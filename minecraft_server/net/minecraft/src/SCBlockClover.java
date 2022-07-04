package net.minecraft.src;

import java.util.List;

public class SCBlockClover extends BlockFlower {

	protected SCBlockClover(int par1) {
		super(par1);
		setStepSound(soundGrassFootstep);
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("SCBlockClover");
	}
	
    public void getSubBlocks(int id, CreativeTabs creativeTabs, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
    }
	

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}