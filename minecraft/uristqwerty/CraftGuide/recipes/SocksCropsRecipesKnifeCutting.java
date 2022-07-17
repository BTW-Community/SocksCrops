package uristqwerty.CraftGuide.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.SCCraftingRecipeKnifeCutting;
import net.minecraft.src.SCItemKnife;
import net.minecraft.src.IRecipe;
import net.minecraft.src.Item;
import net.minecraft.src.ItemAxe;
import net.minecraft.src.ItemStack;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class SocksCropsRecipesKnifeCutting extends CraftGuideAPIObject implements RecipeProvider {
	@Override
	public void generateRecipes(RecipeGenerator generator) {
		List<ItemStack> knives = new ArrayList();
		for (int id = 0; id < Item.itemsList.length; id++) {
			Item item = Item.itemsList[id];
			if (item instanceof SCItemKnife) {
				knives.add(new ItemStack(item));
			}
		}
		
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		
		int outputSize = 0;
		for (IRecipe recipe : recipes) {
			if (recipe instanceof SCCraftingRecipeKnifeCutting) {
				SCCraftingRecipeKnifeCutting knifeRecipe = (SCCraftingRecipeKnifeCutting) recipe;
				
				int outputLen = 0;
				
				if (knifeRecipe.HasSecondaryOutput())
				{
					outputLen = knifeRecipe.getSecondaryOutput().length;
				}
				
				if (knifeRecipe.getHasLowQualityOutputs()) {
					outputLen = Math.max(outputLen, knifeRecipe.getSecondaryOutputLowQuality().length);
				}
				
				outputSize = Math.max(outputSize, outputLen + 1);
			}
		}
		int outputW = (int) Math.ceil(outputSize / 4.0);
		
//		Slot[] slots = createSlots(outputSize);
		Slot[] slots = this.createSlots(2, 4, true);
		int templateW = 4;
		int templateH = 3;
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(templateW * 18 + 6, templateH * 18 + 4);
		
		for (IRecipe recipe : recipes) {
			if (recipe instanceof SCCraftingRecipeKnifeCutting) {
				SCCraftingRecipeKnifeCutting knifeRecipe = (SCCraftingRecipeKnifeCutting) recipe;
				ItemStack[] crafting = new ItemStack[slots.length];
				
				for (ItemStack knife : knives) {
					crafting[0] = knife;
					crafting[1] = knifeRecipe.getInput();
					
					ItemStack[] secondaryOutputs;
					
					crafting[2] = knifeRecipe.getRecipeOutput();
					
					if (knifeRecipe.HasSecondaryOutput()) {
						
						secondaryOutputs = knifeRecipe.getSecondaryOutput();
						
						for (int i = 0; i < secondaryOutputs.length; i++) {
							crafting[3 + i] = secondaryOutputs[i];
						}
					}
					
					//System.out.println("Adding Knife Cutting Recipe");	
					generator.addRecipe(template, crafting);
				}
			}
		}
	}
	
	public static Slot[] createSlots(int inputSize, int outputSize, boolean drawQuantity) {
		int inputW = (int) Math.ceil(inputSize / 2.0);
		int inputH = Math.min(inputSize, 2);
		int inputArea = inputW * inputH;
		
		int outputW = (int) Math.ceil(outputSize / 2.0);
		int outputH = Math.min(outputSize, 2);
		int outputArea = outputW * outputH;
		
		Slot[] slots = new ItemSlot[inputArea + outputArea];
		
		int maxHeight = (Math.max(inputH, outputH));
		
		int inputShift = (maxHeight - inputH) * 9;
		int outputShift = (maxHeight - outputH) * 9;
		
		for (int col = 0; col < inputW; col++) {
			for (int row = 0; row < inputH; row++) {
				slots[inputH * col + row] = new ItemSlot(col * 18 + 12, row * 18 + 12 + inputShift, 16, 16, drawQuantity).drawOwnBackground();
			}
		}
		for (int col = 0; col < outputW; col++) {
			for (int row = 0; row < outputH; row++) {
				slots[inputArea + outputH * col + row] = new ItemSlot((inputW + 1 + col) * 18 + 3, row * 18 + 12 + outputShift, 16, 16, drawQuantity).drawOwnBackground().setSlotType(SlotType.OUTPUT_SLOT);
			}
		}
		
		return slots;
	}
	
//	private Slot[] createSlots(int outputSize) {
//		int outputW = (int) Math.ceil(outputSize / 2.0);
//		int outputH = Math.min(outputSize, 2);
//		int outputArea = outputW * outputH;
//		
//		Slot[] slots = new ItemSlot[2 + outputArea];
//		
//		for (int col = 0; col < 2; col++) {
//			for (int row = 0; row < 2; row++) {
//				slots[2 * col + row] = new ItemSlot(col * 18 + 3, row * 18 + 3, 16, 16).drawOwnBackground();
//			}
//		}
//		for (int col = 0; col < outputW; col++) {
//			for (int row = 0; row < outputH; row++) {
//				slots[4	 + outputH * col + row] = new ItemSlot((3 + col) * 18 + 3, row * 18 + 3, 16, 16, true).drawOwnBackground().setSlotType(SlotType.OUTPUT_SLOT);
//			}
//		}
//		
//		return slots;
//	}
}
