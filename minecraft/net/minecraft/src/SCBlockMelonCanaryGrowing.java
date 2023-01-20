package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonCanaryGrowing extends SCBlockMelonGrowing {

	protected SCBlockMelonCanaryGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID, int sleepingFruit)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, sleepingFruit);
		
		setUnlocalizedName("SCBlockMelonCanaryGrowing");
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		return 0; //Can't be possessed. see canBePossessed() in super
	}
	
	@Override
	protected int getMetaHarvested(int growthLevel)
	{
		return 0; //unused as we are overriding convertBlock() below
	}
	
	@Override
	protected void convertBlock(World world, int i, int j, int k)
	{	
		int meta = world.getBlockMetadata(i, j, k);
		
		world.setBlockAndMetadata(i, j, k, convertedBlockID , meta);
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		
		return SCDefs.melonCanaryHarvested.blockID;
	}

	@Override
	public int damageDropped(int meta)
	{
		if (this.GetGrowthLevel(meta) == 3)
		{
			return 12;
		}
		else if (this.GetGrowthLevel(meta) == 2)
		{
			return 8;
		}
		else if (this.GetGrowthLevel(meta) == 1)
		{
			return 4;
		}
		else return 0;
	}
	
	//--- Render ---//
	
	@Override
	public AxisAlignedBB GetBlockBoundsFromPoolBasedOnState( IBlockAccess blockAccess, int i, int j, int k )
	{
		return this.GetBlockBoundsFromPoolBasedOnState(blockAccess.getBlockMetadata(i, j, k));
	}
	
	private AxisAlignedBB GetBlockBoundsFromPoolBasedOnState(int meta)
	{
		int growthLevel = this.GetGrowthLevel(meta);
		int dir = this.getDirection(meta);
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//young
		if (growthLevel == 0)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(6, 4, 4);
			}
			else return GetGourdBounds(4, 4, 6);
		}
		//teen
		else if (growthLevel == 1)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(10, 6, 6);
			}
			else return GetGourdBounds(6, 6, 10);
		}
		//adult
		else if (growthLevel == 2)
		{
			if (dir == 1 || dir == 3)
			{
				return GetGourdBounds(12, 8, 8);
			}
			else return GetGourdBounds(8, 8, 12);
		}
		//mature
		else if (dir == 1 || dir == 3)
		{
			return GetGourdBounds(16, 10, 10);
		}
		else return GetGourdBounds(10, 10, 16);
	}	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		this.setTextureRotations(renderer, i, j, k, meta);
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(blockAccess, i, j, k) );
		renderer.renderStandardBlock(this, i, j, k);
		
		renderer.ClearUvRotation();
		
		this.renderVineConnector(renderer, i, j, k, connectorIcon[growthLevel]);
		
		return true;
	}
	
	private void setTextureRotations(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		int dir = this.getDirection(meta);
		
		if (dir == 0)
		{
			renderer.SetUvRotateTop(2);
			renderer.SetUvRotateBottom(2);
		}
		else if (dir == 2)
		{
			renderer.SetUvRotateTop(1);
			renderer.SetUvRotateBottom(1);
		}
		else if (dir == 3)
		{
			renderer.SetUvRotateTop(3);
			renderer.SetUvRotateBottom(3);
		}
		
	}

	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int dir = this.getDirection(blockAccess.getBlockMetadata(i, j, k));
		
		this.setTextureRotations(renderer, i, j, k, meta);
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
		
		renderer.ClearUvRotation();
	}
	
	protected Icon[] waterCanaryIconSide;
	protected Icon[] waterCanaryIconSideMirrored;
	protected Icon[] waterCanaryIconTop;
	protected Icon[] waterCanaryIconFront;
	protected Icon[] waterCanaryIconEnd;
	protected Icon[] connectorIcon;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		super.registerIcons(register);
		
		//Orange
		waterCanaryIconSide = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconSide.length; iTempIndex++ )
		{
  			waterCanaryIconSide[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_" + iTempIndex );
		}
  		
  		waterCanaryIconSideMirrored = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconSideMirrored.length; iTempIndex++ )
		{
  			waterCanaryIconSideMirrored[iTempIndex] = register.registerIcon( "SCBlockMelonYellowSide_mirrored_" + iTempIndex );
		}
  		
  		waterCanaryIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconTop.length; iTempIndex++ )
		{
			waterCanaryIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonYellowTop_" + iTempIndex );
		}
		
 		waterCanaryIconFront = new Icon[4];
 		
		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconTop.length; iTempIndex++ )
		{
			waterCanaryIconFront[iTempIndex] = register.registerIcon( "SCBlockMelonYellowFront_" + iTempIndex );
		}
		
 		waterCanaryIconEnd = new Icon[4];
 		
		for ( int iTempIndex = 0; iTempIndex < waterCanaryIconTop.length; iTempIndex++ )
		{
			waterCanaryIconEnd[iTempIndex] = register.registerIcon( "SCBlockMelonYellowBack_" + iTempIndex );
		}
		
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockMelonYellowConnector_" + iTempIndex );
        }
		
		blockIcon = waterCanaryIconSide[3];
	}
	
	@Override
    public Icon getIcon( int side, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	int dir = getDirection(iMetadata);

    	
    	if (dir == 0)
    	{
        	if (side == 0 || side == 1)
        	{
        		return waterCanaryIconTop[growthLevel];
        	}
        	else if (side == 3)
        	{
        		return waterCanaryIconEnd[growthLevel];
        	}
        	else if (side == 2)
        	{
        		return waterCanaryIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 4)
        		{
        			return waterCanaryIconSideMirrored[growthLevel];
        		}
        		return waterCanaryIconSide[growthLevel];
        	}
    	}
    	else if (dir == 2)
    	{
        	if (side == 0 || side == 1)
        	{
        		return waterCanaryIconTop[growthLevel];
        	}
        	else if (side == 2)
        	{
        		return waterCanaryIconEnd[growthLevel];
        	}
        	else if (side == 3)
        	{
        		return waterCanaryIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 5)
        		{
        			return waterCanaryIconSideMirrored[growthLevel];
        		}
        		return waterCanaryIconSide[growthLevel];
        	}
    	}
    	else if (dir == 3)
    	{
        	if (side == 0 || side == 1)
        	{
        		return waterCanaryIconTop[growthLevel];
        	}
        	else if (side == 5)
        	{
        		return waterCanaryIconEnd[growthLevel];
        	}
        	else if (side == 4)
        	{
        		return waterCanaryIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 3)
        		{
        			return waterCanaryIconSideMirrored[growthLevel];
        		}
        		return waterCanaryIconSide[growthLevel];
        	}
    	}
    	else
    	{

        	if (side == 0 || side == 1)
        	{
        		return waterCanaryIconTop[growthLevel];
        	}
        	else if (side == 4)
        	{
        		return waterCanaryIconEnd[growthLevel];
        	}
        	else if (side == 5)
        	{
        		return waterCanaryIconFront[growthLevel];
        	}
        	else
        	{
        		if (side == 2)
        		{
        			return waterCanaryIconSideMirrored[growthLevel];
        		}
        		return waterCanaryIconSide[growthLevel];
        	}
    	}

//    	if (iSide == 2 || iSide == 3 )
//    	{
//    		if (dir == 0 || dir == 2)
//    		{
//    			return waterCanaryIconTop[growthLevel];
//    		}
//    		else return waterCanaryIconSide[growthLevel];
//    	}
//    	else if (iSide == 4 || iSide == 5 )
//    	{
//    		if (dir == 1 || dir == 3)
//    		{
//    			return waterCanaryIconTop[growthLevel];
//    		}
//    		else return waterCanaryIconSide[growthLevel];
//    	}
//    	else return waterCanaryIconSide[growthLevel];
    }	

}
