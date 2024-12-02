package btw.community.sockthing.sockscrops.block.models;

import btw.block.model.BlockModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.BlockLeavesBase;
import net.minecraft.src.RenderBlocks;

public class LargeFlowerpotModel extends BlockModel {
    public BlockModel modelItem;
    public BlockModel dirt;

    private final double CENTER = 0.5D;
    private final double HEIGHT = 6/16D;
    public AxisAlignedBB bounds;

    @Override
    protected void initModel() {

        addBox(CENTER - 7/16D, 0D, CENTER - 3/16D,
                CENTER + 7/16D, HEIGHT, CENTER - 2/16D);

        addBox(CENTER - 7/16D, 0D, CENTER + 2/16D,
                CENTER + 7/16D, HEIGHT, CENTER + 3/16D);

        addBox(CENTER - 7/16D, 0D, CENTER - 2/16D,
                CENTER - 6/16D, HEIGHT, CENTER + 2/16D);

        addBox(CENTER + 6/16D, 0D, CENTER - 2/16D,
                CENTER + 7/16D, HEIGHT, CENTER + 2/16D);

        addBox(CENTER - 6/16D, 0D, CENTER - 2/16D,
                CENTER + 6/16D, 1/16D, CENTER + 2/16D);

        dirt = new BlockModel();

        dirt.addBox(CENTER - 6/16D, 1/16D, CENTER - 2/16D,
                CENTER + 6/16D, 5/16D, CENTER + 2/16D);

        bounds = new AxisAlignedBB(
                CENTER - 7/16D, 0/16D, CENTER - 3/16D,
                CENTER + 7/16D, 6/16D, CENTER + 3/16D
        );
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void renderAsItemBlock(RenderBlocks renderBlocks, Block block, int iItemDamage)
    {
        modelItem.renderAsItemBlock(renderBlocks, block, iItemDamage);
    }
}
