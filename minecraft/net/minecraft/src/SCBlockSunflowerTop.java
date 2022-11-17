package net.minecraft.src;

import java.util.Iterator;
import java.util.Random;

public class SCBlockSunflowerTop extends BlockFlower {

	protected SCBlockSunflowerTop(int par1) {
		super(par1, Material.plants);
		setUnlocalizedName("SCBlockSunflowerTop");
		setCreativeTab(CreativeTabs.tabAllSearch);
	}
	
	private int updateRotationForTime( World world, int i, int j, int k, Random rand )
	{
		int timeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );

		boolean isMorning = timeOfDay >= 0 && timeOfDay < 3000;
		boolean isPreNoon = timeOfDay >= 3000 && timeOfDay < 6000;
		boolean isAfterNoon = timeOfDay >= 6000 && timeOfDay < 9000;
		boolean isEvening = timeOfDay >= 9000 && timeOfDay < 12000;

		boolean earlyNight = timeOfDay >= 12000 && timeOfDay < 15000;
		boolean preMoon = timeOfDay >= 15000 && timeOfDay < 18000;
		boolean afterMoon = timeOfDay >= 18000 && timeOfDay < 21000;
		boolean lateNight = timeOfDay >= 21000 && timeOfDay < 24000;
		
		int meta = world.getBlockMetadata(i, j, k);
		int rotation = 0;

		if (isMorning) rotation = 0;
		else if (isPreNoon) rotation = 1;
		else if (isAfterNoon) rotation = 2;
		else if (isEvening) rotation = 3;
		else if (earlyNight) rotation = 3;
		else if (preMoon) rotation = 2;
		else if (afterMoon) rotation = 1;
		else if (lateNight) rotation = 0;
		
		return rotation;
	}
	
	@Override
	public void updateTick( World world, int i, int j, int k, Random rand )
	{

		int rotation = updateRotationForTime(world, i, j, k, rand);
		
		world.setBlockAndMetadata(i, j, k, this.blockID, rotation);

		//AttemptToGrow( world, i, j, k, rand );

	}
	
//	@Override
//	protected int GetCropItemID() {
//		return 0;
//	}
//
//	@Override
//	protected int GetSeedItemID() {
//		return 0;
//	}
	
	private Icon front[] = new Icon[4];
	private Icon back[] = new Icon[4];
	private Icon stem[] = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		blockIcon = register.registerIcon("SCBlockFlowerSunflower_front_3");
		
		for (int i = 0; i < 4; i++) {
			front[i] = register.registerIcon("SCBlockFlowerSunflower_front_" + i);
			back[i] = register.registerIcon("SCBlockFlowerSunflower_back_" + i);			
			stem[i] = register.registerIcon("SCBlockFlowerSunflower_top_" + i);
		}
	}
	
	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		int growthLevel = renderer.blockAccess.getBlockMetadata(i, j, k) & 3;
		int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
		float flowerHeight = 0/16F;

		if (growthLevel <= 1)
		{
			SCUtilsRender.renderSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, front[3], flowerHeight, growthLevel);
			SCUtilsRender.renderBackSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, back[3], flowerHeight, growthLevel);
		}
		else
		{
			SCUtilsRender.renderSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, back[3], flowerHeight, growthLevel);
			SCUtilsRender.renderBackSunflowerPlaneWithTexturesAndRotation(renderer, this, i, j, k, front[3], flowerHeight, growthLevel);
		}

		
		SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, stem[3], false);
		return true;
	}



}
