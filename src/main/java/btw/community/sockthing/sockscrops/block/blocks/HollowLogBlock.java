package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.blocks.FenceBlock;
import btw.block.blocks.PlanksBlock;
import btw.block.model.BlockModel;
import btw.block.model.FenceModel;
import btw.block.util.RayTraceUtils;
import btw.client.render.util.RenderUtils;
import btw.community.sockthing.sockscrops.block.models.HollowLogModel;
import btw.community.sockthing.sockscrops.interfaces.BlockInterface;
import btw.entity.mob.ChickenEntity;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class HollowLogBlock extends BaseLogBlock implements BlockInterface {

    protected static final HollowLogModel model = new HollowLogModel();
    public static final String[] treeTypes = new String[] {"oak", "spruce", "birch", "jungle"};
    public static final String[] treeTypes2 = new String[] {"blood"};

    public static final String[] decoTreeTypes = new String[] {"darkOak", "acacia", "mahogany", "mangrove"};
    public static final String[] decoTreeTypes2 = new String[] {"hazel", "fir", "aspen", "willow"};
    public static final String[] decoTreeTypes3 = new String[] {"cherry", "redwood"};
    public static final String[] treeTextureTop = new String[] {"hollow_log_oak_top", "hollow_log_spruce_top", "hollow_log_birch_top", "hollow_log_jungle_top"};
    public static final String[] treeTextureInner = new String[] {"fcBlockLogStrippedOak_side", "fcBlockLogStrippedSpruce_side", "fcBlockLogStrippedBirch_side", "fcBlockLogStrippedJungle_side"};
    public static final String[] treeTextureSide = new String[] {"tree_side", "tree_spruce", "tree_birch", "tree_jungle"};

    public static final String[] treeTextureTop2 = new String[] {"hollow_log_blood_top"};
    public static final String[] treeTextureInner2 = new String[] {"hollow_log_blood_inner"};
    public static final String[] treeTextureSide2 = new String[] {"fcBlockBloodWood_side"};

    private final String name;
    private final String[] topTextures;
    private final String[] innerTextures;
    private final String[] sideTextures;

    private final float hardnessModifier = 1F;

    public HollowLogBlock(int blockID, String name, String[] topTextures, String[] innerTextures, String[] sideTextures) {
        super(blockID);

        setHardness(1.25F * this.hardnessModifier); // vanilla 2
        setResistance(3.33F  * this.hardnessModifier);  // odd value to match vanilla resistance set through hardness of 2

        setCreativeTab(CreativeTabs.tabDecorations);
        setUnlocalizedName(name);
        this.name = name;
        this.topTextures = topTextures;
        this.innerTextures = innerTextures;
        this.sideTextures = sideTextures;

    }

    @Override
    public int getFurnaceBurnTime(int iItemDamage) {
        return PlanksBlock.getFurnaceBurnTimeByWoodType(iItemDamage); //quarter of regular logs
    }


    @Environment(EnvType.CLIENT)
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int i = 0; i < topTextures.length; i++) {
            par3List.add(new ItemStack(par1, 1, i));
        }
    }


    @Override
    public boolean canBeReplacedByLeaves(int blockID) {
        return false;
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return this.blockID;
    }

    @Override
    public boolean dropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChanceOfDrop )
    {
        dropItemsIndividually( world, i, j, k, BTWItems.sawDust.itemID, 1, 0, fChanceOfDrop );
        dropItemsIndividually( world, i, j, k, BTWItems.bark.itemID, 1, iMetadata & 3, fChanceOfDrop );

        return true;
    }

    @Override
    public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB boundingBox, List list, Entity entity) {
//        if (entity instanceof ChickenEntity || (entity instanceof EntityAgeable && ((EntityAgeable)entity).isChild() ))
//        {
//            BlockModel tempModel = new BlockModel();
//            tempModel.addBox(0,0,0,1,1,1);
//            tempModel.addIntersectingBoxesToCollisionList(world, i, j, k, boundingBox, list);
//        }
//        else {
//            BlockModel tempModel = assembleTemporaryModel(world, i,j,k);
//            tempModel.addIntersectingBoxesToCollisionList(world, i, j, k, boundingBox, list);
//        }

        BlockModel tempModel = assembleTemporaryModel(world, i,j,k);
        tempModel.addIntersectingBoxesToCollisionList(world, i, j, k, boundingBox, list);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockAccess blockAccess, int i, int j, int k) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public MovingObjectPosition collisionRayTrace( World world, int i, int j, int k, Vec3 startRay, Vec3 endRay )
    {
        RayTraceUtils rayTrace = new RayTraceUtils( world, i, j, k, startRay, endRay );

        BlockModel transformedModel = assembleTemporaryModel(world, i,j,k);

        transformedModel.addToRayTrace(rayTrace);

        return rayTrace.getFirstIntersection();
    }

    protected BlockModel assembleTemporaryModel(IBlockAccess blockAccess, int i, int j, int k) {
//        BlockModel tempModel = model.makeTemporaryCopy();
//
//        for (int iTempFacing = 2; iTempFacing <= 5; iTempFacing++) {
//            BlockModel tempSupportsModel = model.side.makeTemporaryCopy();
//
//            tempSupportsModel.rotateAroundYToFacing(iTempFacing);
//
//            tempSupportsModel.makeTemporaryCopyOfPrimitiveList(tempModel);
//        }

        BlockModel tempModel = model.makeTemporaryCopy();

        int meta = blockAccess.getBlockMetadata(i,j,k);
        int facing = meta >> 2 & 3;

        if (facing == 1) {
            tempModel.tiltToFacingAlongY(4);
        }
        else if (facing == 2 ) {
            tempModel.tiltToFacingAlongY(2);
        }

        return tempModel;
    }

    protected BlockModel assembleTemporaryModelForRender(IBlockAccess blockAccess, int i, int j, int k) {
        BlockModel tempModel = model.makeTemporaryCopy();

        int meta = blockAccess.getBlockMetadata(i,j,k);
        int facing = meta >> 2 & 3;

        if (facing == 1) {
            tempModel.tiltToFacingAlongY(1);
        }
        else if (facing == 2 ) {
            tempModel.tiltToFacingAlongY(2);
        }

        return tempModel;
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
        AxisAlignedBB fenceBox = AxisAlignedBB.getAABBPool().getAABB(0D, 0D, 0D, 1D, 1D, 1D);

        return fenceBox;
    }

    @Override
    public boolean hasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int side)
    {
        int meta = blockAccess.getBlockMetadata(i,j,k);
        int rot = meta & 12;
        int type = meta & 3;


        return  (rot == 0 && (side == 1 || side == 0)) ? false :
                (rot == 4 && (side == 5 || side == 4)) ? false :
                        (rot == 8 && (side == 2 || side == 3)) ? false :
                                true;
    }

    @Override
    public boolean canToolsStickInBlock(IBlockAccess blockAccess, int i, int j, int k) {
        return super.canToolsStickInBlock(blockAccess, i, j, k);
    }

    @Override
    public boolean renderBlock( RenderBlocks renderer, int i, int j, int k )
    {
        int meta = renderer.blockAccess.getBlockMetadata(i, j, k);
        int type = meta & 3;
        int rot = meta & 12;

        renderer.setRenderBounds( 0D, 0D, 0D, 1D, 1D, 1D );
        renderer.renderBlockLog( this, i, j, k );

        // INSIDE
        if (rot == 0)
        {
            renderer.renderFaceXPos(this, i - 14/16D, j, k, tree_inner[type]);
            renderer.renderFaceXNeg(this, i + 14/16D, j, k, tree_inner[type]);
            renderer.renderFaceZPos(this, i, j, k - 14/16D, tree_inner[type]);
            renderer.renderFaceZNeg(this, i, j, k + 14/16D, tree_inner[type]);
        }
        else if (rot == 4)
        {
            renderer.setUVRotateTop(1);
            renderer.setUVRotateEast(1);
            renderer.setUVRotateWest(1);
            renderer.setUVRotateBottom(1);

            renderer.renderFaceZPos(this, i, j, k - 14/16D, tree_inner[type]);
            renderer.renderFaceZNeg(this, i, j, k + 14/16D, tree_inner[type]);
            renderer.renderFaceYPos(this, i, j - 14/16D, k, tree_inner[type]);
            renderer.renderFaceYNeg(this, i, j + 14/16D, k, tree_inner[type]);
        }
        else if (rot == 8)
        {
            renderer.setUVRotateSouth(1);
            renderer.setUVRotateNorth(1);

            renderer.renderFaceXPos(this, i - 14/16D, j, k, tree_inner[type]);
            renderer.renderFaceXNeg(this, i + 14/16D, j, k, tree_inner[type]);
            renderer.renderFaceYPos(this, i, j - 14/16D, k, tree_inner[type]);
            renderer.renderFaceYNeg(this, i, j + 14/16D, k, tree_inner[type]);
        }

        renderer.setUVRotateTop(0);
        renderer.setUVRotateEast(0);
        renderer.setUVRotateWest(0);
        renderer.setUVRotateBottom(0);
        renderer.setUVRotateSouth(0);
        renderer.setUVRotateNorth(0);


        return true;
    }

    @Override
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {

        super.renderBlockAsItem(renderer, iItemDamage, fBrightness);

        int i = 0;
        int j;
        int k;

        int meta = iItemDamage;
        int type = meta & 3;
        int rot = meta & 12;
        // INSIDE

        if (rot == 0)
        {
            renderer.setRenderBounds(
                    1.001/16F, 0.001F, 1.001/16F,
                    1.999/16F, 0.999F, 14.999/16F );
            RenderUtils.renderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );

            renderer.setRenderBounds(
                    14/16F, 0.001F, 1.001/16F,
                    14.999/16F, 0.999F, 14.999/16F );
            RenderUtils.renderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );

            renderer.setRenderBounds(
                    1.001/16F, 0.001F, 1.001/16F,
                    14.999/16F, 0.999F, 1.999/16F );
            RenderUtils.renderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );

            renderer.setRenderBounds(
                    1.001/16F, 0.001F, 14/16F,
                    14.999/16F, 0.999F, 14.999/16F );
            RenderUtils.renderInvBlockWithTexture( renderer, this, -0.5F, -0.5F, -0.5F, tree_inner[type] );
        }


        renderer.setUVRotateTop(0);
        renderer.setUVRotateEast(0);
        renderer.setUVRotateWest(0);
        renderer.setUVRotateBottom(0);
        renderer.setUVRotateSouth(0);
        renderer.setUVRotateNorth(0);



    }


    @Environment(EnvType.CLIENT)
    private Icon[] tree_top;

    @Environment(EnvType.CLIENT)
    private Icon[] tree_inner;
    @Environment(EnvType.CLIENT)
    private Icon[] tree_side;

    @Override
    public void registerIcons(IconRegister register)
    {
        this.tree_top = new Icon[topTextures.length];

        for (int i = 0; i < topTextures.length; ++i)
        {
            this.tree_top[i] = register.registerIcon(topTextures[i]);
        }

        this.tree_inner = new Icon[innerTextures.length];

        for (int i = 0; i < innerTextures.length; ++i)
        {
            this.tree_inner[i] = register.registerIcon(innerTextures[i]);
        }

        this.tree_side = new Icon[sideTextures.length];

        for (int i = 0; i < sideTextures.length; ++i)
        {
            this.tree_side[i] = register.registerIcon(sideTextures[i]);
        }
    }

    public Icon getIcon(int side, int meta)
    {
        int rot = meta & 12;
        int type = meta & 3;

        return  (rot == 0 && (side == 1 || side == 0) ? this.tree_top[type] :
                (rot == 4 && (side == 5 || side == 4) ? this.tree_top[type] :
                        (rot == 8 && (side == 2 || side == 3) ? this.tree_top[type] :
                                this.tree_side[type])));
    }

//    @Override
//    @Environment(EnvType.CLIENT)
//    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
//        return currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds(iNeighborI, iNeighborJ, iNeighborK, iSide);
//    }
//
//    @Override
//    @Environment(EnvType.CLIENT)
//    public boolean renderBlock(RenderBlocks renderBlocks, int i, int j, int k) {
//        renderBlocks.clearUVRotation();
//
//        BlockModel tempModel = assembleTemporaryModel(renderBlocks.blockAccess, i, j, k);
//
//        return tempModel.renderAsBlock(renderBlocks, this, i, j, k);
//    }
//
//    @Override
//    @Environment(EnvType.CLIENT)
//    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
//        model.renderAsItemBlock(renderBlocks, this, iItemDamage);
//    }

    @Override
    @Environment(EnvType.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, MovingObjectPosition rayTraceHit) {
        AxisAlignedBB tempBox = model.boxBoundsCenter.makeTemporaryCopy();

        return tempBox.offset(rayTraceHit.blockX, rayTraceHit.blockY, rayTraceHit.blockZ);
    }

//    @Override
//    @Environment(EnvType.CLIENT)
//    public Icon getIcon(int side, int metadata) {
//        int facing = metadata >> 2 & 3;
//
//        if ((metadata & 12) == 12) {
//            if (side > 1) {
//                return trunkIconArray[metadata & 3];
//            }
//            else {
//                return trunkTopIconArray[metadata & 3];
//            }
//        }
//        else {
//            if (facing == 0 && (side == 0 || side == 1)) {
//                return topIconArray[metadata & 3];
//            }
//            else if (facing == 1 && (side == 4 || side == 5)) {
//                return topIconArray[metadata & 3];
//            }
//            else if (facing == 2 && (side == 2 || side == 3)) {
//                return topIconArray[metadata & 3];
//            }
//        }
//
//        return super.getIcon(side, metadata);
//    }
//

//
//    @Override
//    @Environment(EnvType.CLIENT)
//    public void registerIcons(IconRegister iconRegister) {
//        topIconArray = new Icon[trunkTextureTypes.length];
//        trunkIconArray = new Icon[trunkTextureTypes.length];
//        trunkTopIconArray = new Icon[trunkTextureTypes.length];
//
//        for (int i = 0; i < trunkIconArray.length; i++) {
//            topIconArray[i] = iconRegister.registerIcon(topTextureTypes[i]);
//            trunkIconArray[i] = iconRegister.registerIcon(trunkTextureTypes[i]);
//            trunkTopIconArray[i] = iconRegister.registerIcon(trunkTopTextureTypes[i]);
//        }
//
//        super.registerIcons(iconRegister);
//    }
}
