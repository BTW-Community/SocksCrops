package btw.community.sockthing.sockscrops.mixins;

import btw.community.sockthing.sockscrops.world.BiomeIDs;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.BiomeGenRiver;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiomeGenRiver.class)
public abstract class BiomeGenRiverMixin extends BiomeGenBase {

    private static final BiomeGenBase riverPlains = (new BiomeGenRiver(BiomeIDs.RIVER_PLAINS_ID));
    private static final BiomeGenBase riverDesert = (new BiomeGenRiver(BiomeIDs.RIVER_DESERT_ID));
    private static final BiomeGenBase riverExtremeHills = (new BiomeGenRiver(BiomeIDs.RIVER_XHILLS_ID));
    private static final BiomeGenBase riverForest = (new BiomeGenRiver(BiomeIDs.RIVER_FOREST_ID));
    private static final BiomeGenBase riverTaiga = (new BiomeGenRiver(BiomeIDs.RIVER_TAIGA_ID));
    private static final BiomeGenBase riverSwamp = (new BiomeGenRiver(BiomeIDs.RIVER_SWAMP_ID));
    private static final BiomeGenBase riverJungle = (new BiomeGenRiver(BiomeIDs.RIVER_JUNGLE_ID));
    private static final BiomeGenBase riverMushroomIsland = (new BiomeGenRiver(BiomeIDs.RIVER_MUSHROOM_ISLAND_ID));

    public BiomeGenRiverMixin(int par1) {
        super(par1);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public void init(int biomeID, CallbackInfo info) {

        if (biomeID == BiomeIDs.RIVER_PLAINS_ID) {
            this.setColor(255);
            this.setBiomeName("RiverPlains");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_DESERT_ID) {
            this.setColor(255);
            this.setBiomeName("RiverDesert");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_XHILLS_ID) {
            this.setColor(255);
            this.setBiomeName("RiverExtremeHills");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_FOREST_ID) {
            this.setColor(255);
            this.setBiomeName("RiverForest");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_TAIGA_ID) {
            this.setColor(255);
            this.setBiomeName("RiverTaiga");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_SWAMP_ID) {
            this.setColor(255);
            this.setBiomeName("RiverSwamp");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_JUNGLE_ID) {
            this.setColor(255);
            this.setBiomeName("RiverJungle");
            this.setMinMaxHeight(-0.5F, 0.0F);
        } else if (biomeID == BiomeIDs.RIVER_MUSHROOM_ISLAND_ID) {
            this.setColor(255);
            this.setBiomeName("RiverMushroomIsland");
            this.setMinMaxHeight(-0.5F, 0.0F);
        }
    }
}
