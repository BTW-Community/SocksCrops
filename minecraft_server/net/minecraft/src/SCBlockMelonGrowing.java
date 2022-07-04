package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockMelonGrowing extends SCBlockGourdGrowing {

	protected SCBlockMelonGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	@Override
	protected boolean canBePossessed()
	{
		return false;
	}
	
	@Override
	public int idDropped( int iMetadata, Random random, int iFortuneModifier )
	{		
		return SCDefs.melonHarvested.blockID;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int growthLevel = GetGrowthLevel(meta);
		
		return getMetaHarvested(growthLevel);
	}
	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return FCBetterThanWolves.fcItemMelonMashed;
	}

	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		if (growthLevel == 3)
		{
			return 1;
		}
		else return 0;
	}

    @Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
    {
    	return FCBetterThanWolves.m_iMelonExplodeAuxFXID;
    }
    
	protected DamageSource GetFallDamageSource()
	{
		return FCDamageSourceCustom.m_DamageSourceMelon;
	}

}
