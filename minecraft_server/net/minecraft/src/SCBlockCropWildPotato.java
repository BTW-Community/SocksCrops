package net.minecraft.src;

import java.util.Random;

public class SCBlockCropWildPotato extends FCBlockCropsDailyGrowth {

	protected SCBlockCropWildPotato(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildPotato");
	}

	@Override
	protected int GetCropItemID() {
		return SCDefs.sweetPotato.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return 0;
	}
	
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) || world.getBlockId(i, j - 1, k) == Block.grass.blockID; 
    }

	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float fChance,
			int iFortuneModifier) {
		int growthLevel = GetGrowthLevel(meta);

		int iNumDropped = 0;

		if (growthLevel == 7) {
			iNumDropped = 1;

			if (world.rand.nextInt(4) - iFortuneModifier <= 0) {
				iNumDropped = 2;
			}
		}

		for (int iTempCount = 0; iTempCount < iNumDropped; ++iTempCount) {
			dropBlockAsItem_do(world, i, j, k, new ItemStack(GetCropItemID(), 1, 0));
		}
	}

	// ------------ Client Side Functionality ----------//


}
