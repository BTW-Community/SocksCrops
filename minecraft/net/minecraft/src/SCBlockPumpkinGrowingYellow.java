package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinGrowingYellow extends SCBlockPumpkinGrowing {

	protected SCBlockPumpkinGrowingYellow(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		
		setUnlocalizedName("SCBlockPumpkinGrowingYellow");
	}

	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel) {
		
		for (int i = 0; i < 4; i++) {
			if (growthLevel == i) 
			{
				//shift for if different pumpkin type: eg. i + 4 for green Pumpkins
				return i + 8; 
			}
		}
		return 0;
	}
	
	protected int getMetaHarvested(int growthLevel)
	{		
		if (growthLevel == 3 )
		{
			return 11; 
		}
		else if (growthLevel == 2)
		{
			return 10;
		}
		else if (growthLevel == 1)
		{
			return 9;
		}
		else return 8;
	}

	
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
			return GetGourdBounds(4, 4, 4);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(10, 10, 10);
		}
		else return GetGourdBounds(12, 12, 12);
	}	
	
	//--- Render ---//
	
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
	
	@Override
	public void RenderFallingBlock(RenderBlocks renderer, int i, int j, int k, int meta)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		
		renderer.setRenderBounds( this.GetBlockBoundsFromPoolBasedOnState(meta) );		
		renderer.RenderStandardFallingBlock( this, i, j, k, meta);
	}
	

	protected Icon[] orangeIcon;
	protected Icon[] orangeIconTop;
	protected Icon[] connectorIcon;
	private Icon overlayIcon;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		overlayIcon = register.registerIcon("SCBlockPumpkinYellowSideOverlay");
		
		//Orange
  		orangeIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < orangeIcon.length; iTempIndex++ )
		{
  			orangeIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowSide_" + iTempIndex );
		}
	
		orangeIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < orangeIconTop.length; iTempIndex++ )
		{
		orangeIconTop[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowTop_" + iTempIndex );
		}
		
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockPumpkinYellowConnector_" + iTempIndex );
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

	@Override
	Icon getOverlayIcon() {
		return overlayIcon;
	}
}
