package net.minecraft.src;

public class SocksCropsItemBerries extends FCItemFood
{
    public SocksCropsItemBerries(int var1)
    {
    	super(var1, 2, 0.0F, false, "SocksCropsItemBerries");
        this.SetBellowsBlowDistance(1);
        this.SetFilterableProperties(2);
        //this.setPotionEffect(Potion.confusion.id, 10, 0, 1.0F);
        //this.setAlwaysEdible();
        this.setCreativeTab(CreativeTabs.tabFood);
    }

}
