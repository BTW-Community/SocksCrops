package net.minecraft.src;

import java.util.Random;

public class SCBlockCakeRaw extends SCBlockPastryBase {

	public static String[] cakeTypes = {"chocolate", "carrot"};
	
	public static final int chocolate = 0;
	public static final int carrot = 1;
	
	static final float height = 8/16F;
	static final float width = 14/16F;
	static final float halfWidth = width/2;
	
	
	protected SCBlockCakeRaw(int blockID) {
		super(blockID, Material.clay);
	}

	@Override
	public int idPicked(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		if (meta == chocolate)
		{
			return SCDefs.chocolateCakeItemRaw.itemID;
		}
		else if (meta == carrot)
		{
			return SCDefs.carrotCakeItemRaw.itemID;
		}
		
		return 0;
	}
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{
		if (meta == chocolate)
		{
			return SCDefs.chocolateCakeItemRaw.itemID;
		}
		else if (meta == carrot)
		{
			return SCDefs.carrotCakeItemRaw.itemID;
		}
		
		return blockID;
	}

	// --- CLIENT --- //
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		return new AxisAlignedBB(
				0.5F - halfWidth, 0, 0.5F - halfWidth,
				0.5F + halfWidth, height, 0.5F + halfWidth);
	}
	
	private Icon[] rawCake = new Icon[cakeTypes.length];
	
	@Override
	public void registerIcons(IconRegister register) {
		for (int i=0; i < cakeTypes.length; i++)
		{
			rawCake[i] = register.registerIcon("SCBlockCakeRaw_" + cakeTypes[i]);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return rawCake[meta];
	}	

}
