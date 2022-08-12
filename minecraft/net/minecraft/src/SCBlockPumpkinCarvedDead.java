package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinCarvedDead extends SCBlockPumpkinCarved {

	protected SCBlockPumpkinCarvedDead(int iBlockID) {
		super(iBlockID);
		setCreativeTab(null);
	}
	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		
		return SCDefs.pumpkinCarved.blockID;
	}
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
		//Orange
		if (meta <= 3){
			return 3;
		}
		//Green
		else if (meta <= 7){
			return 7;
		}
		//Yellow
		else if (meta <= 11){
			return 11;
		}
		//White
		else return 15;
    }
	
	
	private Icon connectorIcon[] = new Icon[4];
	private Icon overlayIcon[] = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		super.registerIcons(register);
		
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[0] = register.registerIcon( "SCBlockPumpkinOrangeConnector_" + 3 );
        	connectorIcon[1] = register.registerIcon( "SCBlockPumpkinGreenConnector_" + 3 );
        	connectorIcon[2] = register.registerIcon( "SCBlockPumpkinYellowConnector_" + 3 );
        	connectorIcon[3] = register.registerIcon( "SCBlockPumpkinWhiteConnector_" + 3 );
        }
        
        overlayIcon[0] = register.registerIcon("SCBlockPumpkinOrangeSideOverlay");
        overlayIcon[1] = register.registerIcon("SCBlockPumpkinGreenSideOverlay");
        overlayIcon[2] = register.registerIcon("SCBlockPumpkinYellowSideOverlay");
        overlayIcon[3] = register.registerIcon("SCBlockPumpkinWhiteSideOverlay");
	}
	
	private boolean vinePass;
	private boolean secondPass;
	
	@Override
	public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		if (secondPass) {
			return getBlockTextureSecondPass(par1iBlockAccess, par2, par3, par4, par5);
		}
		else return this.getIcon(par5, par1iBlockAccess.getBlockMetadata(par2, par3, par4));
	}
	
	private Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int i, int j, int k, int side)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		if (meta < 4) return overlayIcon[0];
		
		else if (meta < 8) return overlayIcon[1];
		
		else if (meta < 12) return overlayIcon[2];
		
		else return  overlayIcon[3];
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{		
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		vinePass = true;
		if (hasDeadVineFacing(renderer, i, j, k))
		{
			if (meta < 4) this.renderVineConnector(renderer, i, j, k, connectorIcon[0]);
			else if (meta < 8) this.renderVineConnector(renderer, i, j, k, connectorIcon[1]);
			else if (meta < 12) this.renderVineConnector(renderer, i, j, k, connectorIcon[2]);
			else this.renderVineConnector(renderer, i, j, k, connectorIcon[3]);
		}
		vinePass = false;
		
		return super.RenderBlock(renderer, i, j, k);
	}
	
	@Override
	public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean firstPassResult) {
		secondPass = true;
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k) );
		renderer.renderStandardBlock(this, i, j, k);
		secondPass = false;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i, int j, int k, int side) {
		if (secondPass)
		{
			if (side == 1) return false;
			return true;
		}
		else return super.shouldSideBeRendered(blockAccess, i, j, k, side);
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		if (vinePass) 
		{
			if ( getDirection(blockAccess.getBlockMetadata(x, y, z)) == 0 && blockAccess.getBlockId(x, y, z - 1) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 2 && blockAccess.getBlockId(x, y, z + 1) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 1 && blockAccess.getBlockId(x + 1, y, z) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 3 && blockAccess.getBlockId(x - 1, y, z) == SCDefs.gourdVineDead.blockID)
			{
				return 0xfb9a35; //hue to dead color
			}
		}
		return super.colorMultiplier(blockAccess, x, y, z);
	}
	

	private boolean hasDeadVineFacing( RenderBlocks r, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	    int iTargetFacing = 0;
	    
	    int dir = this.getDirection(r.blockAccess.getBlockMetadata(i, j, k));
	    	
	    if (dir == 0) {
	    	iTargetFacing = 2;
	    }
	    else if (dir == 1) {
	    	iTargetFacing = 5;
	    }
	    else if (dir == 2) {
	    	iTargetFacing = 3;
	    }
	    else if (dir == 3) {
	    	iTargetFacing = 4;
	    }
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = r.blockAccess.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == SCDefs.gourdVineDead.blockID )
	    {	
	    	return true;
	    }
	    else return false;
	}
	
	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4, Icon icon)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;


        tess.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        double var19 = (double)par2;
        double var20 = (double)par3;
        double var15 = (double)par4;
        
        this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), var19, var20, var15, 1.0F, icon);
        
		return true;

    }
	
	/**
     * Utility function to draw crossed swuares
     */
    public void drawConnector(Block block, int meta, double x, double y, double z, float scale, Icon icon)
    {
        Tessellator tess = Tessellator.instance;
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0.5D;// - var20;
        double maxZ = z + 0.5D + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0.5D;// - var20;
            maxX = x + 0.5D + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D - var20;
            maxZ = z + 1.5D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0.5D - var20;
            maxX = x + 1.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (2 * (double)scale) , minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (2 * (double)scale), maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (2 * (double)scale), minZ, maxU, minV);
        }

    }
}
