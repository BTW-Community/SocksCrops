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
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class SocksCropsRecipesCuttingBoard extends CraftGuideAPIObject implements RecipeProvider {
	private ItemStack choppingBoard = new ItemStack(SCDefs.choppingBoard);
	private ItemStack urn = new ItemStack(FCBetterThanWolves.fcItemUrn);
	private ItemStack soulUrn = new ItemStack(FCBetterThanWolves.fcItemSoulUrn);

	@Override
	public void generateRecipes(RecipeGenerator generator) {
		Slot[] slots = this.createSlots(2, 2, 4, true);
		int templateW = 5;
		int templateH = 3;
		RecipeTemplate template = generator.createRecipeTemplate(slots, choppingBoard).setSize(templateW * 18 + 6, templateH * 18 + 4);
		
		try {
			Field recipesField = SCCraftingManagerChoppingBoardFilter.class.getDeclaredField("recipes");
			recipesField.setAccessible(true);
			ArrayList<SCCraftingManagerChoppingBoardFilterRecipe> recipes = (ArrayList<SCCraftingManagerChoppingBoardFilterRecipe>) recipesField.get(SCCraftingManagerChoppingBoardFilter.instance);
			
			for (SCCraftingManagerChoppingBoardFilterRecipe recipe : recipes) {
				ItemStack[] crafting = new ItemStack[slots.length];
				
				crafting[2] = recipe.getInput();
				
				crafting[0] = recipe.getStackOnBoard();
				crafting[3] = choppingBoard;
				
				crafting[4] = recipe.getBoardOutput()[0];
				
				if (recipe.getBoardOutput().length > 1)
				{
					for (int i = 1; i < recipe.getBoardOutput().length; i++)
					{
						crafting[4 + i] = recipe.getBoardOutput()[i];
					}
				}
				

				//System.out.println("Adding CuttingBoard Recipe");				
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
	
	public static Slot[] createSlots(int inputSize, int machineH, int outputSize, boolean drawQuantity) {
		int inputW = (int) Math.ceil(inputSize / 3.0);
		int inputH = Math.min(inputSize, 3);
		int inputArea = inputW * inputH;
		
		int outputW = (int) Math.ceil(outputSize / 2.0);
		int outputH = Math.min(outputSize, 2);
		int outputArea = outputW * outputH;
		
		Slot[] slots = new ItemSlot[inputArea + machineH + outputArea];
		
		int maxHeight = Math.max(Math.max(inputH, outputH), machineH);
		
		int inputShift = (maxHeight - inputH) * 9;
		int outputShift = (maxHeight - outputH) * 9;
		int machineShift = (maxHeight - machineH) * 9;
		
		for (int col = 0; col < inputW; col++) {
			for (int row = 0; row < inputH; row++) {
				slots[inputH * col + row] = new ItemSlot(col * 18 + 12, row * 18 + 12 + inputShift, 16, 16, drawQuantity).drawOwnBackground();
			}
		}
		for (int row = 0; row < machineH; row++) {
			slots[inputArea + row] = new ItemSlot(inputW * 18 + 12, row * 18 + 12 + machineShift, 16, 16).setSlotType(SlotType.MACHINE_SLOT);
		}
		for (int col = 0; col < outputW; col++) {
			for (int row = 0; row < outputH; row++) {
				slots[inputArea + machineH + outputH * col + row] = new ItemSlot((inputW + 1 + col) * 18 + 12, row * 18 + 12 + outputShift, 16, 16, drawQuantity).drawOwnBackground().setSlotType(SlotType.OUTPUT_SLOT);
			}
		}
		
		return slots;
	}
}
