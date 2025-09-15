package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.util.RayTraceUtils;
import btw.community.sockthing.sockscrops.block.tileentities.BowlStackTileEntity;
import btw.item.util.ItemUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class BowlStackBlock extends BlockContainer {
    public BowlStackBlock(int blockID, String name) {
        super(blockID, Material.wood);
        setUnlocalizedName(name);

        initBlockBounds(0,0,0,
                1, 1/128D, 1);
        setHardness(0.05F);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return 0;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 0;
    }

    @Override
    public void breakBlock(World world, int i, int j, int k, int par5, int par6) {
        BowlStackTileEntity bowl = (BowlStackTileEntity) world.getBlockTileEntity(i, j, k);
        int bowlCount = 0;

        if (bowl.centerPositions[0] > 0) bowlCount += bowl.centerPositions[0];

        for (int slot = 0; slot < 4; slot++) {
            if (bowl.squarePositions[slot] > 0) bowlCount += bowl.squarePositions[slot];
            if (bowl.diamondPositions[slot] > 0) bowlCount += bowl.diamondPositions[slot];
        }

        if (bowlCount > 0) this.dropBlockAsItem_do(world, i, j, k, new ItemStack(Item.bowlEmpty, bowlCount, 0));

        super.breakBlock(world, i, j, k, par5, par6);
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

    private final AxisAlignedBB[][] squareBounds = new AxisAlignedBB[][]{
            new AxisAlignedBB[]{
                    // NW
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (1F / 16F),
                            (15F / 16F), (3F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (1F / 16F),
                            (15F / 16F), (6F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (1F / 16F),
                            (15F / 16F), (9F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (1F / 16F),
                            (15F / 16F), (12F / 16F), (7F / 16F)
                    )
            },
            new AxisAlignedBB[]{
                    // NE
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (1F / 16F),
                            (7F / 16F), (3F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (1F / 16F),
                            (7F / 16F), (6F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (1F / 16F),
                            (7F / 16F), (9F / 16F), (7F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (1F / 16F),
                            (7F / 16F), (12F / 16F), (7F / 16F)
                    )
            },
            new AxisAlignedBB[]{
                    // SW
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (9F / 16F),
                            (15F / 16F), (3F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (9F / 16F),
                            (15F / 16F), (6F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (9F / 16F),
                            (15F / 16F), (9F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (9F / 16F), 0, (9F / 16F),
                            (15F / 16F), (12F / 16F), (15F / 16F)
                    )
            },
            new AxisAlignedBB[]{
                    // SE
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (9F / 16F),
                            (7F / 16F), (3F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (9F / 16F),
                            (7F / 16F), (6F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (9F / 16F),
                            (7F / 16F), (9F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (1F / 16F), 0, (9F / 16F),
                            (7F / 16F), (12F / 16F), (15F / 16F)
                    )
            },

    };

    private final AxisAlignedBB[][] diamondBounds = new AxisAlignedBB[][]{
            new AxisAlignedBB[]{
                    // N
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (0F / 16F),
                            (11F / 16F), (3F / 16F), (6F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (0F / 16F),
                            (11F / 16F), (6F / 16F), (6F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (0F / 16F),
                            (11F / 16F), (9F / 16F), (6F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (0F / 16F),
                            (11F / 16F), (12F / 16F), (6F / 16F)
                    ),
            },
            new AxisAlignedBB[]{
                    // W
                    AxisAlignedBB.getBoundingBox(
                            (10F / 16F), 0, (5F / 16F),
                            (16F / 16F), (3F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (10F / 16F), 0, (5F / 16F),
                            (16F / 16F), (6F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (10F / 16F), 0, (5F / 16F),
                            (16F / 16F), (9F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (10F / 16F), 0, (5F / 16F),
                            (16F / 16F), (12F / 16F), (11F / 16F)
                    ),
            },
            new AxisAlignedBB[]{
                    // E
                    AxisAlignedBB.getBoundingBox(
                            (0F / 16F), 0, (5F / 16F),
                            (6F / 16F), (3F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (0F / 16F), 0, (5F / 16F),
                            (6F / 16F), (6F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (0F / 16F), 0, (5F / 16F),
                            (6F / 16F), (9F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (0F / 16F), 0, (5F / 16F),
                            (6F / 16F), (12F / 16F), (11F / 16F)
                    ),
            },
            new AxisAlignedBB[]{
                    // S
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (9F / 16F),
                            (11F / 16F), (3F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (9F / 16F),
                            (11F / 16F), (6F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (9F / 16F),
                            (11F / 16F), (9F / 16F), (15F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (9F / 16F),
                            (11F / 16F), (12F / 16F), (15F / 16F)
                    ),
            },
    };

    @Override
    @Environment(EnvType.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, MovingObjectPosition hit) {
        int i = hit.blockX;
        int j = hit.blockY;
        int k = hit.blockZ;

        Vec3 hitVec = Vec3
                .createVectorHelper(hit.hitVec.xCoord - i, hit.hitVec.yCoord - j, hit.hitVec.zCoord - k);

        BowlStackTileEntity bowl = (BowlStackTileEntity) world.getBlockTileEntity(i, j, k);

        if (bowl.centerPositions[0] > 0){
            AxisAlignedBB[] center = new AxisAlignedBB[]{
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (5F / 16F),
                            (11F / 16F), (3F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (5F / 16F),
                            (11F / 16F), (6F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (5F / 16F),
                            (11F / 16F), (9F / 16F), (11F / 16F)
                    ),
                    AxisAlignedBB.getBoundingBox(
                            (5F / 16F), 0, (5F / 16F),
                            (11F / 16F), (12F / 16F), (11F / 16F)
                    )
            };


            return center[Math.max(bowl.centerPositions[0] - 1, 0)].offset(i,j,k);
        }
        else {
            // Show boxes for already occupied slots and possible placeable slots

            //square hit
            if (hitVec.zCoord < 5/16F) {
                if (hitVec.xCoord < 5/16F) {
                    if (bowl.setSquarePositions( 1, 0)) return squareBounds[1][Math.max(bowl.squarePositions[1] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
                else if (hitVec.xCoord > 11/16F) {
                    if (bowl.setSquarePositions(0, 0)) return squareBounds[0][Math.max(bowl.squarePositions[0] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
            }
            else if (hitVec.zCoord > 11/16F) {
                if (hitVec.xCoord < 5/16F) {
                    if (bowl.setSquarePositions(3, 0)) return squareBounds[3][Math.max(bowl.squarePositions[3] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
                else if (hitVec.xCoord > 11/16F) {
                    if (bowl.setSquarePositions(2, 0)) return squareBounds[2][Math.max(bowl.squarePositions[2] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
            }

            //diamond
            if (hitVec.zCoord < 5/16F) {
                if (hitVec.xCoord > 5/16F && hitVec.xCoord < 11/16F) {
                    if ( bowl.setDiamondPositions(0, 0) ) return diamondBounds[0][Math.max(bowl.diamondPositions[0] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
            }
            else if (hitVec.zCoord < 11/16F) {
                if (hitVec.xCoord < 5/16F ){
                    if (bowl.setDiamondPositions(2, 0)) return diamondBounds[2][Math.max(bowl.diamondPositions[2] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
                if (hitVec.xCoord > 11/16F) {
                    if (bowl.setDiamondPositions(1, 0)) return diamondBounds[1][Math.max(bowl.diamondPositions[1] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
            }
            else {
                if (hitVec.xCoord > 5/16F && hitVec.xCoord < 11/16F) {
                    if (bowl.setDiamondPositions(3, 0)) return diamondBounds[3][Math.max(bowl.diamondPositions[3] - 1, 0)].makeTemporaryCopy().offset(i,j,k);
                }
            }


        }

        return getFixedBlockBoundsFromPool();

    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
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
    public void registerIcons(IconRegister register) {
        blockIcon = register.registerIcon("tree_side");
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
