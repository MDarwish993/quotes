package quotes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Random;

public class QuoteManager {
    private static final String API_URL = "https://favqs.com/api/qotd";
    private static final String API_KEY = "5bf3b86256981df94052596440eede95";
    private List<Quote> quotes;


    public QuoteManager(String jsonFilePath) throws IOException {
        Gson gson = new Gson();
        Type quoteListType = new TypeToken<List<Quote>>() {}.getType();

        try (Reader reader = new FileReader(jsonFilePath)) {
            quotes = gson.fromJson(reader, quoteListType);
        }
    }

    public Quote getRandomQuote() {
        Random random = new Random();
        int randomIndex = random.nextInt(quotes.size());
        return quotes.get(randomIndex);
    }

    public void addQuote(Quote quote) {
        quotes.add(quote);
    }

    public  void saveQuotesToFile(String jsonFilePath) throws IOException {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(jsonFilePath)) {
            gson.toJson(quotes, writer);
        }
    }

    public  Quote fetchRandomQuoteFromApi() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Token token=" + API_KEY);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        BufferedReader quoteBufferedReader= new BufferedReader(reader);
        String quoteData=quoteBufferedReader.readLine();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Quote quote = gson.fromJson(quoteData, Quote.class);
        addQuote(quote);
        saveQuotesToFile("C:\\Users\\C-ROAD\\IdeaProjects\\quotes\\app\\src\\main\\resources\\quotesData.json");
        return quote;
    }


}
