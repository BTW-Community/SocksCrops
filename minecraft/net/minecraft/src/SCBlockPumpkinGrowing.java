package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockPumpkinGrowing extends SCBlockGourdGrowing {

	protected SCBlockPumpkinGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
	}
	
	protected float GetBaseGrowthChance()
    {
    	return 0.5F;
    }


	@Override
	protected boolean canBePossessed() {
		return true;
	}

	@Override
	protected Item ItemToDropOnExplode()
	{
		return SCDefs.pumpkinSeeds;
	}

	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k )
	{
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		if (growthLevel == 3)
		{
			return 1;
		}
		else return 0;
	}

	@Override
	protected int AuxFXIDOnExplode() {
		return FCBetterThanWolves.m_iPumpkinExplodeAuxFXID;
	}

	@Override
	protected DamageSource GetFallDamageSource() {
		return FCDamageSourceCustom.m_DamageSourcePumpkin;
	}

}
