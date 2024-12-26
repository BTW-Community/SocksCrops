package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.utils.SCRenderUtils;
import net.minecraft.src.*;

import java.util.Random;

public class BambooStalkBlock extends BambooRootBlock {

    private static final float BAMBOO_STALK_GROWTH_CHANCE = 0.2F;

    public BambooStalkBlock(int blockID, String name, boolean tickRandomly, int returnItemID, String stalkTexture, String topTexture) {
        super(blockID, name, tickRandomly, returnItemID, stalkTexture, topTexture);

        initBlockBounds(6/16F, 0.0F, 6/16F,
                10/16F, 1.0F, 10/16F);

        setCreativeTab(null);
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random random) {
        //Bamboo growth
        int maxGrowHeight = world.getBlockMetadata(x, y, z); //random height between 7 and 15

        if ( world.provider.dimensionId == 0)
        {
            if ( world.getBlockMetadata(x, y, z) != 0)
            {

                if ( random.nextFloat() <= BAMBOO_STALK_GROWTH_CHANCE && world.isAirBlock( x, y + 1, z ) ) //Sugar can has a chance of 1 in 2 to grow
                {
                    int iReedHeight = 1;

                    //System.out.println("Bamboo Stalk["+x+","+y+","+z+"]: "+"my current maxGrowHeight is: " + maxGrowHeight);

                    while ( world.getBlockId( x, y - iReedHeight, z ) == blockID )
                    {
                        iReedHeight++;
                        //System.out.println("Bamboo Stalk["+x+","+y+","+z+"]: "+"my current reedHeight is: " + iReedHeight);
                    }

                    if ( iReedHeight < maxGrowHeight )
                    {
                        int iMetadata = world.getBlockMetadata( x, y, z );


                        //System.out.println("Bamboo["+x+","+y+","+z+"]: "+"Growing Bamboo above.");
                        world.setBlockAndMetadataWithNotify( x, y + 1, z, blockID , maxGrowHeight);

                    }

                }
            }
            else
            {
                if (world.getBlockId(x, y + 1, z) == this.blockID)
                {
                    world.setBlockMetadata(x, y + 1, z, 0);
                }
            }
        }
    }

    @Override
    protected boolean canGrowOnBlock( World world, int i, int j, int k )
    {
        Block blockOn = Block.blocksList[world.getBlockId( i, j, k )];

        return blockOn != null && ( blockOn instanceof BambooRootBlock || blockOn.blockID == this.blockID ); //blockOn.CanReedsGrowOnBlock( world, i, j, k );
    }

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        super.onNeighborBlockChange(par1World, par2, par3, par4, par5);

        if (!this.canBlockStay(par1World, par2, par3, par4))
        {
            this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
            par1World.setBlockToAir(par2, par3, par4);
        }
    }

    protected Icon m_IconTop;
    protected Icon m_IconRoots;
    private Icon bigLeaves;
    private Icon smallLeaves;

    public void registerIcons( IconRegister register )
    {
        super.registerIcons(register);
        this.blockIcon = register.registerIcon( this.stalkTexture);
        bigLeaves = register.registerIcon( "bamboo_leaves_large" );
        smallLeaves = register.registerIcon( "bamboo_leaves_small" );
        m_IconTop = register.registerIcon( this.topTexture );
    }

    @Override
    public Icon getIcon(int side, int meta )
    {
        if ( side == 1 )
        {
            return m_IconTop;
        }
        return blockIcon;
    }

    @Override
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        //super.RenderBlock(renderer, i, j, k); //Leaves cube

        IBlockAccess blockAccess = renderer.blockAccess;
        int meta = blockAccess.getBlockMetadata( i, j, k );

        //SCUtilsRender.RenderCrossedSquaresWithTexture(renderer, this, i, j, k, m_IconRoots);

        Block blockAbove = Block.blocksList[blockAccess.getBlockId(i, j+1, k)];
        Block blockTwoAbove = Block.blocksList[blockAccess.getBlockId(i, j+2, k)];
        Block blockBelow = Block.blocksList[blockAccess.getBlockId(i, j-1, k)];

        if (!(blockAbove instanceof BambooStalkBlock) && blockBelow instanceof BambooStalkBlock) {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, bigLeaves, true, 4);

        }

        if (!(blockAbove instanceof BambooStalkBlock) && blockBelow instanceof BambooStalkBlock) {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, smallLeaves,true, 4);
        }

        if (blockAbove instanceof BambooStalkBlock && blockBelow instanceof BambooStalkBlock && !(blockTwoAbove instanceof BambooStalkBlock))
        {
            SCRenderUtils.renderCrossedSquaresWithTexture(renderer, this, i, j, k, smallLeaves,true, 4);
        }

        renderer.setRenderBounds(getBambooRenderBounds(2, i, j, k)); //4px x 4px
        renderer.renderStandardBlock(this, i, j, k);

        return true;
    }
}
