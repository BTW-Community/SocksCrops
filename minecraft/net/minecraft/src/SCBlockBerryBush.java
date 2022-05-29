package net.minecraft.src;

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
	
	private Icon[] bushIcon = new Icon[6];
	
    @Override
    public void registerIcons( IconRegister register )
    {
    	for (int i = 0; i < bushIcon.length; i++) {
    		bushIcon[i] = register.registerIcon( textureName + "_" + i );
		}
    }
    
    public Icon getIcon(int side, int meta)
    {
    	return bushIcon[meta];
    }

}
