// FCMOD

package net.minecraft.src;

import java.util.List;
import java.util.ArrayList;

public class SCCraftingManagerMixerRecipe
{
    private final List<ItemStack> m_recipeOutputStacks;
    private final List<ItemStack> m_recipeInputStacks;
    
    private final int i_liquidType;
    
    public SCCraftingManagerMixerRecipe( ItemStack recipeOutputStack, List<ItemStack> recipeInputStacks )
    {
    	this( recipeOutputStack, recipeInputStacks, 0 );
    }

    public SCCraftingManagerMixerRecipe( ItemStack recipeOutputStack, List<ItemStack> recipeInputStacks, int liquidType )
    {
        ArrayList<ItemStack> outputArrayList = new ArrayList<ItemStack>();
        
        outputArrayList.add( recipeOutputStack.copy() );
        
    	m_recipeOutputStacks = outputArrayList;
    	m_recipeInputStacks = recipeInputStacks;
    	i_liquidType = liquidType;
    }

    public SCCraftingManagerMixerRecipe( List<ItemStack> recipeOutputStacks, List<ItemStack> recipeInputStacks, int liquidType )
    {
    	m_recipeOutputStacks = recipeOutputStacks;
    	m_recipeInputStacks = recipeInputStacks;
    	i_liquidType = liquidType;
    }
    
    public int getLiquidType() {
		return i_liquidType;
	}
 
    public List<ItemStack> getCraftingOutputList()
    {
    	return m_recipeOutputStacks;
    }
    
    public List<ItemStack> getCraftingIngrediantList()
    {
    	return m_recipeInputStacks;
    }
    
    public ItemStack GetFirstIngredient()
    {
    	if ( m_recipeInputStacks != null && m_recipeInputStacks.size() > 0 )
    	{
    		return m_recipeInputStacks.get( 0 );
    	}
    	
    	return null;
    }
    
    public boolean DoesInventoryContainIngredients( IInventory inventory )
    {    	
    	if ( m_recipeInputStacks != null && m_recipeInputStacks.size() > 0 )
    	{
            for ( int listIndex = 0; listIndex < m_recipeInputStacks.size(); listIndex++ )
            {
	    		ItemStack tempStack = (ItemStack)m_recipeInputStacks.get( listIndex );
	    		
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
    	if ( m_recipeInputStacks != null && m_recipeInputStacks.size() == 1 )
    	{
    		ItemStack recipeStack = (ItemStack)m_recipeInputStacks.get( 0 );
    		int recipeItemDamage = recipeStack.getItemDamage();
    		
    		if ( stack.itemID == recipeStack.itemID && stack.stackSize >= recipeStack.stackSize &&
        		( recipeItemDamage == FCUtilsInventory.m_iIgnoreMetadata ) )
			{
    			return true;
			}
    	}
    	
    	return false;
    }
    
    public boolean ConsumeInventoryIngrediants( IInventory inventory )
    {
    	boolean bSuccessful = true;

    	if ( m_recipeInputStacks != null && m_recipeInputStacks.size() > 0 )
    	{
            for ( int listIndex = 0; listIndex < m_recipeInputStacks.size(); listIndex++ )
            {
	    		ItemStack tempStack = (ItemStack)m_recipeInputStacks.get( listIndex );
	    		
	    		if ( tempStack != null )
	    		{
	    			if ( !FCUtilsInventory.ConsumeItemsInInventory( inventory, 
    					tempStack.getItem().itemID, tempStack.getItemDamage(), 
    					tempStack.stackSize, false ) )
					{
	    				bSuccessful = false;
					}
	    		}
            }
    	}
    	
    	return bSuccessful;
    }
    
    public boolean matches( SCCraftingManagerMixerRecipe recipe )
    {
    	if ( i_liquidType == recipe.i_liquidType && 
    		m_recipeInputStacks.size() == recipe.m_recipeInputStacks.size() && 
    		m_recipeOutputStacks.size() == recipe.m_recipeOutputStacks.size() )
    	{
    		for ( int iListIndex = 0; iListIndex < m_recipeInputStacks.size(); iListIndex++ )
    		{
    			if ( !DoStacksMatch( m_recipeInputStacks.get( iListIndex ), recipe.m_recipeInputStacks.get( iListIndex ) ) )
    			{
    				return false;
    			}
    		}
    		
    		for ( int iListIndex = 0; iListIndex < m_recipeOutputStacks.size(); iListIndex++ )
    		{
    			if ( !DoStacksMatch( m_recipeOutputStacks.get( iListIndex ), recipe.m_recipeOutputStacks.get( iListIndex ) ) )
    			{
    				return false;
    			}
    		}
    		
    		return true;    		
    	}
    	
    	return false;
    }
    
    private boolean DoStacksMatch( ItemStack stack1, ItemStack stack2 )
    {
    	return ( stack1.getItem().itemID == stack2.getItem().itemID &&
    		stack1.stackSize == stack2.stackSize &&
    		stack1.getItemDamage() == stack2.getItemDamage() );
    }    
}
