package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockGourdVineFloweringBase extends SCBlockGourdVine {
	
	protected Block fruitBlock;
	protected Block greenFruit;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockGourdVineFloweringBase(int iBlockID, int vineBlock,int stemBlock, Block fruitBlock, Block greenFruit) {
		super( iBlockID, vineBlock, stemBlock);
		setUnlocalizedName("SCBlockPumpkinVineFlowering");
		this.fruitBlock = fruitBlock;
		this.greenFruit = greenFruit;
		this.vineBlock = vineBlock;
	}
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		
		if (!this.canBlockStay(world, i, j, k))
		{
			world.setBlock(i, j, k, Block.tallGrass.blockID);
		}
		else
		{
			int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
			if ( iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 ) //daytime
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
		        	world.setBlockAndMetadataWithNotify(i, j, k, this.vineBlock, world.getBlockMetadata(i, j, k)); //TODO CHANGE BACK TO SLEEPING
		        }
			}
			
			
		}
    }
	
	protected void AttemptToGrow(World world, int i, int j, int k, Random random) {
		int GrowthLevel = GetGrowthLevel(world, i, j, k);
		int meta = world.getBlockMetadata(i, j, k);
        
        if (GrowthLevel < 3 && random.nextInt() <= 0.5F) {
        	//TODO CHANGE BACK TO SLEEPING
        	world.setBlockAndMetadataWithNotify(i, j, k, this.blockID ,meta + 4); //increase a growth stage and set sleeping
        	System.out.println("VINE: YES");
        }
		
	}
	
	protected abstract void AttemptToGrowPumpkin(World world, int i, int j, int k, Random random);
	
	
	protected boolean CanGrowFruitAt( World world, int i, int j, int k )
    {
		int iBlockID = world.getBlockId( i, j, k );		
		int iMeta = world.getBlockMetadata(i, j, k);
		
		Block block = Block.blocksList[iBlockID];
		
        if ( FCUtilsWorld.IsReplaceableBlock( world, i, j, k ) ||
        	( block != null && block.blockMaterial == Material.plants && 
    		iBlockID != Block.cocoaPlant.blockID && 
    		iBlockID != stemBlock && 
    		iBlockID != this.blockID &&
    		iBlockID == vineBlock && iMeta <= 3 ) )
        {
			// CanGrowOnBlock() to allow fruit to grow on tilled earth and such
			if (this.CanGrowOnBlock( world, i, j -1, k ) )
            {				
				return true;
            }
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
