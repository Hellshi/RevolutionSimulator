package Controller.View;

import Controller.Users.UserController;
import Controller.Users.UserControllerFactory;
import Model.User.Role;
import Model.User.UserEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class UserViewController {

    @FXML private TextField txtName;
    @FXML private TextField txtSalary;
    @FXML private ComboBox<String> cbRole;
    @FXML private Label lblStatus;
    @FXML private Button btnSave;
    @FXML private Button btnCancel;

    @FXML private TableView<UserEntity> userTable;
    @FXML private TableColumn<UserEntity, String> colId;
    @FXML private TableColumn<UserEntity, String> colName;
    @FXML private TableColumn<UserEntity, Double> colSalary;
    @FXML private TableColumn<UserEntity, String> colRole;
    @FXML private TableColumn<UserEntity, Void> colActions;

    private UserController userController;
    private UserEntity userBeingEdited = null;

    @FXML
    public void initialize() {
        this.userController = UserControllerFactory.getUserController();

        cbRole.getItems().addAll("dev", "po", "qa", "manager");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        // Formatação do salário (R$)
        colSalary.setCellFactory(column -> new TableCell<UserEntity, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    setText(currencyFormat.format(item));
                    setStyle("-fx-alignment: CENTER-RIGHT;");
                }
            }
        });

        addActionButtons();
        loadData();
        resetForm();
    }

    @FXML
    public void onSave() {
        try {
            String name = txtName.getText();
            String salaryStr = txtSalary.getText().replace(",", ".");
            String roleStr = cbRole.getValue();

            if (roleStr == null || name.isEmpty() || salaryStr.isEmpty()) {
                lblStatus.setText("Erro: Preencha todos os campos!");
                lblStatus.setStyle("-fx-text-fill: red;");
                return;
            }

            double salary = Double.parseDouble(salaryStr);

            if (userBeingEdited == null) {
                userController.createUser(salary, name, roleStr);
                lblStatus.setText("Usuário criado com sucesso!");
            } else {
                userBeingEdited.setAttributes(salary, name, Role.fromString(roleStr));
                int id = Integer.parseInt(userBeingEdited.getId());
                userController.updateUser(id, userBeingEdited.toString());
                lblStatus.setText("Usuário atualizado com sucesso!");
            }

            lblStatus.setStyle("-fx-text-fill: green;");
            resetForm();
            loadData();

        } catch (NumberFormatException e) {
            lblStatus.setText("Erro: Salário inválido.");
        } catch (Exception e) {
            lblStatus.setText("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    public void onCancel() {
        resetForm();
        lblStatus.setText("Edição cancelada.");
    }

    private void fillFormForEditing(UserEntity user) {
        this.userBeingEdited = user;
        txtName.setText(user.getName());
        txtSalary.setText(String.valueOf(user.getSalary()));

        if (user.getRole() != null) {
            cbRole.setValue(user.getRole().toString().toLowerCase());
        }

        btnSave.setText("Atualizar");
        btnSave.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancel.setVisible(true);
    }

    private void resetForm() {
        this.userBeingEdited = null;

        txtName.clear();
        txtSalary.clear();
        cbRole.setValue("dev");

        btnSave.setText("Adicionar");
        btnSave.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-weight: bold;");
        btnCancel.setVisible(false);
    }

    private void loadData() {
        List<UserEntity> javaList = userController.getAllUsers();
        ObservableList<UserEntity> observableList = FXCollections.observableArrayList(javaList);
        userTable.setItems(observableList);
    }

    private void addActionButtons() {
        Callback<TableColumn<UserEntity, Void>, TableCell<UserEntity, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<UserEntity, Void> call(final TableColumn<UserEntity, Void> param) {
                return new TableCell<>() {
                    private final Button btnEdit = new Button("✎");
                    private final Button btnDelete = new Button("X");
                    private final HBox container = new HBox(10, btnEdit, btnDelete);

                    {
                        btnEdit.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                        btnEdit.setCursor(javafx.scene.Cursor.HAND);
                        btnEdit.setOnAction(e -> fillFormForEditing(getTableView().getItems().get(getIndex())));

                        btnDelete.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5;");
                        btnDelete.setCursor(javafx.scene.Cursor.HAND);
                        btnDelete.setOnAction(e -> deleteUser(getTableView().getItems().get(getIndex())));

                        container.setAlignment(javafx.geometry.Pos.CENTER);
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(container);
                        }
                    }
                };
            }
        };
        colActions.setCellFactory(cellFactory);
    }

    private void deleteUser(UserEntity user) {
        try {
            int id = Integer.parseInt(user.getId());
            userController.deleteUser(id);
            if (userBeingEdited != null && userBeingEdited.getId().equals(user.getId())) {
                resetForm();
            }
            loadData();
            lblStatus.setText("Usuário ID " + id + " removido.");
        } catch (Exception e) {
            System.out.println("Erro ao deletar: " + e.getMessage());
        }
    }
}