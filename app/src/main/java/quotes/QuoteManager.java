package quotes;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

public class QuoteManager {
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
}
