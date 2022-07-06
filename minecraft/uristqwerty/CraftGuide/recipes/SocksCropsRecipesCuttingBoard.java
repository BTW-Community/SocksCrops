package uristqwerty.CraftGuide.recipes;

import java.lang.reflect.Field;
import java.util.ArrayList;

import net.minecraft.src.SCDefs;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.FCCraftingManagerHopperFilter;
import net.minecraft.src.FCCraftingManagerHopperFilterRecipe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCCraftingManagerChoppingBoardFilter;
import net.minecraft.src.SCCraftingManagerChoppingBoardFilterRecipe;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;

public class SocksCropsRecipesCuttingBoard extends CraftGuideAPIObject implements RecipeProvider {
	private ItemStack choppingBoard = new ItemStack(SCDefs.choppingBoard);
	private ItemStack urn = new ItemStack(FCBetterThanWolves.fcItemUrn);
	private ItemStack soulUrn = new ItemStack(FCBetterThanWolves.fcItemSoulUrn);

	@Override
	public void generateRecipes(RecipeGenerator generator) {
		Slot[] slots = SocksCropsRecipes.createSlots(2, 2, 4);
		RecipeTemplate template = generator.createRecipeTemplate(slots, choppingBoard).setSize(3 * 18 + 6, 2 * 18 + 4);
		
		try {
			Field recipesField = SCCraftingManagerChoppingBoardFilter.class.getDeclaredField("recipes");
			recipesField.setAccessible(true);
			ArrayList<SCCraftingManagerChoppingBoardFilterRecipe> recipes = (ArrayList<SCCraftingManagerChoppingBoardFilterRecipe>) recipesField.get(SCCraftingManagerChoppingBoardFilter.instance);
			
			for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
				ItemStack[] crafting = new ItemStack[slots.length];
				
				crafting[0] = recipe.getInput();
				
				crafting[2] = recipe.getStackOnBoard();
				crafting[3] = choppingBoard;
				
				for (int i = 4; i < recipe.getBoardOutput().length; i++)
				{
					crafting[i] = recipe.getBoardOutput()[i - 4];
				}
				System.out.println("Adding CuttingBoard Recipe");				
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
