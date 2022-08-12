package net.minecraft.src;

import com.prupe.mcpatcher.cc.ColorizeBlock;

public class SCBlockLilyRose extends FCBlockLilyPad {

	protected SCBlockLilyRose(int iBlockID) {
		super(iBlockID);
		setHardness(0.0F);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockItemLilyFlower");
	}
	
	private boolean secondRenderpass = false;
	
	private Icon rose;
	private Icon flower;
	private Icon lily;
	private Icon roots;
	
	@Override
	public void registerIcons(IconRegister register) {
		super.registerIcons(register);
		
		rose = register.registerIcon("SCBlockLilyRose");
		flower = register.registerIcon("SCBlockLilyFlower");
		lily = register.registerIcon("waterlily");
		roots = register.registerIcon("SCBlockLilyRose_roots");
	}
	
    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	if (secondRenderpass) 
    	{
    		return 0xffffff;
    	}
    	else return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    }
    
    @Override
    public int getRenderColor(int par1)
    {
    	return 0xffffff;
    }

    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {    
    	renderer.setOverrideBlockTexture(roots);
    	renderer.setRenderBounds(0, 0, 0, 1, 15/16D, 1);
    	renderer.renderCrossedSquares(this, i, j - 1, k);
    	renderer.clearOverrideBlockTexture();
    	
    	renderer.setOverrideBlockTexture(lily);    	
        renderer.setRenderBounds( GetBlockBoundsFromPoolBasedOnState( renderer.blockAccess, i, j, k ) );        
    	renderer.renderBlockLilyPad( this, i, j, k );
    	renderer.clearOverrideBlockTexture();
    	
    	return true;
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult)
    {   
    	secondRenderpass = true;
    	
    	SCUtilsRender.renderBlockSaladWithTexture(renderer, this, i, j, k, rose);
    	
    	renderer.setOverrideBlockTexture(flower);
    	renderer.renderCrossedSquares(this, i, j, k);
    	renderer.clearOverrideBlockTexture();
    	
    	secondRenderpass = false;
    }
    
    @Override
    public void RenderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness)
    {
    	renderBlocks.setOverrideBlockTexture(flower);
    	renderBlocks.renderBlockAsItemVanilla(this, iItemDamage, fBrightness);
    	renderBlocks.clearOverrideBlockTexture();
 
    }

}