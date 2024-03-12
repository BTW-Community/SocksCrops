package net.minecraft.src;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

public class SCBlockPlanterGrass extends SCBlockPlanterBase {

	public static final int SPREAD_LIGHT_LEVEL = 9; // 7 previously, 4 vanilla
	public static final int SURVIVE_LIGHT_LEVEL = 9; // 4 previously

	public static final float GROWTH_CHANCE = 0.8F;
	public static final int SELF_GROWTH_CHANCE = 12;
	
	protected SCBlockPlanterGrass(int iBlockID, String unlocalisedName) {
		super(iBlockID, unlocalisedName);
	}
	
	@Override
	public float GetPlantGrowthOnMultiplier(World world, int i, int j, int k, Block plantBlock) {
		//TODO: Add Nutrition Based Multiplier
		
		if ( GetIsFertilizedForPlantGrowth( world, i, j, k ) )
		{
			return 2F;
		}

		return 1F;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random) {
		int meta = world.getBlockMetadata(x, y, z);
		
		//Turn Grass to Dirt
		Block blockAbove = Block.blocksList[world.getBlockId(x, y + 1, z)];

		if (blockAbove != null)
		{
			boolean isFullBlock = blockAbove.IsNormalCube(world, x, y, z);
			
			if (isFullBlock)
			{
				world.setBlockAndMetadata(x, y, z, SCDefs.planterFarmland.blockID, getNutritionLevel(meta));
			}
		}
		
		//Grass Spread
		if (FCBlockGrass.canGrassSpreadFromLocation(world, x, y, z)) {
			if (random.nextFloat() <= GROWTH_CHANCE) {
				checkForGrassSpreadFromLocation(world, x, y, z);
			}
		}
		
		//Grass Growth
		if (isSparse(world, x, y, z) && random.nextInt(SELF_GROWTH_CHANCE) == 0) {
			world.setBlockAndMetadata(x, y, z, this.blockID, meta - 4);
		}
		
		//Nutrition loss
		if (!isSparse(world, x,y,z) && getNutritionLevel(meta) > 0 && random.nextInt(SELF_GROWTH_CHANCE) == 0)
		{
			world.setBlockAndMetadata(x, y, z, this.blockID, meta - 1);
		}
		
		//grow plants
		if(!isSparse(world, x,y,z) && getNutritionLevel(meta) == 0)
		{
			growPlants(world, x,y,z, random);
		}		
	}
	
	private void growPlants(World world, int i, int j, int k, Random random) {
		if ( world.isAirBlock( i, j + 1, k ) )
    	{
        	// grass planters with nothing in them will eventually sprout flowers or tall grass
    		  
    		if ( world.getBlockLightValue(i, j + 1, k) >= 8 )
    		{

	    		if ( random.nextInt( 12 ) == 0 )
	    		{		    		
	    			int iPlantType = random.nextInt( 3 );
	    			
	    			if ( iPlantType == 0 )
	    			{
	    				world.setBlockAndMetadataWithNotify( i, j + 1, k, Block.tallGrass.blockID, 1 );
	    			}
	    			else if ( iPlantType == 1 )
	    			{
	    				world.setBlockAndMetadataWithNotify( i, j + 1, k, Block.tallGrass.blockID, 2 );
	    			}
	    			else
	    			{
	    				if (world.isAirBlock( i, j + 2, k ) )
	    				{
	    					if (random.nextInt( 2 ) == 0)
	    					{
	    						world.setBlockAndMetadataWithNotify( i, j + 1, k, SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.FERN);
		    					world.setBlockAndMetadataWithNotify( i, j + 2, k, SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.setTopBlock(SCBlockDoubleTallGrass.FERN));
	    					}
	    					else {
	    						world.setBlockAndMetadataWithNotify( i, j + 1, k, SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.GRASS);
		    					world.setBlockAndMetadataWithNotify( i, j + 2, k, SCDefs.doubleTallGrass.blockID, SCBlockDoubleTallGrass.setTopBlock(SCBlockDoubleTallGrass.GRASS));
	    					}
	    				}
	    			}
	    		}
    		}
    	}		
	}

	public static void checkForGrassSpreadFromLocation(World world, int x, int y, int z) {
		if (world.provider.dimensionId != 1 && !FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z)) {
			// check for grass nearby
			int i = x + world.rand.nextInt(3) - 1;
			int j = y + world.rand.nextInt(4) - 2;
			int k = z + world.rand.nextInt(3) - 1;
			
			Block targetBlock = Block.blocksList[world.getBlockId(i, j, k)];

			if (targetBlock != null) {
				attempToSpreadGrassToLocation(world, i, j, k);
			}
		}
	}

	public static boolean attempToSpreadGrassToLocation(World world, int x, int y, int z) {
		int targetBlockID = world.getBlockId(x, y, z);
		Block targetBlock = Block.blocksList[targetBlockID];

		if (targetBlock.GetCanGrassSpreadToBlock(world, x, y, z) &&
				Block.lightOpacity[world.getBlockId(x, y + 1, z)] <= 2 &&
				!FCBlockGroundCover.IsGroundCoverRestingOnBlock(world, x, y, z))    		
		{
			return targetBlock.SpreadGrassToBlock(world, x, y, z);
		}

		return false;
	}
	
	private boolean isSparse(World world, int x, int y, int z) {
		return isSparse(world.getBlockMetadata(x, y, z));
	}
		
	private boolean isSparse(int meta) {
		return meta > 3;
	}
	
	public void setSparse(World world, int x, int y, int z) {
		//TODO:
	}


	@Override
    public boolean CanConvertBlock( ItemStack stack, World world, int i, int j, int k )
    {
    	return stack != null && stack.getItem() instanceof FCItemHoe;
    }
	
	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide)
	{
		world.setBlockWithNotify(x, y, z, FCBetterThanWolves.fcBlockPlanterSoil.blockID);		
		return true;
	}
	
	private int getNutritionLevel(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		
		return getNutritionLevel(meta);
	}
	
	protected int getNutritionLevel( int meta)
    {    	
    	return meta & 3;
	}
    
    private int getGrowthLevel( int meta)
    {
    	if (meta > 11) return 0;
    	else if (meta > 7) return 1;
    	else if (meta > 3) return 2;
    	else return 3;
    }
    
	@Override
    public boolean CanWildVegetationGrowOnBlock( World world, int i, int j, int k )
    {
		return true;
    }
	
	@Override
	public boolean CanSaplingsGrowOnBlock(World world, int i, int j, int k) {
		return true;
	}
	
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		int meta = blockAccess.getBlockMetadata(x, y, z);

		if (!grassPass)
		{
			return 16777215;
		}
		else {
			
			return getGrassColor(blockAccess, x,y,z);
			
//			switch (getNutritionLevel(meta)) {
//			case 0:
//				return color(blockAccess, x, y, z, 1.0f , 1.0f , 1.0f);
//			case 1:
//				return color(blockAccess, x, y, z, 0.9f , 0.75f , 1.0f);
//			case 2:
//				return color(blockAccess, x, y, z, 0.85f , 0.5f , 1.0f);
//			case 3:
//				return color(blockAccess, x, y, z, 0.8f , 0.25f , 1.0f);
//
//			default:
//				return 0x000;
//			}			
		}
		
		
	}
	
//	private int color( IBlockAccess blockAccess, int i, int j, int k, int r, int g, int b, int h) {
//		
//		
//		Color color = new Color(color(blockAccess, i, j, k, r, g, b));
//		int red = color.getRed();
//		int green = color.getGreen();
//		int blue = color.getBlue();
//		float[] hsb = color.RGBtoHSB(red, green, blue, null);
//		
//		float hue = hsb[0];
//		float sat = hsb[1];
//		float bri = hsb[2];		
//		
//		return color(blockAccess, i, j, k, r, g, b);
//	}
	

	
	
	
	//------------ Client Side Functionality ----------//
    
    private Icon[] topGrass = new Icon[2];
	private Icon dirt;
	
	@Override
    public void registerIcons( IconRegister register )
    {
		super.registerIcons( register );
			
		topGrass[0] = register.registerIcon("fcBlockGrassSparse");
		topGrass[1] = register.registerIcon( "grass_top" );
		
		dirt = register.registerIcon("fcBlockGrassSparseDirt");	
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

	protected boolean grassPass = false;
		
	@Override
	protected void renderOverlay(RenderBlocks renderer, int i, int j, int k) {
		if (getGrassTexture(renderer.blockAccess.getBlockMetadata(i, j, k)) != null)
		{
			grassPass = true;
			renderer.setOverrideBlockTexture(getGrassTexture(renderer.blockAccess.getBlockMetadata(i, j, k)));
			renderer.setRenderBounds(0,0.998,0,1,1,1);
	    	renderer.renderStandardBlock(this, i, j, k);
	    	renderer.clearOverrideBlockTexture();
	    	grassPass = false;
	    }
	}
	
	@Override
	protected void renderOverlayItem(RenderBlocks renderer, int iItemDamage, float brightness) {
		Icon overlay = getGrassTexture(iItemDamage);
		int var14 = getGrassColor(iItemDamage);
		
		float var8 = (float)(var14 >> 16 & 255) / 255.0F;
		float var9 = (float)(var14 >> 8 & 255) / 255.0F;
        float var10 = (float)(var14 & 255) / 255.0F;
        GL11.glColor4f(var8 * brightness, var9 * brightness, var10 * brightness, 1.0F);
		
    	renderer.setOverrideBlockTexture(overlay);
		renderer.setRenderBounds(0,0.998,0,1,1,1);
		FCClientUtilsRender.RenderInvBlockWithMetadata( renderer, this, -0.5F, -0.5F, -0.5F, iItemDamage );    
    	renderer.clearOverrideBlockTexture();
	}
	
//	@Override
//    public Icon getIcon( int iSide, int iMetadata )
//    {
//		if ( iSide == 1  )
//		{		
//			if (grassPass)
//			{
//				return topGrass[getGrowthLevel(iMetadata)];
//			}
//			else if (dirtPass)
//			{
//				return dirt;
//			}
//			else return planter;
//		}        
//    	
//        return blockIcon;
//    }


//	@Override
//    public boolean RenderBlock( RenderBlocks renderer, int x, int y, int z )
//    {    	
//    	dirtPass = true;    	
//    	renderer.setRenderBounds(0,0.99999,0,1,1,1);
//    	renderer.renderStandardBlock(this, x, y, z);    	
//    	dirtPass = false;
//    	
//    	grassPass = true;    	
//    	renderer.setRenderBounds(0,0.99999F,0,1,1,1);
//    	renderer.renderStandardBlock(this, x, y, z);    	
//    	grassPass = false;
//    	
//    	return true;
//    }
//    
//    @Override
//    public void RenderBlockSecondPass(RenderBlocks renderer, int x, int y, int z, boolean bFirstPassResult) {
//    	RenderFilledPlanterBlock( renderer, x, y, z );
//    }
    
//    @Override
//    public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float brightness) {
//    	RenderFilledPlanterInvBlock( renderer, this, iItemDamage );
//    	
//    	int nutritionLevel = getNutritionLevel(iItemDamage);
//    	
//    	SCUtilsRender.renderPlanterContentsAsItem(renderer, this, iItemDamage, brightness, nutritionLevel, topGrass[3], dirt, topGrass[getGrowthLevel(iItemDamage)]);    	
//    	
//    }

	@Override
	protected Icon getContentsTexture(int meta) {
		return dirt;
	}

	@Override
	protected Icon getGrassTexture(int meta) {
		return topGrass[getGrowthLevel(meta)&1];
	}

	@Override
	protected boolean isHydrated(int meta) {
		return false;
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
