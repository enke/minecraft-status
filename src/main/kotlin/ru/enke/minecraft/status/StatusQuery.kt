package ru.enke.minecraft.status

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelFutureListener
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import ru.enke.minecraft.protocol.ProtocolSide
import ru.enke.minecraft.protocol.codec.LengthCodec
import ru.enke.minecraft.protocol.codec.PacketCodec

class StatusQuery(val eventLoopGroup: EventLoopGroup) {

    @JvmOverloads
    fun query(ip: String, port: Int = 25565, callback: StatusQueryCallback) {
        Bootstrap().group(eventLoopGroup)
                .channel(NioSocketChannel::class.java)
                .handler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(channel: Channel) {
                        val pipeline = channel.pipeline()

                        pipeline.addLast(LengthCodec())
                        pipeline.addLast(PacketCodec(ProtocolSide.CLIENT))
                        pipeline.addLast(StatusQueryHandler(ip, port, callback))
                    }
                }).connect(ip, port).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE)
    }

}