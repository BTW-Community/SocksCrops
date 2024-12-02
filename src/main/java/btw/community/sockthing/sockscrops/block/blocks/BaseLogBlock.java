package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.LogBlock;
import net.minecraft.src.BlockLog;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public abstract class BaseLogBlock extends LogBlock {

    public static final String[] treeNames = new String[] {"oak", "spruce", "birch", "jungle"};

    protected BaseLogBlock(int blockID) {
        super(blockID);
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k )
    {
        return false;
    }

    @Override
    public boolean getCanBlockBeIncinerated( World world, int i, int j, int k )
    {
        return false;
    }
}
