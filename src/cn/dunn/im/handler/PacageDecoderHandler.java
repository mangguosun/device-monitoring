package cn.dunn.im.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.EmptyByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.CharsetUtil;
import cn.dunn.im.util.ConvertUtil;

public class PacageDecoderHandler extends LengthFieldBasedFrameDecoder {

	public PacageDecoderHandler(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		if (buf instanceof EmptyByteBuf) {
			return null;
		}
		ByteBuf buff = (ByteBuf) super.decode(ctx, buf);
		if (buff == null) {
			return null;
		}
		byte[] body = new byte[buff.readInt()];
		buff.readBytes(body);
		String xml = new String(body, CharsetUtil.UTF_8);

		int index = xml.indexOf("$||$");

		return ConvertUtil.xml2Object(xml.substring(0, index), xml.substring(index + "$||$".length(), xml.length()));
	}

}
