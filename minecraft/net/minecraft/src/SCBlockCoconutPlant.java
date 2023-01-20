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
    public int idPicked(World par1World, int par2, int par3, int par4)
    {

    	return SCDefs.coconut.itemID;
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
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
    }
    

    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
    		int iSide) {
    	return true;
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
	
	private Icon[] coconutSide = new Icon[4];
	private Icon[] coconutTop = new Icon[4];
	private Icon[] coconutBottom = new Icon[4];
	private Icon[] coconutConnector = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {
		for (int i = 0; i < 4; i++) {
			coconutSide[i] = register.registerIcon("SCBlockCoconutSide_" + i);
			coconutTop[i] = register.registerIcon("SCBlockCoconutTop_" + i);
			coconutBottom[i] = register.registerIcon("SCBlockCoconutBottom_" + i);
			coconutConnector[i] = register.registerIcon("SCBlockCoconutConnector_" + i);
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		int growthStage = getGrowthStage(meta);
		
		if (side == 0)  blockIcon = coconutBottom[growthStage];
		else if (side == 1)  blockIcon = coconutTop[growthStage];
		else blockIcon = coconutSide[growthStage];
		
		return blockIcon;
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		renderer.setRenderBounds( getCoconutBounds( getGrowthStage( meta ) ) );
		renderer.renderStandardBlock(this, i, j, k);
		
		if ( isConnectedToTree(renderer.blockAccess, i, j, k) )
		{
			SCUtilsRender.renderCoconutConnector(renderer, this, i, j, k, coconutConnector[getGrowthStage( meta )]);
		}	

		return true;
	}
	
	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( getCoconutBounds( getGrowthStage( meta ) ) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( getCoconutBounds( getGrowthStage( iItemDamage ) ) );
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage);
	}
}