package net.minecraft.src;

import java.util.List;
import java.util.Random;

public class SCBlockBerryBush extends SCBlockBushBase {
	
	private int berryID;
	private int saplingID;
	private String textureName;
	
	protected SCBlockBerryBush(int blockID, int berryID, int saplingID, String name)
	{
		super(blockID);
		this.textureName = name;
		this.berryID = berryID;
		this.saplingID = saplingID;
		setUnlocalizedName(name);
	}

	@Override
	protected int getBerryID()
	{
		return berryID;
	}

	@Override
	protected int getSaplingID()
	{
		return saplingID;
	}
	    
}
