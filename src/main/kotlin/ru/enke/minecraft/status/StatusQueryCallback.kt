package ru.enke.minecraft.status

import ru.enke.minecraft.protocol.packet.data.status.ServerStatusInfo

interface StatusQueryCallback {

    fun onComplete(status: ServerStatusInfo)

    fun onException(throwable: Throwable)

}