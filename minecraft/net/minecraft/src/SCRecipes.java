package net.minecraft.src;

public class SCRecipes {

	private static final int ignoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
	
	public static void addRecipes()
	{
		addKnifeRecipes();
		addPieRecipes();
		addGourdRecipes();
		addBambooRecipes();
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
    
    //Knife Cutting
    public static void addKnifeCuttingRecipe(ItemStack output, ItemStack[] secondaryOutputs, ItemStack input)
    {
    	CraftingManager.getInstance().getRecipeList().add(new SCCraftingRecipeKnifeCutting(output, secondaryOutputs, input));
    }
    
    public static void addKnifeCuttingRecipe(ItemStack output, ItemStack[] secondaryOutputs, ItemStack outputLowQuality, ItemStack[] secondaryOutputsLowQuality, ItemStack input)
    {
    	CraftingManager.getInstance().getRecipeList().add(new SCCraftingRecipeKnifeCutting(output, secondaryOutputs, outputLowQuality, secondaryOutputsLowQuality, input));
    }
    
    public static void addKnifeCuttingRecipe(ItemStack output, ItemStack input)
    {
    	CraftingManager.getInstance().getRecipeList().add(new SCCraftingRecipeKnifeCutting(output, input));
    }
    
    public static void addKnifeCuttingRecipe(ItemStack output, ItemStack outputLowQuality, ItemStack input)
    {
    	CraftingManager.getInstance().getRecipeList().add(new SCCraftingRecipeKnifeCutting(output,outputLowQuality, input));
    }
    

	private static void addKnifeRecipes()
	{
		// --- Knife --- //
		
		//Stone
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeStone),
				new Object[] {
						"# ", 
						"WS", 
						'#', new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						'S', new ItemStack( Item.stick, 1 ),
						'W', new ItemStack( Item.silk, 1 )
						
				});
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeStone),
				new Object[] {
						"#W", 
						" S",
						'#', new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						'S', new ItemStack( Item.stick, 1 ),
						'W', new ItemStack( Item.silk, 1 )
						
				});
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeStone),
				new Object[] {
						" #", 
						"SW",
						'#', new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						'S', new ItemStack( Item.stick, 1 ),
						'W', new ItemStack( Item.silk, 1 )
						
				});
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeStone),
				new Object[] {
						"W#", 
						"S ",
						'#', new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						'S', new ItemStack( Item.stick, 1 ),
						'W', new ItemStack( Item.silk, 1 )
						
				});
		
		//Iron
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeIron),
				new Object[] {
						"# ", 
						" S", 
						'#', new ItemStack( Item.ingotIron, 1), 
						'S', new ItemStack( Item.stick, 1 )
				});
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeIron),
				new Object[] {
						" #", 
						"S ", 
						'#', new ItemStack( Item.ingotIron, 1), 
						'S', new ItemStack( Item.stick, 1 )
				});
		
		FCRecipes.AddStokedCrucibleRecipe( new ItemStack( FCBetterThanWolves.fcItemNuggetIron, 6 ), 
				new ItemStack[] {
					new ItemStack( SCDefs.knifeIron, 1, ignoreMetadata )
				} );
		
		//Diamond
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeDiamond),
				new Object[] {
						"# ", 
						" S", 
						'#', new ItemStack( FCBetterThanWolves.fcItemIngotDiamond, 1), 
						'S', new ItemStack( Item.stick, 1 )
				});
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeDiamond),
				new Object[] {
						" #", 
						"S ", 
						'#', new ItemStack( FCBetterThanWolves.fcItemIngotDiamond, 1), 
						'S', new ItemStack( Item.stick, 1 )
				});
		
		FCRecipes.AddStokedCrucibleRecipe( new ItemStack(FCBetterThanWolves.fcItemIngotDiamond, 1), 
				new ItemStack[] {
					new ItemStack(SCDefs.knifeDiamond, 1, ignoreMetadata)
				});
		
		
		// --- Pumpkin --- //
		
		Item pumpkinSlice = SCDefs.pumpkinSliceRaw;
		Item pumpkinSeeds = Item.pumpkinSeeds;
		
		for (int i = 0; i < 4; i++)
		{	
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			addKnifeCuttingRecipe(
					new ItemStack(pumpkinSlice, 2), // output
					new ItemStack[] {
							new ItemStack(pumpkinSeeds, 2)	// secondary output
					},
					new ItemStack(SCDefs.pumpkinHarvested, 1, mature) // input
			);
		}
		
		// --- Melon --- //
		
		Item melonSlice = Item.melon;
		Item honeydewSlice = SCDefs.melonHoneydewSlice;
		Item cantaloupeSlice = SCDefs.melonCantaloupeSlice;
		Item canarySlice = SCDefs.melonCanarySlice;
		
		addKnifeCuttingRecipe(new ItemStack(melonSlice, 3),

				new ItemStack(SCDefs.melonHarvested, 1, 3)
		);
		
		addKnifeCuttingRecipe(new ItemStack(honeydewSlice, 3), //Iron output

				new ItemStack(SCDefs.melonHarvested, 1, 7) //input
		);
		
		addKnifeCuttingRecipe(new ItemStack(cantaloupeSlice, 3), //Iron output

				new ItemStack(SCDefs.melonHarvested, 1, 11) //input
		);
		
		addKnifeCuttingRecipe(new ItemStack(canarySlice, 3), //Iron output

				new ItemStack(SCDefs.melonCanaryHarvested, 1, 12) //input
		);
		
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
    	
    	FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonHarvested,  3 ); //Water
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonHoneydewSlice, 5), SCDefs.melonHarvested,  7 ); //Honeydew
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonCantaloupeSlice, 5), SCDefs.melonHarvested,  11 ); //Cantaloupe
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 12);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 13);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 14);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 15);
		
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 4), SCDefs.pumpkinHarvested, 3 ); //Orange
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 4), SCDefs.pumpkinHarvested, 7 ); //Green
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 4), SCDefs.pumpkinHarvested, 11); //Yellow
		FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 4), SCDefs.pumpkinHarvested, 15); //White
		
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
		
		//Carved
		for (int i = 0; i < 4; i++)
		{
			int meta = (i * 4) + 3; //3, 7, 11, 15
			
			FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, meta ),
					new ItemStack( Item.pumpkinSeeds, 4 ),
					new Object[] {
					new ItemStack( SCDefs.pumpkinHarvested, 1, meta ), 
					new ItemStack( FCBetterThanWolves.fcItemChiselIron, 1, ignoreMetadata )	
			} );
			
			FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, meta ),
					new ItemStack( Item.pumpkinSeeds, 4 ),
					new Object[] {
					new ItemStack( SCDefs.pumpkinHarvested, 1, meta ), 
					new ItemStack( FCBetterThanWolves.fcItemChiselDiamond, 1, ignoreMetadata )	
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
		});
		
		for (int i = 0; i < 4; i++)
		{
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
		    		new ItemStack( SCDefs.pumpkinHarvested, 1, mature ),
		    		new ItemStack( SCDefs.pieBase ),
			});			
		}
		
		
		// Pumpkin Pie via Packing
		FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
				new ItemStack[] {	    		
					new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
					new ItemStack( SCDefs.pieBase )
	    });
		
		for (int i = 0; i < 4; i++)
		{
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
					new ItemStack[] {	    		
						new ItemStack( SCDefs.pumpkinHarvested, 1, mature),
						new ItemStack( SCDefs.pieBase )
		    });		
		}
	}
	
	private static void addBambooRecipes()
	{
		FCRecipes.addPistonPackingRecipe(SCDefs.bambooPacked,
				new ItemStack(SCDefs.bambooItem, 8));
		
	}
}
