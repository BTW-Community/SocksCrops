package net.minecraft.src;

public class SCBlockMossyLog extends SCBlockLogBase {

	protected SCBlockMossyLog(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockMossyLog");
	}

	
    
    @Override
    public void RenderBlockSecondPass( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
    {
    	IBlockAccess blockAccess = renderBlocks.blockAccess;
    	
    	int meta = blockAccess.getBlockMetadata(i, j, k);
    	
    	if ( bFirstPassResult)
    	{
	        FCClientUtilsRender.RenderStandardBlockWithTexture(renderBlocks, this, i, j, k, damagedIconArray[meta & 3]);
    	}
    }
    
    public static final String[] damagedTextureTypes = new String[] {"SCBlockMossyLogOak_side", "SCBlockMossyLogSpruce_side", "SCBlockMossyLogBirch_side", "SCBlockMossyLogJungle_side"};
    public static final String[] damagedTopTextures = new String[] {"SCBlockLogOak_top", "SCBlockLogSpruce_top", "SCBlockLogBirch_top", "SCBlockLogJungle_top"};
    public static final String[] treeTextures = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};
    
    private Icon[] damagedIconArray;
    private Icon[] damagedTopIcon;
    private Icon[] treeIcon;
    
    
    
    @Override
    public void registerIcons( IconRegister iconRegister )
    {    
    	super.registerIcons(iconRegister);
    	
    	damagedIconArray = new Icon[damagedTextureTypes.length];

        for (int iTextureID = 0; iTextureID < damagedIconArray.length; iTextureID++ )
        {
        	damagedIconArray[iTextureID] = iconRegister.registerIcon(damagedTextureTypes[iTextureID]);
        }
        
        damagedTopIcon = new Icon[damagedTopTextures.length];

        for (int iTextureID = 0; iTextureID < damagedIconArray.length; iTextureID++ )
        {
        	damagedTopIcon[iTextureID] = iconRegister.registerIcon(damagedTopTextures[iTextureID]);
        }
        
        treeIcon = new Icon[treeTextures.length];

        for (int iTextureID = 0; iTextureID < treeIcon.length; iTextureID++ )
        {
        	treeIcon[iTextureID] = iconRegister.registerIcon(treeTextures[iTextureID]);
        }
        
    } 
    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {    	
    	if ( iMetadata <= 3 )
    	{    		
    		if ( iSide <= 1 )
    		{
    			return damagedTopIcon[iMetadata];
    		}
    	} 
    	else if ( iMetadata > 3 && iMetadata <= 7 )
    	{    		
    		if ( iSide == 4 || iSide == 5 )
    		{
    			return damagedTopIcon[iMetadata - 4];
    		}
    	}
    	else if ( iMetadata > 7 && iMetadata <= 11 )
    	{    		
    		if ( iSide == 2 || iSide == 3 )
    		{
    			return damagedTopIcon[iMetadata - 8];
    		}
    	}
    	return treeIcon[iMetadata & 3];
    } 
	
}
