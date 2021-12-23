package fr.lernejo.navy_battle;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;

class LauncherTest {
    @Test
    void faux_argument_main(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{"azert"});
        });
    }

    @Test
    void aucun_argument_main(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{""});
        });
    }

    @Test
    void pas_bon_arguments_in_main(){
        assertThrows(NumberFormatException.class, ()->{
            Launcher.main(new String[]{"a", "b"});
        });
    }

    @Test
    void un_argumenet_launch_ping() throws Exception {
        Launcher.main(new String[]{"8088"});
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8088/ping"))
            .GET()
            .build();

        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        Assertions.assertThat(response.statusCode()).as("tester un arg ").isEqualTo(200);
    }


}
