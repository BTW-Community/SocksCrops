package btw.community.sockthing.sockscrops.block.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.Icon;

public class PlanterFarmlandBlock extends PlanterFarmlandBaseBlock {

    public PlanterFarmlandBlock(int blockID, String name) {
        super(blockID, name);
    }

    //------------- Client ------------//

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getOverlayTexture(int metadata) {
        return null; //don't render bonemeal or dung
    }
}
