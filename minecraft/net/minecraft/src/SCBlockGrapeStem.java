package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeStem extends BlockFlower {

	public static double stemWidth = 0.125;
	public static double leavesWidth = 0.25;
	Icon leavesIcon;
	
	protected SCBlockGrapeStem(int blockID) {
		super(blockID, Material.wood);
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
        checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy
        
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
            if ( world.getBlockLightValue( i, j + 1, k ) >= 9 && random.nextInt( 1 ) == 0 ) //was 64 for saplings
            {
                int iMetadata = world.getBlockMetadata( i, j, k );
                int iGrowthStage = ( iMetadata & (~3) ) >> 2;

                if ( iGrowthStage < 3 )
                {
                	iGrowthStage++;
                	iMetadata = ( iMetadata & 3 ) | ( iGrowthStage << 2 );
                	
                	System.out.println("stemgrow: "+iGrowthStage);
                	
                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );
                }
                else if (iGrowthStage == 3) {
                	if (world.getBlockId(i, j + 1, k) == SCDefs.fenceRope.blockID) {
                		this.growLeaves( world, i, j, k, random );
                	}
                	if (world.getBlockId(i, j + 1, k) == 0 && world.getBlockId(i, j + 2, k) == SCDefs.fenceRope.blockID ) {
                		this.growStem( world, i, j, k, random );
                	}
                }
            }
        }
    }
	
	public void growLeaves( World world, int i, int j, int k, Random random )
    {	
		int meta = world.getBlockMetadata(i, j + 1, k);
		
		world.setBlockAndMetadataWithNotify(i, j +1, k, SCDefs.grapeLeaves.blockID, meta);
		System.out.println("grow leaves");
    }
	
	private void growStem(World world, int i, int j, int k, Random random) {
		world.setBlockWithNotify(i, j + 1, k, SCDefs.grapeStem.blockID);
		System.out.println("I'm a second stem!");
		
	}
	
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    private AxisAlignedBB GetStemBounds(double size)
	{

		AxisAlignedBB stemBox = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - stemWidth-size, 0.0D, 0.5D - stemWidth-size, 
    		0.5D + stemWidth+size, 0.75D+(size*4), 0.5F + stemWidth+size );
		
		return stemBox;
	}
    
    private AxisAlignedBB GetStemLeavesBounds(double size)
	{

		AxisAlignedBB leavesBox = AxisAlignedBB.getAABBPool().getAABB( 
			0.5D - leavesWidth-size, 0.5D-size, 0.5D - leavesWidth -size, 
    		0.5D + leavesWidth+size, 0.5D + leavesWidth*2 +size, 0.5F + leavesWidth+size );
		
		return leavesBox;
	}
	
    @Override
	public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
	{
		
		
		IBlockAccess blockAccess = renderer.blockAccess;
		int iMetadata = blockAccess.getBlockMetadata( i, j, k );
        int iGrowthStage = ( iMetadata & (~3) ) >> 2;
                	
        if (iGrowthStage == 3) {
        	if (blockAccess.getBlockId(i, j+1, k) == SCDefs.fenceRope.blockID || blockAccess.getBlockId(i, j+1, k) ==  this.blockID) {
        		renderer.setRenderBounds(GetStemBounds(1/16D));
        		renderer.renderStandardBlock( this, i, j, k);
        		
        	}else {
        		renderer.setRenderBounds(GetStemBounds(1/16D));
        		renderer.renderStandardBlock( this, i, j, k);
        	}
        	

        } else {
        	renderer.setRenderBounds(GetStemBounds(0));
    		renderer.renderStandardBlock( this, i, j, k);
    		
    		renderer.setRenderBounds(GetStemLeavesBounds(0));
    		FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, this.leavesIcon );
        }
                	
		return true;
	}
	
	/**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int par1, int par2)
    {
    	return this.blockIcon;
    }
    
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister register)
    {
    	this.blockIcon = register.registerIcon("SCBlockGrapeWood");
    	this.leavesIcon = register.registerIcon("SCBlockGrapeLeaves");
    }
    
    
}
