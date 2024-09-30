package btw.community.sockthing.sockscrops.block.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class PlanterFarmlandFertilizedBlock extends PlanterFarmlandBaseBlock {

    public PlanterFarmlandFertilizedBlock(int iBlockID, String name) {
        super(iBlockID, name);
    }

    //------------- Client ------------//

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        overlayDry = register.registerIcon("farmland_fertilized_overlay_dry");
        overlayWet = register.registerIcon("farmland_fertilized_overlay_wet");
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getOverlayTexture(int metadata) {
        if (isHydrated(metadata)) {
            return overlayWet;
        } else return overlayDry;
    }


}
