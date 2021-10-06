package net.minecraft.src;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.Colorizer;
import com.prupe.mcpatcher.ctm.CTMUtils;
import com.prupe.mcpatcher.ctm.GlassPaneRenderer;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import com.prupe.mcpatcher.renderpass.RenderPass;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCRenderBlocks extends RenderBlocks
{
	public SCRenderBlocks(IBlockAccess blockAccess)
    {
        super.blockAccess = blockAccess;
    }
	
	/**
     * Renders a block requiring double sized crossed squares for Pumpkin Stem
     */
    public boolean renderLargeCrossedSquares(Block par1Block, int x, int y, int z)
    {
    	
    	int blockID = par1Block.blockID;
    	
        Tessellator var5 = Tessellator.instance;
        var5.setBrightness(par1Block.getMixedBrightnessForBlock(this.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(this.blockAccess, x, y, z);
        float var8 = (float)(var7 >> 16 & 255) / 255.0F;
        float var9 = (float)(var7 >> 8 & 255) / 255.0F;
        float var10 = (float)(var7 & 255) / 255.0F;

        double newX = (double)x;
        double newY = (double)y;
        double newZ = (double)z;

        super.drawCrossedSquares(par1Block, this.blockAccess.getBlockMetadata(x, y, z), x, y, z, 2.0F);
        return true;
    }
}
