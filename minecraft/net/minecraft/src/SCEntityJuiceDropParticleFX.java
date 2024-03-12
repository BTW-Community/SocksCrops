package net.minecraft.src;

import java.awt.Color;

import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.cc.ColorizeEntity;
import com.prupe.mcpatcher.cc.Colorizer;

public class SCEntityJuiceDropParticleFX extends EntityFX
{
    /** the material type for dropped items/blocks */    
    private int color;

    /** The height of the current bob */
    private int bobTimer;

    public SCEntityJuiceDropParticleFX(World par1World, double par2, double par4, double par6, int color)
    {
        super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
        this.motionX = this.motionY = this.motionZ = 0.0D;
        
    	if (ColorizeBlock.computeWaterColor(true, (int)this.posX, (int)this.posY, (int)this.posZ))
        {
            this.particleRed = Colorizer.setColor[0];
            this.particleGreen = Colorizer.setColor[1];
            this.particleBlue = Colorizer.setColor[2];
        }
    	
    	Color tempColor = new Color(color);
    	float r = tempColor.getRed();
    	float g = tempColor.getGreen();
    	float b = tempColor.getBlue();

    	this.particleRed = r/255;
    	this.particleGreen = g/255;
    	this.particleBlue = b/255;
    	
//    	this.particleRed = 1;
//        this.particleGreen = 0;
//        this.particleBlue = 0;
//        switch (color)
//        {
//		case 1:
//			
//            this.particleRed = 0.95F;
//            this.particleGreen = 0.8F;
//            this.particleBlue = 0.45F;
//			
//			break;
//			
//		case 2:
//			
//            this.particleRed = 0.21F;
//            this.particleGreen = 0.06F;
//            this.particleBlue = 0.73F;
//			
//			break;
//
//		default:
//			
//            this.particleRed = 0.2F;
//            this.particleGreen = 0.3F;
//            this.particleBlue = 1.0F;
//			break;
//		}



        this.setParticleTextureIndex(114);
        this.particleScale = (this.rand.nextFloat() * 0.5F + 1F) * 2.0F;
        this.setSize(0.01F, 0.01F); //was 0.01F
        this.particleGravity = 0.1F;
        this.bobTimer = 40;
        this.color = color;
//        this.particleMaxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
        this.particleMaxAge = (int)(64.0D / (Math.random() * 0.8D + 0.2D));
        this.motionX = this.motionY = this.motionZ = 0.0D;
        
        this.posX = 0.5D;
        this.posY = 0.5D;
        this.posZ = 0.5D;
        
    }

    public int getBrightnessForRender(float par1)
    {
        return super.getBrightnessForRender(par1);
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float par1)
    {
        return super.getBrightness(par1);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;        

        this.motionY -= (double)this.particleGravity;

        if (this.bobTimer-- > 0)
        {
            this.motionX *= 0.02D;
            this.motionY *= 0.02D;
            this.motionZ *= 0.02D;
            this.setParticleTextureIndex(113);
        }
        else
        {
            this.setParticleTextureIndex(112);
        }

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.particleMaxAge-- <= 0)
        {
            this.setDead();
        }

        if (this.onGround)
        {
        	this.setDead();
//            this.worldObj.spawnParticle("splashApple", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);

//            this.motionX *= 0.699999988079071D;
//            this.motionZ *= 0.699999988079071D;
        }

        Material var1 = this.worldObj.getBlockMaterial(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));

        if (var1.isLiquid() || var1.isSolid())
        {
            double var2 = (double)((float)(MathHelper.floor_double(this.posY) + 1) - BlockFluid.getFluidHeightPercent(this.worldObj.getBlockMetadata(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ))));

            if (this.posY < var2)
            {
                this.setDead();
            }
        }
    }
}
