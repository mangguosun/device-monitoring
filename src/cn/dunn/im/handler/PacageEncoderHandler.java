package cn.dunn.im.handler;

import cn.dunn.im.util.ConvertUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.CharsetUtil;

public class PacageEncoderHandler extends MessageToByteEncoder<Object> {

	protected void encode(ChannelHandlerContext ctx, Object obj, ByteBuf buf) throws Exception {
		String className = obj.getClass().getName();

		String body = ConvertUtil.obj2Xml(obj);
		String xml = className + "$||$" + body;
		byte[] bytes = xml.getBytes(CharsetUtil.UTF_8);
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);
		buf.setInt(0, buf.readableBytes() - 4);
	}

}
