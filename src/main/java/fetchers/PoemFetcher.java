package fetchers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.PoemDTO;
import utils.HttpUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class PoemFetcher {

    private static String poemURL = "https://www.poemist.com/api/v1/randompoems";

    public static PoemDTO fetchPoem (Gson gson) throws IOException {


        String poem = HttpUtils.fetchData(poemURL);

        Type listType = new TypeToken<List<PoemDTO>>() {}.getType();

        List<PoemDTO> poemDTO = gson.fromJson(poem, listType);


        return poemDTO.get(0);

    }

}
