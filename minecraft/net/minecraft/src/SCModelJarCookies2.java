// Made with Blockbench 4.2.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelJarCookies2 extends ModelBase {
	
	private final ModelRenderer cookie;
	private final ModelRenderer cube_r1;
	private final ModelRenderer cookie2;
	private final ModelRenderer cube_r2;

	public SCModelJarCookies2() {
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

	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float fillHeight, float shiftUp, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(fillHeight, shiftUp, f2, f3, f4, f5, entity);
		
		cookie.render(f5);
		cookie2.render(f5);
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