package net.minecraft.src;

public class SCRecipesBurger extends SCRecipes
{	
	public static void addBurgerRecipes()
	{
		String salad = "salad";
		String patty = "patty";
		String cheese = "cheese";
		String egg = "egg";
		String bacon = "bacon";
		String tomato = "tomato";
		String onion = "onion";
		
		addHamburgerRecipes(salad, patty, cheese, egg, bacon, tomato, onion);		
		addLettuceBurgerRecipes(salad, patty, cheese, egg, bacon, tomato, onion);
		
		addEggBurgerRecipest(salad, patty, cheese, egg, bacon, tomato, onion);		
		addLettuceEggBurgerRecipes(salad, patty, cheese, egg, bacon, tomato, onion);

		addFishburgerRecipes(salad, patty, cheese, egg, bacon, tomato, onion);		
		addLettuceFishburgerRecipes(salad, patty, cheese, egg, bacon, tomato, onion);		
	}
	
	//----------- Helper -----------//
	
	public static int setToppings(String[] contents)
	{
		int[] contains = new int[7];
		
		for (String i : contents)
		{
			if (i == "salad") contains[0] =  setBurgerContents(1, 0, 0, 0, 0, 0, 0); 
			if (i == "patty") contains[1] =  setBurgerContents(0, 1, 0, 0, 0, 0, 0);
			if (i == "cheese") contains[2] = setBurgerContents(0, 0, 1, 0, 0, 0, 0);
			if (i == "egg") contains[3] =    setBurgerContents(0, 0, 0, 1, 0, 0, 0);
			if (i == "bacon") contains[4] =  setBurgerContents(0, 0, 0, 0, 1, 0, 0);
			if (i == "tomato") contains[5] = setBurgerContents(0, 0, 0, 0, 0, 1, 0);
			if (i == "onion") contains[6] =  setBurgerContents(0, 0, 0, 0, 0, 0, 1);
		}
						
		return contains[6] | contains[5] | contains [4] | contains[3] | contains[2] | contains[1] | contains[0];
	}
	
	public static int setBurgerContents(int salad, int patty, int cheese, int egg, int bacon, int tomato, int onion)
	{
		return onion << 6 | tomato << 5 | bacon << 4 | egg << 3 | cheese << 2 | patty << 1 | salad;
	}
	
	//----------- Recipes -----------//
	
    private static void addEggBurgerRecipest(String salad, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
		//egg
		addBurgerRecipe( 
				//output
				new ItemStack(SCDefs.burgerUnfinished, 1, 
					setToppings( new String[]
						{
							patty, egg
						})),
				//hand
				new ItemStack(FCBetterThanWolves.fcItemFriedEgg), 
				//onBoard
				new ItemStack(SCDefs.burgerUnfinished, 1,  
					setToppings(new String[] 
							{
								patty
							})),
				false //ejects
		);
		
		addBurgerRecipe( 
				//output
				new ItemStack(SCDefs.burger, 1, 
					setToppings( new String[]
						{
							patty, egg
						})),
				//hand
				new ItemStack(SCDefs.burgerTop), 
				//onBoard
				new ItemStack(SCDefs.burgerUnfinished, 1,  
					setToppings(new String[] 
							{
								patty, egg
							})),
				true //ejects
		);
		
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato, egg
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato, egg
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato, onion, egg
    						})),
    			true //ejects
    	);
		
	}
    
    private static void addLettuceEggBurgerRecipes(String lettuce, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
		//egg
		addBurgerRecipe( 
				//output
				new ItemStack(SCDefs.burgerUnfinished, 1, 
					setToppings( new String[]
						{
							lettuce, patty, egg
						})),
				//hand
				new ItemStack(FCBetterThanWolves.fcItemFriedEgg), 
				//onBoard
				new ItemStack(SCDefs.burgerUnfinished, 1,  
					setToppings(new String[] 
							{
								lettuce, patty
							})),
				false //ejects
		);
		
		addBurgerRecipe( 
				//output
				new ItemStack(SCDefs.burger, 1, 
					setToppings( new String[]
						{
							lettuce, patty, egg
						})),
				//hand
				new ItemStack(SCDefs.burgerTop), 
				//onBoard
				new ItemStack(SCDefs.burgerUnfinished, 1,  
					setToppings(new String[] 
							{
								lettuce, patty, egg
							})),
				true //ejects
		);
		
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato, egg
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, onion, egg
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato, egg
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato, egg
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion, egg
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato, onion, egg
    						})),
    			true //ejects
    	);
		
	}
    
    private static void addLettuceBurgerRecipes(String lettuce, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
    	
	    addBurgerRecipe(
		    	//output
		    	new ItemStack(SCDefs.burgerUnfinished, 1, 
		    		setToppings( new String[]
		    			{
		    				lettuce
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.wildLettuceLeaf), 
		    	//onBoard
		    	new ItemStack(SCDefs.burgerBottom), 
		    	false //ejects
		    );
	    
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty
    					})),
    			//hand
    			new ItemStack(SCDefs.beefPattyCooked), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, onion
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato, onion
    						})),
    			true //ejects
    	);
    	
    }
	
    private static void addHamburgerRecipes(String salad, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
    	//Hamburger
	    addBurgerRecipe(
	    	//output
	    	new ItemStack(SCDefs.burgerUnfinished, 1, 
	    		setToppings( new String[]
	    			{
	    				patty
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.beefPattyCooked), 
	    	//onBoard
	    	new ItemStack(SCDefs.burgerBottom), 
	    	false //ejects
	    );
	    
	    addBurgerRecipe( 
	    	//output
	    	new ItemStack(SCDefs.burger, 1, 
	    		setToppings( new String[]
	    			{
	    				patty 
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.burgerTop), 
	    	//onBoard
	    	new ItemStack(SCDefs.burgerUnfinished, 1,  
	    		setToppings(new String[] 
	    				{
	    					patty 
	    				})),
	    	true //ejects
	    );
    			
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, onion
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.burger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.burgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato, onion
    						})),
    			true //ejects
    	);
    }
	
	private static void addFishburgerRecipes(String salad, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
    	//Hamburger
	    addBurgerRecipe(
	    	//output
	    	new ItemStack(SCDefs.fishburgerUnfinished, 1, 
	    		setToppings( new String[]
	    			{
	    				patty
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.fishFiletDeepFried), 
	    	//onBoard
	    	new ItemStack(SCDefs.burgerBottom), 
	    	false //ejects
	    );
	    
	    addBurgerRecipe( 
	    	//output
	    	new ItemStack(SCDefs.fishburger, 1, 
	    		setToppings( new String[]
	    			{
	    				patty 
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.burgerTop), 
	    	//onBoard
	    	new ItemStack(SCDefs.fishburgerUnfinished, 1,  
	    		setToppings(new String[] 
	    				{
	    					patty 
	    				})),
	    	true //ejects
	    );
    			
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, onion
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, tomato, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							patty, bacon, tomato, onion
    						})),
    			true //ejects
    	);
    }
	
	private static void addLettuceFishburgerRecipes(String lettuce, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
    	//Hamburger
	    addBurgerRecipe(
	    	//output
	    	new ItemStack(SCDefs.fishburgerUnfinished, 1, 
	    		setToppings( new String[]
	    			{
	    				lettuce
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.wildLettuceLeaf), 
	    	//onBoard
	    	new ItemStack(SCDefs.burgerBottom), 
	    	false //ejects
	    );
	    
	    addBurgerRecipe( 
		    	//output
		    	new ItemStack(SCDefs.fishburgerUnfinished, 1, 
		    		setToppings( new String[]
		    			{
		    				lettuce, patty 
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.fishFiletDeepFried), 
		    	//onBoard
		    	new ItemStack(SCDefs.fishburgerUnfinished, 1,  
		    		setToppings(new String[] 
		    				{
		    					lettuce
		    				})),
		    	false //ejects
		    );
	    
	    addBurgerRecipe( 
	    	//output
	    	new ItemStack(SCDefs.fishburger, 1, 
	    		setToppings( new String[]
	    			{
	    				lettuce, patty 
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.burgerTop), 
	    	//onBoard
	    	new ItemStack(SCDefs.fishburgerUnfinished, 1,  
	    		setToppings(new String[] 
	    				{
	    					lettuce, patty 
	    				})),
	    	true //ejects
	    );
    			
    	//onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, onion
    						})),
    			true //ejects
    	);
    			
    	//tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato
    						})),
    			true //ejects
    	);
    			
    	//tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, tomato, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			true //ejects
    	);
    			
    	//bacon&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, onion
    						})),
    			true //ejects
    	);
    			
    	//bacon&tomato
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.tomatoSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato
    						})),
    			true //ejects
    	);
    	
    	//bacon&tomato&onion
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburgerUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.fishburger, 1, 
    				setToppings( new String[]
    					{
    						lettuce, patty, bacon, tomato, onion
    					})),
    			//hand
    			new ItemStack(SCDefs.burgerTop), 
    			//onBoard
    			new ItemStack(SCDefs.fishburgerUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, patty, bacon, tomato, onion
    						})),
    			true //ejects
    	);
    }

}
