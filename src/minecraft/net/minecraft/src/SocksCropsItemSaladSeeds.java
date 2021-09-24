package net.minecraft.src;

public class SocksCropsItemSaladSeeds extends FCItemSeeds
{
    public SocksCropsItemSaladSeeds( int iItemID )
    {
    	super( iItemID, SocksCropsDefs.saladCrop.blockID );
    	
    	SetAsBasicChickenFood();
    	
    	setUnlocalizedName( "SocksCropsItemSaladSeeds" );
	}

}
