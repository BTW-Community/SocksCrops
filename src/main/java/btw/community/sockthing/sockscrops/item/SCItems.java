package btw.community.sockthing.sockscrops.item;

import btw.community.sockthing.sockscrops.SocksCropsAddon;
import btw.community.sockthing.sockscrops.block.SCBlockIDs;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallPlantBlock;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.item.items.CuttingsItem;
import btw.item.BTWItems;
import btw.item.items.PlaceAsBlockItem;
import btw.item.items.SeedItem;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;

public class SCItems {
    public static Item ironKnife;
    public static Item goldKnife;
    public static Item diamondKnife;
    public static Item steelKnife;

    public static Item sweetberry;
    public static Item sweetberryRoots;

    public static Item blueberry;
    public static Item blueberryRoots;

    public static Item cuttings;
    public static Item sunflower;
    public static Item sunflowerSeeds;

    public static Item mossBall;
    public static Item flowerPot;
    public static Item largeFlowerpot;

    public static Item berryBowl;

    public static void initItems() {
        initKnives();
        intiBerries();
        initHay();
        initSunflower();
        initMoss();
        initFlowerpot();
    }

    private static void initFlowerpot() {
        largeFlowerpot = new PlaceAsBlockItem(SCItemIDs.LARGE_FLOWERPOT_ID - 256, SCBlockIDs.LARGE_FLOWERPOT_ID, 0, "large_flowerpot")
            .setCreativeTab(CreativeTabs.tabDecorations);

        Item.flowerPot.setCreativeTab(null);
        flowerPot = new PlaceAsBlockItem(SCBlockIDs.FLOWERPOT_ID - 256, SCBlockIDs.FLOWERPOT_ID, 0, "flowerPot")
                .setCreativeTab(CreativeTabs.tabDecorations);
    }

    private static void initMoss() {
        mossBall = new Item(SCItemIDs.MOSS_BALL_ID - 256).setUnlocalizedName("moss_ball").setCreativeTab(CreativeTabs.tabMaterials);
    }

    private static void initSunflower() {
        sunflower = new PlaceAsBlockItem(SCItemIDs.SUNFLOWER_ID - 256, SCBlockIDs.DOUBLE_TALL_FLOWER_ID, DoubleTallPlantBlock.SUNFLOWER,"sunflower")
                .setCreativeTab(CreativeTabs.tabDecorations)
                .setFilterableProperties(Item.FILTERABLE_FINE);


        sunflowerSeeds = new SeedItem(SCItemIDs.SUNFLOWER_SEEDS_ID - 256, SCBlockIDs.SUNFLOWER_CROP_ID)
                .setUnlocalizedName("sunflower_seeds")
                .setFilterableProperties(Item.FILTERABLE_FINE);
    }

    private static void intiBerries() {
        sweetberryRoots = new SeedItem(SCItemIDs.SWEETBERRY_ROOTS_ID - 256, SCBlockIDs.SWEETBERRY_BUSH_ID)
                .setUnlocalizedName("sweetberry_roots")
                .setCreativeTab(CreativeTabs.tabDecorations);

        sweetberry = new SCFoodItem(SCItemIDs.SWEETBERRY_ID - 256, SCFoodItem.BERRY_HUNGER_HEALED,
                SCFoodItem.BERRY_SATURATION_MODIFIER, false, "sweetberry");

        blueberryRoots = new SeedItem(SCItemIDs.BLUEBERRY_ROOTS_ID - 256, SCBlockIDs.BLUEBERRY_BUSH_ID)
                .setUnlocalizedName("blueberry_roots")
                .setCreativeTab(CreativeTabs.tabDecorations);

        blueberry = new SCFoodItem(SCItemIDs.BLUEBERRY_ID - 256, SCFoodItem.BERRY_HUNGER_HEALED, SCFoodItem.BERRY_SATURATION_MODIFIER,
                false, "blueberry");

        berryBowl = new SCFoodContainerItem(SCItemIDs.BERRY_BOWL_ID - 256, Item.bowlEmpty,
                SCFoodItem.BERRY_BOWL_HUNGER_HEALED, SCFoodItem.BERRY_BOWL_SATURATION_MODIFIER, false,
                "fruit_bowl_berries");
    }

    private static void initKnives() {
        ironKnife = new KnifeItem(SCItemIDs.IRON_KNIFE_ID - 256, EnumToolMaterial.IRON, "iron_knife");
        goldKnife = new KnifeItem(SCItemIDs.GOLD_KNIFE_ID - 256, EnumToolMaterial.GOLD, "gold_knife");
        diamondKnife = new KnifeItem(SCItemIDs.DIAMOND_KNIFE_ID - 256, EnumToolMaterial.EMERALD, "diamond_knife");
        steelKnife = new KnifeItem(SCItemIDs.STEEL_KNIFE_ID - 256, EnumToolMaterial.SOULFORGED_STEEL, "steel_knife");
    }

    private static void initHay() {
        //BTW Overide
        BTWItems.straw = new PlaceAsBlockItem(BTWItems.straw.itemID - 256, SCBlocks.driedHay.blockID, 0, "fcItemStraw");

        cuttings = new CuttingsItem(SCItemIDs.CUTTINGS_ID - 256, SCBlocks.dryingHay.blockID, "cuttings_item");
    }
}
