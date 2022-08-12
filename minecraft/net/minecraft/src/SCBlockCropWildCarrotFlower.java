package net.minecraft.src;

import java.util.Random;

public class SCBlockCropWildCarrotFlower extends SCBlockCropWildCarrot {

	public SCBlockCropWildCarrotFlower(int iBlockID) {
		super(iBlockID);
		setUnlocalizedName("SCBlockCropWildCarrotFlower");
	}
	
	@Override
	protected int GetCropItemID() {
		return SCDefs.wildCarrot.itemID;
	}

	@Override
	protected int GetSeedItemID() {
		return SCDefs.wildCarrotSeeds.itemID;
	}
	
    @Override
    public void updateTick( World world, int i, int j, int k, Random rand )
    {
    	
    }
    
  //----------- Client Side Functionality -----------//
	
  	private Icon icon;
  	
  	public Icon getIcon(int par1, int par2)
    {
  		return icon;
    }
  	
  	public void registerIcons(IconRegister par1IconRegister)
    {
  		 this.icon = par1IconRegister.registerIcon("SCBlockCropWildCarrot_4");
    }

}
