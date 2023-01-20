package net.minecraft.src;

import java.util.Random;

public class SCBlockPieRaw extends SCBlockPieBase {

	public static final int PUMPKIN = 0;
	public static final int SWEETBERRY = 1;
	public static final int BLUEBERRY = 2;
	
	protected SCBlockPieRaw(int blockID)
	{
		super(blockID);
		setUnlocalizedName("SCBlockPieRaw");
	}
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier)
	{
		if (meta == PUMPKIN) return FCBetterThanWolves.fcItemPastryUncookedPumpkinPie.itemID;
		else if (meta == SWEETBERRY) return SCDefs.sweetberryPieRaw.itemID;
		else if (meta == BLUEBERRY) return SCDefs.blueberryPieRaw.itemID;
		else return 0;
	}
	
	@Override
	public int idPicked(World world, int i, int j, int k) {

		return idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
	}
	
	protected Icon rawPastry;
	protected Icon[] pieTop = new Icon[16];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		blockIcon = rawPastry = register.registerIcon( "fcBlockPastryUncooked" );
		pieTop[PUMPKIN] = register.registerIcon( "fcBlockUncookedPumpkinPie_top" );
		pieTop[SWEETBERRY] = register.registerIcon( "SCBlockPieRawTop_sweetberry" );
		pieTop[BLUEBERRY] = register.registerIcon( "SCBlockPieRawTop_blueberry" );
	}
	
	@Override
	public Icon getIcon(int side, int meta)
	{
		if (side == 1)
		{
			return pieTop[meta];
		}
		
		return blockIcon;
	}
	
    @Override
    public void RenderBlockSecondPass( RenderBlocks renderer, int x, int y, int z, boolean firstPassResult )
    {
        RenderCookingByKilnOverlay( renderer, x, y, z, firstPassResult );
    }
}
