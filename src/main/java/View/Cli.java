package View;

import Model.DAO.CategoryDAO;
import Model.DAO.ExpenseDAO;
import Model.VO.CategoryVO;
import Model.VO.ExpenseVO;

import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cli {

    private static final String DESCRIPTION_FLAG = "--description";
    private static final String AMOUNT_FLAG = "--amount";
    private static final String ID_FLAG = "--id";
    private static final String MONTH_FLAG = "--month";
    private static final String UPDATE_FLAG = "--update";
    private static final String CATEGORY_FLAG = "--category";
    ExpenseDAO dao = new ExpenseDAO();
    CategoryDAO cdao = new CategoryDAO();


    public void menu() {

        String[] data;
        do {

            Scanner scanner = new Scanner(System.in);
            System.out.print("\u001B[36m $\u001B[0m");
            System.out.print("\u001B[33m expense tracker \u001B[0m");
            String expenseData = scanner.nextLine();

            String descriptionFormater = "";
            Pattern pattern = Pattern.compile("\"(\\\\.|[^\"\\\\])*\"");
            Matcher matcher = pattern.matcher(expenseData);

            while (matcher.find()) {
                descriptionFormater = matcher.group();
            }
            String dataFormated = expenseData.replaceAll(descriptionFormater, descriptionFormater.replace(" ", ""));

            data = dataFormated.split(" ");

            switch (data[0]) {
                case "add": {
                    try {
                        if (data.length < 6) {
                            System.out.println("Invalid command: missing arguments");
                            break;
                        }

                        if (!DESCRIPTION_FLAG.equals(data[1]) || !AMOUNT_FLAG.equals(data[3]) || !CATEGORY_FLAG.equals(data[5])) {
                            System.out.println("Invalid command: incorrect flags");
                            break;
                        }

                        String expenseDescription;
                        expenseDescription = descriptionFormater;
                        if (!expenseDescription.startsWith("\"") || !expenseDescription.endsWith("\"")) {
                            System.out.println("Invalid expense description: must be enclosed in quotes");
                            break;
                        }

                        expenseDescription = expenseDescription.replace("\"", "");

                        double expenseAmount;
                        try {
                            expenseAmount = Double.parseDouble(data[4].trim());
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid amount entered: must be a valid number");
                            break;
                        }

                        String creationAt = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                        List<CategoryVO> listCategory = cdao.listAll();


                        int categoryId;
                        try {
                            categoryId = Integer.parseInt(data[6]);
                            Optional<CategoryVO> categoryOptional = listCategory.stream()
                                    .filter(category -> category.getId() == categoryId)
                                    .findFirst();

                            String finalExpenseDescription = expenseDescription;
                            categoryOptional.ifPresent(category -> {
                                try {
                                    if (dao.insert(new ExpenseVO(dao.generateId(),
                                            finalExpenseDescription, expenseAmount, categoryId, creationAt))) {
                                        System.out.println("Expense added successfully");
                                    }

                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                }
                            });

                            if (!categoryOptional.isPresent()) {
                                System.out.println("Category not found");
                            }


                        } catch (NumberFormatException e) {
                            System.out.println("Invalid id category entered: must be a valid number");
                            break;
                        }

                    } catch (Exception e) {
                        System.out.println("An unexpected error occurred: " + e.getMessage());
                    }

                    break;
                }
                case "list": {

                    try {

                        String headerFormat = "%-5s %-12s %-20s %-20s %-10s%n";
                        String rowFormat = "%-5d %-12s %-20s %-20s %-10.2s%n";

                        if (data.length < 2) {

                            System.out.printf(headerFormat, "ID", "Date", "Description", "Category", "Amount");
                            System.out.println("--------------------------------------------------");

                            List<CategoryVO> listCategory = cdao.listAll();
                            List<ExpenseVO> listExpenses = dao.selectAll();

                            for (CategoryVO category : listCategory) {
                                for (ExpenseVO expense : listExpenses) {
                                    if (expense.getCategory() == category.getId()) {
                                        System.out.printf(rowFormat,
                                                expense.getId(),
                                                expense.getDate(),
                                                expense.getDescription(),
                                                category.getName(),
                                                "$" + expense.getAmount());
                                    }
                                    ;
                                }
                            }
                            break;
                        }

                        if (!CATEGORY_FLAG.equals(data[1])) {
                            System.out.println("Invalid command argument");
                            break;
                        }

                        List<CategoryVO> listCategory = cdao.listAll();
                        List<ExpenseVO> listExpenses = dao.selectAll();
                        int categoryId = Integer.parseInt(data[2]);
                        Optional<CategoryVO> categoryOptional = listCategory.stream()
                                .filter(category -> category.getId() == categoryId)
                                .findFirst();

                        if (categoryOptional.isEmpty()) {
                            System.out.println("Category not found");
                            break;
                        }

                        System.out.printf(headerFormat, "ID", "Date", "Description", "Category", "Amount");
                        System.out.println("-------------------------------------------------------------------------");

                        for (ExpenseVO expense : listExpenses) {
                            for (CategoryVO category : listCategory) {
                                if (expense.getCategory() == category.getId() && category.getId() == categoryId) {
                                    System.out.printf(rowFormat,
                                            expense.getId(),
                                            expense.getDate(),
                                            expense.getDescription(),
                                            category.getName(),
                                            "$" + expense.getAmount());
                                }
                            }
                        }

                    } catch (FileNotFoundException e) {
                        System.out.println("Bd not found");
                    }
                    break;
                }
                case "summary": {

                    try {
                        if (data.length < 2) {
                            System.out.println("Total expenses: $" + dao.summary());
                            break;
                        }

                        if (!data[1].equals(MONTH_FLAG)) {
                            System.out.println("Invalid command");
                            break;
                        }

                        if (Integer.parseInt(data[2]) > 12) {
                            System.out.println("Month invalid");
                            break;
                        }
                        System.out.println("Total expenses for this month: $" + dao.summaryByMonth(Integer.parseInt(data[2])));

                    } catch (NumberFormatException | ParseException e) {
                        System.out.println("Month number invalid");
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                    break;
                }
                case "delete": {

                    if (!ID_FLAG.equals(data[1])) {
                        System.out.println("Invalid command: incorrect flags");
                        break;
                    }

                    try {
                        if (dao.delete(Integer.parseInt(data[2]))) {
                            System.out.println("Expense deleted successfully");
                        } else {
                            System.out.println("Expense not found");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid id entered: must be a valid number");
                    }

                    break;
                }
                case "update": {

                    if (data.length < 5) {
                        System.out.println("Invalid command: missing arguments");
                        break;
                    }

                    if (!ID_FLAG.equals(data[1]) || !AMOUNT_FLAG.equals(data[3])) {
                        System.out.println("Invalid command: incorrect flags");
                        break;
                    }

                    List<ExpenseVO> listExpenses = dao.selectAll();
                    for (ExpenseVO expense : listExpenses) {
                        if (expense.getId() == Integer.parseInt(data[2])) {
                            expense.setAmount(Double.parseDouble(data[4]));
                            dao.update(expense);
                            System.out.println("Expense updated successfully");
                        }
                    }


                    break;
                }

                case "category": {
                    try {
                        List<CategoryVO> listCategory = cdao.listAll();
                        listCategory.forEach(category -> {
                            System.out.println("\u001B[35m  Id:\u001B[0m " + category.getId() + " \u001B[33m ->\u001B[0m " + category.getName());
                        });
                    } catch (FileNotFoundException _) {
                    }
                    break;
                }
                case "exit":{
                    System.exit(0);
                }
                default: {
                    System.out.println("\u001B[41m Invalid command: incorrect option\u001B[0m");
                    break;
                }
            }

        } while (!data[0].equals("exit"));

    }


}
