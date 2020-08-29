package queries;

import daos.ExpenseDAO;
import daos.IncomeDAO;
import model.Expense;
import model.Income;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

public class Queries {

    public static double getTotalIncomes() throws SQLException {
        double incomes = 0;
        Statement statement = null;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/toluna?serverTimezone=UTC", "root", "1205");
        try {
            statement = connection.createStatement();
            ResultSet resultsetIncomes = statement.executeQuery("SELECT SUM(value) as totalIncomes FROM toluna.income;");
            resultsetIncomes.next();
            incomes = resultsetIncomes.getDouble("totalIncomes");
            resultsetIncomes.close();
            return incomes;
        } catch (Exception e) {
            System.out.println("Error getting budget");
            e.printStackTrace();
        }
        return incomes;
    }

    public static double getTotalExpense() throws SQLException {
        double expenses = 0;
        Statement statement = null;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/toluna?serverTimezone=UTC", "root", "1205");
        try {
            statement = connection.createStatement();
            ResultSet resultsetExpense = statement.executeQuery("SELECT SUM(value) as totalExpenses FROM toluna.expense;");
            resultsetExpense.next();
            expenses = resultsetExpense.getDouble("totalExpenses");
            resultsetExpense.close();
            return expenses;
        } catch (Exception e) {
            System.out.println("Error getting budget");
            e.printStackTrace();
        }
        return expenses;

    }

    public static double getBudget() throws SQLException {
        return getTotalIncomes() - getTotalExpense();
    }

    public static String getPercentageOfExpensesFromIncomes() throws SQLException {
        double percentage = (getTotalExpense() / getTotalIncomes()) * 100;
        int decimalPlaces = 2;
        BigDecimal bd = new BigDecimal(percentage);
        bd = bd.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP);
        percentage = bd.doubleValue();
        return percentage + " %";
    }

    public static List<Income> getAllIncomes() {
        IncomeDAO incomeDAO = new IncomeDAO();
        return incomeDAO.getAll();
    }

    public static List<Expense> getAllExpense() {
        ExpenseDAO expenseDAO = new ExpenseDAO();
        return expenseDAO.getAll();
    }

}
