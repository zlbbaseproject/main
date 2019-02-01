package com.zbin.test.netty;
import io.netty.buffer.ByteBuf;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

		ctx.write(msg); // (1)
		ctx.flush(); // (2)
//		String str = ((ByteBuf)msg).toString(CharsetUtil.UTF_8);
//		System.out.println(str);
//		((ByteBuf) msg).release();
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
        ctx.close();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
