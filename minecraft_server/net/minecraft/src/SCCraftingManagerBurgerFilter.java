package net.minecraft.src;

import java.util.ArrayList;

public class SCCraftingManagerBurgerFilter {
	public static SCCraftingManagerBurgerFilter instance = new SCCraftingManagerBurgerFilter();
	
	private ArrayList<SCCraftingManagerBurgerRecipes> recipes = new ArrayList();
	
	private SCCraftingManagerBurgerFilter() {}
	
	public void addRecipe(ItemStack outputStack, ItemStack heldStack, ItemStack onBoardStack, boolean ejects) {
		if (heldStack.stackSize != 1) {
			FCAddOnHandler.LogWarning("Cannot add hopper filtering recipe with input stack size > 1 for input " + heldStack.getItemName());
		}
		
		heldStack.stackSize = 1;
		onBoardStack.stackSize = 1;
		outputStack.stackSize = 1;

		
		recipes.add(new SCCraftingManagerBurgerRecipes(outputStack, heldStack, onBoardStack, ejects));
	}
		
	public boolean removeRecipe(ItemStack outputStack, ItemStack heldStack, ItemStack onBoardStack, boolean ejects) {
		SCCraftingManagerBurgerRecipes recipeToRemove = 
				new SCCraftingManagerBurgerRecipes(outputStack, heldStack, onBoardStack, ejects);
		
		for (SCCraftingManagerBurgerRecipes recipe : recipes) {
			if (recipe.matchesRecipe(recipeToRemove)) {
				recipes.remove(recipe);
				return true;
			}
		}
		
		return false;
	}
	
	public boolean hasRecipe(ItemStack onBoardStack) {
		for (SCCraftingManagerBurgerRecipes recipe : recipes) {
			if (recipe.matchesRecipe(onBoardStack)) {
				return true;
			}
		}
		
		return false;
	}
	
	public SCCraftingManagerBurgerRecipes getRecipe(ItemStack heldStack, ItemStack onBoardStack) {
		for (SCCraftingManagerBurgerRecipes recipe : recipes) {
			if (recipe.matchesInputs(heldStack, onBoardStack)) {
				return recipe;
			}
		}
		
		return null;
	}
}
