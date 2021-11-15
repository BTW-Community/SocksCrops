package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockDirtLooseNutrition extends SCBlockDirtLooseBase {


	public SCBlockDirtLooseNutrition(int iBlockID) {
		super(iBlockID);
		this.setUnlocalizedName("SCBlockDirtLoose");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public int idDropped( int iMetadata, Random rand, int iFortuneModifier )
	{
		return this.blockID;
	
	}
	
	/**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
        return meta & 3;
    }
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 1));
        par3List.add(new ItemStack(par1, 1, 2));
        par3List.add(new ItemStack(par1, 1, 3));
    }

    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int iFromSide )
    {
    	int nutrientsLevel = getNutritionLevel(world, i, j, k);
    	
    	if (nutrientsLevel == 3)
    	{
    		world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition3.blockID );
    	}
    	else if (nutrientsLevel == 2)
    	{
    		world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition2.blockID );
    	}
    	else if (nutrientsLevel == 1)
    	{
    		world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition1.blockID );
    	}
    	else if (nutrientsLevel == 0) 
    	{
    		world.setBlockWithNotify( i, j, k, SCDefs.farmlandNutrition0.blockID );
    	}

    	if ( !world.isRemote )
		{
            world.playAuxSFX( 2001, i, j, k, blockID ); // block break FX
		}
    	
    	return true;
    }
    
	@Override
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
	{
		if (iMetadata == 0) //nutri 3
		{
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, fChanceOfDrop );
		}
		else
		{
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 6, 0, fChanceOfDrop );
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileGravel.itemID, 6, 0, fChanceOfDrop );
		}		
		
		return true;
	}
    
    @Override
    public boolean SpreadGrassToBlock( World world, int i, int j, int k )
    {
    	int nutrientsLevel = getNutritionLevel(world, i, j, k);
    	
    	if (nutrientsLevel == 3)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 0);
    	}
    	else if (nutrientsLevel == 2)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 1);
    	}
    	else if (nutrientsLevel == 1)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 2);
    	}
    	else world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 3);
    	
    	return true;
    }
    /**
     * Returns the block texture based on the side being looked at.  Args: side
     */
    public final Icon getBlockTextureFromMeta(int meta)
    {
        return this.getIcon(0, meta);
    }
    
    
    
    
}

