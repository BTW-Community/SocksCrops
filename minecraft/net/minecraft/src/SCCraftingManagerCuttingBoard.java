package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;

public class SCCraftingManagerCuttingBoard extends FCCraftingManagerBulk
{
	public static SCCraftingManagerCuttingBoard instance = new SCCraftingManagerCuttingBoard();

    
    public static final SCCraftingManagerCuttingBoard getInstance()
    {
        return instance;
    }
	
    private SCCraftingManagerCuttingBoard()
    {
    	super();    	
    }
}