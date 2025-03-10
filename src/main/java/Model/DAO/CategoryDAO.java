package Model.DAO;

import Model.VO.CategoryVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CategoryDAO {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Path path = Paths.get("src/main/java/DataBase/category.json");

    public List<CategoryVO> listAll() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        Type expenseList = new TypeToken<List<CategoryVO>>() {
        }.getType();
        return gson.fromJson(reader, expenseList);
    }
}
