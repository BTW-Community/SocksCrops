package btw.community.sockthing.sockscrops.item;

import btw.AddonHandler;
import btw.community.sockthing.sockscrops.block.SCBlockIDs;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.block.blocks.CookedPieBlock;
import btw.community.sockthing.sockscrops.block.blocks.DoubleTallPlantBlock;
import btw.community.sockthing.sockscrops.item.items.BambooProgressiveItem;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.item.items.CuttingsItem;
import btw.community.sockthing.sockscrops.item.items.PlacableFoodItem;
import btw.crafting.util.FurnaceBurnTime;
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

    public static Item bamboo;
    public static Item bambooProgressiveItem;
    public static Item bambooWeave;
    public static Item strippedBamboo;
    public static Item boiledBambooShoot;

    public static Item mossBall;
    public static Item flowerPot;
    public static Item largeFlowerpot;

    public static Item berryBowl;

    public static Item cod;
    public static Item codCooked;
    public static Item salmon;
    public static Item salmonCooked;
    public static Item tropicalFish;
    public static Item tropicalFishCooked;
    public static Item pufferFish;

    public static Item cakeSlice;
    public static Item pumpkinPieSlice;
    public static Item pieCrust;

    public static void initItems() {
        initKnives();
        intiBerries();
        initHay();
        initSunflower();
        initMoss();
        initFlowerpot();
        initBamboo();
        initFish();
        initCake();
        initPie();
    }

    private static void initPie() {
        Item.pumpkinPie = new PlacableFoodItem( 144, 2, 2.5F, false,
                SCBlockIDs.COOKED_PIE_ID, CookedPieBlock.PUMPKIN, "pumpkinPie")
                .setAlwaysEdible()
                .setCreativeTab( CreativeTabs.tabFood );

        pumpkinPieSlice = new SCFoodItem(SCItemIDs.PUMPKIN_PIE_SLICE_ID - 256, SCFoodItem.PIE_SLICE_HUNGER_HEALED,
                SCFoodItem.PIE_SLICE_SATURATION_MODIFIER, false, "pumpkin_pie_slice")
                .setAlwaysEdible();

        pieCrust = new Item(SCItemIDs.PIE_CRUST_ID - 256).setUnlocalizedName("pie_crust")
                .setCreativeTab(CreativeTabs.tabFood);
    }

    private static void initCake() {
        cakeSlice = new SCFoodItem(SCItemIDs.CAKE_SLICE_ID - 256, SCFoodItem.CAKE_SLICE_HUNGER_HEALED,
                SCFoodItem.CAKE_SLICE_SATURATION_MODIFIER, false, "cake_slice")
                .setAlwaysEdible();
    }

    private static void initFish() {
        cod = new SCFoodItem(SCItemIDs.COD_ID - 256, SCFoodItem.RAW_FISH_HUNGER_HEALED, SCFoodItem.RAW_FISH_SATURATION_MODIFIER, false,
                "cod").setStandardFoodPoisoningEffect();

        codCooked = new SCFoodItem(SCItemIDs.COD_COOKED_ID - 256, SCFoodItem.COOKED_FISH_HUNGER_HEALED,
                SCFoodItem.COOKED_FISH_SATURATION_MODIFIER, false, "cod_cooked");

        salmon = new SCFoodItem(SCItemIDs.SALMON_ID - 256, SCFoodItem.RAW_FISH_HUNGER_HEALED, SCFoodItem.RAW_FISH_SATURATION_MODIFIER, false,
                "salmon").setStandardFoodPoisoningEffect();

        salmonCooked = new SCFoodItem(SCItemIDs.SALMON_COOKED_ID - 256, SCFoodItem.COOKED_FISH_HUNGER_HEALED,
                SCFoodItem.COOKED_FISH_SATURATION_MODIFIER, false, "cod_cooked");

        tropicalFish = new SCFoodItem(SCItemIDs.TROPICAL_FISH_ID - 256, SCFoodItem.RAW_FISH_HUNGER_HEALED,
                SCFoodItem.RAW_FISH_SATURATION_MODIFIER, false, "tropical_fish").setStandardFoodPoisoningEffect();

        tropicalFishCooked = new SCFoodItem(SCItemIDs.TROPICAL_FISH_COOKED_ID - 256, SCFoodItem.COOKED_FISH_HUNGER_HEALED,
                SCFoodItem.COOKED_FISH_SATURATION_MODIFIER, false, "tropical_fish_cooked");

        pufferFish = new Item(SCItemIDs.PUFFER_FISH_ID - 256).setUnlocalizedName("puffer_fish");

    }

    private static void initBamboo() {
        bamboo = new Item(SCItemIDs.BAMBOO_ID - 256)
                .setBuoyant()
                .setfurnaceburntime( FurnaceBurnTime.KINDLING ) // this also allows the item to be valid fuel
                .setIncineratedInCrucible()
                .setFilterableProperties( Item.FILTERABLE_NARROW )
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("bamboo");

        strippedBamboo = new Item(SCItemIDs.STRIPPED_BAMBOO_ID - 256)
                .setBuoyant()
                .setfurnaceburntime( FurnaceBurnTime.KINDLING ) // this also allows the item to be valid fuel
                .setIncineratedInCrucible()
                .setFilterableProperties( Item.FILTERABLE_NARROW )
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("stripped_bamboo");

        bambooProgressiveItem = new BambooProgressiveItem(SCItemIDs.BAMBOO_PROGRESSIVE_ID - 256, "bamboo_progressive");

        bambooWeave = new Item(SCItemIDs.BAMBOO_WEAVE_ID - 256)
                .setBuoyant()
                .setBellowsBlowDistance( 2 )
                .setfurnaceburntime( FurnaceBurnTime.WICKER_PIECE ) // this also allows the item to be valid fuel
                .setIncineratedInCrucible()
                .setFilterableProperties( Item.FILTERABLE_THIN )
                .setCreativeTab(CreativeTabs.tabMaterials)
                .setUnlocalizedName("bamboo_weave");

        boiledBambooShoot = new SCFoodItem(SCItemIDs.BOILED_BAMBOO_ID - 256, SCFoodItem.BAMBOO_SHOOT_HUNGER_HEALED,
                SCFoodItem.BAMBOO_SHOOT_SATURATION_MODIFIER, false, "bamboo_shoot_cooked");
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
        BTWItems.straw = new PlaceAsBlockItem(BTWItems.straw.itemID - 256, SCBlockIDs.DRIED_HAY_ID, 0, "fcItemStraw");

        cuttings = new CuttingsItem(SCItemIDs.CUTTINGS_ID - 256, SCBlockIDs.DRYING_HAY_ID, "cuttings_item");
    }
}
