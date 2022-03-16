package live.naoufal.Pmon;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;

public class Main {
    public static void main (String[] args) {
        WebhookClientBuilder builder = new WebhookClientBuilder("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        builder.setWait(true);
        WebhookClient client = WebhookClient.withUrl("https://discord.com/api/webhooks/951127407786614834/gUx-8ElAjs4JhEMmiaAaIphJSlDyh1f_t4BEGp5W1srJoyFME1p1xAXrQ0nUomnp-mej");

        client.send("**Message**");

        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xFF00EE)
                .setDescription("Hello World")
                .build();
        client.send(embed)
                .thenAccept((message) -> System.out.printf("Message with embed has been sent [%s]%n", message.getId()));{
        }
    }
}