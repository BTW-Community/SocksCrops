// FCMOD

package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

public class SCCraftingManagerJuicer extends FCCraftingManagerBulk
{
    
	private ArrayList<SCCraftingManagerJuicerRecipes> recipes = new ArrayList();
	
	private static final SCCraftingManagerJuicer instance = new SCCraftingManagerJuicer();
    
    public static final SCCraftingManagerJuicer getInstance()
    {
        return instance;
    }

    protected SCCraftingManagerJuicer()
    {
    	super();    	
    }

    public void AddRecipe( ItemStack outputStacks[], ItemStack inputStacks[], boolean bMetadataExclusive, int timesMilled )
    {
    	SCCraftingManagerJuicerRecipes recipe = CreateRecipe( outputStacks, inputStacks, bMetadataExclusive, timesMilled );        
    	
        recipes.add( recipe );
    }
    
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStacks[], boolean bMetadataExclusive, int timesMilled )
    {
        ItemStack outputStacks[] = new ItemStack[1];
        
        outputStacks[0] = outputStack.copy();
        
        AddRecipe( outputStacks, inputStacks, bMetadataExclusive, timesMilled );        
    }
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStack, boolean bMetadataExclusive, int timesMilled )
    {
        ItemStack outputStacks[] = new ItemStack[1];
        ItemStack inputStacks[] = new ItemStack[1];
        
        outputStacks[0] = outputStack.copy();
        inputStacks[0] = inputStack.copy();
        
        AddRecipe( outputStacks, inputStacks, bMetadataExclusive, timesMilled );        
    }
    
    private SCCraftingManagerJuicerRecipes CreateRecipe( ItemStack outputStacks[], ItemStack inputStacks[], boolean bMetadataExclusive, int timesMilled )
    {
        ArrayList<ItemStack> inputArrayList = new ArrayList<ItemStack>();
        
        int iInputStacksArrayLength = inputStacks.length;
        
        for ( int iTempIndex = 0; iTempIndex < iInputStacksArrayLength; iTempIndex++ )
        {
        	inputArrayList.add( inputStacks[iTempIndex].copy() );            
        }

        ArrayList<ItemStack> outputArrayList = new ArrayList<ItemStack>();
        
        int iOutputStacksArrayLength = outputStacks.length;
        
        for ( int iTempIndex = 0; iTempIndex < iOutputStacksArrayLength; iTempIndex++ )
        {
        	outputArrayList.add( outputStacks[iTempIndex].copy() );            
        }
        
        return new SCCraftingManagerJuicerRecipes( outputArrayList, inputArrayList, bMetadataExclusive, timesMilled );
    }
    
    private SCCraftingManagerJuicerRecipes CreateRecipe( ItemStack outputStacks[], ItemStack inputStacks[], boolean bMetadataExclusive )
    {
        ArrayList<ItemStack> inputArrayList = new ArrayList<ItemStack>();
        
        int iInputStacksArrayLength = inputStacks.length;
        
        for ( int iTempIndex = 0; iTempIndex < iInputStacksArrayLength; iTempIndex++ )
        {
        	inputArrayList.add( inputStacks[iTempIndex].copy() );            
        }

        ArrayList<ItemStack> outputArrayList = new ArrayList<ItemStack>();
        
        int iOutputStacksArrayLength = outputStacks.length;
        
        for ( int iTempIndex = 0; iTempIndex < iOutputStacksArrayLength; iTempIndex++ )
        {
        	outputArrayList.add( outputStacks[iTempIndex].copy() );            
        }
        
        return new SCCraftingManagerJuicerRecipes( outputArrayList, inputArrayList, bMetadataExclusive );
    }
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStacks[] )
    {
    	AddRecipe( outputStack, inputStacks, false );
    }
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStack )
    {
    	AddRecipe( outputStack, inputStack, false );
    }
    
    public void AddRecipe( ItemStack outputStacks[], ItemStack inputStacks[] )
    {
    	AddRecipe( outputStacks, inputStacks, false );
    }
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStacks[], boolean bMetadataExclusive )
    {
        ItemStack outputStacks[] = new ItemStack[1];
        
        outputStacks[0] = outputStack.copy();
        
        AddRecipe( outputStacks, inputStacks, bMetadataExclusive );        
    }
    
    public void AddRecipe( ItemStack outputStack, ItemStack inputStack, boolean bMetadataExclusive )
    {
        ItemStack outputStacks[] = new ItemStack[1];
        
        outputStacks[0] = outputStack.copy();
        
        ItemStack inputStacks[] = new ItemStack[1];
        
        inputStacks[0] = inputStack.copy();
        
        AddRecipe( outputStacks, inputStacks, bMetadataExclusive );        
    }
    
    public void AddRecipe( ItemStack outputStacks[], ItemStack inputStacks[], boolean bMetadataExclusive )
    {
    	SCCraftingManagerJuicerRecipes recipe = CreateRecipe( outputStacks, inputStacks, bMetadataExclusive );        
    	
    	recipes.add( recipe );
    }
    
    public int GetTimesMilled( IInventory inventory )
    {
        for(int i = 0; i < recipes.size(); i++)
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if( tempRecipe.DoesInventoryContainIngredients( inventory ) )
            {
                return tempRecipe.getTimesMilled();
            }
        }
        
    	return 1;
    }
    
    public List<ItemStack> GetCraftingResult( IInventory inventory )
    {
        for(int i = 0; i < recipes.size(); i++)
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if( tempRecipe.DoesInventoryContainIngredients( inventory ) )
            {
                return tempRecipe.getCraftingOutputList();
            }
        }
        
    	return null;
    }
    
    public List<ItemStack> GetCraftingResult( ItemStack inputStack )
    {
        for(int i = 0; i < recipes.size(); i++)
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if ( tempRecipe.DoesStackSatisfyIngredients( inputStack ) )
            {
                return tempRecipe.getCraftingOutputList();
            }
        }
        
    	return null;
    }
    
    public List<ItemStack> GetValidCraftingIngrediants( IInventory inventory )
    {
        for(int i = 0; i < recipes.size(); i++)
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if( tempRecipe.DoesInventoryContainIngredients( inventory ) )
            {
                return tempRecipe.getCraftingIngrediantList();
            }
        }
        
    	return null;
    }
        
    /**
     * Checks if any recipe is satisfied by the single input stack, and returns the required
     * ingredient stack if it does (null otherwise)
     */
    public ItemStack GetValidSingleIngredient( ItemStack inputStack )
    {    	
        for ( int i = 0; i < recipes.size(); i++ )
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if ( tempRecipe.DoesStackSatisfyIngredients( inputStack ) )
            {
                return tempRecipe.GetFirstIngredient();
            }
        }
        
    	return null;
    }
    
    public boolean HasRecipeForSingleIngredient( ItemStack inputStack )
    {
    	return GetValidSingleIngredient( inputStack ) != null;
    }
    
    public List<ItemStack> ConsumeIngrediantsAndReturnResult( IInventory inventory )
    {
        for(int i = 0; i < recipes.size(); i++)
        {
        	SCCraftingManagerJuicerRecipes tempRecipe = recipes.get( i );
        	
            if( tempRecipe.DoesInventoryContainIngredients( inventory ) )
            {
            	tempRecipe.ConsumeInventoryIngrediants( inventory );
            	
                return tempRecipe.getCraftingOutputList();
            }
        }
        
    	return null;
    }
}