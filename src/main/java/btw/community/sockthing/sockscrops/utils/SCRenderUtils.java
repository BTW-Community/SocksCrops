package btw.community.sockthing.sockscrops.utils;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class SCRenderUtils {

    public static final int PLAINS_BIOME_COLOR = -7226023;
    public static final int DESERT_BIOME_COLOR = -4212907;
    public static final int XHILLS_BIOME_COLOR = -7686519;
    public static final int FOREST_BIOME_COLOR = -8798118;
    public static final int TAIGA_BIOME_COLOR = -8211053;
    public static final int SWAMP_BIOME_COLOR = 6056270;
    public static final int JUNGLE_BIOME_COLOR = -11285961;
    public static final int MUSHROOM_BIOME_COLOR = -11155137;

    public static int color(int baseColor, int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                int var10 = baseColor; //BiomeGrassColor
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }

        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
    }

    public static int color(int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                int var10 = -7226023; //BiomeGrassColor
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }

        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
    }

    public static int color(IBlockAccess blockAccess, int i, int j, int k, int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                int var10 = blockAccess.getBiomeGenForCoords(i + var9, k + var8).getBiomeGrassColor();
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }

        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
    }

    public static void renderGrassBlockAsItem(RenderBlocks renderer, Block block, int meta, float brightness, Icon[] snowIcon, Icon[] dirtIcon, Icon grassTop, Icon grassSide) {
        Tessellator tess = Tessellator.instance;
        boolean isBlock = block.blockID == Block.grass.blockID; //SCDefs.grassNutrition.blockID;

        int var6;
        float var7;
        float var8;
        float var9;

//        if (renderer.useInventoryTint)
//        {
//            var6 = block.getRenderColor(meta);
//
//            if (isBlock)
//            {
//                var6 = 16777215;
////                var6 = 0xff0000;
//            }
//
//            var7 = (float)(var6 >> 16 & 255) / 255.0F;
//            var8 = (float)(var6 >> 8 & 255) / 255.0F;
//            var9 = (float)(var6 & 255) / 255.0F;
//            GL11.glColor4f(var7 * brightness, var8 * brightness, var9 * brightness, 1.0F);
//        }

        renderer.setRenderBoundsFromBlock(block);
        int var14;

        renderer.setRenderBounds(block.getBlockBoundsFromPoolForItemRender(meta));

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
        if (meta == 2) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 4) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 6) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);

        tess.draw();

        var14 = color(0, 0, 0);
        if (meta == 2) var14 = color(200, -25, 0);
        if (meta == 4) var14 = color(400, -50, 0);
        if (meta == 6) var14 = color(600, -100, 0);

        var8 = (float) (var14 >> 16 & 255) / 255.0F;
        var9 = (float) (var14 >> 8 & 255) / 255.0F;
        float var10 = (float) (var14 & 255) / 255.0F;
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, grassTop);
        tess.draw();

        GL11.glColor4f(brightness, brightness, brightness, 1.0F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[3]);
        if (meta == 2) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[2]);
        if (meta == 4) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[1]);
        if (meta == 6) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[0]);

        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, snowIcon[3]);
        if (meta == 2) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, snowIcon[2]);
        if (meta == 4) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, snowIcon[1]);
        if (meta == 6) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, snowIcon[0]);
        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[3]);
        if (meta == 2) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[2]);
        if (meta == 4) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[1]);
        if (meta == 6) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, snowIcon[0]);
        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, snowIcon[3]);
        if (meta == 2) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, snowIcon[2]);
        if (meta == 4) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, snowIcon[1]);
        if (meta == 6) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, snowIcon[0]);
        tess.draw();

        int varColor = color(0, 0, 0);
        if (meta == 2) varColor = color(200, -25, 0);
        if (meta == 4) varColor = color(400, -50, 0);
        if (meta == 6) varColor = color(600, -100, 0);

        float var77 = (float) (varColor >> 16 & 255) / 255.0F;
        float var88 = (float) (varColor >> 8 & 255) / 255.0F;
        float var99 = (float) (varColor & 255) / 255.0F;
        GL11.glColor4f(var77 * brightness * 1.5f, var88 * brightness * 1.5f, var99 * brightness * 1.5f, 1.0F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, grassSide);
        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, grassSide);
        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, grassSide);
        tess.draw();

        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, grassSide);
        tess.draw();

        GL11.glTranslatef(0.5F, 0.5F, 0.5F);

    }

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean randomOffset) {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();

        if (!bHasOverride) {
            renderBlocks.setOverrideBlockTexture(texture);
        }

        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(block.getMixedBrightnessForBlock(renderBlocks.blockAccess, i, j, k));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderBlocks.blockAccess, i, j, k);
        float var8 = (float) (var7 >> 16 & 255) / 255.0F;
        float var9 = (float) (var7 >> 8 & 255) / 255.0F;
        float var10 = (float) (var7 & 255) / 255.0F;

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double newX = i;
        double newY = j;
        double newZ = k;

        double newCenterX = i;
        double newCenterZ = k;


        if (randomOffset) {
            long hash = (long) (i * 3129871L) ^ (long) k * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;
            newX += ((double) ((float) (hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            newZ += ((double) ((float) (hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;

            double centerX = 8 / 16D;
            double centerZ = 8 / 16D;

            double c = 0.0D;

            newCenterX = (Math.floor(newX * 16 + c) - c) / 16;
            newCenterZ = (Math.floor(newZ * 16 + c) - c) / 16;
        }

        renderBlocks.drawCrossedSquares(block, renderBlocks.blockAccess.getBlockMetadata(i, j, k), newCenterX, j, newCenterZ, 1.0F);

        if (!bHasOverride) {
            renderBlocks.clearOverrideBlockTexture();
        }
    }

}
