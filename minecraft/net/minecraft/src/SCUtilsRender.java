// SCADDON (client only)

package net.minecraft.src;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCUtilsRender extends FCClientUtilsRender
{
	   public static void drawCrossedSquaresFlowerPot(RenderBlocks render, Block block, int meta, double x, double y, double z, float yHeight, int facing, Icon icon) {
	       Tessellator tesselator = Tessellator.instance;
	       Icon blockIcon = icon; //render.getBlockIconFromSideAndMetadata(block, 0, meta);

	       if (render.hasOverrideBlockTexture())
	       {
	           blockIcon = render.GetOverrideTexture();
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
	
    /**
     * If set to >=0, all block faces will be rendered using this texture index
     */
    private static Icon overrideBlockTexture = null;
	
    public static void RenderCrossedSquaresWithTexture( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture, boolean offset)
    {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
        
        if ( !bHasOverride )
        {
        	renderBlocks.setOverrideBlockTexture( texture );
        }
        
        //renderBlocks.renderCrossedSquares(block, i, j, k );
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(block.getMixedBrightnessForBlock(renderBlocks.blockAccess, i, j, k));
        float var6 = 1.0F;
        int var7 = block.colorMultiplier(renderBlocks.blockAccess, i, j, k);
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
    		
//    		newCenterX = newX - i;
//    		newCenterZ = newZ - k;
    		
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
    
    public static void RenderCrossedSquaresWithTextureAndOffset( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture)
    {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
        
        if ( !bHasOverride )
        {
        	renderBlocks.setOverrideBlockTexture( texture );
        }
        
        renderCrossedSquares(renderBlocks, block, i, j, k );
        
        if ( !bHasOverride )
        {
        	renderBlocks.clearOverrideBlockTexture();
        }
    }
    
    /**
     * Renders any block requiring croseed squares such as reeds, flowers, and mushrooms
     */
    public static boolean renderCrossedSquares(RenderBlocks renderBlocks, Block par1Block, int par2, int par3, int par4)
    {
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(renderBlocks.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(renderBlocks.blockAccess, par2, par3, par4);
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
        
		double newI = (double)par2;
		double newJ = (double)par3;
		double newK = (double)par4;

		//long var17 = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
		long hash = (long)(par2 * 3129871) ^ (long)par4 * 116129781L;
		hash = hash * hash * 42317861L + hash * 11L;
		newI += ((double)((float)(hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
		newJ += ((double)((float)(hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
		newK += ((double)((float)(hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;	

		double centerX = 8/16D;
		double centerZ = 8/16D;
		
		double newCenterX = newI - par2 + centerX;
		double newCenterZ = newK - par4 + centerZ;
		
		double c = 0.0D;
		
    	newCenterX = ( Math.floor( newCenterX * 16 + c ) - c ) / 16;
    	newCenterZ = ( Math.floor( newCenterZ * 16 + c ) - c ) / 16;

        

        

        drawCrossedSquares(renderBlocks, par1Block, renderBlocks.blockAccess.getBlockMetadata(par2, par3, par4), newCenterX, par3, newCenterZ, 1.0F);
        return true;
    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawCrossedSquares(RenderBlocks renderBlocks, Block par1Block, int i, double j, double k, double par7, float par9)
    {
        Tessellator var10 = Tessellator.instance;
        Icon var11 = renderBlocks.blockAccess == null ? renderBlocks.getBlockIconFromSideAndMetadata(par1Block, 0, i) : renderBlocks.getBlockIcon(par1Block, renderBlocks.blockAccess, (int)Math.round(j), (int)Math.round(k), (int)Math.round(par7), -1);


        double var12 = (double)var11.getMinU();
        double var14 = (double)var11.getMinV();
        double var16 = (double)var11.getMaxU();
        double var18 = (double)var11.getMaxV();
        double var20 = 0.45D * (double)par9;
        double var22 = j + 0.5D - var20;
        double var24 = j + 0.5D + var20;
        double var26 = par7 + 0.5D - var20;
        double var28 = par7 + 0.5D + var20;
        var10.addVertexWithUV(var22, k + (double)par9, var26, var12, var14);
        var10.addVertexWithUV(var22, k + 0.0D, var26, var12, var18);
        var10.addVertexWithUV(var24, k + 0.0D, var28, var16, var18);
        var10.addVertexWithUV(var24, k + (double)par9, var28, var16, var14);
        var10.addVertexWithUV(var24, k + (double)par9, var28, var12, var14);
        var10.addVertexWithUV(var24, k + 0.0D, var28, var12, var18);
        var10.addVertexWithUV(var22, k + 0.0D, var26, var16, var18);
        var10.addVertexWithUV(var22, k + (double)par9, var26, var16, var14);
        var10.addVertexWithUV(var22, k + (double)par9, var28, var12, var14);
        var10.addVertexWithUV(var22, k + 0.0D, var28, var12, var18);
        var10.addVertexWithUV(var24, k + 0.0D, var26, var16, var18);
        var10.addVertexWithUV(var24, k + (double)par9, var26, var16, var14);
        var10.addVertexWithUV(var24, k + (double)par9, var26, var12, var14);
        var10.addVertexWithUV(var24, k + 0.0D, var26, var12, var18);
        var10.addVertexWithUV(var22, k + 0.0D, var28, var16, var18);
        var10.addVertexWithUV(var22, k + (double)par9, var28, var16, var14);
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
    
    /**
     * Render block salad
     */
    public static boolean renderBlockSalad(RenderBlocks renderer, Block par1Block, int par2, int par3, int par4)
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

        drawBlockSalad(renderer, par1Block, renderer.blockAccess.getBlockMetadata(par2, par3, par4), (double)par2, (double)par3 + var7, (double)par4, 1.0F);
   
        return true;
    }
    
    public static void drawBlockSalad(RenderBlocks renderer, Block par1Block, int par2, double x, double par5, double z, float par9)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon var10 = renderer.getBlockIconFromSideAndMetadata(par1Block, 0, par2);

        if (renderer.hasOverrideBlockTexture())
        {
            var10 = overrideBlockTexture;
        }

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
    
    public static void drawBlockSaladWithTexture(RenderBlocks renderer, Block par1Block, int par2, double x, double par5, double z, float par9, Icon icon)
    {
    	Tessellator var9 = Tessellator.instance;
        Icon var10 = icon;

        if (renderer.hasOverrideBlockTexture())
        {
            var10 = overrideBlockTexture;
        }

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
    
	public static boolean renderConnector(RenderBlocks render, Block block, int par2, int par3, int par4, Icon icon, int dir)
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

        drawConnector(render, block, render.blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F, icon, dir);
		
		return true;
    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public static void drawConnector(RenderBlocks render, Block block, int meta, double x, double y, double z, float scale, Icon icon, int dir)
    {
        Tessellator tess = Tessellator.instance;
        
//        Icon icon = render.getBlockIconFromSideAndMetadata(block, 0, meta);

        if (render.hasOverrideBlockTexture())
        {
        	icon = overrideBlockTexture;
        }
        
        //int dir = meta & 3;
        
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
	
	private static int color( int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8)
        {
        	for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = 0x7cbd6b;
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }
        
        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}
	
    /**
     * Render block salad
     */
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
    
    public static boolean renderBlockFlowerpot(RenderBlocks renderer, BlockFlowerPot par1BlockFlowerPot, int par2, int par3, int par4, boolean hasWater, Icon fillIcon)
    {
    	renderer.renderStandardBlock(par1BlockFlowerPot, par2, par3, par4);
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1BlockFlowerPot.getMixedBrightnessForBlock(renderer.blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = par1BlockFlowerPot.colorMultiplier(renderer.blockAccess, par2, par3, par4);
        Icon var8 = renderer.blockAccess == null ? renderer.getBlockIconFromSide(par1BlockFlowerPot, 0) : renderer.getBlockIcon(par1BlockFlowerPot, renderer.blockAccess, par2, par3, par4, 0);
        float var9 = (float)(var7 >> 16 & 255) / 255.0F;
        float var10 = (float)(var7 >> 8 & 255) / 255.0F;
        float var11 = (float)(var7 & 255) / 255.0F;
        float var12;
        float var14;

        // FCMOD: Code removed
        /*
        if (EntityRenderer.anaglyphEnable)
        {
            var12 = (var9 * 30.0F + var10 * 59.0F + var11 * 11.0F) / 100.0F;
            float var13 = (var9 * 30.0F + var10 * 70.0F) / 100.0F;
            var14 = (var9 * 30.0F + var11 * 70.0F) / 100.0F;
            var9 = var12;
            var10 = var13;
            var11 = var14;
        }
        */
        // END FCMOD

        var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
        var12 = 0.1865F;
        renderer.renderFaceXPos(par1BlockFlowerPot, (double)((float)par2 - 0.5F + var12), (double)par3, (double)par4, var8);
        renderer.renderFaceXNeg(par1BlockFlowerPot, (double)((float)par2 + 0.5F - var12), (double)par3, (double)par4, var8);
        renderer.renderFaceZPos(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 - 0.5F + var12), var8);
        renderer.renderFaceZNeg(par1BlockFlowerPot, (double)par2, (double)par3, (double)((float)par4 + 0.5F - var12), var8);
        if (hasWater)
        {
        	renderer.renderFaceYPos(par1BlockFlowerPot, (double)par2, (double)((float)par3 - 0.5F + var12 + 0.1875F + 1/16F), (double)par4, fillIcon);
        }
        int var19 = renderer.blockAccess.getBlockMetadata(par2, par3, par4);

        if (var19 != 0)
        {
            var14 = 0.0F;
            float var15 = 4.0F;
            float var16 = 0.0F;
            BlockFlower var17 = null;

            switch (var19)
            {
                case 1:
                    var17 = Block.plantRed;
                    break;

                case 2:
                    var17 = Block.plantYellow;

                case 3:
                case 4:
                case 5:
                case 6:
                default:
                    break;

                case 7:
                    var17 = Block.mushroomRed;
                    break;

                case 8:
                    var17 = Block.mushroomBrown;
            }

            var5.addTranslation(var14 / 16.0F, var15 / 16.0F, var16 / 16.0F);

            if (var17 != null)
            {
            	renderer.renderBlockByRenderType(var17, par2, par3, par4);
            }
            else if (var19 == 9)
            {
            	
            	
            	renderer.SetRenderAllFaces(true);
                float var18 = 0.125F;
                renderer.setRenderBounds((double)(0.5F - var18), 0.0D, (double)(0.5F - var18), (double)(0.5F + var18), 0.25D, (double)(0.5F + var18));
                renderer.renderStandardBlock(Block.cactus, par2, par3, par4);
                renderer.setRenderBounds((double)(0.5F - var18), 0.25D, (double)(0.5F - var18), (double)(0.5F + var18), 0.5D, (double)(0.5F + var18));
                renderer.renderStandardBlock(Block.cactus, par2, par3, par4);
                renderer.setRenderBounds((double)(0.5F - var18), 0.5D, (double)(0.5F - var18), (double)(0.5F + var18), 0.75D, (double)(0.5F + var18));
                renderer.renderStandardBlock(Block.cactus, par2, par3, par4);
                renderer.SetRenderAllFaces(false);
                renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
            }
            else if (var19 == 3)
            {
            	renderer.drawCrossedSquares(Block.sapling, 0, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 5)
            {
            	renderer.drawCrossedSquares(Block.sapling, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 4)
            {
            	renderer.drawCrossedSquares(Block.sapling, 1, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 6)
            {
            	renderer.drawCrossedSquares(Block.sapling, 3, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 11)
            {
                var7 = Block.tallGrass.colorMultiplier(renderer.blockAccess, par2, par3, par4);
                var9 = (float)(var7 >> 16 & 255) / 255.0F;
                var10 = (float)(var7 >> 8 & 255) / 255.0F;
                var11 = (float)(var7 & 255) / 255.0F;
                var5.setColorOpaque_F(var6 * var9, var6 * var10, var6 * var11);
                renderer.drawCrossedSquares(Block.tallGrass, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }
            else if (var19 == 10)
            {
            	renderer.drawCrossedSquares(Block.deadBush, 2, (double)par2, (double)par3, (double)par4, 0.75F);
            }

            var5.addTranslation(-var14 / 16.0F, -var15 / 16.0F, -var16 / 16.0F);
        }

        return true;
    }
    
}
