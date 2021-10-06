package net.minecraft.src;

import java.util.Random;

public class SCBlockGrapeCrop extends BlockFlower {
	
	private Icon[] iconArray = new Icon[4];
	
	protected SCBlockGrapeCrop(int blockID) {
		super(blockID);
		this.setUnlocalizedName("SCBlockGrapeCrop");
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
    	
        checkFlowerChange(world, i, j, k); // replaces call to the super method two levels up in the hierarchy
        
        if ( world.provider.dimensionId != 1 && world.getBlockId( i, j, k ) == blockID ) // necessary because checkFlowerChange() may destroy the sapling
        {
            if ( world.getBlockLightValue( i, j + 1, k ) >= 9 && random.nextInt( 1 ) == 0 )
            {
                int iMetadata = world.getBlockMetadata( i, j, k );
                int iGrowthStage = ( iMetadata & (~3) ) >> 2;
 
                if ( iGrowthStage < 3 )
                {
                	iGrowthStage++;
                	iMetadata = ( iMetadata & 3 ) | ( iGrowthStage << 2 );
                	System.out.println("cropgrow: "+iGrowthStage);
                	
                    world.setBlockMetadataWithNotify( i, j, k, iMetadata );
                }
                else
                {
                    growTree( world, i, j, k, random );
                }
            }
        }
    }
	
	private void growTree(World world, int i, int j, int k, Random random) {
		world.setBlockWithNotify(i, j, k, SCDefs.grapeStem.blockID);
		System.out.println("I'm a stem!");
		
	}
	
	@Override
    protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k );
    }

	
    
    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    
    public void registerIcons( IconRegister register )
    {
    	iconArray[0] = register.registerIcon("SCBlockGrapeCrop_0");
    	iconArray[1] = register.registerIcon("SCBlockGrapeCrop_1");
    	iconArray[2] = register.registerIcon("SCBlockGrapeCrop_2");
    	iconArray[3] = register.registerIcon("SCBlockGrapeCrop_3");
    }
	
	
    public Icon getIcon( int iSide, int iMetadata )
    {
		int iGrowthStage = ( iMetadata & (~3) ) >> 2;
    	return iconArray[iGrowthStage];
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	
    	renderer.renderCrossedSquares(this, i, j -1/16, k);
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );

    	return true;
    }  
   
}
