package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import btw.community.sockthing.sockscrops.item.SCItems;
import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import btw.item.items.AxeItem;
import btw.item.items.ShearsItem;
import btw.item.util.ItemUtils;
import net.minecraft.src.*;

import java.util.Random;

public class BambooRootBlock extends BlockFlower implements BlockInterface {
    private static final float BAMBOO_ROOT_GROWTH_CHANCE = 0.2F;
    private final int returnItemID;
    protected String stalkTexture;
    protected String topTexture;

    public BambooRootBlock(int blockID, String name, boolean tickRandomly, int returnItemID, String stalkTexture, String topTexture) {
        super(blockID, Material.wood);
        setHardness(0.75F);

        setAxesEffectiveOn();

        setStepSound(soundWoodFootstep);
        setUnlocalizedName(name);

        setTickRandomly(tickRandomly);

        this.returnItemID = returnItemID;
        this.stalkTexture = stalkTexture;
        this.topTexture = topTexture;
    }

    @Override
    public boolean canBeReplacedByLeaves(int blockID) {
        return false;
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k )
    {
        return 3;
    }

    @Override
    public int getEfficientToolLevel( IBlockAccess blockAccess, int i, int j, int k )
    {
        return 1;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return this.returnItemID;
    }

    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    @Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
        super.onBlockClicked(world, x, y, z, player);

        int height = 1;
        for (int i = 1; i <= 16; i++) {
            if (Block.blocksList[world.getBlockId(x,y + i,z)] instanceof  BambooStalkBlock) {
                height++;
            }
        }

        setHardness(0.75F * height);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        int maxGrowHeight = world.getBlockMetadata(x, y, z);

        //Bamboo growth
        if ( world.provider.dimensionId == 0 )
        {
            if ( random.nextFloat() <= BAMBOO_ROOT_GROWTH_CHANCE && world.isAirBlock( x, y + 1, z ) && maxGrowHeight > 0  )
            {
                //System.out.println("Bamboo Root["+x+","+y+","+z+"]: "+"Growing Bamboo Stalk above.");
                world.setBlockAndMetadataWithNotify( x, y + 1, z, SCBlocks.bambooStalk.blockID , maxGrowHeight);
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {

        ItemStack stack = player.getCurrentEquippedItem();

        if (!world.isRemote)
        {
            if ( stack != null )
            {
                Item item = stack.getItem();

                if ( world.getBlockMetadata(i, j, k) > 0 && item instanceof ShearsItem
                        && Block.blocksList[world.getBlockId(i, j, k)] instanceof BambooRootBlock
                        && !(Block.blocksList[world.getBlockId(i, j, k)] instanceof BambooStalkBlock ))
                {
                    world.setBlockMetadataWithNotify(i, j, k, 0); //sets this block to inactive

                    if (Block.blocksList[world.getBlockId(i, j + 1, k)] instanceof BambooStalkBlock)
                    {
                        world.setBlockMetadata(i, j + 1, k, 0);
                    }

                    player.playSound("mob.sheep.shear", 1.0F, 1.0F);
                    player.getHeldItem().damageItem(1, player);

                    ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCBlocks.bambooShoot), iFacing);

                    return true;
                }
                else if ( item instanceof AxeItem
                        && (this.blockID != SCBlocks.strippedBambooRoot.blockID
                        && this.blockID != SCBlocks.strippedBambooStalk.blockID) )
                {
                    int currentMeta = world.getBlockMetadata(i,j,k);
                    world.setBlockAndMetadataWithNotify(i, j, k, this.blockID + 2, currentMeta); //sets this block to inactive

                    world.playAuxSFX(2001, i,j,k, this.blockID);
                    player.playSound("mob.zombie.woodbreak", 1.0F, 1.0F);
                    player.getHeldItem().damageItem(1, player);
//                    ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(SCItems.strippedBamboo), iFacing);

                    return true;
                }
            }
        }

        return false;
    }


    @Override
    protected boolean canGrowOnBlock( World world, int i, int j, int k )
    {
        Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];

        return blockOn != null && blockOn.canReedsGrowOnBlock( world, i, j, k );
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k)
    {
        return getBambooRenderBounds(2, i, j, k);
    }

    protected static AxisAlignedBB getBambooRenderBounds(int width, int i, int j, int k)
    {
        double newCenterX = getOffset(i, j, k, 0);
        double newCenterZ = getOffset(i, j, k, 1);

        AxisAlignedBB bambooBounds = AxisAlignedBB.getAABBPool().getAABB(
                newCenterX - width /16D, 0/16D, newCenterZ - width /16D,
                newCenterX + width /16D, 16/16D, newCenterZ + width /16D );

        return bambooBounds;
    }

    public static double getOffset(int i, int j, int k, int xOrZ) {
        //copied from RenderBlocks for TallGrass
        //adapted with help from Zhil and ExpHP
        double newI = (double)i;
        double newJ = (double)j;
        double newK = (double)k;

        //long var17 = (long)(i * 3129871) ^ (long)k * 116129781L ^ (long)j;
        long hash = (long)(i * 3129871) ^ (long)k * 116129781L;
        hash = hash * hash * 42317861L + hash * 11L;
        newI += ((double)((float)(hash >> 16 & 15L) / 15.0F) - 0.5D) * 0.5D;
        newJ += ((double)((float)(hash >> 20 & 15L) / 15.0F) - 1.0D) * 0.2D;
        newK += ((double)((float)(hash >> 24 & 15L) / 15.0F) - 0.5D) * 0.5D;

        double centerX = 8/16D;
        double centerZ = 8/16D;

        double newCenterX = newI - i + centerX;
        double newCenterZ = newK - k + centerZ;

        double c = 0.0D;

        newCenterX = ( Math.floor( newCenterX * 4 + c ) - c ) / 4;
        newCenterZ = ( Math.floor( newCenterZ * 4 + c ) - c ) / 4;

        newCenterX -= 2/16D;
        newCenterZ -= 2/16D;

        if (xOrZ == 0)
        {
            return newCenterX;
        }
        else return newCenterZ;

    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK,
                                        int iSide) {
        return true;
    }

    //----------- Client Side Functionality -----------//

    protected Icon stalk;
    protected Icon m_IconTop;
    protected Icon m_IconRoots;
    protected Icon smallLeaves;
    private Icon[] m_iconArray;
    private Icon[] m_iconTopArray;

    @Override
    public void registerIcons( IconRegister register )
    {
        blockIcon = stalk = register.registerIcon( this.stalkTexture );
        //blockIcon = register.registerIcon( "SCBlockBambooRoot_display" );
        m_IconRoots = register.registerIcon( "bamboo_root" );
        smallLeaves = register.registerIcon( "bamboo_leaves_side" );
        m_IconTop = register.registerIcon( this.topTexture);

    }

    @Override
    public Icon getIcon(int side, int meta)
    {
        if ( side == 1 )
        {
            return m_IconTop;
        }
        else if ( side == 0 )
        {
            return m_IconTop;
        }
        else if (side > 1 && side < 6)
        {
            return stalk;
        }
        else if (side ==  7) return smallLeaves;

        return blockIcon;
    }

    public int getRenderType()
    {
        return 1;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        //super.RenderBlock(renderer, i, j, k); //Leaves cube
        IBlockAccess blockAccess = renderer.blockAccess;
        int meta = blockAccess.getBlockMetadata( i, j, k );

        renderer.setRenderBounds(getBambooRenderBounds(2, i, j, k)); //4px x 4px
        renderer.renderStandardBlock(this, i, j, k);

        if (meta != 0)
        {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, m_IconRoots, true, 4);
        }

        Block blockAbove = Block.blocksList[blockAccess.getBlockId(i, j+1, k)];


        if (!(blockAbove instanceof BambooStalkBlock)) {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, smallLeaves, true, 4);
        }

        return true;
    }
}
