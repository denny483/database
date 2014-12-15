import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CreatData {
	private Formatter output;
	private Scanner input;

	public void openFile() {
		try {
			output = new Formatter("test.txt");
		} catch (FileNotFoundException filesNotFoundException) {
			System.err.println("Error creating file.");
			System.exit(1);
		} catch (SecurityException securityException) {
			System.err.println("You do not have wrrite access to this file.");
			System.exit(1);
		}
	}

	public void addData() {
		ClassData cd = new ClassData();
		input = new Scanner(System.in);
		System.out.printf("%s\n%s", "Enter student name,age,telephone.",
				"? ");
		while (input.hasNext()) {
			try {
				cd.setName(input.next());
				cd.setAge(input.nextInt());
				//cd.setAddress(input.next());
				cd.setTelephone(input.nextInt());
				output.format("%s %s %d", cd.getName(), cd.getAge(),
						cd.getTelephone());
				output.format("\n");
			} catch (FormatterClosedException formatterClosedException) {
				System.err.println("Error writing to file.");
				return;
			} catch (NoSuchElementException noSuchElementException) {
				System.err.println("Invalid input. Please try again.");
				input.nextLine();
			}
			System.out.printf("%s\n%s",
					"Enter student name,age,telephone.", "? ");
		}
	}

	public void closeFile() {
		if (output != null)
			output.close();
	}
	public void test(){
		openFile();
		addData();
		closeFile();
	}
}
