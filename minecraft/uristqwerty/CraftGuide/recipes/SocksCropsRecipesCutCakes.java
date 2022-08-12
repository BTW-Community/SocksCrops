package uristqwerty.CraftGuide.recipes;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SCBlockPieCooked;
import net.minecraft.src.SCDefs;
import uristqwerty.CraftGuide.api.CraftGuideAPIObject;
import uristqwerty.CraftGuide.api.ItemSlot;
import uristqwerty.CraftGuide.api.RecipeGenerator;
import uristqwerty.CraftGuide.api.RecipeProvider;
import uristqwerty.CraftGuide.api.RecipeTemplate;
import uristqwerty.CraftGuide.api.Slot;
import uristqwerty.CraftGuide.api.SlotType;

public class SocksCropsRecipesCutCakes extends CraftGuideAPIObject implements RecipeProvider {

	private ItemStack stoneKnife = new ItemStack(SCDefs.knifeStone);
	private ItemStack ironKnife = new ItemStack(SCDefs.knifeIron);
	private ItemStack diamondKnife = new ItemStack(SCDefs.knifeDiamond);

	private ItemStack cake = new ItemStack(Block.cake);
	private ItemStack cakeSlice = new ItemStack(SCDefs.cakeSlice);
	
	private ItemStack pumpkinPie = new ItemStack(SCDefs.pieCooked, 1, SCBlockPieCooked.subtypePumpkin + 1);
	private ItemStack pumpkinPieSlice = new ItemStack(SCDefs.pumpkinPieSlice);
	
	private ItemStack sweetberryPie = new ItemStack(SCDefs.pieCooked, 1, SCBlockPieCooked.subtypeSweetberry + 1);
	private ItemStack sweetberryPieSlice = new ItemStack(SCDefs.sweetberryPieSlice);
	
	private ItemStack blueberryPie = new ItemStack(SCDefs.pieCooked, 1, SCBlockPieCooked.subtypeBlueberry + 1);
	private ItemStack blueberryPieSlice = new ItemStack(SCDefs.blueberryPieSlice);
	
	@Override
	public void generateRecipes(RecipeGenerator generator) {
		Slot[] slots = createSlots();
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(4 * 18 + 8, 2 * 18 + 4);
		
		for (ItemStack knife : new ItemStack[] {stoneKnife, ironKnife, diamondKnife}) {
			ItemStack[] crafting = new ItemStack[slots.length];
			
			crafting[0] = knife;
			
			ItemStack[] cakes = new ItemStack[] {cake, pumpkinPie, sweetberryPie, blueberryPie};
			ItemStack[] slice = new ItemStack[] {cakeSlice, pumpkinPieSlice, sweetberryPieSlice, blueberryPieSlice};
			for(int i = 0; i < cakes.length; i++)
			{
				crafting[1] = cakes[i];
				
				crafting[2] = slice[i];
				
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
