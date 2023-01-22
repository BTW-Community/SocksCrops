package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeVine extends SCBlockGrapeLeaves {

	private String type;
	private int leavesBlock;
	
	protected SCBlockGrapeVine(int iBlockID, int stemBlock, int leavesBlock, String type) {
		super(iBlockID, 0, stemBlock, 0, leavesBlock, type);
		this.type = type;
		this.leavesBlock = leavesBlock;
		
		this.setUnlocalizedName("SCBlockGrapeVine");
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
		ValidateState(world, i, j, k);
		
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID )
        {
            if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
            {
	            if ( random.nextFloat() <= GetBaseGrowthChance() )
	            {
	            	grow( world, i, j, k, world.getBlockMetadata(i, j, k));
	            }
            }
        }
    }
    
	protected void grow( World world, int i, int j, int k, int iMetadata )
    {	
		world.setBlockAndMetadataWithNotify(i, j, k, leavesBlock, iMetadata);
    }
	    
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        int var7 = (MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) % 4;
        par1World.setBlockMetadata(par2, par3, par4, var7, 2);
        System.out.println(var7);
    }
	
}
