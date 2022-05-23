package net.minecraft.src;

public class SCCraftingRecipeKnifeCutting implements IRecipe {
	private ItemStack output;
	private ItemStack[] secondaryOutputs;
	
	private ItemStack outputLowQuality;
	private ItemStack[] secondaryOutputsLowQuality;
	
	private ItemStack input;
	
	private boolean hasLowQualityOutput = false;
	
	public SCCraftingRecipeKnifeCutting(ItemStack output, ItemStack[] secondaryOutputs, ItemStack input) {
		this.output = output;
		this.secondaryOutputs = secondaryOutputs;
		this.input = input;
	}

	/**
	 * Low quality output used for stone axes
	 */
	public SCCraftingRecipeKnifeCutting(ItemStack output, ItemStack[] secondaryOutputs, ItemStack outputLowQuality, ItemStack[] secondaryOutputsLowQuality, ItemStack input) {
		this(output, secondaryOutputs, input);
		
		this.outputLowQuality = outputLowQuality;
		this.secondaryOutputsLowQuality = secondaryOutputsLowQuality;
		
		this.hasLowQualityOutput = true;
	}
	
	public SCCraftingRecipeKnifeCutting(ItemStack output, ItemStack input) {
		this.output = output;
		this.input = input;
	}

	/**
	 * Low quality output used for stone axes
	 */
	public SCCraftingRecipeKnifeCutting(ItemStack output, ItemStack outputLowQuality, ItemStack input) {
		this(output, input);
		
		this.outputLowQuality = outputLowQuality;
		
		this.hasLowQualityOutput = true;
	}
	
	@Override
	public boolean matches(InventoryCrafting inventory, World world) {
		ItemStack knifeStack = null;
		ItemStack inputStack = null;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack != null) {
				if (isKnife(stack)) {
					if (knifeStack == null) {
						knifeStack = stack;
					}
					else {
						return false;
					}
				}
				else if (stack.itemID == this.input.itemID && 
						(stack.getItemDamage() == this.input.getItemDamage() || this.input.getItemDamage() == FCUtilsInventory.m_iIgnoreMetadata)) 
				{
					if (inputStack == null) {
						inputStack = stack;
					}
					else {
						return false;
					}
				}
				else {
					return false;
				}
			}
		}

		return knifeStack != null && inputStack != null;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inventory) {
		ItemStack knifeStack = null;
		ItemStack inputStack = null;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack != null) {
				if (isKnife(stack)) {
					if (knifeStack == null) {
						knifeStack = stack;
					}
					else {
						return null;
					}
				}
				else if (stack.itemID == this.input.itemID && 
						(stack.getItemDamage() == this.input.getItemDamage() || this.input.getItemDamage() == FCUtilsInventory.m_iIgnoreMetadata)) 
				{
					if (inputStack == null) {
						inputStack = stack;                        
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
			}
		}

		if (inputStack != null && knifeStack != null) {
			ItemStack resultStack = null;
			SCItemKnife knifeItem = (SCItemKnife)knifeStack.getItem();

			if (this.hasLowQualityOutput && isLowQualityKnife(knifeStack)) { // wood, stone & gold
				resultStack = this.outputLowQuality.copy();
			}
			else {
				resultStack = this.output.copy();
			}

			return resultStack;
		}

		return null;
	}

	@Override
	public boolean matches(IRecipe recipe) {
		if (recipe instanceof SCCraftingRecipeKnifeCutting) {
			SCCraftingRecipeKnifeCutting logRecipe = (SCCraftingRecipeKnifeCutting) recipe;
			
			if (logRecipe.input.isItemEqual(this.input)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean HasSecondaryOutput() {
		return this.secondaryOutputs != null;
	}

	@Override
	public ItemStack[] getSecondaryOutput(IInventory inventory) {
		ItemStack knifeStack = null;
		ItemStack inputStack = null;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if (stack != null) {
				if (isKnife(stack)) {
					if (knifeStack == null) {
						knifeStack = stack;
					}
					else {
						return null;
					}
				}
				else if (stack.itemID == this.input.itemID) {
					if (inputStack == null) {
						inputStack = stack;                        
					}
					else {
						return null;
					}
				}
				else {
					return null;
				}
			}
		}

		if (inputStack != null && knifeStack != null) {
			ItemStack[] resultStacks = null;
			SCItemKnife knifeItem = (SCItemKnife)knifeStack.getItem();

			if (this.hasLowQualityOutput && isLowQualityKnife(knifeStack)) { // wood, stone & gold
				resultStacks = this.secondaryOutputsLowQuality;
			}
			else {
				resultStacks = this.secondaryOutputs;
			}

			return resultStacks;
		}

		return null;
	}

	@Override
	public int getRecipeSize() {
		return 2;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return this.output;
	}
	
	public ItemStack[] getSecondaryOutput() {
		return this.secondaryOutputs;
	}
	
	public ItemStack getRecipeOutputLowQuality() {
		return this.outputLowQuality;
	}
	
	public ItemStack[] getSecondaryOutputLowQuality() {
		return this.secondaryOutputsLowQuality;
	}
	
	public boolean getHasLowQualityOutputs() {
		return this.hasLowQualityOutput;
	}
	
	public ItemStack getInput() {
		return this.input;
	}

	//------------- Class Specific Methods ------------//

	private boolean isKnife(ItemStack stack) {
		return stack.getItem() instanceof SCItemKnife;
	}
	
	private boolean isLowQualityKnife(ItemStack stack) {
		return isKnife(stack) && ((SCItemKnife) stack.getItem()).toolMaterial.getHarvestLevel() <= 1;
	}
}