package net.minecraft.src;

//copied and modified from grothcraft for 1.7
public interface SCIRope {

	/**
	 * @param world - world to interact with
	 * @param x - x coord
	 * @param y - y coord
	 * @param z - z coord
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
	public boolean HasValidAttachmentPointToFacing( World world, int i, int j, int k, int iFacing );
}
