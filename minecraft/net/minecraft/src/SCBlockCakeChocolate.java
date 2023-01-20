package net.minecraft.src;

import java.util.Random;

public class SCBlockCakeChocolate extends SCBlockCakeBase {

	protected SCBlockCakeChocolate(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return SCDefs.chocolateCakeSlice.itemID;
	}
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
		return SCDefs.chocolateCakeItem.itemID;
	}

	@Override
	protected int getSliceItem() {
		return SCDefs.chocolateCakeSlice.itemID;
	}
	
	@Override
	public void registerIcons(IconRegister register) {
		this.blockIcon = register.registerIcon("SCBlockCake_chocolate_side");
        this.cakeInsideIcon = register.registerIcon("SCBlockCake_chocolate_inside");
        this.cakeTopIcon = register.registerIcon("SCBlockCake_chocolate_top");
        this.cakeBottomIcon = register.registerIcon("SCBlockCake_chocolate_bottom");
	}


}
