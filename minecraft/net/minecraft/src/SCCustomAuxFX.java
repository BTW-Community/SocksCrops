package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

public class SCCustomAuxFX {
	
	public static final int melonExplodeAuxFXID = 2300;	
	public static final int melonCantaloupeExplodeAuxFXID = 2301;
	public static final int melonHoneydewExplodeAuxFXID = 2302;
	public static final int melonCanaryExplodeAuxFXID = 2303;    
	public static final int pumpkinExplodeAuxFXID = 2305;
	
	public static final int coconutExplodeAuxFXID = 2306;
	
	public static final int choppingBoardAuxFXID = 2320;
	
	public static boolean playChoppingBoardAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData) {
		Random rand = world.rand;

		double posX = (double)i + 0.5F;
		double posY = (double)j + 0.5F;
		double posZ = (double)k + 0.5F;

		int iParticleSetting = mcInstance.gameSettings.particleSetting;

		switch (iFXID)
		{
		case choppingBoardAuxFXID:
			
			// 360 = melon slice based particles

			String particle = "iconcrack_" + Item.melon.itemID;
			if (iFXID == choppingBoardAuxFXID )
			{
				SCTileEntityChoppingBoard choppingBoard = (SCTileEntityChoppingBoard) world.getBlockTileEntity(i, j, k);
				
				if (choppingBoard.getKnifeStack() != null)
				{
					Item itemOnBoard = choppingBoard.getKnifeStack().getItem();
					
					SCCraftingManagerChoppingBoardFilterRecipe recipe = itemOnBoard == null ?
							null : SCCraftingManagerChoppingBoardFilter.instance.getRecipe2(choppingBoard.getKnifeStack());
					
					if (recipe != null) {
						ItemStack[] output = recipe.getBoardOutput();
	    	    		
	    				assert( output != null && output.length > 0 );
	    				   			
	    				int cutStackID = output[0].itemID;
	    				
	    				particle = "iconcrack_" + cutStackID;
					}
					else particle = "iconcrack_" + Item.stick.itemID;
					
				}
				else particle = "iconcrack_" + Item.stick.itemID;
					
				for (int iTempCount = 0; iTempCount < 2; iTempCount ++)
				{
					double dChunkX = posX + world.rand.nextDouble() - 0.5D;
					double dChunkY = posY - 6/16D;
					double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;

					double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.1D;
					double dChunkVelY = world.rand.nextDouble() * 0.3D;
					double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.1D;

					world.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, 
							dChunkVelX, dChunkVelY, dChunkVelZ);
				}    	
				
			} 

			//world.playSound(posX, posY, posZ, "mob.zombie.wood", 0.2F, 0.60F + (rand.nextFloat() * 0.25F));

			//world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
			break;
			
		default:

			return false;
		}

		return true;
		
	}
	
	public static boolean playGourdAuxFX(Minecraft mcInstance, World world, EntityPlayer player, int iFXID, int i, int j, int k, int iFXSpecificData) {
		Random rand = world.rand;

		double posX = (double)i + 0.5F;
		double posY = (double)j + 0.5F;
		double posZ = (double)k + 0.5F;

		int iParticleSetting = mcInstance.gameSettings.particleSetting;

		switch (iFXID)
		{
		case melonExplodeAuxFXID:
		case melonCantaloupeExplodeAuxFXID:
		case melonHoneydewExplodeAuxFXID:
		case melonCanaryExplodeAuxFXID:
		case pumpkinExplodeAuxFXID:
		case coconutExplodeAuxFXID:
			
			// 360 = melon slice based particles

			String particle = "iconcrack_" + Item.melon.itemID;
			
			if (iFXID == coconutExplodeAuxFXID)
			{
				particle = "iconcrack_" + SCDefs.coconutSlice.itemID;
			}
			
//			if (iFXID == pumpkinExplodeAuxFXID)
//			{
//				particle = "iconcrack_" + Item.pumpkinSeeds.itemID;
//			}
//			else if (iFXID == melonCanaryExplodeAuxFXID)
//			{
//				particle = "iconcrack_" + SCDefs.melonCanarySlice.itemID;
//			}
//			else if (iFXID == melonHoneydewExplodeAuxFXID)
//			{
//				particle = "iconcrack_" + SCDefs.melonHoneydewSlice.itemID;
//			}
//			else if (iFXID == melonCantaloupeExplodeAuxFXID)
//			{
//				particle = "iconcrack_" + SCDefs.melonCantaloupeSlice.itemID;
//			}

			
			for (int iTempCount = 0; iTempCount < 150; iTempCount ++)
			{
				double dChunkX = posX + world.rand.nextDouble() - 0.5D;
				double dChunkY = posY - 0.45D;
				double dChunkZ = posZ + world.rand.nextDouble() - 0.5D;

				double dChunkVelX = (world.rand.nextDouble() - 0.5D) * 0.5D;
				double dChunkVelY = world.rand.nextDouble() * 0.7D;
				double dChunkVelZ = (world.rand.nextDouble() - 0.5D) * 0.5D;

				world.spawnParticle(particle, dChunkX, dChunkY, dChunkZ, 
						dChunkVelX, dChunkVelY, dChunkVelZ);
			}    	        

			world.playSound(posX, posY, posZ, "mob.zombie.wood", 0.2F, 0.60F + (rand.nextFloat() * 0.25F));

			world.playSound(posX, posY, posZ, "mob.slime.attack", 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.6F);
			break;
			
		default:

			return false;
		}

		return true;
		
	}
}
