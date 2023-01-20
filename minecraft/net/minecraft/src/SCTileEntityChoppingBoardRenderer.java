package net.minecraft.src;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;

public class SCTileEntityChoppingBoardRenderer extends TileEntitySpecialRenderer
{
	private static ArrayList<Item> cuttingItems = new ArrayList();
	
	public static boolean isCuttingItem(Item item) {
		return cuttingItems.contains(item);
	}
	
	public static void addValidCuttingItem(Item item) {
		cuttingItems.add(item);
	}
	
	static 	{
		addValidCuttingItem(SCDefs.knifeStone);
		addValidCuttingItem(SCDefs.knifeIron);
		addValidCuttingItem(SCDefs.knifeDiamond);
		addValidCuttingItem(Item.axeStone);
		addValidCuttingItem(Item.axeIron);
		addValidCuttingItem(Item.axeDiamond);
	}

	private boolean isBlock(Item item )
    {
    	if ( item instanceof ItemBlock )
    		return true;
    	
    	else return false;
	}
	
	//exeptions where the block is displayed as an item
	private boolean blockException(Item item )
    {	
		Block block = Block.blocksList[item.itemID];
			
    	if (block != null &&
    		block instanceof SCBlockChoppingBoard ||
    		block instanceof BlockPane || 
    		block instanceof BlockFlower ||
    		block instanceof BlockRailBase ||
    		block instanceof FCBlockTorchBase ||
    		block instanceof FCBlockLadderBase ||
    		block instanceof BlockWeb ||
    		block instanceof BlockLever || 
    		block instanceof BlockVine ||
    		block instanceof FCBlockAestheticVegetation)
    		return true;
    	
    	else return false;
	}
	
    public void renderTileEntityAt( TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
    	SCTileEntityChoppingBoard cuttingBoard = (SCTileEntityChoppingBoard)tileEntity;
    	
    	if (!cuttingBoard.hasItemSpecialRenderer()) 
    	{
    		RenderKnifeStackStack( cuttingBoard, xCoord, yCoord, zCoord,cuttingBoard.getKnifeStack() );
    	}
    	else {
    		RenderSpecialStack(cuttingBoard, xCoord, yCoord, zCoord );
    		
    	}
    }
    
    private void RenderFly(SCTileEntityChoppingBoard cuttingBoard, double xCoord, double yCoord, double zCoord) {
		ModelBase model = new SCModelCustomFly();
		this.bindTextureByName("/scmodtex/choppingBoard/fly.png");
    	
		float xPos = (float) xCoord;
    	float yPos = (float) yCoord;
    	float zPos = (float) zCoord;
    	
		float scale = 0.0625F * 0.5F;
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        
        GL11.glTranslatef(xPos + 0.5F,  yPos + 10/16F + MathHelper.sin(cuttingBoard.ticks)*0.02F, zPos + 0.5F);    	        
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(-1.0F, -1.0F, 1.0F);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        
        model.render((Entity)null, cuttingBoard.ticks, 0.0F, 0F, 0F, 0.0F, scale);
        
        GL11.glPopMatrix();
	}

	private void RenderSpecialStack(SCTileEntityChoppingBoard cuttingBoard, double xCoord, double yCoord, double zCoord) {
    	ItemStack stack = cuttingBoard.getKnifeStack();
    	float rotation = (float)(cuttingBoard.getItemRotation() * 360) / 16.0F;
    	
    	int itemDamage = stack.getItemDamage();
    	
    	float xPos = (float) xCoord;
    	float yPos = (float) yCoord;
    	float zPos = (float) zCoord;
    	
    	if ( stack != null )
    	{
    		if (stack.itemID != Item.skull.itemID)
    		{
    			ModelBase model;
    			Item item = stack.getItem();
    			
    			float scale = 0.0625F;
    			
    			if (stack.getItem() instanceof FCItemBucket)
    			{
        			model = new SCModelCustomBucket();
        			this.bindTextureByName("/scmodtex/choppingBoard/bucket.png");
        			
        			scale = scale * 0.75F;
    			}
    			else if (item == Item.book || item == Item.enchantedBook )
    			{
        			model = new SCModelCustomBook();
        			this.bindTextureByName("/scmodtex/choppingBoard/book.png");
        			
        			scale = scale * 0.75F;        			
    			}
    			else if (item == Item.writableBook || item == Item.writtenBook)
    			{
        			model = new SCModelCustomBook();
        			this.bindTextureByName("/scmodtex/choppingBoard/writtenBook.png");
        			
        			scale = scale * 0.75F;
    			}
    			else if (item == Item.bowlEmpty || item == Item.bowlSoup || item == FCBetterThanWolves.fcItemFishSoup || item == FCBetterThanWolves.fcItemChickenSoup || item == FCBetterThanWolves.fcItemHeartyStew)
    			{
        			model = new SCModelCustomBowl();
        			this.bindTextureByName("/scmodtex/choppingBoard/bowl.png");
    			}
    			else if (item == FCBetterThanWolves.fcItemDonut || item == SCDefs.donutSugar || item == SCDefs.donutChocolate)
    			{
    				model = new SCModelCustomDonut();
    				this.bindTextureByName("/scmodtex/choppingBoard/donut.png");
    			}
    			else if (item == SCDefs.itemMuffinChocolate || item == SCDefs.itemMuffinSweetberry || item == SCDefs.itemMuffinBlueberry)
    			{
    				model = new SCModelCustomMuffin();
    				this.bindTextureByName("/scmodtex/choppingBoard/muffin.png");

    			}
    			else if (item == FCBetterThanWolves.fcItemEnderSpectacles)
    			{
    				model = new SCModelCustomEnderSpectacles();
    				this.bindTextureByName("/scmodtex/choppingBoard/spectacles.png");
    			}
    			else if (item == FCBetterThanWolves.fcItemDung || item == FCBetterThanWolves.fcItemGoldenDung)
    			{
    				if (item == FCBetterThanWolves.fcItemDung)
    				{
    					RenderFly(cuttingBoard, xCoord, yCoord, zCoord);
    				}
    				
    				model = new SCModelCustomDung();
    				this.bindTextureByName("/scmodtex/choppingBoard/dung.png");
    			}
    			else if (item == Item.appleRed || item == Item.appleGold)
    			{
    				model = new SCModelCustomApple();
    				this.bindTextureByName("/scmodtex/choppingBoard/apple.png");
    			}
    			else if (item == Item.cake || item == SCDefs.chocolateCakeItem || item == SCDefs.carrotCakeItem)
    			{
    				model = new SCModelCustomCake();
    				this.bindTextureByName("/scmodtex/choppingBoard/cake.png");
    				
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.cakeSlice || item == SCDefs.chocolateCakeSlice || item == SCDefs.carrotCakeSlice)
    			{
    				model = new SCModelCustomCakeSlice();
    				this.bindTextureByName("/scmodtex/choppingBoard/cakeSlice.png");
    			
    				scale *= 0.75F;
    			}
    			else if (item == Item.pumpkinPie || item == SCDefs.sweetberryPieCooked || item == SCDefs.blueberryPieCooked ||
    					item == SCDefs.applePieCooked || item == SCDefs.cherryPieCooked || item == SCDefs.lemonPieCooked || item == SCDefs.pieCrust)
    			{
    				model = new SCModelCustomPie();
    				this.bindTextureByName("/scmodtex/choppingBoard/pie.png");
    			
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.pumpkinPieSlice || item == SCDefs.sweetberryPieSlice || item == SCDefs.blueberryPieSlice ||
    					item == SCDefs.applePieSlice || item == SCDefs.cherryPieSlice || item == SCDefs.lemonPieSlice )
    			{
    				model = new SCModelCustomPieSlice();
    				this.bindTextureByName("/scmodtex/choppingBoard/pieSlice.png");
    			
    				scale *= 0.75F;
    			}
    			else if (item == Item.fishRaw)
    			{
    				model = new SCModelEntityCod();
    				this.bindTextureByName("/scmodtex/fishtrap/fish.png");
    				
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.cod)
    			{
    				model = new SCModelEntityCod();
    				this.bindTextureByName("/scmodtex/fishtrap/cod.png");
    				
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.salmon)
    			{
    				model = new SCModelEntitySalmon();
    				this.bindTextureByName("/scmodtex/fishtrap/salmon.png");
    				
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.tropical)
    			{
    				model = new SCModelEntityTropical();
    				this.bindTextureByName("/scmodtex/fishtrap/tropical.png");
    				
    				scale *= 0.75F;
    			}
    			else if (item == SCDefs.puffer)
    			{
    				model = new SCModelEntityPufferfish();
    				this.bindTextureByName("/scmodtex/fishtrap/puffer.png");
    				
    				scale *= 0.75F;
    			}
    			else
    			{
//    				if (item == SCDefs.hamburgerUnfinished || item == SCDefs.hamburger)
//    				{
//    					ItemStack patty = new ItemStack(SCDefs.beefPattyCooked);
//    					RenderKnifeStackStack(cuttingBoard, xCoord, yCoord + 1/16D, zCoord, patty);
//    				}
    				
    				model = new SCModelCustomItems();
        			this.bindTextureByName("/scmodtex/choppingBoard/texture.png");
    			}
    			
    			GL11.glPushMatrix();
    	        GL11.glDisable(GL11.GL_CULL_FACE);
    	        
    	        GL11.glTranslatef(xPos + 0.5F, yPos + 2/16F, zPos + 0.5F);  	        
    	        
    	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	        GL11.glScalef(-1.0F, -1.0F, 1.0F);
    	        GL11.glEnable(GL11.GL_ALPHA_TEST);
    	        
    	        if ( item == Item.fishRaw || item == SCDefs.cod || item == SCDefs.salmon || item == SCDefs.tropical || item == SCDefs.puffer)
    	        {
    	        	model.render((Entity)null, 0F, -1F, 0.0F, rotation, 0.0F, scale);
    	        }
    	        else model.render((Entity)null, stack.itemID, 0.0F, 0.0F, rotation, 0.0F, scale);
    	        
    	        GL11.glPopMatrix();
    		}
    		else
    		{
    			ModelSkeletonHead modelSkull;
    			
    			switch (itemDamage) {
				default:
    			case 0:
    				modelSkull = new ModelSkeletonHead(0, 0, 64, 32);
    				this.bindTextureByName("/mob/skeleton.png");
					break;
					
    			case 1:
    				modelSkull = new ModelSkeletonHead(0, 0, 64, 32);
    				this.bindTextureByName("/mob/skeleton_wither.png");
					break;
					
    			case 2:
    				modelSkull = new ModelSkeletonHead(0, 0, 64, 64);
    				this.bindTextureByName("/mob/zombie.png");
					break;
					
    			case 3:
    				modelSkull = new ModelSkeletonHead(0, 0, 64, 32);
    				this.bindTextureByName("/mob/char.png");
					break;
					
    			case 4:
    				modelSkull = new ModelSkeletonHead(0, 0, 64, 32);
    				this.bindTextureByName("/mob/creeper.png");
					break;
					
    			case 5:
    				modelSkull = new ModelSkeletonHead(0, 0, 32, 16);
    				this.bindTextureByName("/btwmodtex/fcInfusedSkull.png");
					break;
				}
    			

    	        
    			GL11.glPushMatrix();
    	        GL11.glDisable(GL11.GL_CULL_FACE);
    	        
    	        GL11.glTranslatef(xPos + 0.5F, yPos + 2/16F, zPos + 0.5F);
    	        
    	        float var10 = 0.0625F * 0.75F;
    	        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    	        GL11.glScalef(-1.0F, -1.0F, 1.0F);
    	        GL11.glEnable(GL11.GL_ALPHA_TEST);
    	        
    	        modelSkull.render((Entity)null, 0.0F, 0.0F, 0.0F, rotation, 0.0F, var10);
    	        
    	        
    	        if (itemDamage == 5)
    	        {
    	        	RenderInfusedEyes( modelSkull, rotation);
    	        }
    	        
    	        GL11.glPopMatrix();
    		}
    	}
	}
    
    // FCMOD: Added
    private void RenderInfusedEyes( ModelSkeletonHead model, float fYaw )
    {
        bindTextureByName("/btwmodtex/fcInfusedSkullEyes.png");
        float var4 = 1.0F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDepthMask(true);
        char var5 = 61680;
        int var6 = var5 % 65536;
        int var7 = var5 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var6 / 1.0F, (float)var7 / 1.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, var4);
        
        model.render((Entity)null, 0.0F, 0.0F, 0.0F, fYaw, 0.0F, 0.0625F);
}

    private void RenderKnifeStackStack( SCTileEntityChoppingBoard cuttingBoard, double xCoord, double yCoord, double zCoord, ItemStack stack )
    {
    	if ( stack != null )
    	{

    		EntityItem entity = new EntityItem( cuttingBoard.worldObj, 0.0D, 0.0D, 0.0D, stack );

    		Item item = entity.getEntityItem().getItem();
    		int meta = cuttingBoard.worldObj.getBlockMetadata( cuttingBoard.xCoord, cuttingBoard.yCoord, cuttingBoard.zCoord );

    		entity.getEntityItem().stackSize = 1;
    		entity.hoverStart = 0.0F;

    		GL11.glPushMatrix();
    		
    		float rotation = -(float)(cuttingBoard.getItemRotation() * 22.5F );
    		
    		if ( isBlock(item) && !blockException(item)) {

    			GL11.glTranslatef( (float)xCoord + 0.5F, (float)yCoord + ( 2.75F / 16F ), (float)zCoord + 8F/16F ); 
    			
    			if (item.itemID == SCDefs.storageJar.blockID || item.itemID == SCDefs.seedJar.blockID)
    			{
    				GL11.glScalef(3F, 3F, 3F);
    			}
    			else GL11.glScalef(1.75F, 1.75F, 1.75F);

    			GL11.glRotatef(rotation + 90F, 0F, 1F, 0F);
    		} 
    		else {


    			GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( 2.5F / 16F ), (float)zCoord + 8F/16F ); 

    			GL11.glScalef(1.45F, 1.45F, 1.45F);


    			if (item instanceof FCItemTool)
    			{
    				GL11.glRotatef(rotation + 90F, 0F, 1F, 0F);
    			}
    			else GL11.glRotatef(rotation, 0F, 1F, 0F);
    			

    			Item storedItem = cuttingBoard.getKnifeStack().getItem();

    			if ( isCuttingItem(storedItem))
    			{
    				GL11.glRotatef( 180F, 1.0F, 0.0F, 0.0F);

    				GL11.glTranslatef( 0,  -5.7F / 16F , 0 ); 

    			}
    			else
    			{
    				GL11.glRotatef( 90F, 1.0F, 0.0F, 0.0F);

    				float shift = - (14/64F) - 3/512F;

    				GL11.glTranslatef( 0, shift , 0); 
    			}
    		}

    		RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);

    		GL11.glPopMatrix();
    	}
    }

}