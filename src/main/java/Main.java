import daos.ExpenseDAO;
import daos.IncomeDAO;
import model.Expense;
import model.Income;
import queries.Queries;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        IncomeDAO incomeDAO = new IncomeDAO();
        incomeDAO.createIncome(new Income("stonks",5000));

        ExpenseDAO expenseDAO = new ExpenseDAO();
        expenseDAO.createExpense(new Expense("curent",300));


    }
}
