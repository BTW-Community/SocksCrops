package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVineFlowering extends SCBlockPumpkinVine {
	
	protected Block fruitBlock;
	
	protected SCBlockPumpkinVineFlowering(int iBlockID, Block fruitBlock) {
		super( iBlockID );
		setUnlocalizedName("SCBlockPumpkinVineFlowering");
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		
		if ( iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 ) //daytime
	    {
	        if ( canBlockStay( world, i, j, k ) )
	        {
	
	        	System.out.println("FLOWER: update tick");
	        	System.out.println("FLOWER: my meta is " + world.getBlockMetadata(i, j, k));
	        	int dir = this.GetFacing(world.getBlockMetadata(i, j, k));
	        	
	        	int GrowthLevel = GetGrowthLevel(world, i, j, k);
	        	System.out.println("FLOWER: my GrowthLevel is " + GrowthLevel);
	        	
		        if (GrowthLevel < 3) {
		        	System.out.println("FLOWER: trying to grow pumpkin");
		        	this.AttemptToGrowPumpkin(world, i, j, k, rand);
		        	this.AttemptToGrow(world, i, j, k, rand);
		
		        } else { //at growth stage 3 set sleeping
		        	world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVine.blockID, world.getBlockMetadata(i, j, k)); //TODO CHANGE BACK TO SLEEPING
		        }
		        
		        
		        
	        }
	    }
    }
	
	protected void AttemptToGrow(World world, int i, int j, int k, Random random) {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
        
        if (GrowthLevel < 3 && random.nextInt() <= 0.5F) {
        	//TODO CHANGE BACK TO SLEEPING
        	world.setBlockAndMetadataWithNotify(i, j, k, SCDefs.pumpkinVineFlowering.blockID ,meta + 4); //increase a growth stage and set sleeping
        	System.out.println("VINE: YES");
        }
		
	}
	
	protected void AttemptToGrowPumpkin(World world, int i, int j, int k, Random random) {
		Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
		int meta = world.getBlockMetadata(i, j, k);
		
		//Flowering stage
        FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
        int iTargetFacing = 0;
        
        if ( random.nextFloat() <= 0.5F) {
        
	        if ( HasSpaceToGrow( world, i, j, k ) )
	        {
	        	// if the plant doesn't have space around it to grow, 
	        	// the fruit will crush its own stem
	        	
	            iTargetFacing = random.nextInt( 4 ) + 2;
	        	
	            targetPos.AddFacingAsOffset( iTargetFacing );
	        }
	        
	        if ( CanGrowFruitAt( world, targetPos.i, targetPos.j, targetPos.k ) )
	        {	
	        	blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
	        	
	        	//world.setBlockWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	        	//		SCDefs.pumpkinFresh.blockID );
	        	System.out.println("grow Pumpkin");
	        	
	        	if ( iTargetFacing != 0 )
	        	{
	        		if (iTargetFacing == 2)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	            		SCDefs.pumpkinFresh.blockID, 2 ); //TODO change back to Sleeping
	    	    		
	    	    	} else if (iTargetFacing == 3)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	            		SCDefs.pumpkinFresh.blockID, 0 );//TODO change back to Sleeping
	    	    		
	    	    	} else if (iTargetFacing == 4)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	            		SCDefs.pumpkinFresh.blockID, 1 );//TODO change back to Sleeping
	    	    	} else if (iTargetFacing == 5)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	            		SCDefs.pumpkinFresh.blockID, 3 );//TODO change back to Sleeping
	    	    	}
	        		
	        		int dir = getDirection(meta);
	        		
	                world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.pumpkinVineFlowering.blockID ,dir + 12); //setting growth level to 0
	        	} 
	        }
        }
		
	}
	
//	@Override
//	public void updateTick( World world, int i, int j, int k, Random random )
//	{
//		
//	    //checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy
//	    
//	    System.out.println("update tick");
//	    
//	    if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
//	    {
//	        if ( world.getBlockLightValue( i, j + 1, k ) >= 9)
//	        {
//	            this.CheckForGrowth( world, i, j, k, random );
//	            System.out.println("checking growth for pumpkins");  
//	        }
//	    }
//	}
//	
	
	public void CheckForGrowth( World world, int i, int j, int k, Random rand )
	{
        if ( GetWeedsGrowthLevel( world, i, j, k ) == 0 &&
        	world.getBlockLightValue( i, j + 1, k ) >= 9 )
        {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null ) //IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k )
	        {
	    		float fGrowthChance = 0.8F * //was 0.2F
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		System.out.println("growth chance: " + fGrowthChance);
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	                int iMetadata = world.getBlockMetadata( i, j, k );
	
	                if ( iMetadata < 3 ) //was 14
	                {
	                	System.out.println("flower meta:" + iMetadata);
	                	
	                    ++iMetadata;
	                    
	                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );    
	                    System.out.println("metadata: " + iMetadata);
	                }
	                else if (iMetadata > 0 && iMetadata <= 3)
	                {
	                	//Flowering stage
	                    FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
	                    int iTargetFacing = 0;
	                    
	                    if ( HasSpaceToGrow( world, i, j, k ) )
	                    {
	                    	// if the plant doesn't have space around it to grow, 
	                    	// the fruit will crush its own stem
	                    	
		                    iTargetFacing = rand.nextInt( 4 ) + 2;
		                	
		                    targetPos.AddFacingAsOffset( iTargetFacing );
	                    }
	                    
	                    if ( CanGrowFruitAt( world, targetPos.i, targetPos.j, targetPos.k ) )
	                    {	
	                    	blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
	                    	
	                    	world.setBlockWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	                    			SCDefs.pumpkinFresh.blockID );
	                    	System.out.println("grow Pumpkin");
	                    	
	                    	if ( iTargetFacing != 0 )
	                    	{
	                    		SCDefs.pumpkinFresh.AttachToFacing( world, 
	                    			targetPos.i, targetPos.j, targetPos.k, 
	                    			Block.GetOppositeFacing( iTargetFacing ) );
	                    		
	                            world.setBlockMetadataWithNotify( i, j, k, 6 );
	                    	} 
	                    }
	                	
	                }
	            }
	        }
        }
	}
	
	protected boolean CanGrowFruitAt( World world, int i, int j, int k )
    {
		int iBlockID = world.getBlockId( i, j, k );		
		int iMeta = world.getBlockMetadata(i, j, k);
		
		Block block = Block.blocksList[iBlockID];
		
        if ( FCUtilsWorld.IsReplaceableBlock( world, i, j, k ) ||
        	( block != null && block.blockMaterial == Material.plants && 
    		iBlockID != Block.cocoaPlant.blockID && 
    		iBlockID != SCDefs.pumpkinStem.blockID && 
    		iBlockID != SCDefs.pumpkinVineFlowering.blockID &&
    		iBlockID == SCDefs.pumpkinVine.blockID && iMeta <= 3 ) )
        {
			// CanGrowOnBlock() to allow fruit to grow on tilled earth and such
			if ( world.doesBlockHaveSolidTopSurface( i, j - 1, k ) ||
				this.CanGrowOnBlock( world, i, j - 1, k ) ) 
            {				
				return true;
            }
        }
        
        return false;
    }   
	
	public boolean HasConnectedStringToFacing( IBlockAccess blockAccess, int i, int j, int k, int iFacing )
	{		
        FCBlockStakeString stringBlock = (FCBlockStakeString)( FCBetterThanWolves.fcBlockStakeString );
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
    	
    	targetPos.AddFacingAsOffset( iFacing );
    	
    	int iTargetBlockID = blockAccess.getBlockId( targetPos.i, targetPos.j, targetPos.k );
    	
    	if ( iTargetBlockID == stringBlock.blockID )
    	{
    		return stringBlock.GetExtendsAlongFacing( blockAccess, targetPos.i, targetPos.j, targetPos.k, iFacing );
    	}
    	
		return false;
	}
	
	//RENDER 
	
	private Icon[] m_iconArray;
	private Icon[] connectorIcon;

    @Override
    public void registerIcons( IconRegister register )
    {
        m_iconArray = new Icon[2];

        for ( int iTempIndex = 0; iTempIndex < m_iconArray.length; iTempIndex++ )
        {
            m_iconArray[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineFlowering_" + iTempIndex );
        }
        
        blockIcon = m_iconArray[0]; // for block hit effects and item render
        
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinVineConnector_" + iTempIndex );
        }
   
    }

	@Override
    public Icon getBlockTexture( IBlockAccess blockAccess, int i, int j, int k, int iSide )
    {
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
		if (growthLevel < 3) {
			return m_iconArray[1];
		}
		else return m_iconArray[0];
        
    }
		
	@Override
    public boolean RenderBlock(RenderBlocks r, int i, int j, int k) {
    	r.renderCrossedSquares(this, i, j, k);
    	
    	int iMetadata = r.blockAccess.getBlockMetadata( i, j, k );
    	
    	
        this.renderVineConnector( r, i, j, k);
        

    	return true;
    }
    
    public boolean renderVineConnector(RenderBlocks r, int par2, int par3, int par4)
    {
    	IBlockAccess blockAccess = r.blockAccess;
    	
    	Tessellator tess = Tessellator.instance;
        tess.setBrightness(this.getMixedBrightnessForBlock(blockAccess, par2, par3, par4));
        float var6 = 1.0F;
        int var7 = this.colorMultiplier(blockAccess, par2, par3, par4);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;
        this.drawConnector(this, blockAccess.getBlockMetadata(par2, par3, par4), par2, par3, par4, 1.0F);
        
		return true;

    }
    
    /**
     * Utility function to draw crossed swuares
     */
    public void drawConnector(Block block, int meta, double x, double y, double z, float scale)
    {
        Tessellator tess = Tessellator.instance;
        int growthLevel = this.GetGrowthLevel(meta);
        
        Icon icon = connectorIcon[3];
        
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
