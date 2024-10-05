package btw.community.sockthing.sockscrops.mixin;

import btw.community.sockthing.sockscrops.world.BiomeIDs;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.GenLayer;
import net.minecraft.src.GenLayerShore;
import net.minecraft.src.IntCache;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GenLayerShore.class)
public abstract class GenLayerShoreMixin extends GenLayer {

    public GenLayerShoreMixin(long biomeID) {
        super(biomeID);
    }

    @Override
    public int[] getInts(int par1, int par2, int par3, int par4) {
        int[] var5 = this.parent.getInts(par1 - 1, par2 - 1, par3 + 2, par4 + 2);
        int[] var6 = IntCache.getIntCache(par3 * par4);

        for (int var7 = 0; var7 < par4; ++var7) {
            for (int var8 = 0; var8 < par3; ++var8) {
                this.initChunkSeed(var8 + par1, var7 + par2);
                int var9 = var5[var8 + 1 + (var7 + 1) * (par3 + 2)];
                int var10;
                int var11;
                int var12;
                int var13;

                if (var9 == BiomeGenBase.mushroomIsland.biomeID) {
                    var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

                    if (var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    } else {
                        var6[var8 + var7 * par3] = BiomeGenBase.mushroomIslandShore.biomeID;
                    }
                } else if (var9 != BiomeGenBase.ocean.biomeID && var9 != BiomeGenBase.river.biomeID && var9 != BiomeGenBase.swampland.biomeID && var9 != BiomeGenBase.extremeHills.biomeID) {
                    var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

                    if (var10 != BiomeGenBase.ocean.biomeID && var11 != BiomeGenBase.ocean.biomeID && var12 != BiomeGenBase.ocean.biomeID && var13 != BiomeGenBase.ocean.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    } else {
                        /*
                        var6[var8 + var7 * par3] = BiomeGenBase.beach.biomeID;
                        */
                        int currentBiome = var9;

                        int[] intCache = var6;
                        int xSize = par3;
                        int neighbor1 = var10;
                        int neighbor2 = var11;
                        int neighbor3 = var12;
                        int neighbor4 = var13;

                        if (!BiomeGenBase.biomeList[neighbor1].getEnableSnow() && !BiomeGenBase.biomeList[neighbor2].getEnableSnow() && !BiomeGenBase.biomeList[neighbor3].getEnableSnow() && !BiomeGenBase.biomeList[neighbor4].getEnableSnow()) {
                            if (currentBiome == BiomeGenBase.plains.biomeID) {
                                intCache[var8 + var7 * xSize] = BiomeIDs.BEACH_PLAINS_ID;
                            } else if (currentBiome == BiomeGenBase.desert.biomeID || currentBiome == BiomeGenBase.desertHills.biomeID) {
                                intCache[var8 + var7 * xSize] = BiomeIDs.BEACH_DESERT_ID;
                            } else if (currentBiome == BiomeGenBase.forest.biomeID || currentBiome == BiomeGenBase.forestHills.biomeID) {
                                intCache[var8 + var7 * xSize] = BiomeIDs.BEACH_FOREST_ID;
                            } else if (currentBiome == BiomeGenBase.taiga.biomeID || currentBiome == BiomeGenBase.taigaHills.biomeID) {
                                intCache[var8 + var7 * xSize] = BiomeIDs.BEACH_TAIGA_ID;
                            } else if (currentBiome == BiomeGenBase.jungle.biomeID || currentBiome == BiomeGenBase.jungleHills.biomeID) {
                                intCache[var8 + var7 * xSize] = BiomeIDs.BEACH_JUNGLE_ID;
                            } else intCache[var8 + var7 * xSize] = BiomeGenBase.beach.biomeID;
                        } else {
                            intCache[var8 + var7 * xSize] = BiomeGenBase.beach.biomeID;
                        }
                    }
                } else if (var9 == BiomeGenBase.extremeHills.biomeID) {
                    var10 = var5[var8 + 1 + (var7 + 1 - 1) * (par3 + 2)];
                    var11 = var5[var8 + 1 + 1 + (var7 + 1) * (par3 + 2)];
                    var12 = var5[var8 + 1 - 1 + (var7 + 1) * (par3 + 2)];
                    var13 = var5[var8 + 1 + (var7 + 1 + 1) * (par3 + 2)];

                    if (var10 == BiomeGenBase.extremeHills.biomeID && var11 == BiomeGenBase.extremeHills.biomeID && var12 == BiomeGenBase.extremeHills.biomeID && var13 == BiomeGenBase.extremeHills.biomeID) {
                        var6[var8 + var7 * par3] = var9;
                    } else {
                        var6[var8 + var7 * par3] = BiomeGenBase.extremeHillsEdge.biomeID;
                    }
                } else {
                    var6[var8 + var7 * par3] = var9;
                }
            }
        }

        return var6;
    }
}
