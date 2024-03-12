package net.minecraft.src;

import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class SCBlockPlanterFarmland extends SCBlockPlanterBase {

	protected SCBlockPlanterFarmland(int iBlockID, String unlocalisedName) {
		super(iBlockID, unlocalisedName);
	}
	
	//------------- FCBlockPlanterSoil ------------//
	
	@Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
		boolean bHasIrrigation = HasIrrigatingBlocks( world, i, j, k ) || 
			world.IsRainingAtPos( i, j + 1, k );
		
		if ( bHasIrrigation != GetIsHydrated( world, i, j, k ) )
		{
			SetIsHydrated( world, i, j, k, bHasIrrigation );
		}
    }
	
	@Override
	public boolean AttemptToApplyFertilizerTo( World world, int i, int j, int k )
	{
		if ( !GetIsFertilized( world, i, j, k ) )
		{
			SetIsFertilized( world, i, j, k, true );
			
			return true;
		}
		
		return false;
	}
	
	@Override
    public boolean CanDomesticatedCropsGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
    
	@Override
    public boolean CanReedsGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
    
	@Override
    public boolean CanSaplingsGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
    
	@Override
    public boolean CanWildVegetationGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
	
	@Override
    public boolean CanCactusGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
	
	@Override
	public boolean IsBlockHydratedForPlantGrowthOn( World world, int i, int j, int k )
	{
		return GetIsHydrated( world, i, j, k );
	}
	
	@Override
	public boolean IsConsideredNeighbouringWaterForReedGrowthOn( World world, int i, int j, int k )
	{
		return GetIsHydrated( world, i, j, k );
	}
	
	@Override
	public boolean GetIsFertilizedForPlantGrowth( World world, int i, int j, int k )
	{
		return GetIsFertilized( world, i, j, k );
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		if ( GetIsFertilized( world, i, j, k ) )
		{
			SetIsFertilized( world, i, j, k, false );
		}
	}
	
    protected boolean HasIrrigatingBlocks( World world, int i, int j, int k )
    {
    	// planters can only be irrigated by direct neighbors

    	if ( world.getBlockMaterial( i, j - 1, k ) == Material.water || 
    		world.getBlockMaterial( i, j + 1, k ) == Material.water ||
    		world.getBlockMaterial( i, j, k - 1 ) == Material.water ||
    		world.getBlockMaterial( i, j, k + 1 ) == Material.water ||
    		world.getBlockMaterial( i - 1, j, k ) == Material.water ||
    		world.getBlockMaterial( i + 1, j, k ) == Material.water )
    	{
    		return true;
    	}
    	
    	return false;
    }
    
    //------------- Class Specific Methods ------------//
	
	protected boolean GetIsHydrated( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetIsHydrated( blockAccess.getBlockMetadata( i, j, k ) );
	}
    
	protected boolean GetIsHydrated( int iMetadata )
	{
//		return ( iMetadata & 1 ) != 0;
		return iMetadata > 3;
	}
	
	protected void SetIsHydrated( World world, int i, int j, int k, boolean bHydrated )
	{
		int iMetadata = SetIsHydrated( world.getBlockMetadata( i, j, k ), bHydrated );
		
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
	}
	
	protected int SetIsHydrated( int iMetadata, boolean bHydrated )
	{
//		if ( bHydrated)
//		{
//			iMetadata |= 1;
//		}
//		else
//		{
//			iMetadata &= (~1);
//		}
		
		return iMetadata + 4;
	}
	
	protected boolean GetIsFertilized( IBlockAccess blockAccess, int i, int j, int k )
	{
		return GetIsFertilized( blockAccess.getBlockMetadata( i, j, k ) );
	}
    
	protected boolean GetIsFertilized( int iMetadata )
	{
		return ( iMetadata & 2 ) != 0;
	}
	
	protected void SetIsFertilized( World world, int i, int j, int k, boolean bFertilized )
	{
		int iMetadata = SetIsFertilized( world.getBlockMetadata( i, j, k ), bFertilized );
		
		world.setBlockMetadataWithNotify( i, j, k, iMetadata );
	}
	
	protected int SetIsFertilized( int iMetadata, boolean bFertilized )
	{
		if ( bFertilized)
		{
			iMetadata |= 2;
		}
		else
		{
			iMetadata &= (~2);
		}
		
		return iMetadata;
	}
	
	@Override
    public boolean GetCanGrassSpreadToBlock( World world, int i, int j, int k )
    {
        Block blockAbove = Block.blocksList[world.getBlockId( i, j + 1, k )];
        
        if ( blockAbove == null || blockAbove.GetCanGrassGrowUnderBlock( world, i, j + 1, k, false ) ) 
        {            
        	return hasGrassAdjacent(world, i,j,k);
        }
    	
    	return false;
    }
    
	private boolean hasGrassAdjacent(World world, int i, int j, int k) {
		Block[] validGrassPos = new Block[]{
				Block.blocksList[world.getBlockId( i-1, j, k )],
				Block.blocksList[world.getBlockId( i-1, j+1, k )],
				Block.blocksList[world.getBlockId( i+1, j, k )],
				Block.blocksList[world.getBlockId( i+1, j+1, k )],
				Block.blocksList[world.getBlockId( i, j, k -1 )],
				Block.blocksList[world.getBlockId( i, j+1, k -1 )],
				Block.blocksList[world.getBlockId( i, j, k +1)],	
				Block.blocksList[world.getBlockId( i, j+1, k +1)]	
		};
		
		for (Block block : validGrassPos)
		{
			if (block instanceof FCBlockGrass || block instanceof SCBlockPlanterGrass)
			{
				return true;
			}
		}
		
		return false;
	}

	@Override
    public boolean SpreadGrassToBlock(World world, int x, int y, int z) {
		int oldMeta = world.getBlockMetadata(x, y, z);
		int nutrition = getNutritionLevel(oldMeta);
		int newMeta = nutrition + 4;
		
        world.setBlockAndMetadataWithNotify(x, y, z, SCDefs.planterGrass.blockID, newMeta);
        
    	return true;
    }
	
	protected int getNutritionLevel( int meta)
    {    	
    	return meta & 3;
	}
	
	protected boolean isHydrated(int meta) {
		return meta > 3;
	}
	
	@Override
	public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
        list.add(new ItemStack(blockID, 1, 1));
        list.add(new ItemStack(blockID, 1, 2));
        list.add(new ItemStack(blockID, 1, 3));
        list.add(new ItemStack(blockID, 1, 4));
        list.add(new ItemStack(blockID, 1, 5));
        list.add(new ItemStack(blockID, 1, 6));
        list.add(new ItemStack(blockID, 1, 7));
    }
	
	//------------- Client ------------//
	
	protected Icon[] farmland = new Icon[4];
	protected Icon[] farmlandHydrated = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {
		super.registerIcons(register);
		
		for (int i = 0; i < farmland.length; i++) {
			farmland[i] = register.registerIcon( "SCBlockFarmlandDry_" + i );
			farmlandHydrated[i] = register.registerIcon( "SCBlockFarmlandWet_" + i );			
		}		
	}

	@Override
	protected Icon getContentsTexture(int meta) {
		if (isHydrated(meta))
		{
			return farmlandHydrated[getNutritionLevel(meta)];
		}
		return farmland[getNutritionLevel(meta)];
	}

	@Override
	protected Icon getGrassTexture(int meta) {
		return null;
	}

	@Override
	protected void renderOverlay(RenderBlocks renderer, int i, int j, int k) {
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
