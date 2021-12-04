package net.minecraft.src;

public class SCRecipes {
	public static final SCRecipes instance = new SCRecipes();

	private SCRecipes() {}
	
    //Piston Packing Recipes
    public static void addPistonPackingRecipe(Block output, ItemStack inputStack) {
    	addPistonPackingRecipe(output, 0, inputStack);
    }
    
    public static void addPistonPackingRecipe(Block output, int outputMetadata, ItemStack inputStack) {
    	addPistonPackingRecipe(output, outputMetadata, new ItemStack[] {inputStack});
    }
    
    public static void addPistonPackingRecipe(Block output, ItemStack[] inputStacks) {
    	addPistonPackingRecipe(output, 0, inputStacks);
    }
    
    public static void addPistonPackingRecipe(Block output, int outputMetadata, ItemStack[] inputStacks) {
    	FCCraftingManagerPistonPacking.instance.addRecipe(output, outputMetadata, inputStacks);
    }
    
    public static void addPistonPackingRecipe(Item output, ItemStack inputStack) {
    	addPistonPackingRecipe(output, 0, inputStack);
    }
    
    public static void addPistonPackingRecipe(Item output, int outputMetadata, ItemStack inputStack) {
    	addPistonPackingRecipe(output, outputMetadata, new ItemStack[] {inputStack});
    }
    
    public static void addPistonPackingRecipe(Item output, ItemStack[] inputStacks) {
    	addPistonPackingRecipe(output, 0, inputStacks);
    }
    
    public static void addPistonPackingRecipe(Item output, int outputMetadata, ItemStack[] inputStacks) {
    	FCCraftingManagerPistonPacking.instance.addRecipe(output, outputMetadata, inputStacks);
    }

    
	public static void addRecipes() {
		addChoppingBoardRecipes();
		
		addPistonPackingRecipe(Item.diamond,
				new ItemStack(Item.appleRed, 4));
	}

	private static void addChoppingBoardRecipes() {
		
		AddCuttingBoardRecipe( new ItemStack[] { new ItemStack( SCDefs.melonSeeds, 1), new ItemStack( SCDefs.melonSlice, 3 ) }, new ItemStack[] { new ItemStack( SCDefs.pumpkinHarvested, 1, 3 ) } );
	}
	
    public static void AddCuttingBoardRecipe( ItemStack outputStacks[], ItemStack inputStacks[] )
    {
    	SCCraftingManagerCuttingBoard.getInstance().AddRecipe( outputStacks, inputStacks );
	}
}
