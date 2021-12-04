package net.minecraft.src;

import java.util.List;

public class SCBlockPumpkinHarvested extends SCBlockGourdHarvested {

	protected SCBlockPumpkinHarvested(int iBlockID) {
		super(iBlockID);
		
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
        setUnlocalizedName("SCBlockPumpkinHarvested");
		
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return SCDefs.pumpkinSeeds;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k)
	{
		return 1;
	}
	
	@Override
	protected int AuxFXIDOnExplode()
	{
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourcePumpkin;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 16; i++) {
			par3List.add(new ItemStack(par1, 1, i));
			
		}
    }
		
	//----------- Render/Icon Functionality -----------//
	
	private Icon[] orangeIcon;
	private Icon[] orangeIconTop;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		//Orange
  		orangeIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
		{
  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinSide_" + iTempIndex );
		}
	
		orangeIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
		{
		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinTop_" + iTempIndex );
		}
	}
	
	@Override
	public Icon getIcon( int side, int meta )
	{
		int growthLevel = this.GetGrowthLevel(meta);
		int type = this.getType(meta);
		
		if (type == 0) 
		{
			if ( side == 1 || side == 0 )
	    	{
	    		return blockIcon = orangeIconTop[growthLevel];
	    	}
	    	
			else return blockIcon = orangeIcon[growthLevel];
		}
		
		return blockIcon;

	}
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	}
	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
		renderer.renderStandardBlock( this, i, j, k );

		return true;
	}

	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(iItemDamage) );
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
	}
	
	
	@Override
	protected void setBlockOnFinishedFalling(EntityFallingSand entity, int i, int j, int k) {
		//empty since we don't want to change it
	}

	
	

}
