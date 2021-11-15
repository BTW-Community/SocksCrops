package net.minecraft.src;

import java.util.Random;

public class SCBlockBambooRoot extends BlockFlower {

	protected SCBlockBambooRoot(int par1) {
		super(par1, Material.plants);
		setTickRandomly(true);
		setBlockBounds(6/16F, 0.0F, 6/16F, 
				10/16F, 1.0F, 10/16F);
		setUnlocalizedName("SCBlockBambooRoot");
		this.setHardness(0.5F);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		int maxGrowHeight = world.getBlockMetadata(x, y, z);
		
    	//Bamboo growth
    	if ( world.provider.dimensionId == 0 )
    	{
            if ( random.nextFloat() <= 0.9 && world.isAirBlock( x, y + 1, z ) && maxGrowHeight > 0  )
            {
            	//System.out.println("Bamboo Root["+x+","+y+","+z+"]: "+"Growing Bamboo Stalk above.");
				world.setBlockAndMetadataWithNotify( x, y + 1, z, SCDefs.bambooStalk.blockID , maxGrowHeight); 
            }
    	}
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
	    			world.setBlockMetadataWithNotify(i, j, k, 0);
	    			
	    			player.playSound("mob.sheep.shear", 1.0F, 1.0F);
	    			
	    			FCUtilsItem.EjectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCDefs.bambooShoot), iFacing);
	    			
	    			return true;
	    		}
	    	}
		}
		    	
    	return false;
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


	
	protected static AxisAlignedBB getAppleRenderBounds(int halfSize)
	{
    	Random random = new Random();
    	float center = 0.5F;
    	float randomOffset = random.nextFloat() *2 - 1; //random float from -1.0 to 1.0
    	
    	float newCenter = center;// + randomOffset;
    	
		AxisAlignedBB appleBox = AxisAlignedBB.getAABBPool().getAABB( 
			newCenter - halfSize /16D, 0/16D, newCenter - halfSize /16D, 
			newCenter + halfSize /16D, 16/16D, newCenter + halfSize /16D );
		
		return appleBox;
	}
	
//----------- Client Side Functionality -----------//
    
    protected Icon m_IconTop;
    protected Icon m_IconRoots;
    protected Icon smallLeaves;
    private Icon[] m_iconArray;
    private Icon[] m_iconTopArray;

    @Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockBambooStalk" );
		m_IconRoots = register.registerIcon( "SCBlockBambooRoot" );
		smallLeaves = register.registerIcon( "SCBlockBambooLeavesSmall" );
		m_IconTop = register.registerIcon( "SCBlockBambooRoot_top" );

    }
	
    
    @Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
        if ( iSide == 1 )
        {
            return m_IconTop;
        }
        else if ( iSide == 0 )
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
    	
    	if (meta != 0)
    	{
    		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, m_IconRoots);
    	}    	
    	
    	int blockAbove = blockAccess.getBlockId(i, j+1, k);
    	
    	if (blockAbove != SCDefs.bambooStalk.blockID) {
    		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, smallLeaves);
    	}

    	renderer.setRenderBounds(getAppleRenderBounds(2)); //4px x 4px
    	renderer.renderStandardBlock(this, i, j, k);

    	
    	return true;
    }
    
    public boolean isOpaqueCube()
    {
        return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	return false;
    }
    
    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
    		int iSide) {
    	// TODO Auto-generated method stub
    	return true;
    }

}
