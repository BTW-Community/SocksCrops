package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCBlockPlanterDirt extends SCBlockPlanterBase {

	protected SCBlockPlanterDirt(int iBlockID, String unlocalisedName) {
		super(iBlockID, unlocalisedName);
	}
	
	@Override
    public boolean GetCanGrassSpreadToBlock( World world, int i, int j, int k )
    {
        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
        
        if ( blockAbove == null || blockAbove.GetCanGrassGrowUnderBlock( world, i, j + 1, k, false ) ) 
        {            
        	return true;
        }
    	
    	return false;
    }
    
	@Override
    public boolean SpreadGrassToBlock(World world, int x, int y, int z) {
//        world.setBlockWithNotify(x, y, z, SCDefs.planterGrass.blockID);//TODO: uncomment when adding new planters
//        ((SCBlockPlanterGrass) SCDefs.planterGrass).setSparse(world, x, y, z);//TODO: uncomment when adding new planters
        
    	return true;
    }

	@Override
	protected Icon getContentsTexture(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Icon getGrassTexture(int meta) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int getNutritionLevel(int meta) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected boolean isHydrated(int meta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void renderOverlay(RenderBlocks renderer, int i, int j, int k) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void renderOverlayItem(RenderBlocks renderer, int iItemDamage, float brightness) {

	}

	@Override
	protected boolean isFertilized(int meta) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void setFertilized(World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		
	}
}
