package btw.community.sockthing.sockscrops.mixin;

import btw.block.BTWBlocks;
import btw.block.tileentity.WickerBasketTileEntity;
import btw.community.sockthing.sockscrops.world.LootHandler;
import btw.item.BTWItems;
import btw.item.util.RandomItemStack;
import net.minecraft.src.Item;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenDesertWells;
import net.minecraft.src.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;

@Mixin(WorldGenDesertWells.class)
public abstract class WorldGenDesertWellsMixin extends WorldGenerator {

    //SAU: ADDED
    private static final ArrayList<RandomItemStack> basketLoot = new ArrayList();

    @Inject(method = "initContentsArray", at = @At(value = "HEAD"))
    public void initContentsArray(CallbackInfo ci) {
        //SC: CHANGED
//        lootBasketContents = new RandomItemStack[] {
//                new RandomItemStack( BTWItems.hempSeeds.itemID, 0, 1, 4, 5 ),
//                new RandomItemStack( Item.glassBottle.itemID, 0, 2, 8, 10 ),
//        };
        basketLoot.add(new RandomItemStack(BTWItems.hempSeeds.itemID, 0, 1, 4, 5));
        basketLoot.add(new RandomItemStack(Item.glassBottle.itemID, 0, 2, 8, 10));

        LootHandler.addDesertWellLoot(basketLoot);
    }

    @Inject(method = "addLootBasket", at = @At(value = "HEAD"), remap = false)
    public void addLootBasket(World world, int i, int j, int k, CallbackInfo ci) {
        WorldGenDesertWells thisObject = (WorldGenDesertWells) (Object) this;

//        if (lootBasketContents == null )
        if (basketLoot.size() == 0) {
            // only initialize array on first use to ensure referenced mod items are intialized
            thisObject.initContentsArray();
        }

        if (world.getBlockId(i, j, k) != BTWBlocks.wickerBasket.blockID) {
            world.setBlock(i, j, k, BTWBlocks.wickerBasket.blockID, world.rand.nextInt(4) | 4, 2);

            WickerBasketTileEntity tileEntity = (WickerBasketTileEntity) world.getBlockTileEntity(i, j, k);

            if (tileEntity != null) {
//                tileEntity.setStorageStack(RandomItemStack.getRandomStack(world.rand, lootBasketContents));

                RandomItemStack[] arr = new RandomItemStack[basketLoot.size()];
                tileEntity.setStorageStack(RandomItemStack.getRandomStack(world.rand, basketLoot.toArray(arr)));
            }
        }
    }

}
