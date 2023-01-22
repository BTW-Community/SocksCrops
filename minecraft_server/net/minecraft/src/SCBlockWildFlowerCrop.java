package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockWildFlowerCrop extends FCBlockFlowerBlossom {

	public static String[] cropNames = {"carrot", "potato", "onion", "salad" };
	
	public static final int CARROT = 0;
	public static final int POTATO = 1;
	public static final int ONION = 2;
	public static final int SALAD = 3;
	
	protected SCBlockWildFlowerCrop(int blockID) {
		super(blockID);
		setUnlocalizedName("SCBlockWildCrop");
		setCreativeTab(CreativeTabs.tabDecorations);
	}
	

	@Override
	public int idDropped(int meta, Random par2Random, int iFortuneModifier)
	{		
		if (meta == CARROT)
		{
			return SCDefs.wildCarrot.itemID;
		}
		else if (meta == POTATO)
		{
			return SCDefs.sweetPotato.itemID;
		}
		else if (meta == ONION)
		{
			return SCDefs.wildOnion.itemID;
		}
		else if (meta == SALAD)
		{
			return SCDefs.wildLettuce.itemID;
		}
		
		return 0;		
	}	
	
	private int seedDropped(int meta, Random rand, int fortuneModifier) {
		
		if (meta == CARROT)
		{
			return FCBetterThanWolves.fcItemCarrotSeeds.itemID;
		}
		else if (meta == POTATO)
		{
			return SCDefs.sweetPotato.itemID;
		}
		else if (meta == ONION)
		{
			return SCDefs.wildOnionSeeds.itemID;
		}
		else if (meta == SALAD)
		{
			return SCDefs.wildLettuceSeeds.itemID;
		}
		
		return 0;
	}
	
	
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float dropChance, int fortuneModifier)
    {
        if (!world.isRemote)
        {
            int quantity = this.quantityDroppedWithBonus(fortuneModifier, world.rand);

            for (int i = 0; i < quantity; ++i)
            {
                if (world.rand.nextFloat() <= dropChance)
                {
                    int itemID = this.idDropped(meta, world.rand, fortuneModifier);

                    if (itemID > 0)
                    {
                        this.dropBlockAsItem_do(world, x, y, z, new ItemStack(itemID, 1, this.damageDropped(meta)));
                    }
                    
                }
            }
            
            //SCADDON: ADDED
            int seedID = this.seedDropped(meta, world.rand, fortuneModifier);
            
            if (seedID > 0)
            {
                this.dropBlockAsItem_do(world, x, y, z, new ItemStack(seedID, 1, 0));
            }
        }
    }


	private Icon[] crops = new Icon[cropNames.length];
	private Icon onionOuter;
	private Icon[] saladIcons = new Icon[2];
	private Icon saladFlower;

}
