// Made with Blockbench 4.2.5
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and generate all required imports

package net.minecraft.src;

public class SCModelPan extends ModelBase {
	private final ModelRenderer pan;
	private final ModelRenderer sides;

	public SCModelPan() {
		textureWidth = 32;
		textureHeight = 32;

		pan = new ModelRenderer(this);
		pan.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.pan.setTextureOffset(16, 19).addBox(-1.0F, -3.0F, -11.0F, (int)2.0F, (int)2.0F, (int)6.0F, 0.0F);
		this.pan.setTextureOffset(0, 0).addBox(-4.0F, -1.0F, -4.0F, (int)8.0F, (int)1.0F, (int)8.0F, 0.0F);

		sides = new ModelRenderer(this);
		sides.setRotationPoint(0.0F, 0.0F, 0.0F);
		pan.addChild(sides);
		this.sides.setTextureOffset(0, 28).addBox(-4.0F, -3.0F, 4.0F, (int)9.0F, (int)3.0F, (int)1.0F, 0.0F);
		this.sides.setTextureOffset(0, 9).addBox(-5.0F, -3.0F, -4.0F, (int)1.0F, (int)3.0F, (int)9.0F, 0.0F);
		this.sides.setTextureOffset(0, 9).addBox(4.0F, -3.0F, -5.0F, (int)1.0F, (int)3.0F, (int)9.0F, 0.0F);
		this.sides.setTextureOffset(0, 28).addBox(-5.0F, -3.0F, -5.0F, (int)9.0F, (int)3.0F, (int)1.0F, 0.0F);
	}
	/**
    * Sets the models various rotation angles then renders the model.
    */
	@Override
	public void render(Entity entity, float f, float f1, float f2, float rot, float f4, float f5)
	{
		this.setRotationAngles(f, f1, f2, rot, f4, f5, entity);
		this.pan.render(f5);
	}
	
    /**
     * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
     * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
     * "far" arms and legs can swing at most.
     */
    public void setRotationAngles(float par1, float par2, float par3, float rot, float par5, float par6, Entity par7Entity)
    {
        this.pan.rotateAngleY = rot / (180F / (float)Math.PI);
        this.pan.rotateAngleX = par5 / (180F / (float)Math.PI);
    }


}