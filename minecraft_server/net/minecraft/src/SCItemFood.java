package net.minecraft.src;

public class SCItemFood extends FCItemFood {

	//all SC Foods have High Res food values
	static public final float CAKE_SATURATION_MODIFIER = 4.0F;
	static public final float PIE_SATURATION_MODIFIER = 2.5F;
	static public final float COOKIE_SATURATION_MODIFIER = 1F;
	static public final float FATTY_SATURATION_MODIFIER = 0.5F;
	static public final float PROTEIN_SATURATION_MODIFIER = 0.25F;
	static public final float VEGS_SATURATION_MODIFIER = 0.0F;
	static public final float BREAD_SATURATION_MODIFIER = 0.0F;
	static public final float SWEET_SATURATION_MODIFIER = 0.125F;

	//3 == 1, 6 == 2, 9 == 3, etc..
	static public final int appleHungerHealed = 4;
	static public final float appleSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int appleSliceHungerHealed = 2;
	static public final float appleSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int cherryHungerHealed = 4;
	static public final float cherrySaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int cherrySliceHungerHealed = 2;
	static public final float cherrySliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int lemonHungerHealed = 4;
	static public final float lemonSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int lemonSliceHungerHealed = 2;
	static public final float lemonSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int oliveHungerHealed = 4;
	static public final float oliveSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int oliveSliceHungerHealed = 2;
	static public final float oliveSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int melonSliceHungerHealed = 2;
	static public final float melonSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int pumpkinSliceHungerHealed = 2;
	static public final float pumpkinSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int bambooShootHungerHealed = 4;
	static public final float bambooShootSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int rawFishHungerHealed = 9;
	static public final float rawFishSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int cookedFishHungerHealed = 12;
	static public final float cookedFishSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int rawFishFiletHungerHealed = 4;
	static public final float rawFishFiletSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int cookedFishFiletHungerHealed = 6;
	static public final float cookedFishFiletSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int deepFriedFishFiletHungerHealed = 12;
	static public final float deepFriedFishFiletSaturationModifier = FATTY_SATURATION_MODIFIER;
	
	static public final int berryHungerHealed = 4;
	static public final float berrySaturationModifier = SWEET_SATURATION_MODIFIER;
	
	static public final int berryBowlHungerHealed = 8;
	static public final float berryBowlSaturationModifier = SWEET_SATURATION_MODIFIER;
	
	static public final int pieHungerHealed = 8;
	static public final float pieSaturationModifier = PIE_SATURATION_MODIFIER;
	
	static public final int pieSliceHungerHealed = 2;
	static public final float pieSliceSaturationModifier = PIE_SATURATION_MODIFIER;
	
	static public final int cakeSliceHungerHealed = 12;
	static public final float cakeSliceSaturationModifier = CAKE_SATURATION_MODIFIER;
	
	static public final int muffinHungerHealed = 3;
	static public final float muffinSaturationModifier = COOKIE_SATURATION_MODIFIER;
	
	static public final int cookieHungerHealed = 3;
	static public final float cookieSaturationModifier = COOKIE_SATURATION_MODIFIER;
	
	static public final int donutSugarHungerHealed = 4;
	static public final float donutSugarSaturationModifier = FATTY_SATURATION_MODIFIER;
	
	static public final int donutChocolateHungerHealed = 4;
	static public final float donutChocolateSaturationModifier = FATTY_SATURATION_MODIFIER;
	
	static public final int beefPattyRawHungerHealed = 6;
	static public final float beefPattyRawSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int beefPattyCookedHungerHealed = 8;
	static public final float beefPattyCookedSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int baconRawHungerHealed = 4; // 12/3
	static public final float baconRawSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int baconCookedHungerHealed = 5; // 15/3
	static public final float baconCookedSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int chickenDrumRawHungerHealed = 4;
	static public final float chickenDrumRawSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int chickenDrumCookedHungerHealed = 6;
	static public final float chickenDrumCookedSaturationModifier = PROTEIN_SATURATION_MODIFIER;
	
	static public final int halfBreadHungerHealed = 4;
	static public final float halfBreadSaturationModifier = BREAD_SATURATION_MODIFIER;
	
	static public final int sandwichHalfHungerHealed = 2;
	static public final float sandwichHalfSaturationModifier = BREAD_SATURATION_MODIFIER;
	
	static public final int burgerBunHungerHealed = 4;
	static public final float burgerBunSaturationModifier = SWEET_SATURATION_MODIFIER;
	
	static public final int burgerBunHalfHungerHealed = 2;
	static public final float burgerBunHalfSaturationModifier = SWEET_SATURATION_MODIFIER/2;
	
	static public final int coconutDrinkHungerHealed = 0;
	static public final float coconutDrinkSaturationModifier = 0.0F;

	static public final int coconutSliceHungerHealed = 3;
	static public final float coconutSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int riceHungerHealed = 10;
	static public final float riceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int sunflowerSeedsHungerHealed = 2;
	static public final float sunflowerSeedsSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int sunflowerSeedsRoastedHungerHealed = 3;
	static public final float sunflowerSeedsRoastedSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int grapesHungerHealed = 6;
	static public final float grapesSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int tomatoHungerHealed = 4;
	static public final float tomatoSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int tomatoSliceHungerHealed = 2;
	static public final float tomatoSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int onionHungerHealed = 4;
	static public final float onionSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int onionSliceHungerHealed = 2;
	static public final float onionSliceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int lettuceHungerHealed = 4;
	static public final float lettuceSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int lettuceLeafHungerHealed = 2;
	static public final float lettuceLeafSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int wildCarrotHungerHealed = 6;
	static public final float wildCarrotSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int wildCarrotCookedHungerHealed = 8;
	static public final float wildCarrotCookedSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	static public final int sweetPotatoBoiledHungerHealed = 8;
	static public final float sweetPotatoBoiledSaturationModifier = VEGS_SATURATION_MODIFIER;
	
	public SCItemFood(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName);
		
		setCreativeTab(CreativeTabs.tabFood);
	}
	
	public SCItemFood(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName, boolean doZombiesConsume) {
		super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName, doZombiesConsume);
		setCreativeTab(CreativeTabs.tabFood);
	}

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(GetHungerRestored(par1ItemStack), getSaturationModifier(par1ItemStack));
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);
        return par1ItemStack;
    }
    
	public float getSaturationModifier(ItemStack stack)
	{
		return getSaturationModifier();
	};
	
	public int GetHungerRestored(ItemStack stack)
	{
		return getHealAmount();
	};

}
