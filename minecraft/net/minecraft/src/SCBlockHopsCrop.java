package net.minecraft.src;

import java.util.Random;

public class SCBlockHopsCrop extends FCBlockCrops {

	protected SCBlockHopsCrop(int iBlockID) {
		super(iBlockID);
	    setHardness( 0.2F );
	    SetAxesEffectiveOn( true );        
	        
	    SetBuoyant();        
	    SetFireProperties( FCEnumFlammability.CROPS );
			
		setUnlocalizedName("SCBlockHopsCrop");
	}

	@Override
	protected int GetCropItemID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int GetSeedItemID() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
    public float GetBaseGrowthChance( World world, int i, int j, int k )
    {
    	return 0.5F;
    }
    
    @Override
    public void updateTick( World world, int i, int j, int k, Random random )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
                int meta = world.getBlockMetadata( i, j, k );
                
                if ( !IsFullyGrown(meta) )
                {
                	AttemptToGrow( world, i, j, k, random );
                }
                else
                {
                	int blockAbove = world.getBlockId( i, j + 1, k );
                	
                	if (blockAbove == SCDefs.fenceRope.blockID)
                	{
                		attemptToGrowLeavesAbove( world, i, j, k, random );
                	}
                }
	        }
        }
    }
    
    protected void attemptToGrowLeavesAbove( World world, int i, int j, int k, Random rand )
    {
    	if ( world.GetBlockNaturalLightValue( i, j + 1, k ) >= GetLightLevelForGrowth() )
	    {
	        Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
	        
	        if ( blockBelow != null && blockBelow.IsBlockHydratedForPlantGrowthOn( world, i, j - 1, k ) )
	        {
	    		float fGrowthChance = GetBaseGrowthChance( world, i, j, k ) *
	    			blockBelow.GetPlantGrowthOnMultiplier( world, i, j - 1, k, this );
	    		
	            if ( rand.nextFloat() <= fGrowthChance )
	            {
	            	int meta = world.getBlockMetadata(i, j + 1, k);
	            	world.setBlockAndMetadataWithNotify(i, j + 1, k, SCDefs.hopsVine.blockID, meta);
	            }
	        }
	    }
    }
    
    protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) >= 3;
    }
    
    private Icon[] stem = new Icon [4];
    private Icon[] leaves = new Icon [3];
    private boolean leavesPass;
    
    @Override
    public void registerIcons(IconRegister register) {
    	stem[0] = register.registerIcon("SCBlockHopsStem_1");
    	stem[1] = register.registerIcon("SCBlockHopsStem_2");
    	stem[2] = register.registerIcon("SCBlockHopsStem_3");
    	stem[3] = register.registerIcon("SCBlockHopsStem_4");
    	
    	leaves[0] = register.registerIcon("SCBlockHopsStem_leaves_0");
    	leaves[1] = register.registerIcon("SCBlockHopsStem_leaves_1");
    	leaves[2] = register.registerIcon("SCBlockHopsStem_leaves_2");
    	
    	blockIcon = stem[3];
    }
    
    @Override
    public Icon getIcon(int side, int meta) {
    	if (leavesPass)
    	{
    		return leaves[meta - 1];
    	}
    	else return stem[meta];
    }
    
    @Override
    public boolean RenderBlock( RenderBlocks renderer, int i, int j, int k )
    {
    	int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
    	
    	renderer.renderCrossedSquares(this, i, j, k);
    	
    	leavesPass = true;
    	if (meta > 0)
    	{
        	renderer.renderBlockCrops(this, i,j,k);
    	}
    	leavesPass = false;
    	
    	FCBetterThanWolves.fcBlockWeeds.RenderWeeds( this, renderer, i, j, k );
		
    	return true;
    }

}
