package net.minecraft.src;

import betterterrain.biome.BTABiomeConfiguration;

public class SCBiomeGrassColors {

	public static int plains;
	public static int desert;
	public static int extremeHills;
	public static int forest;
	public static int taiga;
	public static int swampland;
	public static int jungle;
	public static int mushroomIsland;
	
	//BTA	
	public static int icyPeaks;
	public static int frozenBeach;
	public static int aridForest;
	
	//BetterBiomes
	
	
	public static void updateGrassColors() {
		plains = BiomeGenBase.plains.getBiomeGrassColor();
		desert = BiomeGenBase.desert.getBiomeGrassColor();
		extremeHills = BiomeGenBase.extremeHills.getBiomeGrassColor();
		forest = BiomeGenBase.forest.getBiomeGrassColor();
		taiga = BiomeGenBase.taiga.getBiomeGrassColor();
		swampland = BiomeGenBase.swampland.getBiomeGrassColor();
		jungle = BiomeGenBase.jungle.getBiomeGrassColor();
		mushroomIsland = BiomeGenBase.mushroomIsland.getBiomeGrassColor();
		
		if (SCBTAIntegration.isBTAInstalled())
		{
			plains = BTABiomeConfiguration.plains.getBiomeGrassColor();
			desert = BTABiomeConfiguration.desert.getBiomeGrassColor();
			extremeHills = BTABiomeConfiguration.mountains.getBiomeGrassColor();
			forest = BTABiomeConfiguration.woods.getBiomeGrassColor();
			taiga = BTABiomeConfiguration.siberia.getBiomeGrassColor();
			swampland = BTABiomeConfiguration.swamp.getBiomeGrassColor();
			jungle = BTABiomeConfiguration.jungle.getBiomeGrassColor();
			//mushroomIsland = BTABiomeConfiguration.mushroomIsland.getBiomeGrassColor(); doesn't exist
		}
		
		if (SCBTAIntegration.isBBInstalled())
		{
			
		}
	}
	
}
