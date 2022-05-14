package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockDirtLooseBase extends FCBlockDirtLoose {

	public SCBlockDirtLooseBase(int blockID) {
		super(blockID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
	protected int getNutritionLevel( World world, int i, int j, int k) {
    	int meta = world.getBlockMetadata(i, j, k);
    	
    	if (meta == 0)
    	{
    		return 3;
    	}
    	else if (meta == 1) {
    		return 2;
    	}
    	else if (meta == 2 ) {
    		return 1;
    	}
    	else return 0;

	}
		
	//Stump
    private static final String[] nutritionLevelTextures = new String[] {"SCBlockDirtLooseDry_0", "SCBlockDirtLooseDry_1", "SCBlockDirtLooseDry_2", "SCBlockDirtLooseDry_3"};
    private Icon[] sideIconArray;
    //private Icon trunkTopIcon;
    //private Icon m_iconEmbers;

    @Override
    public Icon getIcon( int side, int meta )
    {    	
    	return sideIconArray[meta & 3];
    	
		//return super.getIcon( iSide, iMetadata );
    }    
    
    @Override
    public int idDropped(int par1, Random rand, int par3) {
    	return this.blockID;
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
       	return meta;
    }
    @Override
    public void registerIcons( IconRegister register )
    {
    	
    	//m_iconEmbers = iconRegister.registerIcon( "SCBlockLogVineOverlay" );
    	
    	//trunkTopIcon = iconRegister.registerIcon( "fcBlockTrunkTop" );
    	
    	sideIconArray = new Icon[nutritionLevelTextures.length];

        for (int iTextureID = 0; iTextureID < sideIconArray.length; iTextureID++ )
        {
        	sideIconArray[iTextureID] = register.registerIcon(nutritionLevelTextures[iTextureID]);
        }
        
        //super.registerIcons( iconRegister );
    }
    
	
	
	
	

}
