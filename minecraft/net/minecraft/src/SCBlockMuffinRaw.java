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
	
	
	private Icon[] uncooked = new Icon[types.length];
	private Icon paperSide;
	private Icon paperBottom;
	
	private Icon pastry;
	
	@Override
	public void registerIcons(IconRegister register) {
		
		for (int i = 0; i < types.length; i++)
		{
			uncooked[i] = register.registerIcon("SCBlockPastryRaw_" + types[i]);
		}
		
		paperSide = register.registerIcon("SCBlockMuffin_side");
		paperBottom = register.registerIcon("SCBlockMuffin_bottom");
		
		//blockIcon = uncooked[0] = pastry = register.registerIcon( "fcBlockPastryUncooked" );
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if (paperPass)
		{
			if (side < 2)
			{
				return paperBottom;
				
			}
			else return paperSide;
		}
		
		return uncooked[getType(meta)];
		
	}		

	protected void renderMuffin(RenderBlocks renderer, int x, int y, int z, 
			float centerX, float centerZ, float width, float minY, float maxY) {
		renderer.setRenderBounds(
				centerX - width/2, minY, centerZ - width/2,
				centerX + width/2, maxY, centerZ + width/2
				);
		renderer.renderStandardBlock(this, x, y, z);
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
		
		for (int i = 0; i < 4; i++)
		{
			switch (i) {
			case 0:
				centerX = 4/16F;
				centerZ = 4/16F;
				break;
				
			case 1:
				centerX = 4/16F;
				centerZ = 12/16F;
				break;
				
			case 2:
				centerX = 12/16F;
				centerZ = 4/16F;
				break;
				
			case 3:
				centerX = 12/16F;
				centerZ = 12/16F;
				break;

			}
			
			//Bottom
			paperPass = true;
			renderMuffin(renderer, x, y, z, centerX, centerZ, width, 0, height);
			paperPass = false;
			
			renderMuffin(renderer, x, y, z, centerX, centerZ, widthTop, height, height*2);

		}
		
		return true;
	}
}
