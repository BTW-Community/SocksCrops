package net.minecraft.src;

import org.lwjgl.opengl.GL11;

public class SocksMobsModelSkullGoat extends FCModelBlock {

	private static final float onePX = 0.0625F;
	private static final float twoPX = 0.125F;
	private static final float threePX = onePX + twoPX;
	private static final float fourPX = 0.25F;
	private static final float fivePX = fourPX + onePX;
	private static final float sixPX = 0.375F;
	private static final float sevenPX = sixPX + onePX;
	private static final float eightPX = 0.5F;
	public static final float blockHeight = threePX;
	public static final float fullBlock = eightPX * 2;
	private static final float halfPX = (onePX /2);
	
	IBlockAccess par1IBlockAccess;
	
	private static final float 
		hornWidth = twoPX,
		hornLength = sixPX,
		hornHeight = twoPX,
		leftHornFromEdge = fivePX + halfPX,
		rightHornFromEdge= leftHornFromEdge + threePX,
		hornFromTop = 0.0F;
	
	private static final float 
		skullFromEdge = leftHornFromEdge,
		skullFromTop = fivePX,
		skullWidth = skullFromEdge + fivePX,
		skullLength = skullFromTop + sixPX,
		skullHeight = fourPX;
	
	
	@Override
	public void InitModel() {

		
		//HornLeft
		this.AddBox(leftHornFromEdge, 0, 0, leftHornFromEdge + hornWidth, hornHeight, hornLength);
		//Horn Right
		this.AddBox(rightHornFromEdge, 0, 0, rightHornFromEdge + hornWidth, hornHeight, hornLength);
		
		//Skull
		this.AddBox(skullFromEdge, 0, skullFromTop, skullWidth, skullHeight, skullLength);
		
		this.AddBox(skullFromEdge, skullHeight, skullFromTop, skullWidth, skullHeight + twoPX, skullFromTop + twoPX);
		
		this.AddBox(skullFromEdge, skullHeight, skullFromTop + fourPX, skullFromEdge + fivePX, skullHeight + twoPX, skullFromTop + fourPX + fivePX);
		
		this.AddBox(skullFromEdge + onePX, threePX, skullFromEdge + onePX, skullFromTop + onePX + threePX, threePX + fourPX, fullBlock - onePX);
		

		//this.AddBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
}