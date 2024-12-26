package btw.community.sockthing.sockscrops.utils;

import btw.block.blocks.GroundCoverBlock;
import btw.community.sockthing.sockscrops.block.blocks.MultiFlowerBlock;
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

    private static final double renderMinX = 0D;
    private static final double renderMaxX = 1D;
    private static final double renderMinY = 0D;
    private static final double renderMaxY = 1D;
    private static final double renderMinZ = 0D;
    private static final double renderMaxZ = 1D;

    private static double groundShift = 1/16D + 1/1024D;

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

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean randomOffset){
        renderCrossedSquaresWithTexture(renderBlocks, block, i,j,k, 0D, 0D, 0D, texture, randomOffset, 16, false, 0.2D);
    }

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean randomOffset, int grid){
        renderCrossedSquaresWithTexture(renderBlocks, block, i,j,k, 0D, 0D, 0D, texture, randomOffset, grid, false, 0.2D);
    }

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean randomOffset, boolean randomHeight){
        renderCrossedSquaresWithTexture(renderBlocks, block, i,j,k, 0D, 0D, 0D, texture, randomOffset, 16, randomHeight, 0.2D);
    }

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean randomOffset, boolean randomHeight, double heightVariation){
        renderCrossedSquaresWithTexture(renderBlocks, block, i,j,k, 0D, 0D, 0D, texture, randomOffset, 16,randomHeight, heightVariation);
    }

    public static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, double shiftX, double shiftY, double shiftZ, Icon texture, boolean randomOffset){
        renderCrossedSquaresWithTexture(renderBlocks, block, i,j,k, shiftX, shiftY, shiftZ, texture, randomOffset, 16, false, 0.2D);
    }

    private static void renderCrossedSquaresWithTexture(RenderBlocks renderBlocks, Block block, int i, int j, int k, double shiftX, double shiftY, double shiftZ, Icon texture, boolean randomOffset, int grid, boolean randomHeight, double heightVariation) {
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

        double newCenterX = i + shiftX;
        double newCenterY = j + shiftY;
        double newCenterZ = k + shiftZ;

        double c = 0.0D;

        if (randomOffset) {
            long hash = (long) (i * 3129871L) ^ (long) k * 116129781L;
            hash = hash * hash * 42317861L + hash * 11L;

            newX += ((double) ((float) (hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            newZ += ((double) ((float) (hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;

            double centerX = 8 / 16D;
            double centerZ = 8 / 16D;

            newCenterX = (Math.floor(newX * grid + c) - c) / grid;
            newCenterZ = (Math.floor(newZ * grid + c) - c) / grid;

            if (grid == 4){
                newCenterX -= 2/16D;
                newCenterZ -= 2/16D;
            }
        }

        if (randomHeight)
        {
            long hash = (long) (i * 3129871L) ^ (long) k * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;

            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * heightVariation;

            newCenterY = (Math.floor(newY * 16 + c) - c) / 16;
        }

        renderBlocks.drawCrossedSquares(block, renderBlocks.blockAccess.getBlockMetadata(i, j, k), newCenterX, newCenterY, newCenterZ, 1.0F);

        if (!bHasOverride) {
            renderBlocks.clearOverrideBlockTexture();
        }
    }

    public static void renderVerticalPaneWithRotation(RenderBlocks renderer, Block block, int x, int y, int z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, Icon topIcon, Icon bottomIcon){
        renderFaceZPos(renderer, block, x, y, z - 0.5D, xShift, yShift, zShift, rotationAngleX, rotationAngleY, rotationAngleZ, x+0.5D, y+0.5D, z + 0.5D, topIcon,false, false, 0);
        renderFaceZNeg(renderer, block, x, y, z + 0.5D, xShift, yShift, zShift, rotationAngleX, rotationAngleY, rotationAngleZ, x+0.5D, y+0.5D, z + 0.5D, bottomIcon,false, false, 0);
    }
    public static void renderVerticalPaneWithRotation(RenderBlocks renderer, Block block, int x, int y, int z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, double rotationCenterX, double rotationCenterY, double rotationCenterZ , Icon topIcon, Icon bottomIcon){
        renderFaceZPos(renderer, block, x, y, z - 0.5D, xShift, yShift, zShift, rotationAngleX, rotationAngleY, rotationAngleZ, x+rotationCenterX+0.5D, y+rotationCenterY+0.5D, z + rotationCenterZ + 0.5D, topIcon,false, false, 0);
        renderFaceZNeg(renderer, block, x, y,z + 0.5D, xShift, yShift, zShift, rotationAngleX, rotationAngleY, rotationAngleZ, x+rotationCenterX+0.5D, y+rotationCenterY+0.5D, z + rotationCenterZ + 0.5D, bottomIcon,false, false, 0);
    }

    public static void renderHorizonzalPaneWithRotation(RenderBlocks renderer, Block block, int x, int y, int z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, Icon topIcon, Icon bottomIcon){
        renderFaceYPos(renderer, block, x - xShift, y - 0.5D + yShift, z - zShift, 0, 0, 0, 0, rotationAngleX, rotationAngleY, rotationAngleZ, topIcon,false, false, 0);
        renderFaceYNeg(renderer, block, x - xShift, y + 0.5D + yShift, z - zShift, 0, 0, 0, 0, rotationAngleX, rotationAngleY, rotationAngleZ, bottomIcon,false, false, 0);
    }

    public static void renderHorizonzalPaneSunflower(RenderBlocks renderer, Block block, int x, int y, int z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, Icon topIcon, Icon bottomIcon, boolean randomOffset, boolean randomHeight, double heightVariation){
        renderFaceYPos(renderer, block, x - xShift, y - 0.5D + yShift, z - zShift, 0, 0, 0, 0, rotationAngleX, rotationAngleY, rotationAngleZ, topIcon, randomOffset, randomHeight, heightVariation);
        renderFaceYNeg(renderer, block, x - xShift, y + 0.5D + yShift, z - zShift, 0, 0, 0, 0, rotationAngleX, rotationAngleY, rotationAngleZ, bottomIcon, randomOffset, randomHeight, heightVariation);
    }

    public static void renderHorizonzalPaneWithShift(RenderBlocks renderer, Block block, int x, int y, int z, double xShift, double yShift, double zShift, double nwShift, double neShift, double seShift, double swShift, Icon topIcon, Icon bottomIcon){
        renderFaceYPos(renderer, block, x - xShift, y - yShift, z - zShift, nwShift, neShift, seShift, swShift, 0, 0, 0, topIcon, false, false, 0);
        renderFaceYNeg(renderer, block, x - xShift, y + yShift, z - zShift, nwShift, neShift, seShift, swShift, 0, 0, 0, bottomIcon, false, false, 0);
    }



    private static void renderFaceYPos(RenderBlocks renderer, Block block, double x, double y, double z, double nwShift, double neShift, double seShift, double swShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, Icon icon, boolean randomOffset, boolean randomHeight, double heightVariation){
        Tessellator tess = Tessellator.instance;
        //tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, (int)x, (int)y, (int)z));
        float brightness = 1.0F;
        int colorMultiplier = block.colorMultiplier(renderer.blockAccess, (int)x, (int)y, (int)z);
        float var8 = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float var9 = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float var99 = (float)(colorMultiplier & 255) / 255.0F;

        tess.setColorOpaque_F(brightness * var8, brightness * var9, brightness * var99);

        double renderMinX = 0D;
        double renderMaxX = 1D;
        double renderMinY = 0D;
        double renderMaxY = 1D;
        double renderMinZ = 0D;
        double renderMaxZ = 1D;

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.getOverrideTexture();
        }

        double minU = (double)icon.getInterpolatedU(renderMinX * 16.0D);
        double maxU = (double)icon.getInterpolatedU(renderMaxX * 16.0D);
        double minV = (double)icon.getInterpolatedV(renderMinZ * 16.0D);
        double maxV = (double)icon.getInterpolatedV(renderMaxZ * 16.0D);

        if (renderMinX < 0.0D || renderMaxX > 1.0D)
        {
            minU = (double)icon.getMinU();
            maxU = (double)icon.getMaxU();
        }

        if (renderMinZ < 0.0D || renderMaxZ > 1.0D)
        {
            minV = (double)icon.getMinV();
            maxV = (double)icon.getMaxV();
        }

        double newMaxU = maxU;
        double newMinU = minU;
        double newMinV = minV;
        double newMaxV = maxV;

/*
        if (renderer.uvRotateTop == 1)
        {
            minU = (double)icon.getInterpolatedU(renderMinZ * 16.0D);
            minV = (double)icon.getInterpolatedV(16.0D - renderMaxX * 16.0D);
            maxU = (double)icon.getInterpolatedU(renderMaxZ * 16.0D);
            maxV = (double)icon.getInterpolatedV(16.0D - renderMinX * 16.0D);
            newMinV = minV;
            newMaxV = maxV;
            newMaxU = minU;
            newMinU = maxU;
            minV = maxV;
            maxV = newMinV;
        }
        else if (renderer.uvRotateTop == 2)
        {
            minU = (double)icon.getInterpolatedU(16.0D - renderMaxZ * 16.0D);
            minV = (double)icon.getInterpolatedV(renderMinX * 16.0D);
            maxU = (double)icon.getInterpolatedU(16.0D - renderMinZ * 16.0D);
            maxV = (double)icon.getInterpolatedV(renderMaxX * 16.0D);
            newMaxU = maxU;
            newMinU = minU;
            minU = maxU;
            maxU = newMinU;
            newMinV = maxV;
            newMaxV = minV;
        }
        else if (renderer.uvRotateTop == 3)
        {
            minU = (double)icon.getInterpolatedU(16.0D - renderMinX * 16.0D);
            maxU = (double)icon.getInterpolatedU(16.0D - renderMaxX * 16.0D);
            minV = (double)icon.getInterpolatedV(16.0D - renderMinZ * 16.0D);
            maxV = (double)icon.getInterpolatedV(16.0D - renderMaxZ * 16.0D);
            newMaxU = maxU;
            newMinU = minU;
            newMinV = minV;
            newMaxV = maxV;
        }
*/

        double combinedMinX = x + renderMinX;
        double combinedMaxX = x + renderMaxX;
        double combinedMaxY = y + renderMaxY;
        double combinedMinZ = z + renderMinZ;
        double combinedMaxZ = z + renderMaxZ;

        double c = 0.0D;

        double newX = x;
        double newY = y;
        double newZ = z;

//        if (randomOffset) {
//            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L;
//            hash = hash * hash * 42317861L + hash * 11L;
//
//            newX += ((double) ((float) (hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
//            newZ += ((double) ((float) (hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
//
//            double centerX = 8 / 16D;
//            double centerZ = 8 / 16D;
//
//            newCenterX = (Math.floor(newX * 16 + c) - c) / 16;
//            newCenterZ = (Math.floor(newZ * 16 + c) - c) / 16;
//        }

        if (randomHeight)
        {
            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;

            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * heightVariation;
            newY += 1D;
            combinedMaxY = (Math.floor(newY * 16 + c) - c) / 16;
        }

        if (rotationAngleX != 0 || rotationAngleY != 0 || rotationAngleZ != 0)
        {
            // Calculate the centroid of the plane (average of all X and Z coordinates)
            double centroidX = (combinedMaxX + combinedMinX) / 2.0;
            double centroidZ = (combinedMaxZ + combinedMinZ) / 2.0;
            double centroidY = combinedMaxY;  // Assuming the Y coordinate is constant in this case

            // Convert angles from degrees to radians
            double rotationAngleRadX = Math.toRadians(rotationAngleX);
            double rotationAngleRadY = Math.toRadians(rotationAngleY);
            double rotationAngleRadZ = Math.toRadians(rotationAngleZ);

            // Precompute cosines and sines for each axis rotation
            double cosX = Math.cos(rotationAngleRadX);
            double sinX = Math.sin(rotationAngleRadX);
            double cosY = Math.cos(rotationAngleRadY);
            double sinY = Math.sin(rotationAngleRadY);
            double cosZ = Math.cos(rotationAngleRadZ);
            double sinZ = Math.sin(rotationAngleRadZ);

            // Rotate each vertex's X, Y, Z coordinates
            double[] vertex1 = rotate3D(combinedMaxX, combinedMaxY + seShift, combinedMaxZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex2 = rotate3D(combinedMaxX, combinedMaxY + neShift, combinedMinZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex3 = rotate3D(combinedMinX, combinedMaxY + nwShift, combinedMinZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex4 = rotate3D(combinedMinX, combinedMaxY + swShift, combinedMaxZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);

            // Replace the original coordinates with the rotated ones in the vertex function
            tess.addVertexWithUV(vertex1[0], vertex1[1], vertex1[2], maxU, maxV);
            tess.addVertexWithUV(vertex2[0], vertex2[1], vertex2[2], newMaxU, newMinV);
            tess.addVertexWithUV(vertex3[0], vertex3[1], vertex3[2], minU, minV);
            tess.addVertexWithUV(vertex4[0], vertex4[1], vertex4[2], newMinU, newMaxV);
        }
        else {
            tess.addVertexWithUV(combinedMaxX, combinedMaxY + seShift, combinedMaxZ, maxU, maxV);
            tess.addVertexWithUV(combinedMaxX, combinedMaxY + neShift, combinedMinZ, newMaxU, newMinV);
            tess.addVertexWithUV(combinedMinX, combinedMaxY + nwShift, combinedMinZ, minU, minV);
            tess.addVertexWithUV(combinedMinX, combinedMaxY + swShift, combinedMaxZ, newMinU, newMaxV);
        }
    }

    private static void renderFaceYNeg(RenderBlocks renderer, Block block, double x, double y, double z, double nwShift, double neShift, double seShift, double swShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, Icon icon, boolean randomOffset, boolean randomHeight, double heightVariation){

        Tessellator tess = Tessellator.instance;
        //tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, (int)x, (int)y, (int)z));
        float brightness = 1.0F;
        int colorMultiplier = block.colorMultiplier(renderer.blockAccess, (int)x, (int)y, (int)z);
        float red = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float green = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float blue = (float)(colorMultiplier & 255) / 255.0F;

        tess.setColorOpaque_F(brightness * red, brightness * green, brightness * blue);

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.getOverrideTexture();
        }

        double minU = (double)icon.getInterpolatedU(renderMinX * 16.0D);
        double maxU = (double)icon.getInterpolatedU(renderMaxX * 16.0D);
        double minV = (double)icon.getInterpolatedV(renderMinZ * 16.0D);
        double maxV = (double)icon.getInterpolatedV(renderMaxZ * 16.0D);

        if (renderMinX < 0.0D || renderMaxX > 1.0D)
        {
            minU = (double)icon.getMinU();
            maxU = (double)icon.getMaxU();
        }

        if (renderMinZ < 0.0D || renderMaxZ > 1.0D)
        {
            minV = (double)icon.getMinV();
            maxV = (double)icon.getMaxV();
        }

        double newMaxU = maxU;
        double newMinU = minU;
        double newMinV = minV;
        double newMaxV = maxV;

//        double scale = 0.45D;
        double combinedMinX = x + renderMinX;// - scale;
        double combinedMaxX = x + renderMaxX;// + scale;
        double combinedMinY = y + renderMinY;
        double combinedMinZ = z + renderMinZ;// - scale;
        double combinedMaxZ = z + renderMaxZ;// + scale;

        double c = 0.0D;

        double newX = x;
        double newY = y;
        double newZ = z;

//        if (randomOffset) {
//            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L;
//            hash = hash * hash * 42317861L + hash * 11L;
//
//            newX += ((double) ((float) (hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
//            newZ += ((double) ((float) (hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
//
//            double centerX = 8 / 16D;
//            double centerZ = 8 / 16D;
//
//            newCenterX = (Math.floor(newX * 16 + c) - c) / 16;
//            newCenterZ = (Math.floor(newZ * 16 + c) - c) / 16;
//        }

        if (randomHeight)
        {
            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;

            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * heightVariation;

            combinedMinY = (Math.floor(newY * 16 + c) - c) / 16;
        }

        if (rotationAngleX != 0 || rotationAngleY != 0 || rotationAngleZ != 0)
        {
            // Calculate the centroid of the plane (average of all X and Z coordinates)
            double centroidX = (combinedMaxX + combinedMinX) / 2.0;
            double centroidZ = (combinedMaxZ + combinedMinZ) / 2.0;
            double centroidY = combinedMinY;  // Assuming the Y coordinate is constant in this case

            // Convert angles from degrees to radians
            double rotationAngleRadX = Math.toRadians(rotationAngleX);
            double rotationAngleRadY = Math.toRadians(rotationAngleY);
            double rotationAngleRadZ = Math.toRadians(rotationAngleZ);

            // Precompute cosines and sines for each axis rotation
            double cosX = Math.cos(rotationAngleRadX);
            double sinX = Math.sin(rotationAngleRadX);
            double cosY = Math.cos(rotationAngleRadY);
            double sinY = Math.sin(rotationAngleRadY);
            double cosZ = Math.cos(rotationAngleRadZ);
            double sinZ = Math.sin(rotationAngleRadZ);

            // Rotate each vertex's X, Y, Z coordinates of the bottom-facing plane
            double[] vertex1 = rotate3D(combinedMinX, combinedMinY, combinedMaxZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex2 = rotate3D(combinedMinX, combinedMinY, combinedMinZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex3 = rotate3D(combinedMaxX, combinedMinY, combinedMinZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex4 = rotate3D(combinedMaxX, combinedMinY, combinedMaxZ,
                    centroidX, centroidY, centroidZ, cosX, sinX, cosY, sinY, cosZ, sinZ);

            // Replace the original coordinates with the rotated ones in the vertex function
            tess.addVertexWithUV(vertex1[0], vertex1[1], vertex1[2], newMinU, newMaxV);
            tess.addVertexWithUV(vertex2[0], vertex2[1], vertex2[2], minU, minV);
            tess.addVertexWithUV(vertex3[0], vertex3[1], vertex3[2], newMaxU, newMinV);
            tess.addVertexWithUV(vertex4[0], vertex4[1], vertex4[2], maxU, maxV);
        }
        else {
            tess.addVertexWithUV(combinedMinX, combinedMinY + seShift, combinedMaxZ, newMinU, newMaxV);
            tess.addVertexWithUV(combinedMinX, combinedMinY + neShift, combinedMinZ, minU, minV);
            tess.addVertexWithUV(combinedMaxX, combinedMinY + nwShift, combinedMinZ, newMaxU, newMinV);
            tess.addVertexWithUV(combinedMaxX, combinedMinY + swShift, combinedMaxZ, maxU, maxV);
        }

    }

    public static void renderFaceZNeg(RenderBlocks renderer, Block block, double x, double y, double z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, double rotationCenterX, double rotationCenterY, double rotationCenterZ, Icon icon, boolean randomOffset, boolean randomHeight, double heightVariation)
    {
        Tessellator tess = Tessellator.instance;
        //tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, (int)x, (int)y, (int)z));
        float brightness = 1.0F;
        int colorMultiplier = block.colorMultiplier(renderer.blockAccess, (int)x, (int)y, (int)z);
        float red = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float green = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float blue = (float)(colorMultiplier & 255) / 255.0F;

        tess.setColorOpaque_F(brightness * red, brightness * green, brightness * blue);

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.getOverrideTexture();
        }

        double minX = (double)icon.getInterpolatedU(renderMinX * 16.0D);
        double maxX = (double)icon.getInterpolatedU(renderMaxX * 16.0D);
        double maxY = (double)icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
        double minY = (double)icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
        double newMaxX;

        if (renderMinX < 0.0D || renderMaxX > 1.0D)
        {
            minX = (double)icon.getMinU();
            maxX = (double)icon.getMaxU();
        }

        if (renderMinY < 0.0D || renderMaxY > 1.0D)
        {
            maxY = (double)icon.getMinV();
            minY = (double)icon.getMaxV();
        }

        newMaxX = maxX;
        double newMinX = minX;
        double newMaxY = maxY;
        double newMinY = minY;

        double combinedMinX = x + renderMinX; // + xShift;
        double combinedMaxX = x + renderMaxX; // + xShift;
        double combinedMinY = y + renderMinY; // + yShift;
        double combinedMaxY = y + renderMaxY; // + yShift;
        double combinedMinZ = z + renderMinZ; // + zShift;

        double c = 0.0D;

        double newX = x;
        double newY = y;
        double newZ = z;

        if (randomHeight)
        {
            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;

            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * heightVariation;

            combinedMinY = (Math.floor(newY * 16 + c) - c) / 16;
        }

        if (rotationAngleX != 0 || rotationAngleY != 0 || rotationAngleZ != 0) {
            // Calculate the centroid for rotation
            double centroidX = (combinedMinX + combinedMaxX) / 2.0;
            double centroidY = (combinedMinY + combinedMaxY) / 2.0;
            double centroidZ = combinedMinZ;

            // Convert rotation angles from degrees to radians
            double rotationAngleRadX = Math.toRadians(rotationAngleX);
            double rotationAngleRadY = Math.toRadians(rotationAngleY);
            double rotationAngleRadZ = Math.toRadians(rotationAngleZ);

            // Precompute cosines and sines for each axis rotation
            double cosX = Math.cos(rotationAngleRadX);
            double sinX = Math.sin(rotationAngleRadX);
            double cosY = Math.cos(rotationAngleRadY);
            double sinY = Math.sin(rotationAngleRadY);
            double cosZ = Math.cos(rotationAngleRadZ);
            double sinZ = Math.sin(rotationAngleRadZ);

            // Rotate each vertex around the centroid
            double[] vertex1 = rotate3D(combinedMinX, combinedMaxY, combinedMinZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex2 = rotate3D(combinedMaxX, combinedMaxY, combinedMinZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex3 = rotate3D(combinedMaxX, combinedMinY, combinedMinZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex4 = rotate3D(combinedMinX, combinedMinY, combinedMinZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);

            // Shift vertices
            vertex1[0] += xShift;
            vertex2[0] += xShift;
            vertex3[0] += xShift;
            vertex4[0] += xShift;

            vertex1[1] += yShift;
            vertex2[1] += yShift;
            vertex3[1] += yShift;
            vertex4[1] += yShift;

            vertex1[2] += zShift;
            vertex2[2] += zShift;
            vertex3[2] += zShift;
            vertex4[2] += zShift;

            // Render the rotated vertices
            tess.addVertexWithUV(vertex1[0], vertex1[1], vertex1[2], maxX, maxY);
            tess.addVertexWithUV(vertex2[0], vertex2[1], vertex2[2], minX, maxY);
            tess.addVertexWithUV(vertex3[0], vertex3[1], vertex3[2], minX, minY);
            tess.addVertexWithUV(vertex4[0], vertex4[1], vertex4[2], maxX, minY);
        }
        else {
            tess.addVertexWithUV(combinedMinX, combinedMaxY, combinedMinZ, newMaxX, newMaxY);
            tess.addVertexWithUV(combinedMaxX, combinedMaxY, combinedMinZ, minX, maxY);
            tess.addVertexWithUV(combinedMaxX, combinedMinY, combinedMinZ, newMinX, newMinY);
            tess.addVertexWithUV(combinedMinX, combinedMinY, combinedMinZ, maxX, minY);
        }

    }

    /**
     * Renders the given texture to the south (z-positive) face of the block.  Args: block, x, y, z, texture
     */
    public static void renderFaceZPos(RenderBlocks renderer, Block block, double x, double y, double z, double xShift, double yShift, double zShift, double rotationAngleX, double rotationAngleY, double rotationAngleZ, double rotationCenterX, double rotationCenterY, double rotationCenterZ, Icon icon, boolean randomOffset, boolean randomHeight, double heightVariation)
        {
        Tessellator tess = Tessellator.instance;
        float brightness = 1.0F;
        int colorMultiplier = block.colorMultiplier(renderer.blockAccess, (int)x, (int)y, (int)z);
        float var8 = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float var9 = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float var99 = (float)(colorMultiplier & 255) / 255.0F;

        tess.setColorOpaque_F(brightness * var8, brightness * var9, brightness * var99);

        if (renderer.hasOverrideBlockTexture())
        {
            icon = renderer.getOverrideTexture();
        }

        double minU = (double)icon.getInterpolatedU(renderMinX * 16.0D);
        double maxU = (double)icon.getInterpolatedU(renderMaxX * 16.0D);
        double minV = (double)icon.getInterpolatedV(16.0D - renderMaxY * 16.0D);
        double maxV = (double)icon.getInterpolatedV(16.0D - renderMinY * 16.0D);
        double newMaxU;


        if (renderMinX < 0.0D || renderMaxX > 1.0D)
        {
            minU = (double)icon.getMinU();
            maxU = (double)icon.getMaxU();
        }

        if (renderMinY < 0.0D || renderMaxY > 1.0D)
        {
            minV = (double)icon.getMinV();
            maxV = (double)icon.getMaxV();
        }

        newMaxU = maxU;
        double newMinU = minU;
        double newMinV = minV;
        double newMaxV = maxV;

        double combinedMinX = x + renderMinX; // + xShift;
        double combinedMaxX = x + renderMaxX; // + xShift;
        double combinedMinY = y + renderMinY; // + yShift;
        double combinedMaxY = y + renderMaxY; // + yShift;
        double combinedMaxZ = z + renderMaxZ; // + zShift;

        double c = 0.0D;

        double newX = x;
        double newY = y;
        double newZ = z;

        if (randomHeight)
        {
            long hash = (long) (x * 3129871L) ^ (long) z * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;

            newY += ((double) ((float) (hash >> 20 & 15L) / 15.0F) - 1.0D) * heightVariation;

            combinedMinY = (Math.floor(newY * 16 + c) - c) / 16;
        }

        if (rotationAngleX != 0 || rotationAngleY != 0 || rotationAngleZ != 0)
        {
            // Calculate the centroid of the face for rotation
            double centroidX = (combinedMinX + combinedMaxX) / 2.0;
            double centroidY = (combinedMinY + combinedMaxY) / 2.0;
            double centroidZ = combinedMaxZ;

            // Convert rotation angles from degrees to radians
            double rotationAngleRadX = Math.toRadians(rotationAngleX);
            double rotationAngleRadY = Math.toRadians(rotationAngleY);
            double rotationAngleRadZ = Math.toRadians(rotationAngleZ);

            // Precompute cosines and sines for each axis rotation
            double cosX = Math.cos(rotationAngleRadX);
            double sinX = Math.sin(rotationAngleRadX);
            double cosY = Math.cos(rotationAngleRadY);
            double sinY = Math.sin(rotationAngleRadY);
            double cosZ = Math.cos(rotationAngleRadZ);
            double sinZ = Math.sin(rotationAngleRadZ);

            // Rotate each vertex around the centroid
            double[] vertex1 = rotate3D(combinedMinX, combinedMaxY, combinedMaxZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex2 = rotate3D(combinedMinX, combinedMinY, combinedMaxZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex3 = rotate3D(combinedMaxX, combinedMinY, combinedMaxZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);
            double[] vertex4 = rotate3D(combinedMaxX, combinedMaxY, combinedMaxZ,
                    rotationCenterX, rotationCenterY, rotationCenterZ, cosX, sinX, cosY, sinY, cosZ, sinZ);

            // Shift vertices
            vertex1[0] += xShift;
            vertex2[0] += xShift;
            vertex3[0] += xShift;
            vertex4[0] += xShift;

            vertex1[1] += yShift;
            vertex2[1] += yShift;
            vertex3[1] += yShift;
            vertex4[1] += yShift;

            vertex1[2] += zShift;
            vertex2[2] += zShift;
            vertex3[2] += zShift;
            vertex4[2] += zShift;

            // Render the rotated vertices
            tess.addVertexWithUV(vertex1[0], vertex1[1], vertex1[2], minU, minV);
            tess.addVertexWithUV(vertex2[0], vertex2[1], vertex2[2], minU, maxV);
            tess.addVertexWithUV(vertex3[0], vertex3[1], vertex3[2], maxU, maxV);
            tess.addVertexWithUV(vertex4[0], vertex4[1], vertex4[2], maxU, minV);
        }
        else {
            tess.addVertexWithUV(combinedMinX, combinedMaxY, combinedMaxZ, minU, minV);
            tess.addVertexWithUV(combinedMinX, combinedMinY, combinedMaxZ, newMinU, newMaxV);
            tess.addVertexWithUV(combinedMaxX, combinedMinY, combinedMaxZ, maxU, maxV);
            tess.addVertexWithUV(combinedMaxX, combinedMaxY, combinedMaxZ, newMaxU, newMinV);
        }
    }

    // Helper function to rotate a 3D point around a custom center point
    private static double[] rotate3D(double x, double y, double z,
                              double centerX, double centerY, double centerZ,
                              double cosX, double sinX, double cosY, double sinY, double cosZ, double sinZ) {
        // Translate point to the custom rotation center
        double dx = x - centerX;
        double dy = y - centerY;
        double dz = z - centerZ;

        // Apply rotation around X-axis
        double newY = dy * cosX - dz * sinX;
        double newZ = dy * sinX + dz * cosX;

        // Apply rotation around Y-axis
        double newX = dx * cosY + newZ * sinY;
        newZ = -dx * sinY + newZ * cosY;

        // Apply rotation around Z-axis
        double finalX = newX * cosZ - newY * sinZ;
        double finalY = newX * sinZ + newY * cosZ;
        double finalZ = newZ;

        // Translate point back to the original coordinates
        return new double[]{finalX + centerX, finalY + centerY, finalZ + centerZ};
    }

    public static void renderMultiFlowerBlock(RenderBlocks renderer, Block block, int x, int y, int z, Icon[] icon){
        Tessellator tess = Tessellator.instance;
        IBlockAccess blockAccess = renderer.blockAccess;

        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);

        renderer.setRenderBounds(0,0,0,1,1,1);


        int meta = blockAccess.getBlockMetadata(x,y,z);
        int stage = MultiFlowerBlock.getStage(meta);
        int dir = MultiFlowerBlock.getDirection(meta);

        switch (dir) {
            default:
            case 0: // Default direction (no change)
                renderer.setUVRotateTop(0);
                renderer.setUVRotateBottom(0);
                renderThis(renderer, block, x, y, z, stage, icon);
                renderer.clearUVRotation();
                break;

            case 1: // Rotated 90 degrees clockwise
                renderer.setUVRotateTop(1);
                renderer.setUVRotateBottom(2);
                renderThis(renderer, block, x, y, z, stage, icon);
                renderer.clearUVRotation();
                break;

            case 2: // Rotated 180 degrees
                renderer.setUVRotateTop(3);
                renderer.setUVRotateBottom(3);
                renderThis(renderer, block, x, y, z, stage, icon);
                renderer.clearUVRotation();
                break;

            case 3: // Rotated 270 degrees clockwise (or 90 degrees counterclockwise)
                renderer.setUVRotateTop(2);
                renderer.setUVRotateBottom(1);
                renderThis(renderer, block, x, y, z, stage, icon);
                renderer.clearUVRotation();
                break;
        }
    }

    private static void renderThis(RenderBlocks renderer, Block block, int x, int y, int z, int stage, Icon[] icon) {
        if (stage >= 0) {
            renderCornerSE(renderer, block, x, y, z, icon);
        }
        if (stage >= 1) {
            renderCornerNE(renderer, block, x, y, z, icon);
        }
        if (stage >= 2) {
            renderCornerNW(renderer, block, x, y, z, icon);
        }
        if (stage >= 3) {
            renderCornerSW(renderer, block, x, y, z, icon);
        }
    }

    public static void renderStalkFlowers(RenderBlocks renderer, Block block, int x, int y, int z, int stage, int dir, int[] numberOfStalks, double[][][] shifts, Icon[] icon)  {
        renderStalkFlowers(renderer, block, x, y, z, stage, dir, numberOfStalks, shifts, new double[] {3/16D, 3/16D} ,icon);
    }

    private static void renderStalkFlowers(RenderBlocks renderer, Block block, int x, int y, int z, int stage, int dir, int[] numberOfStalks, double[][][] shifts, double[] yShifts, Icon[] icon) {
        if (dir >= 0 && dir <= 3) {
            Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
            double snowShift = 0D;
            if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;

            if (stage >= 1) {
                for (int i = 0; i < numberOfStalks[1]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[1][i][0], shifts[1][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = -2/16D + yShifts[0] + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[0], false);
                }
            }

            if (stage >= 3) {
                for (int i = 0; i < numberOfStalks[3]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[3][i][0], shifts[3][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = -1/16D + yShifts[1] + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[1], false);
                }
            }
        }
    }

    public static void renderStalks(RenderBlocks renderer, Block block, int x, int y, int z, int stage, int dir, int[] numberOfStalks, double[][][] shifts, Icon[] icon) {
        if (dir >= 0 && dir <= 3) {
            Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
            double snowShift = 0D;
            if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;

            if (stage >= 0) {
                for (int i = 0; i < numberOfStalks[0]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[0][i][0], shifts[0][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = 0/16D + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[0], false);
                }
            }

            if (stage >= 1) {
                for (int i = 0; i < numberOfStalks[1]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[1][i][0], shifts[1][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = -2/16D + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[1], false);
                }
            }

            if (stage >= 2) {
                for (int i = 0; i < numberOfStalks[2]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[2][i][0], shifts[2][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = -1/16D + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[2], false);
                }
            }

            if (stage >= 3) {
                for (int i = 0; i < numberOfStalks[3]; i++) {
                    double[] rotatedShift = rotateAntiClockwise(shifts[3][i][0], shifts[3][i][1], dir);
                    double xShift = rotatedShift[0];
                    double zShift = rotatedShift[1];
                    double yShift = -1/16D + snowShift;
                    renderCrossedSquaresWithTexture(renderer, block, x, y, z, xShift, yShift, zShift, icon[3], false);
                }
            }
        }
    }

    public static double[] rotateClockwise(double xShift, double zShift, int dir) {
        for (int i = 0; i < dir; i++) {
            // Rotate 90 degrees clockwise: (x, z) -> (z, -x)
            double temp = xShift;
            xShift = zShift;
            zShift = -temp;
        }
        return new double[]{xShift, zShift};
    }

    public static double[] rotateAntiClockwise(double xShift, double zShift, int dir) {
        for (int i = 0; i < dir; i++) {
            // Rotate 90 degrees anti-clockwise: (x, z) -> (-z, x)
            double temp = xShift;
            xShift = -zShift;
            zShift = temp;
        }
        return new double[]{xShift, zShift};
    }

    private static void renderCornerSW(RenderBlocks renderer, Block block, int x, int y, int z, Icon[] icon) {
        Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
        double snowShift = 0D;
        if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;

        renderer.renderFaceYNeg(block, x, y + 2/16D + snowShift, z, icon[3]);
        renderer.renderFaceYPos(block, x, y - 14/16D + snowShift, z, icon[3]);
    }

    private static void renderCornerNW(RenderBlocks renderer, Block block, int x, int y, int z, Icon[] icon) {
        Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
        double snowShift = 0D;
        if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;

        renderer.renderFaceYNeg(block, x, y + 2/16D + snowShift, z, icon[2]);
        renderer.renderFaceYPos(block, x, y - 14/16D + snowShift, z, icon[2]);
    }

    private static void renderCornerNE(RenderBlocks renderer, Block block, int x, int y, int z, Icon[] icon) {
        Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
        double snowShift = 0D;
        if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;


        renderer.renderFaceYNeg(block, x, y + 1 / 16D + snowShift, z, icon[1]);
        renderer.renderFaceYPos(block, x, y - 15 / 16D + snowShift, z, icon[1]);
    }

    private static void renderCornerSE(RenderBlocks renderer, Block block, int x, int y, int z, Icon[] icon) {
        Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(x,y + 1,z)];
        double snowShift = 0D;
        if (blockAbove instanceof GroundCoverBlock) snowShift += groundShift;

        renderer.renderFaceYNeg(block, x, y + 3/16D + snowShift, z, icon[0]);
        renderer.renderFaceYPos(block, x, y - 13/16D + snowShift, z, icon[0]);
    }


    public static boolean renderCrossedSquares(RenderBlocks renderer, Block par1Block, int metadata, int par2, int par3, int par4, float xShift, float yShift, float zShift, float scale, boolean randomHeight)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(renderer.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;

        var19 += xShift;
        var20 += yShift;
        var15 += zShift;

        if (randomHeight)
        {
            long var17 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            var19 += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            var20 += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            var15 += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        }

        drawCrossedSquares(renderer, par1Block, metadata, var19, var20, var15, scale);
        return true;
    }

    private static void drawCrossedSquares(RenderBlocks renderer, Block par1Block, int par2, double par3, double par5, double par7, float par9)
    {
        Tessellator var10 = Tessellator.instance;
        Icon var11 = renderer.getBlockIconFromSideAndMetadata(par1Block, 0, par2);
        if (renderer.hasOverrideBlockTexture())
        {
            var11 = renderer.getOverrideTexture();
        }

        double var12 = (double)var11.getMinU();
        double var14 = (double)var11.getMinV();
        double var16 = (double)var11.getMaxU();
        double var18 = (double)var11.getMaxV();
        double var20 = 0.45D * (double)par9;
        double var22 = par3 + 0.5D - var20;
        double var24 = par3 + 0.5D + var20;
        double var26 = par7 + 0.5D - var20;
        double var28 = par7 + 0.5D + var20;
        var10.addVertexWithUV(var22, par5 + (double)par9, var26, var12, var14);
        var10.addVertexWithUV(var22, par5 + 0.0D, var26, var12, var18);
        var10.addVertexWithUV(var24, par5 + 0.0D, var28, var16, var18);
        var10.addVertexWithUV(var24, par5 + (double)par9, var28, var16, var14);
        var10.addVertexWithUV(var24, par5 + (double)par9, var28, var12, var14);
        var10.addVertexWithUV(var24, par5 + 0.0D, var28, var12, var18);
        var10.addVertexWithUV(var22, par5 + 0.0D, var26, var16, var18);
        var10.addVertexWithUV(var22, par5 + (double)par9, var26, var16, var14);
        var10.addVertexWithUV(var22, par5 + (double)par9, var28, var12, var14);
        var10.addVertexWithUV(var22, par5 + 0.0D, var28, var12, var18);
        var10.addVertexWithUV(var24, par5 + 0.0D, var26, var16, var18);
        var10.addVertexWithUV(var24, par5 + (double)par9, var26, var16, var14);
        var10.addVertexWithUV(var24, par5 + (double)par9, var26, var12, var14);
        var10.addVertexWithUV(var24, par5 + 0.0D, var26, var12, var18);
        var10.addVertexWithUV(var22, par5 + 0.0D, var28, var16, var18);
        var10.addVertexWithUV(var22, par5 + (double)par9, var28, var16, var14);
    }
}
