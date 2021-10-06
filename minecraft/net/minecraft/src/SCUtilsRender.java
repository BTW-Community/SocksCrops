// FCMOD (client only)

package net.minecraft.src;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCUtilsRender extends FCClientUtilsRender
{
    public static void RenderCrossedSquaresWithTexture( RenderBlocks renderBlocks, Block block, int i, int j, int k, Icon texture )
    {
        boolean bHasOverride = renderBlocks.hasOverrideBlockTexture();
        
        if ( !bHasOverride )
        {
        	renderBlocks.setOverrideBlockTexture( texture );
        }
        
        renderBlocks.renderCrossedSquares( block, i, j, k );
        
        if ( !bHasOverride )
        {
        	renderBlocks.clearOverrideBlockTexture();
        }
    }

    
}
