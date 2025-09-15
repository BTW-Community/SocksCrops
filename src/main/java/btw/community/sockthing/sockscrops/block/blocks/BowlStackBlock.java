package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.tileentities.BowlStackTileEntity;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

public class BowlStackBlock extends BlockContainer {
    public BowlStackBlock(int blockID, String name) {
        super(blockID, Material.wood);
        setUnlocalizedName(name);

        initBlockBounds(0,0,0,
                1, 1/128D, 1);
    }

    @Override
    public boolean onBlockActivated( World world, int x, int y, int z, EntityPlayer player, int facing, float xClick, float yClick, float zClick )
    {
        int increaseOrRemove = player.getHeldItem() == null ? -1 : player.getHeldItem().itemID == Item.bowlEmpty.itemID ? 1 : 0;
        BowlStackTileEntity bowl = (BowlStackTileEntity) world.getBlockTileEntity(x, y, z);
        if (addBowlToSlot(world, x, y, z, xClick, zClick, bowl, increaseOrRemove)){
            if (increaseOrRemove > 0){
                --player.getHeldItem().stackSize;
            }
            else {
                ItemUtils.givePlayerStackOrEject(player, new ItemStack(Item.bowlEmpty));
            }

            // Remove block if empty //
            removeBlockIfEmpty(world, x, y, z, bowl);
        }

        return true;
    }

    private static boolean removeBlockIfEmpty(World world, int x, int y, int z, BowlStackTileEntity bowl) {
        //if any slots are not empty, don't remove
        for (int i = 0; i < 4; i++) {
            if (bowl.squarePositions[i] > 0 || bowl.diamondPositions[i] > 0) {
                return false;
            }
        }

        if (bowl.centerPositions[0] > 0) return false;

        //remove block
        world.setBlockToAir(x, y, z);
        world.removeBlockTileEntity(x, y, z);
        return true;
    }

    public static boolean addBowlToSlot(World world, int x, int y, int z, float xClick, float zClick, BowlStackTileEntity bowl, int increaseOrRemove) {
        if (bowl == null) return false;

        //center hit
        if (xClick > 5/16F && xClick < 11/16F
                && zClick > 5/16F && zClick < 11/16F){
            return bowl.setCenterPositions(0, increaseOrRemove);
        }

        //square hit
        if (zClick < 5/16F) {
            if (xClick < 5/16F) {
                return bowl.setSquarePositions( 1, increaseOrRemove);
            }
            else if (xClick > 11/16F) {
                return bowl.setSquarePositions(0, increaseOrRemove);
            }
        }
        else if (zClick > 11/16F) {
            if (xClick < 5/16F) return bowl.setSquarePositions(3, increaseOrRemove);
            else if (xClick > 11/16F) return bowl.setSquarePositions(2, increaseOrRemove);
        }

        //diamond hit
        if (zClick < 5/16F) {
            if (xClick > 5/16F && xClick < 11/16F) return bowl.setDiamondPositions(0, increaseOrRemove);
        }
        else if (zClick < 11/16F) {
            if (xClick < 5/16F ) return bowl.setDiamondPositions(2, increaseOrRemove);
            if (xClick > 11/16F) return bowl.setDiamondPositions(1, increaseOrRemove);
        }
        else {
            if (xClick > 5/16F && xClick < 11/16F) return bowl.setDiamondPositions(3, increaseOrRemove);
        }

        world.markBlockForUpdate(x, y, z);

        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World var1) {
        return new BowlStackTileEntity();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
//        BowlStackTileEntity bowl = (BowlStackTileEntity) renderer.blockAccess.getBlockTileEntity(i,j,k);
//
//        if (bowl.centerPositions[0] > 0){
//            // Center Positions
//            renderer.setRenderBounds(
//                    0.25, 0/16D, 0.25,
//                    0.75, 1/16D,0.75
//            );
//            renderer.renderStandardBlock(this, i,j,k);
//        }
//        else {
//            // Square Positions
//            if (bowl.squarePositions[0] > 0){
//                renderer.setRenderBounds(
//                        0.00, 0/16D, 0.00,
//                        0.50, 1/16D,0.50
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.squarePositions[1] > 0){
//                renderer.setRenderBounds(
//                        0.50, 0/16D, 0.00,
//                        1.00, 1/16D,0.50
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.squarePositions[2] > 0){
//                renderer.setRenderBounds(
//                        0.00, 0/16D, 0.50,
//                        0.50, 1/16D,1.00
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.squarePositions[3] > 0){
//                renderer.setRenderBounds(
//                        0.50, 0/16D, 0.50,
//                        1.00, 1/16D,1.00
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//
//            // Diamond Positions
//            if (bowl.diamondPositions[0] > 0){
//                renderer.setRenderBounds(
//                        0.25, 0/16D, 0.00,
//                        0.75, 1/16D,0.50
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.diamondPositions[1] > 0){
//                renderer.setRenderBounds(
//                        0.00, 0/16D, 0.25,
//                        0.50, 1/16D,0.75
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.diamondPositions[2] > 0){
//                renderer.setRenderBounds(
//                        0.50, 0/16D, 0.25,
//                        1.00, 1/16D,0.75
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//            if (bowl.diamondPositions[3] > 0){
//                renderer.setRenderBounds(
//                        0.25, 0/16D, 0.50,
//                        0.75, 1/16D,1.00
//                );
//                renderer.renderStandardBlock(this, i,j,k);
//            }
//        }


        return false;
    }
}
