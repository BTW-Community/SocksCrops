package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVineSleeping extends SCBlockPumpkinVine {

	protected SCBlockPumpkinVineSleeping(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockPumpkinVine");
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {	
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
        
		if ( iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 ) //daytime
	    {
			System.out.println("Vine: I'm sleeping");
	    }
		
        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 ) // night time
        {
        	
	        if ( this.canBlockStay( world, i, j, k ) )
	        {
	        	int meta = world.getBlockMetadata(i, j, k);
	        	
	        	int GrowthLevel = GetGrowthLevel(world, i, j, k);
	        	
	        	if (GrowthLevel < 3) {
		        	if (rand.nextFloat() <= 1.0)
		        	{
		        		System.out.println("Vine: I'm awake again");
		        		world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinVine.blockID, meta); //set vine to awake so it can grow in the morning
		        	}
		        }

	        }
        }
    }
}
