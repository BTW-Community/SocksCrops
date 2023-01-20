package net.minecraft.src;

public class SCItemFruitSeedling extends FCItemSeeds {

	private int cropMetadata;
	
	public SCItemFruitSeedling(int iItemID, int iCropBlockID, int cropMetadata) {
		super(iItemID, iCropBlockID);
		this.cropMetadata = cropMetadata;
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
	                world.setBlockAndMetadataWithNotify( i, j + 1, k, m_iCropBlockID, cropMetadata);
	                
	                world.playSoundEffect( i + 0.5D, j + 0.5D, k + 0.5D, 
	                	Block.soundGrassFootstep.getPlaceSound(), 
	                	( Block.soundGrassFootstep.getPlaceVolume() + 1F ) / 2F, 
	                	Block.soundGrassFootstep.getPlacePitch() * 0.8F );
	                
	                itemStack.stackSize--;
	                
	                return true;
	            }
	        }
        }
        
        return false;
    }
	
}
