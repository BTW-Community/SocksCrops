package btw.community.sockthing.sockscrops.item;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.community.sockthing.sockscrops.item.items.SCItemCuttings;
import btw.item.BTWItems;
import btw.item.items.PlaceAsBlockItem;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;

public class SCItems {
    public static Item ironKnife;
    public static Item goldKnife;
    public static Item diamondKnife;
    public static Item steelKnife;

    public static Item cuttings;

    public static void initItems() {
        initKnives();
        initHay();
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

        cuttings = new SCItemCuttings(SCItemIDs.CUTTINGS_ID - 256, SCBlocks.dryingHay.blockID, "cuttings_item");
    }
}
