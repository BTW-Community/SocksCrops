package net.minecraft.src;

import java.util.List;

import org.lwjgl.opengl.GL11;

public class SCBlockHedges extends Block {

	protected SCBlockHedges(int blockID) {
		super(blockID, Material.wood);
		InitBlockBounds( 0D, 0D, 0D, 1D, 1D, 1D );
		
		setCreativeTab( CreativeTabs.tabDecorations );
		setUnlocalizedName("SCBlockHedges");
	}
	
	@Override
	public int damageDropped(int meta) {
		return meta;
	}
		
	
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
		
		return new AxisAlignedBB(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, 1D, center + leavesWidth/2
    			).offset(i,j,k);
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(
    			center - 2/16D, 0, center - 2/16D,
    			center + 2/16D, 1D, center + 2/16D
    			);	
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return new AxisAlignedBB(
    			center - 1/16D, 0, center - 2/16D,
    			center + 1/16D, 1.5D, center + 2/16D
    			).offset(i,j,k);
	}
	
	@Override
	public void getSubBlocks(int blockID, CreativeTabs tab, List list) {
		
		for(int i = 0; i < hedgeTypes.length; i++)
		{
			list.add(new ItemStack(blockID, 1, i));
		}

	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
			int side) {
		
    	boolean connectW = false;
    	boolean connectE = false;    	
    	boolean connectN = false;    	
    	boolean connectS = false;    	
    	
		if (CanConnectToBlockToFacing(blockAccess, iNeighborI, iNeighborJ, iNeighborK, NORTH)) connectN = true;
		if (CanConnectToBlockToFacing(blockAccess, iNeighborI, iNeighborJ, iNeighborK, SOUTH)) connectS = true;
		if (CanConnectToBlockToFacing(blockAccess, iNeighborI, iNeighborJ, iNeighborK, WEST)) connectW = true;
		if (CanConnectToBlockToFacing(blockAccess, iNeighborI, iNeighborJ, iNeighborK, EAST)) connectE = true;
		
		
		if (leavesPass)
		{
			if (blockAccess.getBlockId(iNeighborI, iNeighborJ, iNeighborK) == this.blockID || 
					CanConnectToBlockAt(blockAccess, iNeighborI, iNeighborJ, iNeighborK)) return false;
			return super.shouldSideBeRendered(blockAccess, iNeighborI, iNeighborJ, iNeighborK, side);
		}
		else if (woodPass)
		{
			return side > 0;
		}
		else
		{
			Block block = Block.blocksList[blockAccess.getBlockId(iNeighborI, iNeighborJ, iNeighborK)];
			
			if (side == 1 && blockAccess.getBlockId(iNeighborI, iNeighborJ, iNeighborK) == this.blockID)
			{
				return false;
			}
			
			if (nPass || sPass)
			{
				
				if (connectN && (side == NORTH || side == SOUTH))
				{

					if (block != null && block != this && (block.blockMaterial.isOpaque() || block.renderAsNormalBlock()) ) return true;
					
					return false;
					
				}
				else if (connectS && (side == NORTH || side == SOUTH))
				{
			    	
					if (block != null && block != this && (block.blockMaterial.isOpaque() || block.renderAsNormalBlock()) ) return true;
					
					return false;
				}
				else return true;
			}
			else if (ePass || wPass)
			{

				if (connectE && (side == EAST || side == WEST))
				{
					if (block != null && block != this && (block.blockMaterial.isOpaque() || block.renderAsNormalBlock()) ) return true;
					
					return false;
				}
				else if (connectW && (side == EAST || side == WEST))
				{
					if (block != null && block != this && (block.blockMaterial.isOpaque() || block.renderAsNormalBlock()) ) return true;
					
					return false;
				}
				else return true;
			}
			else return true;
		}

		
	}
	
    @Override
    public boolean CanGroundCoverRestOnBlock( World world, int i, int j, int k )
    {
    	return world.doesBlockHaveSolidTopSurface( i, j - 1, k );
    }
    
    @Override
    public float GroundCoverRestingOnVisualOffset( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -1F;        
    }
	
	//------------- FCBlockFence ------------//
	
    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean getBlocksMovement( IBlockAccess blockAccess, int i, int j, int k )
    {
		// getBlocksMovement() is misnamed and returns true if the block *doesn't* block movement
    	
        return true;
    }
    
	@Override
    public int GetWeightOnPathBlocked( IBlockAccess blockAccess, int i, int j, int k )
    {
    	return -3;
    }
	
    @Override
	public boolean HasCenterHardPointToFacing( IBlockAccess blockAccess, 
		int i, int j, int k, int iFacing, boolean bIgnoreTransparency )
	{
		return iFacing == 0 || iFacing == 1;
	}
    
    @Override
    public float MobSpawnOnVerticalOffset( World world, int i, int j, int k )
    {
    	// corresponds to the actual collision volume of the fence, which extends
    	// half a block above it
    	
    	return 0.5F;
    }
    
	//------------- Class Specific Methods ------------//
	
    protected boolean CanConnectToBlockToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
    {
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k, iFacing );
		
		return CanConnectToBlockAt( blockAccess, targetPos.i, targetPos.j, targetPos.k );
    }
    
    protected boolean CanConnectToBlockAt( IBlockAccess blockAccess, int i, int j, int k )
    {
        int var5 = blockAccess.getBlockId( i, j, k );

        if ( var5 != this.blockID && var5 != Block.fenceGate.blockID )
        {
            Block var6 = Block.blocksList[var5];
            
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? 
            	var6.blockMaterial != Material.pumpkin : false;
        }
        else
        {
            return true;
        }
    }
    
	public final static int OAK = 0;
	public final static int SPRUCE = 1;
	public final static int BIRCH = 2;
	public final static int JUNGLE = 3;
	public final static int BLOOD = 4;
	public final static int APPLE = 5;
	public final static int CHERRY = 6;
	public final static int LEMON = 7;
	public final static int OLIVE = 8;
	public final static int FLOWER_APPLE = 9;
	public final static int FLOWER_CHERRY = 10;
	public final static int FLOWER_LEMON = 11;
	public final static int FLOWER_OLIVE = 12;
	
	
	//----------- Client Side Functionality -----------//
    
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
    	if (woodPass) return 0xFFFFFF;
    	
    	int meta = blockAccess.getBlockMetadata(x, y, z);
    	
    	if (meta == OAK)
    	{
    		int var6 = 0;
            int var7 = 0;
            int var8 = 0;

            for (int var9 = -1; var9 <= 1; ++var9)
            {
                for (int var10 = -1; var10 <= 1; ++var10)
                {
                    int var11 = blockAccess.getBiomeGenForCoords(x + var10, z + var9).getBiomeFoliageColor();
                    var6 += (var11 & 16711680) >> 16;
                    var7 += (var11 & 65280) >> 8;
                    var8 += var11 & 255;
                }
            }

            return (var6 / 9 & 255) << 16 | (var7 / 9 & 255) << 8 | var8 / 9 & 255;
    	}
    	else if (meta == SPRUCE) 
		{
			return ColorizerFoliage.getFoliageColorPine();
		}
		else if (meta == BIRCH)
		{
			return ColorizerFoliage.getFoliageColorBirch();
		}
		else if (meta == JUNGLE)
		{
			return -11285961;
		}
		else if (meta == BLOOD)
		{
			return 0xD81F1F;
		}
		else
		{
			if (meta > BLOOD) return 0xFFFFFF;
			else return -7226023;
		}
	}
    
    
    public int colorMultiplierForItemRender(int meta)
	{
    	if (meta == SPRUCE) 
		{
			return ColorizerFoliage.getFoliageColorPine();
		}
		else if (meta == BIRCH)
		{
			return ColorizerFoliage.getFoliageColorBirch();
		}
		else if (meta == JUNGLE)
		{
			return -11285961;
		}
		else if (meta == BLOOD)
		{
			return 0xD81F1F;
		}
		else
		{
			if (meta > BLOOD) return 0xFFFFFF;
			
			else return -7226023;
			
		}
	}
    
    public static String[] hedgeTypes = {
    		"oak", "spruce", "birch", "jungle",
    		"blood",
    		"apple", "cherry", "lemon", "olive",
    		"flower_apple", "flower_cherry", "flower_lemon", "flower_olive"};
    
    Icon[] woodSide = new Icon[16];
    private String[] woodSideTex = {
    		"tree_side", "tree_spruce", "tree_birch", "tree_jungle", 
    		"fcBlockBloodWood_side", 
    		"SCBlockFruitTreeLogBranchSide_apple", "SCBlockFruitTreeLogBranchSide_cherry" ,"SCBlockFruitTreeLogBranchSide_lemon", "SCBlockFruitTreeLogBranchSide_olive",
    		"SCBlockFruitTreeLogBranchSide_apple", "SCBlockFruitTreeLogBranchSide_cherry" ,"SCBlockFruitTreeLogBranchSide_lemon", "SCBlockFruitTreeLogBranchSide_olive"};
    
    Icon[] leaves = new Icon[16];
    private String[] leavesTex = {
    		"leaves", "leaves_spruce", "leaves", "leaves_jungle",
    		"leaves", 
    		"SCBlockFruitTreeLeaves_apple", "SCBlockFruitTreeLeaves_cherry", "SCBlockFruitTreeLeaves_lemon", "SCBlockFruitTreeLeaves_olive",
    		"SCBlockFruitTreeLeaves_apple", "SCBlockFruitTreeLeaves_cherry", "SCBlockFruitTreeLeaves_lemon", "SCBlockFruitTreeLeaves_olive" };
    
    Icon[] flower = new Icon[4];
    private String[] flowerTex = {
    	"SCBlockFruitTreeLeavesFlowers_apple", "SCBlockFruitTreeLeavesFlowers_cherry", "SCBlockFruitTreeLeavesFlowers_lemon", "SCBlockFruitTreeLeavesFlowers_olive"	};
    
    
    Icon[] opaqueLeaves = new Icon[16];
    private String[] opaqueLeavesTex = {
    		"leaves_opaque", "leaves_spruce_opaque", "leaves_opaque", "leaves_jungle_opaque",
    		"leaves_opaque",
    		"SCBlockFruitTreeLeaves_appleOpaque", "SCBlockFruitTreeLeaves_cherryOpaque", "SCBlockFruitTreeLeaves_lemonOpaque", "SCBlockFruitTreeLeaves_oliveOpaque",
    		"SCBlockFruitTreeLeaves_appleOpaque", "SCBlockFruitTreeLeaves_cherryOpaque", "SCBlockFruitTreeLeaves_lemonOpaque", "SCBlockFruitTreeLeaves_oliveOpaque"};
    
    
    
    @Override
    public void registerIcons(IconRegister register) {
    	for (int i = 0; i < hedgeTypes.length; i++)
    	{
    		woodSide[i] = register.registerIcon(woodSideTex[i]);
    		leaves[i] = register.registerIcon(leavesTex[i]);
    		opaqueLeaves[i] = register.registerIcon(opaqueLeavesTex[i]);
    	}
    	
    	for (int i=0; i < flower.length; i++)
    	{
    		flower[i] = register.registerIcon(flowerTex[i]);
    	}
    }
    
    @Override
    public Icon getIcon(int side, int meta) {

    	if (leavesPass || nPass || sPass || ePass || wPass )
    	{
    		if (!Block.leaves.graphicsLevel) //fast graphics
    		{
    			return opaqueLeaves[meta];
    		}    		
    		else return leaves[meta];
    	}
    	else return woodSide[meta];
    }
    
    float woodWidth = 4/16F;
    float woodHeight = 14/16F;
    
    float leavesWidth = 10/16F;
    float leavesHeight = 16/16F;
    
    float widthAdjustmentMinXw = 0;
    float widthAdjustmentMaxXw = 0;
    
    float widthAdjustmentMinXe = 0;
    float widthAdjustmentMaxXe = 0;
    
    float widthAdjustmentMinZn = 0;
    float widthAdjustmentMaxZn = 0;
    
    float widthAdjustmentMinZs = 0;
    float widthAdjustmentMaxZs = 0;
    
    float center = 0.5F;
    
    boolean woodPass;
    boolean leavesPass;
    boolean nPass;
    boolean sPass;
    boolean ePass;
    boolean wPass;
    
    final int NORTH = 2;
    final int SOUTH = 3;
    final int WEST = 4;
    final int EAST = 5;
    
    @Override
    public boolean RenderBlock(RenderBlocks renderer, int x, int y, int z) 
    {
    	int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
    	
    	
    	
    	woodPass = true;
    	
      	if (Block.leaves.graphicsLevel) // Only render the log if in fancy graphics
    	{    	
//        	float adjustment;
//        	
//        	if (renderer.blockAccess.getBlockId(x, y + 1, z) == this.blockID)
//        	{
//        		adjustment = 2/16F;
//        	}
//        	else adjustment = 0;
        	
        	//wood
      		if (renderer.blockAccess.getBlockId(x, y - 1, z) != this.blockID)
      		{
            	renderer.setRenderBounds(
            			center - woodWidth/2, 0, center - woodWidth/2,
            			center + woodWidth/2, woodHeight, center + woodWidth/2);
            	renderer.renderStandardBlock(this, x, y, z);
      		}        	
    	}
    	woodPass = false;

    	leavesPass = true;
    	renderLeaves(renderer, x, y, z, getIcon(0,meta));
    	leavesPass = false;
    	return true;
    }
    
    @Override
    public void RenderBlockSecondPass(RenderBlocks renderer, int i, int j, int k, boolean bFirstPassResult) {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	if (meta > OLIVE)
    	{
    		if (meta == FLOWER_APPLE) renderLeaves(renderer, i, j, k, flower[0]);
    		if (meta == FLOWER_CHERRY) renderLeaves(renderer, i, j, k, flower[1]);
    		if (meta == FLOWER_LEMON) renderLeaves(renderer, i, j, k, flower[2]);
    		if (meta == FLOWER_OLIVE) renderLeaves(renderer, i, j, k, flower[3]);
    	}
    }

	protected void renderLeaves(RenderBlocks renderer, int x, int y, int z, Icon leavesIcon) {
		boolean connectW = false;
    	boolean connectE = false;    	
    	boolean connectN = false;    	
    	boolean connectS = false;    	
    	
		if (CanConnectToBlockToFacing(renderer.blockAccess, x, y, z, WEST)) connectW = true;
		if (CanConnectToBlockToFacing(renderer.blockAccess, x, y, z, EAST)) connectE = true;
		if (CanConnectToBlockToFacing(renderer.blockAccess, x, y, z, NORTH)) connectN = true;
		if (CanConnectToBlockToFacing(renderer.blockAccess, x, y, z, SOUTH)) connectS = true;
    	
    	if (connectN)
    	{
    		widthAdjustmentMinZn = 3/16F;
    		widthAdjustmentMaxZn = 0/16F;
    	}
    	
    	if (connectS) 
    	{
    		widthAdjustmentMinZs = 0/16F;
    		widthAdjustmentMaxZs = 3/16F;
    	}
    	
    	if (connectW)
    	{
    		widthAdjustmentMinXw = 3/16F;
    		widthAdjustmentMaxXw = 0/16F;
    	}
    	
    	if (connectE)
    	{
    		widthAdjustmentMinXe = 0/16F;
    		widthAdjustmentMaxXe = 3/16F;
    	}
    	
    	leavesPass = true;
    	
    	//leaves
    	renderer.setRenderBounds(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, leavesHeight, center + leavesWidth/2);
    	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, leavesIcon);
    	
    	leavesPass = false;
    	

    	
    	nPass = true;
    	if (connectN)
    	{
        	renderer.setRenderBounds(
        			center - leavesWidth/2 , 0, center - leavesWidth/2 - widthAdjustmentMinZn,
        			center + leavesWidth/2 , leavesHeight, center - leavesWidth/2 + widthAdjustmentMaxZn);
        	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, leavesIcon);
    	}
    	nPass = false;
    	
    	sPass = true;
    	if (connectS)
    	{
        	renderer.setRenderBounds(
        			center - leavesWidth/2 , 0, center + leavesWidth/2 - widthAdjustmentMinZs,
        			center + leavesWidth/2 , leavesHeight, center + leavesWidth/2 + widthAdjustmentMaxZs);
        	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, leavesIcon);
    	}
    	sPass = false;
    	
    	wPass = true;
    	if (connectW)
    	{
        	renderer.setRenderBounds(
        			center - leavesWidth/2 - widthAdjustmentMinXw , 0, center - leavesWidth/2,
        			center - leavesWidth/2 + widthAdjustmentMaxXw , leavesHeight, center + leavesWidth/2);
        	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, leavesIcon);
    	}
    	wPass = false;
    	
    	ePass = true;
    	if (connectE)
    	{
        	renderer.setRenderBounds(
        			center + leavesWidth/2 - widthAdjustmentMinXe , 0, center - leavesWidth/2,
        			center + leavesWidth/2 + widthAdjustmentMaxXe , leavesHeight, center + leavesWidth/2);
        	FCClientUtilsRender.RenderStandardBlockWithTexture(renderer, this, x, y, z, leavesIcon);
    	}
    	ePass = false;
	}
    
    @Override
    public void RenderBlockAsItem(RenderBlocks renderer, int damage, float brightness) {
    	
    	//wood
    	renderer.setRenderBounds(
    			center - woodWidth/2, 0, center - woodWidth/2,
    			center + woodWidth/2, woodHeight, center + woodWidth/2);
    	FCClientUtilsRender.RenderInvBlockWithMetadata(renderer, this, -center, -center, -center, damage);
    	
        int color = colorMultiplierForItemRender(damage);
        
        float var8 = (float)(color >> 16 & 255) / 255.0F;
        float var9 = (float)(color >> 8 & 255) / 255.0F;
        float var10 = (float)(color & 255) / 255.0F;
        
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
    	
    	//leaves
    	renderer.setRenderBounds(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, leavesHeight, center + leavesWidth/2);
    	FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, leaves[damage]);
    	
    	if (damage >= FLOWER_APPLE)
    	{
        	//flowers
        	renderer.setRenderBounds(
        			center - leavesWidth/2, 0, center - leavesWidth/2,
        			center + leavesWidth/2, leavesHeight, center + leavesWidth/2);
        	if (damage == FLOWER_APPLE) FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, flower[0]);
        	if (damage == FLOWER_CHERRY) FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, flower[1]);
        	if (damage == FLOWER_LEMON) FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, flower[2]);
        	if (damage == FLOWER_OLIVE) FCClientUtilsRender.RenderInvBlockWithTexture(renderer, this, -center, -center, -center, flower[3]);
    	}

    	
    }
    
    

}
