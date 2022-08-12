package net.minecraft.src;

public class SCCraftingManagerChoppingBoardFilterRecipe {
	private final ItemStack[] outputStacks;
	private final ItemStack heldStack;
	private final ItemStack stackOnBoard;
	
	public SCCraftingManagerChoppingBoardFilterRecipe(ItemStack[] outputStacks, ItemStack heldStack, ItemStack onBoardStack) {
		this.outputStacks = outputStacks;
		this.heldStack = heldStack;
		this.stackOnBoard = onBoardStack;
	}
	
	public boolean matchesRecipe(SCCraftingManagerChoppingBoardFilterRecipe recipe) {
		return this.outputStacks.equals(recipe.outputStacks) &&
				this.heldStack.isItemEqual(recipe.heldStack) &&
				this.stackOnBoard.isItemEqual(recipe.stackOnBoard );
	}
	
	public boolean matchesInputs(ItemStack heldStack, ItemStack stackOnBoard) {
		return this.heldStack.isItemEqual(heldStack) &&
				this.stackOnBoard.isItemEqual(stackOnBoard);
	}
	
	public boolean matchesInputs2(ItemStack heldStack, ItemStack stackOnBoard) {
		return this.heldStack.itemID == heldStack.itemID &&
				this.stackOnBoard.isItemEqual(stackOnBoard);
	}
	
	public boolean matchesInputs3( ItemStack stackOnBoard) {
		return this.stackOnBoard.isItemEqual(stackOnBoard);
	}

	public ItemStack[] getBoardOutput() {
		return outputStacks;
	}

	public ItemStack getInput() {
		return heldStack;
	}

	public ItemStack getStackOnBoard() {
		return stackOnBoard;
	}
}
