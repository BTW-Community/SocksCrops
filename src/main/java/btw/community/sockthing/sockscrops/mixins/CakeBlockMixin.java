package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.CakeBlock;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.item.items.KnifeItem;
import btw.inventory.util.InventoryUtils;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CakeBlock.class)
public abstract class CakeBlockMixin extends BlockCake {
    protected CakeBlockMixin(int par1) {
        super(par1);
    }
    @Inject(method = "onBlockActivated", at = @At(value = "HEAD"), cancellable = true)
    public void onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick, CallbackInfoReturnable<Boolean> cir )
    {
        ItemStack heldStack = player.getCurrentEquippedItem();

        if (heldStack != null && heldStack.getItem() instanceof KnifeItem){
            cutCake(world, i, j, k, player);
            dropItemsIndividually(world, i, j, k, SCItems.cakeSlice.itemID, 1, 0, 1.0f);
            heldStack.attemptDamageItem(1, world.rand);
            int maxDamage = heldStack.getMaxDamage();
            if (heldStack.getItemDamage() >= maxDamage)
            {
                //break tool
                heldStack.stackSize = 0;
                player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );
            }

            cir.setReturnValue(true);
        }

    }

    private void cutCake(World world, int i, int j, int k, EntityPlayer player)
    {
        int iEatState = getEatState(world, i, j, k) + 1;

        if ( iEatState >= 6 )
        {
            world.setBlockWithNotify( i, j, k, 0 );
        }
        else
        {
            setEatState(world, i, j, k, iEatState);
        }
    }

    public int getEatState(IBlockAccess iBlockAccess, int i, int j, int k)
    {
        return ( iBlockAccess.getBlockMetadata( i, j, k ) & 7 );
    }

    public void setEatState(World world, int i, int j, int k, int state)
    {
        int iMetaData = world.getBlockMetadata( i, j, k ) & 8; // filter out any old on state

        iMetaData |= state;

        world.setBlockMetadataWithNotify( i, j, k, iMetaData );
    }
}
