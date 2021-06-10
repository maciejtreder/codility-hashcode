import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

class Car {
    enum Color {
        RED,
        BLUE,
        GREEN,
        YELLOW
    }
    
    enum Brand {
        OPEL,
        MERCEDES,
        CHEVROLETT
    }
    
    enum Type {
        CABRIO,
        SEDAN,
        COUPE
    }

	public Color color;
 	public Brand brand;
	public Type type;
	public String registrationNumber;

	public Car(Color color, Brand brand, Type type, String registrationNumber) {
        this.color = color;
        this.brand = brand;
        this.type = type;
        this.registrationNumber = registrationNumber;
    }

    @Override
    public int hashCode() {
        switch(color) {
            case RED: return 1;
            case BLUE: return 2;
            case GREEN: return 3;
            case YELLOW: return 4;
            default: return 5;
        }
    }
 

    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        
        Car other = (Car) obj;
        return 
            this.brand == other.brand && 
            this.color == other.color && 
            this.type == other.type && 
            this.registrationNumber.equals(other.registrationNumber);
    }
}

class Driver {
    public String firstName;
    public String lastName;
    public String ssn;

    public Driver(String firstName, String lastName, String ssn) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssn = ssn;
    }

    public String toString() {
        return "" + this.firstName + " " + this.lastName + " " + this.ssn;
    }
}

class MyHashMap {

    private class Node {
        Car car;
        List<Driver> drivers;

        Node(Car car, List<Driver> drivers) {
            this.car = car;
            this.drivers = drivers;
        }
    }

    private List<Node>[] buckets = new List[3];
    
    public MyHashMap() {
        Arrays.fill(buckets, null);
    }

    public void put(Car car, List<Driver> drivers) {
        Node node = this.getNode(car);
        if (node != null) {
            node.drivers = drivers;
        } else {
            List<Node> bucket = this.buckets[car.hashCode()%3];
            if (bucket == null) {
                bucket = new LinkedList<Node>();
                this.buckets[car.hashCode()%3] = bucket;
            } 
            bucket.add(new Node(car, drivers));
        }
    }

    public List<Driver> get(Car car) {
        Node node = this.getNode(car);
        if (node != null) {
            return node.drivers;
        } else {
            return null;
        }
    }

    private Node getNode(Car car) {
        List<Node> bucket = this.buckets[car.hashCode()%3];

        if (bucket == null) {
            return null;
        } else {
            for (Node node : bucket) {
                if (node.car.equals(car)) {
                    return node;
                }
            }
            return null;
        }
    }

    public void print() {
        System.out.println(this.buckets[0]);
        System.out.println(this.buckets[1]);
        System.out.println(this.buckets[2]);
    }
}

public class Test {
    public static void main(String[] args) {
        MyHashMap insuredCars = new MyHashMap();

        Car car1 = new Car(Car.Color.RED, Car.Brand.OPEL, Car.Type.CABRIO, "abc-123");
        Car car2 = new Car(Car.Color.GREEN, Car.Brand.MERCEDES, Car.Type.COUPE, "foo-321");
        Car car3 = new Car(Car.Color.GREEN, Car.Brand.CHEVROLETT, Car.Type.SEDAN, "bar-123");
        Car car4 = new Car(Car.Color.RED, Car.Brand.MERCEDES, Car.Type.SEDAN, "xyz-321");
        Car car5 = new Car(Car.Color.YELLOW, Car.Brand.OPEL, Car.Type.COUPE, "asd-123");

        Driver driver1 = new Driver("John", "Kowalski", "123-123-123");
        Driver driver2 = new Driver("Kate", "Smith", "321-123-123");
        Driver driver3 = new Driver("Peter", "Jones", "123-123-123");
        Driver driver4 = new Driver("James", "Williams", "123-123-123");
        Driver driver5 = new Driver("Robert", "Taylor", "123-123-123");
        Driver driver6 = new Driver("Margaret", "Davies", "123-123-123");
        Driver driver7 = new Driver("Patricia", "Brown", "123-123-123");
        Driver driver8 = new Driver("Linda", "Wilson", "123-123-123");

        insuredCars.put(car1, Arrays.asList(driver1, driver2));
        insuredCars.put(car2, Arrays.asList(driver3, driver4, driver5));
        insuredCars.put(car3, Arrays.asList(driver6, driver7));
        insuredCars.put(car4, Arrays.asList(driver8, driver2));
        insuredCars.put(car5, Arrays.asList(driver3));



        System.out.println(insuredCars.get(car1));
        insuredCars.print();
    }
}