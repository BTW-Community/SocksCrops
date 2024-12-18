package btw.community.sockthing.sockscrops.interfaces;

import net.minecraft.src.World;

//copied and modified from grothcraft for 1.7
public interface RopeInterface {

    /**
     * @param world - world to interact with
     * @param i - x coord
     * @param j - y coord
     * @param k - z coord
     * @return can the rope be connected at this point?
     *
     * <pre>
     * {@code
     *	 public boolean HasValidAttachmentPointToFacing( World world, int i, int j, int k, int iFacing )
     *	 {
     *	 	 return Block.blocksList[world.getBlockId(x, y, z)] instanceof SCIRope;
     *	 }
     * }
     * </pre>
     */
    public boolean hasValidAttachmentPointToFacing(World world, int i, int j, int k, int iFacing );
}
