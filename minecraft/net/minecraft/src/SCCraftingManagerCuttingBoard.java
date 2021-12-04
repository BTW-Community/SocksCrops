package net.minecraft.src;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SCCraftingManagerCuttingBoard extends FCCraftingManagerBulk
{
	public static SCCraftingManagerCuttingBoard instance = new SCCraftingManagerCuttingBoard();
	
	private static final int m_iIgnoreMetadata = FCUtilsInventory.m_iIgnoreMetadata;
	
    public static final SCCraftingManagerCuttingBoard getInstance()
    {
        return instance;
    }
	
    private SCCraftingManagerCuttingBoard()
    {
    	super();
		    	
    }
}