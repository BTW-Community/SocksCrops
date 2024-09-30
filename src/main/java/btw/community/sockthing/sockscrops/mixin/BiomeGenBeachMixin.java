package btw.community.sockthing.sockscrops.mixin;

import btw.community.sockthing.sockscrops.utils.BiomeUtils;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenBeach;
import net.minecraft.src.BiomeGenRiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenRiver.class)
public class BiomeGenBeachMixin extends BiomeGenBase {

    private static final BiomeGenBase beachPlains = (new BiomeGenBeach(BiomeUtils.BEACH_PLAINS_ID));
    private static final BiomeGenBase beachDesert = (new BiomeGenBeach(BiomeUtils.BEACH_DESERT_ID));
    private static final BiomeGenBase beachForest = (new BiomeGenBeach(BiomeUtils.BEACH_FOREST_ID));
    private static final BiomeGenBase beachTaiga = (new BiomeGenBeach(BiomeUtils.BEACH_TAIGA_ID));
    private static final BiomeGenBase beachJungle = (new BiomeGenBeach(BiomeUtils.BEACH_JUNGLE_ID));

    protected BiomeGenBeachMixin(int par1) {
        super(par1);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(int biomeID, CallbackInfo info) {

        if (biomeID == BiomeUtils.BEACH_PLAINS_ID) {
            this.setColor(9286496);
            this.setBiomeName("BeachPlains");
            this.setMinMaxHeight(0.0F, 0.1F);
            this.setTemperatureRainfall(0.8F, 0.4F);
        } else if (biomeID == BiomeUtils.BEACH_DESERT_ID) {
            this.setColor(16421912);
            this.setBiomeName("BeachDesert");
            this.setMinMaxHeight(0.0F, 0.1F);
            this.setTemperatureRainfall(2.0F, 0.0F);
        } else if (biomeID == BiomeUtils.BEACH_FOREST_ID) {
            this.setColor(353825);
            this.setBiomeName("BeachForest");
            this.setMinMaxHeight(0.0F, 0.1F);
            this.setTemperatureRainfall(0.8F, 0.4F);
        } else if (biomeID == BiomeUtils.BEACH_TAIGA_ID) {
            this.setColor(747097);
            this.setBiomeName("BeachTaiga");
            this.setMinMaxHeight(0.0F, 0.1F);
            this.setTemperatureRainfall(0.8F, 0.4F);
        } else if (biomeID == BiomeUtils.BEACH_JUNGLE_ID) {
            this.setColor(5470985);
            this.setBiomeName("BeachJungle");
            this.setMinMaxHeight(0.0F, 0.1F);
            this.setTemperatureRainfall(0.8F, 0.4F);
        }

    }
}
