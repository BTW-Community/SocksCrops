package net.minecraft.src;

public class SocksCropsRecipes {
	public static void addRecipes() {
		addAllRecipes();
		addCrockPotRecipes();
		
	}

	private static void addCrockPotRecipes() {
		AddCrockPotRecipe(new ItemStack(FCBetterThanWolves.fcItemChickenSoup, 3), new ItemStack[] {new ItemStack(FCBetterThanWolves.fcItemBoiledPotato), new ItemStack(FCBetterThanWolves.fcItemCookedCarrot), new ItemStack(Item.chickenCooked), new ItemStack(Item.bowlEmpty, 3)});
		
	}

	private static void addAllRecipes() {
		FCRecipes.AddShapelessRecipe(new ItemStack(SocksCropsDefs.knifeStone), new Object[] {Item.stick, FCBetterThanWolves.fcItemChiselStone, Item.silk});
		FCRecipes.AddShapelessRecipe(new ItemStack(SocksCropsDefs.knifeStone), new Object[] {Item.stick, FCBetterThanWolves.fcItemChiselStone, FCBetterThanWolves.fcItemHempFibers});
	}
	
	public static void AddCrockPotRecipe(ItemStack var0, ItemStack[] var1)
    {
        SocksCropsCraftingManagerCrockPot.getInstance().AddRecipe(var0, var1);
    }
}
