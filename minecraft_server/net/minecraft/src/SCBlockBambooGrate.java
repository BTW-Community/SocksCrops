package net.minecraft.src;

public class SCBlockBambooGrate extends FCBlockPane {

	protected SCBlockBambooGrate(int iBlockID) {
		super(iBlockID, "SCBlockBambooGrate", "SCBlockBambooGrate", Material.wood, false);
        setHardness( 0.5F );        
        SetAxesEffectiveOn();
		
        SetBuoyant();
        
		SetFireProperties( FCEnumFlammability.PLANKS );
		
        setLightOpacity( 2 );
        Block.useNeighborBrightness[iBlockID] = true;
        
        setStepSound( soundWoodFootstep );        
        
        setUnlocalizedName( "SCBlockBambooGrate" );
	}
	
	@Override
    public boolean DoesBlockBreakSaw( World world, int i, int j, int k )
    {
		return false;
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, 
		int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, Item.stick.itemID, 
			2, 0, fChanceOfDrop );
		
		DropItemsIndividualy( world, i, j, k, SCDefs.bambooItem.itemID, 
			2, 0, fChanceOfDrop );
		
		return true;
	}

}
