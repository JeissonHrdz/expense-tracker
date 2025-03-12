package Model.DAO;

import Model.VO.ExpenseVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ExpenseDAO {

    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    Path path = Paths.get("src/main/java/DataBase/expense.json");

    public boolean insert(ExpenseVO expense) {
        boolean result = false;
        String json = "";
        try {
            List<ExpenseVO> expenses = processJSON();
            if (!Objects.isNull(expenses)) {
                expenses.add(expense);
                json = gson.toJson(expenses);
            } else {
                json = "[" + gson.toJson(expense) + "]";
            }
            Files.write(path, json.getBytes());
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean update(ExpenseVO expense) {
        boolean result = false;

        try {
            List<ExpenseVO> expenses = processJSON();
            for (ExpenseVO expenseVO : expenses) {
                if (expenseVO.getId() == expense.getId()) {
                    expenseVO.setCategory(expense.getCategory());
                    expenseVO.setDescription(expense.getDescription());
                    expenseVO.setDate(expense.getDate());
                    expenseVO.setAmount(expense.getAmount());

                    String json = gson.toJson(expenses);
                    Files.write(path, json.getBytes());
                    result = true;
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    public ExpenseVO getExpenseById(int id) {
        ExpenseVO expense = null;
        try {
            List<ExpenseVO> expenses = processJSON();
            for (ExpenseVO expenseObj : expenses) {
                if (expenseObj.getId() == id) {
                    expense = expenseObj;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return expense;
    }

    public boolean delete(int id) {
        boolean result = false;
        try {
            List<ExpenseVO> expenses = processJSON();
            if (expenses.removeIf(expenseVO -> expenseVO.getId() == id)){
                String json = gson.toJson(expenses);
                Files.write(path, json.getBytes());
                result = true;
            }

        } catch (IOException e) {
            System.out.println("Id argument invalid");
        }
        return result;
    }

    public List<ExpenseVO> selectAll() {
        List<ExpenseVO> expenseVOS = null;
        try {
            expenseVOS = processJSON();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return expenseVOS;
    }

    public double summary() {
        List<ExpenseVO> expenseVOS = null;
        double sum = 0;
        try {
            expenseVOS = processJSON();
            for (ExpenseVO expenseVO : expenseVOS) {
                sum = sum + expenseVO.getAmount();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }

    public double summaryByMonth(int month) throws FileNotFoundException, ParseException {
        List<ExpenseVO> expenseVOS = null;
        double sum = 0;
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        expenseVOS = processJSON();
        for (ExpenseVO expenseVO : expenseVOS) {
            date = formatter.parse(expenseVO.getDate());
            if((date.getMonth()+1) == month) {
                sum = sum + expenseVO.getAmount();
            }
        }
        return sum;
    }

    public int generateId() throws FileNotFoundException {
        int id = 1;
        List<ExpenseVO> expenseVOS = processJSON();
        for (ExpenseVO expenseVO : expenseVOS) {
                id = expenseVO.getId();
        }
    return (id+1);
    }


    public ArrayList<ExpenseVO> selectByCategory(String category) {

        return null;
    }

    public List<ExpenseVO> processJSON() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
        Type expenseList = new TypeToken<List<ExpenseVO>>() {
        }.getType();
        return gson.fromJson(reader, expenseList);
    }

}
