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

	private Icon[] iconArray;

	public Icon getIcon(int par1, int meta) {
		int growthLevel = GetGrowthLevel(meta);

		if (growthLevel == 0)
			return this.iconArray[0];
		else if (growthLevel == 1)
			return this.iconArray[0];
		else if (growthLevel == 2)
			return this.iconArray[1];
		else if (growthLevel == 3)
			return this.iconArray[2];
		else if (growthLevel == 4)
			return this.iconArray[2];
		else if (growthLevel == 5)
			return this.iconArray[3];
		else if (growthLevel == 6)
			return this.iconArray[3];
		else if (growthLevel == 7)
			return this.iconArray[4];
		else
			return this.iconArray[3];
	}

	public void registerIcons(IconRegister par1IconRegister) {
		this.iconArray = new Icon[5];

		for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
			this.iconArray[var2] = par1IconRegister.registerIcon(this.getUnlocalizedName2() + "_" + var2);
		}
	}
	
	@Override
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) {
        renderer.setRenderBounds(GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, x, y, z));
        
        FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, x, y, z );
        
    	return renderer.renderCrossedSquares(this, x, y, z);
    }
}
