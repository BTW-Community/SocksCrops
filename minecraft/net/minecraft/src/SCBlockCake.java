package net.minecraft.src;

import java.util.Random;

public class SCBlockCake extends SCBlockCakeBase {

	protected SCBlockCake(int blockID) {
		super(blockID);
	}
	
	@Override
	protected int getCake() {
		return Item.cake.itemID;
	}
	
	@Override
	public int idPicked(World world, int x, int y, int z) {
		return Item.cake.itemID;
	}
	
	@Override
	protected int getSliceItem() {
		return SCDefs.cakeSlice.itemID;
	}
	
    public void registerIcons(IconRegister register)
    {
        this.blockIcon = register.registerIcon("cake_side");
        this.cakeInsideIcon = register.registerIcon("cake_inner");
        this.cakeTopIcon = register.registerIcon("cake_top");
        this.cakeBottomIcon = register.registerIcon("cake_bottom");
    }




}
