package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.FlowerBlock;
import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.crafting.util.FurnaceBurnTime;
import net.minecraft.src.*;

import java.util.List;

public class SideShroomBlock extends FlowerBlock {

    public static final String[] MUSHROOM_TYPES = {"white", "black", "brown", "red" };
    public SideShroomBlock(int blockID, String name) {
        super(blockID);

        setFurnaceBurnTime( FurnaceBurnTime.KINDLING );

        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(name);
    }

    @Override
    public int damageDropped(int meta) {
        if (getType(meta) == 1)
        {
            return 4;
        }
        if (getType(meta) == 2)
        {
            return 8;
        }
        if (getType(meta) == 3)
        {
            return 12;
        }
        else return 0;
    }


    @Override
    //Copied from BlockButton
    public boolean canPlaceBlockOnSide(World world, int x, int y, int z, int side)
    {
        int blockID = 0;
        if (side == 2){
            blockID = world.getBlockId(x,y,z + 1);
        }
        else if (side == 3){
            blockID = world.getBlockId(x,y,z -1);
        }
        else if (side == 4){
            blockID = world.getBlockId(x + 1,y,z);
        }
        else if (side == 5){
            blockID = world.getBlockId(x - 1,y,z);
        }

        Block block = Block.blocksList[blockID];
        if ( blockID != 0 && block.hasLargeCenterHardPointToFacing(world, x,y,z, side))
        {
            return true;
        }
//        else return false;
        return side == 2 && world.isBlockNormalCube(x, y, z + 1) ? true : (side == 3 && world.isBlockNormalCube(x, y, z - 1) ? true : (side == 4 && world.isBlockNormalCube(x + 1, y, z) ? true : side == 5 && world.isBlockNormalCube(x - 1, y, z)));
    }

    //Copied from BlockButton
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x,y,z);
        int dir = getDirection(meta);
        if ((world.getBlockId(x - 1, y, z) == SCBlocks.hollowLog.blockID && dir == WEST )
            || (world.getBlockId(x + 1, y, z) == SCBlocks.hollowLog.blockID && dir == EAST )
            || (world.getBlockId(x, y, z - 1) == SCBlocks.hollowLog.blockID && dir == NORTH)
            || (world.getBlockId(x, y, z + 1) == SCBlocks.hollowLog.blockID && dir == SOUTH)){
            return true;
        }
        return world.isBlockNormalCube(x - 1, y, z) ? true : (world.isBlockNormalCube(x + 1, y, z) ? true : (world.isBlockNormalCube(x, y, z - 1) ? true : world.isBlockNormalCube(x, y, z + 1)));
    }

    @Override
    public boolean canBlockStay( World world, int i, int j, int k )
    {
        return canPlaceBlockAt(world, i, j, k);
    }

    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        par3List.add(new ItemStack(par1, 1, 0));
        par3List.add(new ItemStack(par1, 1, 4));
        par3List.add(new ItemStack(par1, 1, 8));
        par3List.add(new ItemStack(par1, 1, 12));
    }
    //Direction
    private final int SOUTH = 0;
    private final int WEST = 1;
    private final int NORTH = 2;
    private final int EAST = 3;

    @Override
    public int onBlockPlaced( World world, int i, int j, int k, int targetFace, float fClickX, float fClickY, float fClickZ, int iMetadata )
    {
        //System.out.println("TargetFace: " + targetFace);

        if (targetFace > 1)
        {
            if (targetFace == 2)
                return SOUTH;
            else if (targetFace == 3)
                return NORTH;
            else if (targetFace == 4)
                return EAST;
            else
                return WEST;
        }
        else
        {
            return iMetadata;
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving player, ItemStack stack) {

        //int playerRotation = ((MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3) + 2) % 4;
        int itemDamage = stack.getItemDamage();
        int meta = world.getBlockMetadata(i, j, k);
//		System.out.println(playerRotation);

        world.setBlockAndMetadata(i, j, k, this.blockID, meta + (itemDamage) );
    }

    //Copied from BlockButton and modified
    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighbour) {
        boolean shouldDrop = false;
        int meta = world.getBlockMetadata(x, y, z);
        int dir = getDirection(meta);
        Block blockw = Block.blocksList[world.getBlockId(x-1,y,z)];
        Block blocke = Block.blocksList[world.getBlockId(x+1,y,z)];
        Block blockn = Block.blocksList[world.getBlockId(x,y,z-1)];
        Block blocks = Block.blocksList[world.getBlockId(x,y,z+1)];

        if ((!world.isBlockNormalCube(x - 1, y, z) && !(blockw instanceof HollowLogBlock)) && dir == WEST)
        {
            shouldDrop = true;
        }

        if ((!world.isBlockNormalCube(x + 1, y, z) && !(blocke instanceof HollowLogBlock)) && dir == EAST)
        {
            shouldDrop = true;
        }

        if ((!world.isBlockNormalCube(x, y, z - 1) && !(blockn instanceof HollowLogBlock)) && dir == NORTH)
        {
            shouldDrop = true;
        }

        if ((!world.isBlockNormalCube(x, y, z + 1) && !(blocks instanceof HollowLogBlock)) && dir == SOUTH)
        {
            shouldDrop = true;
        }

        if (shouldDrop)
        {
            this.dropBlockAsItem(world, x, y, z, damageDropped(world.getBlockMetadata(x,y,z)), 0);
            world.setBlockToAir(x, y, z);
        }
    }

    private int getDirection(int meta) {
        return meta & 3;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public Icon getIcon(int side, int metadata) {
        return blockIcon = bottom[getType(metadata)];
    }

    private Icon[] top = new Icon[4];
    private Icon[] middle = new Icon[4];
    private Icon[] bottom = new Icon[4];

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < top.length; i++) {
            top[i] = register.registerIcon("sideshroom_" + MUSHROOM_TYPES[i] + "_0");
            middle[i] = register.registerIcon("sideshroom_" + MUSHROOM_TYPES[i] + "_1");
            bottom[i] = register.registerIcon("sideshroom_" + MUSHROOM_TYPES[i] + "_2");
        }
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {

        float centerX = 8/16F;
        float centerY = 8/16F;
        float centerZ = 8/16F;

        float width = 14/16F;
        float height = 8/16F;
        float depth = 6/16F;

        int meta = blockAccess.getBlockMetadata(i, j, k);
        int dir = getDirection(meta);

        if (dir == NORTH )
        {
            centerZ = centerZ - 5/16F;
        }
        else if (dir == SOUTH)
        {
            centerZ = centerZ + 5/16F;
        }
        else if (dir == EAST)
        {
            width = 6/16F;
            depth = 14/16F;

            centerX = centerX + 5/16F;
        }
        else if (dir == WEST)
        {
            width = 6/16F;
            depth = 14/16F;

            centerX = centerX - 5/16F;
        }


        AxisAlignedBB box = new AxisAlignedBB(
                centerX - width/2, centerY - height/2, centerZ - depth/2,
                centerX + width/2, centerY + height/2, centerZ + depth/2
        );

        return box;
    }

//	private final int SOUTH = 0;
//	private final int WEST = 1;
//	private final int NORTH = 2;
//	private final int EAST = 3;

    @Override
    public boolean renderBlock(RenderBlocks renderer, int x, int y, int z) {

        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        int blockID = renderer.blockAccess.getBlockId(x, y, z);
        Block block = Block.blocksList[blockID];

        Tessellator tess = Tessellator.instance;
        tess.setBrightness(block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float brightness = 1.0F;
        int colorMultiplier = block.colorMultiplier(renderer.blockAccess, x, y, z);
        float r = (float)(colorMultiplier >> 16 & 255) / 255.0F;
        float g = (float)(colorMultiplier >> 8 & 255) / 255.0F;
        float b = (float)(colorMultiplier & 255) / 255.0F;
        tess.setColorOpaque_F(brightness * r, brightness * g, brightness * b);

        renderer.setRenderBounds(0,0,0,1,1,1);

        int dir = getDirection(meta);

//        if (dir == EAST)
//        {
//            renderer.setUVRotateTop(2);
//            renderer.setUVRotateBottom(1);
//        }
//        else if (dir == WEST)
//        {
//            renderer.setUVRotateTop(1);
//            renderer.setUVRotateBottom(2);
//        }
//        else if (dir == NORTH)
//        {
//            renderer.setUVRotateTop(3);
//            renderer.setUVRotateBottom(3);
//        }

        long hash = (long) (x * 3129871L) ^ (long) z * 116129781L ^ (long)y;
        hash = hash * hash * 42317861L + hash * 11L;

        // Generate values for numberOfMushrooms and random mushroom types
        int numberOfMushrooms = (int) (Math.abs(hash) % 3);
        int randMushroom1 = (int) (Math.abs(hash >> 2) % 3);
        int randMushroom2 = (int) (Math.abs(hash >> 4) % 3);
        int randMushroom3 = (int) (Math.abs(hash >> 6) % 3);

        Icon[][][] mushroom = new Icon[3][][];
        mushroom[0] = new Icon[][]{top, middle, bottom};
        mushroom[1] = new Icon[][]{middle, bottom, top};
        mushroom[2] = new Icon[][]{bottom, top, middle};

        if (numberOfMushrooms == 0)
        {
            renderMushroomLayer(renderer, this, x, y, z,0, meta, mushroom[0][randMushroom1]);
        }

        if (numberOfMushrooms == 1)
        {
            // Check and ensure that randMushroom2 and randMushroom3 are not the same
            if (randMushroom2 == randMushroom3) {
                randMushroom3 = (randMushroom3 + 1) % 3;  // Increment and wrap to ensure it's different
            }

            renderMushroomLayer(renderer, this, x, y, z,-3/16F, meta, mushroom[1][randMushroom2]);
            renderMushroomLayer(renderer, this, x, y, z,3/16F, meta, mushroom[1][randMushroom3]);
        }

        if (numberOfMushrooms == 2)
        {
            // Ensure randMushroom1 and randMushroom2 are not the same
            if (randMushroom1 == randMushroom2) {
                randMushroom2 = (randMushroom2 + 1) % 3;  // Change randMushroom2 to a different value
            }

            // Ensure randMushroom1 and randMushroom3 are not the same
            if (randMushroom1 == randMushroom3) {
                randMushroom3 = (randMushroom3 + 1) % 3;  // Change randMushroom3 to a different value
            }

            // Ensure randMushroom2 and randMushroom3 are not the same
            if (randMushroom2 == randMushroom3) {
                randMushroom3 = (randMushroom3 + 1) % 3;  // Change randMushroom3 to avoid matching randMushroom2
            }

            renderMushroomLayer(renderer, this, x, y, z, -4/16F, meta, mushroom[2][randMushroom1]);
            renderMushroomLayer(renderer, this, x, y, z, 0/16F, meta, mushroom[2][randMushroom2]);
            renderMushroomLayer(renderer, this, x, y, z,4/16F, meta, mushroom[2][randMushroom3]);
        }

        renderer.clearUVRotation();

        return true;
    }

    private void renderMushroomLayer(RenderBlocks renderer,  Block block, int x, int y, int z, float yShift, int metadata, Icon[] icons) {
        int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        int dir = getDirection(meta);

        double rotateX = -12.5D;
        double rotateY = 0;
        double rotateZ = 0;

        double xShift = 0;
        double zShift = 0;

        if (dir == EAST)
        {
            rotateY = 90;
            xShift = -1/64D;
        }
        else if (dir == WEST)
        {
            rotateY = -90;
            xShift = 1/64D;
        }
        else if (dir == NORTH)
        {
            rotateY = 180;
            zShift = 1/64D;
        }
        else if (dir == SOUTH)
        {
            zShift = -1/64D;
        }

        SCRenderUtils.renderHorizonzalPaneWithRotation(renderer, block, x,y,z,
                xShift, yShift, zShift,
                rotateX, rotateY, rotateZ,
                icons[getType(meta)],
                icons[getType(meta)]);
    }

    public static int getType(int meta) {
        if (meta < 4)
        {
            return 0;
        }
        else if (meta < 8)
        {
            return 1;
        }
        else if (meta < 12)
        {
            return 2;
        }
        else return 3;
    }
}

