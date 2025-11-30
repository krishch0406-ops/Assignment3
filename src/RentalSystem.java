import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;



public class RentalSystem {
	
	private static RentalSystem instance;
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private RentalHistory rentalHistory = new RentalHistory();
    RentalSystem() { }

    
    public static RentalSystem getInstance() {
    	
    	if(instance == null)
    	{
    		instance = new RentalSystem(); 
    	}
    	return instance;
    }

    public boolean addVehicle(Vehicle vehicle) {

        
        Vehicle existing = findVehicleByPlate(vehicle.getLicensePlate());

        if (existing != null) {
            System.out.println("Vehicle with plate " + vehicle.getLicensePlate() + " already exists.");
            return false;   // duplicate found
        }

        
        vehicles.add(vehicle);
        saveVehicle(vehicle);

        return true;      
    }


    public boolean addCustomer(Customer customer) {

        
        Customer existing = findCustomerById(customer.getCustomerId());

        if (existing != null) {
            System.out.println("Customer with ID " + customer.getCustomerId() + " already exists.");
            return false;   
        }

      
        customers.add(customer);
        saveCustomer(customer);

        return true;       
    }


    public void rentVehicle(Vehicle vehicle, Customer customer, LocalDate date, double amount) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Available) {
            vehicle.setStatus(Vehicle.VehicleStatus.Rented);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            saveRecord(new RentalRecord(vehicle, customer, date, amount, "RENT"));
            System.out.println("Vehicle rented to " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not available for renting.");
        }
    }

    public void returnVehicle(Vehicle vehicle, Customer customer, LocalDate date, double extraFees) {
        if (vehicle.getStatus() == Vehicle.VehicleStatus.Rented) {
            vehicle.setStatus(Vehicle.VehicleStatus.Available);
            rentalHistory.addRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            saveRecord(new RentalRecord(vehicle, customer, date, extraFees, "RETURN"));
            System.out.println("Vehicle returned by " + customer.getCustomerName());
        }
        else {
            System.out.println("Vehicle is not rented.");
        }
    }    

    public void displayVehicles(Vehicle.VehicleStatus status) {
        // Display appropriate title based on status
        if (status == null) {
            System.out.println("\n=== All Vehicles ===");
        } else {
            System.out.println("\n=== " + status + " Vehicles ===");
        }
        
        // Header with proper column widths
        System.out.printf("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n", 
            " Type", "Plate", "Make", "Model", "Year", "Status");
        System.out.println("|--------------------------------------------------------------------------------------------|");
    	  
        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                System.out.printf("| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n", 
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                System.out.println("  No Vehicles found.");
            } else {
                System.out.println("  No vehicles with Status: " + status);
            }
        }
        System.out.println();
    }

    public String displayVehiclesAsText(Vehicle.VehicleStatus status) {

        String text = "";


        // Display appropriate title based on status
        if (status == null) {
            text += "=== All Vehicles ===\n";
        } else {
            text += "=== " + status + " Vehicles ===\n";
        }

        // Header with proper column widths
        text += String.format("|%-16s | %-12s | %-12s | %-12s | %-6s | %-18s |%n",
            " Type", "Plate", "Make", "Model", "Year", "Status");
        text += "\n|--------------------------------------------------------------------------------------------|";

        boolean found = false;
        for (Vehicle vehicle : vehicles) {
            if (status == null || vehicle.getStatus() == status) {
                found = true;
                String vehicleType;
                if (vehicle instanceof Car) {
                    vehicleType = "Car";
                } else if (vehicle instanceof Minibus) {
                    vehicleType = "Minibus";
                } else if (vehicle instanceof PickupTruck) {
                    vehicleType = "Pickup Truck";
                } else {
                    vehicleType = "Unknown";
                }
                text += String.format("\n| %-15s | %-12s | %-12s | %-12s | %-6d | %-18s |%n",
                    vehicleType, vehicle.getLicensePlate(), vehicle.getMake(), vehicle.getModel(), vehicle.getYear(), vehicle.getStatus().toString());
            }
        }
        if (!found) {
            if (status == null) {
                text += "\n  No Vehicles found.";
            } else {
                text += "\n  No vehicles with Status: " + status;
            }
        }
        text += "\n";

        return text;
    }


    public void displayAllCustomers() {
        for (Customer c : customers) {
            System.out.println("  " + c.toString());
        }
    }

        public String displayAllCustomersAsText() {
            String text = "";
        for (Customer c : customers) {
            text += "  " + c.toString() + "\n";
        }
        return text;
    }

    public void displayRentalHistory() {
        if (rentalHistory.getRentalHistory().isEmpty()) {
            System.out.println("  No rental history found.");
        } else {
            // Header with proper column widths
            System.out.printf("|%-10s | %-12s | %-20s | %-12s | %-12s |%n", 
                " Type", "Plate", "Customer", "Date", "Amount");
            System.out.println("|-------------------------------------------------------------------------------|");
            
            for (RentalRecord record : rentalHistory.getRentalHistory()) {                
                System.out.printf("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n", 
                    record.getRecordType(), 
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
            System.out.println();
        }
    }


    public String displayRentalHistoryAsText() {

        String text = "";


        if (rentalHistory.getRentalHistory().isEmpty()) {
            text += "  No rental history found.";
        } else {
            // Header with proper column widths
            text += String.format("|%-10s | %-12s | %-20s | %-12s | %-12s |%n",
                " Type", "Plate", "Customer", "Date", "Amount");
            text += "|-------------------------------------------------------------------------------|\n";

            for (RentalRecord record : rentalHistory.getRentalHistory()) {
                text += String.format("| %-9s | %-12s | %-20s | %-12s | $%-11.2f |%n",
                    record.getRecordType(),
                    record.getVehicle().getLicensePlate(),
                    record.getCustomer().getCustomerName(),
                    record.getRecordDate().toString(),
                    record.getTotalAmount()
                );
            }
        }

        return text;
    }

    public Vehicle findVehicleByPlate(String plate) {
        for (Vehicle v : vehicles) {
            if (v.getLicensePlate().equalsIgnoreCase(plate)) {
                return v;
            }
        }
        return null;
    }
    
    public Customer findCustomerById(int id) {
        for (Customer c : customers)
            if (c.getCustomerId() == id)
                return c;
        return null;
    }
    private void saveVehicle(Vehicle vehicle) {
        try {
            FileWriter myWriter = new FileWriter("vehicles.txt", true);   

            myWriter.write(
                vehicle.getClass().getSimpleName() + "," +
                vehicle.getLicensePlate() + "," +
                vehicle.getMake() + "," +
                vehicle.getModel() + "," +
                vehicle.getYear() + "," +
                vehicle.getStatus() + "\n"
            );

            myWriter.close();   
            System.out.println("Successfully saved vehicle.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void saveCustomer(Customer customer) {
        try {
            FileWriter myWriter = new FileWriter("customers.txt", true);
            myWriter.write(customer.getCustomerId() + "," + customer.getCustomerName() + "\n");
            myWriter.close();
            System.out.println("Successfully saved customer.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void saveRecord(RentalRecord record) {
        try {
            FileWriter myWriter = new FileWriter("rental_records.txt", true);

            myWriter.write(
                record.getRecordType() + "," +
                record.getVehicle().getLicensePlate() + "," +
                record.getCustomer().getCustomerId() + "," +
                record.getCustomer().getCustomerName() + "," +
                record.getRecordDate() + "," +
                record.getTotalAmount() + "\n"
            );

            myWriter.close();
            System.out.println("Successfully saved rental record.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    private void loadData() {

     
        try {
            File file = new File("vehicles.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                String type  = parts[0];
                String make  = parts[1];
                String model = parts[2];
                int year     = Integer.parseInt(parts[3]);
                int lastNum  = Integer.parseInt(parts[4]);  
                Vehicle v = null;

                
                if (type.equals("Car")) {
                    
                    v = new Car(make, model, year, lastNum);
                } else if (type.equals("Minibus")) {
                    
                    v = new Minibus(make, model, year, false);
                } else if (type.equals("PickupTruck")) {
                    
                    v = new PickupTruck(make, model, year, lastNum, false);
                }

                if (v != null) {
                    vehicles.add(v);
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load vehicles.");
        }

     
        try {
            File file = new File("customers.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                int id      = Integer.parseInt(parts[0]);
                String name = parts[1];

                customers.add(new Customer(id, name));
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load customers.");
        }


        try {
            File file = new File("rental_records.txt");
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                String recordType = parts[0];
                String someVehicleKey = parts[1]; // whatever you saved here
                int id          = Integer.parseInt(parts[2]);
                String name     = parts[3];
                LocalDate date  = LocalDate.parse(parts[4]);
                double amount   = Double.parseDouble(parts[5]);

                // You may need to change this lookup based on what you saved
                Vehicle v = findVehicleByPlate(someVehicleKey);   // or by make/model if no plate
                Customer c = findCustomerById(id);

                if (v != null && c != null) {
                    rentalHistory.addRecord(new RentalRecord(v, c, date, amount, recordType));
                }
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Could not load rental records.");
        }
    }


}