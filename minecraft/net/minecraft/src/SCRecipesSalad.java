package net.minecraft.src;

public class SCRecipesSalad extends SCRecipesBurger {

	public static void addSaladRecipes() {
		String lettuce = "lettuce";
		String chicken = "chicken";
		String carrot = "carrot";
		String wildCarrot = "wildCarrot";
		String bread = "bread";
		String tomato = "tomato";
		String mushroom = "mushroom";
		String bacon = "bacon";
		String onion = "onion";
		
		addChickenCaesarRecipe(lettuce, chicken, carrot, wildCarrot, bread, tomato, mushroom, bacon, onion);
	}

	private static void addChickenCaesarRecipe(String lettuce, String chicken, String carrot, String wildCarrot, String bread,
			String tomato, String mushroom, String bacon, String onion) {
		
		
		baseSalad(lettuce);		
		chickenCaesarSalad(lettuce, chicken, bread);
		
	}

	protected static void chickenCaesarSalad(String lettuce, String chicken, String bread) {
		//Chicken
		addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.salad, 1, 
    				setToppings( new String[]
    					{
    						lettuce, chicken
    					})),
    			//hand
    			new ItemStack(Item.chickenCooked), 
    			//onBoard
    			new ItemStack(SCDefs.salad, 1,  
    				setToppings(new String[] 
    						{
    							lettuce
    						})),
    			false //ejects
    	);
		
		addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.salad, 1, 
    				setToppings( new String[]
    					{
    						lettuce, chicken
    					})),
    			//hand
    			new ItemStack(SCDefs.chickenDrumCooked), 
    			//onBoard
    			new ItemStack(SCDefs.salad, 1,  
    				setToppings(new String[] 
    						{
    							lettuce
    						})),
    			false //ejects
    	);
		
		//Bread
		addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.salad, 1, 
    				setToppings( new String[]
    					{
    						lettuce, chicken, bread
    					})),
    			//hand
    			new ItemStack(Item.bread), 
    			//onBoard
    			new ItemStack(SCDefs.salad, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, chicken
    						})),
    			true //ejects
    	);
		
		addBurgerRecipe( 
    			//output
    			new ItemStack(SCDefs.salad, 1, 
    				setToppings( new String[]
    					{
    						lettuce, chicken, bread
    					})),
    			//hand
    			new ItemStack(SCDefs.halfBread), 
    			//onBoard
    			new ItemStack(SCDefs.salad, 1,  
    				setToppings(new String[] 
    						{
    							lettuce, chicken
    						})),
    			true //ejects
    	);
	}

	protected static void baseSalad(String lettuce) {
		addBurgerRecipe(
		    	//output
		    	new ItemStack(SCDefs.salad, 1, 
		    		setToppings( new String[]
		    			{
		    					lettuce
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.wildLettuceHead), 
		    	//onBoard
		    	new ItemStack(Item.bowlEmpty), 
		    	false //ejects	    	
		);
		
		addBurgerRecipe(
		    	//output
		    	new ItemStack(SCDefs.salad, 1, 
		    		setToppings( new String[]
		    			{
		    					lettuce
		    			})),
		    	//hand
		    	new ItemStack(SCDefs.wildLettuceLeaf), 
		    	//onBoard
		    	new ItemStack(Item.bowlEmpty),
		    	false //ejects	    	
		);
	}
	
	public static int setToppings(String[] contents)
	{
		int[] contains = new int[9];
		
		for (String i : contents)
		{
			if (i == "lettuce") contains[0] =		setBurgerContents(1, 0, 0, 0, 0, 0, 0, 0, 0); 
			if (i == "chicken") contains[1] =		setBurgerContents(0, 1, 0, 0, 0, 0, 0, 0, 0);
			if (i == "carrot") contains[2] =		setBurgerContents(0, 0, 1, 0, 0, 0, 0, 0, 0);
			if (i == "wildCarrot") contains[3] =	setBurgerContents(0, 0, 0, 1, 0, 0, 0, 0, 0);
			if (i == "bread") contains[4] =			setBurgerContents(0, 0, 0, 0, 1, 0, 0, 0, 0);
			if (i == "tomato") contains[5] =		setBurgerContents(0, 0, 0, 0, 0, 1, 0, 0, 0);
			if (i == "mushroom") contains[6] =		setBurgerContents(0, 0, 0, 0, 0, 0, 1, 0, 0);
			if (i == "bacon") contains[7] =			setBurgerContents(0, 0, 0, 0, 0, 0, 0, 1, 0);
			if (i == "onion") contains[8] =			setBurgerContents(0, 0, 0, 0, 0, 0, 0, 0, 1);
		}
						
		return contains[8] | contains[7] | contains[6] | contains[5] | contains [4] | contains[3] | contains[2] | contains[1] | contains[0];
	}
	
	public static int setBurgerContents(int salad, int chicken, int carrot, int wildCarrot, int bread, int tomato, int mushroom, int bacon, int onion)
	{
		return onion << 8 | bacon << 7 | mushroom << 6 | tomato << 5 | bread << 4 | wildCarrot << 3 | carrot << 2 | chicken << 1 | salad;
	}
	

}
