package com.geullo.coinchange.UI;

import com.geullo.coinchange.CoinType;
import com.geullo.coinchange.Packet;
import com.geullo.coinchange.PacketList;
import com.geullo.coinchange.Render;
import com.geullo.coinchange.util.Sound;
import com.geullo.coinchange.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CoinChangeUI extends GuiScreen {

    private double[] bgPos = new double[2], bgSize = new double[2], btnPos = new double[2], btnSize = new double[2],
            longBoxPos = new double[2], longBoxSize = new double[2], boxPos = new double[2], boxSize = new double[2],
            playerPointPos = new double[2],playerPointSize = new double[2], alertBgPos = new double[2],alertBgSize = new double[2],
            alertMessagePos = new double[2],alertMessageSize = new double[2]
            ;

    private boolean mouseGrabbed = false, shiftClicked = false,failAlert = false;

    public JobType playerJob = JobType.NONE;
    public CoinType type;
    public int point=0,changePoint=1;
    public String failMessage = "", amtPoint = "";;
    public ItemStack coin = ItemStack.EMPTY;


    public CoinChangeUI(CoinType type) {
        this.type = type;
        Packet.sendMessage(PacketList.GET_COIN_CHANGE_ITEM.recogCode+"/"+type.type);
    }


    @Override
    public void initGui() {
        initBg();
        initBtn();
        initBox();
        initPlayerPoint();
        initAlert();
    }

    private void initBg() {
        bgSize[0] = width / 2.2d;
        bgSize[1] = height / 1.8d;
        bgPos[0] = (width / 2d) - (bgSize[0] / 2);
        bgPos[1] = (height / 2d) - (bgSize[1] / 1.75);
    }

    private void initBtn() {
        btnSize[0] = bgSize[0] / 3.14;
        btnSize[1] = bgSize[1] / 5.5d /1.495;
        btnPos[0] = bgPos[0] + ((bgSize[0] / 2) - (btnSize[0] / 1.95));
        btnPos[1] = bgPos[1] + ((bgSize[1] / 2) + (btnSize[1] * 1.73));
    }

    private void initAlert(){
        alertBgSize[0] = bgSize[0]/1.45;
        alertBgSize[1] = bgSize[1]/2.15;
        alertBgPos[0] = bgPos[0]+(bgSize[0]/2)-(alertBgSize[0]/2);
        alertBgPos[1] = bgPos[1]+(bgSize[1]/2)-(alertBgSize[1]/2);

        alertMessageSize[0] = playerPointSize[0];
        alertMessageSize[1] = playerPointSize[1];
        alertMessagePos[0] = alertBgPos[0]+(alertBgSize[0]/2);
        alertMessagePos[1] = (alertBgPos[1]+(alertBgSize[1]/2))-(alertMessageSize[1]/2);
    }

    private void initBox() {
        boxSize[0] = btnSize[0]/2;
        boxSize[1] = btnSize[1]*1.835;
        longBoxSize[0] = btnSize[0]/1.33;
        longBoxSize[1] = btnSize[1] / 1.25;
        if (type.equals(CoinType.JOB_POINT_TO_COIN)) {
            longBoxPos[0] = bgPos[0] + (btnSize[0]/2.35);
            longBoxPos[1] = bgPos[1] + (btnSize[1]*3.78);
            boxPos[0] = longBoxPos[0]+longBoxSize[0]+(btnSize[0]/1.135);
            boxPos[1] = longBoxPos[1]+((longBoxSize[1]/2)-(boxSize[1]/2));
        }
        else {
            boxPos[0] = bgPos[0] + (btnSize[0]/1.75);
            boxPos[1] = bgPos[1] + (btnSize[1]*3.38);
            longBoxPos[0] = (boxPos[0]+boxSize[0])+(btnSize[0]/1.135);
            longBoxPos[1] = boxPos[1]+((boxSize[1]/2)-(longBoxSize[1]/2));
        }
    }

    private void initPlayerPoint() {
        playerPointPos[0] = bgPos[0] + boxSize[0]/2.85;
        playerPointPos[1] = bgPos[1] + boxSize[1]/3.25;
        playerPointSize[0] = boxSize[0]/2;
        playerPointSize[1] = boxSize[1]/1.75;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        if (!mouseGrabbed) {
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            mouseGrabbed = true;
        }
        if (changePoint>point) changePoint = point;
        if (isShiftDown()) {
            changePoint = Math.min(point,32);
        }
        else if (shiftClicked){
            changePoint = Integer.parseInt(amtPoint.equals("")?"0":amtPoint);
        }
        Render.bindTexture(new ResourceLocation("coinchange", "background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
        Render.bindTexture(new ResourceLocation("coinchange", "change_long.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(longBoxPos[0], longBoxPos[1], longBoxSize[0], longBoxSize[1]);
        Render.bindTexture(new ResourceLocation("coinchange", "change_box.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(boxPos[0], boxPos[1], boxSize[0], boxSize[1]);
        Render.drawString(Utils.translate(point),(float) playerPointPos[0],(float) playerPointPos[1],(int) playerPointSize[0],(int) playerPointSize[1],0,0xffffff);
        Render.drawString(Utils.translate(changePoint),(float) (longBoxPos[0]+(boxSize[0]/5.8)),(float) (longBoxPos[1]+(boxSize[1]/5.5/2)),(int) playerPointSize[0],(int) playerPointSize[1],0,0xffffff);
        ItemStack stack = coin.copy();
        stack.setCount(changePoint==0?1:changePoint);
        Render.drawItemStack(stack,(int) (boxPos[0]+(longBoxSize[0]/5.5/4.25)),(int) (boxPos[1]+(longBoxSize[1]/4.97/2)), (float) boxSize[0]/1.03f,(float) boxSize[1]*1.025f);
        Render.bindTexture(new ResourceLocation("coinchange", "change_btn.png"));
        Render.bindTexture(new ResourceLocation("coinchange", "change_btn.png"));
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btnPos[0], btnPos[1], btnSize[0], btnSize[1])) {
            Render.setColor(0xff929292);
        }
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(btnPos[0], btnPos[1], btnSize[0], btnSize[1]);
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btnPos[0], btnPos[1], btnSize[0], btnSize[1])) {
            List<String> a = new ArrayList<>();
            a.add("§6SHIFT §f키를 누를시 §6최대 32개§f씩 교환이 가능합니다.");
            a.add("§7( 갯수를 입력시 해당 갯수만큼 교환이 됩니다. )");
            Render.drawTooltip(a,mouseX,mouseY);
        }
        if (failAlert) {
            GlStateManager.pushMatrix();
            GlStateManager.disableDepth();
            Render.bindTexture(new ResourceLocation("coinchange", "background.png"));
            Render.setColor(0x806f6f6f);
            Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
            Render.bindTexture(new ResourceLocation("coinchange", "alert_bg.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(alertBgPos[0], alertBgPos[1], alertBgSize[0], alertBgSize[1]);
            Render.drawString(failMessage,(float) alertMessagePos[0],(float) alertMessagePos[1],(int) alertMessageSize[0],(int) alertMessageSize[1],1,0x000000);
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode==Keyboard.KEY_ESCAPE&&failAlert) {
            failAlert = false;
            return;
        }
        if (isShiftDown()) {
            shiftClicked = true;
            changePoint = Math.min(point, 32);
            amtPoint = !amtPoint.equals("")?String.valueOf(changePoint):"";
        }
        else if (shiftClicked){
            shiftClicked = false;
            changePoint = Integer.parseInt(amtPoint.equals("")?"0":amtPoint);
            amtPoint = !amtPoint.equals("")?String.valueOf(changePoint):"";
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
            changeCoin();
            return;
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_0) || Keyboard.isKeyDown(Keyboard.KEY_1) || Keyboard.isKeyDown(Keyboard.KEY_2) || Keyboard.isKeyDown(Keyboard.KEY_3) || Keyboard.isKeyDown(Keyboard.KEY_4) || Keyboard.isKeyDown(Keyboard.KEY_5) || Keyboard.isKeyDown(Keyboard.KEY_6) || Keyboard.isKeyDown(Keyboard.KEY_7) || Keyboard.isKeyDown(Keyboard.KEY_8) || Keyboard.isKeyDown(Keyboard.KEY_9) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9) || Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
            if (keyCode==Keyboard.KEY_BACK||keyCode==Keyboard.KEY_DELETE) {
                if (String.valueOf(amtPoint).length()>=1) amtPoint = amtPoint.substring(0, amtPoint.length() - 1);
                else amtPoint = "";
                changePoint = Math.min(Integer.parseInt(amtPoint.equals("")?"0":amtPoint), Math.max(point,0));
                amtPoint = !amtPoint.equals("")?String.valueOf(changePoint):"";
                return;
            }
            if (amtPoint.length()+1<=String.valueOf(point).length()) amtPoint += typedChar;
            changePoint = Math.min(Integer.parseInt(amtPoint), Math.max(point,0));
            amtPoint = !amtPoint.equals("")?String.valueOf(changePoint):"";
        }
        super.keyTyped(typedChar,keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btnPos[0], btnPos[1], btnSize[0], btnSize[1])) {
            changeCoin();
            Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
        }
    }

    protected void changeCoin() {
        if (changePoint <= point) {
            Packet.sendMessage(PacketList.CHANGE_COIN.recogCode + "/" + type.type + "/" + playerJob.recogCode + "/" + (isShiftDown() ? "1" : "0") + "/"+changePoint);
        }
    }

    public boolean isShiftDown() {
        return Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);
    }
}
