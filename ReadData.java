import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ReadData {
	private Formatter output;
	private Scanner input;

	public void openFile() {
		try {
			input = new Scanner(new File("test.txt"));
		} catch (FileNotFoundException filesNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		}
	}

	public void readData() {
		ClassData cd = new ClassData();
		try {
			while (input.hasNext()) {
				cd.setName(input.next());
				cd.setAddress(input.next());
				cd.setTelephone(input.nextInt());
				System.out.printf("%-10s%-12s%-10d\n", cd.getName(),
						cd.getAddress(), cd.getTelephone());
			}
		} catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed.");
			input.close();
			System.exit(1);
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file.");
			System.exit(1);
		}
	}

	public void closeFile() {
		if (output != null)
			output.close();
	}
	public void test(){
		openFile();
		readData();
		closeFile();
	}
}
