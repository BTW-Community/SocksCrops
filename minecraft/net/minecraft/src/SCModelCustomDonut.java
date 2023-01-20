// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomDonut extends ModelBase {
	private final ModelRenderer donut;
	private final ModelRenderer chocolate;
	private final ModelRenderer sugar;

	public SCModelCustomDonut() {
		textureWidth = 32;
		textureHeight = 32;

		donut = new ModelRenderer(this);
		donut.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.donut.setTextureOffset(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6, 3, 6, 0.0F);
		this.donut.setTextureOffset(18, 1).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);

		chocolate = new ModelRenderer(this);
		chocolate.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chocolate.setTextureOffset(0, 18).addBox(-3.0F, -3.0F, -3.0F, 6, 3, 6, 0.0F);
		this.chocolate.setTextureOffset(18, 19).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);

		sugar = new ModelRenderer(this);
		sugar.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.sugar.setTextureOffset(0, 9).addBox(-3.0F, -3.0F, -3.0F, 6, 3, 6, 0.0F);
		this.sugar.setTextureOffset(18, 10).addBox(-1.0F, -3.0F, -1.0F, 2, 3, 2, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		if ( f == FCBetterThanWolves.fcItemDonut.itemID)
		{
			donut.render(f5);
		}
		
		if (f == SCDefs.donutSugar.itemID)
		{
			sugar.render(f5);
		}
		
		if (f == SCDefs.donutChocolate.itemID)
		{
			chocolate.render(f5);
		}
		

	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.donut.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chocolate.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.sugar.rotateAngleY = f3 / (180F / (float)Math.PI);
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