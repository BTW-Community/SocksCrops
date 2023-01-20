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
	public void getSubBlocks(int blockID, CreativeTabs tab, List list) {
		for (int i = 0; i < flowerTypes.length; i++)
		{
			list.add(new ItemStack(blockID, 1, i));
		}
		
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int side) {
		return side < 2;
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
	
	private Icon[] daisies = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {
		for (int i = 0; i < flowerTypes.length; i++)
		{
			daisies[i] = register.registerIcon("SCBlockGroundFlowers_" + flowerTypes[i]);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return daisies[meta];
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        tess.setColorOpaque_F(1F, 1F, 1F);
        
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        
        renderer.setRenderBounds(0,0,0,1,1/128F,1);
        renderer.renderFaceYPos(this, x, y, z, getIcon(0, meta));
        renderer.renderFaceYNeg(this, x, y, z, getIcon(0, meta));
        
		return true;
	}

}
