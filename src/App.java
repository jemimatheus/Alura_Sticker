import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {



        //fazendo a conexão http para buscar os filmes
        String url = "https://mocki.io/v1/9a7c1ca9-29b4-4eb3-8306-1adb9d159060";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response =  client.send(request, BodyHandlers.ofString());
        String body = response.body();

        System.out.println(body);

        //Extrair somente os dados que vou utilizar
        var parser = new JsonParser();
        List<Map<String,String>> listaDeFilmes = parser.parse(body);
      
        System.out.println(listaDeFilmes.size());

        //Exibir os dados extraidos

        for (Map<String,String> filme : listaDeFilmes) {
            
            String urlImages = filme.get("image");
            String titulo = filme.get("title");
            
            InputStream inputStream = new URL(urlImages).openStream();

            String nomeArquivo = titulo + ".png";

            var geradora = new GeradorDeFigurinhas();
            geradora.cria(inputStream, nomeArquivo);

    
            System.out.println(filme.get(titulo));
            System.out.println();
        }
    }
}
 