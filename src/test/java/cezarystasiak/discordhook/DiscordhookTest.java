package cezarystasiak.discordhook;

import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;

public class DiscordhookTest {

    @Test
    public void should_create_object() throws IOException {
        String url = "https://discord.com/api/webhooks/742736718960001067" +
                "/A1SUc1fZv1CnCTWr6bkH8pBuPjFHa93SBPIky6RIspg7ZXQyMjVviOlzjTTdEEA8EBeN";

        new Discordhook(url).toss("tralala");
    }

}
