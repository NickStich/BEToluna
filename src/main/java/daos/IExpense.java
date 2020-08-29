package daos;



import model.Expense;

import java.util.List;

public interface IExpense {
    void createExpense(Expense expense);

    void removeExpense(Expense expense);

    void updateExpense(Expense expense);

    Expense getById(long id);

    List<Expense> getAll();
}
