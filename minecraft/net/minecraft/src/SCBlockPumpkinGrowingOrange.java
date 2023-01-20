package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingOrange extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingOrange(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID, int sleepingFruit )
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID, sleepingFruit);
		
		setUnlocalizedName("SCBlockPumpkinGrowingOrange");
	}

	protected int getMetaHarvested(int growthLevel)
	{
		if (growthLevel == 3 )
		{
			return 3;
		}
		else if (growthLevel == 2 )
		{
			return 2;
		}
		else if (growthLevel == 1 )
		{
			return 1;
		}
		else return 0;
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i;
			}
		}
		return 0;
	}
	
	
	@Override
	public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k)
	{
		int meta = blockAccess.getBlockMetadata(i, j, k);
		int growthLevel = GetGrowthLevel(meta);
		
		if (growthLevel == 3) return true;
		else return false;
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
		
		//init BB
		AxisAlignedBB pumpkinBounds;
		
		//Orange
		if (growthLevel == 0)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(14, 14, 14);
		}
		else return GetGourdBounds(16, 16, 16);
	}	
	
	private boolean vinePass;
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
		
		super.RenderBlock(renderer, i, j, k);
		
		vinePass = true;
		this.renderVineConnector(renderer, i, j, k, connectorIcon[growthLevel]);
		vinePass = false;
		return true;
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		if (vinePass) 
		{
			if ( getDirection(blockAccess.getBlockMetadata(x, y, z)) == 0 && blockAccess.getBlockId(x, y, z - 1) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 2 && blockAccess.getBlockId(x, y, z + 1) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 1 && blockAccess.getBlockId(x + 1, y, z) == SCDefs.gourdVineDead.blockID ||
					getDirection(blockAccess.getBlockMetadata(x, y, z)) == 3 && blockAccess.getBlockId(x - 1, y, z) == SCDefs.gourdVineDead.blockID)
			{
				return 0xfb9a35; //hue to dead color
			}
		}
		return super.colorMultiplier(blockAccess, x, y, z);
	}
	
	protected Icon[] orangeIcon;
	protected Icon[] orangeIconTop;
	protected Icon[] connectorIcon;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		super.registerIcons(register);
				
		//Orange
  		orangeIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
		{
  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinOrangeSide_" + iTempIndex );
		}
	
		orangeIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
		{
		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinOrangeTop_" + iTempIndex );
		}
		
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinOrangeConnector_" + iTempIndex );
        }
		
		blockIcon = orangeIcon[3];
	}
	
	
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	
    	if ( iSide == 1 || iSide == 0 )
    	{
    		return orangeIconTop[growthLevel];
    	}
    	
    	return orangeIcon[growthLevel];
    }
}
