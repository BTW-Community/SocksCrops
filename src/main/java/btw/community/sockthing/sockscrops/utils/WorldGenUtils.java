package btw.community.sockthing.sockscrops.utils;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class WorldGenUtils{
    public static boolean canLeavesBePlaced(World world, int x, int y, int z)
    {

        int blockID = world.getBlockId(x, y, z);
        Block block = Block.blocksList[blockID];

        if (blockID == 0 || ((BlockInterface)block).canBeReplacedByLeaves(blockID) )
        {
            return true;
        }

        return false;
    }
}
