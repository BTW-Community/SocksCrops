package net.minecraft.src;

import java.util.List;

public class SCBlockPumpkinHarvested extends SCBlockGourd {

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
	protected int ItemCountToDropOnExplode()
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
	
	//TODO isn't working like i want it to
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
    {

		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		//Orange
		if (meta == 0) {
			this.InitBlockBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 0 = 6x6x6
		}else if (meta == 1){
			this.InitBlockBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 1 = 8x8x8
		}else if (meta == 2){
			this.InitBlockBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 12x12x12
		}else if (meta == 3){
			this.InitBlockBounds(GetPumpkinBounds(8/16D, 16/16D)); // stage 2 = 14x14x14
		}
		//Green
		else if (meta == 4){
			this.InitBlockBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 5){
			this.InitBlockBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 6){
			this.InitBlockBounds(GetPumpkinBounds(6/16D, 6/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 7){
			this.InitBlockBounds(GetPumpkinBounds(8/16D, 8/16D)); // stage 2 = 14x14x14
			
		}
		//Yellow
		else if (meta == 8){
			this.InitBlockBounds(GetPumpkinBounds(2/16D, 4/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 9){
			this.InitBlockBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 10){
			this.InitBlockBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 11){
			this.InitBlockBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 14x14x14
			
		}
		//White
		else if (meta == 12){
			this.InitBlockBounds(GetPumpkinBounds(2/16D, 3/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 13){
			this.InitBlockBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 14){
			this.InitBlockBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 15){
			this.InitBlockBounds(GetPumpkinBounds(5/16D, 6/16D)); // stage 2 = 14x14x14
		}
    }
	
	//----------- Client Side Functionality -----------//
		
	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		//Orange
		if (meta == 0) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 0 = 6x6x6
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 1){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 1 = 8x8x8
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 2){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 12x12x12
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 3){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 16/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}
		//Green
		else if (meta == 4){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 1 = 8x8x8
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 5){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 12x12x12
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 6){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 6/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 7){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}
		//Yellow
		else if (meta == 8){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 4/16D)); // stage 1 = 8x8x8
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 9){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 2 = 12x12x12
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 10){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 11){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}
		//White
		else if (meta == 12){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 3/16D)); // stage 1 = 8x8x8
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 13){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 2 = 12x12x12
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 14){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}else if (meta == 15){
			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D)); // stage 2 = 14x14x14
			renderer.renderStandardBlock( this, i, j, k );
		}

		return true;
	}
	

}
