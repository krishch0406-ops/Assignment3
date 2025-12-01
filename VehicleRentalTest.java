//Trinidad Laguardia 0849149
//Task 2 
//11/30/2025

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

class VehicleRentalTest {

	
    @Test
    void testLicensePlate() {
        // create some cars to test license plates
        Vehicle v1 = new Car("Toyota", "Corolla", 2020, 5);
        Vehicle v2 = new Car("Honda", "Civic", 2021, 5);
        Vehicle v3 = new Car("Ford", "Focus", 2019, 5);

        // check valid plates
        v1.setLicensePlate("AAA100");
        assertEquals("AAA100", v1.getLicensePlate());
        assertTrue(v1.getLicensePlate().matches("[A-Z]{3}[0-9]{3}"));

        v2.setLicensePlate("ABC567");
        assertEquals("ABC567", v2.getLicensePlate());
        assertTrue(v2.getLicensePlate().matches("[A-Z]{3}[0-9]{3}"));

        v3.setLicensePlate("ZZZ999");
        assertEquals("ZZZ999", v3.getLicensePlate());
        assertTrue(v3.getLicensePlate().matches("[A-Z]{3}[0-9]{3}"));

        // check invalid plates throw error
        assertThrows(IllegalArgumentException.class, () -> new Car("Mazda", "3", 2022, 5).setLicensePlate(""));
        assertThrows(IllegalArgumentException.class, () -> new Car("Nissan", "Altima", 2020, 5).setLicensePlate(null));
        assertThrows(IllegalArgumentException.class, () -> new Car("Chevy", "Malibu", 2018, 5).setLicensePlate("AAA1000"));
        assertThrows(IllegalArgumentException.class, () -> new Car("BMW", "X3", 2021, 5).setLicensePlate("ZZZ99"));
    }

    
    
    @Test
    void testRentAndReturnVehicle() {
    	
        // create a car and a customer
        Vehicle vehicle = new Car("Toyota", "Corolla", 2020, 5);
        vehicle.setLicensePlate("ABC123");
        Customer customer = new Customer(1, "Juan");

        // use Singleton rental system
        RentalSystem rentalSystem = RentalSystem.getInstance();
        rentalSystem.addVehicle(vehicle);
        rentalSystem.addCustomer(customer);

        // check vehicle starts available
        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        // rent works first time
        boolean rentSuccess = rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), 100.0);
        assertTrue(rentSuccess);
        assertEquals(Vehicle.VehicleStatus.Rented, vehicle.getStatus());

        // rent fails second time
        boolean rentAgain = rentalSystem.rentVehicle(vehicle, customer, LocalDate.now(), 100.0);
        assertFalse(rentAgain);

        // return works first time
        boolean returnSuccess = rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), 0.0);
        assertTrue(returnSuccess);
        assertEquals(Vehicle.VehicleStatus.Available, vehicle.getStatus());

        // return fails second time
        boolean returnAgain = rentalSystem.returnVehicle(vehicle, customer, LocalDate.now(), 0.0);
        assertFalse(returnAgain);
    }

    
    
    @Test
    void testSingletonRentalSystem() throws Exception {
        // check constructor is private
        Constructor<RentalSystem> constructor = RentalSystem.class.getDeclaredConstructor();
        int modifiers = constructor.getModifiers();
        assertEquals(Modifier.PRIVATE, modifiers);

        // check getInstance returns object
        RentalSystem instance = RentalSystem.getInstance();
        assertNotNull(instance);

        // check same instance is returned
        RentalSystem anotherInstance = RentalSystem.getInstance();
        assertSame(instance, anotherInstance);
    }
}
