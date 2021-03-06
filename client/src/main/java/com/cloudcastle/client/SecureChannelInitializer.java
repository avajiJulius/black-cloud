package com.cloudcastle.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

public class SecureChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public SecureChannelInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    public void initChannel(SocketChannel socketChannel) throws Exception {

        socketChannel.pipeline().addLast(
                sslCtx.newHandler(socketChannel.alloc(), Network.HOST, Network.PORT),
                new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()),
                new StringDecoder(),
                new StringEncoder(),
                new SecureChannelHandler());

    }
}
