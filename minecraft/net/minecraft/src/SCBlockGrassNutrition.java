package net.minecraft.src;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;

import net.minecraft.client.Minecraft; // client only

public class SCBlockGrassNutrition extends FCBlockGrass
{
	public static String[] nutritionLevelNames = new String[] {
			"grass0", "grass0_sparse",
			"grass1", "grass1_sparse",
			"grass2", "grass2_sparse",
			"grass3", "grass3_sparse"
	};
    
	public static final float NUTRITION_GROWTH_CHANCE = 0.4F;
	
    public SCBlockGrassNutrition( int iBlockID )
    {
    	super( iBlockID );
    	
    	setHardness( 0.6F );
    	SetShovelsEffectiveOn();
    	SetHoesEffectiveOn();
    	
    	setStepSound(soundGrassFootstep);
    	
    	setUnlocalizedName("SCBlockGrassNutrition");
    }
    
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (!canGrassSurviveAtLocation(world, x, y, z)) {
			// convert back to dirt in low light
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 3);
			
		}
		else if (canGrassSpreadFromLocation(world, x, y, z)) {
			if (rand.nextFloat() <= GROWTH_CHANCE) {
				checkForGrassSpreadFromLocation(world, x, y, z);
			}

			if (isSparse(world, x, y, z) && rand.nextInt(SELF_GROWTH_CHANCE) == 0) {
				this.setFullyGrown(world, x, y, z);
			}
		}
		
		if (nutritionLevel < 3) //ie not full
		{
			attemptToIncreaseNutrition(world, x, y, z, rand);
		}
		
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta > 0 && meta < 6)
		{
			// revert back to soil
			world.setBlockAndMetadataWithNotify( i, j, k, this.blockID, meta + 2 );
		}

	}
	
	@Override
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return getNutritionMultiplier(world.getBlockMetadata(i, j, k));
	}

	private float getNutritionMultiplier(int meta) {
		if (meta <= 1) return 1F;
		else if (meta <= 3) return 0.75F;
		else if (meta <= 5) return 0.5F;
		else return 0.25F;

	}
    
    private void attemptToIncreaseNutrition(World world, int x, int y, int z, Random rand)
    {
        int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
        int meta = world.getBlockMetadata(x, y, z);
		
		if ( rand.nextFloat() <= 0.1F)
		{
	        if ( iTimeOfDay > 14000 && iTimeOfDay < 22000 ) // at night
	        {
	        	world.setBlockAndMetadataWithNotify(x, y, z, this.blockID, meta - 2);
	        }
		}
	}
    
	@Override
	public int idDropped(int metadata, Random rand, int fortuneModifier)
	{
		return FCBetterThanWolves.fcBlockDirtLoose.blockID;
	}
	
	@Override
	public int damageDropped(int meta)
	{
		int nutritionLevel = getNutritionLevel(meta);
		
		if (nutritionLevel == 3) return 0;
		if (nutritionLevel == 2) return 1;
		if (nutritionLevel == 1) return 2;
		else return 3;
	}
	
	@Override
	public boolean DropComponentItemsOnBadBreak(World world, int x, int y, int z, int metadata, float chanceOfDrop)
	{
		//TODO: change the dropped item. Probably to coarse dirt piles
		DropItemsIndividualy(world, x, y, z, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, chanceOfDrop);

		return true;
	}
	
	@Override
	protected void OnNeighborDirtDugWithImproperTool(World world, int x, int y, int z, int toFacing)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		// only disrupt grass/mycelium when block below is dug out
		if (toFacing == 0)
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
		}    		
	}
    
	@Override
	public void OnGrazed(World world, int x, int y, int z, EntityAnimal animal)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (!animal.GetDisruptsEarthOnGraze())
		{
			if (isSparse(world, x, y, z))
			{
				if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 0);
				if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 1);
				if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 2);
				if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, Block.dirt.blockID, 3);
			}
			else
			{
				setSparse(world, x, y, z);
			}
		}
		else
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
			
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}

	@Override
	public void OnVegetationAboveGrazed(World world, int x, int y, int z, EntityAnimal animal)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (animal.GetDisruptsEarthOnGraze())
		{
			if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
			if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
			if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
			if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);
			
			NotifyNeighborsBlockDisrupted(world, x, y, z);
		}
	}
	
	@Override
	public boolean ConvertBlock(ItemStack stack, World world, int x, int y, int z, int fromSide)
	{
		int nutritionLevel = getNutritionLevel(world, x, y, z);
		
		if (nutritionLevel == 3) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 0);
		if (nutritionLevel == 2) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 1);
		if (nutritionLevel == 1) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 2);
		if (nutritionLevel == 0) world.setBlockAndMetadataWithNotify(x, y, z, FCBetterThanWolves.fcBlockDirtLoose.blockID, 3);

		//only drop hemp seeds at full Nutrition
		if (!world.isRemote && nutritionLevel == 3)
		{
			if (world.rand.nextInt(25) == 0) {
				FCUtilsItem.EjectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(FCBetterThanWolves.fcItemHempSeeds), fromSide);
			}
		}

		return true;
	}
	
    /**
     * 3 = 100% Nutrition (meta 0 & 1)
     * 2 = 66% Nutrition (meta 2 & 3)
     * 1 = 33% Nutrition (meta 4 & 5)
     * 0 = 0% Nutrition (meta 6 & 7)
     */
    private int getNutritionLevel( World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
    	return getNutritionLevel(meta);
	}
    
    private int getNutritionLevel( int meta)
    {
    	int nutritionLevel;    	
    	if (meta == 0 || meta == 1) return nutritionLevel = 3;
    	else if (meta == 2 || meta == 3) return nutritionLevel = 2;
    	else if (meta == 4 || meta == 5) return nutritionLevel = 1;
    	else return nutritionLevel = 0;
	}
    

    @Override
	public boolean isSparse(int meta) {
		return meta == 1 || meta == 3 || meta == 5 || meta == 7;
	}
    
    @Override
	public boolean isSparse(IBlockAccess blockAccess, int x, int y, int z) {
		return isSparse(blockAccess.getBlockMetadata(x, y, z));
	}
	
    @Override
	public void setSparse(World world, int x, int y, int z)
    {
    	int meta = world.getBlockMetadata(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, meta + 1);
	}
    
    @Override
	public void setFullyGrown(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, meta - 1);
	}
    

	public void getSubBlocks(int par1, CreativeTabs tab, List list)
    {
//        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 4));
        list.add(new ItemStack(par1, 1, 6));
        
        //Sparse
//        list.add(new ItemStack(par1, 1, 1));
//        list.add(new ItemStack(par1, 1, 3));
//        list.add(new ItemStack(par1, 1, 5));
//        list.add(new ItemStack(par1, 1, 7));
    }
    
	//----------- Client Side Functionality -----------//    

	private boolean hasSnowOnTop; // temporary variable used by rendering
	public static boolean secondPass;
    
	private Icon[] iconSnowSideArray = new Icon[4];
	private Icon[] iconDirtArray = new Icon[4];
	private Icon[] iconGrassTopSparseDirtArray = new Icon[4]; //SCADDON: Changed to protected
	
	// duplicate variables to parent class to avoid base class modification
	private Icon iconGrassTop;
	private Icon iconGrassTopSparse;
	private Icon iconGrassSideOverlay;
	
	private Icon iconGrassTopSparseDirt;
	private Icon iconSnowSide;
	
	@Override
	public void registerIcons(IconRegister register)
	{
		super.registerIcons(register);
		
		iconSnowSideArray[0] = register.registerIcon("SCBlockDirtSnow_" + 3 );
		iconSnowSideArray[1] = register.registerIcon("SCBlockDirtSnow_" + 2 );
		iconSnowSideArray[2] = register.registerIcon("SCBlockDirtSnow_" + 1 );
		iconSnowSideArray[3] = register.registerIcon("SCBlockDirtSnow_" + 0 );
		
		iconDirtArray[0] = register.registerIcon("SCBlockDirtDry_" + 3 );
		iconDirtArray[1] = register.registerIcon("SCBlockDirtDry_" + 2 );
		iconDirtArray[2] = register.registerIcon("SCBlockDirtDry_" + 1 );
		iconDirtArray[3] = register.registerIcon("dirt");
        
        iconGrassTopSparseDirtArray[0] = register.registerIcon("SCBlockGrassSparseDirt_" + 3 );
    	iconGrassTopSparseDirtArray[1] = register.registerIcon("SCBlockGrassSparseDirt_" + 2 );
    	iconGrassTopSparseDirtArray[2] = register.registerIcon("SCBlockGrassSparseDirt_" + 1 );
    	iconGrassTopSparseDirtArray[3] = register.registerIcon("SCBlockGrassSparseDirt_" + 0 );
    	
    	
    	this.iconGrassTop = register.registerIcon("grass_top");
		this.iconSnowSide = register.registerIcon("snow_side");
		this.iconGrassSideOverlay = register.registerIcon("grass_side_overlay");

		iconGrassTopSparse = register.registerIcon("fcBlockGrassSparse");
		iconGrassTopSparseDirt = register.registerIcon("fcBlockGrassSparseDirt");
	}
	
	@Override
	public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		if (!secondPass) {
			if (side == 1 && this.isSparse(blockAccess, x, y, z)) {
				return this.iconGrassTopSparseDirtArray[ getNutritionLevel( blockAccess.getBlockMetadata(x, y, z) )];
			}
			else if (side > 1 && hasSnowOnTop) {
				Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, iconGrassTop);

				if (betterGrassIcon != null && betterGrassIcon != iconGrassTop && betterGrassIcon != iconGrassTopSparse) {
					return betterGrassIcon;
				}
				else {
					return iconSnowSideArray[ getNutritionLevel( blockAccess.getBlockMetadata(x, y, z) )];
				}
			}
			else {
				if (side == 0) {
					return iconDirtArray[ getNutritionLevel( blockAccess.getBlockMetadata(x, y, z) )];
				}
				return iconSnowSideArray[ getNutritionLevel( blockAccess.getBlockMetadata(x, y, z) )];
			}
		}
		else {
			return getBlockTextureSecondPass(blockAccess, x, y, z, side);
		}
	}
	
	public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
		Icon topIcon;

		if (isSparse(blockAccess, x, y, z)) {
			topIcon = iconGrassTopSparse;
		}
		else {
			topIcon = iconGrassTop;
		}

		Icon betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, topIcon);

		if (betterGrassIcon != null) {
			return betterGrassIcon;
		}
		else if (side == 1) {
			return topIcon;
		}
		else if (side > 1) {
			return this.iconGrassSideOverlay;
		}

		return null;
	}

		
	@Override
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z)
	{
		int meta = blockAccess.getBlockMetadata(x, y, z);
		
		if (hasSnowOnTop || !secondPass)
		{
			return 16777215;
		}
		else {
			if (getNutritionLevel(meta) == 2)
			{
				return color(blockAccess, x, y, z, 150 , -25 , 0);
			}
			else if (getNutritionLevel(meta) == 1)
			{
				return color(blockAccess, x, y, z, 300 , -50 , 0);
			}
			else if (getNutritionLevel(meta) == 0)
			{
				return color(blockAccess, x, y, z, 400 , -100 , 0);
			}
			else return color(blockAccess, x, y, z, 0 , 0 , 0);
		}
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side) {
		FCUtilsBlockPos pos = new FCUtilsBlockPos(neighborX, neighborY, neighborZ, Facing.oppositeSide[side]);

		if (!secondPass ) {
			//Don't render dirt under normal grass
			if (side == 1 && !isSparse(blockAccess, pos.i, pos.j, pos.k) && !hasSnowOnTop) {
				//return false;
			}
		}
		else {
			//Bottom never has a second pass texture
			if (side == 0) {
				return false;
			}
			//Snow has its own texture and should not render the second pass
			else if (side >= 2 && hasSnowOnTop) {
				return false;
			}
		}

		Block neighborBlock = Block.blocksList[blockAccess.getBlockId( neighborX, neighborY, neighborZ )];

		if ( neighborBlock != null )
		{
			return neighborBlock.ShouldRenderNeighborFullFaceSide( blockAccess, neighborX, neighborY, neighborZ, side ); 
		}

		return true;
	}
	
	private int color( IBlockAccess blockAccess, int i, int j, int k, int r, int g, int b) {

        for (int var8 = -1; var8 <= 1; ++var8)
        {
        	for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = blockAccess.getBiomeGenForCoords(i + var9, k + var8).getBiomeGrassColor();
                r += (var10 & 16711680) >> 16;
                g += (var10 & 65280) >> 8;
                b += var10 & 255;
            }
        }
        
        return (r / 9 & 255) << 16 | (g / 9 & 255) << 8 | b / 9 & 255;
	}
	
	@Override
	public void RenderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness)
	{
		SCUtilsRender.renderGrassBlockAsItem(renderer, this, iItemDamage, fBrightness, iconSnowSideArray, iconGrassTop, iconGrassSideOverlay);
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks render, int x, int y, int z) {
		hasSnowOnTop = IsSnowCoveringTopSurface(render.blockAccess, x, y, z);
		render.setRenderBounds(0, 0, 0, 1, 1, 1);
		return render.renderStandardBlock(this, x, y, z);
	}

	@Override
	public void RenderBlockSecondPass(RenderBlocks render, int x, int y, int z, boolean firstPassResult) {
		secondPass = true;
		render.setRenderBounds(0, 0, 0, 1, 1, 1);
		render.renderStandardBlock(this, x, y, z);
		secondPass = false;
	}
    
}