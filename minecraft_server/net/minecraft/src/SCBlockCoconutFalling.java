package net.minecraft.src;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class SCBlockCoconutFalling extends FCBlockFalling {

    private static final double m_dArrowSpeedSquaredToExplode = 1.10D;
	
	public SCBlockCoconutFalling(int iBlockID) {
		super(iBlockID, Material.pumpkin);
		
		setTickRandomly( true );
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	int meta = world.getBlockMetadata(i, j, k);
    	
    	if ( !isConnectedToTree(world, i, j, k, rand) )
    	{
    		if ( getGrowthStage(meta) == 3 )
    		{
    			CheckForFall( world, i, j, k );
    			
    		}
    		else 
    		{
    			world.setBlockToAir(i, j, k);
    		}
    	}
    	else
    	{
    		if ( getGrowthStage(meta) == 3 )
    		{
    			if (playerIsUnder(world, i, j, k, rand))
    			{
    				CheckForFall( world, i, j, k );
    			}
    		}
    	}
 
    }
    
    private static final int m_iLoadedRangeToCheckFalling = 32;
    
	protected boolean CheckForFall( World world, int i, int j, int k)
	{
		if ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j >= 0 )
		{
			if ( !BlockSand.fallInstantly && world.checkChunksExist( 
					i - m_iLoadedRangeToCheckFalling, j - m_iLoadedRangeToCheckFalling, k - m_iLoadedRangeToCheckFalling, 
					i + m_iLoadedRangeToCheckFalling, j + m_iLoadedRangeToCheckFalling, k + m_iLoadedRangeToCheckFalling ) )
			{
				if ( !world.isRemote )
				{
					FCEntityFallingBlock fallingEntity = (FCEntityFallingBlock) EntityList.createEntityOfType(FCEntityFallingBlock.class, world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, 
							blockID, world.getBlockMetadata( i, j, k ) );

					onStartFalling( fallingEntity );

					world.spawnEntityInWorld( fallingEntity );
				}

				return true;
			}
			else
			{
				world.setBlockToAir( i, j, k );

				while ( CanFallIntoBlockAtPos( world, i, j - 1, k ) && j > 0 )
				{
					j--;
				}

				if ( j > 0 )
				{
					world.setBlock( i, j, k, SCDefs.coconutSapling.blockID);
				}

				return true;
			}
		}

		return false;
	}
    
    
    private boolean playerIsUnder(World world, int i, int j, int k, Random rand)
    {
        List players = world.getEntitiesWithinAABB( EntityPlayer.class, new AxisAlignedBB(i-1, j-16, k-1, i+1, j-1, k+1) );
        
        Iterator iterator = players.iterator();
    	
        while ( iterator.hasNext() )
        {
        	EntityPlayer tempCreature = (EntityPlayer)iterator.next();
        	
        	System.out.println(tempCreature.username);
        	return true;        
        }
        
        return false;
    }

	protected boolean isConnectedToTree(World world, int i, int j, int k, Random rand) {
		
    	int dir = getDirection(world.getBlockMetadata(i, j, k));
    	
    	if (dir == 3 && world.getBlockId(i - 1, j, k) == Block.wood.blockID) return true;
    	else if (dir == 1 && world.getBlockId(i + 1, j, k) == Block.wood.blockID) return true;
    	else if (dir == 0 && world.getBlockId(i, j, k - 1) == Block.wood.blockID) return true;
    	else if (dir == 2 && world.getBlockId(i, j, k + 1) == Block.wood.blockID) return true;
    	
    	return false;
	}
    
    protected boolean isConnectedToTree(IBlockAccess blockAccess, int i, int j, int k) {
		
    	int dir = getDirection(blockAccess.getBlockMetadata(i, j, k));

    	if (dir == 3 && blockAccess.getBlockId(i - 1, j, k) == Block.wood.blockID &&
    			blockAccess.getBlockMetadata(i - 1, j, k) < 12) return true;
    	
    	else if (dir == 1 && blockAccess.getBlockId(i + 1, j, k) == Block.wood.blockID &&
    			blockAccess.getBlockMetadata(i + 1, j, k) < 12) return true;
    	
    	else if (dir == 0 && blockAccess.getBlockId(i, j, k - 1) == Block.wood.blockID &&
    			blockAccess.getBlockMetadata(i, j, k - 1) < 12) return true;
    	
    	else if (dir == 2 && blockAccess.getBlockId(i, j, k + 1) == Block.wood.blockID &&
    			blockAccess.getBlockMetadata(i, j, k + 1) < 12 ) return true;
    	
    	return false;
	}
    
	protected int getGrowthStage(int meta)
	{
		if (meta < 4) return 0;
		else if (meta >= 4 && meta < 8) return 1;
		else if (meta >= 8 && meta < 12) return 2;
		else return 3;
	}
    
	@Override
    public int getMobilityFlag()
    {
    	// allow gourds to be pushed by pistons
    	return 0;
    }
	
    
    /**
     * Returns the orentation value from the specified metadata
     */
    public static int getDirection(int par0)
    {
        return par0 & 3;
    }
	
    @Override
    public boolean IsNormalCube(IBlockAccess blockAccess, int i, int j, int k) 
    {
    	return false;
    }
    
	@Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    @Override
    public boolean isOpaqueCube()
    {
    	return false;
    }

    @Override
    public void OnArrowImpact( World world, int i, int j, int k, EntityArrow arrow )
    {
    	if ( !world.isRemote )
    	{
    		double dArrowSpeedSq = arrow.motionX * arrow.motionX + arrow.motionY * arrow.motionY + arrow.motionZ * arrow.motionZ;
    		
    		if ( dArrowSpeedSq >= m_dArrowSpeedSquaredToExplode )
    		{
	    		world.setBlockWithNotify( i, j, k, 0 );
	    		
	    		Explode( world, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D , world.getBlockMetadata(i, j, k));
    		}
    		else
    		{    		
    			world.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
    		}
    	}
    }
    
    @Override
    public void OnBlockDestroyedWithImproperTool( World world, EntityPlayer player, int i, int j, int k, int iMetadata )
    {
    	// there's no improper tool to harvest gourds, but this also happens if the block is deleted after falling due to sitting on an
    	// improper block
    	
        world.playAuxSFX( AuxFXIDOnExplode(world,i,j,k, iMetadata), i, j, k, 0);
    }
    
    @Override
    public boolean OnFinishedFalling( EntityFallingSand entity, float fFallDistance )
    {
    	//entity.metadata = 0; // reset stem connection
    	
    	if ( !entity.worldObj.isRemote )
    	{
	        int i = MathHelper.floor_double( entity.posX );
	        int j = MathHelper.floor_double( entity.posY );
	        int k = MathHelper.floor_double( entity.posZ );
	        
	        int iFallDistance = MathHelper.ceiling_float_int( entity.fallDistance - 5.0F );
	        
	    	if ( iFallDistance >= 0 )
	    	{	    		
	    		DamageCollidingEntitiesOnFall( entity, fFallDistance );
	    		
	    		if ( !Material.water.equals( entity.worldObj.getBlockMaterial( i, j, k ) ) )
	    		{	    			
		    		if ( entity.rand.nextInt( 10 ) < iFallDistance )
		    		{
		    			Explode( entity.worldObj, (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, entity.metadata);
		    			
		    			return false;
		    		}
	    		}
	    	}
	    	
			entity.worldObj.playAuxSFX( FCBetterThanWolves.m_iMelonImpactSoundAuxFXID, i, j, k, 0 );
			
			this.setBlockOnFinishedFalling(entity, i, j, k);
    	}
        
    	return true;
    } 
    
	protected void setBlockOnFinishedFalling(EntityFallingSand entity, int i, int j, int k)	{

		entity.worldObj.setBlockAndMetadata(i, j, k, SCDefs.coconutSapling.blockID, 0);
	}

	//------------- Class Specific Methods ------------//
    
    
	abstract protected Item ItemToDropOnExplode();
	
	abstract protected int ItemCountToDropOnExplode(World world, int i, int k, int j, int meta);
	
	abstract protected int AuxFXIDOnExplode(World world, int i, int k, int j, int meta);
	
	abstract protected DamageSource GetFallDamageSource();	
	
    private void Explode( World world, double posX, double posY, double posZ, int entityMeta )
    {
    	Item itemToDrop = ItemToDropOnExplode();
    	
    	if ( itemToDrop != null )
    	{
	        for (int iTempCount = 0; iTempCount < ItemCountToDropOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta); iTempCount++)
	        {
	    		ItemStack itemStack = new ItemStack( itemToDrop, 1, 0 );
	
	            EntityItem entityItem = (EntityItem) EntityList.createEntityOfType(EntityItem.class, world, posX, posY+0.5, posZ, itemStack );
	            
	            entityItem.motionX = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            entityItem.motionY = 0.2D + world.rand.nextDouble() * 0.3D;
	            entityItem.motionZ = ( world.rand.nextDouble() - 0.5D ) * 0.5D;
	            
	            entityItem.delayBeforeCanPickup = 10;
	            
	            world.spawnEntityInWorld( entityItem );
	        }
    	}
    	
    	NotifyNearbyAnimalsFinishedFalling( world, MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ) );
        
        world.playAuxSFX( AuxFXIDOnExplode(world, (int)posX, (int)posY, (int)posZ, entityMeta), 
    		MathHelper.floor_double( posX ), MathHelper.floor_double( posY ), MathHelper.floor_double( posZ ), 
    		0 );
    }
    
    private void DamageCollidingEntitiesOnFall( EntityFallingSand entity, float fFallDistance )
    {
        int var2 = MathHelper.ceiling_float_int( fFallDistance - 1.0F );

        if (var2 > 0)
        {
            ArrayList collisionList = new ArrayList( entity.worldObj.getEntitiesWithinAABBExcludingEntity( entity, entity.boundingBox ) );
            
            DamageSource source = GetFallDamageSource();
            
            Iterator iterator = collisionList.iterator();

            while ( iterator.hasNext() )
            {
                Entity tempEntity = (Entity)iterator.next();
                
                tempEntity.attackEntityFrom( source, 4 );
            }

        }
    }

}
