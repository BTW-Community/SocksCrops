package net.minecraft.src;

public class SCItemFoodRaw extends SCItemFood {
	public SCItemFoodRaw(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName);
		
		SetStandardFoodPoisoningEffect();
	}
	
	public SCItemFoodRaw(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName, boolean doZombiesConsume) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName, doZombiesConsume);
		
		SetStandardFoodPoisoningEffect();
	}
}
