package net.minecraft.src;

public class SCBlockMossCarpet extends FCBlockGroundCover {

	protected SCBlockMossCarpet(int blockID)
	{
		super(blockID, Material.plants);
		SetFireProperties( FCEnumFlammability.LEAVES );
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockMossCarpet");
		
	}

    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	int iBlockBelowID = world.getBlockId( i, j - 1, k );
    	Block blockBelow = Block.blocksList[iBlockBelowID];
    	
    	if ( blockBelow != null )
    	{
    		return blockBelow.CanGroundCoverRestOnBlock( world, i, j - 1, k ) || blockBelow.HasLargeCenterHardPointToFacing(world, i, j -1, k, 1);
    	}
    	
    	return false;
    }
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
//        int var5 = 0;
//        int var6 = 0;
//        int var7 = 0;
//
//        for (int var8 = -1; var8 <= 1; ++var8)
//        {
//            for (int var9 = -1; var9 <= 1; ++var9)
//            {
//                int var10 = blockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor();
//                var5 += (var10 & 16711680) >> 16;
//                var6 += (var10 & 65280) >> 8;
//                var7 += var10 & 255;
//            }
//        }
//
//        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
		
    	double var1 = 1.0D;
    	double var3 = 0.5D;
    	//return 16777215;
    	return ColorizerGrass.getGrassColor(var1, var3);
	}
	
	@Override
	public int getRenderColor(int par1)
	{
    	double var1 = 1.0D;
    	double var3 = 0.5D;
    	return ColorizerGrass.getGrassColor(var1, var3);
	}
	
	private Icon mossSide;
	
	public static boolean secondPass;
	@Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = register.registerIcon( "grass_top" );
        mossSide = register.registerIcon("grass_side_overlay");
    }  
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborI, int neighborJ, int neighborK, int side)
	{
		if (side == 0) return false;
		
		return true;
	}
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean firstPassResult)
	{
		secondPass = true;
		
		float visualOffset = 0F;
		
    	int iBlockBelowID = renderBlocks.blockAccess.getBlockId( i, j - 1, k );
    	Block blockBelow = Block.blocksList[iBlockBelowID];
		
		if (blockBelow != null) {
			visualOffset = blockBelow.GroundCoverRestingOnVisualOffset(renderBlocks.blockAccess, i, j - 1, k);

			if (visualOffset < 0.0F)
			{
				j -= 1;

				visualOffset += 1F;
			}

		}
		
		renderBlocks.setOverrideBlockTexture(mossSide);
		renderBlocks.setRenderBounds(
				-0.0005,  0  + visualOffset, -0.0005,
				1.0005, 1 + visualOffset, 1.0005
				);
		renderBlocks.renderStandardBlock(this, i, j - 1, k);
		
		renderBlocks.clearOverrideBlockTexture();
		
		secondPass = false;
	}

}
