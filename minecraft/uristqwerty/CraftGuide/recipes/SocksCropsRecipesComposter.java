package uristqwerty.CraftGuide.recipes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.src.Block;
import net.minecraft.src.FCBetterThanWolves;
import net.minecraft.src.Item;
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

public class SocksCropsRecipesComposter extends CraftGuideAPIObject implements RecipeProvider, StackInfoSource {

	private ItemStack composter = new ItemStack(SCDefs.composter);
	private static ArrayList<Integer> compostingTypes = new ArrayList<Integer>();


	
	@Override
	public void generateRecipes(RecipeGenerator generator)
	{	
		//SCStuff
    	compostingTypes.add(SCDefs.bambooItem.itemID);
    	compostingTypes.add(SCDefs.bambooShoot.blockID);
    	compostingTypes.add(SCDefs.sweetberry.itemID);
    	compostingTypes.add(SCDefs.blueberry.itemID);
    	compostingTypes.add(SCDefs.sweetberrySapling.itemID);
    	compostingTypes.add(SCDefs.blueberrySapling.itemID);
    	compostingTypes.add(SCDefs.pumpkinSliceRaw.itemID);
    	compostingTypes.add(SCDefs.melonHoneydewSlice.itemID);
    	compostingTypes.add(SCDefs.melonCantaloupeSlice.itemID);
    	compostingTypes.add(SCDefs.melonCanarySlice.itemID);
    	
    	compostingTypes.add(SCDefs.clover.blockID);
    	compostingTypes.add(SCDefs.shortPlant.blockID);
    	compostingTypes.add(SCDefs.tallPlant.blockID);
    	compostingTypes.add(SCDefs.mossCarpet.blockID);
    	compostingTypes.add(SCDefs.lilyRose.blockID);

    	//Seeds
    	compostingTypes.add(Item.melonSeeds.itemID);
    	compostingTypes.add(Item.pumpkinSeeds.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemWheatSeeds.itemID);
    	compostingTypes.add(Item.netherStalkSeeds.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemHempSeeds.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemCarrotSeeds.itemID);
    	
    	//other
    	compostingTypes.add(Item.spiderEye.itemID);    	
    	compostingTypes.add(Item.fermentedSpiderEye.itemID);    	
    	compostingTypes.add(Item.poisonousPotato.itemID);    	
    	compostingTypes.add(FCBetterThanWolves.fcItemHemp.itemID);    	
    	compostingTypes.add(FCBetterThanWolves.fcItemFoulFood.itemID);    	
    	compostingTypes.add(FCBetterThanWolves.fcItemDogFood.itemID);    	
    	compostingTypes.add(FCBetterThanWolves.fcItemCreeperOysters.itemID);    	
    	compostingTypes.add(Block.cocoaPlant.blockID);   	
    	
    	//Plants    	
    	compostingTypes.add(Item.reed.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemReedRoots.itemID);
    	compostingTypes.add(Block.tallGrass.blockID);
    	compostingTypes.add(Block.plantYellow.blockID);
    	compostingTypes.add(Block.plantRed.blockID);
    	compostingTypes.add(Block.leaves.blockID);
    	compostingTypes.add(FCBetterThanWolves.fcBlockBloodLeaves.blockID); 	
    	//Blood sapling here..    	
    	compostingTypes.add(Block.vine.blockID);
    	compostingTypes.add(Block.sapling.blockID);
    	compostingTypes.add(Block.waterlily.blockID);
    	compostingTypes.add(FCBetterThanWolves.fcItemMushroomBrown.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemMushroomRed.itemID);
    	
    	compostingTypes.add(FCBetterThanWolves.fcItemBark.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemSawDust.itemID);
    	
    	//Food
    	compostingTypes.add(FCBetterThanWolves.fcItemMushroomBrown.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemMushroomRed.itemID);
    	compostingTypes.add(Item.appleRed.itemID);
    	compostingTypes.add(Item.melon.itemID);
    	compostingTypes.add(Item.carrot.itemID);
    	compostingTypes.add(FCBetterThanWolves.fcItemCarrot.itemID);
    	compostingTypes.add(Item.potato.itemID);
		
		Slot[] slots = createSlots();
		RecipeTemplate template = generator.createRecipeTemplate(slots, null).setSize(4 * 18 + 8, 2 * 18 + 4);
		
		ItemStack[] crafting = new ItemStack[slots.length];
		for (int i = 0; i < compostingTypes.size(); i++) {
			
			crafting[0] = new ItemStack(compostingTypes.get(i), 1, 0);
			
			crafting[1] = composter;
			
			crafting[2] = new ItemStack(SCDefs.compostBlock);
			
			generator.addRecipe(template, crafting);
		}

		
		
	}
	
	// Taken from DefaultRecipeProvider and modified.
	private Slot[] createSlots() {
		return new ItemSlot[]{
			new ItemSlot(12, 12, 16, 16).drawOwnBackground(),
			new ItemSlot(30+3, 12, 16, 16).setSlotType(SlotType.MACHINE_SLOT),
			new ItemSlot(51+3, 12, 16, 16, true).drawOwnBackground().setSlotType(SlotType.OUTPUT_SLOT),
		};
	}
	
	@Override
	public String getInfo(ItemStack itemStack) {
		if (itemStack.itemID == SCDefs.composter.blockID )
		{
			return "After decomposing in Composter";
		}
		else return null;
	}
}
