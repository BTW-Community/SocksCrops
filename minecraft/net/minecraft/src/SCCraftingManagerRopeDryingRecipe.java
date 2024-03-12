package net.minecraft.src;

public class SCCraftingManagerRopeDryingRecipe {
	private final ItemStack output;
	private final ItemStack input;
	
	public SCCraftingManagerRopeDryingRecipe(ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
	}
	
	public boolean matchesRecipe(SCCraftingManagerRopeDryingRecipe recipe) {
		return this.output.equals(recipe.output) &&
				this.input.isItemEqual(recipe.input);
	}
	
	public boolean matchesInputs(ItemStack inputToCheck) {
		return this.input.isItemEqual(inputToCheck);
	}

	public ItemStack getOutput() {
		return output;
	}

	public ItemStack getInput() {
		return input;
	}
}
