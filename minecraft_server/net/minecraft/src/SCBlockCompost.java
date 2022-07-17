package net.minecraft.src;

import java.util.Random;

public class SCBlockCompost extends FCBlockFalling {

	protected SCBlockCompost(int par1) {
        super(par1, Material.ground);
        
        this.setHardness( 0.5F );
        
        this.SetShovelsEffectiveOn();
        
        this.SetBuoyant();
        
        this.setStepSound( soundGravelFootstep );
        
        this.setUnlocalizedName("SCBlockCompost");
        
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
	
	@Override
	public boolean CanWildVegetationGrowOnBlock(World world, int i, int j, int k)
	{
		return true;
	}
	
	@Override
	public boolean CanReedsGrowOnBlock(World world, int i, int j, int k)
	{	
		return true;
	}

	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock)
	{
		return 2F;
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		// revert back to soil
		world.setBlockAndMetadataWithNotify( i, j, k, 
			FCBetterThanWolves.fcBlockDirtLoose.blockID, 0 );
	}

}