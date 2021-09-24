package net.minecraft.src;

public class SocksCropsItemBerrySapling extends FCItemSeeds{
	protected final int m_iCropBlockID;

    public SocksCropsItemBerrySapling(int var1, int var2)
    {
        super(var1, 4001);
        this.m_iCropBlockID = var2;
        this.SetBuoyant();
        this.SetBellowsBlowDistance(2);
        this.SetIncineratedInCrucible();
        this.SetFilterableProperties(8);
        this.setCreativeTab(CreativeTabs.tabMaterials);
        this.setUnlocalizedName("SocksCropsSaplingBerryBush");
    }

}
