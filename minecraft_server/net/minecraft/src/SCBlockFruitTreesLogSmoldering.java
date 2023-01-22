package net.minecraft.src;

public class SCBlockFruitTreesLogSmoldering extends FCBlockLogSmouldering {

	protected SCBlockFruitTreesLogSmoldering(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName( "fcBlockLogSmouldering" );
	}
	protected boolean IsSupportedBySolidBlocks( World world, int i, int j, int k )
	{
		Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];

		return blockBelow != null && ( blockBelow.HasLargeCenterHardPointToFacing( world, i, j - 1, k, 1, false ) || blockBelow instanceof SCBlockFruitTreesLog );
	}
	
	public boolean GetIsStump( int iMetadata )
	{
		return ( iMetadata & 8 ) != 0;
	}

	public static int setIsStump( int iMetadata, boolean bStump )
	{
		if ( bStump )
		{
			iMetadata |= 8;
		}
		else
		{
			iMetadata &= ~8;
		}

		return iMetadata;
	}
	
	@Override
	public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
	{
		if ( GetIsStump( world.getBlockMetadata( i, j, k ) ) )
		{
			world.setBlockWithNotify( i, j, k, SCDefs.fruitStumpCharred.blockID );

			return true;
		}   

		return false;
	}
	
	private void ConvertToCinders( World world, int i, int j, int k )
	{
		if ( GetIsStump( world, i, j, k ) )
		{
			int iNewMetadata = SCBlockFruitTreesLogCincers.setIsStump( 0, true );

			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.fruitLogCinders.blockID, iNewMetadata );						
		}
		else
		{
			world.setBlockWithNotify( i, j, k, SCDefs.fruitLogCinders.blockID );
		}
	}

	//----------- Client Side Functionality -----------//

    @Override
    public boolean isOpaqueCube() {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	// TODO Auto-generated method stub
    	return false;
    }

	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		float minx = 0;
		float miny = 0;
		float minz = 0;
		float maxx = 1;
		float maxy = 1;
		float maxz = 1;
		
		int meta = blockAccess.getBlockMetadata(i, j, k) ;
		
		minx = 4/16F;
		maxx = 12/16F;
		minz = 4/16F;
		maxz = 12/16F;
		
		AxisAlignedBB box = new AxisAlignedBB(minx,miny,minz,maxx,maxy,maxz);
		
		return box;
	}
	

}
