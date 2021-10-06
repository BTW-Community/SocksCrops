package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeLeaves extends Block implements SCIRope {

	EntityPlayer player;
	
	protected SCBlockGrapeLeaves(int par1) {
		super(par1, Material.leaves);
		this.setUnlocalizedName("SCBlockGrapeLeaves");
		this.setTickRandomly(true);
		this.setHardness( 0.2F );        
	    this.SetAxesEffectiveOn( true );
        this.setLightOpacity( 1 );
        this.SetFireProperties( FCEnumFlammability.LEAVES );
        this.setStepSound( soundGrassFootstep );
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void onNeighborBlockChange( World world, int i, int j, int k, int iNeighborBlockID )
    {
	
    }

	public void SetExtendsAlongAxis( World world, int i, int j, int k, int iAxis, boolean bExtends, boolean bNotify )
	{
		int iMetadata = world.getBlockMetadata( i, j, k ) & (~(1 << iAxis)); // filter out old value
		
		if ( bExtends )
		{
			iMetadata |= 1 << iAxis;
		}
		
		if ( bNotify )
		{
			world.setBlockMetadataWithNotify( i, j, k, iMetadata );
		}
		else
		{
			world.setBlockMetadataWithClient( i, j, k, iMetadata );
		}
	}
	
	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
        return true;
    }
    
    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	int iMetadata = world.getBlockMetadata(i, j, k);
    	
        if ( stack.getItem().itemID == Item.shears.itemID)
        {
            world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.fenceRope.blockID, iMetadata);
            System.out.println("shears");
        }
        return true;
    }
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
        //checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy
        
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
            if ( world.getBlockLightValue( i, j + 1, k ) >= 9 && random.nextInt( 1 ) == 0 ) //was 64 for saplings
            {
                int iMetadata = world.getBlockMetadata( i, j, k );
                int iGrowthStage = iMetadata +4; //( iMetadata & (~3) ) >> 2; Added 4 to avoid metadata conflict

                if ( iGrowthStage < 1 )
                {
                	iGrowthStage++;
                	//iMetadata = ( iMetadata & 3 ) | ( iGrowthStage << 2 );
                	
                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );
                }
                else
                {
                	if (world.getBlockId(i, j - 1, k) == 0) {
                		this.growGrapes( world, i, j -1, k, random );
                	}
                	
                	int ropeID = SCDefs.fenceRope.blockID;
                	int meta;
                	
                	if (world.getBlockId(i +1, j, k) == ropeID) { 
                		
                		meta = world.getBlockMetadata(i +1, j, k);
                		this.growVines( world, i +1, j, k, random, iMetadata );
                	}
                	else if (world.getBlockId(i - 1, j, k) == ropeID) { 
                		
                		meta = world.getBlockMetadata(i - 1, j, k);
                		this.growVines( world, i -1, j, k, random, iMetadata );
                	}
                	else if (world.getBlockId(i, j, k +1 ) == ropeID) { 
                		
                		meta = world.getBlockMetadata(i, j, k + 1);
                		this.growVines( world, i, j, k +1, random, iMetadata );
                	}
                	else if (world.getBlockId(i, j, k -1) == ropeID) { 
                		meta = world.getBlockMetadata(i, j, k -1);
                		this.growVines( world, i, j, k -1, random, iMetadata );
                	}
                }
            }
        }
    }
	
	public void growVines( World world, int i, int j, int k, Random random, int iMetadata )
    {	
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID, iMetadata);
		System.out.println("vines grow");
    }
	
	public void growGrapes( World world, int i, int j, int k, Random random)
    {	
		world.setBlockWithNotify(i, j, k, SCDefs.grapeBlock.blockID);
		System.out.println("grapes grow");
    }
	
	public boolean HasValidAttachmentPointToFacing( World world, int i, int j, int k, int iFacing )
	{
		FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
		
		targetPos.AddFacingAsOffset( iFacing );
		
		int iTargetBlockID = world.getBlockId( targetPos.i, targetPos.j, targetPos.k );
		
		if ( iTargetBlockID == blockID )
		{
			if ( GetExtendsAlongFacing( world, targetPos.i, targetPos.j, targetPos.k, iFacing ) )
			{
				return true;
			}
		}
		else if ( iTargetBlockID == SCDefs.fence.blockID )
		{
			return true;
		}
		else if ( Block.blocksList[world.getBlockId(targetPos.i, targetPos.j, targetPos.k)] instanceof SCIRope )
		{
			if ( GetExtendsAlongFacing( world, targetPos.i, targetPos.j, targetPos.k, iFacing ) )
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean GetExtendsAlongAxis( IBlockAccess blockAccess, int i, int j, int k, int iAxis )
	{
		return GetExtendsAlongAxisFromMetadata( blockAccess.getBlockMetadata( i, j, k ), iAxis );		
	}
	
	public boolean GetExtendsAlongFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{
		return GetExtendsAlongAxis( blockAccess, i, j, k, ConvertFacingToAxis( iFacing ) );
	}
	
	static public int ConvertFacingToAxis( int iFacing )
	{
		if ( iFacing == 4 || iFacing == 5 )
		{
			return 0;
		}
		else if ( iFacing == 0 || iFacing == 1 )
		{
			return 1;
		}
		else
		{
			return 2;
		}
	}
	
	public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
		super.RenderBlock(renderer, i, j, k);
		renderer.renderBlockCrops(SCDefs.grapeDropLeaves, i, j-1, k);
    
		IBlockAccess blockAccess = renderer.blockAccess;
    	
    	for ( int iAxis = 0; iAxis < 3; iAxis++ )
    	{
    		if ( GetExtendsAlongAxis( blockAccess, i, j, k, iAxis ) )
    		{
    			SCBlockFenceRope.SetRenderBoundsForAxis( renderer, iAxis );
    			
    			FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
    		}
    	}
    	
    	if (SCBlockFenceRope.HasStemBelow(blockAccess, i, j, k)) {
    		renderer.setRenderBounds (SCBlockFenceRope.GetRopeKnotBounds());
    		FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
			
    		renderer.setRenderBounds( SCBlockFenceRope.GetVerticalRopeBounds(1.0D));			
    		FCClientUtilsRender.RenderStandardBlockWithTexture( renderer, this, i, j, k, SCDefs.fenceRope.blockIcon );
			
		}
		
		
    	return true;
    	
    	
    	
    }
	
	private void SetRenderBoundsForAxis( RenderBlocks renderBlocks, int iAxis )
	{
    	Vec3 min = Vec3.createVectorHelper( 0, 0, 0 );
    	Vec3 max = Vec3.createVectorHelper( 0, 0, 0 );
    	
    	GetBlockBoundsForAxis( iAxis, min, max, 0.125D / 16D );
    	
    	renderBlocks.setRenderBounds( (float)min.xCoord, (float)min.yCoord, (float)min.zCoord, (float)max.xCoord, (float)max.yCoord, (float)max.zCoord );    	
	}
	
	private void GetBlockBoundsForAxis( int iAxis, Vec3 min, Vec3 max, double dHalfHeight )
	{
		if ( iAxis == 0 )
		{
			min.setComponents( 0F, 0.5F - dHalfHeight, 0.5F - dHalfHeight );
			max.setComponents( 1F, 0.5F + dHalfHeight, 0.5F + dHalfHeight );
		}
		else if ( iAxis == 1 )
		{
			min.setComponents( 0.5F - dHalfHeight, 0F, 0.5F - dHalfHeight ); 
			max.setComponents( 0.5F + dHalfHeight, 1F, 0.5F + dHalfHeight );
		}
		else // 2
		{
			min.setComponents( 0.5F - dHalfHeight, 0.5F - dHalfHeight, 0F ); 
    		max.setComponents( 0.5F + dHalfHeight, 0.5F + dHalfHeight, 1F );
		}
	}
	
	public boolean GetExtendsAlongAxisFromMetadata( int iMetadata, int iAxis )
	{
		return ( iMetadata & ( 1 << iAxis ) ) > 0;
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

}
