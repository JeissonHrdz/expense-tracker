package org.example;


import Model.DAO.ExpenseDAO;
import Model.VO.ExpenseVO;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
       /* ExpenseDAO dao = new ExpenseDAO();
        Date date = new Date();
        String str = new SimpleDateFormat("yyyy-MM-dd").format(date);
        dao.insert(new ExpenseVO(3,"prueba 3",5230,"rent",str));*/

        ExpenseDAO expenseDAO = new ExpenseDAO();
     /*   ExpenseVO expense = expenseDAO.getExpenseById(8);
        if(expense != null) {
            expense.setAmount(expense.getAmount() + expense.getAmount());
            expense.setDescription("Expense Description");
            expense.setCategory("Expense Category");
            expenseDAO.update(expense);
        } else {
            System.out.println("Expense not found");
        }*/

        expenseDAO.delete(5);


    }
}