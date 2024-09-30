package btw.community.sockthing.sockscrops.utils;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class NutritionUtils {

    public static final String[] GRASS_TEXTURE_NAMES = new String[]{
            "grass", "grass_sparse",
            "grass_reduced", "grass_sparse_reduced",
            "grass_low", "grass_sparse_low",
            "grass_depleted", "grass_sparse_depleted"
    };

    public static final String[] DIRT_TEXTURE_NAMES = new String[]{"dirt", "dirt_reduced", "dirt_low", "dirt_depleted"};
    public static final String[] SPARSE_DIRT_TEXTURE_NAMES = new String[]{"fcBlockGrassSparseDirt", "dirt_sparse_reduced", "dirt_sparse_low", "dirt_sparse_depleted"};
    public static final String[] LOOSE_DIRT_TEXTURE_NAMES = new String[]{"fcBlockDirtLoose", "dirt_loose_reduced", "dirt_loose_low", "dirt_loose_depleted"};
    public static final String[] WET_LOOSE_DIRT_TEXTURE_NAMES = new String[]{"dirt_loose_wet", "dirt_loose_wet_reduced", "dirt_loose_wet_low", "dirt_loose_wet_depleted"};
    public static final String[] WET_FARMLAND_TEXTURE_NAMES = new String[]{"farmland_wet", "farmland_wet_reduced", "farmland_wet_low", "farmland_wet_depleted"};
    public static final String[] DRY_FARMLAND_TEXTURE_NAMES = new String[]{"farmland_dry", "farmland_dry_reduced", "farmland_dry_low", "farmland_dry_depleted"};

    public static final String[] PLANTER_TEXTURE_NAMES = new String[]{
            "full", "reduced", "low", "depleted",
            "full", "reduced", "low", "depleted"
    };
    public static final String[] GRASS_PLANTER_TEXTURE_NAMES = new String[]{
            "full", "reduced", "low", "depleted",
            "sparse_full", "sparse_reduced", "sparse_low", "sparse_depleted"
    };

    public static final int FULL_NUTRITION_LEVEL = 0;
    public static final int REDUCED_NUTRITION_LEVEL = 1;
    public static final int LOW_NUTRITION_LEVEL = 2;
    public static final int DEPLETED_NUTRITION_LEVEL = 3;

    public static final float MULTIPLIER_FULL_NUTRITION = 1.0F;
    public static final float MULTIPLIER_REDUCED_NUTRITION = 0.7F; //0.75F;
    public static final float MULTIPLIER_LOW_NUTRITION = 0.4F; //0.5F;
    public static final float MULTIPLIER_DEPLETED_NUTRITION = 0.1F; // 0.25F;

    //default was 1 in 20 ie 0.05F
    public static final float WEEDS_GROWTH_CHANCE_FULL_NUTRITION = 0.05F;
    public static final float WEEDS_GROWTH_CHANCE_REDUCED_NUTRITION = 0.1F;
    public static final float WEEDS_GROWTH_CHANCE_LOW_NUTRITION = 0.15F;
    public static final float WEEDS_GROWTH_CHANCE_DEPLETED_NUTRITION = 0.2F;

    public static final float GRASS_NUTRITION_GROWTH_CHANCE = 0.5F;

    //Used in DirtBlockMixin
    public static float getNutritionMultiplier(int metadata) {
        if (metadata == FULL_NUTRITION_LEVEL) return MULTIPLIER_FULL_NUTRITION;
        else if (metadata == REDUCED_NUTRITION_LEVEL) return MULTIPLIER_REDUCED_NUTRITION;
        else if (metadata == LOW_NUTRITION_LEVEL) return MULTIPLIER_LOW_NUTRITION;
        else return MULTIPLIER_DEPLETED_NUTRITION;
    }

    public static float getGrassNutritionMultiplier(int metadata) {
        if (metadata < 2) return MULTIPLIER_FULL_NUTRITION;
        else if (metadata < 4) return MULTIPLIER_REDUCED_NUTRITION;
        else if (metadata < 6) return MULTIPLIER_LOW_NUTRITION;
        else return MULTIPLIER_DEPLETED_NUTRITION;
    }

    public static int getGrassNutritionLevel(int metadata) {
        if (metadata < 2) return FULL_NUTRITION_LEVEL;
        else if (metadata < 4) return REDUCED_NUTRITION_LEVEL;
        else if (metadata < 6) return LOW_NUTRITION_LEVEL;
        else return DEPLETED_NUTRITION_LEVEL;
    }

    public static int getNutritionLevel(int metadata) {
        return metadata;
    }

    public static int getPlanterNutritionLevel(int metadata) {
        return metadata & 3;
    }

    public static boolean isValidPlantAbove(World world, int i, int j, int k) {
        return world.getBlockId(i, j, k) == Block.tallGrass.blockID
                || world.getBlockId(i, j, k) == Block.plantRed.blockID
                || world.getBlockId(i, j, k) == Block.plantYellow.blockID
                || world.getBlockId(i, j, k) == SCBlocks.doubleTallGrass.blockID;
    }
}
