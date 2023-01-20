package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCTileEntityChurnRenderer extends TileEntitySpecialRenderer {

	@Override
    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityChurn churn = (SCTileEntityChurn)tileEntity;
    	renderChurnStick(churn, xCoord, yCoord, zCoord );
	}

	private void renderChurnStick(SCTileEntityChurn churn, double xCoord, double yCoord, double zCoord) {
		
		int rot = churn.getCounter();
		
		SCModelChurnStick stick  = new SCModelChurnStick();
		this.bindTextureByName("/textures/blocks/wood_spruce.png");
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
    	float xPos = (float) xCoord;
    	float yPos = (float) yCoord;
    	float zPos = (float) zCoord;
        
        GL11.glTranslatef(xPos + 0.5F, yPos + 0.25F , zPos + 0.5F);
        
        GL11.glRotatef(rot * 10, 0, 1, 0);
        
        float var10 = 0.0625F;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
        stick.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
        
        GL11.glPopMatrix();
	}

}
