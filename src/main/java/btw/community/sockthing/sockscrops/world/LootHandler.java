package btw.community.sockthing.sockscrops.world;

import btw.item.util.RandomItemStack;
import net.minecraft.src.EntityMinecartChest;
import net.minecraft.src.WeightedRandomChestContent;
import net.minecraft.src.World;

import java.util.ArrayList;

public class LootHandler {

    public static void addBonusBasketLoot(ArrayList<RandomItemStack> bonusBasket) {
//        bonusBasket.add( new RandomItemStack(BTWItems.hemp.itemID, 0, 1, 2, 10));
    }

    public static void addWitchHutLoot(ArrayList<RandomItemStack> basketLoot) {
//        basketLoot.add( new RandomItemStack(BTWItems.hemp.itemID, 0, 1, 2, 10));
    }

    public static void addDesertWellLoot(ArrayList<RandomItemStack> basketLoot) {
//        basketLoot.add( new RandomItemStack(BTWItems.hemp.itemID, 0, 1, 2, 10));
    }

    public static void addDesertPyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {
        // item ID, item damage, min stack size, max stack size, weight
//        lootedChestLoot.add( new WeightedRandomChestContent(BTWItems.hemp.itemID, 0, 1, 1, 3 ) );
//        chestLoot.add( new WeightedRandomChestContent( SCItems.goldKnife.itemID, 0, 1, 1, 10 ) );
    }

    public static void addJunglePyramidLoot(ArrayList<WeightedRandomChestContent> chestLoot, ArrayList<WeightedRandomChestContent> lootedChestLoot) {

        // item ID, item damage, min stack size, max stack size, weight
//        lootedChestLoot.add( new WeightedRandomChestContent(BTWItems.hemp.itemID, 0, 1, 1, 3 ) );
//        chestLoot.add( new WeightedRandomChestContent( SCItems.goldKnife.itemID, 0, 1, 1, 10 ) );

    }

    public static void addMinecartLoot(ArrayList<WeightedRandomChestContent> minecartLoot) {

    }

    public static void filterMinecartContents(EntityMinecartChest minecart) {

    }

    public static void addDungeonChestLoot(ArrayList<RandomItemStack> chestLoot) {

    }

    public static void filterDungeonChestContents(World world, int chestPosX, int chestPosY, int chestPosZ) {

    }

    public static void addBlacksmithChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {

    }

    public static void addStrongholdChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {

    }

    public static void addStrongholdLibraryChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {

    }

    public static void addStrongholdCrossingChestLoot(ArrayList<WeightedRandomChestContent> chestLoot) {

    }
}
