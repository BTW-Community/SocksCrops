package net.minecraft.src;

public class SCRecipes {
	public static final SCRecipes instance = new SCRecipes();

	private SCRecipes() {}
	
	public static void addRecipes() {
		//addChoppingBoardRecipes();
	}

	private static void addChoppingBoardRecipes() {
		
		AddCuttingBoardRecipe( new ItemStack( Item.dyePowder, 1, 1 ), new ItemStack( Block.plantRed ) );
	}
	
	public static void AddCuttingBoardRecipe( ItemStack outputStack, ItemStack inputStack )
    {
		SCCraftingManagerCuttingBoard.instance.AddRecipe( outputStack, inputStack );
	}
}
