package net.minecraft.src;

public class SocksCropsRecipes {
	public static void addRecipes() {
		addAllRecipes();
		
	}

	private static void addAllRecipes() {
		FCRecipes.AddShapelessRecipe(new ItemStack(SocksCropsDefs.knifeStone), new Object[] {Item.stick, FCBetterThanWolves.fcItemChiselStone, Item.silk});
		FCRecipes.AddShapelessRecipe(new ItemStack(SocksCropsDefs.knifeStone), new Object[] {Item.stick, FCBetterThanWolves.fcItemChiselStone, FCBetterThanWolves.fcItemHempFibers});
	}
}
