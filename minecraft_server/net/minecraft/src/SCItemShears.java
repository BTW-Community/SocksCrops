// FCMOD

package net.minecraft.src;

public class SCItemShears extends FCItemShears
{
    public SCItemShears( int iItemID )
    {
    	super( iItemID );
    }
    
//    @Override
//    public boolean onBlockDestroyed( ItemStack stack, World world, int iBlockID, int i, int j, int k, EntityLiving usingEntity )
//    {
//        if ( iBlockID != Block.leaves.blockID && 
//        	iBlockID != FCBetterThanWolves.fcBlockBloodLeaves.blockID &&
//        	iBlockID != Block.web.blockID && 
//        	iBlockID != FCBetterThanWolves.fcBlockWeb.blockID &&
//        	iBlockID != Block.tallGrass.blockID && 
//        	iBlockID != Block.vine.blockID && 
//        	iBlockID != Block.tripWire.blockID &&
//        	iBlockID != FCBetterThanWolves.fcBlockHempCrop.blockID )
//        {
//            return super.onBlockDestroyed(stack, world, iBlockID, i, j, k, usingEntity);
//        }
//        else
//        {
//            stack.damageItem( 1, usingEntity );
//            return true;
//        }
//    }
//    
//    @Override
//    public boolean canHarvestBlock( ItemStack stack, World world, Block block, int i, int j, int k )
//    {
//        return block.blockID == Block.web.blockID || 
//        	block.blockID == FCBetterThanWolves.fcBlockWeb.blockID || 
//        	block.blockID == Block.redstoneWire.blockID || 
//        	block.blockID == Block.tripWire.blockID;
//    }
    
    protected boolean isSCBlock(Block block)
    {
    	return block instanceof SCBlockFruitTreesLeavesBase ||
    		   block instanceof SCBlockBushBase ||
    		   block instanceof SCBlockBambooShoot;
    }
    
    @Override
    public float getStrVsBlock( ItemStack stack, World world, Block block, int i, int j, int k ) 
    {
    	if ( IsEfficientVsBlock( stack, world, block, i, j, k ) )
		{
    		//SC: Added
        	if ( isSCBlock(block) )
        	{
        		return 15F;
        	}   
        	//END SC
    		
        	if ( block.blockID == FCBetterThanWolves.fcBlockBloodLeaves.blockID || 
        		block.blockID == Block.leaves.blockID ||
        		block.blockID == Block.web.blockID || 
        		block.blockID == FCBetterThanWolves.fcBlockWeb.blockID )
        	{
                return 15F;
        	}
        	else
        	{        		
        		return 5F;
        	}
		}
    	
    	return super.getStrVsBlock( stack, world, block, i, j, k );
    }


    
    @Override
    public boolean IsEfficientVsBlock( ItemStack stack, World world, Block block, int i, int j, int k )
    {
    	if ( !block.blockMaterial.isToolNotRequired() )
    	{
    		if ( canHarvestBlock( stack, world, block, i, j, k ) )
    		{
    			return true;
    		}
    	}
    	
    	//SC: Added
    	if ( isSCBlock(block) )
       	{
    		return true;
    	}   	
    	//END SC
    	
    	//DECO
		if (SCDecoIntegration.isDecoInstalled())
		{
			String prefix = FCUtilsReflection.isObfuscated() ? "net.minecraft.src." : "";
			
			try {
				if (block == SCDecoIntegration.carpet 
					|| block == SCDecoIntegration.cherryLeaves
					|| block == SCDecoIntegration.acaciaLeaves
					|| block == SCDecoIntegration.autumnLeaves
					|| Class.forName(prefix + "DecoBlockHedgeMouldingAndDecorative").isAssignableFrom(block.getClass())
					|| Class.forName(prefix + "DecoBlockHedgeSidingAndCornerDecorativeWall").isAssignableFrom(block.getClass())
					|| Class.forName(prefix + "DecoBlockHedge").isAssignableFrom(block.getClass()) )
				{
					return true;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		// END DECO
    	
    	if ( block == Block.cloth || 
    		block == Block.leaves || 
    		block == FCBetterThanWolves.fcBlockBloodLeaves || 
    		block == FCBetterThanWolves.fcWoolSlab || 
    		block == FCBetterThanWolves.fcBlockWoolSlabTop ||
    		block == Block.vine ||
    		block == FCBetterThanWolves.fcBlockHempCrop )
    	{
    		return true;
    	}
    	
        return false;
    }
    
}
