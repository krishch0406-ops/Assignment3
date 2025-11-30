import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import java.time.LocalDate;


public class RentalSystemGUI extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Rental System");
        RentalSystem rentalSystem = new RentalSystem();

        Pane pane = new Pane();
        int height = 0; // height of buttons
        Button addVehicleButton = new Button("Add Vehicle");
        Button addCustomerButton = new Button("Add Customer");
        Button rentVehicleButton = new Button("Rent Vehicle");
        Button returnVehicleButton = new Button("Return Vehicle");
        Button displayAvailableButton = new Button("Display Available Vehicles");
        Button showRentalHistoryButton = new Button("Show Rental History");
        Button exitButton = new Button("Exit");
        Button[] mainmenuButtons = new Button[] { addVehicleButton, addCustomerButton, rentVehicleButton,
                returnVehicleButton, displayAvailableButton, showRentalHistoryButton, exitButton };
        pane.getChildren().addAll(mainmenuButtons);
        addVehicleButton.relocate(50, height += 30);
        addCustomerButton.relocate(50, height += 30);
        rentVehicleButton.relocate(50, height += 30);
        returnVehicleButton.relocate(50, height += 30);
        displayAvailableButton.relocate(50, height += 30);
        showRentalHistoryButton.relocate(50, height += 30);
        exitButton.relocate(50, height += 30);

        addVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                pane.getChildren().clear(); // clear the screen

                Text type = new Text("0"); // instead of dealing with weird variable stuff, use this as a variable
                Text selectTypeText = new Text("Select Type Of Vehicle:");
                CheckBox carBox = new CheckBox("Car");
                CheckBox minibusBox = new CheckBox("Minbus");
                CheckBox pickupTruckBox = new CheckBox("Pickup Truck");

                Text failText = new Text("Vehicle not added successfully.");
                Text successText = new Text("Vehicle was added successfully.");

                Text specialText = new Text(""); // special question for each type of vehicle
                CheckBox specialBox = new CheckBox("");
                TextField specialField = new TextField();
                pane.getChildren().add(specialText);
                specialText.relocate(50, 385);

                pane.getChildren().add(selectTypeText);
                selectTypeText.relocate(50, 25);
                pane.getChildren().add(carBox);
                carBox.relocate(50, 55);
                pane.getChildren().add(minibusBox);
                minibusBox.relocate(50, 85);
                pane.getChildren().add(pickupTruckBox);
                pickupTruckBox.relocate(50, 115);

                carBox.setOnAction(new EventHandler<ActionEvent>() { // when you select one, deselect others, theres
                                                                     // definately a better way to do this
                    public void handle(ActionEvent e) {
                        minibusBox.setSelected(false);
                        pickupTruckBox.setSelected(false);
                        specialText.setText("Enter number of seats:");
                        type.setText("1");

                        pane.getChildren().remove(specialField);
                        pane.getChildren().add(specialField);
                        pane.getChildren().remove(specialBox);
                        specialField.relocate(50, 415);
                        specialBox.setSelected(false);
                    }
                });
                minibusBox.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        carBox.setSelected(false);
                        pickupTruckBox.setSelected(false);
                        specialText.setText("");
                        type.setText("2");
                        specialBox.setText("Is accessible?");
                        pane.getChildren().remove(specialField);
                        pane.getChildren().remove(specialBox);
                        pane.getChildren().add(specialBox);
                        specialText.relocate(50, 385);
                        specialBox.relocate(50, 385);
                        specialField.setText("");
                    }
                });
                pickupTruckBox.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        minibusBox.setSelected(false);
                        carBox.setSelected(false);
                        specialText.setText("Enter the cargo size:");
                        type.setText("3");
                        specialBox.setText("Has trailer?");
                        pane.getChildren().remove(specialField);
                        pane.getChildren().remove(specialBox);
                        pane.getChildren().add(specialBox);
                        pane.getChildren().add(specialField);
                        specialText.relocate(50, 385);
                        specialField.relocate(50, 415);
                        specialBox.relocate(200, 385);
                        specialField.setText("");
                    }
                });

                Text plateText = new Text("License Plate:");
                TextField plate = new TextField();

                pane.getChildren().add(plateText);
                plateText.relocate(50, 145);
                pane.getChildren().add(plate);
                plate.relocate(50, 175);

                Text makeText = new Text("Make:");
                TextField make = new TextField();

                pane.getChildren().add(makeText);
                makeText.relocate(50, 205);
                pane.getChildren().add(make);
                make.relocate(50, 235);

                Text modelText = new Text("Model:");
                TextField model = new TextField();

                pane.getChildren().add(modelText);
                modelText.relocate(50, 265);
                pane.getChildren().add(model);
                model.relocate(50, 295);

                Text yearText = new Text("Year:");
                TextField year = new TextField();

                pane.getChildren().add(yearText);
                yearText.relocate(50, 325);
                pane.getChildren().add(year);
                year.relocate(50, 355);

                Button submitButton = new Button("Submit Vehicle");
                pane.getChildren().add(submitButton);
                submitButton.relocate(50, 450);

                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e) {
                        pane.getChildren().remove(failText);
                        pane.getChildren().remove(successText);
                        Vehicle vehicle;

                        if (make.getText() == "" || model.getText() == "" || plate.getText() == ""
                                || year.getText().length() != 4 || ("13".contains(type.getText())
                                && specialField.getText() == "")) {
                            vehicle = null;
                        }

                        else if (type.getText().equals("1")) {
                            vehicle = new Car(make.getText(), model.getText(), Integer.parseInt(year.getText()),
                                    Integer.parseInt(specialField.getText()));

                        } else if (type.getText().equals("2")) {
                            vehicle = new Minibus(make.getText(), model.getText(), Integer.parseInt(year.getText()),
                                    specialBox.isSelected());

                        } else if (type.getText().equals("3")) {
                            vehicle = new PickupTruck(make.getText(), model.getText(), Integer.parseInt(year.getText()), Double.parseDouble(specialField.getText()), specialBox.isSelected());

                        } else {
                            vehicle = null;
                        }
                        if (vehicle != null) {
                            vehicle.setLicensePlate(plate.getText().toUpperCase());
                            rentalSystem.addVehicle(vehicle);
                            pane.getChildren().add(successText);
                            successText.relocate(50, 510);
                        }
                        else {
                            pane.getChildren().add(failText);
                            failText.relocate(50, 510);
                        }
                    }

    });

                Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 480);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });

            }
        });

        addCustomerButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                pane.getChildren().clear(); // clear the screen

                Text idText = new Text("Customer ID: ");
                pane.getChildren().add(idText);
                idText.relocate(50, 55);
                TextField idTextField = new TextField();
                pane.getChildren().add(idTextField);
                idTextField.relocate(50, 85);

                Text nameText = new Text("Full Name: ");
                pane.getChildren().add(nameText);
                nameText.relocate(50, 115);
                TextField nameTextField = new TextField();
                pane.getChildren().add(nameTextField);
                nameTextField.relocate(50, 145);


                Button submitButton = new Button("Add Customer");
                pane.getChildren().add(submitButton);
                submitButton.relocate(50, 200);

                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Text outputText = new Text("Customer added successfully.");
                    pane.getChildren().remove(outputText);
                    try {
                        rentalSystem.addCustomer(new Customer(Integer.parseInt(idTextField.getText()), nameTextField.getText()));

                    pane.getChildren().add(outputText);
                    outputText.relocate(50, 260);

                    } catch (Exception ex) {
                        outputText.setText("Adding Customer Not Successful.");
                        pane.getChildren().add(outputText);
                        outputText.relocate(50, 265);
                    }
                }
        });


        Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 290);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });







            }
        });




        rentVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                pane.getChildren().clear(); // clear the screen

                Text plateText = new Text("Enter license plate:");
                pane.getChildren().add(plateText);
                plateText.relocate(50, 55);
                TextField plateTextField = new TextField();
                pane.getChildren().add(plateTextField);
                plateTextField.relocate(50, 85);

                Text vehicleText = new Text(rentalSystem.displayVehiclesAsText(Vehicle.VehicleStatus.Available));
                pane.getChildren().add(vehicleText);
                vehicleText.relocate(225, 55);




                Text idText = new Text("Customer ID:");
                pane.getChildren().add(idText);
                idText.relocate(50, 115);
                TextField idTextField = new TextField();
                pane.getChildren().add(idTextField);
                idTextField.relocate(50, 145);


                Text customerText = new Text(rentalSystem.displayAllCustomersAsText());
                pane.getChildren().add(customerText);
                customerText.relocate(225, 350);

                Text amountText = new Text("Enter rental amount:");
                pane.getChildren().add(amountText);
                amountText.relocate(50, 175);
                TextField amountTextField = new TextField();
                pane.getChildren().add(amountTextField);
                amountTextField.relocate(50, 205);


                Button submitButton = new Button("Rent Vehicle");
                pane.getChildren().add(submitButton);
                submitButton.relocate(50, 235);

                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Text outputText = new Text("Vehicle Rented successfully.");
                    try {
                        rentalSystem.rentVehicle(rentalSystem.findVehicleByPlate(plateTextField.getText().toUpperCase()), rentalSystem.findCustomerById(Integer.parseInt(idTextField.getText())), LocalDate.now(), Double.parseDouble(amountTextField.getText()));

                    pane.getChildren().add(outputText);
                    outputText.relocate(50, 265);

                    } catch (Exception ex) {
                        outputText.setText("Vehicle Rental Not Successful.");
                        pane.getChildren().add(outputText);
                        outputText.relocate(50, 265);
                    }

                }
        });


        Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 280);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });







            }
        });


        returnVehicleButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                pane.getChildren().clear(); // clear the screen

                Text plateText = new Text("Enter license plate:");
                pane.getChildren().add(plateText);
                plateText.relocate(50, 55);
                TextField plateTextField = new TextField();
                pane.getChildren().add(plateTextField);
                plateTextField.relocate(50, 85);

                Text vehicleText = new Text(rentalSystem.displayVehiclesAsText(Vehicle.VehicleStatus.Rented));
                pane.getChildren().add(vehicleText);
                vehicleText.relocate(225, 55);




                Text idText = new Text("Customer ID:");
                pane.getChildren().add(idText);
                idText.relocate(50, 115);
                TextField idTextField = new TextField();
                pane.getChildren().add(idTextField);
                idTextField.relocate(50, 145);


                Text customerText = new Text(rentalSystem.displayAllCustomersAsText());
                pane.getChildren().add(customerText);
                customerText.relocate(225, 350);

                Text feesText = new Text("Enter any additional return fees:");
                pane.getChildren().add(feesText);
                feesText.relocate(50, 175);
                TextField feesTextField = new TextField();
                pane.getChildren().add(feesTextField);
                feesTextField.relocate(50, 205);


                Button submitButton = new Button("Return Vehicle");
                pane.getChildren().add(submitButton);
                submitButton.relocate(50, 235);

                submitButton.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    Text outputText = new Text("Vehicle Returned successfully.");
                    try {
                        rentalSystem.returnVehicle(rentalSystem.findVehicleByPlate(plateTextField.getText().toUpperCase()), rentalSystem.findCustomerById(Integer.parseInt(idTextField.getText())), LocalDate.now(), Double.parseDouble(feesTextField.getText()));

                    pane.getChildren().add(outputText);
                    outputText.relocate(50, 265);

                    } catch (Exception ex) {
                        outputText.setText("Vehicle Return Not Successful.");
                        pane.getChildren().add(outputText);
                        outputText.relocate(50, 265);
                    }

                }
        });


        Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 280);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });







            }
        });



        displayAvailableButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                pane.getChildren().clear();

                Text vehicleText = new Text(rentalSystem.displayVehiclesAsText(Vehicle.VehicleStatus.Available));
                pane.getChildren().add(vehicleText);
                vehicleText.relocate(50, 100);


                        Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 50);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });
            }
        });



        showRentalHistoryButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                pane.getChildren().clear();

                Text vehicleText = new Text(rentalSystem.displayRentalHistoryAsText());
                pane.getChildren().add(vehicleText);
                vehicleText.relocate(50, 100);


                        Button backButton = new Button("< Back");
                pane.getChildren().add(backButton);
                backButton.relocate(50, 50);

                backButton.setOnAction(new EventHandler<ActionEvent>() { // on back, clear screen and redraw homepage
                    public void handle(ActionEvent e) {
                        int height = 0;
                        pane.getChildren().clear();
                        pane.getChildren().addAll(mainmenuButtons);
                        addVehicleButton.relocate(50, height += 30);
                        addCustomerButton.relocate(50, height += 30);
                        rentVehicleButton.relocate(50, height += 30);
                        returnVehicleButton.relocate(50, height += 30);
                        displayAvailableButton.relocate(50, height += 30);
                        showRentalHistoryButton.relocate(50, height += 30);
                        exitButton.relocate(50, height += 30);

                    }
                });
            }
        });



        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                System.exit(0);
            }
        });


        Scene scene = new Scene(pane, 800, 600);

        stage.setScene(scene);

        stage.show();


    }



    public static void main(String[] args) {

        launch(args);

    }



}
