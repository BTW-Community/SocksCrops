package net.minecraft.src;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class SCTileEntitySeedJarRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntitySeedJar jar = (SCTileEntitySeedJar)tileEntity;
    	ItemStack stack = jar.getStorageStack();
    	
    	if (stack != null && stack.itemID == Item.cookie.itemID) 
    	{
    		renderCookie( jar, xCoord, yCoord, zCoord );
    	}
    }

	private void renderCookie(SCTileEntitySeedJar jar, double xCoord, double yCoord, double zCoord) {
		int stackSize = jar.getStorageStack().stackSize /= 8;
//		float fillLevel = MathHelper.ceiling_float_int(stackSize)/16F;
		float shiftUp = 0;
		
		if (jar.hasAttachableBlockAbove()) shiftUp = 5/16F;
		
		
		
		ModelBase model;
		

		
		this.bindTextureByName("/scmodtex/jar/cookie.png");
    	
		float xPos = (float) xCoord;
    	float yPos = (float) yCoord;
    	float zPos = (float) zCoord;
    	
		float scale = 0.0625F * 0.9F;
		
		GL11.glPushMatrix();
		
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glTranslatef(xPos + 0.5F,  yPos + 1/32F + shiftUp, zPos + 8/16F);
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
		switch (stackSize) {
		case 1:
			model = new SCModelJarCookies();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 2:
			model = new SCModelJarCookies2();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 3:
			model = new SCModelJarCookies3();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 4:
			model = new SCModelJarCookies4();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 5:
			model = new SCModelJarCookies5();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 6:
			model = new SCModelJarCookies6();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 7:
			model = new SCModelJarCookies7();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		case 8:
			model = new SCModelJarCookies8();
			model.render((Entity)null, 0, 0, 0F, 0F, 0.0F, scale);
			break;
		}        
        GL11.glPopMatrix();
		
	}

}
