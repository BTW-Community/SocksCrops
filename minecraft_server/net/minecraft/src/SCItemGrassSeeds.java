package net.minecraft.src;

public class SCItemGrassSeeds extends Item {

	protected SCItemGrassSeeds(int itemID) {
		super(itemID);
		
	}
	
	@Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int iFacing, float fClickX, float fClickY, float fClickZ )    
    {
		int blockID = world.getBlockId(x, y, z);
		BiomeGenBase currentBiome = world.getBiomeGenForCoords(x, z);
		

		
		int biomeType = getBiomeType(currentBiome);
		
		if (blockID == SCDefs.compostBlock.blockID && biomeType != -1)
		{
			world.setBlockAndMetadata(x, y, z, SCDefs.biomeGrassCompost.blockID, biomeType);
			
			itemStack.stackSize--;
			
			if (!world.isRemote)
			{
				world.playAuxSFX( FCBetterThanWolves.m_iBlockPlaceAuxFXID, x, y, z, Block.tallGrass.blockID );              
			}
			
			return true;
		}
		
		return false;
    }

	private int getBiomeType(BiomeGenBase currentBiome) {
		boolean isPlains = currentBiome == BiomeGenBase.plains;
		boolean isDesert = currentBiome == BiomeGenBase.desert || currentBiome == BiomeGenBase.desertHills;
		boolean isXHills = currentBiome == BiomeGenBase.extremeHills || currentBiome == BiomeGenBase.extremeHillsEdge;
		boolean isForest = currentBiome == BiomeGenBase.forest || currentBiome == BiomeGenBase.forestHills;
		boolean isTaiga = currentBiome == BiomeGenBase.taiga || currentBiome == BiomeGenBase.taigaHills;
		boolean isSwamp = currentBiome == BiomeGenBase.swampland;
		boolean isJungle = currentBiome == BiomeGenBase.jungle || currentBiome == BiomeGenBase.jungleHills;
		boolean isMushroom = currentBiome == BiomeGenBase.mushroomIsland || currentBiome == BiomeGenBase.mushroomIslandShore;
		
		if (isPlains) return 0;
		else if (isDesert) return 1;
		else if (isXHills) return 2;
		else if (isForest) return 3;
		else if (isTaiga) return 4;
		else if (isSwamp) return 5;
		else if (isJungle) return 6;
		else if (isMushroom) return 7;
		else return -1;
	}

}
