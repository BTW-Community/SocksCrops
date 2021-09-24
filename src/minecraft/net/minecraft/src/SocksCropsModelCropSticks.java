package net.minecraft.src;

public class SocksCropsModelCropSticks extends FCModelBlock {
	private static final float onePX = 0.0625F;
	private static final float twoPX = 0.125F;
	private static final float threePX = onePX + twoPX;
	private static final float fourPX = 0.25F;
	private static final float fivePX = fourPX + onePX;
	private static final float sixPX = 0.375F;
	private static final float sevenPX = sixPX + onePX;
	private static final float eightPX = 0.5F;
	public static final float blockHeight = 2 * eightPX - onePX;
	
	@Override
	public void InitModel() {
		this.AddBox(twoPX, 0, twoPX, fourPX, blockHeight, fourPX);
		this.AddBox(eightPX + fourPX, 0, twoPX, eightPX + sixPX, blockHeight, fourPX);
		this.AddBox(twoPX, 0, eightPX + fourPX, fourPX, blockHeight, eightPX + sixPX);
		this.AddBox(eightPX + fourPX, 0, eightPX + fourPX, eightPX + sixPX, blockHeight, eightPX + sixPX);

	}
}

/**
 * this.AddBox(minx, miny, minz, maxx, maxy, maxz);
 *
 * 0.04F = 1px
 * 0.1875 = 3px
 * 0.2F = 5px
 * 0.375F = 6px
 * 0.5F = 8px
 * 0.4F = 10px
 * 0.75F = 12px
 */