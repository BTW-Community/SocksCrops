package net.minecraft.src;

import java.util.List;

public class SCBlockClover extends BlockFlower {

	protected SCBlockClover(int par1) {
		super(par1);
		setStepSound(soundGrassFootstep);
		SetFireProperties( FCEnumFlammability.GRASS );
		setCreativeTab(CreativeTabs.tabDecorations);
		setUnlocalizedName("SCBlockClover");
	}
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
	}
	
    public void getSubBlocks(int id, CreativeTabs creativeTabs, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
    }
	
	private Icon cloverTop;
	private Icon[] cloverStem = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {
		cloverTop = register.registerIcon("SCBlockClover_top");
		cloverStem[0] = register.registerIcon("SCBlockClover_stem");
		cloverStem[1] = register.registerIcon("SCBlockClover_flowerPurple");
		cloverStem[2] = register.registerIcon("SCBlockClover_flowerWhite");
		cloverStem[3] = register.registerIcon("SCBlockClover_flowerRed");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return blockIcon = cloverStem[meta];
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		SCUtilsRender.renderBlockCloverWithTexture(renderer, this, i, j, k, cloverStem[meta]);
		
		renderer.setOverrideBlockTexture(cloverStem[meta]);
		renderer.renderCrossedSquares(this, i, j, k);
		renderer.clearOverrideBlockTexture();
		
		renderer.setRenderBounds(0, 3/16D, 0, 1, 3/16D, 1);		
		renderer.setOverrideBlockTexture(cloverTop);
		renderer.renderStandardBlock(this, i, j, k);
		renderer.clearOverrideBlockTexture();
		

		return true;
	}
}