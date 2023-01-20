package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeStem extends FCBlockLog {
	
	public final int RED = 0;
	public final int WHITE = 1;
	
	private String type;
	private int leavesBlock;
	
	protected SCBlockGrapeStem(int iBlockID, int leavesBlock, String type) {
		super(iBlockID);
		this.type = type;
		this.leavesBlock = leavesBlock;
		
	    setHardness( 1.25F ); // vanilla 2
	    setResistance( 3.33F );  // odd value to match vanilla resistance set through hardness of 2
        
		SetAxesEffectiveOn();
		
        SetBuoyant();
		
		SetFireProperties( FCEnumFlammability.LOGS );
		
		InitBlockBounds(4/16F, 0F, 4/16F, 12/16F, 1F, 12/16F);
		
        setStepSound( soundWoodFootstep );
		
		
		setUnlocalizedName( "SCBlockGrapeStem" ); 
		setTickRandomly(true);
	}
	
	public float getGrowthChance() {
		return 8/16F;
	}
	
	@Override
	//Debug method
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!FCUtilsReflection.isObfuscated()) {
			if (!world.isRemote) {
				incrementGrowth(world, x, y, z, world.getBlockMetadata(x, y, z));
			}

			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		
		int meta = world.getBlockMetadata(x, y, z);
		int growthStage = getGrowthStage(meta);
		int fruitType = meta & 3;

		if (!FCUtilsTrees.CanSaplingGrowOnBlock(world, x, y - 1, z))
		{
			return;
		}
		
//		System.out.println("Hello");
//		System.out.println(growthStage);
				
		if (growthStage < 3)
		{
			if (random.nextFloat() <= getGrowthChance()) 
			{
				incrementGrowth(world, x, y, z, meta);
				
//				System.out.println("Growing");
				growthStage += 1;
				
//				System.out.println(growthStage);
				
				if (growthStage == 3)
				{
					growGrapeLeaves(world, x, y, z, fruitType);
				}
			}
		}
	}
	
    private void growGrapeLeaves(World world, int x, int y, int z, int fruitType) {

//    	System.out.println("Growing Leaves");
    	
    	if (world.getBlockId(x, y + 1, z) == SCDefs.fenceRope.blockID)
    	{
//    		System.out.println("I have rope");
    		
    		int metaRope = world.getBlockMetadata(x, y + 1, z);
    		
        	int grapeLeavesID = leavesBlock;
        	
        	world.setBlockAndMetadataWithNotify(x, y + 1, z, grapeLeavesID, metaRope);
    	}

	}
	
	protected void incrementGrowth(World world, int x, int y, int z, int meta) {
		world.setBlockMetadataWithNotify(x, y, z, meta + 4);
	}
	
    public boolean IsWorkStumpItemConversionTool( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
	
    @Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return false;
    }
    
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	return false;
    }
	
	@Override
    public boolean GetIsStump( int iMetadata )
    {
    	return true;
    }  
	
	private int getGrowthStage(int meta)
	{
		if (meta < 4) return 0;
		else if (meta >= 4 && meta < 8) return 1;
		else if (meta >= 8 && meta < 12) return 2;
		else return 3;

	}
		
	public void ConvertToSmouldering( World world, int i, int j, int k )
	{
		int meta = SCBlockFruitTreesLogSmoldering.setIsStump( 0, GetIsStump( world, i, j, k ) );
		
		if (world.getBlockId(i, j, k) != SCDefs.fruitBranch.blockID)
		{			
			world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.fruitLogSmoldering.blockID, meta );
		}

	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int neighbourID)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int fruitType = meta & 3;
		
		//Convert stump to non growing if the blocks above are not valid/ get removed
		
//		if (world.getBlockId(x, y + 1, z) == 0)
//		{
//			world.setBlockAndMetadata(x, y, z, this.blockID, fruitType + 12);
//		}
	}

	public static double stemWidth = 4/16D;
	public static double stemHeight = 12/16D;
	public static double leavesWidth = 8/16D;
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
		
		return getBounds(0.5F, 0.5F, 0.5F, stemWidth, stemHeight, stemWidth);
	}
	
    private AxisAlignedBB getBounds(double centerX, double centerY, double centerZ, double width, double height, double depth)
	{
    	return AxisAlignedBB.getAABBPool().getAABB( 
			centerX - width/2, centerY - height/2, centerZ - depth/2, 
    		centerX + width/2, centerY + height/2, centerZ + depth/2 );
	}
	
    private AxisAlignedBB getStemBounds(int meta) {
    	
    	int growthStage = getGrowthStage(meta);
    	
    	double centerY = 0.5F;
    	
    	switch (growthStage) {
    	default:
    	case 0:
    		stemWidth = 4/16F;
    		stemHeight = 10/16F;
    		break;
    		
    	case 1:
    		stemWidth = 6/16F;
    		stemHeight = 12/16F;
    		break;
    		
    	case 2:
    		stemWidth = 8/16F;
    		stemHeight = 14/16F;
    		
    	case 3:
    		stemWidth = 8/16F;
    		stemHeight = 16/16F;
    		break;
		}
    	
    	centerY = stemHeight/2;
    	
    	return getBounds(0.5F, centerY, 0.5F, stemWidth, stemHeight, stemWidth);
	}
    
    private AxisAlignedBB getLeavesBounds(int meta) {
    	
    	int growthStage = getGrowthStage(meta);
    	double centerX = 0.5F;
    	double centerY = 0.5F;
    	double centerZ = 0.5F;
    	
    	double leavesHeight = 8/16F;
    	
    	switch (growthStage) {
    	default:
    	case 0:
    		leavesWidth = 8/16F;
    		leavesHeight = 8/16F;
    		
    		centerY = 10/16F;
    		break;
    		
    	case 1:
    		leavesWidth = 10/16F;    		
    		leavesHeight = 9/16F;
    		
    		centerY = 23/32F;
    		break;
    		
    	case 2:
    		leavesWidth = 12/16F;
    		leavesHeight = 8/16F;
    		
    		centerY = 12/16F;
    		break;
    		
    	case 3:
    		leavesWidth = 14/16F;
    		leavesHeight = 7/16F;
    		
    		centerY = 25/32F;
    		break;
		}
    	
    	return getBounds(centerX, centerY, centerZ, leavesWidth, leavesHeight, leavesWidth);
	}
    
    private AxisAlignedBB getLeavesBoundsTop(int meta) {
    	
    	int growthStage = getGrowthStage(meta);
    	double centerX = 0.5F;
    	double centerY = 0.5F;
    	double centerZ = 0.5F;
    	
    	double leavesHeight = 8/16F;
    	
    	switch (growthStage) {
    	default:
    	case 0:
    	case 1:
    		leavesWidth = 10/16F;
    		leavesHeight = 2/32F;
    		
    		centerY = 1/32F;
    		break;
    		
    	case 2:
    		leavesWidth = 12/16F;
    		leavesHeight = 4/16F;
    		
    		centerY = 2/16F;
    		break;
    		
    	case 3:
    		leavesWidth = 14/16F;
    		leavesHeight = 7/16F;
    		
    		centerY = 7/32F;
    		break;
		}
    	
    	return getBounds(centerX, centerY, centerZ, leavesWidth, leavesHeight, leavesWidth);
	}
    
//----------- Client Side Functionality -----------//
    
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
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int side) {
    	if (logPass) return true;
    	
    	if (leavesPass) return true;
		
    	if (topLeavesPass)
		{
			if (side == 0) return false;
		}
		
		if (bottomLeavesPass)
		{			
			if (side == 1) return false;
		}
		
    	return true;
    }
    

  

    protected Icon stumpIcon;
    protected Icon leaves;
    
    @Override
    public void registerIcons( IconRegister iconRegister )
    {
    	blockIcon = stumpIcon = iconRegister.registerIcon("SCBlockGrapeWood_" + type);
    	
    	leaves = iconRegister.registerIcon("SCBlockGrapeLeaves_" + type);
    }
    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {    	
    	return blockIcon;
	}
    
    boolean logPass;
    boolean leavesPass;
    boolean topLeavesPass;
    boolean bottomLeavesPass;
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	logPass = true;
    	//stump
    	renderer.setRenderBounds( getStemBounds(meta) );    	
    	renderer.renderStandardBlock( this, i, j, k );
    	logPass = false;
    	
    	Block blockAbove = Block.blocksList[renderer.blockAccess.getBlockId(i, j + 1, k)];
    	
    	if (blockAbove != null && blockAbove instanceof SCBlockGrapeLeaves )
    	{
    		//Return and don't render Leaves
    		return true;
    	}
    	
    	//leaves    	
    	renderer.setOverrideBlockTexture(leaves);
    	
    	if (getGrowthStage(meta) != 0)
    	{   
    		topLeavesPass = true;
        	renderer.setRenderBounds(getLeavesBoundsTop(meta));
        	renderer.renderStandardBlock( this, i, j + 1, k );
        	topLeavesPass = false;
        	
        	bottomLeavesPass = true;
        	renderer.setRenderBounds(getLeavesBounds(meta));
        	renderer.renderStandardBlock( this, i, j, k );
        	bottomLeavesPass = false;
    	}
    	else
    	{
    		leavesPass = true;
        	renderer.setRenderBounds(getLeavesBounds(meta));
        	renderer.renderStandardBlock( this, i, j, k );
        	leavesPass = false;
    	}
    	
    	renderer.clearOverrideBlockTexture();

    	return true;
    }
    
}
