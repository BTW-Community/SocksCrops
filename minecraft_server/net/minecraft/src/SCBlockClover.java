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
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }
    
    @Override
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -1F;        
    }
	
	@Override
	public int damageDropped(int meta)
	{
		return meta;
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