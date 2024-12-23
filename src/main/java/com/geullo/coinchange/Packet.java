package com.geullo.coinchange;

import com.geullo.coinchange.proxy.ClientProxy;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

public class Packet implements IMessage {
	
	public String data;
	
	public Packet() {

	}
	
	public Packet(String data) {
		this.data = data;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int len = buf.readInt();
		data = buf.toString(buf.readerIndex(), len, StandardCharsets.UTF_8);
		buf.readerIndex(buf.readerIndex() + len);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
	}
	
	public static class Handle implements IMessageHandler<Packet, IMessage> {
		@Override
		public IMessage onMessage(Packet message, MessageContext ctx) {
			try {
				Message.getInstance().handle(message);
			} catch (InvocationTargetException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			return null;
		}
	}
	public static void sendMessage(String msg){
		StringBuilder builder = new StringBuilder(msg);
		ClientProxy.NETWORK.sendToServer(new Packet(builder.toString()));
	}
}
