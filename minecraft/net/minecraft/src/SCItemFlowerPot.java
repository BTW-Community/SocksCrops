package net.minecraft.src;

public class SCItemFlowerPot extends FCItemPlacesAsBlock {
	public SCItemFlowerPot(int id) {
		super(id, SCDefs.flowerPot.blockID);
        this.setUnlocalizedName("SCItemPot_flower");
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.SetFilterableProperties(1);
        this.SetBuoyant();
	}
}
