package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

public class FarmlandFertilizedDepletedNutritionBlock extends FarmlandDepletedNutritionBlock {
    public FarmlandFertilizedDepletedNutritionBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    protected boolean isFertilized(IBlockAccess blockAccess, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean getIsFertilizedForPlantGrowth(World world, int x, int y, int z) {
        return true;
    }

    public float getNutritionMultiplier() {
        return 2F * NutritionUtils.MULTIPLIER_DEPLETED_NUTRITION;
    }

    @Override
    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return SCBlocks.farmlandLowNutrition.blockID;
    }

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (secondPass) {
            return side == 1;
        } else return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    @Environment(EnvType.CLIENT)
    protected Icon fertilizerOverlayDry;
    @Environment(EnvType.CLIENT)
    protected Icon fertilizerOverlayWet;

    @Environment(EnvType.CLIENT)
    private boolean secondPass;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        fertilizerOverlayDry = register.registerIcon("farmland_fertilized_overlay_dry");
        fertilizerOverlayWet = register.registerIcon("farmland_fertilized_overlay_wet");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (secondPass) {
            return getBlockTextureSecondPass(blockAccess, x, y, z, side);
        } else return super.getBlockTexture(blockAccess, x, y, z, side);
    }

    @Environment(EnvType.CLIENT)
    private Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (side == 1) {
            if (isHydrated(blockAccess.getBlockMetadata(x, y, z))) {
                return fertilizerOverlayWet;
            } else return fertilizerOverlayDry;
        } else return null;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPassResult) {
        secondPass = true;

        renderer.renderStandardBlock(this, x, y, z);

        secondPass = false;
    }
}
