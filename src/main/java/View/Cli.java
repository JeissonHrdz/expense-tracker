package View;

import Model.DAO.CategoryDAO;
import Model.DAO.ExpenseDAO;
import Model.VO.CategoryVO;
import Model.VO.ExpenseVO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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


        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter new expense data ----------- >");
        System.out.print("\u001B[36m $\u001B[0m");
        System.out.print("\u001B[33m expense tracker \u001B[0m");
        String expenseData = scanner.nextLine();
        String[] data = expenseData.split(" ");

        switch (data[0]) {
            case "add": {
                try {
                    if (data.length < 5) {
                        System.out.println("Invalid command: missing arguments");
                        return;
                    }

                    if (!DESCRIPTION_FLAG.equals(data[1]) || !AMOUNT_FLAG.equals(data[3]) || !CATEGORY_FLAG.equals(data[5])) {
                        System.out.println("Invalid command: incorrect flags");
                        return;
                    }

                    String  expenseDescription;
                    expenseDescription = data[2];
                    if (!expenseDescription.startsWith("\"") || !expenseDescription.endsWith("\"")) {
                        System.out.println("Invalid expense description: must be enclosed in quotes");
                        return;
                    }

                    expenseDescription = expenseDescription.replace("\"", "");

                    double expenseAmount;
                    try {
                        expenseAmount = Double.parseDouble(data[4].trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid amount entered: must be a valid number");
                        return;
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
                            dao.insert(new ExpenseVO(3, finalExpenseDescription, expenseAmount, categoryId, creationAt));
                        });

                        if (!categoryOptional.isPresent()) {
                            System.out.println("Category not found");
                        }


                    } catch (NumberFormatException e) {
                        System.out.println("Invalid id category entered: must be a valid number");
                        return;
                    }



                } catch (Exception e) {
                    System.out.println("An unexpected error occurred: " + e.getMessage());
                }

                break;
            }
            case "list": {
                break;
            }
            case "summary": {
                break;
            }
            case "delete": {
                break;
            }
            case "update": {
                break;
            }
            default: {
                break;
            }
        }

    }


}
