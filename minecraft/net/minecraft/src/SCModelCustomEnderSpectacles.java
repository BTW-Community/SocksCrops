// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomEnderSpectacles extends ModelBase {
	private final ModelRenderer spectacles;
	private final ModelRenderer cube_r1;

	public SCModelCustomEnderSpectacles() {
		textureWidth = 32;
		textureHeight = 16;

		spectacles = new ModelRenderer(this);
		spectacles.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.spectacles.setTextureOffset(0, 11).addBox(-4.0F, -4.0F, -4.5F, 4, 4, 1, -0.25F);
		this.spectacles.setTextureOffset(16, 11).addBox(0.0F, -4.0F, -4.5F, 4, 4, 1, -0.25F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, -2.5F, -4.0F);
		spectacles.addChild(cube_r1);
		setRotation(cube_r1, -0.2618F, 0.0F, 0.0F);
		this.cube_r1.setTextureOffset(0, 0).addBox(-4.0F, -0.5F, 0.0F, 8, 1, 8, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		spectacles.render(f5);
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		this.spectacles.rotateAngleY = f3 / (180F / (float)Math.PI);
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