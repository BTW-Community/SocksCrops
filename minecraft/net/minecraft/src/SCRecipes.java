package net.minecraft.src;

public class SCRecipes {

	private static final int ignoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
	
	public static void addRecipes()
	{
		addPieRecipes();
		addGourdRecipes();
	}
	
	//Saw recipes
    public static void removeSawRecipe(ItemStack[] outputStacks, Block block, int[] metadata) {
    	FCCraftingManagerSaw.instance.removeRecipe(outputStacks, block, metadata);
    }
    
    public static void removeSawRecipe(ItemStack outputStack, Block block) {
    	removeSawRecipe(outputStack, block, ignoreMetadata);
    }
    
    public static void removeSawRecipe(ItemStack outputStack, Block block, int metadata) {
    	removeSawRecipe(new ItemStack[] {outputStack}, block, new int[] {metadata});
    }
    
	private static void addGourdRecipes() 
	{
		// --- FOOD --- //
		// Roasted Pumpkin Slice
		FurnaceRecipes.smelting().addSmelting( SCDefs.pumpkinSliceRaw.itemID, new ItemStack( SCDefs.pumpkinSliceRoasted ), 0 );
		
		//Boiled Pumpkin Slice
    	FCRecipes.AddCauldronRecipe( 
        		new ItemStack( SCDefs.pumpkinSliceBoiled ), 
        		new ItemStack[] {
        				new ItemStack( SCDefs.pumpkinSliceRaw )
        		} );
    	
    	// --- SAW --- //
    	removeSawRecipe(new ItemStack(Item.melon, 5), Block.melon );
    	
    	FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonHarvested,  3 ); //Water
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonHoneydewSlice, 5), SCDefs.melonHarvested,  7 ); //Honeydew
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonCantaloupeSlice, 5), SCDefs.melonHarvested,  11 ); //Cantaloupe
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 12);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 13);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 14);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 15);
		
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 5), SCDefs.pumpkinHarvested, 3 ); //Orange
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 5), SCDefs.pumpkinHarvested, 7 ); //Green
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 5), SCDefs.pumpkinHarvested, 11); //Yellow
		FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 5), SCDefs.pumpkinHarvested, 15); //White
		
		// --- TRADING --- //
		int farmer = FCEntityVillager.professionIDFarmer;
		int mature = 3;
		FCEntityVillager.removeTradeToBuy(farmer, Block.melon.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.melonHarvested.blockID, mature, 8, 10, 1F, 3);

		FCEntityVillager.removeTradeToBuy(farmer, FCBetterThanWolves.fcBlockPumpkinFresh.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.pumpkinHarvested.blockID, mature, 10, 16, 1F, 3);
		
		// --- Blocks --- //
		// Jack
		for (int i = 0; i < 4; i++)
		{
			int meta = (i * 4) + 3; //3, 7, 11, 15
			
			FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, meta ), new Object[] {
					new ItemStack( SCDefs.pumpkinCarved, 1, meta ), 
					new ItemStack( FCBetterThanWolves.fcItemCandle, 1, ignoreMetadata )
			} );
		}
	}

	private static void addPieRecipes()
	{
		//Remove Old Recipes
		FCRecipes.RemoveVanillaShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
			} );
		
		// Pie Base
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pieBase, 1 ), 
				new Object[] {	    		
	    		new ItemStack( Item.sugar ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
		} );
		
		// Pumpkin Pie
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
				new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
	    		new ItemStack( SCDefs.pieBase ),
		} );
		
		for (int i = 0; i < 4; i++)
		{
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
					new Object[] {	    		
		    		new ItemStack( SCDefs.pumpkinHarvested, 1, mature ),
		    		new ItemStack( SCDefs.pieBase ),
			} );			
		}
	}


}
