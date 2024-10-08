package btw.community.sockthing.sockscrops.mixins;

import btw.block.BTWBlocks;
import btw.block.tileentity.WickerBasketTileEntity;
import btw.community.sockthing.sockscrops.world.LootHandler;
import btw.item.BTWItems;
import btw.item.util.RandomItemStack;
import btw.world.feature.trees.BonusBasketGenerator;
import net.minecraft.src.Block;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenerator;
import org.spongepowered.asm.mixin.Mixin;

import java.util.ArrayList;
import java.util.Random;

@Mixin(BonusBasketGenerator.class)
public abstract class BonusBasketGeneratorMixin extends WorldGenerator {

    private final ArrayList<RandomItemStack> bonusBasket = new ArrayList();

    private void initContentsArray() {
        bonusBasket.add(new RandomItemStack(BTWItems.goldenDung.itemID, 0, 1, 1, 10));

        LootHandler.addBonusBasketLoot(bonusBasket);
    }

    @Override
    public boolean generate(World world, Random rand, int x, int y, int z) {
        int iTempBlockID = world.getBlockId(x, y, z);

        while (y > 1 && (iTempBlockID == 0 || iTempBlockID == Block.leaves.blockID)) {
            y--;
            iTempBlockID = world.getBlockId(x, y, z);
        }

        ++y;

        for (int iTempCount = 0; iTempCount < 4; iTempCount++) {
            int iTempI = x + rand.nextInt(4) - rand.nextInt(4);
            int iTempJ = y + rand.nextInt(3) - rand.nextInt(3);
            int iTempK = z + rand.nextInt(4) - rand.nextInt(4);

            if (world.isAirBlock(iTempI, iTempJ, iTempK) && world.doesBlockHaveSolidTopSurface(iTempI, iTempJ - 1, iTempK)) {
                world.setBlock(iTempI, iTempJ, iTempK, BTWBlocks.wickerBasket.blockID, world.rand.nextInt(4) | 4, 2);

                WickerBasketTileEntity tileEntity = (WickerBasketTileEntity) world.getBlockTileEntity(iTempI, iTempJ, iTempK);

//                if ( tileEntity != null )
//                {
//                    tileEntity.setStorageStack(new ItemStack(BTWItems.goldenDung));
//                }

                if (bonusBasket.size() == 0) {
                    // only initialize array on first use to ensure referenced mod items are intialized
                    initContentsArray();
                }

                return true;
            }
        }

        return false;
    }
}
