package net.minecraft.src;

import java.util.Random;

public class SCBlockBambooStalk extends SCBlockBambooRoot {

	protected SCBlockBambooStalk(int par1) {
		super(par1);
		setUnlocalizedName("SCBlockBambooStalk");
		
		setBlockBounds(6/16F, 0.0F, 6/16F, 
					10/16F, 1.0F, 10/16F);
		this.setHardness(0.5F);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		//Bamboo growth
		int maxGrowHeight = world.getBlockMetadata(x, y, z); //random height between 7 and 15
		
    	if ( world.provider.dimensionId == 0 )
    	{
            if ( random.nextInt(2) == 0 && world.isAirBlock( x, y + 1, z ) ) //Sugar can has a chance of 1 in 2 to grow
            {
                int iReedHeight = 1;
                
                //System.out.println("Bamboo Stalk["+x+","+y+","+z+"]: "+"my current maxGrowHeight is: " + maxGrowHeight);

                while ( world.getBlockId( x, y - iReedHeight, z ) == blockID )
                {
                	iReedHeight++;
                	//System.out.println("Bamboo Stalk["+x+","+y+","+z+"]: "+"my current reedHeight is: " + iReedHeight);
                }

                if ( iReedHeight < maxGrowHeight )
                {
                    int iMetadata = world.getBlockMetadata( x, y, z );
                 
					
					//System.out.println("Bamboo["+x+","+y+","+z+"]: "+"Growing Bamboo above.");
					world.setBlockAndMetadataWithNotify( x, y + 1, z, blockID , maxGrowHeight); 

                }

            }
    	}
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return SCDefs.bambooItem.itemID;
	}
	
	@Override
	public boolean canBlockStay(World world, int i, int j, int k) {
		
		
		if (world.getBlockId(i, j - 1, k) == this.blockID || world.getBlockId(i, j - 1, k) == SCDefs.bambooRoot.blockID)
		{
			super.canBlockStay(world, i, j, k);
			return true;
		}
		else return false;
		
	}
	
	protected Icon m_IconTop;
    protected Icon m_IconRoots;
	private Icon bigLeaves;
	private Icon sideLeaves;


    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockBambooStalk" );
		bigLeaves = register.registerIcon( "SCBlockBambooLeavesTop" );
		sideLeaves = register.registerIcon( "SCBlockBambooLeaves" );
		m_IconTop = register.registerIcon( "SCBlockBambooRoot_top" );
    }
	
    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
        if ( iSide == 1 )
        {
            return m_IconTop;
        }
		return blockIcon;
    }
	 
    @Override
    public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
    	//super.RenderBlock(renderer, i, j, k); //Leaves cube
    	
    	IBlockAccess blockAccess = renderer.blockAccess;
    	int meta = blockAccess.getBlockMetadata( i, j, k );
    	
    	//SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, m_IconRoots);
    	
    	int blockAbove = blockAccess.getBlockId(i, j+1, k);
    	int blockBelow = blockAccess.getBlockId(i, j-1, k);
    	
    	if (blockAbove == 0 && blockBelow == this.blockID) {
    		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, bigLeaves);
 
    	}

    	if (blockAbove == 0 && blockBelow == SCDefs.bambooRoot.blockID) {
    		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, sideLeaves);
    	}
    	
    	if (blockAbove == this.blockID && blockBelow == this.blockID && blockAccess.getBlockId(i, j+2, k) != this.blockID) 
    	{
    		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, sideLeaves);
    	}

    	renderer.setRenderBounds(getAppleRenderBounds(2)); //4px x 4px
    	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, i, j, k, this.blockIcon);

    	
    	return true;
    }

}
