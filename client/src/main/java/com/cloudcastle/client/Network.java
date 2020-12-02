package com.cloudcastle.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.DelegatingSslContext;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.security.cert.X509Certificate;

public class Network {
    private SocketChannel channel;
    static final String HOST = System.getProperty("localhost", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8181"));

    public Network(){
        Thread thread = new Thread(() ->{
            EventLoopGroup group = new NioEventLoopGroup(1);
            try {
//                TrustManagerFactory tmf = TrustManagerFactory.getInstance("ssl.KeyManagerFactory.algorithm");
//                tmf.init((KeyStore) null);
//                TrustManager[] trustManagers = tmf.getTrustManagers();
//                SSLContext sslContext = SSLContext.getInstance("TLS");
//                sslContext.init(null, trustManagers, null);

                final SslContext sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();

                Bootstrap b = new Bootstrap();
                b.group(group)
                        .channel(NioSocketChannel.class)
                        .handler(new SecureChannelInitializer(sslCtx));
                Channel ch = b.connect(HOST, PORT).sync().channel();


                ChannelFuture lastWriteFuture = null;
                BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                for (;;) {
                    String line = in.readLine();
                    if (line == null) {
                        break;
                    }

                    // Sends the received line to the server.
                    lastWriteFuture = ch.writeAndFlush(line + "\r\n");

                    // If user typed the 'bye' command, wait until the server closes
                    // the connection.
                    if ("bye".equals(line.toLowerCase())) {
                        ch.closeFuture().sync();
                        break;
                    }
                }

                // Wait until all messages are flushed before closing the channel.
                if (lastWriteFuture != null) {
                    lastWriteFuture.sync();
                }
            } catch (Exception e) {

            } finally {
                group.shutdownGracefully();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
