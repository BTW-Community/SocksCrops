package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonWaterGrowing extends SCBlockMelonGrowing {

	protected SCBlockMelonWaterGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock,	int convertedBlockID)
	{
		super(iBlockID, stemBlock, vineBlock, flowerBlock, convertedBlockID);
		
		setUnlocalizedName("SCBlockMelonWaterGrowing");
	}
	
	protected int getMetaHarvested(int growthLevel)
	{		
		if (growthLevel == 3 )
		{
			return 3; 
		}
		else if (growthLevel == 2)
		{
			return 2;
		}
		else if (growthLevel == 1)
		{
			return 1;
		}
		else return 0;
	}
	
	@Override
	protected int getPossessedMetaForGrowthLevel(int growthLevel)
	{
		return 0; //Can't be possessed. see canBePossessed() in super
	}

	
    @Override
	protected int AuxFXIDOnExplode(World world, int i, int j, int k, int meta)
    {
    	return SCCustomAuxFX.melonExplodeAuxFXID;
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
		
		//Water
		if (growthLevel == 0)
		{
			return GetGourdBounds(6, 6, 6);
		}
		else if (growthLevel == 1)
		{
			return GetGourdBounds(8, 8, 8);
		}
		else if (growthLevel == 2)
		{
			return GetGourdBounds(12, 12, 12);
		}
		else return GetGourdBounds(16, 16, 16);
	}	
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k)
	{
		IBlockAccess blockAccess = renderer.blockAccess;
		int growthLevel = this.GetGrowthLevel(blockAccess, i, j, k);
		
		super.RenderBlock(renderer, i, j, k);
		
		this.renderVineConnector(renderer, i, j, k, connectorIcon[growthLevel]);
		
		return true;
	}
	
	
	protected Icon[] waterMelonIcon;
	protected Icon[] waterMelonIconTop;
	protected Icon[] connectorIcon;
	private Icon overlayIcon;
	
	@Override
  	public void registerIcons( IconRegister register )
  	{
		overlayIcon = register.registerIcon("SCBlockMelonWaterSideOverlay");
		//Orange
		waterMelonIcon = new Icon[4];
		
  		for ( int iTempIndex = 0; iTempIndex < waterMelonIcon.length; iTempIndex++ )
		{
  			waterMelonIcon[iTempIndex] = register.registerIcon( "SCBlockMelonWaterSide_" + iTempIndex );
		}
	
  		waterMelonIconTop = new Icon[4];
	
		for ( int iTempIndex = 0; iTempIndex < waterMelonIconTop.length; iTempIndex++ )
		{
			waterMelonIconTop[iTempIndex] = register.registerIcon( "SCBlockMelonWaterTop_" + iTempIndex );
		}
		
        connectorIcon = new Icon[4];
        for ( int iTempIndex = 0; iTempIndex < connectorIcon.length; iTempIndex++ )
        {
        	connectorIcon[iTempIndex] = register.registerIcon( "SCBlockMelonWaterConnector_" + iTempIndex );
        }
		
		blockIcon = waterMelonIcon[3];
	}
	
	@Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	int growthLevel = GetGrowthLevel(iMetadata);
    	
    	if ( iSide == 1 || iSide == 0 )
    	{
    		return waterMelonIconTop[growthLevel];
    	}
    	
    	return waterMelonIcon[growthLevel];
    }

	@Override
	Icon getOverlayIcon() {
		return overlayIcon;
	}
	

}
