package net.minecraft.src;

public abstract class SCBlockPlanterBase extends FCBlockPlanterBase {

	protected SCBlockPlanterBase(int iBlockID) {
		super(iBlockID);
		
		SetBlockMaterial(Material.ground);
		
		setHardness( 0.5F );
		
		SetPicksEffectiveOn( true );
		SetHoesEffectiveOn( true );
		setTickRandomly(true);
	}

}
