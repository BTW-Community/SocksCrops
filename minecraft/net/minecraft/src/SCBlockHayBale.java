package net.minecraft.src;

public class SCBlockHayBale extends SCBlockStrawBale {

	public SCBlockHayBale(int id, String name) {
		super(id, name);
	}
	
	static Icon topIcon;
	
	@Override public void registerIcons(IconRegister r)
	{
		blockIcon = r.registerIcon("SCBlockHayBale_side");
		topIcon = r.registerIcon("SCBlockHayBale_top");
	}

	@Override public Icon getIcon(int side, int meta)
	{
		switch(meta)
		{
			case 0:
				return (side<2)?topIcon:blockIcon;
			case 1:
				return (side<4&&side>1)?topIcon:blockIcon;
			default:
				return (side>3)?topIcon:blockIcon;
		}
	}

}
