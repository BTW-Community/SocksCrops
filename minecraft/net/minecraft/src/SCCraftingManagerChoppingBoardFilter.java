package net.minecraft.src;

import java.util.ArrayList;

public class SCCraftingManagerChoppingBoardFilter {
	public static SCCraftingManagerChoppingBoardFilter instance = new SCCraftingManagerChoppingBoardFilter();
	
	private ArrayList<SCCraftingManagerChoppingBoardFilterRecipe> recipes = new ArrayList();
	
	private SCCraftingManagerChoppingBoardFilter() {}
	
	public void addRecipe(ItemStack[] outputStacks, ItemStack heldStack, ItemStack onBoardStack) {
		if (heldStack.stackSize != 1) {
			FCAddOnHandler.LogWarning("Cannot add hopper filtering recipe with input stack size > 1 for input " + heldStack.getItemName());
		}
		
		heldStack.stackSize = 1;
		onBoardStack.stackSize = 1;
		
		recipes.add(new SCCraftingManagerChoppingBoardFilterRecipe(outputStacks, heldStack, onBoardStack));
	}
		
	public boolean removeRecipe(ItemStack[] outputStacks, ItemStack heldStack, ItemStack onBoardStack) {
		SCCraftingManagerChoppingBoardFilterRecipe recipeToRemove = 
				new SCCraftingManagerChoppingBoardFilterRecipe(outputStacks, heldStack, onBoardStack);
		
		for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
			if (recipe.matchesRecipe(recipeToRemove)) {
				recipes.remove(recipe);
				return true;
			}
		}
		
		return false;
	}
	
	public SCCraftingManagerChoppingBoardFilterRecipe getRecipe(ItemStack heldStack, ItemStack onBoardStack) {
		for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
			if (recipe.matchesInputs2(heldStack, onBoardStack)) {
				return recipe;
			}
		}
		
		return null;
	}
	
	public SCCraftingManagerChoppingBoardFilterRecipe getRecipe2(ItemStack onBoardStack) {
		for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
			if (recipe.matchesInputs3(onBoardStack)) {
				return recipe;
			}
		}
		
		return null;
	}
}
