package uristqwerty.CraftGuide.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.Item;
import net.minecraft.src.ItemShears;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCBlockPieCooked;
import net.minecraft.src.SCBlockWaterPot;
import net.minecraft.src.SCDefs;
import net.minecraft.src.SCItemKnife;
import net.minecraft.src.SCTileEntityComposter;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class SocksCropsRecipesWaterPot extends CraftGuideAPIObject implements RecipeProvider {

	private ItemStack sweetPotato = new ItemStack(SCDefs.wildPotato);
	private ItemStack sweetPotatoCut = new ItemStack(SCDefs.wildPotatoCut);
	private ItemStack sweetPotatoSapling = new ItemStack(SCDefs.wildPotatoCropSapling);
	
	private ItemStack wildCarrotTop = new ItemStack(SCDefs.wildCarrotTop);
	private ItemStack wildCarrotSeeds = new ItemStack(SCDefs.wildCarrotCropSapling);
	
	private ItemStack waterpot = new ItemStack(SCDefs.waterPot);
	
	

	@Override
	public void generateRecipes(RecipeGenerator generator)
	{

		
		Slot[] slots = createSlots();
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(4 * 18 + 8, 2 * 18 + 4);
		
		ItemStack[] crafting = new ItemStack[slots.length];
		ItemStack[] input = new ItemStack[] {sweetPotato, sweetPotatoCut, wildCarrotTop};
		ItemStack[] output = new ItemStack[] {sweetPotatoSapling, sweetPotatoSapling, wildCarrotSeeds};
		
		for (int i = 0; i < 3; i++)
		{
			crafting[0] = input[i];
			
			crafting[1] = waterpot;
			
			crafting[2] = output[i];
			
			generator.addRecipe(template, crafting);
		}

		
		

		
		
	}
	
	// Taken from DefaultRecipeProvider and modified.
	private Slot[] createSlots() {
		return new ItemSlot[]{
			new ItemSlot(12, 12, 16, 16).drawOwnBackground(),
			new ItemSlot(30+3, 12, 16, 16),
			new ItemSlot(51+3, 12, 16, 16, true).drawOwnBackground().setSlotType(SlotType.OUTPUT_SLOT),
		};
	}
}
