package project.lyit.hotel;

import javafx.application.*;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.*;
import javafx.stage.*;

public class HotelApp extends Application {
	
	private Scene scene;
	private BorderPane sceneLayout;
	
	//LEFT PANE
	private VBox leftPane;
	private RadioButton rbRoom, rbCustomer, rbExtra;
	private Button btAdd, btEdit, btDelete;
	private ToggleGroup group;
	
	//DIALOG + DATABASE CONNECTOR
	private Dialog<String> dialog = new Dialog<>();
	private DatabaseConnector dbConnect;
	
	
	@Override
	public void start(Stage primaryStage){
		
		sceneLayout = new BorderPane();
		leftPane = getLeftPane();
		
		sceneLayout.setPrefSize(200,125);
		sceneLayout.setTop(new Label("Hotel Reservation System"));
		sceneLayout.setCenter(leftPane);
		
		scene = new Scene(sceneLayout);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Hotel Reservation System");
		primaryStage.show();
	}
	
	private VBox getLeftPane() {
		
		VBox vbox = new VBox(5);
		HBox hbox = new HBox(5);
		
		rbRoom = new RadioButton("Room");
		rbCustomer = new RadioButton("Customer");
		rbExtra = new RadioButton("Extra");
		
		group = new ToggleGroup();
		rbRoom.setToggleGroup(group);
		rbCustomer.setToggleGroup(group);
		rbExtra.setToggleGroup(group);
		
		btAdd = new Button("Add");
		btEdit = new Button("Edit");
		btDelete = new Button("Delete");
		
		btAdd.setOnAction(e -> {
	    	handleAdd();
	    });
		
		btEdit.setOnAction(e -> {
			handleEdit();
	    });
		
		btDelete.setOnAction(e -> {
			handleDelete();
	    });
		
		hbox.getChildren().addAll(btAdd, btEdit, btDelete);
		vbox.getChildren().addAll(rbRoom, rbCustomer, rbExtra, hbox);
		
		return vbox;
	}
	
	private void handleAdd() {
		dialog.setTitle("Add Details");
		dialog.setHeaderText("Enter Details to add to Database");
		// Set the button types.
		ButtonType btOk = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btOk, ButtonType.CANCEL);
		
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		String selectedOpt = selected.getText();
		
		switch (selectedOpt) {
		case "Room":
			System.out.println("You choose room!!");
			addARoom();
			break;
		case "Customer":
			System.out.println("You choose customer!!");
			addACustomer();
			break;
		case "Extra":
			System.out.println("You choose extra!!");
			addAnExtra();
			break;
		}
	}
	
	private void addARoom() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField roomNo = new TextField();
		roomNo.setPromptText("Room No");
		TextField roomType = new TextField();
		roomType.setPromptText("Room Type");
		TextField decommissioned = new TextField();
		decommissioned.setPromptText("Decommissioned");
		
		grid.add(new Label("Room No: "),0,0);
		grid.add(roomNo,1,0);
		grid.add(new Label("Room Type: "), 0, 1);
		grid.add(roomType, 1, 1);
		grid.add(new Label("Decommissioned: "), 0, 2);
		grid.add(decommissioned, 1, 2);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.addRoom(Integer.parseInt(roomNo.getText()), roomType.getText(), 
							Boolean.parseBoolean(decommissioned.getText()));
	}
	
	private void addACustomer() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField custNo = new TextField();
		custNo.setPromptText("Customer No");
		TextField first = new TextField();
		first.setPromptText("First Name");
		TextField last = new TextField();
		last.setPromptText("Last Name");
		TextField addr = new TextField();
		addr.setPromptText("Address");
		TextField phone = new TextField();
		phone.setPromptText("Phone");
		TextField email = new TextField();
		email.setPromptText("Email");
		
		grid.add(new Label("Customer No: "),0,0);
		grid.add(custNo,1,0);
		grid.add(new Label("First Name: "), 0, 1);
		grid.add(first, 1, 1);
		grid.add(new Label("Last Name: "), 0, 2);
		grid.add(last, 1, 2);
		grid.add(new Label("Address: "), 0, 3);
		grid.add(addr, 1, 3);
		grid.add(new Label("Phone: "), 0, 4);
		grid.add(phone, 1, 4);
		grid.add(new Label("Email: "), 0, 5);
		grid.add(email, 1, 5);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.addCustomer(Integer.parseInt(custNo.getText()), first.getText(), last.getText(), 
								addr.getText(), phone.getText(), email.getText());
	}
	
	private void addAnExtra() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField extraNo = new TextField();
		extraNo.setPromptText("Extra No");
		TextField type = new TextField();
		type.setPromptText("Type");
		TextField qty = new TextField();
		qty.setPromptText("Qty");
		TextField cost = new TextField();
		cost.setPromptText("Cost");
		TextField bookingNo = new TextField();
		bookingNo.setPromptText("Booking No");
		
		grid.add(new Label("Extra No: "),0,0);
		grid.add(extraNo,1,0);
		grid.add(new Label("Type: "), 0, 1);
		grid.add(type, 1, 1);
		grid.add(new Label("Qty: "), 0, 2);
		grid.add(qty, 1, 2);
		grid.add(new Label("Cost: "), 0, 3);
		grid.add(cost, 1, 3);
		grid.add(new Label("Booking No: "), 0, 4);
		grid.add(bookingNo, 1, 4);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.addExtra(Integer.parseInt(extraNo.getText()), type.getText(), 
							Integer.parseInt(qty.getText()), Double.parseDouble(cost.getText()), 
							Integer.parseInt(bookingNo.getText()));
	}
	
	private void handleEdit() {
		dialog.setTitle("Add Details");
		dialog.setHeaderText("Enter Details for the item you want to edit.");
		// Set the button types.
		ButtonType btOk = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btOk, ButtonType.CANCEL);
		
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		String selectedOpt = selected.getText();
		
		switch (selectedOpt) {
		case "Room":
			System.out.println("You choose room!!");
			editARoom();
			break;
		case "Customer":
			System.out.println("You choose customer!!");
			editACustomer();
			break;
		case "Extra":
			System.out.println("You choose extra!!");
			editAnExtra();
			break;
		}
	}
	
	private void editARoom() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField roomNo = new TextField();
		roomNo.setPromptText("Room No");
		TextField roomType = new TextField();
		roomType.setPromptText("Room Type");
		TextField decommissioned = new TextField();
		decommissioned.setPromptText("Decommissioned");
		
		grid.add(new Label("Room No: "),0,0);
		grid.add(roomNo,1,0);
		grid.add(new Label("Room Type: "), 0, 1);
		grid.add(roomType, 1, 1);
		grid.add(new Label("Decommissioned: "), 0, 2);
		grid.add(decommissioned, 1, 2);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.editRoom(Integer.parseInt(roomNo.getText()), roomType.getText(), 
							Boolean.parseBoolean(decommissioned.getText()));
	}
	
	private void editACustomer() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField custNo = new TextField();
		custNo.setPromptText("Customer No");
		TextField first = new TextField();
		first.setPromptText("First Name");
		TextField last = new TextField();
		last.setPromptText("Last Name");
		TextField addr = new TextField();
		addr.setPromptText("Address");
		TextField phone = new TextField();
		phone.setPromptText("Phone");
		TextField email = new TextField();
		email.setPromptText("Email");
		
		grid.add(new Label("Customer No: "),0,0);
		grid.add(custNo,1,0);
		grid.add(new Label("First Name: "), 0, 1);
		grid.add(first, 1, 1);
		grid.add(new Label("Last Name: "), 0, 2);
		grid.add(last, 1, 2);
		grid.add(new Label("Address: "), 0, 3);
		grid.add(addr, 1, 3);
		grid.add(new Label("Phone: "), 0, 4);
		grid.add(phone, 1, 4);
		grid.add(new Label("Email: "), 0, 5);
		grid.add(email, 1, 5);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.editCustomer(Integer.parseInt(custNo.getText()), first.getText(), last.getText(), 
								addr.getText(), phone.getText(), email.getText());
	}
	
	
	private void editAnExtra() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField extraNo = new TextField();
		extraNo.setPromptText("Extra No");
		TextField type = new TextField();
		type.setPromptText("Type");
		TextField qty = new TextField();
		qty.setPromptText("Qty");
		TextField cost = new TextField();
		cost.setPromptText("Cost");
		TextField bookingNo = new TextField();
		bookingNo.setPromptText("Booking No");
		
		grid.add(new Label("Extra No: "),0,0);
		grid.add(extraNo,1,0);
		grid.add(new Label("Type: "), 0, 1);
		grid.add(type, 1, 1);
		grid.add(new Label("Qty: "), 0, 2);
		grid.add(qty, 1, 2);
		grid.add(new Label("Cost: "), 0, 3);
		grid.add(cost, 1, 3);
		grid.add(new Label("Booking No: "), 0, 4);
		grid.add(bookingNo, 1, 4);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.editExtra(Integer.parseInt(extraNo.getText()), type.getText(), 
							Integer.parseInt(qty.getText()), Double.parseDouble(cost.getText()), 
							Integer.parseInt(bookingNo.getText()));
	}
	
	

	private void handleDelete() {
		dialog.setTitle("What room");
		dialog.setHeaderText("Enter id to delete.");
		// Set the button types.
		ButtonType btOk = new ButtonType("Ok", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(btOk, ButtonType.CANCEL);
		
		RadioButton selected = (RadioButton) group.getSelectedToggle();
		String selectedOpt = selected.getText();
		
		switch (selectedOpt) {
		case "Room":
			System.out.println("You choose room!!");
			deleteARoom();
			break;
		case "Customer":
			System.out.println("You choose customer!!");
			deleteACustomer();
			break;
		case "Extra":
			System.out.println("You choose extra!!");
			deleteAnExtra();
			break;
		}
	}
	
	private void deleteARoom() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField roomNo = new TextField();
		roomNo.setPromptText("Room No");
		
		grid.add(new Label("Room No: "),0,0);
		grid.add(roomNo,1,0);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.deleteRoom(Integer.parseInt(roomNo.getText()));
	}
	
	private void deleteACustomer() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField custNo = new TextField();
		custNo.setPromptText("Customer No");
		
		grid.add(new Label("Customer No: "),0,0);
		grid.add(custNo,1,0);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.deleteCustomer(Integer.parseInt(custNo.getText()));
	}
	
	private void deleteAnExtra() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField extraNo = new TextField();
		extraNo.setPromptText("Extra No");
		
		grid.add(new Label("Extra No: "),0,0);
		grid.add(extraNo,1,0);
		
		dialog.getDialogPane().setContent(grid);
		dialog.showAndWait();
		
		dbConnect = new DatabaseConnector();
		dbConnect.deleteCustomer(Integer.parseInt(extraNo.getText()));
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}
}
