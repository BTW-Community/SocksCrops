package net.minecraft.src;

import java.util.Random;

public class SCBlockPieRaw extends SCBlockPieBase {

	public static int subtypeSweetberry = 0;
	public static int subtypeBlueberry = 1;
	
	protected SCBlockPieRaw(int blockID)
	{
		super(blockID);
	}
	
	@Override
	public int idDropped( int meta, Random random, int fortuneModifier)
	{
		if (meta == subtypeSweetberry) return SCDefs.sweetberryPieRaw.itemID;
		else if (meta == subtypeBlueberry) return SCDefs.blueberryPieRaw.itemID;
		else return 0;
	}
	
	@Override
	public int idPicked(World world, int i, int j, int k) {

		return idDropped(world.getBlockMetadata(i, j, k), world.rand, 0);
	}
	
	private Icon rawPastry;
	private Icon[] pieTop = new Icon[16];
	
	@Override
	public void registerIcons(IconRegister register)
	{
		blockIcon = rawPastry = register.registerIcon( "fcBlockPastryUncooked" );
		pieTop[0] = register.registerIcon( "SCBlockPieRawTop_sweetberry" );
		pieTop[1] = register.registerIcon( "SCBlockPieRawTop_blueberry" );
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
    public void RenderBlockSecondPass( RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult )
    {
        RenderCookingByKilnOverlay( renderBlocks, i, j, k, bFirstPassResult );
    }

}
