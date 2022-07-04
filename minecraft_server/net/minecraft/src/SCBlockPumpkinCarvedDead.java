package net.minecraft.src;

import java.util.Random;

public class SCBlockPumpkinCarvedDead extends SCBlockPumpkinCarved {

	protected SCBlockPumpkinCarvedDead(int iBlockID) {
		super(iBlockID);
		setCreativeTab(null);
	}
	
	@Override
	public int idDropped(int iMetadata, Random rand, int iFortuneModifier) {
		
		return SCDefs.pumpkinCarved.blockID;
	}
	
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int meta)
    {
		//Orange
		if (meta <= 3){
			return 3;
		}
		//Green
		else if (meta <= 7){
			return 7;
		}
		//Yellow
		else if (meta <= 11){
			return 11;
		}
		//White
		else return 15;
    }
	
	
}
