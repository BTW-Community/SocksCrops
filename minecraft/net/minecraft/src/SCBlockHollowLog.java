package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockHollowLog extends SCBlockLogBase {
	
	public static final String[] treeNames = new String[] {"oak", "spruce", "birch", "jungle"};
	public static final String[] treeTextureTypes = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};
	public static final String[] treeTextureTop = new String[] {"SCBlockLogHollowOak_top", "SCBlockLogHollowSpruce_top", "SCBlockLogHollowBirch_top", "SCBlockLogHollowJungle_top"};
	public static final String[] treeTextureInner = new String[] {"SCBlockLogHollowOak_inside", "SCBlockLogHollowSpruce_inside", "SCBlockLogHollowBirch_inside", "SCBlockLogHollowJungle_inside"};
	
	protected static final SCModelBlockHollowLog model = new SCModelBlockHollowLog();
	
	protected SCBlockHollowLog(int iBlockID) {
		super(iBlockID);
		
	    setHardness( 1.25F ); // vanilla 2
	    setResistance( 3.33F );  // odd value to match vanilla resistance set through hardness of 2
        
		SetAxesEffectiveOn();
		SetChiselsEffectiveOn(false);
		
        SetBuoyant();
		
		SetFireProperties( FCEnumFlammability.LOGS );
		
        setStepSound( soundWoodFootstep );
		
		this.setUnlocalizedName("SCBlockHollowLog");
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}
	
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemSawDust.itemID, 2, 0, fChanceOfDrop );
		DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemBark.itemID, 1, iMetadata & 3, fChanceOfDrop );
		
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean HasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency)
	{
		return true;
	}

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int side, int meta)
    {
        int rot = meta & 12;
        int type = meta & 3;
        
        return  (rot == 0 && (side == 1 || side == 0) ? this.tree_top[type] :
        	(rot == 4 && (side == 5 || side == 4) ? this.tree_top[type] :
        		(rot == 8 && (side == 2 || side == 3) ? this.tree_top[type] :
        			this.tree_side[type])));
    }

	private Icon[] tree_top;
	private Icon[] tree_inner;
	private Icon[] tree_side;
	
	@Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.tree_top = new Icon[treeTextureTop.length];

        for (int var2 = 0; var2 < this.treeTextureTop.length; ++var2)
        {
            this.tree_top[var2] = par1IconRegister.registerIcon(treeTextureTop[var2]);
        } 
        
        this.tree_inner = new Icon[treeTextureInner.length];

        for (int var2 = 0; var2 < this.treeTextureInner.length; ++var2)
        {
            this.tree_inner[var2] = par1IconRegister.registerIcon(treeTextureInner[var2]);
        }
        
        this.tree_side = new Icon[treeTextureTypes.length];

        for (int var2 = 0; var2 < this.treeTextureTypes.length; ++var2)
        {
            this.tree_side[var2] = par1IconRegister.registerIcon(treeTextureTypes[var2]);
        }
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	int type = meta & 3;
    	int rot = meta & 12;
    	
        renderer.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D );        
    	renderer.renderBlockLog( this, i, j, k );
    	
    	// INSIDE 

    	
    	if (rot == 0)
    	{
    		renderer.renderFaceXPos(this, i - 14/16D, j, k, tree_inner[type]);
    		renderer.renderFaceXNeg(this, i + 14/16D, j, k, tree_inner[type]);
    		renderer.renderFaceZPos(this, i, j, k - 14/16D, tree_inner[type]);
    		renderer.renderFaceZNeg(this, i, j, k + 14/16D, tree_inner[type]);
    	}
    	else if (rot == 4)
    	{
        	renderer.SetUvRotateTop(1);
        	renderer.SetUvRotateEast(1);
        	renderer.SetUvRotateWest(1);
        	renderer.SetUvRotateBottom(1);

    		renderer.renderFaceZPos(this, i, j, k - 14/16D, tree_inner[type]);
    		renderer.renderFaceZNeg(this, i, j, k + 14/16D, tree_inner[type]);
    		renderer.renderFaceYPos(this, i, j - 14/16D, k, tree_inner[type]);
    		renderer.renderFaceYNeg(this, i, j + 14/16D, k, tree_inner[type]);
    	}
    	else if (rot == 8)
    	{
        	renderer.SetUvRotateSouth(1);
        	renderer.SetUvRotateNorth(1);
        	
    		renderer.renderFaceXPos(this, i - 14/16D, j, k, tree_inner[type]);
    		renderer.renderFaceXNeg(this, i + 14/16D, j, k, tree_inner[type]);
    		renderer.renderFaceYPos(this, i, j - 14/16D, k, tree_inner[type]);
    		renderer.renderFaceYNeg(this, i, j + 14/16D, k, tree_inner[type]);
    	}
    	
    	renderer.SetUvRotateTop(0);
    	renderer.SetUvRotateEast(0);
    	renderer.SetUvRotateWest(0);
    	renderer.SetUvRotateBottom(0);
    	renderer.SetUvRotateSouth(0);
    	renderer.SetUvRotateNorth(0);
    	
    	
    	return true;
    }  
    
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {

    	super.RenderBlockAsItem(renderer, iItemDamage, fBrightness);
    	
    	int i = 0;
    	int j;
    	int k;
    	
    	int meta = iItemDamage;
    	int type = meta & 3;
    	int rot = meta & 12;
    	// INSIDE
    	
    	if (rot == 0)
    	{
    		renderer.setRenderBounds( 
    				1.001/16F, 0.001F, 1.001/16F, 
    				1.999/16F, 0.999F, 14.999/16F );
    		FCClientUtilsRender.RenderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );
    		
    		renderer.setRenderBounds( 
    				14/16F, 0.001F, 1.001/16F, 
    				14.999/16F, 0.999F, 14.999/16F );
    		FCClientUtilsRender.RenderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );
    		
    		renderer.setRenderBounds( 
    				1.001/16F, 0.001F, 1.001/16F, 
    				14.999/16F, 0.999F, 1.999/16F );
    		FCClientUtilsRender.RenderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );
    		
    		renderer.setRenderBounds( 
    				1.001/16F, 0.001F, 14/16F, 
    				14.999/16F, 0.999F, 14.999/16F );
    		FCClientUtilsRender.RenderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );
    	}
    	
    	
    	renderer.SetUvRotateTop(0);
    	renderer.SetUvRotateEast(0);
    	renderer.SetUvRotateWest(0);
    	renderer.SetUvRotateBottom(0);
    	renderer.SetUvRotateSouth(0);
    	renderer.SetUvRotateNorth(0);
    	
    	

    }

}
