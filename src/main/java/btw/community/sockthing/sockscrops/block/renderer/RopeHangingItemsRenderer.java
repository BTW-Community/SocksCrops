package btw.community.sockthing.sockscrops.block.renderer;

import btw.community.sockthing.sockscrops.block.tileentities.RopeHangingItemsTileEntity;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RopeHangingItemsRenderer extends TileEntitySpecialRenderer {

    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount )
    {
        RopeHangingItemsTileEntity hangingRope = (RopeHangingItemsTileEntity)tileEntity;

        ItemStack[] storageStack = hangingRope.getStorageStack();

        RenderStorageStack( hangingRope, xCoord, yCoord, zCoord, storageStack);



    }

    private void RenderStorageStack(RopeHangingItemsTileEntity rope, double xCoord, double yCoord, double zCoord, ItemStack[] stack) {

        int numberSlotsFilled = 0;
        for (int slot = 0; slot < 3; slot++) {
            if ( stack[slot] != null ) numberSlotsFilled++;
        }

        for (int slot = 0; slot < 3; slot++) {
            if ( stack[slot] != null )
            {

                EntityItem entity = new EntityItem( rope.worldObj, 0.0D, 0.0D, 0.0D, stack[slot] );

                Item item = entity.getEntityItem().getItem();
                int meta = rope.worldObj.getBlockMetadata( rope.xCoord, rope.yCoord, rope.zCoord );

                entity.getEntityItem().stackSize = stack[slot].stackSize;
                entity.hoverStart = 0.0F;

                GL11.glPushMatrix();

                float rotation = -(float)(rope.getItemRotation()[0] * 22.5F );

                GL11.glTranslatef( (float)xCoord + ( 8F / 16F ), (float)yCoord + ( -1F / 16F ), (float)zCoord + 8F/16F );

                GL11.glScalef(1.45F, 1.45F, 1.45F);

                GL11.glRotatef(rotation, 0F, 1F, 0F);

//			GL11.glRotatef( 180F, 1.0F, 0.0F, 0.0F);

//			GL11.glTranslatef( 0,  -5.7F / 16F , 0 );

                if (numberSlotsFilled == 2){
                    if (slot == 0) GL11.glTranslatef( (float)xCoord + ( 4F / 16F ), (float)yCoord, (float)zCoord );
                    if (slot == 1) GL11.glTranslatef( (float)xCoord - ( 4F / 16F ), (float)yCoord, (float)zCoord );
                }

                RenderManager.instance.renderEntityWithPosYaw(entity, 0, 0, 0, 0, 0);

                GL11.glPopMatrix();
            }
        }

    }


}
