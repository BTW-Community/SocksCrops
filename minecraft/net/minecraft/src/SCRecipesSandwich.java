package net.minecraft.src;

public class SCRecipesSandwich extends SCRecipesBurger {
	
	public static void addSandwichRecipes()
	{
		String lettuce = "salad";
		String patty = "patty";
		String cheese = "cheese";
		String egg = "egg";
		String bacon = "bacon";
		String tomato = "tomato";
		String onion = "onion";
		
		addBLTRecipe(lettuce, patty, cheese, egg, bacon, tomato, onion);
		addBaconRecipe(lettuce, patty, cheese, egg, bacon, tomato, onion);
		addBaconEggRecipe(lettuce, patty, cheese, egg, bacon, tomato, onion);
	}
	
	private static void addBaconEggRecipe(String lettuce, String patty, String cheese, String egg, String bacon,
			String tomato, String onion) {
	    addBurgerRecipe(
		    	//output
		    	new ItemStack(SCDefs.sandwichUnfinished, 1, 
		    		setToppings( new String[]
		    			{
		    					egg
		    			})),
		    	//hand
		    	new ItemStack(FCBetterThanWolves.fcItemFriedEgg), 
		    	//onBoard
		    	new ItemStack(SCDefs.sandwichBottom), 
		    	false //ejects
		);
	    
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.sandwichUnfinished, 1, 
    				setToppings( new String[]
    					{
    						egg, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.sandwichUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							egg
    						})),
    			false //ejects
    	);
	    
	    addBurgerRecipe( 
		    	//output
		    	new ItemStack(SCDefs.sandwich, 1, 
		    		setToppings( new String[]
		    			{
		    					bacon, egg
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.sandwichTop), 
		    	//onBoard
		    	new ItemStack(SCDefs.sandwichUnfinished, 1,  
		    		setToppings(new String[] 
		    				{
		    					bacon, egg
		    				})),
		    	true //ejects
	    );
		
	}
	
    private static void addBaconRecipe(String lettuce, String patty, String cheese, String egg, String bacon,
			String tomato, String onion) {
	    addBurgerRecipe(
		    	//output
		    	new ItemStack(SCDefs.sandwichUnfinished, 1, 
		    		setToppings( new String[]
		    			{
		    					bacon
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.baconCooked), 
		    	//onBoard
		    	new ItemStack(SCDefs.sandwichBottom), 
		    	false //ejects
		);
	    
	    addBurgerRecipe( 
		    	//output
		    	new ItemStack(SCDefs.sandwich, 1, 
		    		setToppings( new String[]
		    			{
		    					bacon
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.sandwichTop), 
		    	//onBoard
		    	new ItemStack(SCDefs.sandwichUnfinished, 1,  
		    		setToppings(new String[] 
		    				{
		    					bacon
		    				})),
		    	true //ejects
	    );
		
	}

	private static void addBLTRecipe(String lettuce, String patty, String cheese, String egg, String bacon, String tomato, String onion) {
    	
	    addBurgerRecipe(
	    	//output
	    	new ItemStack(SCDefs.sandwichUnfinished, 1, 
	    		setToppings( new String[]
	    			{
	    				lettuce
	    			})),
	    	//hand
	    	new ItemStack(SCDefs.wildLettuceLeaf), 
	    	//onBoard
	    	new ItemStack(SCDefs.sandwichBottom), 
	    	false //ejects
	    );
	    
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.sandwichUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, bacon
    					})),
    			//hand
    			new ItemStack(SCDefs.baconCooked), 
    			//onBoard
    			new ItemStack(SCDefs.sandwichUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce
    						})),
    			false //ejects
    	);
    	
    	addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.sandwichUnfinished, 1, 
    				setToppings( new String[]
    					{
    						lettuce, bacon, tomato
    					})),
    			//hand
    			new ItemStack(SCDefs.wildOnionSlice), 
    			//onBoard
    			new ItemStack(SCDefs.sandwichUnfinished, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, bacon
    						})),
    			false //ejects
    	);
    	
	    addBurgerRecipe( 
		    	//output
		    	new ItemStack(SCDefs.sandwich, 1, 
		    		setToppings( new String[]
		    			{
		    				lettuce, bacon, tomato 
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.burgerTop), 
		    	//onBoard
		    	new ItemStack(SCDefs.sandwichUnfinished, 1,  
		    		setToppings(new String[] 
		    				{
		    					lettuce, bacon, tomato 
		    				})),
		    	true //ejects
	    );
    }
}
