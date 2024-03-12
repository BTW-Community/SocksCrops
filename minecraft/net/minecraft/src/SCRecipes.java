package net.minecraft.src;

import java.util.ArrayList;

public class SCRecipes extends SCRecipeHelper {

	public static final byte cookTimeMultiplier = 1;
	
	public static void addRecipes()
	{		
		//Tools
		addKnifeRecipes();
		
		//Tile Entities
		addStorageJarRecipes();
		addFishtrapRecipes();
		addCuttingBoardRecipes();
		addComposterRecipes();
		addMixerRecipes();
		addJuicerRecipes();
		addPanCookingRecipes();
		addRopeDryingRecipes();
		//addCrateRecipes();
		
		//Compost
		//Compost Grass / Biome Grass
		//Dirts
		//Farmland
		
		//Leaves stuff
		addHedgesRecipes();
		addLeafCarpetRecipes();
		
		//Decorative plants
		addMossRecipes();
		//Logs
		//Sideshrooms
		//Fence & Rope
		addFenceRecipes();
		
		//Grapes
		addGrapeRecipes();
		
		//Hops
		addHopsRecipes();
		
		//Tomato
		addTomatoRecipes();
		
		//Pumpkins
		addPumpkinRecipes();
		
		//Melons
		addMelonRecipes();
		
		addGourdTrades();
		
		//Bamboo
		addBambooRecipes();
		addBambooPackingRecipes();
		
		//Fish
		addFishCuttingRecipes();
		addFishCookingRecipes();
		
		//Bushes
		addBerryRecipes();
		
		//Fruit Trees
		addFruitCuttingRecipes();
		
		//Coconut
		addCoconutCuttingRecipes();
		addCoconutRecipes();
		
		//Crops
		addWildCarrotRecipes();		
		addSweetPotatoRecipes();		
		addWildLettuceRecipes();		
		addWildOnionRecipes();
		
		//Pies
		addPiesRecipes();
		addPieCuttingRecipes();
		
		//Cakes
		addCakeRecipes();
		
		//Muffin
		addMuffinRecipes();
		
		//Cookie
		addCookieRecipes();
		
		//Donut
		addDonutRecipes();
		
		//Burger
		SCRecipesBurger.addBurgerRecipes();
		addBurgerCuttingRecipes();
		addBurgerToppingsRecipes();
		addBurgerDoughRecipes();
		
		//Sandwich
		SCRecipesSandwich.addSandwichRecipes();
		
		//Salads
		SCRecipesSalad.addSaladRecipes();
		
		//Sunflower
		addSunflowerRecipes();
		
		//Rice
		addRiceRecipes();
		
		addChickenFeedRecipes();
		
		addMiscFoodRecipes();
		
		addSoupRecipes();
		
		addHayRecipes();
		
	}
	
	private static void addRopeDryingRecipes() {
		addRopeDryingRecipe(new ItemStack (SCDefs.redRaisins), new ItemStack (SCDefs.redGrapes));
		addRopeDryingRecipe(new ItemStack (SCDefs.whiteRaisins), new ItemStack (SCDefs.whiteGrapes));
	}

	private static void addPanCookingRecipes() {
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.pan),
				new Object[] {
						"IBI",  
						"III",  
						'I', new ItemStack( Item.ingotIron ),
						'B', new ItemStack( Item.bone )
				}
		);
		// Burned meat
		Item[][] meats = {			
			{Item.beefCooked, Item.beefRaw},
			{FCBetterThanWolves.fcItemCookedMysteryMeat, FCBetterThanWolves.fcItemRawMysteryMeat},
			{Item.porkCooked, Item.porkRaw},
			{FCBetterThanWolves.fcItemWolfCooked, FCBetterThanWolves.fcItemWolfRaw},
			{Item.chickenCooked, Item.chickenRaw},
			{Item.fishCooked, Item.fishRaw},
			{SCDefs.codCooked, SCDefs.cod},
			{SCDefs.salmonCooked, SCDefs.salmon},
			{SCDefs.tropicalCooked, SCDefs.tropical},
			{FCBetterThanWolves.fcItemBeastLiverCooked, FCBetterThanWolves.fcItemBeastLiverRaw},
		};
		
		for (Item[] meat : meats)
		{
			addPanCookingRecipe(
					new ItemStack(meat[0]),
					new ItemStack(FCBetterThanWolves.fcItemMeatBurned),
					new ItemStack(meat[1])
					);
		}
		
		//Burned food
		Item[][] foods = {
				//{SCDefs.beefPattyCooked, SCDefs.beefPattyRaw},
				//{SCDefs.baconCooked, SCDefs.baconRaw},
				//{SCDefs.chickenBreastCooked, SCDefs.chickenBreastRaw},
				//{SCDefs.chickenDrumCooked, SCDefs.chickenDrumRaw},
				{FCBetterThanWolves.fcItemFriedEgg, FCBetterThanWolves.fcItemRawEgg},
				{FCBetterThanWolves.fcItemCookedScrambledEggs, FCBetterThanWolves.fcItemRawScrambledEggs},
				{FCBetterThanWolves.fcItemCookedMushroomOmelet, FCBetterThanWolves.fcItemRawMushroomOmelet},
				{FCBetterThanWolves.fcItemCookedCarrot, Item.carrot},
				//{SCDefs.wildCarrotCooked, SCDefs.wildCarrot},
		};
		
		for (int i=0; i < foods.length; i++)
		{
			addPanCookingRecipe(
					new ItemStack(foods[i][0]),
					new ItemStack(SCDefs.burnedFood),
					new ItemStack(foods[i][1])
					);
		}
	}

	private static void addSoupRecipes() {
		
		Item[] carrots = {SCDefs.wildCarrotCooked, FCBetterThanWolves.fcItemCookedCarrot};
		
		for (Item carrot : carrots)
		{
			FCRecipes.AddCauldronRecipe( 
		    		new ItemStack( SCDefs.tomatoSoup, 3 ), 
		    		new ItemStack[] {
						new ItemStack( SCDefs.tomato, 3), 
						new ItemStack( SCDefs.wildOnion ), 
						new ItemStack( carrot ),
						new ItemStack( Item.bowlEmpty, 3 )
			} );
			
			FCRecipes.AddCauldronRecipe( 
		    		new ItemStack( SCDefs.tomatoSoup, 3 ), 
		    		new ItemStack[] {
						new ItemStack( SCDefs.tomatoSlice, 6), 
						new ItemStack( SCDefs.wildOnion ), 
						new ItemStack( carrot ),
						new ItemStack( Item.bowlEmpty, 3 )
			} );
		}
	}
	
	private static void addJuicerRecipes() {
		AddJuicerRecipe( new ItemStack( SCDefs.liquidBlocks[SCUtilsLiquids.APPLE_JUICE]),
				new ItemStack( Item.appleRed), 4
		);
		
		AddJuicerRecipe( new ItemStack( SCDefs.liquidBlocks[SCUtilsLiquids.CHERRY_JUICE]),
				new ItemStack( SCDefs.cherry), 8
		);
	}


	private static void addMixerRecipes() {		
		
		//--- CRAFTING ---//
		//Whisk
		FCRecipes.AddRecipe( new ItemStack (SCDefs.whisk),
				new Object[] {
						" I ",  
						"BBB",  
						"BBB",  
						'I', new ItemStack( Item.ingotIron ),
						'B', new ItemStack( Block.fenceIron )
				}
		);
		
		//Mixer
		FCRecipes.AddRecipe( new ItemStack (SCDefs.mixer ),
				new Object[] {
						"SSS",  
						"GGG",  
						"SWS",  
						'S', new ItemStack( FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, ignoreMetadata ),
						'G', new ItemStack( FCBetterThanWolves.fcItemGear, 1, ignoreMetadata ),
						'W', new ItemStack( SCDefs.whisk )
				}
		);
	
		//--- MIXING ---//
		//No Heat Mixing Recipes
		addMixerDyeRecipes();		
		addMixerSeedsRecipes();		
		addMixerPastryRecipes();		
		addMixerLiquidsRecipes();
		
		addMixerFireRecipes();
		addMixerStokedRecipes();
	}

	private static void addMixerFireRecipes() {
//		AddMixerRecipeFire(new ItemStack(FCBetterThanWolves.fcItemBreadDough), new ItemStack[] {
//				new ItemStack(FCBetterThanWolves.fcItemFlour, 3),
//				//LIQUID
//				new ItemStack(Block.waterStill) 
//			});		
	}
	
	private static void addMixerStokedRecipes() {
//		AddMixerRecipeStoked(new ItemStack(FCBetterThanWolves.fcItemBreadDough), new ItemStack[] {
//				new ItemStack(FCBetterThanWolves.fcItemFlour, 3),
//				//LIQUID
//				new ItemStack(Block.waterStill) 
//			});
	}
	
	protected static void addMixerLiquidsRecipes() {
		AddMixerRecipe( new ItemStack( SCDefs.liquidBlocks[SCUtilsLiquids.COCONUT_MILK], 3 ), new ItemStack[] {
	            new ItemStack( SCDefs.coconutSlice, 8 ),
	            //LIQUID
				new ItemStack( Block.waterStill, 3 )
		});
	}

	protected static void addMixerPastryRecipes() {
		AddMixerRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedCake, 1 ), new ItemStack[] {
            new ItemStack( Item.sugar, 3 ),
            new ItemStack( FCBetterThanWolves.fcItemFlour, 3 ),
            new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
            //LIQUID
			new ItemStack( FCBetterThanWolves.fcBlockMilk)
		});
		
		AddMixerRecipe(new ItemStack(FCBetterThanWolves.fcItemBreadDough), new ItemStack[] {
			new ItemStack(FCBetterThanWolves.fcItemFlour, 3),
			//LIQUID
			new ItemStack(Block.waterStill)
		});
		
		AddMixerRecipe(new ItemStack(SCDefs.pieCrust), new ItemStack[] {
			new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
    		new ItemStack( FCBetterThanWolves.fcItemFlour, 3)
		});
		
		AddMixerRecipe(new ItemStack(FCBetterThanWolves.fcItemPastryUncookedCookies), new ItemStack[] {
			new ItemStack(FCBetterThanWolves.fcItemChocolate),
    		new ItemStack( FCBetterThanWolves.fcItemFlour, 4)

		});
		
		AddMixerRecipe(new ItemStack(SCDefs.burgerDough), new ItemStack[] {
			new ItemStack(FCBetterThanWolves.fcItemFlour, 2),
			new ItemStack( FCBetterThanWolves.fcItemRawEgg, 2 )
		});
		
		AddMixerRecipe(new ItemStack(SCDefs.cookieSweetberryRaw), new ItemStack[] {				
			new ItemStack( SCDefs.sweetberry ),
			new ItemStack( Item.sugar ),
			new ItemStack( FCBetterThanWolves.fcItemRawEgg ),				
			new ItemStack( FCBetterThanWolves.fcItemFlour, 3)
		});
		
		AddMixerRecipe(new ItemStack(SCDefs.cookieBlueberryRaw), new ItemStack[] {				
			new ItemStack( SCDefs.blueberry),
			new ItemStack( Item.sugar ),
			new ItemStack( FCBetterThanWolves.fcItemRawEgg ),				
			new ItemStack( FCBetterThanWolves.fcItemFlour, 3)
		});
		
		Item[] ingredients = { FCBetterThanWolves.fcItemChocolate, SCDefs.sweetberry, SCDefs.blueberry };
		Item[] uncookedMuffin = { SCDefs.itemMuffinRawChocolate, SCDefs.itemMuffinRawSweetberry, SCDefs.itemMuffinRawBlueberry };
		
		for(int i=0; i < uncookedMuffin.length; i++)
		{
			AddMixerRecipe( new ItemStack( uncookedMuffin[i], 1 ), new ItemStack[] {
					new ItemStack( ingredients[i] ), 
		            new ItemStack( Item.sugar ), 
		            new ItemStack( FCBetterThanWolves.fcItemFlour, 2), 
		            new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
		            //LIQUID
					new ItemStack( FCBetterThanWolves.fcBlockMilk)
		    });
		}		
		
	}

	protected static void addMixerSeedsRecipes() {
		//BTW/Vanilla
		Item[] seeds = {
				Item.melonSeeds,
				Item.pumpkinSeeds,
				FCBetterThanWolves.fcItemHempSeeds,
				FCBetterThanWolves.fcItemCarrotSeeds,
				FCBetterThanWolves.fcItemWheatSeeds,				
				//SC
				SCDefs.appleSeeds,
				SCDefs.cherrySeeds,
				SCDefs.lemonSeeds,
				SCDefs.oliveSeeds,
				
				SCDefs.domesticatedMelonSeeds,
				SCDefs.domesticatedPumpkinSeeds,
				
				SCDefs.grassSeeds,
				SCDefs.hopSeeds,				
				SCDefs.tomatoSeeds,
				SCDefs.sunflowerSeeds,
				
				SCDefs.redGrapeSeeds,
				SCDefs.whiteGrapeSeeds,
				
				SCDefs.wildCarrotSeeds,
				SCDefs.wildLettuceSeeds,
				SCDefs.wildOnionSeeds,
				
				SCDefs.rice
		};
		
		for(Item seed : seeds)
		{
			AddMixerRecipe(new ItemStack(FCBetterThanWolves.fcItemChickenFeed), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, 15), //Bonemeal
				new ItemStack(seed)
			});
		}
	}

	protected static void addMixerDyeRecipes() {
		int BLACK = 0;
		int RED = 1;
		int GREEN = 2;
		int BROWN = 3;
		int BLUE = 4;
		int PURPLE = 5;
		int CYAN = 6;
		int SILVER = 7;
		int GRAY = 8;
		int PINK = 9;
		int LIME = 10;
		int YELLOW = 11;
		int LIGHTBLUE = 12;
		int MAGENTA = 13;
		int ORANGE = 14;
		int WHITE = 15;
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, PINK), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 1, RED),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, LIME), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 1, GREEN),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, CYAN), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, BLUE),			
				new ItemStack(Item.dyePowder, 1, GREEN),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, PURPLE), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, BLUE),			
				new ItemStack(Item.dyePowder, 1, RED),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, LIGHTBLUE), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 1, BLUE),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, SILVER), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 1, GRAY),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 3, SILVER), new ItemStack[] {
				new ItemStack(Item.dyePowder, 2, WHITE),			
				new ItemStack(Item.dyePowder, 1, BLACK),			
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, GRAY), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 1, BLACK),	
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 3, MAGENTA), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, PINK),			
				new ItemStack(Item.dyePowder, 1, RED),
				new ItemStack(Item.dyePowder, 1, BLUE),	
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 4, MAGENTA), new ItemStack[] {
				new ItemStack(Item.dyePowder, 1, WHITE),			
				new ItemStack(Item.dyePowder, 2, RED),
				new ItemStack(Item.dyePowder, 1, BLUE),	
			});
		
		AddMixerRecipe(new ItemStack(Item.dyePowder, 2, ORANGE), new ItemStack[] {		
				new ItemStack(Item.dyePowder, 1, RED),
				new ItemStack(Item.dyePowder, 1, YELLOW),	
			});
		
	}

	private static void addCoconutRecipes() {
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.coconutDrink),
				new Object[] {
						new ItemStack( SCDefs.bambooStrippedItem, 1 ),
						new ItemStack( SCDefs.coconut, 1 )
				});
	}

	private static void addHayRecipes()
	{
		FCRecipes.AddShapelessRecipe(new ItemStack(SCDefs.strawBale), 
				new ItemStack[]{
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
					new ItemStack(FCBetterThanWolves.fcItemStraw),
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SCDefs.hayBale), 
				new ItemStack[]{
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
					new ItemStack(SCDefs.hay),
				});
		
	
	}

	private static void addMiscFoodRecipes()
	{
		FCRecipes.addKilnRecipe(new ItemStack[] {
				new ItemStack(Item.bucketEmpty),
				new ItemStack(SCDefs.salt)
				
		}, FCBetterThanWolves.fcBlockBucketWater);
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SCDefs.butter), new ItemStack[] {
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
				new ItemStack(SCDefs.butterPiece),
		});
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SCDefs.bowlMilk, 3), new ItemStack[] {
				new ItemStack(Item.bucketMilk),
				new ItemStack(Item.bowlEmpty),
				new ItemStack(Item.bowlEmpty),
				new ItemStack(Item.bowlEmpty),
		});
		
		FCRecipes.AddShapelessRecipe(new ItemStack(SCDefs.bowlMilkProgressive, 1, 
				SCItemBowlMilkProgressive.milkMaxDamage - 1), new ItemStack[] {
				new ItemStack(SCDefs.bowlMilk)
		});
	}
	
	//----------- Tools	-----------//
	private static void addKnifeRecipes()
	{
		//Stone		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.knifeStone),
				new Object[] {
						new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						new ItemStack( Item.stick, 1 ),
						new ItemStack( Item.silk, 1 )
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.knifeStone),
				new Object[] {
						new ItemStack( FCBetterThanWolves.fcItemChiselStone, 1), 
						new ItemStack( Item.stick, 1 ),
						new ItemStack( FCBetterThanWolves.fcItemHempFibers, 1 )
				});
		
		addKnifeRecipe(SCDefs.knifeIron, Item.ingotIron, Item.stick, FCBetterThanWolves.fcItemNuggetIron, 6);
		addKnifeRecipe(SCDefs.knifeGold, Item.ingotGold, Item.stick, Item.goldNugget, 6);
		addKnifeRecipe(SCDefs.knifeDiamond, FCBetterThanWolves.fcItemIngotDiamond, Item.stick, FCBetterThanWolves.fcItemIngotDiamond, 1);
		addKnifeRecipe(SCDefs.knifeSteel, FCBetterThanWolves.fcItemSteel, FCBetterThanWolves.fcItemHaft, FCBetterThanWolves.fcItemSteel, 1);
	}
	
	//----------- Tile Entities	-----------//
	private static void addCuttingBoardRecipes()
	{
		int bamboo = 5;
		int strippedBamboo = 6;
		
		for (int type = 0; type < 5; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.choppingBoard, 1, type),
					new Object[] {
							"SSS",  
							'S', new ItemStack( Block.woodSingleSlab, 1, type ),
					}
			);
		}
		
		//Sidings
		for (int type = 0; type < 5; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.choppingBoard, 1, type),
					new Object[] {
							"SSS",  
							'S', new ItemStack( FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, type ),
					}
			);
		}
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.choppingBoard, 1, bamboo),
				new Object[] {
						"SSS",  
						'S', new ItemStack( SCDefs.bambooPacked, 1, 0 ),
				}
		);
		
		FCRecipes.AddRecipe( new ItemStack (SCDefs.choppingBoard, 1, strippedBamboo),
				new Object[] {
						"SSS",  
						'S', new ItemStack( SCDefs.bambooPacked, 1, 1 ),
				}
		);
	}

	private static void addStorageJarRecipes()
	{
		Block[] jars = { SCDefs.storageJar, SCDefs.seedJar };
		
		for (Block jar : jars)
		{
			for (int woodType = 0; woodType < 5; woodType++)
			{
				FCRecipes.AddRecipe( new ItemStack (jar, 1, 0),
						new Object[] {
								"C",  
								"G",  
								'C', new ItemStack( FCBetterThanWolves.fcBlockWoodCornerItemStubID, 1, woodType ),
								'G', new ItemStack( Block.glass, 1 ),
						});
			}
			
			//Apply Label	
			FCRecipes.AddShapelessRecipe( new ItemStack(jar, 1, 1),
					new Object[] {
							new ItemStack(jar, 1, 0),
							new ItemStack(Item.paper, 1)
	    			});
			
			//Label Removal
			FCRecipes.AddStokedCauldronRecipe(new ItemStack(jar, 1, 0), 
					new ItemStack[] {
							new ItemStack(FCBetterThanWolves.fcItemSoap),
							new ItemStack(jar, 1, 1),
					});
		}
	}

	private static void addFishtrapRecipes() 
	{
		Item[] strings = { Item.silk, FCBetterThanWolves.fcItemHempFibers };
		Item[] hooks = { FCBetterThanWolves.fcItemNuggetIron, FCBetterThanWolves.fcItemFishHookBone };
		
		for (Item hook : hooks) {
			for (Item string : strings) {

				FCRecipes.AddRecipe(new ItemStack(SCDefs.fishTrap),
						new Object[] {
								"SBS",
								"BFB",
								"SBS",
								'B', new ItemStack(SCDefs.bambooWeave),
								'S', new ItemStack(string),
								'F', new ItemStack(hook), });
			}
		}		
	}
	
	private static void addComposterRecipes()
	{
		//Composter
		FCRecipes.AddRecipe( new ItemStack (SCDefs.composter),
				new Object[] {
						"S S",  
						"S S",  
						"SSS",  
						'S', new ItemStack( Block.woodSingleSlab, 1, ignoreMetadata ),
				});
		
    	for (int wood=0; wood<4; wood++)
    	{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.composter),
					new Object[] {
							"S S",  
							"S S",  
							"SSS",  
							'S', new ItemStack( FCBetterThanWolves.fcBlockWoodSidingItemStubID, 1, wood ),
					});
    	}
	}
	
	private static void addCrateRecipes()
	{
		for (int woodType = SCBlockCrate.OAK; woodType <= SCBlockCrate.BLOOD; woodType++)
		{	
			//Crate
			FCRecipes.AddRecipe( new ItemStack (SCDefs.crate, 1, woodType),
					new Object[] {
							"M M",  
							"M M",
							"SSS",
							'M', new ItemStack( FCBetterThanWolves.fcBlockWoodMouldingItemStubID, 1, woodType ),
							'S', new ItemStack( Block.woodSingleSlab, 1, woodType )
					});
			
			//Contents
			for (int type = SCBlockCrate.WHEAT; type <= SCBlockCrate.BLUEBERRY; type++)
			{
				Item item = SCBlockCrate.items.get(type);
				
				//Adds Recipes from empty to full
				
				//woodType, inputCrate, item, outputCrate
				fillCrateRecipes(woodType, SCBlockCrate.EMPTY, item, type);

				//Adds Recipes to empty the whole crate
				//woodType, inputCrate, item
				emptyCrateRecipes(woodType, type, item);
				
				//Adds Recipes to add to an already filled crate				
				for (int count = 1; count < 8; count++)
				{
					 for (int fill = 0; fill < count; fill++)
					 {
						//woodType, item, crateType, currentFill, countAddded
						 addToCrateRecipes(woodType, item, type, fill, 8 - count);
					 }
				}
			}
		}		
	}

	//----------- Compost-----------//	
	//----------- Compost Grass / Biome Grass -----------//
	
	//----------- Dirts -----------//	
	//----------- Farmland -----------//
	
	//----------- Leaves stuff -----------//
	private static void addHedgesRecipes()
	{
		//TREES
		for (int type = SCBlockHedges.OAK; type <= SCBlockHedges.JUNGLE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedges, 3, type),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( Block.leaves, 1, type ),
							'W', new ItemStack( Block.wood, 1, type ),
					});
		}
		
		//BLOOD TREE
		FCRecipes.AddRecipe( new ItemStack (SCDefs.hedges, 3, SCBlockHedges.BLOOD),
				new Object[] {
						"LLL",
						"LWL",
						'L', new ItemStack( FCBetterThanWolves.fcBlockBloodLeaves, 1),
						'W', new ItemStack( FCBetterThanWolves.fcBloodWood, 1),
				});
		
		//FRUIT
		for (int type = SCBlockHedges.APPLE; type <= SCBlockHedges.OLIVE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedges, 3, type),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDefs.fruitLeaves, 1, type - 5 ),
							'W', new ItemStack( SCDefs.fruitLog, 1, type - 5 ), //-5 meta Adjustment
					});
		}
		
		//FLOWERING FRUIT
		for (int type = SCBlockHedges.FLOWER_APPLE; type <= SCBlockHedges.FLOWER_OLIVE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedges, 3, type),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDefs.fruitLeavesFlowers, 1, type - 9),
							'W', new ItemStack( SCDefs.fruitLog, 1, type - 9), //-9 meta adjustment
					});
		}
		
		//DECO
		if ( SCDecoIntegration.isDecoInstalled() )
		{
			//CHERRY
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedges, 3, SCBlockHedges.CHERRY),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDecoIntegration.cherryLeaves, 1),
							'W', new ItemStack( SCDefs.fruitLog, 1, SCBlockHedges.CHERRY ),
					});
			
			//ACACIA
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedgesDeco, 3, SCBlockHedgesDeco.ACACIA),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDecoIntegration.acaciaLeaves, 1),
							'W', new ItemStack( SCDecoIntegration.acaciaLog, 1, 0),
					});
			
			//AUTUMN
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedgesDeco, 3, SCBlockHedgesDeco.AUTUMN_RED),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 0),
							'W', new ItemStack( Block.wood, 1, 0),
					});
			
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedgesDeco, 3, SCBlockHedgesDeco.AUTUMN_ORANGE),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 1),
							'W', new ItemStack( Block.wood, 1, 0),
					});
			
			FCRecipes.AddRecipe( new ItemStack (SCDefs.hedgesDeco, 3, SCBlockHedgesDeco.AUTUMN_YELLOW),
					new Object[] {
							"LLL",
							"LWL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 2),
							'W', new ItemStack( Block.wood, 1, 0),
					});
		}

	}
	
	private static void addLeafCarpetRecipes()
	{
		//TREES
		for (int type = SCBlockLeafCarpet.OAK; type <= SCBlockLeafCarpet.JUNGLE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpet, 3, type),
					new Object[] {
							"LLL",
							'L', new ItemStack( Block.leaves, 1, type ),
					});
		}
		
		//BLOOD TREE
		FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpet, 3, SCBlockLeafCarpet.BLOOD),
				new Object[] {
						"LLL",
						'L', new ItemStack( FCBetterThanWolves.fcBlockBloodLeaves, 1),
				});
		
		//FRUIT
		for (int type = SCBlockLeafCarpet.APPLE; type <= SCBlockLeafCarpet.OLIVE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpet, 3, type - 5),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDefs.fruitLeaves, 1, type - 5),
					});
		}
		
		//FLOWERING FRUIT
		for (int type = SCBlockLeafCarpet.FLOWER_APPLE; type <= SCBlockLeafCarpet.FLOWER_OLIVE; type++)
		{
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpet, 3, type - 9),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDefs.fruitLeavesFlowers, 1, type - 9 ),
					});
		}
		
		//DECO
		if (SCDecoIntegration.isDecoInstalled())
		{
			//ACACIA
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpetDeco, 3, SCBlockLeafCarpetDeco.ACACIA),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDecoIntegration.acaciaLeaves, 1 ),
					});
			
			//AUTUMN
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpetDeco, 3, SCBlockLeafCarpetDeco.AUTUMN_RED),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 0),
					});
			
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpetDeco, 3, SCBlockLeafCarpetDeco.AUTUMN_ORANGE),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 1),
					});
			
			FCRecipes.AddRecipe( new ItemStack (SCDefs.leafCarpetDeco, 3, SCBlockLeafCarpetDeco.AUTUMN_YELLOW),
					new Object[] {
							"LLL",
							'L', new ItemStack( SCDecoIntegration.autumnLeaves, 1, 2),
					});
		}
	}
	
	//----------- Decorative plants -----------//
	private static void addMossRecipes() {
		//MOSSBALL > CARPET
		FCRecipes.AddRecipe(new ItemStack(SCDefs.mossCarpet, 1), new Object[] {
				"MMM", 
				'M', SCDefs.mossBall});
		
		//CARPET > MOSSBALL
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.mossBall, 3 ), 
				new Object[] {	    		
	    		new ItemStack( SCDefs.mossCarpet ),
		} );
		
		//MOSSBALL > BLOCK
		FCRecipes.addPistonPackingRecipe(SCDefs.mossBlock,
				new ItemStack[] {	    		
					new ItemStack( SCDefs.mossBall, 8 )
	    });
		
		//BLOCK > MOSSBALL
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.mossBall, 8 ), 
				new Object[] {	    		
	    		new ItemStack( SCDefs.mossBlock ),
		} );
	}
	
	//----------- Logs -----------//	
	//----------- Sideshrooms -----------//
	
	//----------- Fence & Rope -----------//
	private static void addFenceRecipes() {

		//BAMBOO
		FCRecipes.AddRecipe( new ItemStack( Block.fence, 6, SCBlockFence.BAMBOO ),
			new Object[] {
	            "###", 
	            "###", 
	    		'#', new ItemStack( SCDefs.bambooPacked, 1, SCBlockBambooPacked.BAMBOO ),
			} );
		
		//STRIPPED BAMBOO
		FCRecipes.AddRecipe( new ItemStack( Block.fence, 6, SCBlockFence.STRIPPED_BAMBOO ),
				new Object[] {
		            "###", 
		            "###", 
		    		'#', new ItemStack( SCDefs.bambooPacked, 1, SCBlockBambooPacked.STRIPPED_BAMBOO ),
				} );
	
	}

	//----------- Grapes -----------//
	private static void addGrapeRecipes() 
	{
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.redGrapeSeeds, 1), 
				new Object[] {	    		
	    		new ItemStack( SCDefs.redGrapes )
		} );
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.whiteGrapeSeeds, 1), 
				new Object[] {	    		
	    		new ItemStack( SCDefs.whiteGrapes )
		} );
	}
	
	
	//----------- Hops -----------//
	private static void addHopsRecipes()
	{
		FCRecipes.addHopperFilteringRecipe(new ItemStack(SCDefs.hopSeeds, 2), 
				new ItemStack(SCDefs.hops),
				new ItemStack(FCBetterThanWolves.fcBlockWickerPane));
		
	}
	
	//----------- Tomato -----------//		
	private static void addTomatoRecipes()
	{
		addKnifeCuttingRecipes(new ItemStack(SCDefs.tomato),
				new ItemStack[] {
					new ItemStack(SCDefs.tomatoSlice),
					new ItemStack(SCDefs.tomatoSlice),
					new ItemStack(SCDefs.tomatoSeeds)
				});
	}

	//----------- Pumpkins -----------//
	private static void addPumpkinRecipes()
	{
		// --- DECO --- //
		if (SCDecoIntegration.isDecoInstalled())
		{
			int orange = 3;
			Item[] chisels = {FCBetterThanWolves.fcItemChiselWood, FCBetterThanWolves.fcItemChiselStone, FCBetterThanWolves.fcItemChiselIron, FCBetterThanWolves.fcItemChiselDiamond };
			
			for (Item chisel : chisels)
			{
				FCRecipes.AddShapelessRecipe( new ItemStack( SCDecoIntegration.pumpkin, 1, 0 ), new Object[] {
						new ItemStack( SCDefs.pumpkinHarvested, 1, orange ), 
						new ItemStack( chisel, 1, ignoreMetadata )
				} );
				
				FCRecipes.AddShapelessRecipe( new ItemStack( SCDecoIntegration.pumpkin, 1, 1 ), new Object[] {
						new ItemStack( SCDefs.pumpkinCarved, 1, orange ), 
						new ItemStack( chisel, 1, ignoreMetadata )
				} );
			}
		}
		
		// --- FOOD --- //
		
		// Roasted Pumpkin Slice
		FurnaceRecipes.smelting().addSmelting( SCDefs.pumpkinSliceRaw.itemID, new ItemStack( SCDefs.pumpkinSliceRoasted ), 0 );
				
		//Boiled Pumpkin Slice
		FCRecipes.AddCauldronRecipe( 
		        		new ItemStack( SCDefs.pumpkinSliceBoiled ), 
		        		new ItemStack[] {
		        				new ItemStack( SCDefs.pumpkinSliceRaw )
		        		} );
		
		for (int type = 0; type < 4; type++)
		{
			int mature = (type * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			Item[] carving = {FCBetterThanWolves.fcItemChiselIron, FCBetterThanWolves.fcItemChiselDiamond };
			
			// --- Jack --- //
			FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pumpkinJack, 1, mature ), new Object[] {
					new ItemStack( SCDefs.pumpkinCarved, 1, mature ), 
					new ItemStack( FCBetterThanWolves.fcItemCandle, 1, ignoreMetadata )
			} );
			
			// --- Carved --- //
			for (Item chisel : carving)
			{
				FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.pumpkinCarved, 1, mature ),
						new ItemStack( Item.pumpkinSeeds, 2 ),
						new Object[] {
						new ItemStack( SCDefs.pumpkinHarvested, 1, mature ), 
						new ItemStack( chisel, 1, ignoreMetadata )	
				} );
			}
			
			// --- Saw --- //
			FCRecipes.addSawRecipe(new ItemStack(SCDefs.pumpkinSliceRaw, 4), SCDefs.pumpkinHarvested, mature ); //Orange

			// --- Cutting Board --- //			
			
			//SLICES
			addGourdSliceRecipe(SCDefs.pumpkinHarvested, mature, SCDefs.pumpkinSliceRaw);
			
			//CARVING
			addChiselCarvingRecipes(new ItemStack(SCDefs.pumpkinHarvested, 1, mature),
					new ItemStack[] {
							new ItemStack(SCDefs.pumpkinCarved, 1, mature),
							new ItemStack(Item.pumpkinSeeds),
							new ItemStack(Item.pumpkinSeeds)
						});
		}
	}
	
	//----------- Melons -----------//
	private static void addMelonRecipes()
	{			
    	FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonHarvested,  3 ); //Water
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonHoneydewSlice, 5), SCDefs.melonHarvested,  7 ); //Honeydew
    	FCRecipes.addSawRecipe(new ItemStack(SCDefs.melonCantaloupeSlice, 5), SCDefs.melonHarvested,  11 ); //Cantaloupe
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 12);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 13);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 14);
		FCRecipes.addSawRecipe(new ItemStack(Item.melon, 5), SCDefs.melonCanaryHarvested, 15);
		
		FCRecipes.AddShapelessRecipe(new ItemStack(Item.melonSeeds, 2), new Object[] {
				new ItemStack(FCBetterThanWolves.fcItemMelonMashed)
			});
		
		//Slices
		addGourdSliceRecipe(SCDefs.melonHarvested, 3, Item.melon);
		addGourdSliceRecipe(SCDefs.melonHarvested, 7, SCDefs.melonHoneydewSlice);
		addGourdSliceRecipe(SCDefs.melonHarvested, 11, SCDefs.melonCantaloupeSlice);
		addGourdSliceRecipe(SCDefs.melonCanaryHarvested, 12, SCDefs.melonCanarySlice);
	}
		
	private static void addGourdTrades() {
		// --- TRADING --- //
		int farmer = FCEntityVillager.professionIDFarmer;
		int mature = 3;
		FCEntityVillager.removeTradeToBuy(farmer, Block.melon.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.melonHarvested.blockID, mature, 8, 10, 1F, 3);

		FCEntityVillager.removeTradeToBuy(farmer, FCBetterThanWolves.fcBlockPumpkinFresh.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.pumpkinHarvested.blockID, mature, 10, 16, 1F, 3);
	}	

	//----------- Bamboo -----------//	
	private static void addBambooRecipes()
	{
		//Cutting
		addKnifeCuttingRecipes(new ItemStack(SCDefs.bambooItem),
				new ItemStack[] {
					new ItemStack(SCDefs.bambooStrippedItem)
				});
		
		//Stripped Bamboo
		FCRecipes.addLogChoppingRecipe(new ItemStack(SCDefs.bambooStrippedItem), 
				new ItemStack[] {
						
				}, 
				new ItemStack(SCDefs.bambooItem));
		
		//Bamboo Grate
		FCRecipes.AddRecipe( new ItemStack( SCDefs.bambooGrate),
			new Object[] {
	    		"#B#", 
	    		"BSB",
	    		"#B#",
	    		'#', Item.silk,
	    		'B', SCDefs.bambooItem,
	    		'S', Item.stick
			} );
		
		FCRecipes.AddRecipe( new ItemStack( SCDefs.bambooGrate),
				new Object[] {
		    		"#B#", 
		    		"BSB",
		    		"#B#",
		    		'#', FCBetterThanWolves.fcItemHempFibers,
		    		'B', SCDefs.bambooItem,
		    		'S', Item.stick
				} );
		
		//Bamboo Weaving
		FCRecipes.AddRecipe( new ItemStack( SCDefs.bambooProgressiveItem, 1,
				SCItemBambooProgressive.bambooWeavingMaxDamage - 1 ), new Object[] {
	    		"#S#", 
	    		"SSS",
	    		"#S#",
	    		'#', SCDefs.bambooItem,
	    		'S', Item.stick
			} );
	}
	
	private static void addBambooPackingRecipes()
	{
		FCRecipes.addPistonPackingRecipe(SCDefs.smallPacked, SCBlockSmallPacked.REEDS,
				new ItemStack[]	{
					new ItemStack(Item.reed, 16)
				}	
		);
		
		FCRecipes.addPistonPackingRecipe(SCDefs.smallPacked, SCBlockSmallPacked.SHAFTS,
				new ItemStack[]	{
					new ItemStack(Item.stick, 16)
				}	
		);
		
		FCRecipes.addPistonPackingRecipe(SCDefs.bambooPacked, SCBlockBambooPacked.BAMBOO,
				new ItemStack[]	{
					new ItemStack(SCDefs.bambooItem, 8)
				}	
		);
		
		FCRecipes.addPistonPackingRecipe(SCDefs.bambooPacked, SCBlockBambooPacked.STRIPPED_BAMBOO,
				new ItemStack[]	{
					new ItemStack(SCDefs.bambooStrippedItem, 8)
				}	
		);
	}
	
	//----------- Fish -----------//
	private static void addFishCookingRecipes()
	{
		//Whole fish
		Item[] fish = { SCDefs.cod, SCDefs.salmon, SCDefs.tropical }; //excluding puffer and fish
		Item[] cooked = { SCDefs.codCooked, SCDefs.salmonCooked, SCDefs.tropicalCooked }; //excluding puffer and fish
		
		for(int type = 0; type < fish.length; type++)
		{
			FCRecipes.AddCampfireRecipe( fish[type].itemID, new ItemStack( cooked[type] ) );
			
			FurnaceRecipes.smelting().addSmelting( fish[type].itemID, new ItemStack( cooked[type] ), 0);
		}
		
		//Filet
		Item[] filets = { SCDefs.fishFiletRaw, SCDefs.codFiletRaw, SCDefs.salmonFiletRaw, SCDefs.tropicalFiletRaw };
		Item[] filetCooked = { SCDefs.fishFiletCooked, SCDefs.codFiletCooked, SCDefs.salmonFiletCooked, SCDefs.tropicalFiletCooked };
		
		for(int type = 0; type < fish.length; type++)
		{
			FurnaceRecipes.smelting().addSmelting( filets[type].itemID, new ItemStack( filetCooked[type] ), 0);
		}
		
		//batter
		//remove raw egg recipe, readded below
		FCCraftingManagerCauldron.getInstance().RemoveRecipe( 
	    		new ItemStack( FCBetterThanWolves.fcItemHardBoiledEgg ), 
	    		new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemRawEgg ) 
		} );
		
		FCRecipes.AddCauldronRecipe(
				new ItemStack[] {
					new ItemStack(SCDefs.batter, 2)
				},
				new ItemStack[] {				
					new ItemStack(FCBetterThanWolves.fcItemFlour, 2 ),
					new ItemStack(FCBetterThanWolves.fcItemRawEgg ),
					new ItemStack(SCDefs.salt)
		});
		
		//re-add raw egg recipe
		FCRecipes.AddCauldronRecipe( 
	    		new ItemStack( FCBetterThanWolves.fcItemHardBoiledEgg ), 
	    		new ItemStack[] {
					new ItemStack( FCBetterThanWolves.fcItemRawEgg ) 
			} );
		
		
		//deepfried
		for (Item filet : filets)
		{
			FCRecipes.AddCauldronRecipe(new ItemStack(SCDefs.fishFiletDeepFried), new ItemStack[] 
			{				
				new ItemStack(filet),
				new ItemStack(SCDefs.batter),
			});
		}
		

	}
	
	private static void addFishCuttingRecipes()
	{
		addKnifeCuttingRecipes(new ItemStack(Item.fishRaw),
				new ItemStack[] {
					new ItemStack(SCDefs.fishFiletRaw),
					new ItemStack(SCDefs.fishFiletRaw)
				});
		
		addKnifeCuttingRecipes(new ItemStack(SCDefs.cod),
				new ItemStack[] {
					new ItemStack(SCDefs.codFiletRaw),
					new ItemStack(SCDefs.codFiletRaw)
				});
		
		addKnifeCuttingRecipes(new ItemStack(SCDefs.salmon),
				new ItemStack[] {
					new ItemStack(SCDefs.salmonFiletRaw),
					new ItemStack(SCDefs.salmonFiletRaw)
				});
		
		addKnifeCuttingRecipes(new ItemStack(SCDefs.tropical),
				new ItemStack[] {
					new ItemStack(SCDefs.tropicalFiletRaw),
					new ItemStack(SCDefs.tropicalFiletRaw)
				});
		
		addKnifeCuttingRecipes(new ItemStack(SCDefs.puffer),
				new ItemStack[] {
					new ItemStack(SCDefs.pufferFiletRaw)
				});
	}

	//----------- Bushes -----------//
	private static void addBerryRecipes()
	{
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.sweetBlueBerryBowl, 1 ), 
				new Object[] {	    		
					new ItemStack( SCDefs.blueberry),
					new ItemStack( SCDefs.sweetberry),
					new ItemStack( Item.sugar),
					new ItemStack( Item.bowlEmpty),
		});
	}
	
	//----------- Fruit Trees -----------//
	private static void addFruitCuttingRecipes() {
		//APPLE
		addKnifeCuttingRecipes(new ItemStack(Item.appleRed),
				new ItemStack[] {
					new ItemStack(SCDefs.appleSlice),
					new ItemStack(SCDefs.appleSlice),
					new ItemStack(SCDefs.appleSeeds)
				});
		
		//CHERRY
		addKnifeCuttingRecipes(new ItemStack(SCDefs.cherry),
				new ItemStack[] {
					new ItemStack(SCDefs.cherrySlice),
					new ItemStack(SCDefs.cherrySlice),
					new ItemStack(SCDefs.cherrySeeds)
				});
		
		//LEMON
		addKnifeCuttingRecipes(new ItemStack(SCDefs.lemon),
				new ItemStack[] {
					new ItemStack(SCDefs.lemonSlice),
					new ItemStack(SCDefs.lemonSlice),
					new ItemStack(SCDefs.lemonSeeds)
				});
		
		//OLIVE
		addKnifeCuttingRecipes(new ItemStack(SCDefs.olive),
				new ItemStack[] {
					new ItemStack(SCDefs.oliveSlice),
					new ItemStack(SCDefs.oliveSlice),
					new ItemStack(SCDefs.oliveSeeds)
				});
	}

	//----------- Coconut -----------//	
	private static void addCoconutCuttingRecipes() {
		addAxeChoppingRecipes(new ItemStack(SCDefs.coconut),
				new ItemStack[] {
						new ItemStack(SCDefs.coconutSlice),
						new ItemStack(SCDefs.coconutSlice)
					});
	}

	//----------- Crops -----------//
	private static void addWildCarrotRecipes() {
		//WILD CARROT
		addKnifeCuttingRecipes(new ItemStack(SCDefs.wildCarrot),
				new ItemStack[] {
					new ItemStack(SCDefs.wildCarrotRoot),
					new ItemStack(SCDefs.wildCarrotTop)
				});
		
		FurnaceRecipes.smelting().addSmelting( SCDefs.wildCarrotRoot.itemID, new ItemStack( SCDefs.wildCarrotCooked ), 0 );
		
		FCRecipes.AddCauldronRecipe( new ItemStack(SCDefs.wildCarrotCooked), 
				new ItemStack[] {
						new ItemStack(SCDefs.wildCarrotRoot)
				});
	}
	
	private static void addWildOnionRecipes() {
		//ONION
		addKnifeCuttingRecipes(new ItemStack(SCDefs.wildOnion),
				new ItemStack[] {
					new ItemStack(SCDefs.wildOnionSlice),
					new ItemStack(SCDefs.wildOnionSlice),
					new ItemStack(SCDefs.wildOnionRoots)
				});
	}

	private static void addWildLettuceRecipes() {

		//LETTUCE
		addKnifeCuttingRecipes(new ItemStack(SCDefs.wildLettuce),
				new ItemStack[] {
					new ItemStack(SCDefs.wildLettuceLeaf),
					new ItemStack(SCDefs.wildLettuceLeaf),
					new ItemStack(SCDefs.wildLettuceRoots)
				});
		
		//addSimpleKnifeCuttingRecipe(SCDefs.wildLettuceHead, SCDefs.wildLettuceLeaf, 2);
	}

	private static void addSweetPotatoRecipes() {
		//SWEET POTATO
		addKnifeCuttingRecipes(new ItemStack(SCDefs.sweetPotato),
				new ItemStack[] {
					new ItemStack(SCDefs.sweetPotatoHalf),
					new ItemStack(SCDefs.sweetPotatoHalf)
				});
	}

		
	//----------- Pies -----------//	
	private static void addPiesRecipes()
	{
		//Crust
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.pieCrust, 1 ), 
				new Object[] {	    		
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour )
			} );
		
		//Pumpkin		
		for (int type = 0; type < 4; type++)
		{
			int mature = (type * 4) + 3; //only meta 3, 7, 11 and 15 which are the mature harvested pumpkins
			
			//Pies
			FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
					new Object[] {	    		
				    		new ItemStack( Item.sugar ),
				    		new ItemStack( SCDefs.pumpkinHarvested, 1, mature),
				    		new ItemStack( SCDefs.pieCrust )
				} );
			
			//piston
			FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
					new ItemStack[] {
				    		new ItemStack( Item.sugar ),
				    		new ItemStack( SCDefs.pumpkinHarvested, 1, mature),
				    		new ItemStack( SCDefs.pieCrust )
				} );
		}
		
		//Old Pumpkin
		FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, 1 ), 
				new Object[] {	    		
			    		new ItemStack( Item.sugar ),
			    		new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
			    		new ItemStack( SCDefs.pieCrust )
			} );
		
		FCRecipes.addPistonPackingRecipe(FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie,
				new ItemStack[] {
			    		new ItemStack( Item.sugar ),
			    		new ItemStack( FCBetterThanWolves.fcBlockPumpkinFresh ),
			    		new ItemStack( SCDefs.pieCrust )
			} );
		
		addPieRecipe(SCDefs.pumpkinSliceRaw, FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, Item.pumpkinPie, FCBetterThanWolves.fcUnfiredPottery, FCBlockUnfiredPottery.m_iSubtypeUncookedPumpkinPie);

		//FruitPies
		addPieRecipe(SCDefs.pumpkinSliceRaw, FCBetterThanWolves.fcItemPastryUncookedPumpkinPie, Item.pumpkinPie, SCDefs.pieRaw, SCBlockPieRaw.PUMPKIN);
		addPieRecipe(SCDefs.sweetberry, SCDefs.sweetberryPieRaw, SCDefs.sweetberryPieCooked, SCDefs.pieRaw, SCBlockPieRaw.SWEETBERRY);
		addPieRecipe(SCDefs.blueberry, SCDefs.blueberryPieRaw, SCDefs.blueberryPieCooked, SCDefs.pieRaw, SCBlockPieRaw.BLUEBERRY);
		
		addPieRecipe(SCDefs.appleSlice, SCDefs.applePieRaw, SCDefs.applePieCooked, SCDefs.fruitPieRaw, SCBlockFruitPieRaw.apple);
		addPieRecipe(SCDefs.cherrySlice, SCDefs.cherryPieRaw, SCDefs.cherryPieCooked, SCDefs.fruitPieRaw, SCBlockFruitPieRaw.cherry);
		addPieRecipe(SCDefs.lemonSlice, SCDefs.lemonPieRaw, SCDefs.lemonPieCooked, SCDefs.fruitPieRaw, SCBlockFruitPieRaw.lemon);
		
		
	}

	private static void addPieCuttingRecipes()
	{
		Item[] pies = {
			Item.pumpkinPie, SCDefs.sweetberryPieCooked, SCDefs.blueberryPieCooked,
			SCDefs.applePieCooked, SCDefs.cherryPieCooked, SCDefs.lemonPieCooked	
		};
		
		Item[] slices = {
				SCDefs.pumpkinPieSlice,SCDefs.sweetberryPieSlice,SCDefs.blueberryPieSlice,
				SCDefs.applePieSlice,SCDefs.cherryPieSlice,SCDefs.lemonPieSlice				
		};
		
		for (int type = 0; type < pies.length; type++)
		{
			addKnifeCuttingRecipes(new ItemStack(pies[type]),
					new ItemStack[] {
							new ItemStack(slices[type], 4)
					});
		}
	}
	
	//----------- Cakes -----------//
	private static void addCakeRecipes() {
		
		Item[] cakes = { SCDefs.chocolateCakeItem, SCDefs.carrotCakeItem };
		int[] subtypes = { SCBlockCakeRaw.chocolate, SCBlockCakeRaw.carrot };
		Item[] uncookedCakes = { SCDefs.chocolateCakeItemRaw, SCDefs.chocolateCakeItemRaw };
		Item[] ingredients = { FCBetterThanWolves.fcItemChocolate, Item.carrot };

		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.chocolateCakeItemRaw, 1 ), 
	        	new Object[] {
	        	new ItemStack( SCDefs.cherrySlice),
	        	new ItemStack( SCDefs.cherrySlice),
	        	new ItemStack( SCDefs.cherrySlice),
	            new ItemStack( Item.dyePowder, 1, 3 ), //cocoa powder
	            new ItemStack( Item.dyePowder, 1, 3 ), //cocoa powder
	            new ItemStack( FCBetterThanWolves.fcItemPastryUncookedCake )
	        });
		
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.carrotCakeItemRaw, 1 ), 
	        	new Object[] {
	        	new ItemStack( SCDefs.wildCarrotRoot),
	        	new ItemStack( SCDefs.wildCarrotRoot),
	            new ItemStack( FCBetterThanWolves.fcItemPastryUncookedCake )
	        });
		
		for (int type = 0; type < cakes.length; type++)
		{
			FCRecipes.addKilnRecipe(new ItemStack(cakes[type]),
					SCDefs.cakeRaw, subtypes[type]);
			
			FurnaceRecipes.smelting().addSmelting( uncookedCakes[type].itemID, new ItemStack( cakes[type] ), 0 );
		}
	}

	private static void addCakeCuttingRecipes()
	{
		Item[] cakes = {
			Item.cake, SCDefs.chocolateCakeItem, SCDefs.carrotCakeItem	
		};
		
		Item[] slices = {
				SCDefs.cakeSlice, SCDefs.chocolateCakeItem, SCDefs.carrotCakeItem		
		};
		
		for (int type = 0; type < cakes.length; type++)
		{
			addKnifeCuttingRecipes(new ItemStack(cakes[type]),
					new ItemStack[] {
							new ItemStack(slices[type], 6)
					});
		}
	}
	
	//----------- Muffin -----------//
	private static void addMuffinRecipes()
	{
		Item[] muffins = { SCDefs.itemMuffinChocolate, SCDefs.itemMuffinSweetberry, SCDefs.itemMuffinBlueberry };
		Item[] ingredients = { FCBetterThanWolves.fcItemChocolate, SCDefs.sweetberry, SCDefs.blueberry };
		Item[] uncookedMuffin = { SCDefs.itemMuffinRawChocolate, SCDefs.itemMuffinRawSweetberry, SCDefs.itemMuffinRawBlueberry };
		int[] type = { SCBlockMuffinRaw.CHOCOLATE, SCBlockMuffinRaw.SWEETBERRY, SCBlockMuffinRaw.BLUEBERRY};
		
		int index = 0;
		
		for (Item muffin : muffins)
		{
			FCRecipes.addKilnRecipe(new ItemStack(muffin, 4),
					SCDefs.muffinRaw, type[index]
			);
			
			FCRecipes.AddShapelessRecipe( new ItemStack( uncookedMuffin[index], 1 ), 
					new Object[] {
					new ItemStack( ingredients[index] ), 
					
					new ItemStack( Item.bucketMilk ),
					new ItemStack( Item.sugar ),
					new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
					
					new ItemStack( FCBetterThanWolves.fcItemFlour ),
					new ItemStack( FCBetterThanWolves.fcItemFlour )
				} );
			
			FurnaceRecipes.smelting().addSmelting( uncookedMuffin[index].itemID, new ItemStack( muffin, 4 ), 0 );
			
			index++;
		}
	}

	//----------- Cookie -----------//
	private static void addCookieRecipes()
	{
		Item[] cookies = { Item.cookie, SCDefs.cookieSweetberry, SCDefs.cookieBlueberry };
		Item[] indgredients = { FCBetterThanWolves.fcItemChocolate, SCDefs.sweetberry, SCDefs.blueberry };
		Item[] uncookedCookie = { FCBetterThanWolves.fcItemPastryUncookedCookies, SCDefs.cookieSweetberryRaw, SCDefs.cookieBlueberryRaw };
		int[] type = { SCBlockCookieRaw.chocolate, SCBlockCookieRaw.sweetberry, SCBlockCookieRaw.blueberry};
		int[] typeIAligned = { SCBlockCookieRaw.chocolateIAligned, SCBlockCookieRaw.sweetberryIAligned, SCBlockCookieRaw.blueberryIAligned};
		int index = 0;
		
		for (Item cookie : cookies)
		{
			FCRecipes.addKilnRecipe(new ItemStack(cookie, 8),
					SCDefs.cookieRaw, new int[] {
							type[index], typeIAligned[index]
							}
			);
			
			FCRecipes.AddShapelessRecipe( new ItemStack( uncookedCookie[index], 1 ), 
					new Object[] {
					new ItemStack( indgredients[index] ), 
					
					new ItemStack( Item.sugar ),
					new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
					
					new ItemStack( FCBetterThanWolves.fcItemFlour ),
					new ItemStack( FCBetterThanWolves.fcItemFlour ),
					new ItemStack( FCBetterThanWolves.fcItemFlour )
				} );
			
			FurnaceRecipes.smelting().addSmelting( uncookedCookie[index].itemID, new ItemStack( cookie, 8 ), 0 );
			
			index++;
		}		
	}

	//----------- Donut -----------//
	private static void addDonutRecipes()
	{
    	//SUGAR
    	FCRecipes.AddCauldronRecipe( 
			new ItemStack( SCDefs.donutSugar, 2 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemDonut, 2 ),
				new ItemStack( Item.sugar, 1 )
		} );
    	
    	//CHOCO
    	FCRecipes.AddCauldronRecipe( 
			new ItemStack( SCDefs.donutChocolate, 2 ), 
			new ItemStack[] {
				new ItemStack( FCBetterThanWolves.fcItemDonut, 2 ),
				new ItemStack( FCBetterThanWolves.fcItemChocolate, 1 )
		} );
	}

	//----------- Burger -----------//
	private static void addBurgerToppingsRecipes()
	{		
		//Toppings	
		addSimpleKnifeCuttingRecipe(Item.porkRaw, SCDefs.baconRaw, 3);
		addSimpleKnifeCuttingRecipe(Item.beefRaw, SCDefs.beefPattyRaw, 2);
		
//		addKnifeCuttingRecipes(new ItemStack(Item.chickenRaw),
//				new ItemStack[] {
//						new ItemStack(SCDefs.chickenDrumRaw),
//						new ItemStack(SCDefs.chickenBreastRaw)
//				});
		
		FurnaceRecipes.smelting().addSmelting( SCDefs.baconRaw.itemID, new ItemStack( SCDefs.baconCooked ), 0 );
		FurnaceRecipes.smelting().addSmelting( SCDefs.beefPattyRaw.itemID, new ItemStack( SCDefs.beefPattyCooked ), 0 );
		FurnaceRecipes.smelting().addSmelting( SCDefs.chickenDrumRaw.itemID, new ItemStack( SCDefs.chickenDrumCooked ), 0 );
	}
	
	private static void addBurgerCuttingRecipes()
	{		
		//Bun
		addKnifeCuttingRecipes(new ItemStack(SCDefs.burgerBun),
				new ItemStack[] {
						new ItemStack(SCDefs.burgerTop),
						new ItemStack(SCDefs.burgerBottom)
				});
		
		//Sandwich Bread
		addSimpleKnifeCuttingRecipe(Item.bread, SCDefs.halfBread, 2);
				
		addKnifeCuttingRecipes(new ItemStack(SCDefs.halfBread),
				new ItemStack[] {
						new ItemStack(SCDefs.sandwichTop),
						new ItemStack(SCDefs.sandwichBottom)
				});
	}
	
	private static void addBurgerDoughRecipes()
	{
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.burgerDough, 1 ), new Object[] {	    		
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemFlour ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg ),
	    		new ItemStack( FCBetterThanWolves.fcItemRawEgg )
			} );
		
		FCRecipes.addKilnRecipe(new ItemStack[] {
				new ItemStack(SCDefs.burgerBun, 2)
				
		}, SCDefs.pastryRaw, SCBlockPastryRaw.burgerBun);
		
		FCRecipes.addKilnRecipe(new ItemStack[] {
				new ItemStack(SCDefs.burgerBun, 2)
				
		}, SCDefs.pastryRaw, SCBlockPastryRaw.burgerBunIAligned);
	}
		
	
	//----------- Sunflower -----------//
	private static void addSunflowerRecipes()
	{
		FCRecipes.AddShapelessRecipe( new ItemStack( SCDefs.sunflowerSeeds, 2 ), new Object[] {	    		
	    		new ItemStack( SCDefs.sunflower )
			} );
			
		FCRecipes.addHopperFilteringRecipe(new ItemStack(SCDefs.sunflowerSeeds, 2), 
//				new ItemStack(FCBetterThanWolves.fcItemStraw), 				
				new ItemStack(SCDefs.sunflower),
				new ItemStack(FCBetterThanWolves.fcBlockWickerPane));
			
	}
	
	//----------- Rice -----------//
	private static void addRiceRecipes()
	{
		//Rice Cooking
		FCRecipes.AddCauldronRecipe( 
	    		new ItemStack( SCDefs.riceCooked, 1 ),
	    		new ItemStack[] {
	    			new ItemStack( SCDefs.rice, 2),
					new ItemStack( Item.bowlEmpty, 1 )
			} );
		
		//Bundle to Rice
		FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator( new ItemStack( SCDefs.rice, 2 ),
				new ItemStack( SCDefs.hay, 1 ),
				new Object[] {
				new ItemStack( SCDefs.riceBundle )
			} );
		
		//Hopper Filtering
		FCRecipes.addHopperFilteringRecipe(new ItemStack(SCDefs.rice, 2), 
				new ItemStack(SCDefs.hay), 
				new ItemStack(SCDefs.riceBundle),
				new ItemStack(FCBetterThanWolves.fcBlockWickerPane));
	}
	
	private static void addChickenFeedRecipes()
	{	
		Item[] seeds = {
				SCDefs.grassSeeds,
				SCDefs.redGrapeSeeds, SCDefs.whiteGrapeSeeds,
				SCDefs.hopSeeds, SCDefs.tomatoSeeds,
				SCDefs.appleSeeds, SCDefs.cherrySeeds,
				SCDefs.lemonSeeds, SCDefs.oliveSeeds,
				SCDefs.wildCarrotSeeds, SCDefs.wildLettuceSeeds, SCDefs.wildOnionSeeds,
				SCDefs.sunflowerSeeds,
				SCDefs.rice,
		};
		
		
		for (Item seed : seeds)
		{
			FCRecipes.AddShapelessRecipe( new ItemStack( FCBetterThanWolves.fcItemChickenFeed ), new Object[] {	    		
		    		new ItemStack( Item.dyePowder, 1, 15 ), // bone meal 
		    		new ItemStack( seed )
				} );
		}		
	}
}