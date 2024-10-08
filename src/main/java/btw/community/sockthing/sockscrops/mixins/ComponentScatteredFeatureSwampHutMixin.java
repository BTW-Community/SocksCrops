package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.block.tileentity.WickerBasketTileEntity;
import btw.community.sockthing.sockscrops.world.LootHandler;
import btw.item.BTWItems;
import btw.item.util.RandomItemStack;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Random;

@Mixin(ComponentScatteredFeatureSwampHut.class)
public abstract class ComponentScatteredFeatureSwampHutMixin extends ComponentScatteredFeature {
    private static final ArrayList<RandomItemStack> basketLoot = new ArrayList();
    @Shadow
    private boolean hasLootBasket;


    protected ComponentScatteredFeatureSwampHutMixin(Random par1Random, int par2, int par3, int par4, int par5, int par6, int par7) {
        super(par1Random, par2, par3, par4, par5, par6, par7);
    }

    @Inject(method = "initContentsArray", at = @At(value = "HEAD"), cancellable = true)
    public void initContentsArray(CallbackInfo ci) {
//        lootBasketContents = new RandomItemStack[] {
//                new RandomItemStack( BTWItems.hempSeeds.itemID, 0, 1, 4, 5 ),
//                new RandomItemStack( Item.glassBottle.itemID, 0, 2, 8, 10 ),
//                new RandomItemStack( BTWItems.redMushroom.itemID, 0, 5, 16, 5 )
//        };

        basketLoot.add(new RandomItemStack(BTWItems.hempSeeds.itemID, 0, 1, 4, 5));
        basketLoot.add(new RandomItemStack(Item.glassBottle.itemID, 0, 2, 8, 10));
        basketLoot.add(new RandomItemStack(BTWItems.redMushroom.itemID, 0, 5, 16, 5));

        LootHandler.addWitchHutLoot(basketLoot);

        ci.cancel();
    }

    @Inject(method = "addLootBasket", at = @At(value = "HEAD"), cancellable = true)
    public void addLootBasket(World world, StructureBoundingBox boundingBox, int iRelX, int iRelY, int iRelZ, CallbackInfo ci) {
        ComponentScatteredFeatureSwampHut thisObject = (ComponentScatteredFeatureSwampHut) (Object) this;

//        if (lootBasketContents == null )
        if (basketLoot.size() == 0) {
            // only initialize array on first use to ensure referenced mod items are intialized
            thisObject.initContentsArray();
        }

        int i = getXWithOffset(iRelX, iRelZ);
        int j = getYWithOffset(iRelY);
        int k = getZWithOffset(iRelX, iRelZ);

        if (boundingBox.isVecInside(i, j, k) && world.getBlockId(i, j, k) != BTWBlocks.wickerBasket.blockID) {
            hasLootBasket = true;

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
