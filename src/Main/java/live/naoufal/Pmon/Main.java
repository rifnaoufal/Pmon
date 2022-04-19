package live.naoufal.Pmon;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    static DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .build();
    static DockerHttpClient dockerHttpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(dockerClientConfig.getDockerHost())
            .sslConfig(dockerClientConfig.getSSLConfig())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(5))
            .responseTimeout(Duration.ofSeconds(10))
            .build();
    static DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);

    //arraylist
    static List<String> containerNames = new ArrayList<>();


    public static void main(String[] args) {
        containerNames.add("exciting_shaw");
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");


        builder.setWait(true);
        WebhookClient webhookClient = WebhookClient.withUrl("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        for (Container container : dockerClient.listContainersCmd().exec()) {
            webhookClient.send(String.join(", ", container.getNames()));
        }

        for (Container container : Main.dockerClient.listContainersCmd().exec()) {
                if ("running".equals(container.getState())) {
                    dockerClient.inspectContainerCmd("exciting_shaw").exec();
                    continue;
                }
        }

        Runnable helloRunnable = new Runnable() {
            @Override
            public void run() {
                webhookClient.send("**Message**");

                WebhookEmbed embed = new WebhookEmbedBuilder()
                        .setColor(65280)
                        .setDescription("healthy :white_check_mark:")
                        .setImageUrl("https://media.istockphoto.com/vectors/green-check-mark-icon-green-tick-symbol-round-checkmark-sign-vector-vector-id1159270056?k=20&m=1159270056&s=170667a&w=0&h=ewlZtL_NAF5L4dFRHxWLmNpZtnWyvxtDQ6BEPVrvlzw=")
                        .build();
                webhookClient.send(embed)
                        .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));
            }
        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 30, TimeUnit.SECONDS);

    }
}
//
//        webhookClient.send("**Message**");
//
//                WebhookEmbed embed = new WebhookEmbedBuilder()
//                .setColor(16711680)
//                .setDescription("unhealthy :x:")
//                .setImageUrl("https://media.istockphoto.com/vectors/design-of-red-wrong-mark-grunge-letter-xred-cross-sign-hand-drawn-vector-id1214857021?k=20&m=1214857021&s=612x612&w=0&h=vMllYNYlBX5rHw-5r0MS0gROSXokOcbQmGtvhVOWVEI=")
//                .build();
//                webhookClient.send(embed)
//                .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));
