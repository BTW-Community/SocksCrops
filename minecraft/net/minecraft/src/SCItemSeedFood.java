package net.minecraft.src;

public class SCItemSeedFood extends FCItemSeedFood {

	private int metaPlaced = 0;
	
	public SCItemSeedFood(int iItemID, int iHealAmount, float fSaturationModifier, int iCropBlockID, int metaPlaced, String name) {
		super(iItemID, iHealAmount, fSaturationModifier, iCropBlockID);
		setUnlocalizedName(name);
		SetBuoyant();        
        SetBellowsBlowDistance( 2 );        
		SetFilterableProperties( m_iFilterable_Fine );
		this.metaPlaced = metaPlaced;
	}
	
    @Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        if ( iFacing == 1 )
        {
	        if ( player == null || ( player.canPlayerEdit( i, j, k, iFacing, itemStack ) && 
	        	player.canPlayerEdit( i, j + 1, k, iFacing, itemStack ) ) )
	        {
	            Block cropBlock = Block.blocksList[m_iCropBlockID]; 
	
	            if ( cropBlock != null && cropBlock.canPlaceBlockAt( world, i, j + 1, k ) )
	            {
	                world.setBlockAndMetadataWithNotify( i, j + 1, k, m_iCropBlockID, metaPlaced );
	                
	                world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, 
	                	Block.soundGrassFootstep.getPlaceSound(), 
	                	( Block.soundGrassFootstep.getPlaceVolume() + 1F ) / 2F, 
	                	Block.soundGrassFootstep.getPlacePitch() * 0.8F );
	                
	                --itemStack.stackSize;
	                
	                return true;
	            }
	        }
        }
        
        return false;
    }
    
    @Override
	public boolean OnItemUsedByBlockDispenser( ItemStack stack, World world, 
		int i, int j, int k, int iFacing )
	{
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k, iFacing );
        Block cropBlock = Block.blocksList[m_iCropBlockID]; 
    	
        if ( cropBlock != null && cropBlock.canPlaceBlockAt( world, 
        	targetPos.i, targetPos.j, targetPos.k ) )
        {
            world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, m_iCropBlockID, metaPlaced );
            
            world.playSoundEffect( targetPos.i + 0.5D, targetPos.j + 0.5D, targetPos.k + 0.5D, 
            	Block.soundGrassFootstep.getPlaceSound(), 
            	( Block.soundGrassFootstep.getPlaceVolume() + 1F ) / 2F, 
            	Block.soundGrassFootstep.getPlacePitch() * 0.8F );
            
            return true;
        }
        
        return false;
	}

}
