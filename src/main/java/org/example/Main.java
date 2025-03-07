package org.example;



import Model.DAO.ExpenseDAO;
import Model.VO.ExpenseVO;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.cli.*;

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

        //  expenseDAO.delete(5);

       // List<ExpenseVO> expenseVOS = expenseDAO.selectAll();

     /*   try {
            System.out.println(expenseDAO.summaryByMonth(3));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }*/

        Options options = new Options();
        options.addOption("h", "help", false, "Muestra la ayuda");
        options.addOption("v", "version", false, "Muestra la versión");
        options.addOption("f", "file", true, "Especifica el archivo de entrada");

        // Crear un Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese los argumentos (ejemplo: -f archivo.txt):");
        String userInput = scanner.nextLine();

        // Convertir la entrada del usuario en un array de String para Apache Commons CLI
        String[] inputArgs = userInput.split(" ");

        // Parsear los argumentos usando Apache Commons CLI
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, inputArgs);

            // Procesar los argumentos
            if (cmd.hasOption("h")) {
                System.out.println("Ayuda: Este es un programa de ejemplo.");
            }
            if (cmd.hasOption("v")) {
                System.out.println("Versión: 1.0");
            }
            if (cmd.hasOption("f")) {
                System.out.println("Archivo de entrada: " + cmd.getOptionValue("f"));
            }
        } catch (org.apache.commons.cli.ParseException e) {
            throw new RuntimeException(e);
        }

        // Cerrar el Scanner
        scanner.close();




    }
}