package net.minecraft.src;

import java.util.List;

public class SCBlockPumpkinHarvested extends SCBlockGourdHarvested {

	protected SCBlockPumpkinHarvested(int iBlockID)
	{
		super(iBlockID);

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
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
		
		if (blockAccess.getBlockMetadata(i, j, k) == 3) //mature pumpkin
		{
			return true;
		}
		else return super.IsNormalCube(blockAccess, i, j, k);
	}
	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return Item.pumpkinSeeds;
	}
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 1;
		}
		else return 0;
	}
	
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
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
	
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return this.GetBlockBoundsFromPoolBasedOnState(meta);
	}
	

}
