package RoyalEnfield.RoyalEnfield;

public class Bike {
	private String bikeType;
	private String bikeName;
	public String getBikeType() {
		return bikeType;
	}
	public void setBikeType(String bikeType) {
		this.bikeType = bikeType;
	}
	public String getBikeName() {
		return bikeName;
	}
	public void setBikeName(String bikeName) {
		this.bikeName = bikeName;
	}
	@Override
	public String toString() {
		return "Bike [bikeType=" + bikeType + ", bikeName=" + bikeName + "]";
	}
	public Bike(String bikeType, String bikeName) {
		super();
		this.bikeType = bikeType;
		this.bikeName = bikeName;
	}
	
}
