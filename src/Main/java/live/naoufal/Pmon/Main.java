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

    static List<String> containerNames = new ArrayList<>();

    public static void main(String[] args) {
        containerNames.add("amazing_roentgen");
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        builder.setWait(true);
        WebhookClient webhookClient = WebhookClient.withUrl("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        for (Container container : dockerClient.listContainersCmd().exec()) {
            webhookClient.send(String.join(", ", container.getNames()));
        }

        for (Container container : Main.dockerClient.listContainersCmd().exec()) {
                if ("running".equals(container.getState())) {
                    continue;

                }
        }
        webhookClient.send("**Message**");

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(65280)
                .setDescription("running :white_check_mark:")
                .build();
        webhookClient.send(embed)
                .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));
        {
        }
    }
}