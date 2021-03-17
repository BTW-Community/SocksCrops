package net.minecraft.src;

public class SocksCropsDefs {
	//assign ID number
	private static int
		id_bowl = 4000;
		//end 4096
	private static int
		id_knifeStone = 31000;
		//end 32000
			
	//add block
	public static Block bowl;
	//add item
	public static Item knifeStone;
	
	//definitions
	public static void addDefinitions() {
		bowl = new SocksCropsBlockBowl(id_bowl);
		Item.itemsList[bowl.blockID] = new ItemBlock(bowl.blockID - 256);		
				
		knifeStone = new SocksCropsItemKnifeStone(id_knifeStone);
		Item.itemsList[knifeStone.itemID] = new Item(knifeStone.itemID);
		
	}
} 