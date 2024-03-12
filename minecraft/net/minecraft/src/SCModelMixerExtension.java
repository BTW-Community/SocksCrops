// Made with Blockbench 4.7.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelMixerExtension extends ModelBase {
	private final ModelRenderer arm;
	public final ModelRenderer whisk;
	private final ModelRenderer bone2;
	private final ModelRenderer bone7;
	private final ModelRenderer bone5;
	private final ModelRenderer bone6;
	private final ModelRenderer bone8;
	private final ModelRenderer mixer;

	public SCModelMixerExtension() {
		textureWidth = 32;
		textureHeight = 32;
		

		
		arm = new ModelRenderer(this);
		arm.setRotationPoint(0.0F, 29.0F, 0.0F);
		this.arm.setTextureOffset(0, 0).addBox(-2.0F, -31.0F, -2.0F, 4, 16, 4, 0.0F);

		mixer = new ModelRenderer(this);
		mixer.setRotationPoint(0.0F, -3.0F, 0.0F);
		mixer.addChild(arm);
		
		whisk = new ModelRenderer(this);
		whisk.setRotationPoint(0.0F, 0.0F, 0.0F);
		arm.addChild(whisk);
		
		bone2 = new ModelRenderer(this);
		bone2.setRotationPoint(0.0F, 0.0F, 0.0F);
		whisk.addChild(bone2);
		this.bone2.setTextureOffset(0, 20).addBox(-2.0F, -15.0F, -2.0F, 4, 1, 4, 0.0F);
		this.bone2.setTextureOffset(0, 25).addBox(-2.0F, -6.0F, -2.0F, 4, 1, 4, 0.0F);

		bone7 = new ModelRenderer(this);
		bone7.setRotationPoint(0.0F, -1.0F, 0.0F);
		whisk.addChild(bone7);
		this.bone7.setTextureOffset(16, 5).addBox(4.0F, -14.0F, -2.0F, 1, 10, 4, 0.0F);
		this.bone7.setTextureOffset(16, 0).addBox(2.0F, -14.0F, -2.0F, 2, 1, 4, 0.0F);
		this.bone7.setTextureOffset(15, 19).addBox(2.0F, -5.0F, -2.0F, 2, 1, 4, 0.0F);

		bone5 = new ModelRenderer(this);
		bone5.setRotationPoint(0.0F, -1.0F, 0.0F);
		whisk.addChild(bone5);
		setRotation(bone5, 0.0F, -1.5708F, 0.0F);
		this.bone5.setTextureOffset(16, 5).addBox(4.0F, -14.0F, -2.0F, 1, 10, 4, 0.0F);
		this.bone5.setTextureOffset(16, 0).addBox(2.0F, -14.0F, -2.0F, 2, 1, 4, 0.0F);
		this.bone5.setTextureOffset(15, 19).addBox(2.0F, -5.0F, -2.0F, 2, 1, 4, 0.0F);

		bone6 = new ModelRenderer(this);
		bone6.setRotationPoint(0.0F, -1.0F, 0.0F);
		whisk.addChild(bone6);
		setRotation(bone6, 0.0F, 3.1416F, 0.0F);
		this.bone6.setTextureOffset(16, 5).addBox(4.0F, -14.0F, -2.0F, 1, 10, 4, 0.0F);
		this.bone6.setTextureOffset(16, 0).addBox(2.0F, -14.0F, -2.0F, 2, 1, 4, 0.0F);
		this.bone6.setTextureOffset(15, 19).addBox(2.0F, -5.0F, -2.0F, 2, 1, 4, 0.0F);

		bone8 = new ModelRenderer(this);
		bone8.setRotationPoint(0.0F, -1.0F, 0.0F);
		whisk.addChild(bone8);
		setRotation(bone8, 0.0F, 1.5708F, 0.0F);
		this.bone8.setTextureOffset(16, 5).addBox(4.0F, -14.0F, -2.0F, 1, 10, 4, 0.0F);
		this.bone8.setTextureOffset(16, 0).addBox(2.0F, -14.0F, -2.0F, 2, 1, 4, 0.0F);
		this.bone8.setTextureOffset(15, 19).addBox(2.0F, -5.0F, -2.0F, 2, 1, 4, 0.0F);


		
	}
	/**
     		* Sets the models various rotation angles then renders the model.
     		*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		super.render(entity, f, f1, f2, f3, f4, f5);
		mixer.render(f5);
	}

	public void setRotation(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}

}