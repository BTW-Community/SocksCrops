package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockFruitTreeLogStack extends SCBlockFruitTreeLogStackBase {

	protected SCBlockFruitTreeLogStack(int id, String type) {
		super(id, type);
		
		setUnlocalizedName("SCBlockFruitLogStack");
	}
	
	private Icon[] topIcon = new Icon[4];
	private Icon[] sideIcon = new Icon[4];
	private Icon[] frontIcon = new Icon[4];
	
	private Icon[][] icons = {topIcon, sideIcon, frontIcon};

	@Override
	public void registerIcons(IconRegister register) {
		
		for (int i = 0; i < 4; i++)
		{
			if (type == "apple")
			{
				topIcon[i] = register.registerIcon("SCBlockFruitTreeLogTop_apple_" + i);
				sideIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide_apple_" + i);
				frontIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide2_apple_" + i);
			}
			else if (type== "lemon")
			{
				topIcon[i] = register.registerIcon("SCBlockFruitTreeLogTop_lemon_" + i);
				sideIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide_lemon_" + i);
				frontIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide2_lemon_" + i);
			}
			
			if (type == "cherry")
			{
				topIcon[i] = register.registerIcon("SCBlockFruitTreeLogTop_cherry_" + i);
				sideIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide_cherry_" + i);
				frontIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide2_cherry_" + i);
			}
			else if (type == "olive")
			{
				topIcon[i] = register.registerIcon("SCBlockFruitTreeLogTop_olive_" + i);
				sideIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide_olive_" + i);
				frontIcon[i] = register.registerIcon("SCBlockFruitTreeLogSide2_olive_" + i);
			}
		}
	}
	
	@Override
	public Icon getIcon(int side, int meta) {
		
		int size = getLogsStored(meta);
		boolean isRotated = isRotated(meta);
		
		if (side < 2)
		{
			return topIcon[size];
		}
		else if (side < 4)
		{
			if (isRotated)
			{
				return frontIcon[size];
			}			
			else return sideIcon[size];
		}
		else
		{
			if (isRotated)
			{
				return sideIcon[size];
			}			
			else return frontIcon[size];
		}
		
	}



}
