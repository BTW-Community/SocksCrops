package net.minecraft.src;

public class SCCraftingManagerPanCookingRecipe {
	private final ItemStack output;
	private final ItemStack burnedOutput;
	private final ItemStack input;
	
	public SCCraftingManagerPanCookingRecipe(ItemStack hopperOutput, ItemStack burnedOutput, ItemStack input) {
		this.output = hopperOutput;
		this.burnedOutput = burnedOutput;
		this.input = input;
	}
	
	public boolean matchesRecipe(SCCraftingManagerPanCookingRecipe recipe) {
		return this.output.equals(recipe.output) &&
				this.burnedOutput.equals(recipe.burnedOutput) &&
				this.input.isItemEqual(recipe.input);
	}
	
	public boolean matchesInputs(ItemStack inputToCheck) {
		return this.input.isItemEqual(inputToCheck);
	}
	
	public boolean matchesBurnedOutput(ItemStack inputToCheck) {
		return this.burnedOutput.isItemEqual(inputToCheck);
	}

	public ItemStack getOutput() {
		return output;
	}

	public ItemStack getBurnedOutput() {
		return burnedOutput;
	}

	public ItemStack getInput() {
		return input;
	}

}
