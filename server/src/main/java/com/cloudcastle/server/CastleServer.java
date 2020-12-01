package com.cloudcastle.server;

import com.cloudcastle.server.exception.ServerInitializeError;
import com.cloudcastle.server.secure.SecureChannelInitializer;
import com.cloudcastle.server.secure.SslHandlerProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;

import java.io.IOException;
import java.security.*;

public class CastleServer {
    private static CastleServer server;
    private static SslContext sslCtx;

    private CastleServer() throws GeneralSecurityException, IOException {
        SslHandlerProvider.initSslContext();
        SslHandler sslHandler = SslHandlerProvider.getSSLHandler();
        sslCtx = SslContextBuilder.forServer(SslHandlerProvider.getKMF()).build();
    }

    public static CastleServer init() throws GeneralSecurityException, IOException {
        if(server == null) {
            server = new CastleServer();
        }
        return server;
    }

    public static void run() throws GeneralSecurityException, IOException {
        init();
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//TODO Create own ServerSocketChannel
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new SecureChannelInitializer(sslCtx));

            b.bind(8181).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }



    public static void main(String[] args) throws GeneralSecurityException, IOException {
        run();
    }
}
