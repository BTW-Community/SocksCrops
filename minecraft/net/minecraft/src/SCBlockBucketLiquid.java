package net.minecraft.src;

import java.util.Random;

public class SCBlockBucketLiquid extends FCBlockBucketMilk {
	
	private int type;
	private String name;
	
	public SCBlockBucketLiquid(int iBlockID, String name, int type) {
		super(iBlockID);
		setUnlocalizedName( name );
		
        this.type = type;
        this.name = name;
	}
	
	@Override
    public int idDropped( int iMetadata, Random rand, int iFortuneMod )
    {
		return SCDefs.bucketWithLiquid.itemID;
    }
	
	@Override
	public int damageDropped(int par1) {
		return type;
	}
	
	//------------- Class Specific Methods ------------//
	
	@Override
    public boolean AttemptToSpillIntoBlock( World world, int i, int j, int k )
    {
        if ( ( world.isAirBlock( i, j, k ) || !world.getBlockMaterial( i, j, k ).isSolid() ) )
        {     
    		world.setBlockWithNotify( i, j, k, SCDefs.liquidBlocks[type].blockID );
            
            return true;
        }
        
        return false;
    }
	
	//----------- Client Side Functionality -----------//
	
    private Icon m_iconContents;
    
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons( register );
		
		m_iconContents = register.registerIcon( "SCBlockLiquid_" + SCUtilsLiquids.namesList.get(type) );
    }
	
	@Override
	protected Icon GetContentsIcon()
	{
		return m_iconContents;
	}
}
