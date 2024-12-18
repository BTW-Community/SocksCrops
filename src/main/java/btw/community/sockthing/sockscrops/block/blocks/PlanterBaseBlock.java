package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.PlanterBlockBase;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.NutritionUtils;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public abstract class PlanterBaseBlock extends PlanterBlockBase {

    public PlanterBaseBlock(int blockID, String name) {
        super(blockID);

        setHardness(0.5F);
        setPicksEffectiveOn(true);
        setHoesEffectiveOn(true);
        setTickRandomly(true);

        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(name);
    }

    //------------- Abstract ------------//

    protected abstract boolean isHydrated(int metadata);

    /**
     * Used to render the content textures like dirt or farmland.
     * If it returns null the overlay texture won't be rendered.
     */
    @Environment(EnvType.CLIENT)
    protected abstract Icon getContentsTexture(int metadata);

    /**
     * Used to render overlay textures like bonemeal or sparse grass.
     * If it returns null the overlay texture won't be rendered.
     */
    @Environment(EnvType.CLIENT)
    protected abstract Icon getOverlayTexture(int metadata);

    //------------- Client ------------//

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int metadata) {
        if (side == 1) {
            return getContentsTexture(metadata);
        } else return blockIcon;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("fcBlockPlanter");
        topRing = register.registerIcon("planter_top");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {

        renderFilledPlanterBlock(renderer, x, y, z);

        if (getOverlayTexture(renderer.blockAccess.getBlockMetadata(x, y, z)) != null) {
            renderOverlayTexture(renderer, x, y, z, overlayDry, overlayWet);
        }
        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean firstPassResult) {
        renderer.setOverrideBlockTexture(topRing);
        renderer.setRenderBounds(0, 1, 0, 1, 1.001, 1);
        renderer.renderStandardBlock(this, x, y, z);
        renderer.clearOverrideBlockTexture();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderer, int itemDamage, float brightness) {
        renderFilledPlanterInvBlock(renderer, this, itemDamage);

        renderOverlayForItemRender(renderer, itemDamage, brightness, overlayDry);

        GL11.glColor4f(1 * brightness, 1 * brightness, 1 * brightness, 1.0F);
        renderer.setOverrideBlockTexture(topRing);
        renderer.setRenderBounds(0, 1.001, 0, 1, 1.002, 1);
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, itemDamage);
        renderer.clearOverrideBlockTexture();
    }


    //------------- Addon Client ------------//

    @Environment(EnvType.CLIENT)
    protected Icon topRing;
    @Environment(EnvType.CLIENT)
    protected Icon overlayDry;
    @Environment(EnvType.CLIENT)
    protected Icon overlayWet;

    @Environment(EnvType.CLIENT)
    protected void renderOverlayTexture(RenderBlocks renderer, int x, int y, int z, Icon overlayDry, Icon overlayWet) {
        Icon overlay;

        if (renderer.blockAccess.getBlockId(x, y, z) == SCBlocks.planterGrass.blockID) {
            overlay = overlayDry; //it's the grass_top or sparse texture
        } else {
            if (isHydrated(renderer.blockAccess.getBlockMetadata(x, y, z))) {
                overlay = overlayWet;
            } else overlay = overlayDry;
        }

        renderer.setOverrideBlockTexture(overlay);
        renderer.setRenderBounds(0, 0.999, 0, 1, 1, 1);
        renderer.renderStandardBlock(this, x, y, z);
        renderer.clearOverrideBlockTexture();
    }

    @Environment(EnvType.CLIENT)
    protected void renderOverlayForItemRender(RenderBlocks renderer, int itemDamage, float brightness, Icon overlayIcon) {
        renderer.setOverrideBlockTexture(overlayIcon);
        renderer.setRenderBounds(0, 1, 0, 1, 1.001, 1);
        RenderUtils.renderInvBlockWithMetadata(renderer, this, -0.5F, -0.5F, -0.5F, itemDamage);
        renderer.clearOverrideBlockTexture();
    }

    @Environment(EnvType.CLIENT)
    protected int getGrassColor(IBlockAccess blockAccess, int x, int y, int z) {
        switch (NutritionUtils.getPlanterNutritionLevel(blockAccess.getBlockMetadata(x, y, z))) {
            default:
                return -7226023;
            case NutritionUtils.FULL_NUTRITION_LEVEL:
                return SCRenderUtils.color(blockAccess, x, y, z, 0, 0, 0);
            case NutritionUtils.REDUCED_NUTRITION_LEVEL:
                return SCRenderUtils.color(blockAccess, x, y, z, 150, -25, 0);
            case NutritionUtils.LOW_NUTRITION_LEVEL:
                return SCRenderUtils.color(blockAccess, x, y, z, 300, -50, 0);
            case NutritionUtils.DEPLETED_NUTRITION_LEVEL:
                return SCRenderUtils.color(blockAccess, x, y, z, 400, -100, 0);
        }
    }
}
