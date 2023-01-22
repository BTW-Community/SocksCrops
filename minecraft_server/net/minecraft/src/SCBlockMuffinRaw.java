package net.minecraft.src;

public class SCBlockMuffinRaw extends SCBlockPastryBase {
	
	public static final int CHOCOLATE = 0;
	public static final int SWEETBERRY = 1;
	public static final int BLUEBERRY = 2;
	
	public static String[] types = {"chocolate", "sweetberry", "blueberry"};
	
	
	protected SCBlockMuffinRaw(int par1) {
		super(par1, Material.clay);
				
		setUnlocalizedName("SCBlockMuffin");
	}
	
	protected int getType(int meta) {
		if (meta == CHOCOLATE) return 0;
		else if (meta == SWEETBERRY) return 1;
		else return 2; //BLUEBERRY
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(
				1/16F, 0, 1/16F,
				15/16F, height*2, 15/16F);
	}
	
	// --- CLIENT --- //
	protected boolean paperPass = false;
	
	protected float centerX;
	protected float centerZ;
	
	protected float width = 4/16F;
	protected float height = 3/16F;
	
	protected float widthTop = 5/16F;

}
