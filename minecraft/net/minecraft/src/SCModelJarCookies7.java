// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelJarCookies7 extends ModelBase {
	private final ModelRenderer cookie;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cookie2;
	private final ModelRenderer cube_r2;
	private final ModelRenderer cookie3;
	private final ModelRenderer cube_r3;
	private final ModelRenderer cookie4;
	private final ModelRenderer cube_r4;
	private final ModelRenderer cookie5;
	private final ModelRenderer cube_r5;
	private final ModelRenderer cookie6;
	private final ModelRenderer cube_r6;
	private final ModelRenderer cookie7;
	private final ModelRenderer cube_r7;

	public SCModelJarCookies7() {
		textureWidth = 24;
		textureHeight = 16;

		cookie = new ModelRenderer(this);
		cookie.setRotationPoint(-0.1306F, 23.2426F - 24F, 0.0367F);
		setRotation(cookie, 0.0F, 0.5236F, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(-0.0605F, -0.6426F, 0.0F);
		cookie.addChild(cube_r1);
		setRotation(cube_r1, 0.0F, 0.0F, -0.0611F);
		this.cube_r1.setTextureOffset(0, 0).addBox(-2.4789F, 0.1451F, -2.5F, 5, 1, 5, 0.0F);

		cookie2 = new ModelRenderer(this);
		cookie2.setRotationPoint(0.4683F, 22.0978F - 24F, -0.5183F);
		setRotation(cookie2, 0.0F, -0.5236F, 0.0F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0366F, -0.5978F, 0.0F);
		cookie2.addChild(cube_r2);
		setRotation(cube_r2, 0.0F, 0.0F, 0.0349F);
		this.cube_r2.setTextureOffset(0, 0).addBox(-2.5157F, 0.0987F, -2.5F, 5, 1, 5, 0.0F);

		cookie3 = new ModelRenderer(this);
		cookie3.setRotationPoint(0.5F, 21.05F - 24F, -0.25F);
		setRotation(cookie3, -3.1416F, 1.2654F, 3.1416F);

		cube_r3 = new ModelRenderer(this);
		cube_r3.setRotationPoint(0.4769F, -1.3F, 0.1504F);
		cookie3.addChild(cube_r3);
		setRotation(cube_r3, 0.0F, 0.0F, -0.0349F);
		this.cube_r3.setTextureOffset(0, 0).addBox(-3.0219F, 0.7826F, -2.6504F, 5, 1, 5, 0.0F);

		cookie4 = new ModelRenderer(this);
		cookie4.setRotationPoint(-0.2398F, 18.7474F - 24F, 0.4622F);
		setRotation(cookie4, 0.0F, -0.2618F, 0.0F);
		

		cube_r4 = new ModelRenderer(this);
		cube_r4.setRotationPoint(-0.0001F, -0.2974F, 0.0391F);
		cookie4.addChild(cube_r4);
		setRotation(cube_r4, -0.1309F, 0.0F, 0.0436F);
		this.cube_r4.setTextureOffset(0, 0).addBox(-2.4869F, -0.2003F, -2.5F, 5, 1, 5, 0.0F);

		cookie5 = new ModelRenderer(this);
		cookie5.setRotationPoint(0.25F, 19.85F - 24F, 0.0F);
		setRotation(cookie5, -3.1416F, -0.7418F, 3.1416F);
		

		cube_r5 = new ModelRenderer(this);
		cube_r5.setRotationPoint(0.0F, -0.5F, 0.0F);
		cookie5.addChild(cube_r5);
		setRotation(cube_r5, 0.0F, 0.0F, 0.1309F);
		this.cube_r5.setTextureOffset(0, 0).addBox(-2.4347F, -0.0043F, -2.5F, 5, 1, 5, 0.0F);

		cookie6 = new ModelRenderer(this);
		cookie6.setRotationPoint(0.5F, 17.85F - 24F, 0.25F);
		setRotation(cookie6, -3.1416F, 1.3963F, 3.1416F);
		

		cube_r6 = new ModelRenderer(this);
		cube_r6.setRotationPoint(0.0F, -0.5F, 0.0F);
		cookie6.addChild(cube_r6);
		setRotation(cube_r6, 0.0F, 0.0F, -0.0873F);
		this.cube_r6.setTextureOffset(0, 0).addBox(-2.5436F, -0.0019F, -2.5F, 5, 1, 5, 0.0F);

		cookie7 = new ModelRenderer(this);
		cookie7.setRotationPoint(-0.5F, 16.9F - 24F, -0.25F);
		setRotation(cookie7, 0.0F, -0.1309F, 0.0F);
		

		cube_r7 = new ModelRenderer(this);
		cube_r7.setRotationPoint(-0.0326F, -0.9F, -0.2479F);
		cookie7.addChild(cube_r7);
		setRotation(cube_r7, 0.0F, 0.0F, -0.0873F);
		this.cube_r7.setTextureOffset(0, 0).addBox(-2.5459F, 0.3994F, -2.2521F, 5, 1, 5, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float fillHeight, float shiftUp, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(fillHeight, shiftUp, f2, f3, f4, f5, entity);
		
		cookie.render(f5);
		cookie2.render(f5);
		cookie3.render(f5);
		cookie4.render(f5);
		cookie5.render(f5);
		cookie6.render(f5);
		cookie7.render(f5);
	}
	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float fillHeight, float shiftUp, float f2, float f3, float f4, float f5, Entity entity) {

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