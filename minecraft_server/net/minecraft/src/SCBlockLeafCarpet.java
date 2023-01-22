package net.minecraft.src;

import java.util.List;

public class SCBlockLeafCarpet extends FCBlockGroundCover {

	protected SCBlockLeafCarpet(int blockID) {
		super(blockID, Material.wood);
		InitBlockBounds( 0F, 0F, 0F, 1F, 2/16F, 1F);
		
        setHardness( 1/16F );
        SetShovelsEffectiveOn(false);
        SetAxesEffectiveOn();
 
		setCreativeTab(CreativeTabs.tabDecorations);
		setStepSound(soundGrassFootstep);
		setUnlocalizedName("SCBlockLeafCarpet");
	}

    @Override
    public boolean canPlaceBlockAt( World world, int i, int j, int k )
    {
    	int iBlockBelowID = world.getBlockId( i, j - 1, k );
    	Block blockBelow = Block.blocksList[iBlockBelowID];
    	
    	if ( blockBelow != null )
    	{
    		return blockBelow.CanGroundCoverRestOnBlock( world, i, j - 1, k ) || blockBelow.HasLargeCenterHardPointToFacing(world, i, j -1, k, 1);
    	}
    	
    	return false;
    }
	
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
	public int damageDropped(int meta) {
		return meta;
	}
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		return new AxisAlignedBB(
				0,0,0,
				1,2/16F,1);
	}
    
    public boolean IsGroundCoverRestingOnBlockForRender( IBlockAccess blockAccess, int i, int j, int k )
    {
    	Block blockAbove = Block.blocksList[blockAccess.getBlockId( i, j + 1, k )];
    	
    	if ( blockAbove != null )
    	{
    		if ( blockAbove.IsGroundCover() )
    		{
    			return true;
    		}
    		else if ( blockAbove.GroundCoverRestingOnVisualOffset( blockAccess, i, j + 1, k ) < -0.99F )
    		{
    			Block block2Above = blockAbove = Block.blocksList[blockAccess.getBlockId( i, j + 2, k )];
    	    	
    	    	if ( block2Above != null && block2Above.IsGroundCover() )
    	    	{
	    			return true;
    	    	}
    		}
    	}
    	
    	return false;
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


	public static String[] carpetTypes = {
			"oak", "spruce", "birch", "jungle",
			"blood", 
			"apple", "cherry", "lemon", "olive",
			"flower_apple", "flower_cherry", "flower_lemon", "flower_olive"};
		
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
    
}
