package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SCTileEntityMixerRenderer extends TileEntitySpecialRenderer {

	private SCModelMixerExtension mixerExtension = new SCModelMixerExtension();

	
    public void renderTileEntityAt(TileEntity tileEnt, double x, double y, double z, float par8)
    {
        this.renderTileEntityMixerAt((SCTileEntityMixer)tileEnt, x, y, z, par8);
    }

	private void renderTileEntityMixerAt(SCTileEntityMixer mixer, double x, double y, double z, float par8) {

        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y + 0.5F, (float)z + 0.5F);

        GL11.glRotatef(180F, 1, 0, 0);
        
        this.bindTextureByName("/scmodtex/mixer/arm.png");
        GL11.glEnable(GL11.GL_CULL_FACE);

        float var9 = (float)mixer.moveCount + par8; 
        
        float moveCount = (float) mixer.getMoveCount();

        float bounceCount = 0F;
        
        if ( mixer.getBlockMetadata() == 1 ) // is powered
        {
        	//GL11.glTranslatef(0.0F, 14/16F + MathHelper.sin(var9 * 0.05F) * 0.08F, 0.0F);
        	this.mixerExtension.whisk.rotateAngleY = mixer.worldObj.getTotalWorldTime() * 0.25F ;
        		
        	if (moveCount >= 100)
        	{
        		bounceCount = MathHelper.sin( mixer.worldObj.getTotalWorldTime() * 0.1F);
        	}	
        }
        else
        {
        	this.mixerExtension.whisk.rotateAngleY = 0;
        }
        
        GL11.glTranslatef(0.0F, moveCount/120 + bounceCount/16 - 2/16F, 0.0F);
        
        this.mixerExtension.render((Entity)null, 0, 0, 0, 0, 0.0F, 0.0625F);
        
        GL11.glPopMatrix();
        
	}

}
