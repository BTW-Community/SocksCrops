// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomPieSlice extends ModelBase {
	private final ModelRenderer slice;
	private final ModelRenderer pumpkin;
	private final ModelRenderer blueberry;
	private final ModelRenderer sweetberry;
	private final ModelRenderer apple;
	private final ModelRenderer cherry;
	private final ModelRenderer lemon;

	public SCModelCustomPieSlice() {
		textureWidth = 32;
		textureHeight = 48;

		slice = new ModelRenderer(this);
		slice.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.slice.setTextureOffset(0, 0).addBox(-3.0F, -5.0F, -3.0F, 6, 5, 6, 0.0F);

		pumpkin = new ModelRenderer(this);
		pumpkin.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.pumpkin.setTextureOffset(0, 11).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);

		blueberry = new ModelRenderer(this);
		blueberry.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.blueberry.setTextureOffset(0, 20).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);

		sweetberry = new ModelRenderer(this);
		sweetberry.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.sweetberry.setTextureOffset(0, 29).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);

		apple = new ModelRenderer(this);
		apple.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.apple.setTextureOffset(10, 11).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);

		cherry = new ModelRenderer(this);
		cherry.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.cherry.setTextureOffset(10, 20).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);

		lemon = new ModelRenderer(this);
		lemon.setRotationPoint(0.0F, -1.0F, 0.0F);
		this.lemon.setTextureOffset(10, 29).addBox(-2.0F, -4.0F, -3.0F, 5, 4, 5, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		slice.render(f5);
		
		if (f == SCDefs.pumpkinPieSlice.itemID)
		{
			pumpkin.render(f5);
		}
		else if (f == SCDefs.sweetberryPieSlice.itemID)
		{
			sweetberry.render(f5);
		}
		else if (f == SCDefs.blueberryPieSlice.itemID)
		{
			blueberry.render(f5);
		}
		else if (f == SCDefs.applePieSlice.itemID)
		{
			apple.render(f5);
		}
		else if (f == SCDefs.cherryPieSlice.itemID)
		{
			cherry.render(f5);
		}
		else if (f == SCDefs.lemonPieSlice.itemID)
		{
			lemon.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        this.slice.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.pumpkin.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.sweetberry.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.blueberry.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.apple.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.cherry.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.lemon.rotateAngleY = f3 / (180F / (float)Math.PI);
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