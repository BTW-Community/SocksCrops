package net.minecraft.src;

import java.util.Random;

public class SCBlockCakeCarrot extends SCBlockCakeBase {

	protected SCBlockCakeCarrot(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return SCDefs.carrotCakeItem.itemID;
	}

	@Override
	public int idPicked(World world, int x, int y, int z) {
		return SCDefs.carrotCakeItem.itemID;
	}
	
	@Override
	protected int getSliceItem() {
		return SCDefs.carrotCakeSlice.itemID;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("SCBlockCake_carrot_side");
        this.cakeInsideIcon = register.registerIcon("SCBlockCake_carrot_inside");
        this.cakeTopIcon = register.registerIcon("SCBlockCake_carrot_top");
        this.cakeBottomIcon = register.registerIcon("SCBlockCake_carrot_bottom");
	}

}
