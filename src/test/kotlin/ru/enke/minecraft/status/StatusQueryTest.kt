package ru.enke.minecraft.status

import io.netty.channel.nio.NioEventLoopGroup
import org.junit.After
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import ru.enke.minecraft.protocol.packet.data.status.ServerStatusInfo
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class StatusQueryTest {

    val eventLoopGroup = NioEventLoopGroup(1)

    @Test
    fun testQuery() {
        val lock = CountDownLatch(1)
        var result : ServerStatusInfo? = null

        StatusQuery(eventLoopGroup).query("mc.hypixel.net", 25565, object : StatusQueryCallback {
            override fun onComplete(status: ServerStatusInfo) {
                result = status
            }

            override fun onException(throwable: Throwable) {
                fail("Could not get status: " + throwable.message)
            }
        })

        lock.await(5, TimeUnit.SECONDS)
        assertNotNull(result)
        println(result)
    }

    @After
    fun tearDown() {
        eventLoopGroup.shutdownGracefully()
    }

}