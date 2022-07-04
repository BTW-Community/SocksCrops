package net.minecraft.src;

public class SCBiomeConfiguration {

	
	public static int getRiverVariantForBiomes(int baseBiome)
	{
		int riverBiome = -1;
		
		if (BiomeGenBase.biomeList[baseBiome] instanceof BiomeGenRiver)
		{
			riverBiome = baseBiome;
		}
		else if (baseBiome == BiomeGenBase.desert.biomeID || baseBiome == BiomeGenBase.desertHills.biomeID )
		{
			riverBiome = BiomeGenBase.riverDesert.biomeID;
		}
		else if (baseBiome == BiomeGenBase.forest.biomeID || baseBiome == BiomeGenBase.forestHills.biomeID )
		{
			riverBiome = BiomeGenBase.riverForest.biomeID;
		}
		else if (baseBiome == BiomeGenBase.extremeHills.biomeID || baseBiome == BiomeGenBase.extremeHillsEdge.biomeID )
		{
			riverBiome = BiomeGenBase.riverExtremeHills.biomeID;
		}
		else if (baseBiome == BiomeGenBase.jungle.biomeID || baseBiome == BiomeGenBase.jungleHills.biomeID )
		{
			riverBiome = BiomeGenBase.riverJungle.biomeID;
		}
		else if (baseBiome == BiomeGenBase.swampland.biomeID )
		{
			riverBiome = BiomeGenBase.riverSwamp.biomeID;
		}
		else if (BiomeGenBase.biomeList[baseBiome].getEnableSnow())
		{
			if ( baseBiome == BiomeGenBase.taiga.biomeID  || baseBiome == BiomeGenBase.taigaHills.biomeID )
			{
				riverBiome = BiomeGenBase.riverTaiga.biomeID;
			}
			else riverBiome = BiomeGenBase.frozenRiver.biomeID;
		}
		else if (baseBiome == BiomeGenBase.plains.biomeID )
		{
			riverBiome = BiomeGenBase.riverPlains.biomeID;
		}
		else
		{
			riverBiome = BiomeGenBase.river.biomeID;
		}
		
		return riverBiome;
	}
}
