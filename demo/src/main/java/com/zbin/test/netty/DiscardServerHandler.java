package com.zbin.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;


import java.net.SocketAddress;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		Channel channel = ctx.channel();
//		SocketAddress socketAddress = channel.remoteAddress();
//		System.out.println(socketAddress+"连接了你");

//		ctx.write(msg); // (1)
//		ctx.flush(); // (2)
//		String str = ((ByteBuf)msg).toString(CharsetUtil.UTF_8);
//		System.out.println(str);
//		((ByteBuf) msg).release();

		ByteBuf in = (ByteBuf) msg;
		try {
			while (in.isReadable()) {
				Channel channel = ctx.channel();
				SocketAddress socketAddress = channel.remoteAddress();
				System.out.println(socketAddress+"连接了你");
				System.out.print((char) in.readByte());
				System.out.flush();
			}
		} finally {
			ReferenceCountUtil.release(msg);
		}
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		System.out.println(ctx.channel().remoteAddress()+"强迫关闭了连接");
		System.out.println("跑完了......肥噶!");
		ctx.close();

	}


}
