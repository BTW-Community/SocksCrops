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
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) {
		par3List.add(new ItemStack(par1, 1, 0));
		par3List.add(new ItemStack(par1, 1, 1));
		par3List.add(new ItemStack(par1, 1, 2));
		par3List.add(new ItemStack(par1, 1, 3));
	}
	
	@Override
	public int idPicked(World par1World, int par2, int par3, int par4) {
		// TODO Auto-generated method stub
		return super.idPicked(par1World, par2, par3, par4);
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
	
	@Override
	public void registerIcons(IconRegister register)
	{
		for(int type = 0; type < cropNames.length; type++)
		{
			crops[type] = register.registerIcon("SCBlockWildCrop_" + cropNames[type]);			
		}
		
		saladIcons[0] = register.registerIcon("SCBlockWildLettuceCrop_4");
		saladIcons[1] = register.registerIcon("SCBlockWildLettuceCrop_5");
		
		saladFlower = register.registerIcon("SCBlockWildLettuceFlower_7");
		
		onionOuter = register.registerIcon("SCBlockWildCrop_onionOuter");
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		return crops[meta];
	}

	@Override
	public boolean RenderBlock(RenderBlocks renderer, int i, int j, int k) {
		
		int type = renderer.blockAccess.getBlockMetadata(i, j, k);
		
		if (type == POTATO)
		{
			SCUtilsRender.renderBlockCropsWithAdjustmentWithTexture(renderer, this, i, j, k, crops[POTATO], 0/16F);
			
			return true;
		}
		else if (type == ONION )
		{
			SCUtilsRender.renderBlockCropsAtAngleWithTexture(renderer, this, i, j, k, onionOuter, 2/16F);
			
			return super.RenderBlock(renderer, i, j, k);
		}
		else if (type == SALAD )
		{			
			SCUtilsRender.renderBlockSaladWithTexture(renderer, this, i, j, k, saladIcons[1]);
			
			SCUtilsRender.renderBlockCropsAtAngleWithTexture(renderer, this, i, j, k, saladIcons[0], 2/16F);
			
			SCUtilsRender.renderCrossedSquaresWithTexture(renderer, this, i, j, k, saladFlower, false);
			
			return true;
		}
		else return super.RenderBlock(renderer, i, j, k);
	}
}
