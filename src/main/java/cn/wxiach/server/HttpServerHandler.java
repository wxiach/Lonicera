package cn.wxiach.server;

import cn.wxiach.annotation.Get;
import cn.wxiach.http.MediaType;
import cn.wxiach.route.Route;
import cn.wxiach.route.Router;
import cn.wxiach.util.RouteUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.CompletableFuture;

/**
 * @author wxiach 2023/10/8
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        CompletableFuture<FullHttpRequest> future = CompletableFuture.completedFuture(msg);
        future.thenApplyAsync(this::executeRequest)
                .exceptionally(this::handleException)
                .thenAcceptAsync(ctx::writeAndFlush);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    private FullHttpResponse executeRequest(FullHttpRequest request) {
        String routeKey = RouteUtils.generateRouteKey(request.uri(), request.method().name());
        Route route = Router.getInstance().getRoute(routeKey);

        Object result;
        try {
            result = route.action().invoke(route.target());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

        ByteBuf byteBuf = Unpooled.copiedBuffer(result.toString(), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);

        String[] produces = route.action().getAnnotation(Get.class).produces();
        String contentType = produces.length == 0 ? MediaType.TEXT_PLAIN : produces[0];

        response.headers().set(HttpHeaderNames.CONTENT_TYPE, contentType);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }

    private FullHttpResponse handleException(Throwable throwable) {
        ByteBuf byteBuf = Unpooled.copiedBuffer(throwable.getMessage(), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                                                                HttpResponseStatus.INTERNAL_SERVER_ERROR,
                                                                byteBuf);
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, MediaType.TEXT_PLAIN);
        response.headers().set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        return response;
    }
}