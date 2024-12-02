package btw.community.sockthing.sockscrops.mixins;

import btw.block.blocks.FlowerBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(FlowerBlock.class)
public abstract class FlowerBlockMixin extends BlockFlower {
    private Icon dandilion;
    private Icon yellowFlower;
    private Icon redFlower;

    protected FlowerBlockMixin(int par1) {
        super(par1);
    }

    @Environment(EnvType.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        if (this.blockID == Block.plantRed.blockID)
        {
            par3List.add(new ItemStack(par1, 1, 0));
        }
        else {
            par3List.add(new ItemStack(par1, 1, 0));
            par3List.add(new ItemStack(par1, 1, 1));
        }
    }

    @Override
    public int damageDropped(int metadata) {
        return metadata;
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        yellowFlower = par1IconRegister.registerIcon("flower");
        redFlower = par1IconRegister.registerIcon("rose");
        dandilion = par1IconRegister.registerIcon("dandilion_white");
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Icon getIcon(int side, int metadata)
    {
        if (this.blockID == Block.plantRed.blockID)
        {
            return this.blockIcon = redFlower;
        }

        if (metadata == 1){
            return this.blockIcon = dandilion;
        }
        else return this.blockIcon = yellowFlower;
    }
}
