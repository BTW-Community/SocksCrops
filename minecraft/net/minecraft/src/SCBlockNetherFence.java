package net.minecraft.src;

import java.util.List;

public class SCBlockNetherFence extends SCBlockFence {

	public static final String[] iconNames = new String[] {
			"netherBrick" };
	
	
	public SCBlockNetherFence(int iBlockID, Material material) {
		super(iBlockID, material);
        SetAxesEffectiveOn(false);        
        SetNonBuoyant();
        
		SetFireProperties( FCEnumFlammability.NONE );
		
		setHardness(2F);
		setResistance(10F);
		SetPicksEffectiveOn();
		setStepSound(soundStoneFootstep);
		setUnlocalizedName("SCBlockNetherFence");
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		par3List.add(new ItemStack(par1, 1, 0) );
    }
	
	private Icon nether;
	
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons(register);
		
		nether = register.registerIcon( "netherBrick" );

    }
	
	@Override
	public Icon getIcon( int side, int meta )
	{
		return blockIcon = nether;
	}
	
    @Override
    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
    {
    	renderBlocks.setOverrideBlockTexture(nether);
		m_model.RenderAsItemBlock( renderBlocks, this, iItemDamage );
		renderBlocks.clearOverrideBlockTexture();
    }
	
	

}
