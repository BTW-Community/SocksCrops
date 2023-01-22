// FCMOD: FCBlockCocoa

package net.minecraft.src;

import java.util.Random;

public class SCBlockCoconutPlant extends SCBlockCoconutFalling
{
	protected SCBlockCoconutPlant(int blockID) {
		super(blockID);
		
		this.setUnlocalizedName("SCBlockCoconutPlant");
	}
	
	private float getGrowthChance() {
		return 1/16F;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		
		super.updateTick(world, x, y, z, rand);
		
		int meta = world.getBlockMetadata(x, y, z);
		
		if (rand.nextFloat() <= getGrowthChance() && getGrowthStage(meta) < 3)
		{
			world.setBlockMetadataWithNotify(x, y, z, meta + 4);
		}
	}
	
	
	@Override
	protected Item ItemToDropOnExplode()
	{
		return SCDefs.coconut;
	}
	
    @Override
    public int idDropped( int meta, Random rand, int fortuneModifier )
    {
    	int growthStage = getGrowthStage(meta);
    	if (growthStage < 3)
    	{
    		return 0;
    	}
    	else return SCDefs.coconut.itemID;
    }
	
	@Override
	protected int ItemCountToDropOnExplode(World world, int i, int j, int k, int meta)
	{
		if (getGrowthStage(meta) == 3)
		{
			return 1;
		}
		else return 0;
	}
	
	
	@Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
	{
		return SCCustomAuxFX.coconutExplodeAuxFXID;
	}
		
	protected DamageSource GetFallDamageSource()
	{
		return SCDamageSourceCustom.damageSourceCoconut;
	}
	
    /**
     * Called when the block is placed in the world.
     */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
    {
        int var7 = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        par1World.setBlockMetadata(par2, par3, par4, var7, 2);
    }
    
	
	private AxisAlignedBB getCoconutBounds(int saplingGrowthStage) {
		int coconutWidth;
		
		switch (saplingGrowthStage) {

		case 0:
			
			coconutWidth = 2;
			break;
			
		case 1:
			
			coconutWidth = 3;
			break;
			
		case 2:
			
			coconutWidth = 4;
			break;
			
		case 3:
			
			coconutWidth = 5;
			break;

		default:
			coconutWidth = 5;
			break;
		}
		
		AxisAlignedBB bambooBounds = AxisAlignedBB.getAABBPool().getAABB( 
    			0.5F - coconutWidth/16D, 0.5F - coconutWidth/16D, 0.5F - coconutWidth/16D, 
    			0.5F + coconutWidth/16D, 0.5F + coconutWidth/16D, 0.5F + coconutWidth/16D );
		
		return bambooBounds;
	}
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{	
		return getCoconutBounds(getGrowthStage(meta));
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		int meta = blockAccess.getBlockMetadata(i, j, k);
		return getCoconutBounds(getGrowthStage(meta));
	}

}