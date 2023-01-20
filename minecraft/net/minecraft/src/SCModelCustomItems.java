// Made with Blockbench 4.5.0
// Exported for Minecraft version 1.5.2
// Paste this class into your mod and call render() in your Entity Render class
// Note: You may need to adjust the y values of the 'setRotationPoint's

package net.minecraft.src;

public class SCModelCustomItems extends ModelBase {
	private final ModelRenderer cutBread;
	private final ModelRenderer cube_r1;
	private final ModelRenderer bread;
	private final ModelRenderer potatoes;
	private final ModelRenderer bowl;
	private final ModelRenderer cube_r2;
	private final ModelRenderer stew;
	private final ModelRenderer mushroom;
	private final ModelRenderer chicken;
	private final ModelRenderer fish;
	private final ModelRenderer sandwich;
	private final ModelRenderer creeper;
	private final ModelRenderer book;
	private final ModelRenderer manuscript;
	private final ModelRenderer quill;
	private final ModelRenderer feather_r1;
	private final ModelRenderer breadSlice;
	private final ModelRenderer breadSliceTop;
	
	public SCModelCustomItems() {
		textureWidth = 64;
		textureHeight = 64;

		breadSlice = new ModelRenderer(this);
		breadSlice.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.breadSlice.setTextureOffset(18, 18).addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
		
		breadSliceTop = new ModelRenderer(this);
		breadSliceTop.setRotationPoint(0.0F, -2.0F, 0.0F);
		this.breadSliceTop.setTextureOffset(18, 18).addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
		
		cutBread = new ModelRenderer(this);
		cutBread.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.cutBread.setTextureOffset(0, 12).addBox(-6.0F, -6.0F, -3.0F, 5, 6, 6, 0.0F);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setRotationPoint(0.0F, 0.0F, 0.0F);
		cutBread.addChild(cube_r1);
		setRotation(cube_r1, 0.0F, 1.5708F, 0.0F);
		this.cube_r1.setTextureOffset(18, 18).addBox(-3.0F, -1.0F, 0.0F, 6, 1, 6, 0.0F);

		bread = new ModelRenderer(this);
		bread.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bread.setTextureOffset(0, 0).addBox(-6.0F, -6.0F, -3.0F, 12, 6, 6, 0.0F);
		
		potatoes = new ModelRenderer(this);
		potatoes.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.potatoes.setTextureOffset(52, 0).addBox(-5.0F, -3.0F, 0.0F, 3, 3, 3, 0.0F);
		this.potatoes.setTextureOffset(52, 0).addBox(-2.0F, -2.0F, 2.0F, 3, 2, 3, 0.0F);
		
		bowl = new ModelRenderer(this);
		bowl.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.bowl.setTextureOffset(40, 17).addBox(-3.0F, -1.0F, -3.0F, 6, 1, 6, 0.0F);
		this.bowl.setTextureOffset(40, 5).addBox(3.0F, -4.0F, -3.0F, 1, 3, 6, 0.0F);
		this.bowl.setTextureOffset(40, 5).addBox(-4.0F, -4.0F, -3.0F, 1, 3, 6, 0.0F);

		cube_r2 = new ModelRenderer(this);
		cube_r2.setRotationPoint(0.0F, -2.5F, 0.0F);
		bowl.addChild(cube_r2);
		setRotation(cube_r2, 0.0F, -1.5708F, 0.0F);
		this.cube_r2.setTextureOffset(46, 6).addBox(-4.0F, -1.5F, -4.0F, 1, 3, 8, 0.0F);
		this.cube_r2.setTextureOffset(46, 6).addBox(3.0F, -1.5F, -4.0F, 1, 3, 8, 0.0F);

		stew = new ModelRenderer(this);
		stew.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.stew.setTextureOffset(40, 24).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		mushroom = new ModelRenderer(this);
		mushroom.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.mushroom.setTextureOffset(40, 30).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		chicken = new ModelRenderer(this);
		chicken.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.chicken.setTextureOffset(40, 36).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);
		
		fish = new ModelRenderer(this);
		fish.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.fish.setTextureOffset(40, 42).addBox(-3.0F, -3.0F, -3.0F, 6, 1, 6, 0.0F);

		sandwich = new ModelRenderer(this);
		sandwich.setRotationPoint(0.0F, 0.0F, 0.0F);
		setRotation(sandwich, 0.0F, -1.5708F, 0.0F);
		this.sandwich.setTextureOffset(0, 24).addBox(-3.0F, -5.0F, -3.0F, 6, 5, 6, 0.0F);
		
		creeper = new ModelRenderer(this);
		creeper.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.creeper.setTextureOffset(24, 27).addBox(0.0F, -4.0F, -3.0F, 4, 4, 4, 0.0F);
		this.creeper.setTextureOffset(24, 27).addBox(-4.0F, -4.0F, -1.0F, 4, 4, 4, 0.0F);
		
		book = new ModelRenderer(this);
		book.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.book.setTextureOffset(0, 35).addBox(-4.0F, -1.0F, -5.0F, 8, 1, 10, 0.0F);
		this.book.setTextureOffset(0, 49).addBox(-4.0F, -4.0F, -5.0F, 1, 3, 10, 0.0F);
		this.book.setTextureOffset(0, 35).addBox(-4.0F, -5.0F, -5.0F, 8, 1, 10, 0.0F);
		this.book.setTextureOffset(0, 37).addBox(-3.5F, -4.0F, -4.5F, 7, 3, 9, 0.0F);

		manuscript = new ModelRenderer(this);
		manuscript.setRotationPoint(0.0F, 0.0F, 0.0F);
		this.manuscript.setTextureOffset(12, 49).addBox(-4.0F, -5.0F, -1.0F, 8, 5, 2, 0.25F);
		this.manuscript.setTextureOffset(0, 49).addBox(0.75F, -5.5F, -1.0F, 2, 1, 2, 0.25F);
		
		quill = new ModelRenderer(this);
		quill.setRotationPoint(0.0F, 0.0F, 0.0F);
		
		feather_r1 = new ModelRenderer(this);
		feather_r1.setRotationPoint(6.0F, 0.0F, 0.5F);
		quill.addChild(feather_r1);
		setRotation(feather_r1, 0.0F, 0.0F, -0.3491F);
		this.feather_r1.setTextureOffset(48, 43).addBox(0.0F, -13.0F, -3.5F, 0, 13, 7, 0.0F);
	}

	/**
	* Sets the models various rotation angles then renders the model.
	*/
	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
		this.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
		
//		if (f == SCDefs.burger.itemID)
//		{
//			breadSliceTop.render(f5);
//			breadSlice.render(f5);
//		}
//		
//		if (f == SCDefs.breadSlice.itemID || f == SCDefs.hamburgerUnfinished.itemID)
//		{
//			breadSlice.render(f5);
//		}
		
		if (f == SCDefs.halfBread.itemID)
		{
			cutBread.render(f5);
		}
		
		if (f == Item.bread.itemID)
		{
			bread.render(f5);
		}
		
		if (f == Item.appleRed.itemID)
		{
			cutBread.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemTastySandwich.itemID)
		{
			sandwich.render(f5);
		}
		
		if (f == FCBetterThanWolves.fcItemCreeperOysters.itemID)
		{
			creeper.render(f5);
		}
	}

	/**
	* Sets the model's various rotation angles. For bipeds, f and f1 are used for animating the movement of arms
	* and legs, where f represents the time(so that arms and legs swing back and forth) and f1 represents how
	* "far" arms and legs can swing at most.
	*/
	@Override
    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
		this.breadSliceTop.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.breadSlice.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.bread.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.potatoes.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.bowl.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.stew.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.mushroom.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.chicken.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.fish.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.sandwich.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.creeper.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.book.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.manuscript.rotateAngleY = f3 / (180F / (float)Math.PI);
        this.quill.rotateAngleY = f3 / (180F / (float)Math.PI);

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