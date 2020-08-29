package app;

import daos.ExpenseDAO;
import daos.IncomeDAO;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Expense;
import model.Income;
import queries.Queries;

import java.sql.SQLException;
import java.util.List;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // window dimensions
        primaryStage.setWidth(1000);
        primaryStage.setHeight(1000);

        //window title
        primaryStage.setTitle("Toluna Budget App");

        //image background with text
        Image image = new Image("file:background.png");
        ImageView mv = new ImageView(image);

        Text text = new Text("Available Budget in June 2020:" + "\n" + "\n" + "\n" + "\n");
        text.setFont(Font.font("Verdana", 15));
        text.setFill(Color.WHITE);

        final Text budget = new Text();
        budget.setText("\n" + Queries.getBudget() + "\n");
        budget.setFont(Font.font("Verdana", 55));
        budget.setFill(Color.WHITE);
        budget.setTextAlignment(TextAlignment.JUSTIFY);

        final Text income = new Text();
        income.setText("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "Income             " + " + " + Queries.getTotalIncomes() + "\n" + "\n" + "\n" + "\n");
        income.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        income.setFill(Color.BLUE);

        final Text expense = new Text();
        expense.setText("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "Expense             " + " - " + Queries.getTotalExpense() + " [" + Queries.getPercentageOfExpensesFromIncomes() + "]");
        expense.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        expense.setFill(Color.RED);

        final StackPane pane = new StackPane();
        pane.getChildren().addAll(mv, text, budget, income, expense);

        //horizontal box with type, insert areas and action button
        HBox hBox = new HBox();

        hBox.setPadding(new Insets(20, 0, 0, 0));
        hBox.setAlignment(Pos.TOP_CENTER);

        final ComboBox<String> type = new ComboBox<String>();
        type.getItems().addAll("-", "Income", "Expense");
        type.getSelectionModel().select(0);
        type.setMaxWidth(140);
        type.setPadding(new Insets(0, 10, 0, 0));

        final TextField insert = new TextField();
        insert.setMaxWidth(200);

        final TextField value = new TextField();
        value.setMaxWidth(60);

        Button button = new Button();
        button.setStyle(
                "-fx-background-radius: 25em; " +
                        "-fx-min-width: 30px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 30px; " +
                        "-fx-max-height: 30px;" + "-fx-color-label-visible: green"
        );
        button.setText("✓");

        //bottom box with list display
        HBox names = new HBox();
        Text nameCategory = new Text("Income                   Expense");
        names.setPadding(new Insets(50, 0, 0, 0));
        names.setAlignment(Pos.TOP_CENTER);
        nameCategory.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        names.getChildren().add(nameCategory);

        final HBox bottomHBox = new HBox();

        final ListView<String> incomes = new ListView<String>();

        final ListView<String> expenses = new ListView<String>();

        bottomHBox.setPadding(new Insets(20, 0, 0, 0));
        bottomHBox.setAlignment(Pos.TOP_CENTER);

        // action button with refresh event handle
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                incomes.getItems().clear();
                expenses.getItems().clear();

                if (type.getSelectionModel().getSelectedItem().equals("Income")) {
                    IncomeDAO incomeDAO = new IncomeDAO();
                    incomeDAO.createIncome(new Income(insert.getText(), Double.parseDouble(value.getText())));
                }
                if (type.getSelectionModel().getSelectedItem().equals("Expense")) {
                    ExpenseDAO expenseDAO = new ExpenseDAO();
                    expenseDAO.createExpense(new Expense(insert.getText(), Double.parseDouble(value.getText())));
                }
                System.out.println(type.getSelectionModel().getSelectedItem());
                try {
                    budget.setText("\n" + Queries.getBudget() + "\n");
                    income.setText("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "Income             " + " + " + Queries.getTotalIncomes() + "\n" + "\n" + "\n" + "\n");
                    expense.setText("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "Expense             " + " - " + Queries.getTotalExpense() + " [" + Queries.getPercentageOfExpensesFromIncomes() + "]");

                    final List<Income> allIncomes = Queries.getAllIncomes();
                    for (Income allIncome : allIncomes) {
                        incomes.getItems().add(allIncome.getType() + " : " + allIncome.getValue());
                    }

                    final List<Expense> allExpenses = Queries.getAllExpense();
                    for (Expense allExpense : allExpenses) {
                        expenses.getItems().add(allExpense.getType() + " : " + allExpense.getValue());
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        bottomHBox.getChildren().addAll(incomes, expenses);

        hBox.getChildren().addAll(type, insert, value, button);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(pane, hBox, names, bottomHBox);
        Scene scene = new Scene(vBox);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Application stopped!");
    }
}
