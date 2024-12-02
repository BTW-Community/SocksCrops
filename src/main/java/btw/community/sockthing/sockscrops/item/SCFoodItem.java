package btw.community.sockthing.sockscrops.item;

import btw.item.items.FoodItem;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class SCFoodItem extends FoodItem {

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
    public static final int BERRY_HUNGER_HEALED = 4;
    public static final float BERRY_SATURATION_MODIFIER = SWEET_SATURATION_MODIFIER;
    static public final int BERRY_BOWL_HUNGER_HEALED = 8;
    static public final float BERRY_BOWL_SATURATION_MODIFIER = SWEET_SATURATION_MODIFIER * 2;


    public SCFoodItem(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName) {
        super(iItemID, iHungerHealed, fSaturationModifier, bWolfMeat, sItemName);

        setCreativeTab(CreativeTabs.tabFood);
    }

    public SCFoodItem(int iItemID, int iHungerHealed, float fSaturationModifier, boolean bWolfMeat, String sItemName, boolean doZombiesConsume) {
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
