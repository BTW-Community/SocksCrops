package btw.community.sockthing.sockscrops.block.models;

import btw.block.model.BlockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.RenderBlocks;

public class HollowLogModel extends BlockModel {
    protected static final double WALL_THICKNESS = ( 2D / 16D );
    protected static final double ZFIX = ( 1 / 1024D);
    public BlockModel modelItem;
    public BlockModel side;

    public AxisAlignedBB boxBoundsCenter;
    public AxisAlignedBB[] modelBounds;

    @Override
    protected void initModel() {
        // north
        addBox(0D, 0D, 0D,
                WALL_THICKNESS, 1D, 1D);

        addBox(1D - WALL_THICKNESS, 0D, 0D,
                1D, 1D, 1D);

        addBox(WALL_THICKNESS, 0D, 0D,
                1D - WALL_THICKNESS, 1D, WALL_THICKNESS);

        addBox(WALL_THICKNESS, 0D, 1D - WALL_THICKNESS,
                1D - WALL_THICKNESS, 1D, 1D);

        modelBounds = new AxisAlignedBB[4];

        side = new BlockModel();
        side.addBox(0D, 0D, ZFIX,
                WALL_THICKNESS, 1D, 1D - ZFIX);

        // north
        modelBounds[0] = new AxisAlignedBB(0D, 0D, 0D,
                WALL_THICKNESS, 1D, 1D);

//        // south
//        addBox(0D, 0D, 1D - WALL_THICKNESS,
//                1D, 1D, 1D);
//
//        // west
//        addBox(0D , 0D, WALL_THICKNESS,
//                WALL_THICKNESS , 1D, 1D - WALL_THICKNESS);
//
//        // west
//        addBox(1D - WALL_THICKNESS , 0D, WALL_THICKNESS,
//                1D , 1D, 1D - WALL_THICKNESS);


        modelItem = new BlockModel();
        // north
        modelItem.addBox(0D, 0D, 0D,
                1D, 1D, WALL_THICKNESS);

        // south
        modelItem.addBox(0D, 0D, 1D - WALL_THICKNESS,
                1D, 1D, 1D);

        // west
        modelItem.addBox(0D , 0D, WALL_THICKNESS,
                WALL_THICKNESS , 1D, 1D - WALL_THICKNESS);

        // west
        modelItem.addBox(1D - WALL_THICKNESS , 0D, WALL_THICKNESS,
                1D , 1D, 1D - WALL_THICKNESS);
        // collision volumes
        boxBoundsCenter = new AxisAlignedBB(
                0D, 0D, 0D,
                1D, 1D, 1D);


//
//        // south
//        modelBounds[1] = new AxisAlignedBB(0D, 0D, 1D - WALL_THICKNESS,
//                1D, 1D, 1D);
//
//        // west
//        modelBounds[2] = new AxisAlignedBB(0D , 0D, WALL_THICKNESS,
//                WALL_THICKNESS , 1D, 1D - WALL_THICKNESS);
//
//        // west
//        modelBounds[3] = new AxisAlignedBB(1D - WALL_THICKNESS , 0D, WALL_THICKNESS,
//                1D , 1D, 1D - WALL_THICKNESS);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderAsItemBlock(RenderBlocks renderBlocks, Block block, int iItemDamage)
    {
        modelItem.renderAsItemBlock(renderBlocks, block, iItemDamage);
    }
}
