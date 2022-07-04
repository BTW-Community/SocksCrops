package net.minecraft.src;

import java.util.Random;

public abstract class SCBlockGourdGrowing extends SCBlockGourdFalling {
	
	protected int stemBlock;
	protected int vineBlock;
	protected int flowerBlock;
	protected int convertedBlockID;
	
	protected SCBlockGourdGrowing(int iBlockID, int stemBlock, int vineBlock, int flowerBlock, int convertedBlockID) {
		super(iBlockID);
		
		this.stemBlock = stemBlock;
		this.vineBlock = vineBlock;
        this.flowerBlock = flowerBlock;
        this.convertedBlockID = convertedBlockID;
        
        SetAxesEffectiveOn( true );
        
        SetBuoyant();        
        
		setHardness(1.0F);
        
        setStepSound(soundWoodFootstep);
        
	}
	
	protected float GetBaseGrowthChance()
    {
    	return 0.1F;
    }
	
	protected float getPossesionChance()
    {
    	return 1.0F;
    }
	
	protected int GetPortalRange()
    {
    	return 16;
    }
	
	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{	
		if (!canBlockStay(world, i, j, k))
		{
			this.convertBlock(world, i, j, k); //converts the block to the non growing/harvested version
			//super.updateTick(world, i, j, k, random); //check falling, we don't as the converted block handles the falling
		}
		else
		{
			if ( this.canBePossessed() && random.nextFloat() <= getPossesionChance() && hasPortalInRange(world, i, j, k) )
		    {
				this.becomePossessed(world, i, j, k, random);
		    }
			else if ( checkTimeOfDay(world) && !IsFullyGrown( world, i, j, k) && random.nextFloat() <= this.GetBaseGrowthChance() ) //daytime
			{
				this.grow(world, i, j, k, random);
			}
		}				
	}

	protected boolean canBePossessed()
	{
		return false;
	}

	protected void convertBlock(World world, int i, int j, int k)
	{	
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		int harvestedMeta = getMetaHarvested(growthLevel);
		
		world.setBlockAndMetadata(i, j, k, convertedBlockID , harvestedMeta);
	}

	/**
	 * Returns the meta harvested for the given growth Level
	 * @param growthLevel
	 */
	protected abstract int getMetaHarvested(int growthLevel);

	private void grow(World world, int i, int j, int k, Random random)
	{
		int meta = world.getBlockMetadata(i, j, k);        
		world.setBlockAndMetadataWithNotify(i, j, k, this.blockID ,meta + 4);
	}

	protected int GetGrowthLevel( int meta) {
		
		if (meta <= 3) 
		{
			return 0;
		}
		else if (meta > 3 && meta <= 7) 
		{
			return 1;
		}
		else if (meta > 7 && meta <= 11) 
		{
			return 2;
		} 
		else return 3;
	}
	
	protected boolean checkTimeOfDay(World world) {
		int iTimeOfDay = (int)( world.worldInfo.getWorldTime() % 24000L );
		return (iTimeOfDay > 24000 || iTimeOfDay > 0 && iTimeOfDay < 14000 );
	}

	protected int GetGrowthLevel( IBlockAccess blockAccess, int x, int y, int z)
	{		
		return GetGrowthLevel(blockAccess.getBlockMetadata(x, y, z));		
	}
	
	protected int GetGrowthLevel( World world, int x, int y, int z) {

		return GetGrowthLevel(world.getBlockMetadata(x, y, z));		
	}
	
	protected boolean IsFullyGrown( int iMetadata )
    {
    	return GetGrowthLevel( iMetadata ) == 3;
    }	
	
	protected boolean IsFullyGrown(World world, int i, int j, int k) {
		return IsFullyGrown(world.getBlockMetadata(i, j, k));
	}
	
	protected void becomePossessed(World world, int i, int j, int k, Random random)
	{
		int growthLevel = this.GetGrowthLevel(world, i, j, k);
		
		for (int growthLevelIndex = 0; growthLevelIndex < 4; growthLevelIndex++) {
			if (growthLevel == growthLevelIndex)
			{
				//world.setBlockAndMetadata(i, j, k, SCDefs.pumpkinPossessed.blockID, getPossessedMetaForGrowthLevel(growthLevel));
				
				world.playAuxSFX( FCBetterThanWolves.m_iGhastMoanSoundAuxFXID, 
			            MathHelper.floor_double( i ), MathHelper.floor_double( j ), MathHelper.floor_double( k ), 0 );
			}			
		}

	}

	protected abstract int getPossessedMetaForGrowthLevel(int growthLevel);

	@Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return CanGrowOnBlock( world, i, j - 1, k ) && hasVineFacing(world, i, j, k);
    }
	
	protected boolean CanGrowOnBlock( World world, int i, int j, int k )
    {
    	Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];
    	
    	return blockOn != null && (world.doesBlockHaveSolidTopSurface( i, j, k ) || blockOn.CanDomesticatedCropsGrowOnBlock( world, i, j, k ));
    }
	
	protected boolean hasVineFacing( World world, int i, int j, int k )
    {
    	FCUtilsBlockPos targetPos = new FCUtilsBlockPos( i, j, k );

	    int dir = getDirection(world.getBlockMetadata(i, j, k));
	    
	    
	    int oppositeFacing = Direction.footInvisibleFaceRemap[dir];
	    int iTargetFacing = Direction.directionToFacing[oppositeFacing];
	    
	    
	    
	    targetPos.AddFacingAsOffset( iTargetFacing );
	    
	    int targetBlockID = world.getBlockId(targetPos.i, targetPos.j, targetPos.k);
	    
	    if ( targetBlockID == this.vineBlock || targetBlockID == this.flowerBlock || targetBlockID == SCDefs.gourdVineDead.blockID)
	    {	
	    	return true;
	    	
	    }
	    else return false;
	}
	
	
    protected boolean hasPortalInRange( World world, int i, int j, int k )
    {
    	int portalRange = GetPortalRange();
    	
        for ( int iTempI = i - portalRange; iTempI <= i + portalRange; iTempI++ )
        {
            for ( int iTempJ = j - portalRange; iTempJ <= j + portalRange; iTempJ++ )
            {
                for ( int iTempK = k - portalRange; iTempK <= k + portalRange; iTempK++ )
                {
                    if ( world.getBlockId( iTempI, iTempJ, iTempK ) == Block.portal.blockID )
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
