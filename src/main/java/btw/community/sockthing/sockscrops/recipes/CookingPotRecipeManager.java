package btw.community.sockthing.sockscrops.recipes;

import btw.community.sockthing.sockscrops.block.tileentities.CookingPotTileEntity;
import btw.crafting.recipe.types.BulkRecipe;
import net.minecraft.src.IInventory;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CookingPotRecipeManager {
    public static CookingPotRecipeManager instance = new CookingPotRecipeManager();

    private static final List<CookingPotRecipe> recipes = new ArrayList<CookingPotRecipe>();

    public static void addRecipe(ItemStack[] ingredients, ItemStack liquid, ItemStack result) {
        recipes.add(new CookingPotRecipe(ingredients, liquid, result));
    }

    public static CookingPotRecipe findMatchingRecipe(ItemStack[] inputs, ItemStack liquid) {
        for (CookingPotRecipe recipe : recipes) {
            if (matches(recipe, inputs, liquid)) {
                return recipe;
            }
        }
        return null;
    }

    public static boolean isValidIngredients(ItemStack stack){
        if (stack == null) return false;
        for (CookingPotRecipe recipe : recipes) {
            for (ItemStack ingredient : recipe.getIngredients()){
                if (ingredient.isItemEqual(stack)) return true;
            }
        }
        return false;
    }

    private static boolean matchesStrict(CookingPotRecipe recipe, ItemStack[] inputs, ItemStack liquid) {
        // 1. Liquid check (type + amount)
        ItemStack req = recipe.getRequiredLiquid();
        if (liquid == null) return false;
        if (req == null) return false;
        if (liquid.itemID != req.itemID) return false;
        if (liquid.stackSize < req.stackSize) return false;

        // 2. Ingredient count must match
        int recipeCount = 0;
        for (ItemStack s : recipe.getIngredients()) {
            if (s != null) recipeCount++;
        }

        int inputCount = 0;
        for (ItemStack s : inputs) {
            if (s != null) inputCount++;
        }

        if (recipeCount != inputCount) return false;

        // 3. Every recipe ingredient must exist in inputs (unordered)
        List<Item> needed = new ArrayList<Item>();
        for (ItemStack stack : recipe.getIngredients()) {
            needed.add(stack.getItem());
        }

        for (ItemStack in : inputs) {
            if (in != null) {
                if (!needed.remove(in.getItem())) {
                    return false; // found an item not in recipe
                }
            }
        }

        return needed.isEmpty();
    }
    private static boolean doStacksMatch(ItemStack stack1, ItemStack stack2)
    {
        if (stack1 == null || stack2 == null) return false;

        return ( stack1.getItem().itemID == stack2.getItem().itemID &&
                stack1.stackSize == stack2.stackSize &&
                stack1.getItemDamage() == stack2.getItemDamage() );
    }
    private static boolean matches(CookingPotRecipe recipe, ItemStack[] inputs, ItemStack liquid) {

        for (int i = 0; i < inputs.length; i++) {
            if (!doStacksMatch(recipe.getIngredients()[i], inputs[i])) return false;
        }


        return liquid != null && recipe.getRequiredLiquid().isItemEqual(liquid);


//        // 1. Check liquid type + minimum amount
//        ItemStack required = recipe.getRequiredLiquid();
//        if (liquid == null) return false;
//        if (liquid.itemID != required.itemID) return false;
//        if (liquid.stackSize < required.stackSize) return false;
//
//        // 2. Check ingredients (unordered, one of each)
//        List<Item> needed = new ArrayList<Item>();
//        for (ItemStack stack : recipe.getIngredients()) {
//            needed.add(stack.getItem());
//        }
//
//        for (ItemStack in : inputs) {
//            if (in != null) {
//                needed.remove(in.getItem());
//            }
//        }
//
//        return needed.isEmpty();
    }

    public static List<CookingPotRecipe> getRecipes() {
        return recipes;
    }

    public ItemStack getCraftingResult(IInventory inventory)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            CookingPotRecipe tempRecipe = recipes.get(i);

            if( tempRecipe.doesInventoryContainIngredients(inventory))
            {
                if (tempRecipe.doesInventoryContainLiquidIngredients(inventory)){

                    return tempRecipe.getResult();
                }
            }
        }

        return null;
    }

    public static boolean isResult(ItemStack stack) {
        for (CookingPotRecipe recipe : recipes) {
            if (stack.isItemEqual(recipe.getResult())) {
                return true;
            }
        }
        return false;
    }

    public ItemStack consumeIngredientsAndReturnResult(IInventory inventory)
    {
        for(int i = 0; i < recipes.size(); i++)
        {
            CookingPotRecipe tempRecipe = recipes.get(i);

            if( tempRecipe.doesInventoryContainIngredients(inventory) )
            {
                tempRecipe.consumeInventoryIngredients(inventory);
                tempRecipe.consumeInventoryLiquidIngredients(inventory);

                return tempRecipe.getResult();
            }
        }

        return null;
    }
}
