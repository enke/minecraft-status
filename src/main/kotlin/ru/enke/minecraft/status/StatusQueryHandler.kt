package ru.enke.minecraft.status

import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import ru.enke.minecraft.protocol.Protocol
import ru.enke.minecraft.protocol.ProtocolState
import ru.enke.minecraft.protocol.packet.PacketMessage
import ru.enke.minecraft.protocol.packet.client.handshake.Handshake
import ru.enke.minecraft.protocol.packet.client.status.StatusRequest
import ru.enke.minecraft.protocol.packet.server.status.StatusResponse

class StatusQueryHandler(private val ip: String, private val port: Int,
                         private val callback: StatusQueryCallback) : SimpleChannelInboundHandler<PacketMessage>() {

    override fun channelActive(ctx: ChannelHandlerContext) {
        val channel = ctx.channel()

        channel.writeAndFlush(Handshake(Protocol.VERSION, ip, port, ProtocolState.STATUS))
        channel.writeAndFlush(StatusRequest())
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: PacketMessage) {
        callback.onComplete((msg as StatusResponse).statusInfo)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, throwable: Throwable) {
        callback.onException(throwable)
    }

}