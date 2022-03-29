package live.naoufal.Pmon;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;

import java.time.Duration;

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

    public static void main(String[] args) {
        Main.dockerClient.listContainersCmd().exec().forEach(container -> {
            System.out.println(container.getId());
        });

        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        builder.setWait(true);
        WebhookClient client = WebhookClient.withUrl("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");


//        this.client.listContainersCmd().exec()
        client.send("**Message**");

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF00EE)
                .setDescription("https://gitlab.rijnijssel.nl/00313992/javaopdracht")
                .build();
        client.send(embed)
                .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));
        {
        }
    }
}