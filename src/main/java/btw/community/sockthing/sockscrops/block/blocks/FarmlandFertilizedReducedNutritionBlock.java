package btw.community.sockthing.sockscrops.block.blocks;

import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

public class FarmlandFertilizedReducedNutritionBlock extends FarmlandReducedNutritionBlock {
    public FarmlandFertilizedReducedNutritionBlock(int blockID, String name) {
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
        return 2F * NutritionUtils.MULTIPLIER_REDUCED_NUTRITION;
    }

    @Override
    protected int getFarmlandOnFullPlantGrowthWhenDunged() {
        return SCBlocks.farmlandReducedNutrition.blockID;
    }

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (secondPass) {
            return side == 1;
        } else return super.shouldSideBeRendered(blockAccess, x, y, z, side);
    }

    protected Icon fertilizerOverlayDry;
    protected Icon fertilizerOverlayWet;

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
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
        if (secondPass) {
            return getBlockTextureSecondPass(par1iBlockAccess, par2, par3, par4, par5);
        } else return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
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

    @Override
    @Environment(EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        super.renderBlockAsItem(renderer, iItemDamage, fBrightness);
        RenderUtils.renderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, fertilizerOverlayDry);
    }
}
