package net.minecraft.src;

public class SCCraftingManagerBurgerRecipes 
{
	private final ItemStack outputStack;
	private final ItemStack heldStack;
	private final ItemStack stackOnBoard;
	private final boolean eject;
	
	public SCCraftingManagerBurgerRecipes(ItemStack outputStack, ItemStack heldStack, ItemStack onBoardStack, boolean eject) {
		this.outputStack = outputStack;
		this.heldStack = heldStack;
		this.stackOnBoard = onBoardStack;
		this.eject = eject;
	}
	
	public boolean matchesRecipe(SCCraftingManagerBurgerRecipes recipe) {
		return this.outputStack.equals(recipe.outputStack) &&
				this.heldStack.isItemEqual(recipe.heldStack) &&
				this.stackOnBoard.isItemEqual(recipe.stackOnBoard);
	}
	
	public boolean matchesRecipe(ItemStack stackOnBoard) {
			return this.stackOnBoard.isItemEqual(stackOnBoard );
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
	
	public boolean matchesInputs(ItemStack inputToCheck) {
		return this.stackOnBoard.isItemEqual(inputToCheck);
	}

	public ItemStack getBoardOutput() {
		return outputStack;
	}

	public ItemStack getInput() {
		return heldStack;
	}

	public ItemStack getStackOnBoard() {
		return stackOnBoard;
	}
	
	public boolean getEjects()
	{
		return eject;
	}
}
