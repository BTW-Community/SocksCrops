package net.minecraft.src;

import java.util.Random;

public class SCBlockTomatoVine extends SCBlockTomatoStem {

	protected SCBlockTomatoVine(int iBlockID, String name) {
		super(iBlockID, name);
	}
    
	
	private float getFlowerChance() {

		return 0.5F;
	}

	private int getMaxPlantHeight()
	{
		return 4;
	}
	
	@Override
	protected boolean canConvertToRope() {
		
		return true;
	}

	
	@Override
	public boolean CanWeedsGrowInBlock( IBlockAccess blockAccess, int i, int j, int k )
	{
		return false;
	}	
	
	@Override
	protected void growVineAbove(World world, int x, int y, int z) 
	{
		int plantHeight = getPlantHeight(world, x, y, z);
		
		System.out.println("Plant Height: " + plantHeight);
		
		if (plantHeight < getMaxPlantHeight())
		{
			System.out.println("Growing Vine above");
			
			world.setBlockAndMetadataWithNotify(x, y + 1, z, SCDefs.tomatoVine.blockID, 8);
		}		
	}
	
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
        if ( UpdateIfBlockStays( world, i, j, k ) )
        {
        	// no plants can grow in the end
        	
	        if ( world.provider.dimensionId != 1 )
	        {
	        	if (!IsFullyGrown( world, i, j, k ) )
	        	{
	        		AttemptToGrow( world, i, j, k, rand );
	        	}
	        	else if (GetGrowthLevel(world, i, j, k) == 3 && canFlower())
	        	{
	        		//System.out.println("can flower");
	        		attemptToFlower(world, i, j, k, rand);
	        	}
	        }
        }
    }
	
	private boolean canFlower() {
		return true;
	}


	private void attemptToFlower(World world, int i, int j, int k, Random rand)
	{
		//System.out.println("trying to flower");

		if (!isNight(world) && rand.nextFloat() <= getFlowerChance())
		{
			//System.out.println("setting flowers");
			
			setBlocksToFlowering(world, i, j, k, rand);
		}

	}
	
    private void setBlocksToFlowering(World world, int x, int y, int z, Random rand) {
		
    	int plantHeight = getPlantHeight(world, x, y, z);
    	
		int id = world.getBlockId(x, y + 1, z);
		Block block = Block.blocksList[id];
		
    	if (block == SCDefs.fenceRope) 
    	{
    		if (((SCBlockFenceRope) block).GetExtendsAlongAxis(world, x, y + 1, z, 0) || ((SCBlockFenceRope) block).GetExtendsAlongAxis(world, x, y + 1, z, 2))
        	{
        		plantHeight = getMaxPlantHeight();
        	}
    	}
    	
    	System.out.println("Height: " + plantHeight);
    	
		if (plantHeight == getMaxPlantHeight())
		{
			System.out.println("Flowering");
			
			int count = 0;
			
            for ( int j = y - plantHeight; j <= y; ++j )
            {
				if (!isFlower(world, x, j, z)) {
					
					count++;
										
				}
            }
            
            System.out.println("Count " + count);
			 
            if (count > 0)
            {                
            	for (int num = 0; num < count; num++)
            	{
            		y = y - rand.nextInt(count);
            		
            		if (!isFlower(world, x, y, z)) {
            		 setFlower(world, x, y, z);
            		}
            	}
            }
		}
		
		

	}
	
	private void setFlower(World world, int x, int y, int z) {
		int id = world.getBlockId(x, y, z);
		Block block = Block.blocksList[id];
		int idBelow = world.getBlockId(x, y - 1, z);
		Block blockBelow = Block.blocksList[idBelow];
		int toBePlaced = 0;
		int metaPlaced = 0;
		
		if (blockBelow instanceof SCBlockFarmlandBase)
		{
			toBePlaced = idBelow;
			metaPlaced = world.getBlockMetadata(x, y - 1, z);
		}		
			
		if (block == SCDefs.tomatoVine)
		{
			world.setBlockAndMetadataWithNotify(x, y, z, SCDefs.tomatoVineFruits.blockID, 8);
		}
		
	}


	private boolean isFlower(World world, int x, int y, int z) {
		
		int id = world.getBlockId(x, y, z);
		Block block = Block.blocksList[id];
		
		if (block instanceof SCBlockTomatoVineFruits || block instanceof SCBlockTomatoStemFruits)
		{
			return true;
		}
		
		return false;
	}


	private int getPlantHeight(World world, int x, int y, int z) {
		
        int plantHeight = 1;
        
 
        while (Block.blocksList[ world.getBlockId( x, y - plantHeight, z ) ] instanceof SCBlockTomatoStem )
        {
        	plantHeight++;
        }
        
        return plantHeight;
	}



	protected void IncrementGrowthLevel( World world, int i, int j, int k )
    {
    	int iGrowthLevel = GetGrowthLevel( world, i, j, k ) + 1;
    	
        SetGrowthLevel( world, i, j, k, iGrowthLevel );
        
        //Added
        if (isBlockBelowStem(world, i, j - 1, k))
        {
        	int growthLevelBelow = GetGrowthLevel( world, i, j - 1, k ) + 1;
        	SetGrowthLevel(world, i, j - 1, k, growthLevelBelow);
        }
        
        if ( IsFullyGrown( world, i, j, k ) )
        {
        	Block blockBelow = Block.blocksList[world.getBlockId( i, j - 1, k )];
        	
        	if ( blockBelow != null )
        	{
        		blockBelow.NotifyOfFullStagePlantGrowthOn( world, i, j - 1, k, this );
        	}
        }
    }


	private boolean isBlockBelowStem(World world, int i, int j, int k) {

		if (world.getBlockId(i, j, k) == SCDefs.tomatoCrop.blockID && GetGrowthLevel( world, i, j, k ) < 7)
		{
			return true;
		}
		
		return false;
	}
	
	protected Icon vineLeaves;
	protected Icon[] cropIcons = new Icon[4];
	protected Icon[] leavesIcons = new Icon[4];
	
	@Override
	public void registerIcons(IconRegister register) {

		for(int i = 0; i < cropIcons.length; i++)
		{
			blockIcon = cropIcons[i] = register.registerIcon(name + "_" + i);
		}
		
		for(int i = 0; i < leavesIcons.length; i++)
		{
			leavesIcons[i] = register.registerIcon(name + "_leaves_" + i);
		}
		
		flowerIcon = register.registerIcon("SCBlockTomato_flower");
		
		vineLeaves = register.registerIcon( name +  "_leaves");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		int growthStage = GetGrowthLevel(meta);
		return cropIcons[growthStage & 3];
	}
	
	protected Icon getLeavesIcon(IBlockAccess blockAccess, int i, int j, int k)
	{
        int blockIDAbove = blockAccess.getBlockId(i, j + 1, k);
        Block blockAbove = Block.blocksList[blockIDAbove];
        
        if ( blockAbove != null && blockAbove instanceof SCBlockTomatoVine )
        {
        	return vineLeaves;
        }
		
		return leavesIcons[GetGrowthLevel(blockAccess, i,j,k) & 3];
	}
	
	@Override
	protected boolean shouldRenderRope() {
		
		return true;
	}

	@Override
	protected boolean shouldRenderLeaves(int meta)
	{
		return true;
	}
	
	@Override
	protected boolean shouldRenderWeeds() {
		return false;
	}
	
    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        int blockIDAbove = world.getBlockId(i, j + 1, k);
        Block blockAbove = Block.blocksList[blockIDAbove];
        
        int blockIDBelow = world.getBlockId(i, j - 1, k);
        Block blockBelow = Block.blocksList[blockIDBelow];
        
        if ( blockBelow != null && !(blockBelow instanceof SCBlockTomatoStem))
        {	
        	return false;
        }       
        
        if ( blockBelow != null && blockAbove != null && (blockAbove instanceof SCBlockTomatoVine || blockAbove instanceof SCBlockFenceRope))
        {
        	return true;
        }
        
        return false;
    }
	
}
