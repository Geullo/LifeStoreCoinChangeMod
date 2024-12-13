package com.geullo.coinchange;

import com.geullo.coinchange.UI.*;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class Message {
	
	private static Message instance;
	private HashMap<PacketList, Method> executePacket = new HashMap<>();
	private Minecraft mc = Minecraft.getMinecraft();
	public static Message getInstance() {
		if(instance == null) {
			instance = new Message();
		}
		return instance;
	}
	
	private Message() {
		try {
			executePacket.put(PacketList.OPEN_CHANGE_COIN_UI,getClass().getDeclaredMethod("openCoin", String.class));
			executePacket.put(PacketList.GET_COIN_CHANGE_ITEM,getClass().getDeclaredMethod("getCoinChangeItem", String.class));
			executePacket.put(PacketList.CHANGE_COIN,getClass().getDeclaredMethod("changeCoin", String.class));
			executePacket.put(PacketList.NOTICE,getClass().getDeclaredMethod("notice", String.class));
			executePacket.put(PacketList.OPEN_TRAIN_UI,getClass().getDeclaredMethod("openTrainUI", String.class));
			executePacket.put(PacketList.GET_FAKE_NAME,getClass().getDeclaredMethod("getFakeName", String.class));
			executePacket.put(PacketList.SHOW_FAVOR,getClass().getDeclaredMethod("showFavor", String.class));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void showFavor(String arg1) {
		Minecraft.getMinecraft().displayGuiScreen(new FavorCharmUI());
	}
	private void openCoin(String arg1) {
		String[] split = arg1.split("/");
		Minecraft.getMinecraft().displayGuiScreen(new CoinChangeUI(CoinType.convert(split[1])));
	}
	private void getCoinChangeItem(String arg1) {
		if (mc.currentScreen instanceof CoinChangeUI) {
			String[] split = arg1.split("/");
			String[] job = split[3].split("=");
			ItemStack itemStack = new ItemStack(Item.getItemById(Integer.parseInt(split[2])));
			CoinChangeUI coinChangeUI = (CoinChangeUI) mc.currentScreen;
			coinChangeUI.coin = itemStack;
			coinChangeUI.point = Integer.parseInt(job[1]);
			coinChangeUI.playerJob = JobType.codeToJobType(job[0].replace(" ",""));
		}
	}
	private void changeCoin(String arg1) {
		if (mc.currentScreen instanceof CoinChangeUI) {
			CoinChangeUI ui = (CoinChangeUI) mc.currentScreen;
			String[] split = arg1.split("/"),amt = split[2].split("=");
			ui.point = Integer.parseInt(amt[1]);
		}
	}
	private void notice(String arg1) {
		if (arg1.substring(2).contains("--clean")) MinecraftForge.EVENT_BUS.register(null);
		MinecraftForge.EVENT_BUS.register(new NoticeUI(arg1.substring(2)));
	}
	private void openTrainUI(String arg1) {
		String[] b = arg1.substring(3).split("/");
		StationUI sui = new StationUI();
		Minecraft.getMinecraft().displayGuiScreen(sui);
		for (int i=0;i<b.length;i+=2) {
			String[] c = b[i].split("=");
			if (sui.stationMap.get(c[0])!=null) {
				sui.stationMap.get(c[0]).setLocked(c[1].equals("false"));
			}
			if (i+1<b.length) {
				c = b[i+1].split("=");
				if (sui.stationMap.get(c[0])!=null) {
					sui.stationMap.get(c[0]).setLocked(c[1].equals("false"));
				}
			}
		}
		initStations(sui);

	}
	private void initStations(StationUI sui) {
		sui.stationMap.get("햇무리").setLocked(sui.stationMap.get("햇무리").isLocked());
		sui.stationMap.get("포포").setLocked(sui.stationMap.get("포포").isLocked());
		sui.stationMap.get("할데스").setLocked(sui.stationMap.get("할데스").isLocked());
		sui.stationMap.get("에클레").setLocked(sui.stationMap.get("에클레").isLocked());
		sui.stationMap.get("피오사").setLocked(sui.stationMap.get("피오사").isLocked());
		sui.stationMap.get("베렌티").setLocked(sui.stationMap.get("베렌티").isLocked());
	}
	private void getFakeName(String arg1) {
		if (mc.currentScreen instanceof FavorCouponUI) {
			((FavorCouponUI) mc.currentScreen).user = arg1.split("/")[1];
		} else if (mc.currentScreen instanceof FavorCharmUI) {
			((FavorCharmUI) mc.currentScreen).user = arg1.split("/")[1];
		}
	}
	/*
	* {Stat=name:싼손,point:2},{Stat=name:축지법,point:3},{Stat=name:밤의 등불,point:1},{Stat=name:추가 인벤,point:0}
	* {name:0,point:2},{name:1,point:3},{name:2,point:1},{name:3,point:0}
	* */
	public void handle(Packet message) throws InvocationTargetException, IllegalAccessException {
		executePacket.get(PacketList.convert(message.data.substring(0,2))).invoke(this,message.data);
	}
}
