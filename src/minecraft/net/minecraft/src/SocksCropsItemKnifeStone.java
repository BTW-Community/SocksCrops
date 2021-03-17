package net.minecraft.src;

public class SocksCropsItemKnifeStone extends FCItemChisel
{
    protected SocksCropsItemKnifeStone(int var1)
    {
        super(var1, EnumToolMaterial.STONE, 8);
        this.SetFilterableProperties(2);
        this.efficiencyOnProperMaterial /= 2.0F;
        this.setUnlocalizedName("SocksCropsItemKnifeStone");
        this.setCreativeTab(CreativeTabs.tabAllSearch);
    }

    protected boolean GetCanBePlacedAsBlock()
	{
		return true;
	}
    
    
}
