public class ClassData {
	private int telephone;
	private String address;
	private String name;
	private int age;

	public ClassData() {
		this(0, "", "");
	}

	public ClassData(int tel, String addr, String na) {

	}

	public void setTelephone(int tel) {
		telephone = tel;
	}	
	public int getTelephone() {
		return telephone;
	}

	public void setAge(int ag){
		age=ag;
	}
	public int getAge(){
		return age;
	}

	public void setAddress(String addr) {
		address = addr;
	}

	public String getAddress() {
		return address;
	}

	public void setName(String na) {
		name = na;
	}

	public String getName() {
		return name;
	}
}
