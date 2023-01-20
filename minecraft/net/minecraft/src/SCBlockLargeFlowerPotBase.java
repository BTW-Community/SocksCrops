package net.minecraft.src;

public abstract class SCBlockLargeFlowerPotBase extends BlockContainer {
	
	protected SCModelBlockLargeFlowerPot m_model;
    
	protected FCModelBlock m_modelTransformed;
	
	protected SCBlockLargeFlowerPotBase(int par1) {
		super(par1, FCBetterThanWolves.fcMaterialMiscellaneous);
		setHardness( 0F );
		setResistance( 0F );
		InitBlockBounds(0.5D - SCModelBlockLargeFlowerPot.halfWidth, 0D, 0.5D - SCModelBlockLargeFlowerPot.halfLength,
				0.5D + SCModelBlockLargeFlowerPot.halfWidth, SCModelBlockLargeFlowerPot.height,
				0.5D + SCModelBlockLargeFlowerPot.halfLength);

    	InitModels();
	}

	
	@Override
    public int onBlockPlaced( World world, int i, int j, int k, int targetFace, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {

		System.out.println("TargetFace: " + targetFace);
		
		if (targetFace > 1)
		{
			return targetFace;
		}
		else return 0;
    }
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack stack) {

		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta == 0)
		{
			int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 2;
			int playerPitch = ((MathHelper.floor_double((double)(player.rotationPitch % 360.0F))));

			//always let player place bucket straight, as any sane person would. :P

			int playerFacing = Direction.directionToFacing[playerRotation];

			if (!world.isRemote)
			{
				System.out.println("Rot: " + playerRotation);
				System.out.println("Facing: " + playerFacing);
				System.out.println("Pitch: " + playerPitch);
			}

			SetFacing(world, i, j, k, playerRotation);
			
			if (!world.isRemote)
			{
				System.out.println("my meta: " + world.getBlockMetadata(i, j, k));
			}
		}
		

		
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
	
//	@Override
//    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
//    {
//        return null;
//    }

    @Override
    public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
    {
    	m_modelTransformed = m_model.MakeTemporaryCopy();
    	
    	int meta = world.getBlockMetadata(i, j, k);
    	
    	if (meta > 1)
    	{
    		int iFacing = GetFacing( world, i, j, k );
    		
        	m_modelTransformed.RotateAroundJToFacing(iFacing);
        	
        	SCModelBlockLargeFlowerPot.OffsetModelForFacing( m_modelTransformed, iFacing );
    	}
    	else if (meta == 1)
    	{
    		m_modelTransformed.RotateAroundJToFacing(4);
    	}
    	else m_modelTransformed.RotateAroundJToFacing(2);
    	
    	return m_modelTransformed.CollisionRayTrace( world, i, j, k, startRay, endRay );    	
    }
    
	@Override
	public int GetFacing( int iMetadata )
	{		
    	return iMetadata & 7;
	}
	
	@Override
	public int SetFacing( int iMetadata, int iFacing )
	{
		iMetadata &= ~7;
		
        return iMetadata | iFacing;
	}
	
	protected void InitModels()
	{
		m_model = new SCModelBlockLargeFlowerPot();
	    
		// must initialize transformed model due to weird vanilla getIcon() calls that 
		// occur outside of regular rendering
		
		m_modelTransformed = m_model; 
	}
	
	private Icon dirt;
	
	@Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = register.registerIcon( "fcBlockCookedBrick" );
        dirt = register.registerIcon( "dirt" );
        
    }
	
	@Override
    public boolean shouldSideBeRendered( IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide )
    {
		return true;
    }
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
		if ( m_modelTransformed.GetActivePrimitiveID() == m_model.assemblyIDContents )
		{
			return dirt;
		}
		
		return super.getIcon( iSide, iMetadata );
    }
	
	@Override
    public boolean RenderBlock( RenderBlocks renderBlocks, int i, int j, int k )
    {
    	m_modelTransformed = m_model.MakeTemporaryCopy();
    	
    	int meta = renderBlocks.blockAccess.getBlockMetadata(i, j, k);
    	
    	if (meta > 1)
    	{
    		int iFacing = GetFacing( renderBlocks.blockAccess, i, j, k );
    		
        	m_modelTransformed.RotateAroundJToFacing(iFacing);
        	
        	SCModelBlockLargeFlowerPot.OffsetModelForFacing( m_modelTransformed, iFacing );
    	}
    	else if (meta == 1)
    	{
    		m_modelTransformed.RotateAroundJToFacing(4);
    	}
    	else m_modelTransformed.RotateAroundJToFacing(2);
    	
    	return m_modelTransformed.RenderAsBlock( renderBlocks, this, i, j, k );
    }
    
    @Override
    public void RenderBlockAsItem( RenderBlocks renderBlocks, int iItemDamage, float fBrightness )
    {
    	m_modelTransformed = m_model; 
    	
    	m_modelTransformed.RenderAsItemBlock( renderBlocks, this, iItemDamage );
    }
    
    private static AxisAlignedBB m_selectionBox = new AxisAlignedBB( 
    		0.5D - SCModelBlockLargeFlowerPot.halfWidth, 0, 
    		0.5D - SCModelBlockLargeFlowerPot.halfLength, 
    		0.5D + SCModelBlockLargeFlowerPot.halfWidth, SCModelBlockLargeFlowerPot.height, 
    		0.5D + SCModelBlockLargeFlowerPot.halfLength );
        	
    	@Override
        public AxisAlignedBB getSelectedBoundingBoxFromPool( World world, MovingObjectPosition rayTraceHit )
        {
    		int i = rayTraceHit.blockX;
    		int j = rayTraceHit.blockY;
    		int k = rayTraceHit.blockZ;
    		
    		int iFacing = GetFacing( world, i, j, k );
    		
    		AxisAlignedBB tempBox = m_selectionBox.MakeTemporaryCopy();
    		
        	int meta = world.getBlockMetadata(i, j, k);
        	
        	if (meta > 1)
        	{
    			tempBox.RotateAroundJToFacing(iFacing);
    	    	
    			Vec3 offset = SCModelBlockLargeFlowerPot.GetOffsetForFacing( iFacing );
    			
    			tempBox.Translate( offset.xCoord, offset.yCoord, offset.zCoord );
        	}
        	else if (meta == 1)
        	{
        		tempBox.RotateAroundJToFacing(4);
        	}
        	else tempBox.RotateAroundJToFacing(2);
    		
    		
    		return tempBox.offset( i, j, k );
        }
	
}
