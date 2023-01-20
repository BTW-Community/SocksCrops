// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomMuffin extends ModelBase {
	private final ModelRenderer bottom;
	private final ModelRenderer chocolate;
	private final ModelRenderer blueberry;
	private final ModelRenderer sweetberry;

	public SCModelCustomMuffin() {
		textureWidth = 32;
		textureHeight = 32;

		bottom = new ModelRenderer(this);
		bottom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bottom.setTextureOffset(16, 25).addBox(-2.0F, -3.0F, -2.0F, 4, 3, 4, 0.0F);

		chocolate = new ModelRenderer(this);
		chocolate.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chocolate.setTextureOffset(0, 0).addBox(-2.5F, -6.0F, -2.5F, 5, 3, 5, 0.0F);

		blueberry = new ModelRenderer(this);
		blueberry.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.blueberry.setTextureOffset(0, 8).addBox(-2.5F, -6.0F, -2.5F, 5, 3, 5, 0.0F);

		sweetberry = new ModelRenderer(this);
		sweetberry.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.sweetberry.setTextureOffset(0, 16).addBox(-2.5F, -6.0F, -2.5F, 5, 3, 5, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
		bottom.render(f5);
		
		if ( f == SCDefs.itemMuffinChocolate.itemID )
		{
			chocolate.render(f5);
		}
		
		if ( f == SCDefs.itemMuffinSweetberry.itemID)
		{
			sweetberry.render(f5);
		}
		
		if ( f == SCDefs.itemMuffinBlueberry.itemID)
		{
			blueberry.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.bottom.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chocolate.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.sweetberry.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.blueberry.rotateAngleY = f3 / (180F / (float)Math.PI);
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