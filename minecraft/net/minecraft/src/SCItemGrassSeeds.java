package net.minecraft.src;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SCItemGrassSeeds extends Item {

	protected SCItemGrassSeeds(int itemID) {
		super(itemID);
		
	}
	
	public static void CreateFile() {
	    try {
	      File myObj = new File("filename.txt");
	      if (myObj.createNewFile()) {
	        System.out.println("File created: " + myObj.getAbsolutePath());
	      } else {
	        System.out.println("File already exists.");
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	}
	
	  public static void WriteToFile () {
		    try {
		      FileWriter myWriter = new FileWriter("filename.txt");
		      
		      for (int i = 0; i < ColorizerGrass.grassBuffer.length; i++) {
		    	  myWriter.write("grassBuffer["+i+"] = " + ColorizerGrass.grassBuffer[i] + "\n");
				}
		      
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		  }
	
	
	
	@Override
    public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int iFacing, float fClickX, float fClickY, float fClickZ )    
    {
		int blockID = world.getBlockId(x, y, z);
		BiomeGenBase currentBiome = world.getBiomeGenForCoords(x, z);
		
		int biomeType = getBiomeType(currentBiome.biomeID);
		
//		CreateFile();
//		WriteToFile();
		//System.out.println("int " + currentBiome.biomeName + " = " + currentBiome.getBiomeGrassColor());
		
		if (blockID == SCDefs.compostBlock.blockID && biomeType != -1)
		{			
			world.setBlockAndMetadata(x, y, z, SCDefs.biomeGrassCompost.blockID, biomeType);
			
			itemStack.stackSize--;
			
			if (!world.isRemote)
			{
				world.playAuxSFX( FCBetterThanWolves.m_iBlockPlaceAuxFXID, x, y, z, Block.tallGrass.blockID );              
			}
			
			return true;
		}
		
		return false;
    }

	private int getBiomeType(int biomeID) {
		boolean isPlains = biomeID == BiomeGenBase.plains.biomeID;
		boolean isDesert = biomeID == BiomeGenBase.desert.biomeID || biomeID == BiomeGenBase.desertHills.biomeID;
		boolean isXHills = biomeID == BiomeGenBase.extremeHills.biomeID || biomeID == BiomeGenBase.extremeHillsEdge.biomeID;
		boolean isForest = biomeID == BiomeGenBase.forest.biomeID || biomeID == BiomeGenBase.forestHills.biomeID;
		boolean isTaiga = biomeID == BiomeGenBase.taiga.biomeID || biomeID == BiomeGenBase.taigaHills.biomeID || biomeID == BiomeGenBase.icePlains.biomeID || biomeID == BiomeGenBase.iceMountains.biomeID;
		boolean isSwamp = biomeID == BiomeGenBase.swampland.biomeID;
		boolean isJungle = biomeID == BiomeGenBase.jungle.biomeID || biomeID == BiomeGenBase.jungleHills.biomeID;
		boolean isMushroom = biomeID == BiomeGenBase.mushroomIsland.biomeID || biomeID == BiomeGenBase.mushroomIslandShore.biomeID;
		//Extended
		boolean isIcy;
		boolean isAutumn;
		boolean isCherry;
		boolean isGrassland;
		boolean isHeath;
		boolean isSteppe;
		boolean isTemperate;
		boolean isFir;
		boolean isMeadow;
		boolean isBirch;
		boolean isBoreal;
		boolean isBrush;
		boolean isFungal;
		
		if (isPlains) return 0;
		else if (isDesert) return 1;
		else if (isXHills) return 2;
		else if (isForest) return 3;
		else if (isTaiga) return 4;
		else if (isSwamp) return 5;
		else if (isJungle) return 6;
		else if (isMushroom) return 7;
		else return 0;// -1;
	}

}
