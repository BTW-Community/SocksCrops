package net.minecraft.src;

public class SCBlockPlanterFarmland extends FCBlockPlanterSoil {

	protected SCBlockPlanterFarmland(int iBlockID) {
		super(iBlockID);
		
		SetBlockMaterial(Material.ground);
		
		setHardness( 0.5F );
		
		SetPicksEffectiveOn( true );
		SetHoesEffectiveOn( true );
		setTickRandomly(true);
		
		setUnlocalizedName( "SCBlockPlanterFarmland" );
		
		setCreativeTab(CreativeTabs.tabDecorations);
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

}
