package net.minecraft.src;

import java.util.ArrayList;

public class SCCraftingManagerRopeDrying {
	public static SCCraftingManagerRopeDrying instance = new SCCraftingManagerRopeDrying();
	
	private ArrayList<SCCraftingManagerRopeDryingRecipe> recipes = new ArrayList();
	
	private SCCraftingManagerRopeDrying() {}
	
	public void addRecipe(ItemStack ouput, ItemStack input) {
		if (input.stackSize != 1) {
			FCAddOnHandler.LogWarning("Cannot add hopper filtering recipe with input stack size > 1 for input " + input.getItemName());
		}
		
		input.stackSize = 1;
		
		recipes.add(new SCCraftingManagerRopeDryingRecipe(ouput, input));
	}
	
	public boolean removeRecipe(ItemStack ouput, ItemStack input) {
		SCCraftingManagerRopeDryingRecipe recipeToRemove = 
				new SCCraftingManagerRopeDryingRecipe(ouput, input);
		
		for (SCCraftingManagerRopeDryingRecipe recipe : recipes) {
			if (recipe.matchesRecipe(recipeToRemove)) {
				recipes.remove(recipe);
				return true;
			}
		}
		
		return false;
	}
	
	public SCCraftingManagerRopeDryingRecipe getRecipe(ItemStack input) {
		for (SCCraftingManagerRopeDryingRecipe recipe : recipes) {
			if (recipe.matchesInputs(input)) {
				return recipe;
			}
		}
		
		return null;
	}

}
