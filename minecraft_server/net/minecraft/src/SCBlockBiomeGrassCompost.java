package net.minecraft.src;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SCBlockBiomeGrassCompost extends SCBlockBiomeGrassBase {

	private static final int SELF_GROWTH_CHANCE = 12;

	protected SCBlockBiomeGrassCompost(int blockID) {
		super(blockID);
    	
    	setUnlocalizedName("SCBlockBiomeGrass");
    	setCreativeTab(null);
    	
    	//setTickRandomly(true);
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
		//System.out.println("Current Biome Grass Color: " + world.getBiomeGenForCoords(x, z).getBiomeGrassColor());
		//System.out.println("Current Biome Foliage Color: " + world.getBiomeGenForCoords(x, z).getBiomeFoliageColor());
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		super.updateTick(world, x, y, z, rand);
		
		int biomeType = world.getBlockMetadata(x, y, z) & 7;
		
		if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
			this.grow(world, x, y, z);
		}
		else if (!isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0)
		{
			world.setBlockAndMetadata(x, y, z, SCDefs.biomeGrassDirt.blockID, biomeType);
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
		if (isSparse(meta)) return 0;
		else return 1;
	}
}
