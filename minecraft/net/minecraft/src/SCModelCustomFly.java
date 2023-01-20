// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomFly extends ModelBase {
	private final ModelRenderer bone;
	private final ModelRenderer left_wing;
	private final ModelRenderer right_wing;

	public SCModelCustomFly() {
		textureWidth = 16;
		textureHeight = 16;

		bone = new ModelRenderer(this);
		bone.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bone.setTextureOffset(0, 0).addBox(4.5F, -2.0F, -2.0F, 3, 2, 4, 0.0F);

		left_wing = new ModelRenderer(this);
		left_wing.setRotationPoint(6.5F, -2.0F, 1.0F);
		bone.addChild(left_wing);
		this.left_wing.setTextureOffset(-1, 6).addBox(0.0F, 0.0F, -2.0F, 3, 0, 4, 0.0F);

		right_wing = new ModelRenderer(this);
		right_wing.setRotationPoint(5.5F, -2.0F, 1.0F);
		bone.addChild(right_wing);
		this.right_wing.setTextureOffset(5, 6).addBox(-3.0F, 0.0F, -2.0F, 3, 0, 4, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bone.render(f5);
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.right_wing.rotateAngleZ = MathHelper.cos(f * 1.3F) * (float)Math.PI * 0.25F;
        this.left_wing.rotateAngleZ = -this.right_wing.rotateAngleZ;
        
        this.bone.rotateAngleY = f * 0.2F;
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