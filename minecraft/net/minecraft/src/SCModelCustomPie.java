// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomPie extends ModelBase {
	private final ModelRenderer crustInside;
	private final ModelRenderer crust;
	private final ModelRenderer pumpkin;
	private final ModelRenderer blueberry;
	private final ModelRenderer sweetberry;
	private final ModelRenderer apple;
	private final ModelRenderer cherry;
	private final ModelRenderer lemon;

	public SCModelCustomPie() {
		textureWidth = 48;
		textureHeight = 96;

		crustInside = new ModelRenderer(this);
		crustInside.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.crustInside.setTextureOffset(0, 17).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		crust = new ModelRenderer(this);
		crust.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.crust.setTextureOffset(0, 0).addBox(-6.0F, -5.0F, -6.0F, 12, 5, 12, 0.0F);

		pumpkin = new ModelRenderer(this);
		pumpkin.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.pumpkin.setTextureOffset(0, 31).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		blueberry = new ModelRenderer(this);
		blueberry.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.blueberry.setTextureOffset(0, 41).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		sweetberry = new ModelRenderer(this);
		sweetberry.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.sweetberry.setTextureOffset(0, 51).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		apple = new ModelRenderer(this);
		apple.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.apple.setTextureOffset(0, 61).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		cherry = new ModelRenderer(this);
		cherry.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cherry.setTextureOffset(0, 71).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);

		lemon = new ModelRenderer(this);
		lemon.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.lemon.setTextureOffset(0, 81).addBox(-5.0F, -5.0F, -5.0F, 10, 4, 10, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		crustInside.render(f5);
		crust.render(f5);
		
		if (f == Item.pumpkinPie.itemID)
		{
			pumpkin.render(f5);
		}
		else if (f == SCDefs.sweetberryPieCooked.itemID)
		{
			sweetberry.render(f5);
		}
		else if (f == SCDefs.blueberryPieCooked.itemID)
		{
			blueberry.render(f5);
		}
		else if (f == SCDefs.applePieCooked.itemID)
		{
			apple.render(f5);
		}
		else if (f == SCDefs.cherryPieCooked.itemID)
		{
			cherry.render(f5);
		}
		else if (f == SCDefs.lemonPieCooked.itemID)
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
        this.crustInside.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.crust.rotateAngleY = f3 / (180F / (float)Math.PI);
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