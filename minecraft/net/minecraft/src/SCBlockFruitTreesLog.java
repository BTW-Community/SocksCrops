package net.minecraft.src;

import java.util.Random;

public class SCBlockFruitTreesLog extends SCBlockFruitTreesLogBase {

	protected SCBlockFruitTreesLog(int iBlockID) {
		super(iBlockID);
		
		setUnlocalizedName( "SCBlockFruitLog" ); 
	}
	
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return SCDefs.fruitLog.blockID;
	}
	
    public int damageDropped(int meta)
    {
        return meta & 3;
    }
	

}
