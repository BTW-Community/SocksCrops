package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinVineFlowering extends SCBlockGourdVineFloweringBase {
	
	protected Block fruitBlock;
	protected Block greenFruit;
	protected Block yellowFruit;
	protected Block whiteFruit;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockPumpkinVineFlowering(int iBlockID, int vineBlock,int stemBlock, Block fruitBlock, Block greenFruit, Block yellowFruit, Block whiteFruit) {
		super( iBlockID, vineBlock, stemBlock, fruitBlock, greenFruit);
		setUnlocalizedName("SCBlockPumpkinVineFlowering");
		this.fruitBlock = fruitBlock;
		this.greenFruit = greenFruit;
		this.yellowFruit = yellowFruit;
		this.whiteFruit = whiteFruit;
		this.vineBlock = vineBlock;
	}
	

	@Override
	protected void AttemptToGrowPumpkin(World world, int i, int j, int k, Random random) {
		Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
		int meta = world.getBlockMetadata(i, j, k);
		
		//Flowering stage
        FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );
        int iTargetFacing = 0;
        
        if ( random.nextFloat() <= 0.5F) {
        
	        if ( HasSpaceToGrow( world, i, j, k ) )
	        {
	        	// if the plant doesn't have space around it to grow, 
	        	// the fruit will crush its own stem
	        	
	            iTargetFacing = random.nextInt( 4 ) + 2;
	        	
	            targetPos.AddFacingAsOffset( iTargetFacing );
	        }
	        
	        if ( CanGrowFruitAt( world, targetPos.i, targetPos.j, targetPos.k ) )
	        {	
	        	blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j, k, this );
	        	
	        	//world.setBlockWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	        	//		SCDefs.pumpkinFresh.blockID );
	        	//System.out.println("grow Pumpkin");
	        	
	        	Block biomeFruit;
	        	
	        	//set default fruit for all biomes
	        	biomeFruit = this.fruitBlock;
	        	
	        	BiomeGenBase targetBiome = world.getBiomeGenForCoords(targetPos.i, targetPos.k);
	        	
	        	BiomeGenBase greenPumpkin[] = { BiomeGenBase.swampland, BiomeGenBase.river,
	        									BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge
	        									};
	        	
	        	BiomeGenBase yellowPumpkin[] = { BiomeGenBase.desert, BiomeGenBase.desertHills,
	        									 BiomeGenBase.jungle, BiomeGenBase.jungleHills,
	        									 BiomeGenBase.beach
	        									 };
	        	
	        	BiomeGenBase whitePumpkin[] = { BiomeGenBase.icePlains, BiomeGenBase.iceMountains,
	        									BiomeGenBase.taiga, BiomeGenBase.taigaHills,
	        									BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver
	        									};
	        	
	        	BiomeGenBase allPumpkins[] = { BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore };
	        	
	        	//Also all on mycelum
	        	
	        	for (int a = 0; a < allPumpkins.length; a++) {
	        		if (targetBiome == allPumpkins[a] || blockBelow.blockID == Block.mycelium.blockID)
		        	{
	        			for (int r = 0; r <=3 ; r++) {
	        				int rand = random.nextInt(4);
	        				
	        				if (rand == 0) {
	        					biomeFruit = this.fruitBlock;
	        				}
	        				else if (rand == 1) {
	        					biomeFruit = this.greenFruit;
	        				}
	        				else if (rand == 2) {
	        					biomeFruit = this.yellowFruit;
	        				}
	        				else if (rand == 3) {
	        					biomeFruit = this.whiteFruit;
	        				}
	        		        		
						}
		        	}
				}
	        	
	        	
	        	for (int g = 0; g < greenPumpkin.length; g++) {
	        		if (targetBiome == greenPumpkin[g])
		        	{
		        		biomeFruit = this.greenFruit;
		        	}
				}
	        	
	        	for (int y = 0; y < yellowPumpkin.length; y++) {
	        		if (targetBiome == yellowPumpkin[y])
		        	{
		        		biomeFruit = this.yellowFruit;
		        	}
				}
	        	
	        	for (int w = 0; w < whitePumpkin.length; w++) {
	        		if (targetBiome == whitePumpkin[w])
		        	{
		        		biomeFruit = this.whiteFruit;
		        	}
				}
	        	
	        	
	        	
	        	if ( iTargetFacing != 0 )
	        	{
	        		if (iTargetFacing == 2)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	    				biomeFruit.blockID, 2 ); //TODO change back to Sleeping
	    	    		
	    	    	} else if (iTargetFacing == 3)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	    				biomeFruit.blockID, 0 );//TODO change back to Sleeping
	    	    		
	    	    	} else if (iTargetFacing == 4)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	    				biomeFruit.blockID, 1 );//TODO change back to Sleeping
	    	    	} else if (iTargetFacing == 5)
	    	    	{
	    	    		world.setBlockAndMetadataWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	    	    				biomeFruit.blockID, 3 );//TODO change back to Sleeping
	    	    	}
	        		
	        		int dir = getDirection(meta);
	        		
	                world.setBlockAndMetadataWithNotify( i, j, k,this.blockID ,dir + 12); //setting growth level to 0
	        	} 
	        }
        }
		
	}

}
