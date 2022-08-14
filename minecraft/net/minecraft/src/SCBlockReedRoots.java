//SCADDON: based on FCBlockReedRoots
package net.minecraft.src;

import java.util.Random;

public class SCBlockReedRoots extends FCBlockReedBase {
	
	public SCBlockReedRoots(int id) {
		super(id);
		this.setHardness(0.0F);
		this.SetBuoyant();
		this.setStepSound(soundGrassFootstep);
		this.disableStats();
		
		this.setTickRandomly(true);

		this.setUnlocalizedName("fcBlockReedRoots");
	}
	
	@Override
    public void updateTick(World world, int x, int y, int z, Random rand)
	{
    	// prevent growth in the end dimension
    	if (world.provider.dimensionId != 1 && this.canBlockStay(world, x, y, z)) {
            if (world.isAirBlock(x, y + 1, z)) {
                int reedHeight = 1;

                while (Block.blocksList[world.getBlockId(x, y - reedHeight, z)] instanceof FCBlockReedBase) {
                	reedHeight++;
                }

                if (reedHeight < 3) {
                    int metadata = world.getBlockMetadata(x, y, z);

                    if (metadata == 15) {
                        world.setBlock(x, y + 1, z, FCBetterThanWolves.fcBlockReeds.blockID);
                        
                        world.SetBlockMetadataWithNotify(x, y, z, 0, 3); //changed from 4
                    }
                    else {
                        world.SetBlockMetadataWithNotify(x, y, z, metadata + 1, 3);
                    }
                }
            }
    	}
    }

	@Override
	public int idDropped(int par1, Random rand, int par3)
	{
        return FCBetterThanWolves.fcItemReedRoots.itemID;
    }
	
	@Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
        int blockBelowID = world.getBlockId(x, y - 1, z);
        Block blockBelow = Block.blocksList[blockBelowID];

    	return blockBelow != null && 
    		!(blockBelow instanceof FCBlockReedBase) &&
    		!(blockBelow instanceof FCBlockReedLegacy) &&
    		super.canPlaceBlockAt(world, x, y, z);
    }
	
	private static Icon[] roots = new Icon [4];
	private static Icon[] rootsFlower = new Icon [4];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for (int i = 0; i < roots.length; i++) {
			roots[i] = register.registerIcon("SCBlockReedsRoot_" + i);			
		}
		
		for (int i = 0; i < roots.length; i++) {
			rootsFlower[i] = register.registerIcon("SCBlockReedsRootFlower_" + i);			
		}
		
		blockIcon = roots[3];
	}
	
	//used for rendering in waterpot
	public static Icon getIconForWaterPot(int side, int meta)
	{		
		if (meta < 2) return rootsFlower[0];
		if (meta < 4) return rootsFlower[1];
		if (meta < 6) return rootsFlower[2];
		else return rootsFlower[3];
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side)
	{		
		boolean reedsAbove = Block.blocksList[blockAccess.getBlockId(x, y + 1, z)] instanceof FCBlockReedBase;
		int meta = blockAccess.getBlockMetadata(x, y, z);
		
		if (!reedsAbove)
		{
			if (meta < 4) return roots[0];
			if (meta < 8) return roots[1];
			if (meta < 12) return roots[2];
			else return roots[3];
		}
		else return roots[3];
	}
	
}