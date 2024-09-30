package btw.community.sockthing.sockscrops.item.items;

import btw.crafting.util.FurnaceBurnTime;
import btw.item.items.PlaceAsBlockItem;
import net.minecraft.src.CreativeTabs;

public class SCItemCuttings extends PlaceAsBlockItem {
    public SCItemCuttings(int itemID, int blockToPlace, String name) {
        super(itemID, blockToPlace);

        setBuoyant();
        setIncineratedInCrucible();
        setBellowsBlowDistance(2);
        setfurnaceburntime(FurnaceBurnTime.KINDLING);
        setFilterableProperties(FILTERABLE_NARROW);

        setHerbivoreFoodValue(BASE_HERBIVORE_ITEM_FOOD_VALUE / 4);

        setUnlocalizedName(name);

        setCreativeTab(CreativeTabs.tabMaterials);
    }
}
