package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinPossessed extends SCBlockPumpkinHarvested {

	protected SCBlockPumpkinPossessed(int iBlockID) {
		super(iBlockID);

		setUnlocalizedName("SCBlockPumpkinPossessed");
		setCreativeTab(CreativeTabs.tabDecorations);
        
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random rand) {
		super.updateTick(world, i, j, k, rand); //Falling
		
		
	}

//----------- Client Side Functionality -----------//
    
    private Icon m_IconTop;

    @Override
    public void registerIcons( IconRegister register )
    {
		blockIcon = register.registerIcon( "SCBlockPumpkinBlackSide");
		
		m_IconTop = register.registerIcon("SCBlockPumpkinBlackTop");
		
    }
    
    @Override
    public Icon getIcon( int iSide, int iMetadata )
    {
    	
    	if ( iSide == 1 || iSide == 0 )
    	{
    		return m_IconTop;
    	}
    	
    	return blockIcon;
    }
    
}
