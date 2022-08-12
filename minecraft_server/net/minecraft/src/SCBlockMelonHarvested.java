package net.minecraft.src;

import java.util.List;

public class SCBlockMelonHarvested extends SCBlockGourdHarvested {

	protected SCBlockMelonHarvested(int iBlockID)
	{
		super(iBlockID);

        setUnlocalizedName("SCBlockMelonHarvested");		
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
		
		if (blockAccess.getBlockMetadata(i, j, k) == 3) //mature watermelon
		{
			return true;
		}
		else return super.IsNormalCube(blockAccess, i, j, k);
	}
	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return FCBetterThanWolves.fcItemMelonMashed;
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
		if (this.getType(meta) == 1)
		{
			 return SCCustomAuxFX.melonHoneydewExplodeAuxFXID;
		}
		else if (this.getType(meta) == 2)
		{
			 return SCCustomAuxFX.melonCantaloupeExplodeAuxFXID;
		}
		else return SCCustomAuxFX.melonExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}
	
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
		for (int i = 0; i < 12; i++) {
			par3List.add(new ItemStack(par1, 1, i));
			
		}
    }

	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{	
				
		//init BB
		AxisAlignedBB pumpkinBounds =  GetGourdBounds(16, 16, 16);
		int type = this.getType(meta);
		int growthLevel = this.GetGrowthLevel(meta);
	
		if (meta == 0)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (meta == 2)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else if (meta == 3)
		{
			return GetGourdBounds(16, 16, 16);
		}
		
		else if (meta == 4 || meta == 8 )
		{
			return GetGourdBounds(4, 4, 4);
		}
		else if (meta == 5 || meta == 9)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (meta == 6 || meta == 10)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (meta == 7 || meta == 11)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else return GetGourdBounds(16, 16, 16);
	
	}

}
