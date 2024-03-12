package net.minecraft.src;

import java.util.ArrayList;

public class SCUtilsLiquids {
    
	public static ArrayList<ItemStack> fruitList = new ArrayList<>();
	public static ArrayList<ItemStack> liquidList = new ArrayList<>();
	public static ArrayList<ItemStack> bottleList = new ArrayList<>();
	public static ArrayList<ItemStack> bucketList = new ArrayList<>();
	public static ArrayList<Integer> colorList = new ArrayList<>();
	public static ArrayList<String> namesList = new ArrayList<>();
	
	public static final int WATER = 0;
	
	public static final int MILK = 1;
	public static final int CHOCOLATE_MILK = 2;
	public static final int COCONUT_MILK = 3;
	
	public static final int APPLE_JUICE = 4;
	public static final int CHERRY_JUICE = 5;
	public static final int LEMON_JUICE = 6;
	
	public static final int OLIVE_OIL = 7;
	public static final int SUNFLOWER_OIL = 8;
	
	public static final int TOTAL_LIQUIDS = 9;
	
//    public static void addLiquid(Block liquid, int liquidMeta, Item bottle, int bottleMeta, Item bucket, int bucketMeta)
//    {
//    	addLiquid(liquid, liquidMeta, bottle, bottleMeta, bucket, bucketMeta, null);
//    }
    
    public static void addLiquid(Item fruit, int fruitMeta, Block liquid, int liquidMeta, Item bottle, int bottleMeta, Item bucket, int bucketMeta, int color, String name)
    {
    	fruitList.add(new ItemStack(fruit, 1, fruitMeta));
    	liquidList.add(new ItemStack(liquid, 1, liquidMeta));
    	bottleList.add(new ItemStack(bottle, 1, bottleMeta));
    	bucketList.add(new ItemStack(bucket, 1, bucketMeta));
    	colorList.add(color);
    	namesList.add(name);
    }
    
    public static boolean doListscontain(ItemStack stack)
    {
    	for (int i=0; i < liquidList.size(); i++)
    	{
    		if (stack.isItemEqual(liquidList.get(i))) return true;
        	if (stack.isItemEqual(bottleList.get(i))) return true;
        	if (stack.isItemEqual(bucketList.get(i))) return true;
    	} 	
    	
    	return false;
    }
    
    public static boolean isValidLiquidBlock(ItemStack stack)
    {
    	for (int i=0; i < liquidList.size(); i++)
    	{
    		if (stack.isItemEqual(liquidList.get(i))) return true;
    	} 	
    	
    	return false;
    }
    
    public static int getIndexValidLiquidBlock(int blockID)
    {
    	for (ItemStack liquid : liquidList)
  	   	{
    		if (liquid.itemID == blockID)
    		{
    			return liquidList.indexOf(liquid);
    		}
  	   	}

    	return -1;
    }
    
    public static ItemStack getBottleFromLiquidBlock(ItemStack stack)
    {
 	   for (ItemStack liquid : liquidList)
 	   {
 		   if (liquid.isItemEqual(stack)) 
 		   {
 			   int index = liquidList.indexOf(liquid);
 			   return bottleList.get(index);
 		   }
 	   }
 	   
 	   return null;
    }
    
    public static ItemStack getBucketFromLiquidBlock(ItemStack stack)
    {
 	   for (ItemStack validLiquid : liquidList)
 	   {
 		   if (validLiquid.isItemEqual(stack)) 
 		   {
 			   int index = liquidList.indexOf(validLiquid);
 			   return bucketList.get(index);
 		   }
 	   }
 	   
 	   return null;
    }
    
    public static ItemStack getLiquidBlockFromBottle(ItemStack stack)
    {
 	   for (ItemStack validBottle : bottleList)
 	   {
 		   if (validBottle.isItemEqual(stack)) 
 		   {
 			   int index = bottleList.indexOf(validBottle);
 			   return liquidList.get(index);
 		   }
 	   }
 	   
 	   return null;
    }
    
    public static ItemStack getLiquidBlockFromBucket(ItemStack stack)
    {
 	   for (ItemStack validBucket : bucketList)
 	   {
 		   if (validBucket.isItemEqual(stack)) 
 		   {
 			   int index = bucketList.indexOf(validBucket);
 			   return liquidList.get(index);
 		   }
 	   }
 	   
 	   return null;
    }    
    
    public static int getColorFromFruit(ItemStack stack)
    {
 	   for (ItemStack fruit : fruitList)
 	   {
 		   if (fruit.isItemEqual(stack)) 
 		   {
 			   int index = fruitList.indexOf(fruit);
 			   return colorList.get(index);
 		   }
 	   }
 	   
 	   return 0x000000;
    }
}
