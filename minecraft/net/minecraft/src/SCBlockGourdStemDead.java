package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdStemDead extends SCBlockGourdStem {

	private static final double m_dWidth = 0.25D;
	private static final double m_dHalfWidth = ( m_dWidth / 2D );
	
	protected SCBlockGourdStemDead(int iBlockID) {
		super(iBlockID, 0, 0, null);
		
    	setHardness( 0F );
    	
    	SetBuoyant();
    	
        InitBlockBounds( 0.5F - m_dHalfWidth, 0F, 0.5F - m_dHalfWidth, 
        	0.5F + m_dHalfWidth, 0.25F, 0.5F + m_dHalfWidth );
        
        setStepSound( soundGrassFootstep );
        
        setUnlocalizedName("SCBlockStemDead");
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		UpdateIfBlockStays( world, i, j, k );
	}
	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		return 0;
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		return 0xfb9a35; //hue to dead color
	}
	
	@Override
	protected boolean CanGrowOnBlock(World world, int i, int j, int k) {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.blockID == Block.grass.blockID || blockOn.blockID == Block.sand.blockID;
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {

		return CanGrowOnBlock(world, i, j, k);
	}
	
//----------- Client Side Functionality -----------//
    
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		return super.RenderBlock(renderer, i, j, k);
	}
	

}
