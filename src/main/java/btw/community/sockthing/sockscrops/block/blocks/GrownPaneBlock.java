//package btw.community.sockthing.sockscrops.block.blocks;
//
//import btw.block.blocks.PaneBlock;
//import btw.block.util.Flammability;
//import btw.item.BTWItems;
//import net.minecraft.src.*;
//
//import java.util.Random;
//
//public class GrownPaneBlock extends PaneBlock {
//
//    private final ItemStack paneBlock;
//    private ItemStack[] storedBlock;
//    public static final int VINES = 0;
//
//    public GrownPaneBlock(int blockID, Material material, String name, ItemStack paneBlock,
//                          String toptexture, String sideTextureName,
//                          ItemStack[] storedBlock) {
//        super( blockID, toptexture, sideTextureName,
//                material, false );
//
//        setHardness( 0.5F );
//        setAxesEffectiveOn();
//
//        setBuoyant();
//
//        setFireProperties(Flammability.PLANKS);
//
//        setLightOpacity( 2 );
//        Block.useNeighborBrightness[blockID] = true;
//
//        setStepSound( Block.soundWoodFootstep );
//
//        setUnlocalizedName( name );
//
//        this.paneBlock = paneBlock;
//        this.storedBlock = storedBlock;
//    }
//
//    @Override
//    public int idPicked(World par1World, int par2, int par3, int par4) {
//        return paneBlock.itemID;
//    }
//
//    @Override
//    public int idDropped(int par1, Random par2Random, int par3) {
//        return paneBlock.itemID;
//    }
//
//    @Override
//    public int damageDropped(int par1) {
//        return 0;
//    }
//
//    @Override
//    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
//    {
//        ItemStack heldStack = player.getHeldItem();
//
//        if (heldStack == null)
//        {
//            world.setBlockAndMetadataWithNotify(x,y,z, paneBlock.itemID, paneBlock.getItemDamage());
//
//            dropItemsIndividually(world, x, y, z, storedBlock[].itemID,
//                    1, storedBlock.getItemDamage(), 1);
//
//            return true;
//        }
//
//        return super.onBlockActivated(world, x, y, z, player, iFacing, fXClick, fYClick, fZClick);
//    }
//
//    @Override
//    public boolean doesBlockBreakSaw(World world, int i, int j, int k )
//    {
//        return false;
//    }
//
//    @Override
//    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k,
//                                                int iMetadata, float fChanceOfDrop)
//    {
//        dropItemsIndividually(world, i, j, k, Item.stick.itemID,
//                2, 0, fChanceOfDrop);
//
//        dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID,
//                2, 0, fChanceOfDrop);
//
//        dropItemsIndividually(world, i, j, k, storedBlock.itemID,
//                1, storedBlock.getItemDamage(), 1);
//
//        return true;
//    }
//
//    @Override
//    public void registerIcons(IconRegister par1IconRegister) {
//        super.registerIcons(par1IconRegister);
//    }
//
//    @Override
//    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
//        boolean north = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 2);
//        boolean south = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 3);
//        boolean east = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 4);
//        boolean west = canConnectToBlockToFacing(renderer.blockAccess, x, y, z, 5);
//
//        if (Block.blocksList[storedBlock.itemID] != null) {
//            renderer.renderCrossedSquares(Block.blocksList[storedBlock.itemID], x, y, z);
//        }
//
//        return super.renderBlock(renderer, x, y, z);
//    }
//}
