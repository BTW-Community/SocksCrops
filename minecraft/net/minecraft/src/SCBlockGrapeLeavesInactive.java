package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeLeavesInactive extends SCBlockGrapeLeaves {

	public SCBlockGrapeLeavesInactive(int iBlockID, int stemBlock, int vineBlock, int deadLeaves, String type) {
		super(iBlockID, 0, stemBlock, vineBlock, deadLeaves, type);
		// TODO Auto-generated constructor stub
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
		ValidateState(world, i, j, k);
    }

}
