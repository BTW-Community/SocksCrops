package net.minecraft.src;

import java.util.Random;

public class SCBlockMelonVineFlowering extends SCBlockGourdVineFloweringBase {
	
	protected Block fruitBlock;
	protected int vineBlock;
	protected int stemBlock;

	protected SCBlockMelonVineFlowering(int iBlockID, int vineBlock,int stemBlock, Block fruitBlock) {
		super( iBlockID, vineBlock, stemBlock, fruitBlock, null);
		setUnlocalizedName("SCBlockPumpkinVineFlowering");
		this.fruitBlock = fruitBlock;
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
	        	blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
	        	
	        	//world.setBlockWithNotify( targetPos.i, targetPos.j, targetPos.k, 
	        	//		SCDefs.pumpkinFresh.blockID );
	        	//System.out.println("grow Pumpkin");
	        	
	        	Block biomeFruit;
	        	
	        	biomeFruit = this.fruitBlock;
	        	
//	        	if (world.getBiomeGenForCoords(targetPos.i, targetPos.k) == BiomeGenBase.forest)
//	        	{
//	        		biomeFruit = this.greenFruit;
//	        	}
	        	
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
