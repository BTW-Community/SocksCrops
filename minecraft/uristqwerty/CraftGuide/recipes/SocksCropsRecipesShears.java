package uristqwerty.CraftGuide.recipes;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.Item;
import net.minecraft.src.ItemShears;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCBlockPieCooked;
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
import uristqwerty.CraftGuide.api.StackInfoSource;
import uristqwerty.CraftGuide.api.Util;

public class SocksCropsRecipesShears extends CraftGuideAPIObject implements RecipeProvider, StackInfoSource {

	private ItemStack bambooShoot = new ItemStack(SCDefs.bambooShoot);
	private ItemStack bambooRoot = new ItemStack(SCDefs.bambooRoot);
	private ItemStack sweetberryBush = new ItemStack(SCDefs.sweetberryBush, 1, 5);
	private ItemStack sweetberrySapling = new ItemStack(SCDefs.sweetberrySapling);
	private ItemStack blueberryBush = new ItemStack(SCDefs.blueberryBush, 1, 5);
	private ItemStack blueberrySapling = new ItemStack(SCDefs.blueberrySapling);
	
	

	@Override
	public void generateRecipes(RecipeGenerator generator)
	{
		List<ItemStack> shears = new ArrayList();
		for (int id = 0; id < Item.itemsList.length; id++) {
			Item item = Item.itemsList[id];
			if (item instanceof ItemShears) {
				shears.add(new ItemStack(item));
			}
		}
		
		
		Slot[] slots = createSlots();
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(4 * 18 + 8, 2 * 18 + 4);
		
		ItemStack[] crafting = new ItemStack[slots.length];
		for (ItemStack tool : shears) {
			
			crafting[0] = tool;
			
			ItemStack[] plants = new ItemStack[] {bambooRoot, sweetberryBush, blueberryBush};
			ItemStack[] sapling = new ItemStack[] {bambooShoot, sweetberrySapling, blueberrySapling};
			
			for (int i = 0; i < 3; i++) {
				crafting[1] = plants[i];
				
				crafting[2] = sapling[i];
				generator.addRecipe(template, crafting);
				
				
			}
			
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

	@Override
	public String getInfo(ItemStack itemStack) {
		if (itemStack.itemID == SCDefs.blueberryBush.blockID || itemStack.itemID == SCDefs.sweetberryBush.blockID)
		{
			return "On broken with Shears";
		}
		else if (itemStack.itemID == SCDefs.bambooRoot.blockID)
		{
			return "On right clicked with Shears";
		}
		else return null;
	}
	
	
}
