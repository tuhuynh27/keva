package dev.keva.core.command;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import redis.clients.jedis.JedisPubSub;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PubSubCommandTest extends BaseCommandTest {

    @Test
    @Timeout(30)
    void pubsub() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> subscriber.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                future.complete(message);
            }

            @Override
            public void onSubscribe(String channel, int subscribedChannels) {
                jedis.publish("test", "Test message");
            }
        }, "test")).start();
        val message = future.get();
        assertEquals("Test message", message);
    }

}
