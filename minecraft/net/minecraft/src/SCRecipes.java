package net.minecraft.src;

public class SCRecipes {

	private static final int ignoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
	
	public static void addRecipes()
	{
		addCuttingBoardRecipes();
		
		addToolDefs();
		addKnifeCuttingRecipes();
		addPieRecipes();
		addGourdRecipes();
		addBambooRecipes();
		addFishRecipes();
		addBerryRecipes();
	}
	
    private static void addCuttingBoardRecipes()
	{
    	addKnifeChoppingBoardRecipes();
		addAxeChoppingBoardRecipes();
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.choppingBoard),
				new Object[] {
						"SSS",  
						'S', new ItemStack( Block.woodSingleSlab, 1, ignoreMetadata ),
				});
		
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
    
    /**
     * Note that choppingBoard recipe inputs are limited to stack sizes of 1 (which is enforced upon adding the recipe)
     * @param cuttingOutput The cut items
     * @param input The item to be cut
     * @param itemOnBoard The tool used for cutting
     */
    public static void addChoppingBoardRecipe( ItemStack[] choppingOutput, ItemStack heldStack, ItemStack onBoardStack )
    {
    	SCCraftingManagerChoppingBoardFilter.instance.addRecipe(choppingOutput, heldStack, onBoardStack);
    }

	private static void addKnifeChoppingBoardRecipes()
    {
    	Item knife = SCDefs.knifeStone;
    	
    	for (int i=0; i<3; i++)
    	{
    		switch (i) {
			case 0:
				knife = SCDefs.knifeStone;
				break;
			case 1:
				knife = SCDefs.knifeIron;
				break;
			case 2:
				knife = SCDefs.knifeDiamond;
				break;
			}
    		
    		// -- MELON -- //
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    						new ItemStack(Item.melon, 4),
    						new ItemStack(Item.melonSeeds, 1)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.melonHarvested, 1, 3)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    						new ItemStack(SCDefs.melonHoneydewSlice, 4),
    						new ItemStack(Item.melonSeeds, 1)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.melonHarvested, 1, 7)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    						new ItemStack(SCDefs.melonCantaloupeSlice, 4),
    						new ItemStack(Item.melonSeeds, 1)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.melonHarvested, 1, 11)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    						new ItemStack(SCDefs.melonCanarySlice, 4),
    						new ItemStack(Item.melonSeeds, 1)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.melonCanaryHarvested, 1, 12)
    		);
    		
    		// -- CAKES -- //
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] { 
    					new ItemStack(SCDefs.cakeSlice, 4)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(Item.cake)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] { 
    					new ItemStack(SCDefs.pumpkinPieSlice, 4)
    				},				
    				new ItemStack(knife), //hand		
    				new ItemStack(Item.pumpkinPie)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    					new ItemStack(SCDefs.sweetberryPieSlice, 4)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.sweetberryPieCooked)
    		);
    		
    		addChoppingBoardRecipe(
    				new ItemStack[] {
    					new ItemStack(SCDefs.blueberryPieSlice, 4)
    				},				
    				new ItemStack(knife), //hand
    				new ItemStack(SCDefs.blueberryPieCooked)
    		);    		
    	}
	}
	
    private static void addAxeChoppingBoardRecipes()
    {
		// -- AXE -- //
		
		//Wood to Planks and sticks
		Item axe = Item.axeStone;
		
		for (int i = 0; i < 4; i++)
		{
			addChoppingBoardRecipe(
					new ItemStack[] {
							new ItemStack(Item.stick, 2),
							new ItemStack(FCBetterThanWolves.fcItemBark, 1, i),
							new ItemStack(FCBetterThanWolves.fcItemSawDust, 2, 0), 
					},				
					new ItemStack(axe), //hand
					new ItemStack(Block.wood, 1, i)
			);
		}
		
		//Bloodwood
		addChoppingBoardRecipe(
				new ItemStack[] {
						new ItemStack(Item.stick, 2),
						new ItemStack(FCBetterThanWolves.fcItemBark, 1, 4),
						new ItemStack(FCBetterThanWolves.fcItemSoulDust, 2, 0), 
				},				
				new ItemStack(axe), //hand
				new ItemStack(FCBetterThanWolves.fcBloodWood, 1)
		);

		for (int i = 0; i < 2; i++)
		{
			switch (i) {
			case 0:	
				axe = Item.axeIron;
				break;
			case 1:
				axe = Item.axeGold;
				break;
			case 2:
				axe = Item.axeDiamond;
				break;
			}
			
			for (int j = 0; j < 4; j++)
			{
				addChoppingBoardRecipe(
						new ItemStack[] {
								new ItemStack(Block.planks, 2, j),
								new ItemStack(FCBetterThanWolves.fcItemBark, 1, j),
								new ItemStack(FCBetterThanWolves.fcItemSawDust, 2, 0), 
						},				
						new ItemStack(axe), //hand
						new ItemStack(Block.wood, 1, j)
				);
			}
			
			//Bloodwood
			addChoppingBoardRecipe(
					new ItemStack[] {
							new ItemStack(Block.planks, 2, 4),
							new ItemStack(FCBetterThanWolves.fcItemBark, 1, 4),
							new ItemStack(FCBetterThanWolves.fcItemSoulDust, 2, 0),
					},				
					new ItemStack(axe), //hand
					new ItemStack(FCBetterThanWolves.fcBloodWood, 1)
			);
			
		}
	}

	private static void addToolDefs()
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
		
		//smelting
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
		
		//smelting
		FCRecipes.AddStokedCrucibleRecipe( new ItemStack(FCBetterThanWolves.fcItemIngotDiamond, 1), 
				new ItemStack[] {
					new ItemStack(SCDefs.knifeDiamond, 1, ignoreMetadata)
				});
	}
	
	private static void addKnifeCuttingRecipes()
	{
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
		
		addKnifeCuttingRecipe(new ItemStack(melonSlice, 2),

				new ItemStack(SCDefs.melonHarvested, 1, 3)
		);
		
		addKnifeCuttingRecipe(new ItemStack(honeydewSlice, 2), //output

				new ItemStack(SCDefs.melonHarvested, 1, 7) //input
		);
		
		addKnifeCuttingRecipe(new ItemStack(cantaloupeSlice, 2), //output

				new ItemStack(SCDefs.melonHarvested, 1, 11) //input
		);
		
		addKnifeCuttingRecipe(new ItemStack(canarySlice, 2), //output

				new ItemStack(SCDefs.melonCanaryHarvested, 1, 12) //input
		);
		
		// --- Hemp --- //
		addKnifeCuttingRecipe(new ItemStack(FCBetterThanWolves.fcItemHempFibers, 3), //output

				new ItemStack(FCBetterThanWolves.fcItemHempCloth, 1) //input
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
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pieCrust, 1 ), 
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
					new ItemStack( SCDefs.pieCrust ),
		});
		
		for (int i = 0; i < 4; i++)
		{
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
		    		new ItemStack( SCDefs.pumpkinHarvested, 1, mature ),
		    		new ItemStack( SCDefs.pieCrust ),
			});			
		}
		
		//Berry Pie
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.sweetberryPieRaw, 1 ), 
				new Object[] {	    		
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( Item.sugar),
					new ItemStack( SCDefs.pieCrust ),
		});
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.blueberryPieRaw, 1 ), 
				new Object[] {	    		
					new ItemStack( SCDefs.blueberry),
					new ItemStack( SCDefs.blueberry),
					new ItemStack( SCDefs.blueberry),
					new ItemStack( SCDefs.blueberry),
					new ItemStack( Item.sugar),
					new ItemStack( SCDefs.pieCrust ),
		});
		
		//baking
		FCRecipes.addKilnRecipe(new ItemStack(SCDefs.sweetberryPieCooked), SCDefs.pieRaw, SCBlockPieRaw.subtypeSweetberry);
		FCRecipes.addKilnRecipe(new ItemStack(SCDefs.blueberryPieCooked), SCDefs.pieRaw, SCBlockPieRaw.subtypeBlueberry);
		
		FurnaceRecipes.smelting().addSmelting( SCDefs.sweetberryPieRaw.itemID, new ItemStack( SCDefs.sweetberryPieCooked ), 0 );
		FurnaceRecipes.smelting().addSmelting( SCDefs.blueberryPieRaw.itemID, new ItemStack( SCDefs.blueberryPieCooked ), 0 );
		
		// Pumpkin Pie via Packing
		FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
				new ItemStack[] {	    		
					new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
					new ItemStack( SCDefs.pieCrust )
	    });
		
		for (int i = 0; i < 4; i++)
		{
			int mature = (i * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
					new ItemStack[] {	    		
						new ItemStack( SCDefs.pumpkinHarvested, 1, mature),
						new ItemStack( SCDefs.pieCrust )
		    });		
		}
		
		// Berry Pie via Packing
		FCRecipes.addPistonPackingRecipe(SCDefs.pieRaw, SCBlockPieRaw.subtypeSweetberry,
				new ItemStack[] {	    		
					new ItemStack( SCDefs.sweetberry, 4 ),
					new ItemStack( Item.sugar, 1 ),
					new ItemStack( SCDefs.pieCrust )
	    });
		
		FCRecipes.addPistonPackingRecipe(SCDefs.pieRaw, SCBlockPieRaw.subtypeBlueberry,
				new ItemStack[] {	    		
					new ItemStack( SCDefs.blueberry, 4 ),
					new ItemStack( Item.sugar, 1 ),
					new ItemStack( SCDefs.pieCrust )
	    });
	}
	
	private static void addBambooRecipes()
	{
		//Packed Bamboo
		FCRecipes.addPistonPackingRecipe(SCDefs.bambooPacked,
				new ItemStack(SCDefs.bambooItem, 8));
		
		//FishTrap
		FCRecipes.AddRecipe( new ItemStack (SCDefs.knifeDiamond),
				new Object[] {
						"SBS", 
						"BFB", 
						"SBS", 
						'B', new ItemStack( SCDefs.bambooItem ), 
						'S', new ItemStack( Item.silk ),
						'F', new ItemStack( FCBetterThanWolves.fcItemFishHookBone ),
				});
	}
	
	private static void addFishRecipes()
	{

		//campfire
		FCRecipes.AddCampfireRecipe(SCDefs.salmonRaw.itemID, new ItemStack(SCDefs.salmonCooked));
		FCRecipes.AddCampfireRecipe(SCDefs.codRaw.itemID, new ItemStack(SCDefs.codCooked));
		FCRecipes.AddCampfireRecipe(SCDefs.tropicalRaw.itemID, new ItemStack(SCDefs.tropicalCooked));
		
		//oven
		FurnaceRecipes.smelting().addSmelting( SCDefs.salmonRaw.itemID, new ItemStack( SCDefs.salmonCooked ), 0 );
		FurnaceRecipes.smelting().addSmelting( SCDefs.codRaw.itemID, new ItemStack( SCDefs.codCooked ), 0 );
		FurnaceRecipes.smelting().addSmelting( SCDefs.tropicalRaw.itemID, new ItemStack( SCDefs.tropicalCooked ), 0 );
		
		//cauldron
		for(int i=0; i<3; i++)
		{

			Item fish;
			Item fishCooked;
			
			switch (i) {
			case 0:
				fish = SCDefs.salmonRaw;
				fishCooked = SCDefs.salmonCooked;
				break;
			case 1:
				fish = SCDefs.codRaw;
				fishCooked = SCDefs.codCooked;
				break;
			case 2:
				fish = SCDefs.tropicalRaw;
				fishCooked = SCDefs.tropicalCooked;
				break;

			default:
				fish = SCDefs.salmonRaw;
				fishCooked = SCDefs.salmonCooked;
				break;
			}
			
			FCRecipes.AddCauldronRecipe(new ItemStack(fishCooked),
					new ItemStack[] {
							new ItemStack(fish)
					});
			
			FCRecipes.AddCauldronRecipe( 
		    		new ItemStack[] { 
		    			new ItemStack( FCBetterThanWolves.fcItemFishSoup, 2 ),    			
		    			new ItemStack( Item.bucketEmpty )
		    		},
		    		 
		    		new ItemStack[] {
						new ItemStack( Item.bucketMilk ), 
						new ItemStack( fishCooked ),
						new ItemStack( Item.bowlEmpty, 2 )
				} );
		}
	}
	
	private static void addBerryRecipes()
	{
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.berryBowl, 1 ), 
				new Object[] {	    		
					new ItemStack( SCDefs.blueberry),
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( Item.sugar),
					new ItemStack( Item.bowlEmpty),
		});
	}
}
