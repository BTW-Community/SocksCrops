package net.minecraft.src;

import java.util.Random;

public class SCBlockPastryRaw extends SCBlockPastryBase {
	
	public static final int burgerBun = 0;
	public static final int burgerBunIAligned = 1;
	
	public static String[] types = {"burger"};
	
	
	protected SCBlockPastryRaw(int par1) {
		super(par1, Material.clay);
				
		setUnlocalizedName("SCBlockPastryRaw");
	}
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{
		if (meta == burgerBun || meta == burgerBunIAligned)
		{
			return SCDefs.burgerDough.itemID;
		}
		
		return blockID;
	}
	
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
		if ( iFacing == 4 || iFacing == 5 )
		{
			if ( iMetadata == burgerBun )
			{
				iMetadata = burgerBunIAligned;
			}
		}
		
		return iMetadata;
    }
	
	@Override
    public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving placingEntity, ItemStack stack )
    {
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( placingEntity );

		if ( iFacing == 4 || iFacing == 5 )
		{
			int iMetadata = world.getBlockMetadata( i, j, k );
			
			if ( iMetadata == burgerBun )
			{
				world.setBlockMetadataWithNotify( i, j, k, burgerBunIAligned );
			}
		}			
    }
	
	@Override
	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
	{
		if ( iMetadata == burgerBun )
		{
			iMetadata = burgerBunIAligned;
		}

		return iMetadata;			
	}
	
	private float center = 0.5F;
	protected float centerX = 0.5F;
	protected float centerZ = 0.5F;
	
	protected float width = 6/16F;
	protected float length = 14/16F;
	protected float height = 3/16F;
	
	protected float singleWidth = 6/16F;
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		
		int meta = blockAccess.getBlockMetadata( x, y, z );
		switch (meta) {
		default:
		case burgerBun:
			return AxisAlignedBB.getAABBPool().getAABB(         	
	        		center - length/2, 0, center - width/2,
	        		center + length/2, 4/16F, center + width/2 );
			
		case burgerBunIAligned:
			return AxisAlignedBB.getAABBPool().getAABB(         	
	        		center - width/2, 0, center - length/2,
	        		center + width/2, 4/16F, center + length/2 );
		}
		

	}
	
	
	// --- CLIENT --- //
	
	private Icon[] iconTop = new Icon[types.length];
	private Icon[] iconSide = new Icon[types.length];
	
	private String[] topTextures = {"SCBlockPastryRaw_burgerBun_top"};	
	private String[] sideTextures = {"fcBlockPastryUncooked"};	
	@Override
	public void registerIcons(IconRegister register) {
		
		for (int i = 0; i < types.length; i++)
		{
			iconTop[i] = register.registerIcon(topTextures[i]);
			iconSide[i] = register.registerIcon(sideTextures[i]);
		}
		
		blockIcon = register.registerIcon("fcBlockPastryUncooked");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		if (side == 1)
		{
			if (meta == burgerBun || meta == burgerBunIAligned)
			{
				return iconTop[0];
			}
		}
		else
		{
			if (meta == burgerBun || meta == burgerBunIAligned)
			{
				return iconSide[0];
			}
		}
		
		return blockIcon;		
	}		

	protected void renderBun(RenderBlocks renderer, int x, int y, int z, float centerX, float centerZ, float width, float minY, float maxY) {
		renderer.setRenderBounds(
				centerX - width/2, minY, centerZ - width/2,
				centerX + width/2, maxY, centerZ + width/2
				);
		renderer.renderStandardBlock(this, x, y, z);
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
		
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
		
		for (int i = 0; i < 2; i++)
		{
			switch (i) {
			case 0:
				centerX = 12/16F;
				centerZ = 8/16F;
				break;
				
			case 1:
				centerX = 4/16F;
				centerZ = 8/16F;
				break;
			}
			
			if (meta == burgerBun)
			{
				renderBun(renderer, x, y, z, centerX, centerZ, singleWidth, 0, height);
			}
			else renderBun(renderer, x, y, z, centerZ, centerX, singleWidth, 0, height);
		}
		
		return true;
	}
    
    @Override
    public void RenderBlockSecondPass( RenderBlocks renderer, int x, int y, int z, boolean firstPassResult )
    {
        RenderCookingByKilnOverlay( renderer, x, y, z, firstPassResult );
    }
}
