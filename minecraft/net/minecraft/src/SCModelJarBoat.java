// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelJarBoat extends ModelBase {
	private final ModelRenderer bone2;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cube_r3;
	private final ModelRenderer bone3;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cube_r5;
	private final ModelRenderer bone4;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cube_r7;

	public SCModelJarBoat() {
		textureWidth = 64;
		textureHeight = 64;

		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 1.0F);
		this.bone2.setTextureOffset(13, 15).addBox(-0.5F, -1.0F, -3.0F, 1, 1, 5, 0.0F);
		this.bone2.setTextureOffset(11, 0).addBox(-1.5F, -2.0F, -4.0F, 3, 1, 5, 0.0F);
		this.bone2.setTextureOffset(0, 9).addBox(-2.25F, -3.25F, -4.75F, 2, 2, 7, -0.75F);
		this.bone2.setTextureOffset(0, 0).addBox(0.25F, -3.25F, -4.75F, 2, 2, 7, -0.75F);
		this.bone2.setTextureOffset(24, 24).addBox(-1.75F, -3.25F, -4.75F, 3, 2, 2, -0.75F);
		this.bone2.setTextureOffset(28, 15).addBox(-0.25F, -3.25F, -4.75F, 2, 2, 2, -0.75F);
		this.bone2.setTextureOffset(23, 19).addBox(-1.75F, -4.25F, 1.75F, 3, 2, 2, -0.75F);
		this.bone2.setTextureOffset(27, 9).addBox(-0.25F, -4.25F, 1.75F, 2, 2, 2, -0.75F);
		this.bone2.setTextureOffset(22, 0).addBox(0.25F, -4.25F, 0.75F, 2, 2, 3, -0.75F);
		this.bone2.setTextureOffset(19, 6).addBox(-2.25F, -4.25F, 0.75F, 2, 2, 3, -0.75F);
		this.bone2.setTextureOffset(29, 0).addBox(-0.5F, -2.5F, -6.0F, 1, 1, 2, 0.0F);
		this.bone2.setTextureOffset(26, 5).addBox(-1.5F, -3.0F, 1.0F, 3, 2, 2, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(1.0F, 0.183F, -3.683F);
		bone2.addChild(cube_r1);
		setRotation(cube_r1, -0.5236F, 0.0F, 0.0F);
		this.cube_r1.setTextureOffset(11, 9).addBox(-2.0F, -2.0F, -3.0F, 2, 2, 4, -0.5F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(-1.25F, -3.0295F, 1.683F);
		bone2.addChild(cube_r2);
		setRotation(cube_r2, 0.7418F, 0.0F, 0.0F);
		this.cube_r2.setTextureOffset(20, 12).addBox(-1.0F, -1.2205F, -2.067F, 2, 2, 3, -0.75F);
		this.cube_r2.setTextureOffset(16, 21).addBox(1.5F, -1.2205F, -2.067F, 2, 2, 3, -0.75F);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(-0.5F, 0.0F, 2.0F);
		bone2.addChild(cube_r3);
		setRotation(cube_r3, 1.0472F, 0.0F, 0.0F);
		this.cube_r3.setTextureOffset(24, 28).addBox(0.0F, -1.0F, 0.0F, 1, 1, 2, 0.0F);

		bone3 = new ModelRenderer(this);
		bone3.setRotationPoint(1.5F, -4.75F, -4.25F);
		bone2.addChild(bone3);
		this.bone3.setTextureOffset(0, 27).addBox(-3.0F, -2.0F, 0.0F, 3, 3, 1, 0.0F);
		this.bone3.setTextureOffset(8, 23).addBox(-2.0F, -2.0F, 1.0F, 1, 5, 1, -0.25F);
		this.bone3.setTextureOffset(8, 23).addBox(-2.0F, -4.0F, 3.5F, 1, 7, 1, -0.25F);
		this.bone3.setTextureOffset(8, 23).addBox(-4.0F, 0.75F, 3.0F, 5, 1, 1, -0.25F);
		this.bone3.setTextureOffset(8, 23).addBox(-4.0F, 0.75F, 0.5F, 5, 1, 1, -0.25F);

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(0.0F, 0.5F, 0.0F);
		bone3.addChild(cube_r4);
		setRotation(cube_r4, 0.0F, -0.5236F, 0.0F);
		this.cube_r4.setTextureOffset(11, 9).addBox(0.0F, -2.5F, 0.0F, 1, 3, 1, 0.0F);

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(-3.0F, 0.5F, 0.0F);
		bone3.addChild(cube_r5);
		setRotation(cube_r5, 0.0F, 0.5672F, 0.0F);
		this.cube_r5.setTextureOffset(11, 0).addBox(-1.0F, -2.5F, 0.0F, 1, 3, 1, 0.0F);

		bone4 = new ModelRenderer(this);
		bone4.setRotationPoint(1.5F, -4.75F, -1.75F);
		bone2.addChild(bone4);
		this.bone4.setTextureOffset(16, 26).addBox(-3.0F, -3.0F, 0.0F, 3, 4, 1, 0.0F);

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, 0.0F, 0.0F);
		bone4.addChild(cube_r6);
		setRotation(cube_r6, 0.0F, -0.5236F, 0.0F);
		this.cube_r6.setTextureOffset(0, 0).addBox(0.0F, -3.0F, 0.0F, 1, 4, 1, 0.0F);

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-3.0F, 0.0F, 0.0F);
		bone4.addChild(cube_r7);
		setRotation(cube_r7, 0.0F, 0.5672F, 0.0F);
		this.cube_r7.setTextureOffset(0, 9).addBox(-1.0F, -3.0F, 0.0F, 1, 4, 1, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		bone2.render(f5);
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {

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