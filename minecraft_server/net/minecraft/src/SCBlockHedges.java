package net.minecraft.src;

import java.util.List;

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
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {

    	boolean connectW = false;
    	boolean connectE = false;    	
    	boolean connectN = false;    	
    	boolean connectS = false;    	
    	
		if (CanConnectToBlockToFacing(blockAccess, i, j, k, NORTH)) connectN = true;
		if (CanConnectToBlockToFacing(blockAccess, i, j, k, SOUTH)) connectS = true;
		if (CanConnectToBlockToFacing(blockAccess, i, j, k, WEST)) connectW = true;
		if (CanConnectToBlockToFacing(blockAccess, i, j, k, EAST)) connectE = true;
		
		AxisAlignedBB box = new AxisAlignedBB(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, leavesHeight, center + leavesWidth/2
    			);
		
		return box;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
		return 	new AxisAlignedBB(
    			center - leavesWidth/2, 0, center - leavesWidth/2,
    			center + leavesWidth/2, leavesHeight + 0.5F, center + leavesWidth/2
    			).offset(i, j, k);
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
    	
        return false;
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

}
