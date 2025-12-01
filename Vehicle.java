public abstract class Vehicle {
    private String licensePlate;
    private String make;
    private String model;
    private int year;
    private VehicleStatus status;

    public enum VehicleStatus { Available, Held, Rented, UnderMaintenance, OutOfService }

    public Vehicle(String make, String model, int year) {
    	if (make == null || make.isEmpty())
    		this.make = null;
    	else
    		this.make = make.substring(0, 1).toUpperCase() + make.substring(1).toLowerCase();
    	
    	if (model == null || model.isEmpty())
    		this.model = null;
    	else
    		this.model = model.substring(0, 1).toUpperCase() + model.substring(1).toLowerCase();
    	
        this.year = year;
        this.status = VehicleStatus.Available;
        this.licensePlate = null;
    }

    public Vehicle() {
        this(null, null, 0);
    }

    
    //this method checks if a plate is valid or not
    private boolean isValidPlate(String plate) { 
        if (plate == null || plate.isEmpty()) {
            return false; // returns false if plate is missing 
        }
        return plate.matches("[A-Z]{3}[0-9]{3}"); // makes sure plate is 3 letters + 3 numbers
    }

    
    // this method sets the license plate
    public void setLicensePlate(String plate) {
        if (!isValidPlate(plate)) { 
            throw new IllegalArgumentException("Invalid. Please input the expected format: ABC123 "); // shows error if wrong format
        }
        this.licensePlate = plate.toUpperCase(); // saves plate in uppercase
        }


    public void setStatus(VehicleStatus status) {
    	this.status = status;
    }

    public String getLicensePlate() { return licensePlate; }

    public String getMake() { return make; }

    public String getModel() { return model;}

    public int getYear() { return year; }

    public VehicleStatus getStatus() { return status; }

    public String getInfo() {
        return "| " + licensePlate + " | " + make + " | " + model + " | " + year + " | " + status + " |";
    }

}
