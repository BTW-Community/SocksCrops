package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesLogCincers extends FCBlockWoodCinders {

	public SCBlockFruitTreesLogCincers(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName( "fcBlockWoodCinders" );
	}

    @Override
    public void updateTick( World world, int i, int j, int k, Random rand ) 
    {
    	// prevent falling behavior for stumps
    	
    	if ( !GetIsStump( world, i, j, k ) )
    	{
    		super.updateTick( world, i, j, k, rand );
    	}
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
    
    
    //------------- Class Specific Methods ------------//
    
    public boolean GetIsStump( IBlockAccess blockAccess, int i, int j, int k )
    {
		int iMetadata = blockAccess.getBlockMetadata( i, j, k );
		
		return GetIsStump( iMetadata );
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
    public boolean isOpaqueCube() {
    	// TODO Auto-generated method stub
    	return false;
    }
    
    @Override
    public boolean renderAsNormalBlock() {
    	// TODO Auto-generated method stub
    	return false;
    }
}
