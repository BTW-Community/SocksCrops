package net.minecraft.src;

import java.util.Random;

public class SCBlockBambooRoot extends BlockFlower {

	protected SCBlockBambooRoot(int par1) {
		super(par1, Material.wood);
		setTickRandomly(true);
		setUnlocalizedName("SCBlockBambooRoot");
		setHardness(4.5F);
		SetAxesEffectiveOn();
		setStepSound(soundWoodFootstep);
	}
	
    @Override
    public int GetHarvestToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	
    	return 3;
    }
    
    @Override
    public int GetEfficientToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return 1;
    }
	
    private float getGrowthChance()
    {
    	return 0.2f;
	}
    
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		int maxGrowHeight = world.getBlockMetadata(x, y, z);
		
    	//Bamboo growth
    	if ( world.provider.dimensionId == 0 )
    	{
            if ( random.nextFloat() <= getGrowthChance() && world.isAirBlock( x, y + 1, z ) && maxGrowHeight > 0  )
            {
            	//System.out.println("Bamboo Root["+x+","+y+","+z+"]: "+"Growing Bamboo Stalk above.");
				world.setBlockAndMetadataWithNotify( x, y + 1, z, SCDefs.bambooStalk.blockID , maxGrowHeight); 
            }
    	}
	}
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanReedsGrowOnBlock( world, i, j, k );
    }

	
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
		
		ItemStack stack = player.getCurrentEquippedItem();
    	
		if (world.getBlockMetadata(i, j, k) > 0 && !world.isRemote)
		{
			if ( stack != null )
	    	{
	    		Item item = stack.getItem();
	    		
	    		if ( item instanceof FCItemShears)
				{
	    			world.setBlockMetadataWithNotify(i, j, k, 0); //sets this block to inactive
	    			
	    			if (world.getBlockId(i, j + 1, k) == SCDefs.bambooStalk.blockID)
	    			{
	    				world.setBlockMetadata(i, j + 1, k, 0);
	    			}	    					
	    			
	    			player.playSound("mob.sheep.shear", 1.0F, 1.0F);
	    			
	    			FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCDefs.bambooShoot), iFacing);
	    			
	    			return true;
	    		}
	    	}
		}
		    	
    	return false;
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return SCDefs.bambooItem.itemID;
	}
		
		//spreading code from mushroom
    	//commented out to make it possible to keep spread under control
//        if (random.nextFloat() <= 0.99) //1 in 25
//        {
//        	//System.out.println("Bamboo["+x+","+y+","+z+"]: "+"Spreading Bamboo Shoots");
//        	
//            byte spreadingRadius = 2; //was 4 (mushroom)
//            int familySize = 3;	//was 5 (mushroom)
//            int var8;
//            int var9;
//            int var10;
//            
//
//            for (var8 = x - spreadingRadius; var8 <= y + spreadingRadius; ++var8)
//            {
//                for (var9 = z - spreadingRadius; var9 <= z + spreadingRadius; ++var9)
//                {
//                    for (var10 = y - 1; var10 <= y + 1; ++var10)
//                    {
//                    	int blockID = world.getBlockId(var8, var10, var9);
//                        if ( blockID == this.blockID || blockID == SCDefs.bambooShoot.blockID) //include shoots for familySize check
//                        {
//                            --familySize;
//
//                            if (familySize <= 0)
//                            {
//                                return;
//                            }
//                        }
//                    }
//                }
//            }
//
//            var8 = x + random.nextInt(2) - 1; //-1 to 1
//            var9 = y + random.nextInt(2) - random.nextInt(2); //-2 to 4
//            var10 = z + random.nextInt(2) - 1; //-1 to 1
//
//            for (int var11 = 0; var11 < familySize; ++var11)
//            {
//                if (world.isAirBlock(var8, var9, var10) && canBlockStay(world, var8, var9, var10))
//                {
//                    x = var8;
//                    y = var9;
//                    z = var10;
//                }
//
//                var8 = x + random.nextInt(2) - 1;
//                var9 = y + random.nextInt(2) - random.nextInt(2);
//                var10 = z + random.nextInt(2) - 1;
//            }
//
//            if (world.isAirBlock(var8, var9, var10) && canBlockStay(world, var8, var9, var10))
//            {
//            	world.setBlock(var8, var9, var10, SCDefs.bambooShoot.blockID);
//            	//System.out.println("spreading");
//            }
//      }


	public static double getOffset(int i, int j, int k, int xOrZ) {
		//copied from RenderBlocks for TallGrass
		//adapted with help from Zhil and ExpHP
		double newI = (double)i;
		double newJ = (double)j;
		double newK = (double)k;

		//long var17 = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
		long hash = (long)(i * 3129871) ^ (long)k * 116129781L;
		hash = hash * hash * 42317861L + hash * 11L;
		newI += ((double)((float)(hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
		newJ += ((double)((float)(hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
		newK += ((double)((float)(hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;	

		double centerX = 8/16D;
		double centerZ = 8/16D;
		
		double newCenterX = newI - i + centerX;
		double newCenterZ = newK - k + centerZ;
		
		double c = 0.0D;
		
    	newCenterX = ( Math.floor( newCenterX * 16 + c ) - c ) / 16;
    	newCenterZ = ( Math.floor( newCenterZ * 16 + c ) - c ) / 16;
		
		if (xOrZ == 0)
		{
			return newCenterX;
		}
		else return newCenterZ;

	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
	{
		return getBambooRenderBounds(2, i, j, k);
	}

	protected static AxisAlignedBB getBambooRenderBounds(int width, int i, int j, int k)
	{    	
    	double newCenterX = getOffset(i, j, k, 0);
    	double newCenterZ = getOffset(i, j, k, 1);
    	
    	AxisAlignedBB bambooBounds = AxisAlignedBB.getAABBPool().getAABB( 
    			newCenterX - width /16D, 0/16D, newCenterZ - width /16D, 
    			newCenterX + width /16D, 16/16D, newCenterZ + width /16D );
		
		return bambooBounds;
	}
	
//----------- Client Side Functionality -----------//
    
	
    @Override
    public int getRenderType()
    {
        return 1;
    }

    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
}
