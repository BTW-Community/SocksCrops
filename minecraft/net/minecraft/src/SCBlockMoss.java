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

	@Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = register.registerIcon( "grass_top" );
    }  


}
