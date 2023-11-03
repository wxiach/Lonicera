package cn.wxiach.server;

import cn.wxiach.route.Route;
import cn.wxiach.route.Router;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author wxiach 2023/10/8
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        String routeKey = msg.uri() + ":" + msg.method().name();
        Route route = Router.getInstance().getRoute(routeKey);
        String result = (String) route.action().invoke(route.target());

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                                                                HttpResponseStatus.OK,
                                                                Unpooled.copiedBuffer(result, CharsetUtil.UTF_8));

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html");
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

        ctx.writeAndFlush(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}