package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockPumpkinPossessed extends SCBlockPumpkinHarvested {

	protected SCBlockPumpkinPossessed(int iBlockID) {
		super(iBlockID);

		setUnlocalizedName("fcBlockPumpkinPossessed");
		setCreativeTab(CreativeTabs.tabDecorations);
        
	}
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{		
		//Don't grow
	}
	
	private AxisAlignedBB GetPumpkinBounds(double size, double height)
	{
    	AxisAlignedBB pumpkinBox = null;
    	
    	pumpkinBox = AxisAlignedBB.getAABBPool().getAABB( 
    			8/16D - size, 0.0D, 8/16D - size, 
    			8/16D + size, height, 8/16D + size);
    	
    		return pumpkinBox;
		
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		IBlockAccess blockAccess = renderer.blockAccess;
		int meta = blockAccess.getBlockMetadata(i, j, k);
		
		//Orange
		if (meta == 0) {
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 0 = 6x6x6
		}else if (meta == 1){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 1 = 8x8x8
		}else if (meta == 2){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 12x12x12
		}else if (meta == 3){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 16/16D)); // stage 2 = 14x14x14
		}
		//Green
		else if (meta == 4){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 5){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 6){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 6/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 7){
			renderer.setRenderBounds(GetPumpkinBounds(8/16D, 8/16D)); // stage 2 = 14x14x14
			
		}
		//Yellow
		else if (meta == 8){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 4/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 9){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 6/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 10){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 8/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 11){
			renderer.setRenderBounds(GetPumpkinBounds(6/16D, 12/16D)); // stage 2 = 14x14x14
			
		}
		//White
		else if (meta == 12){
			renderer.setRenderBounds(GetPumpkinBounds(2/16D, 3/16D)); // stage 1 = 8x8x8
			
		}else if (meta == 13){
			renderer.setRenderBounds(GetPumpkinBounds(3/16D, 4/16D)); // stage 2 = 12x12x12
			
		}else if (meta == 14){
			renderer.setRenderBounds(GetPumpkinBounds(4/16D, 5/16D)); // stage 2 = 14x14x14
			
		}else if (meta == 15){
			renderer.setRenderBounds(GetPumpkinBounds(5/16D, 6/16D)); // stage 2 = 14x14x14
		}
		
		renderer.renderStandardBlock( this, i, j, k );
		return true;
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
