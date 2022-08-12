package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockDirtLooseNutrition extends SCBlockDirtLooseBase {
	
	public static final String[] nutritionLevelNames  = new String[] {"looseDirt0", "looseDirt1", "looseDirt2", "looseDirt3"};

	public SCBlockDirtLooseNutrition(int blockID) {
		super(blockID);
		this.setUnlocalizedName("SCBlockDirtLooseNutrition");
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public void NotifyOfFullStagePlantGrowthOn( World world, int i, int j, int k, Block plantBlock )
	{
		int meta = world.getBlockMetadata(i, j, k);
		
		if (meta < 3)
		{
			// revert back to soil
			world.setBlockAndMetadataWithNotify( i, j, k, this.blockID, meta + 1 );
		}

	}
	
	@Override
	public float GetPlantGrowthOnMultiplier( World world, int i, int j, int k, Block plantBlock )
	{
		return getNutritionMultiplier(world.getBlockMetadata(i, j, k));
	}

	private float getNutritionMultiplier(int meta) {
		if (meta < 1) return 1F;
		else if (meta < 2) return 0.75F;
		else if (meta < 3) return 0.5F;
		else return 0.25F;

	}
	
	@Override
	public int idDropped( int meta, Random rand, int fortuneModifier )
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
    public void getSubBlocks(int id, CreativeTabs creativeTabs, List list)
    {
        list.add(new ItemStack(id, 1, 0));
        list.add(new ItemStack(id, 1, 1));
        list.add(new ItemStack(id, 1, 2));
        list.add(new ItemStack(id, 1, 3));
    }

    @Override
    public boolean ConvertBlock( ItemStack stack, World world, int i, int j, int k, int fromSide )
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
	public boolean DropComponentItemsOnBadBreak( World world, int i, int j, int k, int meta, float chanceOfDrop )
	{
		if (meta == 0) //nutri 3
		{
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileDirt.itemID, 6, 0, chanceOfDrop );
		}
		else
		{
			DropItemsIndividualy( world, i, j, k, FCBetterThanWolves.fcItemPileSand.itemID, 3, 0, chanceOfDrop );
		}		
		
		return true;
	}
    
    @Override
    public boolean SpreadGrassToBlock( World world, int i, int j, int k )
    {
    	int nutrientsLevel = getNutritionLevel(world, i, j, k);
    	
    	if (nutrientsLevel == 3)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, Block.grass.blockID , 1);
    	}
    	else if (nutrientsLevel == 2)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 3);
    	}
    	else if (nutrientsLevel == 1)
    	{
    		world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 5);
    	}
    	else world.setBlockAndMetadataWithNotify( i, j, k, SCDefs.grassNutrition.blockID , 7);
    	
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

