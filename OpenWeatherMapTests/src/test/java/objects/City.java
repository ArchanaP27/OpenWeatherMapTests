package objects;

/**
 * This defines the structure of an object that contains information about a City
 * @author Archana Patel
 */
public class City {
	public String id;
	public String name;
	public String country;
	public Coordinate coord;

	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", country=" + country + ", lat=" + coord.lat + ", lon=" + coord.lon + "]";
	}
}