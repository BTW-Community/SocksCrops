package net.minecraft.src;

public class SCBlockCropWildCarrotHighYield extends SCBlockCropWildCarrot {

	public SCBlockCropWildCarrotHighYield(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildCarrotHighYield");
	}
	
	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildCarrotCropSapling.blockID;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, int i, int j, int k, int meta, float fChance,
			int iFortuneModifier) {
		int growthLevel = GetGrowthLevel(meta);

		int iNumDropped = 0;

		if (growthLevel >= 6) {
			iNumDropped = 1;
			
			int random = world.rand.nextInt(4);
			
			if (random <= 1) iNumDropped = 2;
			else if (random == 2) iNumDropped = 3;
		}

		for (int iTempCount = 0; iTempCount < iNumDropped; ++iTempCount) {
			dropBlockAsItem_do(world, i, j, k, new ItemStack(GetCropItemID(), 1, 0));
		}
		
		if ( growthLevel == 7 )
    	{
    		DropSeeds( world, i, j, k, 0 );
    	}
	}

}
