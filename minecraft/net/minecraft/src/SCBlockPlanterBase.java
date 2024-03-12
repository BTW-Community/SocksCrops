package net.minecraft.src;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public abstract class SCBlockPlanterBase extends FCBlockPlanterBase {

	protected SCBlockPlanterBase(int iBlockID, String unlocalisedName) {
		super(iBlockID);
		
		SetBlockMaterial(Material.ground);
		
		setHardness( 0.5F );
		
		SetPicksEffectiveOn( true );
		SetHoesEffectiveOn( true );
		setTickRandomly(true);

		setCreativeTab(CreativeTabs.tabDecorations);
		
		setUnlocalizedName( unlocalisedName );
	}
	
		
	
	
	
	protected abstract boolean isHydrated(int meta);

	protected abstract boolean isFertilized(int meta);
	
	protected abstract void setFertilized(World world, int x, int y, int z);
	
	protected abstract int getNutritionLevel( int meta);
	
	protected float getNutritionMultiplier(int meta)
	{
		switch(getNutritionLevel(meta))
		{
			default:
			case 0:
				return 1.0F;
			case 1:
				return 0.75F;
			case 2:
				return 0.5F;
			case 3:
				return 0.25F;
		}		
	}

	
	protected abstract Icon getContentsTexture(int meta);
	
	protected abstract Icon getGrassTexture(int meta);
	
	@Override
	public Icon getIcon(int side, int meta) {
		if ( side == 1 )
		{
			return getContentsTexture(meta);
		}
		else return blockIcon;
	}
	
	protected Icon topRing;
	
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = register.registerIcon( "fcBlockPlanter" );
		topRing = register.registerIcon("SCBlockPlanter_topRing");
	}
	
	protected abstract void renderOverlay(RenderBlocks renderer, int i, int j, int k);

	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		RenderFilledPlanterBlock(renderer, i, j, k);		
		renderOverlay(renderer, i, j, k);	
        
		return true;
	}
	
	protected int getGrassColor(int meta) {
		switch (getNutritionLevel(meta)) {
		default:	
			return -7226023;
		case 0:
			return color(null, 0, 0, 0, 1.0f , 1.0f , 0.9f);
		case 1:
			return color(null, 0, 0, 0, 0.9f , 0.9f , 0.9f);
		case 2:
			return color(null, 0, 0, 0, 0.85f , 0.6f , 0.9f);
		case 3:
			return color(null, 0, 0, 0, 0.8f , 0.3f , 0.9f);
	}
	}
	
	protected int getGrassColor(IBlockAccess blockAccess, int x, int y, int z) {
		switch (getNutritionLevel(blockAccess.getBlockMetadata(x, y, z))) {
			default:	
				return -7226023;
			case 0:
				return color(blockAccess, x, y, z, 1.0f , 1.0f , 1.0f);
			case 1:
				return color(blockAccess, x, y, z, 0.9f , 0.9f , 1.0f);
			case 2:
				return color(blockAccess, x, y, z, 0.85f , 0.6f , 1.0f);
			case 3:
				return color(blockAccess, x, y, z, 0.8f , 0.3f , 1.0f);
		}
	}
	
	@Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult) {
		renderer.setOverrideBlockTexture(topRing);
		renderer.setRenderBounds(0,1,0,1,1.001,1);
    	renderer.renderStandardBlock(this, x, y, z);
    	renderer.clearOverrideBlockTexture();
    }
	
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float brightness) {
    	RenderFilledPlanterInvBlock( renderer, this, iItemDamage );
    	
    	
    	int nutritionLevel = getNutritionLevel(iItemDamage);
    	int var14;
        int var6;
        float var7;
        float var8;
        float var9;
    	
    	renderOverlayItem(renderer, iItemDamage, brightness);
    	
    	GL11.glColor4f(1 * brightness, 1 * brightness, 1 * brightness, 1.0F);
    	renderer.setOverrideBlockTexture(topRing);
		renderer.setRenderBounds(0,0.999,0,1,1,1);
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage );
    	renderer.clearOverrideBlockTexture();
    	
//    	int nutritionLevel = getNutritionLevel(iItemDamage);    	
//    	SCUtilsRender.renderPlanterContentsAsItem(
//    			renderer, this, 
//    			iItemDamage, brightness, 
//    			nutritionLevel, 
//    			getContentsTexture(iItemDamage),
//    			getGrassTexture(iItemDamage),
//    			topRing);    	
    	
    }
    
    protected abstract void renderOverlayItem(RenderBlocks renderer, int iItemDamage, float brightness);



	protected int color( IBlockAccess blockAccess, int i, int j, int k, float h, float s, float b) {
//      for (int var8 = -1; var8 <= 1; ++var8)
//      {
//      	for (int var9 = -1; var9 <= 1; ++var9)
//          {
//              int var10 = blockAccess.getBiomeGenForCoords(i + var9, k + var8).getBiomeGrassColor();
//              
//              r += (var10 & 16711680) >> 16;
//              g += (var10 & 65280) >> 8;
//              b += var10 & 255;
//          }
//      }
//      
//      return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
		int biomeColor = 0;
		int red = 0;
		int green = 0;
		int blue = 0;
		float[] hsb;

		if (blockAccess == null) //used in itemrender
		{
			int var10 = -7226023; // Plains
//
//			red += (var10 & 16711680) >> 16;
//			green += (var10 & 65280) >> 8;
//			blue += var10 & 255;
			
			Color color = new Color(var10);
			red = color.getRed();
			green = color.getGreen();
			blue = color.getBlue();
			
			hsb = Color.RGBtoHSB(red, green, blue, null);

		} else {
			for (int var8 = -1; var8 <= 1; ++var8) {
				for (int var9 = -1; var9 <= 1; ++var9) {
					int var10 = blockAccess.getBiomeGenForCoords(i + var9, k + var8).getBiomeGrassColor();

					red += (var10 & 16711680) >> 16;
					green += (var10 & 65280) >> 8;
					blue += var10 & 255;
				}
			}
			
			hsb = Color.RGBtoHSB(red / 9, green / 9, blue / 9, null);
		}		

		// Step 2: Modify Hue
		hsb[0] = (hsb[0] + h) % 1.0f;
		if (hsb[0] < 0) {
			hsb[0] += 1.0f;
		}

		// Step 3: Modify Saturation
		hsb[1] = Math.max(0.0f, Math.min(1.0f, hsb[1] * s));

		// Step 4: Modify Brightness
		hsb[2] = Math.max(0.0f, Math.min(1.0f, hsb[2] * b));

		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}
}
