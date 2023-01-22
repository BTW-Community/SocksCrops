package net.minecraft.src;

import java.util.Random;

public class SCBlockCookieRaw extends Block{

	public static String[] cookieTypes = {"sweetberry", "blueberry", "chocolate"};
	
	public static final int sweetberry = 0;
	public static final int sweetberryIAligned = 1;
	
	public static final int blueberry = 2;
	public static final int blueberryIAligned = 3;
	
	public static final int chocolate = 4;
	public static final int chocolateIAligned = 5;
	
	public static final float m_fUnfiredPotteryUncookedCookiesHeight = ( 1F / 16F );
	public static final float m_fUnfiredPotteryUncookedCookiesWidth = ( 6F / 16F );
	public static final float m_fUnfiredPotteryUncookedCookiesHalfWidth = ( m_fUnfiredPotteryUncookedCookiesWidth / 2F );
	public static final float m_fUnfiredPotteryUncookedCookiesLength = ( 14F / 16F );
	public static final float m_fUnfiredPotteryUncookedCookiesHalfLength = ( m_fUnfiredPotteryUncookedCookiesLength / 2F );
	
	public static final float m_fUnfiredPotteryUncookedCookiesIndividualWidth = ( 2F / 16F );
	public static final float m_fUnfiredPotteryUncookedCookiesIndividualHalfWidth = ( m_fUnfiredPotteryUncookedCookiesIndividualWidth / 2F );
	
	
	protected SCBlockCookieRaw(int blockID) {
		super(blockID, Material.clay);
		setStepSound( FCBetterThanWolves.fcStepSoundSquish );
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
            dropBlockAsItem( world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            
            world.setBlockToAir( i, j, k );
    	}
    }
	
	@Override
	public int idDropped(int meta, Random rand, int fortuneModifier)
	{
		if (meta == sweetberry || meta == sweetberryIAligned)
		{
			return SCDefs.cookieSweetberryRaw.itemID;
		}
		else if (meta == blueberry || meta == blueberryIAligned)
		{
			return SCDefs.cookieBlueberryRaw.itemID;
		}
		else if (meta == chocolate || meta == chocolateIAligned)
		{
			return FCBetterThanWolves.fcItemPastryUncookedCookies.itemID;
		}
		
		return blockID;
	}
	
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
		if ( iFacing == 4 || iFacing == 5 )
		{
			if ( iMetadata == sweetberry )
			{
				iMetadata = sweetberryIAligned;
			}
			else if ( iMetadata == blueberry )
			{
				iMetadata = blueberryIAligned;
			}
			else if ( iMetadata == chocolate )
			{
				iMetadata = chocolateIAligned;
			}
		}
		
		return iMetadata;
    }
	
	
	@Override
    public void onBlockPlacedBy( World world, int i, int j, int k, EntityLiving placingEntity, ItemStack stack )
    {
		int iFacing = FCUtilsMisc.ConvertOrientationToFlatBlockFacingReversed( placingEntity );

		if ( iFacing == 4 || iFacing == 5 )
		{
			int iMetadata = world.getBlockMetadata( i, j, k );
			
			if ( iMetadata == sweetberry )
			{
				world.setBlockMetadataWithNotify( i, j, k, sweetberryIAligned );
			}
			else if ( iMetadata == blueberry )
			{
				world.setBlockMetadataWithNotify( i, j, k, blueberryIAligned );
			}
			else if ( iMetadata == chocolate )
			{
				world.setBlockMetadataWithNotify( i, j, k, chocolateIAligned );
			}
		}			
    }
	
	@Override
    public void onBlockAdded( World world, int i, int j, int k )
    {
		// check beneath for valid block due to piston pushing not sending a notify
		if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
		{
	        dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
	        world.setBlockWithNotify(i, j, k, 0);
		}
    }

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {

		if ( !canBlockStay(world,x,y,z))
		{
			return false;
		}
		else return super.canPlaceBlockAt(world, x, y, z);
	}

	//Copied from FCBlockUnfiredPottery
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iBlockID )
    {
    	if ( !FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, i, j - 1, k, 1, true ) )
    	{
    		// Unfired pottery can both be pushed by pistons and needs to rest on a block, which can create weird
    		// interactions if the block below is pushed at the same time as this one, 
    		// creating a ghost block on the client. Delay the popping of the block to next tick prevent this.

    		if( !world.IsUpdatePendingThisTickForBlock( i, j, k, blockID ) )
    		{
    			world.scheduleBlockUpdate( i, j, k, blockID, 1 );
    		}
    	}
    }
    
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        return FCUtilsWorld.DoesBlockHaveLargeCenterHardpointToFacing( world, x, y - 1, z, 1, true );
    }
	
	@Override
    public boolean CanBePistonShoveled( World world, int i, int j, int k )
    {
    	return true;
    }
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int RotateMetadataAroundJAxis( int iMetadata, boolean bReverse )
	{
		if ( iMetadata == sweetberry )
		{
			iMetadata = sweetberryIAligned;
		}
		else if ( iMetadata == blueberry )
		{
			iMetadata = blueberryIAligned;
		}
		else if ( iMetadata == chocolate )
		{
			iMetadata = chocolateIAligned;
		}
		return iMetadata;			
	}
	
	@Override
	public boolean CanRotateOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
	{
		return true;
	}
		
	// --- CLIENT --- //

	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		
		int meta = blockAccess.getBlockMetadata( x, y, z );
		switch (meta) {
		case sweetberry:
		case blueberry:
		case chocolate:
			
        	return AxisAlignedBB.getAABBPool().getAABB(         	
        		( 0.5F - m_fUnfiredPotteryUncookedCookiesHalfWidth ), 0.0F, 
        		( 0.5F - m_fUnfiredPotteryUncookedCookiesHalfLength ), 
        		( 0.5F + m_fUnfiredPotteryUncookedCookiesHalfWidth ), 
        		m_fUnfiredPotteryUncookedCookiesHeight, 
        		( 0.5F + m_fUnfiredPotteryUncookedCookiesHalfLength ) );
			
		case sweetberryIAligned:
		case blueberryIAligned:
		case chocolateIAligned:
			
        	return AxisAlignedBB.getAABBPool().getAABB(         	
        		( 0.5F - m_fUnfiredPotteryUncookedCookiesHalfLength ), 0.0F, 
        		( 0.5F - m_fUnfiredPotteryUncookedCookiesHalfWidth ), 
        		( 0.5F + m_fUnfiredPotteryUncookedCookiesHalfLength ), 
        		m_fUnfiredPotteryUncookedCookiesHeight, 
        		( 0.5F + m_fUnfiredPotteryUncookedCookiesHalfWidth ) );
		}
		
		return AxisAlignedBB.getAABBPool().getAABB( 0D, 0D, 0D, 1D, 1D, 1D );
	}
}
