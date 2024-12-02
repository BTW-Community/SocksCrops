package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.FlowerBlock;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

import java.util.Random;

public class MultiFlowerBlock extends FlowerBlock {

    public MultiFlowerBlock(int blockID, String name) {
        super(blockID);
        initBlockBounds(0.5F - 7/16D, 0.0F, 0.5F - 7/16D, 0.5F + 7/16D, 3/16D, 0.5F + 7/16D);
        setUnlocalizedName(name);
        setCreativeTab(CreativeTabs.tabDecorations);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return this.blockID;
    }

    @Override
    public int damageDropped(int par1) {
        return 0;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, int iBlockID, int iMetadata) {
        if (world.getBlockId(x,y + 1,z) == Block.snow.blockID) world.setBlockToAir(x,y + 1,z);
        super.breakBlock(world, x, y, z, iBlockID, iMetadata);
//        int numberDropped = getStage(iMetadata);
//        for (int i = 0; i < numberDropped; i++) {
//            dropBlockAsItem(world, x,y,z, iMetadata, 0);
//        }
    }

    public static int getDirection(int metadata)
    {
        return metadata & 3;
    }

    public static int getStage(int metadata) {
        return (metadata >> 2) & 3;  // This shifts right by 2 bits and extracts the next 2 bits
    }

    public static int setDirection(int metadata, int direction) {
        // Ensure direction is within valid range (0 to 3)
        direction = direction & 3;  // Mask the input direction to use only 2 bits
        // Clear the first 2 bits (direction bits) and set the new direction
        return (metadata & ~3) | direction;
    }

    // Method to set the stage (using the next 2 bits)
    public static int setStage(int metadata, int stage) {
        // Ensure stage is within valid range (0 to 3)
        stage = stage & 3;  // Mask the input stage to use only 2 bits
        // Clear the 2 bits at position 2 and 3 (stage bits) and set the new stage
        return (metadata & ~(3 << 2)) | (stage << 2);
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
        ItemStack heldStack = player.getHeldItem();
        if (heldStack != null && heldStack.itemID == this.blockID || Block.blocksList[heldStack.itemID] instanceof MultiFlowerBlock)
        {
            int meta = world.getBlockMetadata(i,j,k);
            int stage = getStage(meta);

            if (stage < 3) {
                world.setBlockMetadataWithNotify(i,j,k, setStage(meta, stage + 1));
//                heldStack.stackSize--;
                return true;
            }

        }

        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving player, ItemStack stack) {
        int playerRot = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;

        world.setBlockMetadataWithNotify(x,y,z, setDirection(0, playerRot));
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return super.canPlaceBlockAt(world, i, j, k);
    }

    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {

        if (grassPass)
        {
            return par1IBlockAccess.getBiomeGenForCoords(par2, par4).getBiomeGrassColor();
        }

        return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    private Icon[] flower = new Icon[4];
    private static Icon[] stalk = new Icon[4];
    private static Icon[] stalkFlower = new Icon[2];

    protected boolean grassPass;

    @Override
    public Icon getIcon(int side, int meta) {
        int dir = getDirection(meta);
        return flower[dir];
    }

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < flower.length; i++) {
            flower[i] = register.registerIcon("pink_petals_"+i);
        }

        for (int i = 0; i < flower.length; i++) {
            stalk[i] = register.registerIcon("petals_stalk");
        }

        stalkFlower[0] = register.registerIcon("petals_stalk");
        stalkFlower[1] = register.registerIcon("petals_stalk");

        this.blockIcon = flower[0];
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {
        int meta = renderer.blockAccess.getBlockMetadata(x,y,z);
        int stage = MultiFlowerBlock.getStage(meta);
        int dir = MultiFlowerBlock.getDirection(meta);

        SCRenderUtils.renderMultiFlowerBlock(renderer, this, x, y, z, flower);

        grassPass = true;
        int[] numberOfStalks = {
                3, 1, 3, 1
        };
        double[][][] shifts = {
                {
                        {3/32D, 3/32D}, {13/32D, 5/32D}, {8/32D, 12/32D}, // (xShift, zShift) for stage >= 0
                },
                {
                        {7/32D, -9/32D},    // (xShift, zShift) for stage >= 1
                },
                {
                        {-3/32D, -3/32D}, {-13/32D, -5/32D}, {-7/32D, -13/32D}, // (xShift, zShift) for stage >= 2
                },
                {
                        {-7/32D, 7/32D} // (xShift, zShift) for stage >= 3
                }
        };
        SCRenderUtils.renderStalks(renderer, this, x, y, z,
                stage, dir,
                numberOfStalks, shifts,
                stalk);
        grassPass = false;

        return true;
    }
}
