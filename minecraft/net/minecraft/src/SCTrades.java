package net.minecraft.src;

public class SCTrades {
	
	private static int farmer = FCEntityVillager.professionIDFarmer;
	
	public static void addTrades()
	{
		addGourdTrades();
		
		addFruitTreeTrades();
	}

	private static void addGourdTrades()
	{
		int matureGourd = 3;
		FCEntityVillager.removeTradeToBuy(farmer, Block.melon.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.melonHarvested.blockID, matureGourd, 8, 10, 1F, 3);

		FCEntityVillager.removeTradeToBuy(farmer, FCBetterThanWolves.fcBlockPumpkinFresh.blockID, 0);
		FCEntityVillager.addTradeToBuyMultipleItems(farmer, SCDefs.pumpkinHarvested.blockID, matureGourd, 10, 16, 1F, 3);
		
		FCEntityVillager.addItemConversionTrade(farmer, Item.pumpkinSeeds.itemID, 6, 8, SCDefs.domesticatedPumpkinSeeds.itemID, 0.25F, 5);
		FCEntityVillager.addItemConversionTrade(farmer, Item.melonSeeds.itemID, 6, 8, SCDefs.domesticatedMelonSeeds.itemID, 0.25F, 5);
		
//		FCEntityVillager.addComplexTrade(
//				farmer,
//				Item.pumpkinSeeds.itemID, 0, 4, 4,
//				Item.emerald.itemID, 0, 4, 8,
//				SCDefs.domesticatedPumpkinSeeds.itemID, 0, 4, 4,
//				0.25F, 4
//		);
//		
//		FCEntityVillager.addComplexTrade(
//				farmer,
//				Item.melonSeeds.itemID, 0, 4, 4,
//				Item.emerald.itemID, 0, 4, 8,
//				SCDefs.domesticatedMelonSeeds.itemID, 0, 4, 4,
//				0.25F, 4
//		);
	
	}
	
	private static void addFruitTreeTrades()
	{
//		FCEntityVillager.addTradeToSellMultipleItems(farmer, Item.appleRed.itemID, 2, 4, 0.5F, 2);
		FCEntityVillager.addTradeToSellMultipleItems(farmer, SCDefs.cherry.itemID, 2, 4, 0.5F, 2);
		FCEntityVillager.addTradeToSellMultipleItems(farmer, SCDefs.lemon.itemID, 2, 4, 0.5F, 2);
		FCEntityVillager.addTradeToSellMultipleItems(farmer, SCDefs.olive.itemID, 2, 4, 0.5F, 2);		
	}
}
