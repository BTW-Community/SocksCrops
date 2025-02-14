package btw.community.sockthing.sockscrops.block.blocks;

import btw.community.sockthing.sockscrops.block.SCBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

import java.util.Random;

public class PlanterFarmlandDungBlock extends PlanterFarmlandBaseBlock {

    public PlanterFarmlandDungBlock(int blockID, String name) {
        super(blockID, name);
    }

    @Override
    public int idDropped(int par1, Random par2Random, int par3) {
        return SCBlocks.planterFarmland.blockID;
    }

    protected boolean getIsFertilized(int metadata) {
        return false;
    }

    protected boolean getIsDunged(int metadata) {
        return true;
    }


    //------------- Client ------------//

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        overlayDry = register.registerIcon("farmland_dung_overlay_dry");
        overlayWet = register.registerIcon("farmland_dung_overlay_wet");
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected Icon getOverlayTexture(int metadata) {
        if (isHydrated(metadata)) {
            return overlayWet;
        } else return overlayDry;
    }
}
