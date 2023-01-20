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
        par1World.setBlockMetadataWithNotify(par2, par3, par4, var7, 2);
        System.out.println(var7);
    }
	
	private Icon vine;
	
	@Override
	public void registerIcons(IconRegister register)
	{
		blockIcon = vine = register.registerIcon("SCBlockGrapeVine_" + type);
	}
	
	@Override
	public Icon getIcon(int par1, int par2)
	{
		return vine;
	}
	
	@Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult)
    {
		IBlockAccess blockAccess = renderer.blockAccess;
		
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		if (GetExtendsAlongAxis(blockAccess, i, j, k, 1))
		{
			SCUtilsRender.renderCrossedSquaresWithTexture(renderer, this, i, j, k, vine, false);
		}
		
		if (meta == 4)
		{
			SCUtilsRender.renderNorthCrossedSquares(renderer, this, i, j, k);
			
		}
		else if (meta == 1)
		{
			SCUtilsRender.renderWestCrossedSquares(renderer, this, i, j, k);
		}
		else if (meta == 5)
		{
			SCUtilsRender.renderWestCrossedSquares(renderer, this, i, j, k);
			SCUtilsRender.renderNorthCrossedSquares(renderer, this, i, j, k);
		}
		
    	for ( int iAxis = 0; iAxis < 3; iAxis++ )
    	{
    		if ( GetExtendsAlongAxis( blockAccess, i, j, k, iAxis ) )
    		{
    			SCBlockFenceRope.SetRenderBoundsForAxis( renderer, iAxis );
    			
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
    		}
    	}
		
    }
	
	
    @Override
    public void randomDisplayTick( World world, int i, int j, int k, Random rand )
    {
    	
    }




}
