package net.minecraft.src;

import java.util.Random;

public class SocksCropsBlockRiceRoots extends SocksCropsBlockRiceCrop
{
    public static final double m_dWeedsBoundsWidth = 0.75D;
    public static final double m_dWeedsBoundsHalfWidth = 0.375D;
    private Icon m_IconRoots;

    protected SocksCropsBlockRiceRoots(int var1)
    {
        super(var1);
    }

    /**
     * When this method is called, your block should register all the icons it needs with the given IconRegister. This
     * is the only chance you get to register icons.
     */
    public void registerIcons(IconRegister var1)
    {
    	this.m_IconRoots = var1.registerIcon("SocksCropsBlockRice_roots");
    }
    
    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    public Icon getIcon(int var1, int var2)
    {
        return this.m_IconRoots;
    }


}
