package com.kapp.io.net.netty.server;

import com.kapp.io.net.netty.protocol.code.RpcDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfiguration {
    @Bean
    public ServerBootstrap serverBootstrap() {

        ServerBootstrap serverBootstrap = new ServerBootstrap();

        serverBootstrap.group(new DefaultEventLoopGroup(), new DefaultEventLoopGroup()).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                // 添加逻辑处理器链，比如编码器、解码器、业务处理器等
                ch.pipeline().addLast(new RpcDecoder(), new RpcDecoder());
            }
        });
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.option(ChannelOption.SO_REUSEADDR, true);
        serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        return serverBootstrap;
    }
}
