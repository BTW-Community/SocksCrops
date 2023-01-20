// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomCakeSlice extends ModelBase {
	private final ModelRenderer cake;
	private final ModelRenderer chocolate;
	private final ModelRenderer carrot;

	public SCModelCustomCakeSlice() {
		textureWidth = 48;
		textureHeight = 32;

		cake = new ModelRenderer(this);
		cake.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cake.setTextureOffset(0, 0).addBox(-7.0F, -2.0F, -4.0F, 14, 2, 8, 0.0F);

		chocolate = new ModelRenderer(this);
		chocolate.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chocolate.setTextureOffset(0, 10).addBox(-7.0F, -2.0F, -4.0F, 14, 2, 8, 0.0F);

		carrot = new ModelRenderer(this);
		carrot.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.carrot.setTextureOffset(0, 20).addBox(-7.0F, -2.0F, -4.0F, 14, 2, 8, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);	
		
		if (f == SCDefs.cakeSlice.itemID)
		{
			cake.render(f5);
		}
		else if (f == SCDefs.chocolateCakeSlice.itemID)
		{
			chocolate.render(f5);
		}
		else if (f == SCDefs.carrotCakeSlice.itemID)
		{
			carrot.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.cake.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chocolate.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.carrot.rotateAngleY = f3 / (180F / (float)Math.PI);
    }
	
	/**
	*	Sets the rotation of a ModelRenderer. Only called if the ModelRenderer has a rotation
	*/
    public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
}