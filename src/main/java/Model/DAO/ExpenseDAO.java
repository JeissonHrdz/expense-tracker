package Model.DAO;
import Model.VO.ExpenseVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExpenseDAO {

    public boolean insert(ExpenseVO expense) {
        try {

            Date date = new Date();
            String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            ExpenseVO expenseVO = new ExpenseVO(1, "Prueba", 5350, "Food", str);
            Writer writer = Files.newBufferedWriter(Paths.get("src/main/java/DataBase/expense.json"));
            gson.toJson(expenseVO, writer);
            writer.close();


        } catch (IOException e) {
            throw new RuntimeException(e);

        }
        return false;
    }

    public boolean update(ExpenseVO expense) {
        return false;
    }

    public boolean delete(ExpenseVO expense) {
        return false;
    }

    public ArrayList<ExpenseVO> selectAll() {
        return null;
    }

    public ArrayList<ExpenseVO> selectByCategory(String category) {
        return null;
    }

}
