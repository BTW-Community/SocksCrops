package net.minecraft.src;

public class SocksCropsItemWildCarrot extends FCItemSeedFood
{
    public SocksCropsItemWildCarrot(int var1)
    {
    	super(var1, 2, 0.8F, SocksCropsDefs.wildCarrotBlock.blockID);
    	this.setUnlocalizedName("SocksCropsItemWildCarrot");
        this.SetBellowsBlowDistance(1);
        this.SetFilterableProperties(2);
        //this.setPotionEffect(Potion.confusion.id, 10, 0, 1.0F);
        //this.setAlwaysEdible();
        this.setCreativeTab(CreativeTabs.tabFood);
    }

}