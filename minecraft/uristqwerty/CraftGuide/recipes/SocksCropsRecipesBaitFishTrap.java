package uristqwerty.CraftGuide.recipes;

import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCDefs;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class SocksCropsRecipesBaitFishTrap extends CraftGuideAPIObject implements RecipeProvider {
	private ItemStack oyster = new ItemStack(FCBetterThanWolves.fcItemCreeperOysters);
	private ItemStack wing = new ItemStack(FCBetterThanWolves.fcItemBatWing);
	private ItemStack wart = new ItemStack(FCBetterThanWolves.fcItemWitchWart);
	private ItemStack eye = new ItemStack(Item.spiderEye);
	private ItemStack flesh = new ItemStack(Item.rottenFlesh);
	
	private ItemStack fish = new ItemStack(Item.fishRaw);
	private ItemStack cod = new ItemStack(SCDefs.codRaw);
	private ItemStack salmon = new ItemStack(SCDefs.salmonRaw);
	private ItemStack tropical = new ItemStack(SCDefs.tropicalRaw);

	@Override
	public void generateRecipes(RecipeGenerator generator) {
		Slot[] slots = createSlots();
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(4 * 18 + 8, 2 * 18 + 4);
		
		for (ItemStack bait : new ItemStack[] {oyster, wing, wart, eye, flesh}) {
			ItemStack[] crafting = new ItemStack[slots.length];
			
			crafting[0] = bait;

			crafting[1] = new ItemStack(SCDefs.fishTrap);

			for (ItemStack fish : new ItemStack[] {fish, cod, salmon, tropical}) {
				crafting[2] = fish;
				
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
}
