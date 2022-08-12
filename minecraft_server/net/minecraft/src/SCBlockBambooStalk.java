package net.minecraft.src;

import java.util.Random;

public class SCBlockBambooStalk extends SCBlockBambooRoot {

	protected SCBlockBambooStalk(int par1) {
		super(par1);
		setUnlocalizedName("SCBlockBambooStalk");
		
		setBlockBounds(6/16F, 0.0F, 6/16F, 
					10/16F, 1.0F, 10/16F);
		
		setCreativeTab(null);
	}
	
	
	private float getGrowthChance()
	{
		return 0.2f;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		//Bamboo growth
		int maxGrowHeight = world.getBlockMetadata(x, y, z); //random height between 7 and 15
		
    	if ( world.provider.dimensionId == 0)
    	{
    		if ( world.getBlockMetadata(x, y, z) != 0)
    		{

	            if ( random.nextFloat() <= getGrowthChance() && world.isAirBlock( x, y + 1, z ) ) //Sugar can has a chance of 1 in 2 to grow
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
    		else
    		{
    			if (world.getBlockId(x, y + 1, z) == this.blockID)
    			{
    				world.setBlockMetadata(x, y + 1, z, 0);
    			}
    		}
    	}
	}


	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return SCDefs.bambooItem.itemID;
	}
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && ( blockOn.blockID == SCDefs.bambooRoot.blockID || blockOn.blockID == this.blockID ); //blockOn.CanReedsGrowOnBlock( world, i, j, k );
    }

	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
        
        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

}
