package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SCBlockBiomeGrassDirt extends SCBlockBiomeGrassBase {

	private static final int SELF_GROWTH_CHANCE = 12;

	protected SCBlockBiomeGrassDirt(int blockID) {
		super(blockID);
    	
    	setUnlocalizedName("SCBlockBiomeGrass");
    	
    	//setTickRandomly(true);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		
		if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
			this.grow(world, x, y, z);
		}
	}
	
	public void grow(World world, int x, int y, int z) {
		int meta = world.getBlockMetadata(x, y, z);
		
		world.setBlockAndMetadata(x, y, z , this.blockID, meta + 8);
	}
	
	@Override
	public boolean isSparse(int metadata) {
		return metadata < 8;
	}
	
	public int getTypeForIcons(int meta)
	{
		if (isSparse(meta)) return 2;
		else return 3;
	}
}
