package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesLeavesFlowers extends SCBlockFruitTreesLeavesBase {

    public static final String[] flowerTextures = new String[] {
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[APPLE],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[CHERRY],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[LEMON],
    		"SCBlockFruitTreeLeavesFlowers_" + LEAF_TYPES[OLIVE]
    		};

	
	protected SCBlockFruitTreesLeavesFlowers(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockFruitLeavesFlower");
	}

	@Override
	public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
		return 0;
	}

	@Override
	public int damageDropped(int meta) {
		return meta & 3;
	}
	
	private Icon[] flowerIcons = new Icon[4];
	
	public void registerIcons(IconRegister register)
	{
		super.registerIcons(register);
		
		for (int i = 0; i < flowerTextures.length; ++i) 
		{
			this.flowerIcons[i] = register.registerIcon(flowerTextures[i]);
		}
	}
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	int type = renderer.blockAccess.getBlockMetadata(i, j, k) & 3;
    	
    	renderer.setOverrideBlockTexture(flowerIcons[type]);
		renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( 
				renderer.blockAccess, i, j, k ) );

		renderer.renderStandardBlock( this, i, j, k );
		
		renderer.clearOverrideBlockTexture();
    }
    
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int itemDamage, float fBrightness) {
    	    	
    	renderer.setRenderBounds(0.001,0.001,0.001,0.999,0.999,0.999);
//    	FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, leafIcons[itemDamage & 3]);
    	FCClientUtilsRender.RenderInvBlockFullBrightWithTexture(renderer, this, -0.5F, -0.5F, -0.5F,leafIcons[itemDamage & 3]);
    	
    	renderer.setRenderBounds(0,0,0,1,1,1);
//    	renderer.setOverrideBlockTexture(flowerIcons[itemDamage & 3]);
//    	FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -0.5F, -0.5F, -0.5F, flowerIcons[itemDamage & 3]);
    	FCClientUtilsRender.RenderInvBlockFullBrightWithTexture(renderer, this, -0.5F, -0.5F, -0.5F,flowerIcons[itemDamage & 3]);
//    	renderer.renderBlockAsItemVanilla( this, itemDamage, fBrightness );
//    	renderer.clearOverrideBlockTexture();

    }

}
