package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCUtilsRender {
	
    /**
     * If set to >=0, all block faces will be rendered using this texture index
     */
    private static Icon overrideBlockTexture = null;
	
	private static int color( int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8)
        {
        	for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = -7226023; //BiomeGrassColor
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }
        
        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}
	
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawCrossedSquares(RenderBlocks renderer, Block block, int meta, double x, double y, double z, float scale)
    {
        Tessellator var10 = Tessellator.instance;
        Icon var11 = renderer.blockAccess == null ? renderer.getBlockIconFromSideAndMetadata(block, 0, meta) : renderer.getBlockIcon(block, renderer.blockAccess, (int)Math.round(x), (int)Math.round(y), (int)Math.round(z), -1);

        if (renderer.hasOverrideBlockTexture())
        {
            var11 = renderer.GetOverrideTexture();
        }

        double var12 = (double)var11.getMinU();
        double var14 = (double)var11.getMinV();
        double var16 = (double)var11.getMaxU();
        double var18 = (double)var11.getMaxV();
        double var20 = 0.45D * (double)scale;
        double minX = x + 0.5D - var20;
        double maxX = x + 0.5D + var20;
        double minZ = z + 0.5D - var20;
        double maxZ = z + 0.5D + var20;
        
        minX = x + 0.495D - var20;
        maxX = x + 0.495D + var20;
        minZ = z + 0.5D - var20;
        maxZ = z + 0.5D + var20;
        
        var10.addVertexWithUV(minX, y + (double)scale, minZ, var12, var14);
        var10.addVertexWithUV(minX, y + 0.0D, minZ, var12, var18);
        
        var10.addVertexWithUV(maxX, y + 0.0D, maxZ, var16, var18);
        var10.addVertexWithUV(maxX, y + (double)scale, maxZ, var16, var14);
        
        var10.addVertexWithUV(maxX, y + (double)scale, maxZ, var12, var14);
        var10.addVertexWithUV(maxX, y + 0.0D, maxZ, var12, var18);
        
        var10.addVertexWithUV(minX, y + 0.0D, minZ, var16, var18);
        var10.addVertexWithUV(minX, y + (double)scale, minZ, var16, var14);
        
        var10.addVertexWithUV(minX, y + (double)scale, maxZ, var12, var14);
        var10.addVertexWithUV(minX, y + 0.0D, maxZ, var12, var18);
        
        var10.addVertexWithUV(maxX, y + 0.0D, minZ, var16, var18);
        var10.addVertexWithUV(maxX, y + (double)scale, minZ, var16, var14);
        
        var10.addVertexWithUV(maxX, y + (double)scale, minZ, var12, var14);
        var10.addVertexWithUV(maxX, y + 0.0D, minZ, var12, var18);
        
        var10.addVertexWithUV(minX, y + 0.0D, maxZ, var16, var18);
        var10.addVertexWithUV(minX, y + (double)scale, maxZ, var16, var14);
    }

	public static boolean renderBlockCropsAtAngleWithTexture(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, Icon texture, float adjustment)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        Block var6 = Block.blocksList[renderer.blockAccess.getBlockId(par2, par3 - 1, par4)];
        double var7 = 0.0D;
        double var11 = (double)par2;
        double var13 = (double)par3;
        double var15 = (double)par4;
        if (var6 != null)
        {
            var7 = (double)var6.GroundCoverRestingOnVisualOffset(renderer.blockAccess, par2, par3 - 1, par4);
        }

        drawBlockCropsAtAngleWithTexture(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F,
        		texture, adjustment);
   
        return true;
    }
	
	public static boolean renderBlockCropsWithAdjustmentWithTexture(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, Icon texture, float adjustment)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        Block var6 = Block.blocksList[renderer.blockAccess.getBlockId(par2, par3 - 1, par4)];
        double var7 = 0.0D;
        double var11 = (double)par2;
        double var13 = (double)par3;
        double var15 = (double)par4;
//        if (var6 != null)
//        {
//            var7 = (double)var6.GroundCoverRestingOnVisualOffset(renderer.blockAccess, par2, par3 - 1, par4);
//        }

        drawBlockCropsWithAdjustmentWithTexture(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F,
        		texture, adjustment);
   
        return true;
    }
	
	public static boolean renderBlockCropsAtAngleWithAdjustmentAndTexture(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, Icon texture,
			float topAdjustment, float bottomAdjustment, float adjustment)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        Block var6 = Block.blocksList[renderer.blockAccess.getBlockId(par2, par3 - 1, par4)];
        double var7 = 0.0D;
        double var11 = (double)par2;
        double var13 = (double)par3;
        double var15 = (double)par4;
//        if (var6 != null)
//        {
//            var7 = (double)var6.GroundCoverRestingOnVisualOffset(renderer.blockAccess, par2, par3 - 1, par4);
//        }

        drawBlockCropsAtAngleWithAdjustmentAndTexture(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F,
        		texture, topAdjustment, bottomAdjustment, adjustment);
   
        return true;
    }
	
	public static void drawBlockCropsAtAngleWithAdjustmentAndTexture(RenderBlocks renderer, Block block, int meta, double x, double y, double z, float par9, Icon texture,
			float topAdjustment, float bottomAdjustment, float widthAdjustment)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon icon = texture;

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double minX = x + 0.5D - 0.25D - widthAdjustment; 
        double maxX = x + 0.5D + 0.25D + widthAdjustment;
        double minZ = z + 0.5D - 0.5D;
        double maxZ = z + 0.5D + 0.5D;
        
        double minXBottom = minX - bottomAdjustment;
        double minZBottom = minZ - bottomAdjustment;        
        double maxXBottom = maxX + bottomAdjustment;
        double maxZBottom = maxZ + bottomAdjustment;
        
        double minXTop = minX - topAdjustment;
        double minZTop = minZ - topAdjustment;
        double maxXTop = maxX + topAdjustment;
        double maxZTop = maxZ + topAdjustment;


        var9.addVertexWithUV(minXTop, y + 1.0D, minZTop, minU, minV);
        var9.addVertexWithUV(minXBottom , y + 0.0D, minZBottom, minU, maxV);
        
        var9.addVertexWithUV(minXBottom , y + 0.0D, minZBottom, maxU, maxV);
        var9.addVertexWithUV(minXTop, y + 1.0D, maxZTop, maxU, minV);
        
        var9.addVertexWithUV(minXTop, y + 1.0D, minZTop, minU, minV);
        var9.addVertexWithUV(minX , y + 0.0D, maxZBottom, minU, maxV);
        
        var9.addVertexWithUV(minXBottom , y + 0.0D, minZBottom, maxU, maxV);
        var9.addVertexWithUV(minXTop, y + 1.0D, minZTop, maxU, minV);
        
        var9.addVertexWithUV(maxXTop , y + 1.0D, maxZTop, minU, minV);
        var9.addVertexWithUV(maxXBottom, y + 0.0D, maxZBottom, minU, maxV);
        
        var9.addVertexWithUV(maxXBottom, y + 0.0D, minZBottom, maxU, maxV);
        var9.addVertexWithUV(maxXTop , y + 1.0D, minZTop, maxU, minV);
        
        var9.addVertexWithUV(maxXTop , y + 1.0D, minZTop, minU, minV);
        var9.addVertexWithUV(maxXBottom, y + 0.0D, minZBottom, minU, maxV);
        
        var9.addVertexWithUV(maxXBottom, y + 0.0D, maxZBottom, maxU, maxV);
        var9.addVertexWithUV(maxXTop , y + 1.0D, maxZTop, maxU, minV);
        
        minX = x + 0.5D - 0.5D;
        maxX = x + 0.5D + 0.5D;
        minZ = z + 0.5D - 0.25D - widthAdjustment;
        maxZ = z + 0.5D + 0.25D + widthAdjustment;
        
        minXBottom = minX - bottomAdjustment;
        minZBottom = minZ - bottomAdjustment;        
        maxXBottom = maxX + bottomAdjustment;
        maxZBottom = maxZ + bottomAdjustment;
        
        minXTop = minX - topAdjustment;
        minZTop = minZ - topAdjustment;
        maxXTop = maxX + topAdjustment;
        maxZTop = maxZ + topAdjustment;
        
//        var9.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
//        var9.addVertexWithUV(minX, y + 0.0D, minZ , minU, maxV);
//        
//        var9.addVertexWithUV(maxX, y + 0.0D, minZ , maxU, maxV);
//        var9.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
//        
//        var9.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
//        var9.addVertexWithUV(maxX, y + 0.0D, minZ , minU, maxV);
//        
//        var9.addVertexWithUV(minX, y + 0.0D, minZ , maxU, maxV);
//        var9.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
//        
//        var9.addVertexWithUV(maxX, y + 1.0D, maxZ , minU, minV);
//        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
//        
//        var9.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
//        var9.addVertexWithUV(minX, y + 1.0D, maxZ , maxU, minV);
//        
//        var9.addVertexWithUV(minX, y + 1.0D, maxZ , minU, minV);
//        var9.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
//        
//        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
//        var9.addVertexWithUV(maxX, y + 1.0D, maxZ , maxU, minV); 
    }
	
	public static void drawBlockCropsWithAdjustmentWithTexture(RenderBlocks renderer, Block block, int meta, double x, double y, double z, float par9, Icon texture, float adjustment)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon icon = texture;

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double minX = x + 0.5D - 0.25D; 
        double maxX = x + 0.5D + 0.25D;
        double minZ = z + 0.5D - 0.5D;
        double maxZ = z + 0.5D + 0.5D;
        
        minX -= adjustment;
        maxX += adjustment;

        var9.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(minX , y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(minX , y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, maxZ, maxU, minV);
        
        var9.addVertexWithUV(minX, y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(minX , y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(minX , y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX , y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(maxX , y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX , y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(maxX , y + 1.0D, maxZ, maxU, minV);
        
        minX = x + 0.5D - 0.5D;
        maxX = x + 0.5D + 0.5D;
        minZ = z + 0.5D - 0.25D;
        maxZ = z + 0.5D + 0.25D;
        
        minZ -= adjustment;
        maxZ += adjustment;
        
        var9.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, minZ , minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, minZ , maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ , minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, minZ , maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ , minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, maxZ , maxU, minV);
        
        var9.addVertexWithUV(minX, y + 1.0D, maxZ , minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ , maxU, minV); 
    }
	
	public static void drawBlockCropsAtAngleWithTexture(RenderBlocks renderer, Block block, int meta, double x, double y, double z, float par9, Icon texture, float adjustment)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon icon = texture;

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double minX = x + 0.5D - 0.125D; 
        double maxX = x + 0.5D + 0.125D;
        double minZ = z + 0.5D - 0.5D;
        double maxZ = z + 0.5D + 0.5D;
        //double adjustment = 0.0; //3px
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, maxZ, maxU, minV);
        
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, maxZ, maxU, minV);
        
        minX = x + 0.5D - 0.5D;
        maxX = x + 0.5D + 0.5D;
        minZ = z + 0.5D - 0.125D;
        maxZ = z + 0.5D + 0.125D;
        
        var9.addVertexWithUV(minX, y + 1.0D, minZ - adjustment, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, minZ + adjustment, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, minZ + adjustment, maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, minZ - adjustment, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, minZ - adjustment, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ + adjustment, minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, minZ + adjustment, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, minZ - adjustment, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ + adjustment, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ - adjustment, minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, maxZ - adjustment, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, maxZ + adjustment, maxU, minV);
        
        var9.addVertexWithUV(minX, y + 1.0D, maxZ + adjustment, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, maxZ - adjustment, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ - adjustment, maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ + adjustment, maxU, minV); 
    }
	
	public static void renderCrossedSquaresWithVerticalOffset(RenderBlocks renderer, Block block, int x, int y, int z, Icon icon, double offset)
	{
		renderer.setOverrideBlockTexture( icon );

        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int multi = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(multi >> 16 & 255) / 255.0F;
        float var9 = (float)(multi >> 8 & 255) / 255.0F;
        float var10 = (float)(multi & 255) / 255.0F;
        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
                                
		renderer.drawCrossedSquares(block, renderer.blockAccess.getBlockMetadata(x, y, z), x, y + offset, z, 1.0F);
        
        renderer.clearOverrideBlockTexture();
		
	}
	
	public static void renderCrossedSquaresWithTexture(RenderBlocks renderer, Block block, int x, int y, int z, Icon icon, boolean offset)
	{
		renderer.setOverrideBlockTexture( icon );

        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int multi = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(multi >> 16 & 255) / 255.0F;
        float var9 = (float)(multi >> 8 & 255) / 255.0F;
        float var10 = (float)(multi & 255) / 255.0F;
        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
                        
        double newX = (double)x;
        double newY = (double)y;
        double newZ = (double)z;
        
        double newCenterX = (double)x;
        double newCenterZ = (double)z;
		
        if (offset)
        {
            long hash = (long)(x * 3129871) ^ (long)z * 116129781L; // ^ (long)y;
            hash = hash * hash * 42317861L + hash * 11L;
            newX += ((double)((float)(hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            newY += ((double)((float)(hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            newZ += ((double)((float)(hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
                		
    		double c = 0.0D;
    		
        	newCenterX = ( Math.floor( newX * 16 + c ) - c ) / 16;
        	newCenterZ = ( Math.floor( newZ * 16 + c ) - c ) / 16;
        }
        
		renderer.drawCrossedSquares(block, renderer.blockAccess.getBlockMetadata(x, y, z), newCenterX, y, newCenterZ, 1.0F);
        
        renderer.clearOverrideBlockTexture();
		
	}
	
	public static void renderPlanterContentsAsItem(RenderBlocks renderer, Block block, int iItemDamage, float brightness, int nutritionLevel, Icon topGrass, Icon topGrassSparseDirt, Icon topGrassSparse) {
		Tessellator tess = Tessellator.instance;
//        boolean isBlock = block.blockID == SCDefs.planterGrass.blockID;//TODO: uncomment when adding new planters

        int var6;
        float var7;
        float var8;
        float var9;
        
        renderer.setRenderBoundsFromBlock(block);
        int var14;
        
        renderer.setRenderBounds( block.GetBlockBoundsFromPoolForItemRender( iItemDamage ) );
        
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5001F, -0.5F);
        
        
        if (nutritionLevel == 2) var14 = color(200, -25, 0);
        else if (nutritionLevel == 1)  var14 = color(350, -50, 0);
        else if (nutritionLevel == 0)  var14 = color(500, -200, 0);
        else var14 = color(0, 0, 0);
        
        if (iItemDamage <= 3)
        {
            var8 = (float)(var14 >> 16 & 255) / 255.0F;
            var9 = (float)(var14 >> 8 & 255) / 255.0F;
            float var10 = (float)(var14 & 255) / 255.0F;
            GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
            
            tess.startDrawingQuads();
            tess.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, topGrass);
            tess.draw();
        }
        else
        {
            tess.startDrawingQuads();
            tess.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, topGrassSparseDirt);
            tess.draw();
            
            var8 = (float)(var14 >> 16 & 255) / 255.0F;
            var9 = (float)(var14 >> 8 & 255) / 255.0F;
            float var10 = (float)(var14 & 255) / 255.0F;
            GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
            
            tess.startDrawingQuads();
            tess.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, topGrassSparse);
            tess.draw();
        }
	}
	
	public static void renderBiomeGrassAsItem(RenderBlocks renderer, Block block, int iItemDamage, float brightness, int type, 
			Icon[] grassTop, Icon[] grassSide, Icon[] dirt) {
		Tessellator tess = Tessellator.instance;
        boolean isBlock = block.blockID == SCDefs.biomeGrassDirt.blockID;

        int var6;
        float var7;
        float var8;
        float var9;
        
        renderer.setRenderBoundsFromBlock(block);
        renderer.setRenderBounds( block.GetBlockBoundsFromPoolForItemRender( iItemDamage ) );
        
        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        if (block == SCDefs.biomeGrassDirt)
        {
            if (iItemDamage < 8)
            {       
            	GL11.glColor4f(brightness, brightness, brightness, 1.0F);
                
            	tess.startDrawingQuads();
                tess.setNormal(0.0F, 1.0F, 0.0F);
                renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, dirt[type]);
                tess.draw();
            }
        }
        else
        {
        	GL11.glColor4f(brightness, brightness, brightness, 1.0F);
            
        	tess.startDrawingQuads();
            tess.setNormal(0.0F, 1.0F, 0.0F);
            renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, dirt[type]);
            tess.draw();
        }
        

                
        int color = SCBlockBiomeGrassBase.getBiomeColor(iItemDamage);
        
        var8 = (float)(color >> 16 & 255) / 255.0F;
        var9 = (float)(color >> 8 & 255) / 255.0F;
        float var10 = (float)(color & 255) / 255.0F;
        
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
       
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, grassTop[type]);
        tess.draw();

        GL11.glColor4f(brightness, brightness, brightness, 1.0F);
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirt[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, dirt[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, dirt[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, dirt[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, dirt[type]);
        tess.draw();
        
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
        
        //Sides
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, grassSide[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, grassSide[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, grassSide[type]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, grassSide[type]);
        tess.draw();
        
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}


	public static void RenderCrossedSquaresWithTextureAndOffset( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean offset)
    {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
        
        if ( !bHasOverride )
        {
        	renderBlocks.setOverrideBlockTexture( texture );
        }
        
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(block.getMixedBrightnessForBlock(renderBlocks.blockAccess, i, j, k));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderBlocks.blockAccess, i, j, k);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double newX = (double)i;
        double newY = (double)j;
        double newZ = (double)k;
        
        double newCenterX = (double)i;
        double newCenterZ = (double)k;
        
        
        if (offset == true)
        {
            long hash = (long)(i * 3129871) ^ (long)k * 116129781L; // ^ (long)j;
            hash = hash * hash * 42317861L + hash * 11L;
            newX += ((double)((float)(hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            newY += ((double)((float)(hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            newZ += ((double)((float)(hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
            
    		double centerX = 8/16D;
    		double centerZ = 8/16D;
    		
    		double c = 0.0D;
    		
        	newCenterX = ( Math.floor( newX * 16 + c ) - c ) / 16;
        	newCenterZ = ( Math.floor( newZ * 16 + c ) - c ) / 16;
        }

        renderBlocks.drawCrossedSquares(block, renderBlocks.blockAccess.getBlockMetadata(i, j, k), newCenterX, j, newCenterZ, 1.0F);
        
        if ( !bHasOverride )
        {
        	renderBlocks.clearOverrideBlockTexture();
        }
    }

	public static void renderGrassBlockAsItem(RenderBlocks renderer, Block block, int meta, float brightness, Icon[] dirtIcon, Icon grassTop, Icon grassSide)
	{
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
        
        renderer.setRenderBounds( block.GetBlockBoundsFromPoolForItemRender( meta ) );

        GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);
        if (meta == 2) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 4) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 6) renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
       
        tess.draw();
        
        var14 = color(0, 0, 0);
        if (meta == 2) var14 = color(200, -25, 0);
        if (meta == 4) var14 = color(400, -50, 0);
        if (meta == 6) var14 = color(600, -100, 0);
        
        var8 = (float)(var14 >> 16 & 255) / 255.0F;
        var9 = (float)(var14 >> 8 & 255) / 255.0F;
        float var10 = (float)(var14 & 255) / 255.0F;
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);

        tess.startDrawingQuads();
        tess.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, grassTop);
        tess.draw();

        GL11.glColor4f(brightness, brightness, brightness, 1.0F);
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);
        if (meta == 2) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 4) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 6) renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
        	
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);
        if (meta == 2) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 4) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 6) renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);
        if (meta == 2) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 4) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 6) renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
        tess.draw();
        
        tess.startDrawingQuads();
        tess.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[3]);
        if (meta == 2) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[2]);
        if (meta == 4) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[1]);
        if (meta == 6) renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, dirtIcon[0]);
        tess.draw();
        
        int varColor = color(0, 0, 0);
        if (meta == 2) varColor = color(200, -25, 0);
        if (meta == 4) varColor = color(400, -50, 0);
        if (meta == 6) varColor = color(600, -100, 0);
        
        float var77 = (float)(varColor >> 16 & 255) / 255.0F;
        float var88 = (float)(varColor >> 8 & 255) / 255.0F;
        float var99 = (float)(varColor & 255) / 255.0F;
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
	
    public static boolean renderBlockSaladWithTexture(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, Icon icon)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        Block var6 = Block.blocksList[renderer.blockAccess.getBlockId(par2, par3 - 1, par4)];
        double var7 = 0.0D;
        double var11 = (double)par2;
        double var13 = (double)par3;
        double var15 = (double)par4;
        if (var6 != null)
        {
            var7 = (double)var6.GroundCoverRestingOnVisualOffset(renderer.blockAccess, par2, par3 - 1, par4);
        }

        drawBlockSaladWithTexture(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F, icon);
   
        return true;
    }
    
    public static void drawBlockSaladWithTexture(RenderBlocks renderer, Block par1Block, int par2, double x, double par5, double z, float par9, Icon icon)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon var10 = icon;
        
        double var11 = (double)var10.getMinU();
        double var13 = (double)var10.getMinV();
        double var15 = (double)var10.getMaxU();
        double var17 = (double)var10.getMaxV();
        double var19 = x + 0.5D - 0.25D; 
        double var21 = x + 0.5D + 0.25D;
        double var23 = z + 0.5D - 0.5D;
        double var25 = z + 0.5D + 0.5D;
        double adjustment = 0.1875D; //3px
        var9.addVertexWithUV(var19 - adjustment, par5 + 1.0D, var23, var11, var13);
        var9.addVertexWithUV(var19 + adjustment, par5 + 0.0D, var23, var11, var17);
        
        var9.addVertexWithUV(var19 + adjustment, par5 + 0.0D, var25, var15, var17);
        var9.addVertexWithUV(var19 - adjustment, par5 + 1.0D, var25, var15, var13);
        
        var9.addVertexWithUV(var19 - adjustment, par5 + 1.0D, var25, var11, var13);
        var9.addVertexWithUV(var19 + adjustment, par5 + 0.0D, var25, var11, var17);
        
        var9.addVertexWithUV(var19 + adjustment, par5 + 0.0D, var23, var15, var17);
        var9.addVertexWithUV(var19 - adjustment, par5 + 1.0D, var23, var15, var13);
        
        var9.addVertexWithUV(var21 + adjustment, par5 + 1.0D, var25, var11, var13);
        var9.addVertexWithUV(var21 - adjustment, par5 + 0.0D, var25, var11, var17);
        
        var9.addVertexWithUV(var21 - adjustment, par5 + 0.0D, var23, var15, var17);
        var9.addVertexWithUV(var21 + adjustment, par5 + 1.0D, var23, var15, var13);
        
        var9.addVertexWithUV(var21 + adjustment, par5 + 1.0D, var23, var11, var13);
        var9.addVertexWithUV(var21 - adjustment, par5 + 0.0D, var23, var11, var17);
        
        var9.addVertexWithUV(var21 - adjustment, par5 + 0.0D, var25, var15, var17);
        var9.addVertexWithUV(var21 + adjustment, par5 + 1.0D, var25, var15, var13);
        
        var19 = x + 0.5D - 0.5D;
        var21 = x + 0.5D + 0.5D;
        var23 = z + 0.5D - 0.25D;
        var25 = z + 0.5D + 0.25D;
        
        var9.addVertexWithUV(var19, par5 + 1.0D, var23 - adjustment, var11, var13);
        var9.addVertexWithUV(var19, par5 + 0.0D, var23 + adjustment, var11, var17);
        
        var9.addVertexWithUV(var21, par5 + 0.0D, var23 + adjustment, var15, var17);
        var9.addVertexWithUV(var21, par5 + 1.0D, var23 - adjustment, var15, var13);
        
        var9.addVertexWithUV(var21, par5 + 1.0D, var23 - adjustment, var11, var13);
        var9.addVertexWithUV(var21, par5 + 0.0D, var23 + adjustment, var11, var17);
        
        var9.addVertexWithUV(var19, par5 + 0.0D, var23 + adjustment, var15, var17);
        var9.addVertexWithUV(var19, par5 + 1.0D, var23 - adjustment, var15, var13);
        
        var9.addVertexWithUV(var21, par5 + 1.0D, var25 + adjustment, var11, var13);
        var9.addVertexWithUV(var21, par5 + 0.0D, var25 - adjustment, var11, var17);
        
        var9.addVertexWithUV(var19, par5 + 0.0D, var25 - adjustment, var15, var17);
        var9.addVertexWithUV(var19, par5 + 1.0D, var25 + adjustment, var15, var13);
        
        var9.addVertexWithUV(var19, par5 + 1.0D, var25 + adjustment, var11, var13);
        var9.addVertexWithUV(var19, par5 + 0.0D, var25 - adjustment, var11, var17);
        
        var9.addVertexWithUV(var21, par5 + 0.0D, var25 - adjustment, var15, var17);
        var9.addVertexWithUV(var21, par5 + 1.0D, var25 + adjustment, var15, var13); 
    }
    
    public static boolean renderBlockCloverWithTexture(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4, Icon texture)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        var5.setColorOpaque_F(1.0F, 1.0F, 1.0F);
        Block var6 = Block.blocksList[renderer.blockAccess.getBlockId(par2, par3 - 1, par4)];
        double var7 = 0.0D;
        double var11 = (double)par2;
        double var13 = (double)par3;
        double var15 = (double)par4;
        if (var6 != null)
        {
            var7 = (double)var6.GroundCoverRestingOnVisualOffset(renderer.blockAccess, par2, par3 - 1, par4);
        }

        drawBlockClover(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F, texture);
   
        return true;
    }
    
    public static void drawBlockClover(RenderBlocks renderer, Block block, int par2, double x, double y, double z, float par9, Icon texture)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon icon = texture;

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double minX = x + 0.5D - 0.25D; 
        double maxX = x + 0.5D + 0.25D;
        double minZ = z + 0.5D - 0.5D;
        double maxZ = z + 0.5D + 0.5D;
        double adjustment = 0.0; //3px
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, maxZ, maxU, minV);
        
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(minX + adjustment, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(minX - adjustment, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, maxZ, minU, minV);
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, maxZ, minU, maxV);
        
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, minZ, maxU, maxV);
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, minZ, maxU, minV);
        
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, minZ, minU, minV);
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, minZ, minU, maxV);
        
        var9.addVertexWithUV(maxX - adjustment, y + 0.0D, maxZ, maxU, maxV);
        var9.addVertexWithUV(maxX + adjustment, y + 1.0D, maxZ, maxU, minV);
        
        minX = x + 0.5D - 0.5D;
        maxX = x + 0.5D + 0.5D;
        minZ = z + 0.5D - 0.25D;
        maxZ = z + 0.5D + 0.25D;
        
        var9.addVertexWithUV(minX, y + 1.0D, minZ - adjustment, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, minZ + adjustment, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, minZ + adjustment, maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, minZ - adjustment, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, minZ - adjustment, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, minZ + adjustment, minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, minZ + adjustment, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, minZ - adjustment, maxU, minV);
        
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ + adjustment, minU, minV);
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ - adjustment, minU, maxV);
        
        var9.addVertexWithUV(minX, y + 0.0D, maxZ - adjustment, maxU, maxV);
        var9.addVertexWithUV(minX, y + 1.0D, maxZ + adjustment, maxU, minV);
        
        var9.addVertexWithUV(minX, y + 1.0D, maxZ + adjustment, minU, minV);
        var9.addVertexWithUV(minX, y + 0.0D, maxZ - adjustment, minU, maxV);
        
        var9.addVertexWithUV(maxX, y + 0.0D, maxZ - adjustment, maxU, maxV);
        var9.addVertexWithUV(maxX, y + 1.0D, maxZ + adjustment, maxU, minV); 
    }

    public static void renderCrossedSquaresFlowers(RenderBlocks render, Block block, int meta,
			double x, double y, double z, float yHeight, int facing, int slot)
	{

		renderCrossedSquaresBigFlowerPot(render, block, meta, x, y, z,yHeight, facing, false);

		boolean isTopBlock = block.blockID == SCDefs.doubleTallGrass.blockID;
		
		if (isTopBlock)
		{			
			renderCrossedSquaresBigFlowerPot(render, block, meta, x, y, z, yHeight, facing, true);
		}
		
	}
	
	public static void renderCrossedSquaresBigFlowerPot(RenderBlocks render, Block block, int meta,
			double x, double y, double z, float yHeight, int facing, boolean isTopBlock) {
        Tessellator tesselator = Tessellator.instance;
        Icon blockIcon = render.getBlockIconFromSideAndMetadata(block, 0, meta);
        
        if (block.blockID == SCDefs.doubleTallGrass.blockID)
        {
            blockIcon = render.getBlockIconFromSideAndMetadata(block, 0, meta);

            if (isTopBlock)
            {
            	blockIcon = render.getBlockIconFromSideAndMetadata(block, 1, meta + 8);
            }
            
            yHeight = yHeight / (yHeight*2);
        }
        
        int blockMultiplier = block.colorMultiplier(render.blockAccess,(int)x, (int)y, (int)z);
        if (render.hasOverrideBlockTexture())
        {
            blockIcon = render.GetOverrideTexture();
        }
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(render.blockAccess, (int)x, (int)y, (int)z);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        float var12;
        float var14;
        
		if (block.blockID == Block.tallGrass.blockID || block.blockID == SCDefs.doubleTallGrass.blockID)
		{
            var7 = render.blockAccess.getBiomeGenForCoords((int)x, (int)y).getBiomeGrassColor();
                        
            var9 = (float)(var7 >> 16 & 255) / 255.0F;
            var10 = (float)(var7 >> 8 & 255) / 255.0F;
            var11 = (float)(var7 & 255) / 255.0F;
            tesselator.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
		}
        
        double minU = (double)blockIcon.getMinU();
        double minV = (double)blockIcon.getMinV();
        double maxU = (double)blockIcon.getMaxU();
        double maxV = (double)blockIcon.getMaxV();

        double diagonalOffset = 0.45D * (double)yHeight;
        double minX = 0.5D - diagonalOffset;
        double maxX = 0.5D + diagonalOffset;
        double minY = 0;
        double maxY = yHeight;
        double minZ = 0.5D - diagonalOffset;
        double maxZ = 0.5D + diagonalOffset;
        
        double tempX;
        double tempY;
        double tempZ; 
        
        if (isTopBlock)
        {
            minY = yHeight;
            maxY = yHeight + yHeight;
        }
        
        switch (facing) {
        case 0: //Down
        	//Swap min and max
        	tempX = minX;
            tempY = minY;
            tempZ = minZ;
            
        	minX = maxX;
        	minY = maxY;
        	minZ = maxZ;
        	
        	maxX = tempX;
        	maxY = tempY;
        	maxZ = tempZ;
        default:
        case 1: //Up
            minX += x;
            maxX += x;
            minY += y;
            maxY += y;
            minZ += z;
            maxZ += z;
            
        	tesselator.addVertexWithUV(minX, maxY, minZ, minU, minV);
            tesselator.addVertexWithUV(minX, minY, minZ, minU, maxV);
            tesselator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
            tesselator.addVertexWithUV(maxX, maxY, maxZ, maxU, minV);
            
            tesselator.addVertexWithUV(maxX, maxY, maxZ, minU, minV);
            tesselator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
            tesselator.addVertexWithUV(minX, minY, minZ, maxU, maxV);
            tesselator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
            
            tesselator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
            tesselator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
            tesselator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
            tesselator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
            
            tesselator.addVertexWithUV(maxX, maxY, minZ, minU, minV);
            tesselator.addVertexWithUV(maxX, minY, minZ, minU, maxV);
            tesselator.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
            tesselator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
            
        	break;
        case 3: //South
        	//Swap min and max
        	tempX = minX;
            tempY = minY;
            tempZ = minZ;
            
        	minX = maxX;
        	minY = maxY;
        	minZ = maxZ;
        	
        	maxX = tempX;
        	maxY = tempY;
        	maxZ = tempZ;
        case 2: //North
        	//Swap Y and Z
        	tempZ = minZ;
        	minZ = minY;
        	minY = tempZ;
        	
        	tempZ = maxZ;
        	maxZ = maxY;
        	maxY = tempZ;
        	
            minX += x;
            maxX += x;
            minY += y;
            maxY += y;
            minZ += z;
            maxZ += z;
            
        	tesselator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        	tesselator.addVertexWithUV(minX, maxY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(maxX, minY, minZ, maxU, minV);

        	tesselator.addVertexWithUV(maxX, minY, minZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(minX, maxY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
            
        	tesselator.addVertexWithUV(maxX, maxY, minZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, maxY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(minX, minY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, minY, minZ, maxU, minV);

        	tesselator.addVertexWithUV(minX, minY, minZ, minU, minV);
        	tesselator.addVertexWithUV(minX, minY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(maxX, maxY, minZ, maxU, minV);
        	
        	break;
        case 5: //West
        	//Swap min and max
        	tempX = minX;
            tempY = minY;
            tempZ = minZ;
            
        	minX = maxX;
        	minY = maxY;
        	minZ = maxZ;
        	
        	maxX = tempX;
        	maxY = tempY;
        	maxZ = tempZ;
        case 4: //East
        	//Swap X and Y
        	tempX = minX;
        	minX = minY;
        	minY = tempX;
        	
        	tempX = maxX;
        	maxX = maxY;
        	maxY = tempX;
        	
            minX += x;
            maxX += x;
            minY += y;
            maxY += y;
            minZ += z;
            maxZ += z;
            
        	tesselator.addVertexWithUV(minX, maxY, minZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, maxY, minZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, minY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, minY, maxZ, maxU, minV);

        	tesselator.addVertexWithUV(minX, minY, maxZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, minY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, maxY, minZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, maxY, minZ, maxU, minV);
            
        	tesselator.addVertexWithUV(minX, maxY, maxZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, maxY, maxZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, minY, minZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, minY, minZ, maxU, minV);

        	tesselator.addVertexWithUV(minX, minY, minZ, minU, minV);
        	tesselator.addVertexWithUV(maxX, minY, minZ, minU, maxV);
        	tesselator.addVertexWithUV(maxX, maxY, maxZ, maxU, maxV);
        	tesselator.addVertexWithUV(minX, maxY, maxZ, maxU, minV);
        	
        	break;
        }
    }

	public static boolean renderCoconutConnector(RenderBlocks render, Block block, int par2, int par3, int par4, Icon icon)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(block.getMixedBrightnessForBlock(render.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(render.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        // FCMOD: Code removed
        /*
        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        */
        // END FCMOD

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;

        drawConnector(render, block, render.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F, icon);
		
		return true;
    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawConnector(RenderBlocks render, Block block, int meta, double x, double y, double z, float scale, Icon icon)
    {
        Tessellator tess = Tessellator.instance;
        
//        Icon icon = render.getBlockIconFromSideAndMetadata(block, 0, meta);
//
//        if (render.hasOverrideBlockTexture())
//        {
//        	icon = overrideBlockTexture;
//        }
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = 0.45D * (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0D;// - var20;
        double maxZ = z + 1D;// + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0D;// - var20;
            maxX = x + 1D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0D;// - var20;
            maxZ = z + 1D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0D;// - var20;
            maxX = x + 1D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (double)scale, minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (double)scale, minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (double)scale, minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (double)scale, minZ, maxU, minV);
        }
    }
    
    public static void renderSunflowerPlaneWithTexturesAndRotation( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, double height, int rotation)
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderFrontSideFlowerWithRotation(renderBlocks, block, i, j, k, texture, height, rotation);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderBackSunflowerPlaneWithTexturesAndRotation( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, double height, int rotation)
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderBackSideFlowerWithRotation(renderBlocks, block, i, j, k, texture, height, rotation);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderSunflowerPlaneWithTextures( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, double height)
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderFrontSideFlower(renderBlocks, i, j, k, texture, height);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderBackSunflowerPlaneWithTextures( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, double height)
    {
    	 boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
         
         if ( !bHasOverride )
         {
         	renderBlocks.setOverrideBlockTexture( texture );
         }
         
         renderBackSideFlower(renderBlocks, i, j, k, texture, height);
         
         if ( !bHasOverride )
         {
         	renderBlocks.clearOverrideBlockTexture();
         }
	}
    
    public static void renderFrontSideFlowerWithRotation(RenderBlocks renderer, Block block, int x, int y, int z, Icon texture, double height, int rotation)
    {
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        
        Icon icon = texture;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        
        double minX2 = x + 0.5D + (0.125/2);
        double maxX2 = x + 0.5D + (0.125/2);
        
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D + height;
        
        double xShift = 0.25D;
        double yShiftMax = 1D;
        double yShiftMin = 0D;
        
        if (rotation == 1)
        {
        	yShiftMax = 0.75D;
            yShiftMin = 0.25D;
            xShift = 0.5D;
        }
        else if (rotation == 2)
        {
        	minX = x + 0.5D - (0.125/2);
        	maxX = x + 0.5D - (0.125/2);
        	
        	yShiftMax = 0.75D;
            yShiftMin = 0.25D;
            xShift = -0.5D;
        }
        else if (rotation == 3)
        {
        	minX = x + 0.5D - (0.125/2);
        	maxX = x + 0.5D - (0.125/2);
        	
        	yShiftMax = 1D;
            yShiftMin = 0D;
            
            xShift = -0.25D;
        }
        
        tess.addVertexWithUV(maxX - xShift, y + yShiftMax + yUp, maxZ, maxU, minV);
        
        tess.addVertexWithUV(maxX + xShift, y + yShiftMin + yUp, maxZ, maxU, maxV);
        tess.addVertexWithUV(minX + xShift, y + yShiftMin + yUp, minZ, minU, maxV);
        
        tess.addVertexWithUV(minX - xShift , y + yShiftMax + yUp, minZ, minU, minV);
		
    }

    public static void renderBackSideFlowerWithRotation(RenderBlocks renderer, Block block, int x, int y, int z, Icon texture, double height, int rotation)
    {
        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        
        Icon icon = texture;

        
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D + height;

        double xShift = 0.25D;
        double yShiftMax = 1D;
        double yShiftMin = 0D;
        
        if (rotation == 1)
        {
        	yShiftMax = 0.75D;
            yShiftMin = 0.25D;
            xShift = 0.5D;
        }
        else if (rotation == 2)
        {
        	minX = x + 0.5D - (0.125/2);
        	maxX = x + 0.5D - (0.125/2);
        	
        	yShiftMax = 0.75D;
            yShiftMin = 0.25D;
            xShift = -0.5D;
        }
        else if (rotation == 3)
        {
        	minX = x + 0.5D - (0.125/2);
        	maxX = x + 0.5D - (0.125/2);
        	
        	yShiftMax = 1D;
            yShiftMin = 0D;
            
            xShift = -0.25D;
        }
        
        tess.addVertexWithUV(minX - xShift, y + yShiftMax + yUp, minZ, minU, minV);
        
        tess.addVertexWithUV(minX + xShift, y + yShiftMin + yUp, minZ , minU, maxV);
        tess.addVertexWithUV(maxX + xShift, y + yShiftMin + yUp, maxZ , maxU, maxV);
        
        tess.addVertexWithUV(maxX - xShift, y + yShiftMax + yUp, maxZ , maxU, minV);
		
    }
    
    public static void renderFrontSideFlower(RenderBlocks renderer, int x, int y, int z, Icon texture, double height)
    {
    	Tessellator tess = Tessellator.instance;
        
        Icon icon = texture;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D + height;
        
        tess.addVertexWithUV(maxX  - 0.25, y + 1D + yUp, maxZ, maxU, minV);
        
        tess.addVertexWithUV(maxX + 0.25, y + 0D + yUp, maxZ, maxU, maxV);
        tess.addVertexWithUV(minX + 0.25, y + 0D + yUp, minZ, minU, maxV);
        
        tess.addVertexWithUV(minX - 0.25 , y + 1D + yUp, minZ, minU, minV);
		
    }

    public static void renderBackSideFlower(RenderBlocks renderer, int x, int y, int z, Icon texture, double height)
    {
        Tessellator tess = Tessellator.instance;
        
        Icon icon = texture;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double minX = x + 0.5D + (0.125/2);
        double maxX = x + 0.5D + (0.125/2);
        double minZ = z + 0D;
        double maxZ = z + 1D;
        
        double yUp = 0.125D + height;

        tess.addVertexWithUV(minX  - 0.25, y + 1D + yUp, minZ, minU, minV);
        
        tess.addVertexWithUV(minX + 0.25, y + 0D + yUp, minZ , minU, maxV);
        tess.addVertexWithUV(maxX + 0.25, y + 0D + yUp, maxZ , maxU, maxV);
        
        tess.addVertexWithUV(maxX - 0.25, y + 1D + yUp, maxZ , maxU, minV);
		
    }

    /**
     * Renders any block requiring croseed squares such as reeds, flowers, and mushrooms
     */
    public static boolean renderNorthCrossedSquares(RenderBlocks render, Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(render.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(render.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        // FCMOD: Code removed
        /*
        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        */
        // END FCMOD

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;

        if (par1Block == Block.tallGrass)
        {
            long var17 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            var19 += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            var20 += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            var15 += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        }

        drawHorizontalNorthSquares(render, par1Block, render.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);
        return true;
    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawHorizontalNorthSquares(RenderBlocks render, Block block, int meta, double x, double y, double z, float scale)
    {
        Tessellator tess = Tessellator.instance;
        Icon icon = render.blockAccess == null ? render.getBlockIconFromSideAndMetadata(block, 0, meta) : render.getBlockIcon(block, render.blockAccess, (int)Math.round(x), (int)Math.round(y), (int)Math.round(z), -1);

        if (render.hasOverrideBlockTexture())
        {
            icon = overrideBlockTexture;
        }

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double shiftX = 0.5D;
        double shiftZ = 0.5D;
        
        double var20 = 0.5D * (double)scale;
        
        double var22 = x + 0.5D - var20;
        double var24 = x + 0.5D + var20;
        double var26 = z + 0.5D - var20;
        double var28 = z + 0.5D + var20;
        
        double newX = 0.5D;
        double newZ = 0.5D;
        
        tess.addVertexWithUV(var22 + 1, y + (double)scale, var26, minU, minV);
        tess.addVertexWithUV(var22 + 1 - 1 , y + 0.0D, var26,  minU, maxV);
        tess.addVertexWithUV(var24 - 1, y + 0.0D, var28, maxU, maxV);
        tess.addVertexWithUV(var24, y + (double)scale, var28, maxU, minV);
        
        tess.addVertexWithUV(var24, y + (double)scale, var28, minU, minV);
        tess.addVertexWithUV(var24 - 1 , y + 0.0D, var28, minU, maxV);
        tess.addVertexWithUV(var22 + 1 - 1, y + 0.0D, var26, maxU, maxV);
        tess.addVertexWithUV(var22 + 1, y + (double)scale, var26, maxU, minV);
        
        tess.addVertexWithUV(var22, y + (double)scale, var26, minU, minV);
        tess.addVertexWithUV(var22 + 1 , y + 0.0D, var26,  minU, maxV);
        tess.addVertexWithUV(var24 - 1 + 1, y + 0.0D, var28, maxU, maxV);
        tess.addVertexWithUV(var24 - 1, y + (double)scale, var28, maxU, minV);
      
        tess.addVertexWithUV(var24 - 1, y + (double)scale, var28, minU, minV);
        tess.addVertexWithUV(var24 - 1 + 1 , y + 0.0D, var28, minU, maxV);
        tess.addVertexWithUV(var22 + 1, y + 0.0D, var26, maxU, maxV);
        tess.addVertexWithUV(var22, y + (double)scale, var26, maxU, minV);
//        
//        tess.addVertexWithUV(var22, y + (double)scale, var28, minU, minV);
//        tess.addVertexWithUV(var22, y + 0.0D, var28, minU, maxV);
//        tess.addVertexWithUV(var24, y + 0.0D, var26, maxU, maxV);
//        tess.addVertexWithUV(var24, y + (double)scale, var26, maxU, minV);
//        
//        tess.addVertexWithUV(var24, y + (double)scale, var26, minU, minV);
//        tess.addVertexWithUV(var24, y + 0.0D, var26, minU, maxV);
//        tess.addVertexWithUV(var22, y + 0.0D, var28, maxU, maxV);
//        tess.addVertexWithUV(var22, y + (double)scale, var28, maxU, minV);
    }
    
    /**
     * Renders any block requiring croseed squares such as reeds, flowers, and mushrooms
     */
    public static boolean renderWestCrossedSquares(RenderBlocks render, Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(render.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(render.blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        // FCMOD: Code removed
        /*
        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        */
        // END FCMOD

        var5.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;

        if (par1Block == Block.tallGrass)
        {
            long var17 = (long)(par2 * 3129871) ^ (long)par4 * 116129781L ^ (long)par3;
            var17 = var17 * var17 * 42317861L + var17 * 11L;
            var19 += ((double)((float)(var17 >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
            var20 += ((double)((float)(var17 >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
            var15 += ((double)((float)(var17 >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;
        }

        drawHorizontalWestSquares(render, par1Block, render.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F);
        return true;
    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawHorizontalWestSquares(RenderBlocks render, Block block, int meta, double x, double y, double z, float scale)
    {
        Tessellator tess = Tessellator.instance;
        Icon icon = render.blockAccess == null ? render.getBlockIconFromSideAndMetadata(block, 0, meta) : render.getBlockIcon(block, render.blockAccess, (int)Math.round(x), (int)Math.round(y), (int)Math.round(z), -1);

        if (render.hasOverrideBlockTexture())
        {
            icon = overrideBlockTexture;
        }

        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        
        double var20 = 0.5D * (double)scale;
        
        double var22 = x + 0.5D - var20;
        double var24 = x + 0.5D + var20;
        double var26 = z + 0.5D - var20;
        double var28 = z + 0.5D + var20;
    
        tess.addVertexWithUV(var22, y + (double)scale, var28 - 1 + 1 , minU, minV);
        tess.addVertexWithUV(var22, y + 0.0D, var28 - 1, minU, maxV);
        tess.addVertexWithUV(var24, y + 0.0D, var26, maxU, maxV);
        tess.addVertexWithUV(var24, y + (double)scale, var26 + 1, maxU, minV);
        
        tess.addVertexWithUV(var22, y + (double)scale, var28 - 1 , minU, minV);
        tess.addVertexWithUV(var22, y + 0.0D, var28 - 1 + 1, minU, maxV);
        tess.addVertexWithUV(var24, y + 0.0D, var26 + 1, maxU, maxV);
        tess.addVertexWithUV(var24, y + (double)scale, var26, maxU, minV);
        
        tess.addVertexWithUV(var24, y + (double)scale, var28 - 1 , minU, minV);
        tess.addVertexWithUV(var24, y + 0.0D, var28 - 1 + 1, minU, maxV);
        tess.addVertexWithUV(var22, y + 0.0D, var26 + 1, maxU, maxV);
        tess.addVertexWithUV(var22, y + (double)scale, var26, maxU, minV);
        
        tess.addVertexWithUV(var24, y + (double)scale, var28 - 1  + 1 , minU, minV);
        tess.addVertexWithUV(var24, y + 0.0D, var28 - 1, minU, maxV);
        tess.addVertexWithUV(var22, y + 0.0D, var26, maxU, maxV);
        tess.addVertexWithUV(var22, y + (double)scale, var26  + 1, maxU, minV);
    }
}
