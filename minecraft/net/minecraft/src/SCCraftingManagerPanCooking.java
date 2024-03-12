package net.minecraft.src;

import java.util.ArrayList;

public class SCCraftingManagerPanCooking {
	public static SCCraftingManagerPanCooking instance = new SCCraftingManagerPanCooking();
	
	private ArrayList<SCCraftingManagerPanCookingRecipe> recipes = new ArrayList();
	
	private SCCraftingManagerPanCooking() {}
	
	public void addRecipe(ItemStack ouput, ItemStack burnedOutput, ItemStack input) {
		if (input.stackSize != 1) {
			FCAddOnHandler.LogWarning("Cannot add hopper filtering recipe with input stack size > 1 for input " + input.getItemName());
		}
		
		input.stackSize = 1;
		
		recipes.add(new SCCraftingManagerPanCookingRecipe(ouput, burnedOutput, input));
	}
	
	public boolean removeRecipe(ItemStack ouput, ItemStack burnedOutput, ItemStack input) {
		SCCraftingManagerPanCookingRecipe recipeToRemove = 
				new SCCraftingManagerPanCookingRecipe(ouput, burnedOutput, input);
		
		for (SCCraftingManagerPanCookingRecipe recipe : recipes) {
			if (recipe.matchesRecipe(recipeToRemove)) {
				recipes.remove(recipe);
				return true;
			}
		}
		
		return false;
	}
	
	public SCCraftingManagerPanCookingRecipe getRecipe(ItemStack input) {
		for (SCCraftingManagerPanCookingRecipe recipe : recipes) {
			if (recipe.matchesInputs(input)) {
				return recipe;
			}
		}
		
		return null;
	}
	
	public SCCraftingManagerPanCookingRecipe getBurnedOutput(ItemStack input) {
		for (SCCraftingManagerPanCookingRecipe recipe : recipes) {
			if (recipe.matchesBurnedOutput(input)) {
				return recipe;
			}
		}
		
		return null;
	}
}
