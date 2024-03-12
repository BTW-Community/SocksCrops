package net.minecraft.src;

public class SCRecipeHelper {
	
	public static final int ignoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
	
	//----------- Knives -----------//
	public static void addKnifeRecipe(Item knife, Item ingot, Item stick, Item nugget, int amount)
	{
		FCRecipes.AddRecipe( new ItemStack (knife),
				new Object[] {
						"I ", 
						" S", 
						'I', new ItemStack( ingot, 1), 
						'S', new ItemStack( stick, 1 )
				});
		
		FCRecipes.AddRecipe( new ItemStack (knife),
				new Object[] {
						" I", 
						"S ", 
						'I', new ItemStack( ingot, 1), 
						'S', new ItemStack( stick, 1 )
				});
		
		//smelting
		FCRecipes.AddStokedCrucibleRecipe( new ItemStack( nugget, amount ), 
				new ItemStack[] {
					new ItemStack( knife, 1, ignoreMetadata )
				} );
	}
	
	//----------- Gourds -----------//
	public static void addGourdSliceRecipe(Block gourd, int meta, Item slice)
	{
		addAxeChoppingRecipes(new ItemStack(gourd, 1, meta),
				new ItemStack[] {
						new ItemStack(slice),
						new ItemStack(slice),
						new ItemStack(slice),
						new ItemStack(slice)
					});
	}

	//----------- Pie -----------//
	
	public static void addPieRecipe(Item fruit, Item rawPie, Item cookedPie, Block pie, int subtype) 
	{
		//Crafting
		FCRecipes.AddShapelessRecipe( new ItemStack( rawPie, 1 ), 
				new Object[] {	    		
			    		new ItemStack( Item.sugar ),
			    		new ItemStack( fruit ),
			    		new ItemStack( fruit ),
			    		new ItemStack( fruit ),
			    		new ItemStack( fruit ),
			    		new ItemStack( SCDefs.pieCrust )
			} );
		
		FCRecipes.addPistonPackingRecipe(pie, subtype,
				new ItemStack[] {	    		
			    		new ItemStack( Item.sugar ),
			    		new ItemStack( fruit, 4),
			    		new ItemStack( SCDefs.pieCrust )
			} );
		
		//Cooking
		FurnaceRecipes.smelting().addSmelting( rawPie.itemID, new ItemStack( cookedPie ), 0 );
		
		FCRecipes.addKilnRecipe( new ItemStack(cookedPie), pie, subtype );
	}
	
	//----------- Crate -----------//

	public static void emptyCrateRecipes(int wood, int input, Item fruit)
	{
		for (int fill = 0; fill < 8; fill++)
		{
			FCRecipes.AddShapelessRecipeWithSecondaryOutputIndicator(
					new ItemStack( fruit, fill + 1),
					new ItemStack(SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, SCBlockCrate.EMPTY)),
					new Object[] {	    		
		    		new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, fill, input) )
				} );	
		}
	}
	
	public static void addToCrateRecipes(int wood, Item fruit, int crate, int inputFill, int countAdded) {
		if (countAdded == 1)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 2)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}		
		else if (countAdded == 3)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 4)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 5)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 6)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 7)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
		else if (countAdded == 8)
		{
			FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill + countAdded, crate)),
					new Object[] {
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( fruit),
							new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, inputFill, crate))
					});
		}
	}

	public static void fillCrateRecipes(int wood, int inputCrate, Item fruit, int outputCrate)
	{
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 1, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 2, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 3, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 4, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 5, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 6, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
		
		FCRecipes.AddShapelessRecipe(new ItemStack (SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 7, outputCrate)),
				new Object[] {
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( fruit),
						new ItemStack( SCDefs.crate, 1, SCBlockCrate.dataToDamage(wood, 0, inputCrate))
				});
	}
    
	//----------- CuttingBoard -----------//
	
	public static void addSimpleKnifeCuttingRecipe(Item input, Item output, int outputAmount)
	{
		ItemStack[] returnStack = new ItemStack[outputAmount];
		
		for (int i = 0; i < returnStack.length; i++)
		{
			returnStack[i] = new ItemStack(output);
		}
		
		addKnifeCuttingRecipes(new ItemStack(input),
				returnStack
		);
	}
	
	public static void addSimpleKnifeCuttingRecipe(Block input, Item output, int outputAmount)
	{
		ItemStack[] returnStack = new ItemStack[outputAmount];
		
		for (int i = 0; i < returnStack.length; i++)
		{
			returnStack[i] = new ItemStack(output);
		}
		
		addKnifeCuttingRecipes(new ItemStack(input),
				returnStack
		);
	}
	
	public static void addKnifeCuttingRecipes( ItemStack input, ItemStack[] output)
	{
		Item[] tools = { SCDefs.knifeStone, SCDefs.knifeIron, SCDefs.knifeGold, SCDefs.knifeDiamond, SCDefs.knifeSteel };
		
		for (Item tool : tools)
		{
			addChoppingBoardRecipe(
					output,				
					new ItemStack(tool, 1, ignoreMetadata), //hand
					input
			);
		}
	}
    
	public static void addAxeChoppingRecipes( ItemStack input, ItemStack[] output)
	{
		Item[] tools = { Item.axeStone, Item.axeIron, Item.axeDiamond };
		
		for (Item tool : tools)
		{			
			addChoppingBoardRecipe(
					output,				
					new ItemStack(tool, 1, ignoreMetadata), //hand
					input
			);
		}
	}
	
	public static void addSimpleAxeChoppingRecipe(Item input, Item output, int outputAmount)
	{
		ItemStack[] returnStack = new ItemStack[outputAmount];
		
		for (int i = 0; i < returnStack.length; i++)
		{
			returnStack[i] = new ItemStack(output);
		}
		
		addAxeChoppingRecipes(new ItemStack(input),
				returnStack
		);
	}
	
	public static void addSimpleAxeChoppingRecipe(Block input, Item output, int outputAmount)
	{
		ItemStack[] returnStack = new ItemStack[outputAmount];
		
		for (int i = 0; i < returnStack.length; i++)
		{
			returnStack[i] = new ItemStack(output);
		}
		
		addAxeChoppingRecipes(new ItemStack(input),
				returnStack
		);
	}
	
	public static void addChiselCarvingRecipes( ItemStack input, ItemStack[] output)
	{	
		Item[] tools = { FCBetterThanWolves.fcItemChiselIron, FCBetterThanWolves.fcItemChiselDiamond };
		
		for (Item tool : tools)
		{
			addChoppingBoardRecipe(
					output,				
					new ItemStack(tool, 1, ignoreMetadata), //hand
					input
			);
		}
	}
	
    /**
     * Note that choppingBoard recipe inputs are limited to stack sizes of 1 (which is enforced upon adding the recipe)
     * @param choppingOutput The cut items
     * @param onBoardStack The item to be cut
     * @param heldStack The tool used for cutting
     */
    public static void addChoppingBoardRecipe( ItemStack[] choppingOutput, ItemStack heldStack, ItemStack onBoardStack )
    {
    	SCCraftingManagerChoppingBoardFilter.instance.addRecipe(choppingOutput, heldStack, onBoardStack);
    }
    
    public static void addBurgerRecipe( ItemStack output, ItemStack heldStack, ItemStack onBoardStack, boolean ejects )
    {
    	SCCraftingManagerBurgerFilter.instance.addRecipe(output, heldStack, onBoardStack, ejects);
    }
    
	//--- MIXER ---//
    
    public static void AddMixerRecipe( ItemStack outputStack, ItemStack inputStacks[], float timeToMixMultiplier )
    {
    	SCCraftingManagerMixerNormal.getInstance().AddRecipe( outputStack, inputStacks, false, timeToMixMultiplier );
	}
    
    public static void AddMixerRecipe( ItemStack outputStack, ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerNormal.getInstance().AddRecipe( outputStack, inputStacks );
	}
    
    public static void AddMixerRecipe( ItemStack outputStacks[], ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerNormal.getInstance().AddRecipe( outputStacks, inputStacks );
	}
    
    public static void AddMixerRecipeFire( ItemStack outputStack, ItemStack inputStacks[], float timeToMixMultiplier )
    {
    	SCCraftingManagerMixerFire.getInstance().AddRecipe( outputStack, inputStacks, false, timeToMixMultiplier );
	}
    
    public static void AddMixerRecipeFire( ItemStack outputStack, ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerFire.getInstance().AddRecipe( outputStack, inputStacks );
	}
    
    public static void AddMixerRecipeFire( ItemStack outputStacks[], ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerFire.getInstance().AddRecipe( outputStacks, inputStacks );
	}
    
    public static void AddMixerRecipeStoked( ItemStack outputStack, ItemStack inputStacks[], float timeToMixMultiplier )
    {
    	SCCraftingManagerMixerStoked.getInstance().AddRecipe( outputStack, inputStacks, false, timeToMixMultiplier );
	}
    
    public static void AddMixerRecipeStoked( ItemStack outputStack, ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerStoked.getInstance().AddRecipe( outputStack, inputStacks );
	}
    
    public static void AddMixerRecipeStoked( ItemStack outputStacks[], ItemStack inputStacks[] )
    {
    	SCCraftingManagerMixerStoked.getInstance().AddRecipe( outputStacks, inputStacks );
	}
    
    //--- JUICER ---//
    
//    public static void AddJuicerRecipe( ItemStack outputStack, ItemStack inputStacks[], int amount)
//    {
//    	SCCraftingManagerJuicer.getInstance().AddRecipe( outputStack, inputStacks, false, amount);
//	}
    
    public static void AddJuicerRecipe( ItemStack outputStack, ItemStack inputStack, int amount )
    {
    	SCCraftingManagerJuicer.getInstance().AddRecipe( outputStack, inputStack, false, amount);
	}
    
//    public static void AddJuicerRecipe( ItemStack outputStacks[], ItemStack inputStacks[], int amount )
//    {
//    	SCCraftingManagerJuicer.getInstance().AddRecipe( outputStacks, inputStacks, false, amount );
//	}
    
    /**
	 * Note that pan recipe inputs are limited to stack sizes of 1 (which is
	 * enforced upon adding the recipe)
	 * 
	 * @param output         The cooked item
	 * @param burnedResult	 The burned item
	 * @param input          The raw item that should be cooked
	 */
	public static void addPanCookingRecipe(ItemStack output, ItemStack burnedResult, ItemStack input)
	{
		SCCraftingManagerPanCooking.instance.addRecipe(output, burnedResult, input);
	}
	
	public static void addRopeDryingRecipe(ItemStack output, ItemStack input)
	{
		SCCraftingManagerRopeDrying.instance.addRecipe(output, input);
	}
}