package uristqwerty.CraftGuide.recipes;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.src.SCDefs;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCCraftingManagerHopperFilter;
import net.minecraft.src.FCCraftingManagerHopperFilterRecipe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCCraftingManagerChoppingBoardFilterRecipe;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;

public class SocksCropsRecipesInWorld extends CraftGuideAPIObject implements RecipeProvider {
	private ItemStack[] knives = { new ItemStack(SCDefs.knifeStone), new ItemStack(SCDefs.knifeIron), new ItemStack(SCDefs.knifeDiamond) };
	
	@Override
	public void generateRecipes(RecipeGenerator generator) {
		Slot[] slots = SocksCropsRecipes.createSlots(1, 1, 4);
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(3 * 18 + 6, 2 * 18 + 4);
		
		try {
			Field recipesField = FCCraftingManagerHopperFilter.class.getDeclaredField("recipes");
			recipesField.setAccessible(true);
			ArrayList<SCCraftingManagerChoppingBoardFilterRecipe> recipes = (ArrayList<SCCraftingManagerChoppingBoardFilterRecipe>) recipesField.get(FCCraftingManagerHopperFilter.instance);
			
			
			for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
				ItemStack[] crafting = new ItemStack[slots.length];
				
				crafting[0] = recipe.getInput();
				
				crafting[2] = recipe.getStackOnBoard();
				crafting[3] = knives[0];
				
				for (int i = 4; i < recipe.getBoardOutput().length; i++)
				{
					crafting[i] = recipe.getBoardOutput()[i - 4];
				}
								
				generator.addRecipe(template, crafting);
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
