package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCCraftingManagerJuicerRecipes extends FCCraftingManagerBulkRecipe {
	
	private final List<ItemStack> outputStacks;
	private final List<ItemStack> inputStacks;
	private final int timesMilled;
	private final boolean metadataExclusive;
	
	
	public SCCraftingManagerJuicerRecipes(List<ItemStack> outputStacks, List<ItemStack> inputStacks, boolean metadataExclusive, int timesMilled) {
		super(outputStacks, inputStacks, metadataExclusive);

		this.timesMilled = timesMilled;		
		this.outputStacks = outputStacks;
        this.inputStacks = inputStacks;
        this.metadataExclusive = metadataExclusive;
		
	}
	
	public SCCraftingManagerJuicerRecipes(List<ItemStack> outputStacks, List<ItemStack> inputStacks, boolean metadataExclusive) {
		this(outputStacks, inputStacks, metadataExclusive, 1);
	}
	
	public boolean matchesRecipe(SCCraftingManagerJuicerRecipes recipe) {
		return this.outputStacks.equals(recipe.outputStacks) &&
				this.inputStacks.equals(recipe.inputStacks);
	}
	
	@Override
	public List<ItemStack> getCraftingOutputList()
    {
    	return outputStacks;
    }
	
	@Override
    public List<ItemStack> getCraftingIngrediantList()
    {
    	return inputStacks;
    }
	
	public int getTimesMilled()
    {
    	return timesMilled;
    }
	
	
	public boolean DoesInventoryContainIngredients( IInventory inventory )
    {    	
    	if ( inputStacks != null && inputStacks.size() > 0 )
    	{
            for ( int listIndex = 0; listIndex < inputStacks.size(); listIndex++ )
            {
	    		ItemStack tempStack = inputStacks.get(listIndex);
	    		
	    		if ( tempStack != null )
	    		{
	    			if ( FCUtilsInventory.CountItemsInInventory( inventory, 
    					tempStack.getItem().itemID, tempStack.getItemDamage(), false )
    					< tempStack.stackSize )
	    			{
	    				return false;
	    			}
	    		}
            }
            
            return true;
    	}
    	
    	return false;
    }
	
	public boolean DoesStackSatisfyIngredients( ItemStack stack )
    {
    	if ( inputStacks != null && inputStacks.size() == 1 )
    	{
    		ItemStack recipeStack = (ItemStack)inputStacks.get( 0 );
    		int recipeItemDamage = recipeStack.getItemDamage();
    		
    		if ( stack.itemID == recipeStack.itemID && stack.stackSize >= recipeStack.stackSize &&
        		( recipeItemDamage == FCUtilsInventory.m_iIgnoreMetadata || 
				( !metadataExclusive && stack.getItemDamage() == recipeItemDamage ) || 
				( metadataExclusive && stack.getItemDamage() != recipeItemDamage ) ) )
			{
    			return true;
			}
    	}
    	
    	return false;
    }
	
	 public boolean ConsumeInventoryIngrediants( IInventory inventory )
	    {
	    	boolean bSuccessful = true;

	    	if ( inputStacks != null && inputStacks.size() > 0 )
	    	{
	            for ( int listIndex = 0; listIndex < inputStacks.size(); listIndex++ )
	            {
		    		ItemStack tempStack = (ItemStack)inputStacks.get( listIndex );
		    		
		    		if ( tempStack != null )
		    		{
		    			if ( !FCUtilsInventory.ConsumeItemsInInventory( inventory, 
	    					tempStack.getItem().itemID, tempStack.getItemDamage(), 
	    					tempStack.stackSize, metadataExclusive ) )
						{
		    				bSuccessful = false;
						}
		    		}
	            }
	    	}
	    	
	    	return bSuccessful;
	    }
	
}
