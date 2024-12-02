package btw.community.sockthing.sockscrops.block.blocks;

import btw.block.util.Flammability;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.StepSound;

public class MossBlock extends Block {
    public MossBlock(int blockID, String name) {
        super(blockID, Material.plants);

        setHardness(0.1F);

        setUnlocalizedName(name);
        setFireProperties( Flammability.LEAVES );
        setStepSound(soundGrassFootstep);
        setCreativeTab(CreativeTabs.tabDecorations);
    }
}
