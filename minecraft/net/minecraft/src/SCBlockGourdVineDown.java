package net.minecraft.src;

import java.util.Random;

public class SCBlockGourdVineDown extends SCBlockGourdVine {
	
	private int vineBlock;
	
	protected SCBlockGourdVineDown(int iBlockID, int stemBlock, int vineBlock, int convertedBlock, int hangingVine, String leavesTex, String topTex, String bottomTex) {
		super(iBlockID, 0, stemBlock, convertedBlock, hangingVine, null, null, "SCBlockGourdLeaf_");
		
		this.vineBlock = vineBlock;
		
		setHardness( 0F );
		
		setUnlocalizedName("SCBlockGourdVineDown");
		
		this.leavesTex = leavesTex;
		this.topTex = topTex;
		this.bottomTex = bottomTex;
		
	}

	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {	
		if (!this.canBlockStay(world, i, j, k))
		{
			this.convert(world, i, j, k);
		}
		else
		{
			if (!IsFullyGrown( world, i, j, k) && checkTimeOfDay(world)) //daytime
			{
				int growthStage = this.GetGrowthLevel(world, i, j, k);
				
				if (rand.nextFloat() <= this.GetBaseGrowthChance())
		    	{
					
					if (growthStage < 2)
					{
						this.grow(world, i, j, k, rand);
					}
					else if (growthStage == 2)
					{
						if (world.doesBlockHaveSolidTopSurface(i, j - 1, k))
						{
							this.grow(world, i, j, k, rand);
							
							this.attemptToGrowVine(world, i, j, k, rand);
						}
					}
		    		
		    	}
			}

		}
    }
	
	protected void convert(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int growthLevel = this.GetGrowthLevel(meta);
		int blockID = world.getBlockId(i, j, k);

		if (growthLevel == 0) 
		{
			world.setBlock(i, j, k, 0);
			
		}
		
		world.setBlockAndMetadataWithNotify(i, j, k, convertedBlockID, meta);
	}
	
  	protected void attemptToGrowVine(World world, int i, int j, int k, Random random)
  	{
  		int dir = this.getDirection(world.getBlockMetadata(i, j, k));

          int sideA = dir;
          int sideB = Direction.rotateLeft[sideA];
          int sideC = Direction.rotateOpposite[sideB];

          int sideFinal;
          float randomFloat = random.nextFloat();
          
          if (randomFloat < 0.80)
          {
              sideFinal=sideA;
          }
          else if (randomFloat < 0.80 + 0.1)
          {
              sideFinal = sideB;
          }
          else
          {
              sideFinal = sideC;
          }
  		
  		int offsetI = Direction.offsetX[sideFinal];
  		int offsetK = Direction.offsetZ[sideFinal];
  		
  		
  		int finalI = i + offsetI;
  		int finalK = k + offsetK;
  		
  		if( CanGrowVineAt( world, finalI, j, finalK ) )
  		{
  			world.setBlockAndMetadata(finalI, j, finalK, vineBlock, sideFinal);
  		}
  		else
  		{
  			if (world.getBlockId(finalI, j, finalK) == 0 && world.getBlockId(finalI, j - 1, finalK) == 0)
  			{
  				world.setBlockAndMetadata(finalI, j - 1, finalK, this.blockID, sideFinal);
  			}
  		}
  	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack stack)
    {
		int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
		
		world.setBlockAndMetadata(i, j, k, this.blockID, playerRotation);
	}
	
	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return true; // CanGrowOnBlock( world, i, j - 1, k ); // && hasStemFacing(world, i, j + 1, k);
    }
	
	protected boolean hasStemFacing( World world, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	    int dir = this.getDirection(world.getBlockMetadata(i, j, k));
	    
	    
	    int oppositeFacing = Direction.rotateOpposite[dir];
	    int iTargetFacing = Direction.directionToFacing[oppositeFacing];
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = world.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    Block block = Block.blocksList[targetBlockID];
	    	
	    if (block instanceof SCBlockGourdVineDead || block instanceof SCBlockGourdVineDead)
	    {
	    	return false;
	    }
	    
	    if (block instanceof SCBlockGourdVineDown || block instanceof SCBlockGourdVine || block instanceof SCBlockGourdGrowing || targetBlockID == this.stemBlock)
	    {
	    	//System.out.println(block.blockID);
	    	return true;
	    	
	    }
	    else return false;
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool( World world, int i, int j, int k )
	{
		return null;
	}
	
    @Override
    public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
    	int dir = this.getDirection(blockAccess.getBlockMetadata(i, j, k));
    	
    	if (growthLevel == 0)
		{
    		if (dir == 1) 
    		{
    			return GetVineBounds(14, 8, 4, 16, 8);
    		}
    		else if (dir == 2) 
    		{
    			return GetVineBounds(4, 8, 14, 8, 16); //"north"
    		}
    		else if (dir == 3) 
    		{
    			return GetVineBounds(14, 8, 4, 0, 8);
    		}
    		else return GetVineBounds(4, 8, 14, 8, 0); //"south"

		}
		else if (growthLevel == 1)
		{
			return GetVineBounds(6, 6, 6);
		}
		else if (growthLevel == 2)
		{
			return GetVineBounds(10, 8, 10);
		}
		else return GetVineBounds(12, 8, 12);
    		
    }
	//RENDER 
	
	public Icon[] vineIcons;
	public Icon[] connectorIcons;
	public Icon[] top = new Icon[4];
	public Icon[] bottom = new Icon[4];

	public static String leavesTex;
	public static String topTex;
	public static String bottomTex;
	
    @Override
    public void registerIcons( IconRegister register )
    {
    	super.registerIcons(register);
    	
    	vineIcons = new Icon[4];
    	
        for ( int iTempIndex = 0; iTempIndex < vineIcons.length; iTempIndex++ )
        {
        	vineIcons[iTempIndex] = register.registerIcon( leavesTex + iTempIndex );
//        	vineIcons[iTempIndex] = register.registerIcon( "stone" );
        }

        
        blockIcon = vineIcons[3]; // for block hit effects and item render

        for ( int iTempIndex = 0; iTempIndex < top.length; iTempIndex++ )
        {

        	top[iTempIndex] = register.registerIcon(topTex + iTempIndex);
        	bottom[iTempIndex] = register.registerIcon(bottomTex + iTempIndex);
        }   
    }
    
	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
        return vineIcons[growthLevel];
    }
	
    @Override
    public boolean RenderBlock(RenderBlocks r, int i, int j, int k) {
    	int meta = r.blockAccess.getBlockMetadata( i, j, k );
    	if (GetGrowthLevel(meta) > 1)
    	{
    		r.renderCrossedSquares(this, i, j, k);
    		this.renderGourdLeaf(r, i, j, k);
    	}

    	if (this.hasStemFacing(r, i, j, k, 1))
    	{
        	this.renderVineConnector( r, i, j, k);    
        	
    	}
    	return true;
    }
    
	protected boolean hasStemFacing( RenderBlocks r, int i, int j, int k, int shift )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	    int dir = this.getDirection(r.blockAccess.getBlockMetadata(i, j, k));
	    
	    
	    int oppositeFacing = Direction.rotateOpposite[dir];
	    int iTargetFacing = Direction.directionToFacing[oppositeFacing];
	    
	    
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = r.blockAccess.getBlockId(targetPos.i, targetPos.j + shift, targetPos.k);
	    
	    Block block = Block.blocksList[targetBlockID];
	    if ( block instanceof SCBlockGourdVine || block instanceof SCBlockGourdGrowing || targetBlockID == this.stemBlock)
	    {	
	    	return true;
	    	
	    }
	    else return false;
		
    }


	public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        
        tess.setColorOpaque_F(1F, 1F, 1F);
        
        int growthLevel = this.GetGrowthLevel(blockAccess.getBlockMetadata(par2, par3, par4));
        this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), par2, par3, par4, 1.0F, bottom[growthLevel]);
        this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), par2, par3 + 1, par4, 1.0F, top[growthLevel]);
        
		return true;
    }
	
	public void drawConnector(Block block, int meta, double x, double y, double z, float scale, Icon icon)
    {
        Tessellator tess = Tessellator.instance;
        int growthLevel = this.GetGrowthLevel(meta);
        
        int dir = meta & 3;
        
        double minU = (double)icon.getMinU();
        double minV = (double)icon.getMinV();
        double maxU = (double)icon.getMaxU();
        double maxV = (double)icon.getMaxV();
        double var20 = 0.45D * (double)scale;
        
        //dir 0: North
        double minX = x + 0.5D;// - var20;
        double maxX = x + 0.5D;// + var20;
        double minZ = z - 0.5D;// - var20;
        double maxZ = z + 0.5D;// + var20;
        
        
        if (dir == 3) { //west
            minX = x - 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
            
        } else if (dir == 2) {  //South 	
            minX = x + 0.5D;// - var20;
            maxX = x + 0.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 1.5D;// + var20;
            
        } else if (dir == 1) { //east  	
            minX = x + 0.5D;// - var20;
            maxX = x + 1.5D;// + var20;
            minZ = z + 0.5D;// - var20;
            maxZ = z + 0.5D;// + var20;
        }
        
        
        if (dir == 3 || dir == 0) {
            tess.addVertexWithUV(minX, y + (double)scale, minZ, minU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, maxU, minV);
            
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, maxU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, minU, maxV);
            tess.addVertexWithUV(minX, y + (double)scale, minZ, minU, minV);
        } else {
            tess.addVertexWithUV(minX, y + (double)scale, minZ, maxU, minV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(maxX, y + 0.0D, 		  maxZ, minU, maxV);
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, minU, minV);
            
            tess.addVertexWithUV(maxX, y + (double)scale, maxZ, minU, minV);
            tess.addVertexWithUV(maxX, y + 0.0D,		  maxZ, minU, maxV);
            tess.addVertexWithUV(minX, y + 0.0D, 		  minZ, maxU, maxV);
            tess.addVertexWithUV(minX, y + (double)scale, minZ, maxU, minV);
        }

    }
}
