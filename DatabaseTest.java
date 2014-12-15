import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;

class StudentData {
	public String[] stuData = new String[4];

	public StudentData(String i, String j, String k, String l) {
		stuData[0] = i;
		stuData[1] = j;
		stuData[2] = k;
		stuData[3] = l;
	}
}

public class DatabaseTest {
	public static Scanner dataInput;
	public static Scanner dataInput2;
	public static Scanner instructInput;
	public static FileWriter fw;
	public static Boolean DESC = false, isDISTINCT = false,
			DISTINCTFLAG = false, joinFlag = false;
	public static String s, chk;
	public static String[] SpiltedString;
	public static String[] SpiltedFrom;
	public static String[] tmpdata;
	public static String[] jointmpdata;
	public static String[] SpiltedSelecttable;
	public static String[] tmpString;
	public static String[] dataraw;
	public static String[] SpiltedOrderby;
	public static String[] Distincttemp = new String[20];
	public static String tmptovarString = "";
	public static int[] Datastate;
	public static int whereStart = 0, whereEnd = 0, orderStart = 0,
			orderEnd = 0, orderType = 0, tempordertype = 0, tableStart = 0,
			DISTINCTtype = 0, DISnum = 0, fromStart = 0;
	public static int type = 0;// delete 1 select 2
	public static int resultrecords = 0;
	static ArrayList<StudentData> m_data = new ArrayList<StudentData>();
	static ArrayList<StudentData> temp_data = new ArrayList<StudentData>();
	static ArrayList<StudentData> Result_data = new ArrayList<StudentData>();
	static ArrayList<StudentData> join_data = new ArrayList<StudentData>();
	static ArrayList<StudentData> join_data2 = new ArrayList<StudentData>();

	public static void openDataFile() {
		try {
			dataInput = new Scanner(new File("data.txt"));

		} catch (FileNotFoundException filesNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		}
	}

	public static void openData2File() {
		try {
			dataInput = new Scanner(new File("data2.txt"));

		} catch (FileNotFoundException filesNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		}
	}

	public static void resultfile() {
		try {
			fw = new FileWriter("result.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void jointable() throws IOException {

		String[] tempStudentData = new String[4];
		String[] title1, title2;
		Boolean flag = false;
		String titleTemp = null;
		int tempd1 = 0, tempd2 = 0;
		String joinSelectField = SpiltedString[1];
		String[] joinField = joinSelectField.split(",");
		String temptable = "";
		// 存第一行欄位
		dataInput = new Scanner(new File("data.txt"));
		titleTemp = dataInput.nextLine();
		title1 = titleTemp.split(" +");
		int[] FieldTF1 = new int[title1.length];
		Arrays.fill(FieldTF1, 0);
		dataInput.close();

		dataInput = new Scanner(new File("data2.txt"));

		titleTemp = dataInput.nextLine();
		title2 = titleTemp.split(" +");
		int[] FieldTF2 = new int[title2.length];
		Arrays.fill(FieldTF2, 0);
		for (int j = 0; j < joinField.length; j++) {
			temptable = temptable + joinField[j] + ".";
		}
		String[] Table = temptable.split("[.]");
		for (int k = 0; k < Table.length; k += 2) {
			if (Table[k].equalsIgnoreCase("data")) {
				for (int p = 0; p < title1.length; p++) {
					if (Table[k + 1].equalsIgnoreCase(title1[p])) {
						FieldTF1[p] = 1;
						// System.out.print(title1[p]+"\t");
					}
				}

			} else if (Table[k].equalsIgnoreCase("data2")) {
				for (int q = 0; q < title2.length; q++) {
					if (Table[k + 1].equalsIgnoreCase(title2[q])) {
						FieldTF2[q] = 1;
						// System.out.print(title2[q]+"\t");
					}
				}

			}

		}
		for (int i = 0; i < title1.length; i++) {
			if (FieldTF1[i] == 1) {
				System.out.print(title1[i] + "\t");
				fw.write(title1[i] + "\t");
			}
		}
		for (int i = 0; i < title2.length; i++) {
			if (FieldTF2[i] == 1) {
				System.out.print(title2[i] + "\t");
				fw.write(title2[i] + "\t");
			}
		}
		System.out.println();
		fw.write("\n");
		dataInput.close();

		dataInput = new Scanner(new File("data.txt"));

		dataInput.nextLine();
		while (dataInput.hasNext()) {
			for (int i = 0; i < 4; i++) {
				tempStudentData[i] = dataInput.next();
			}
			join_data
					.add(new StudentData(tempStudentData[0],
							tempStudentData[1], tempStudentData[2],
							tempStudentData[3]));
		}
		Collections.sort(join_data, new Comparator<StudentData>() {
			public int compare(StudentData o1, StudentData o2) {
				return o1.stuData[3].compareTo(o2.stuData[3]);
			}
		});
		dataInput.close();
		dataInput = new Scanner(new File("data2.txt"));

		dataInput.nextLine();
		while (dataInput.hasNext()) {
			for (int i = 0; i < 4; i++) {
				tempStudentData[i] = dataInput.next();
			}
			join_data2
					.add(new StudentData(tempStudentData[0],
							tempStudentData[1], tempStudentData[2],
							tempStudentData[3]));
		}
		Collections.sort(join_data2, new Comparator<StudentData>() {
			public int compare(StudentData o1, StudentData o2) {
				return o1.stuData[3].compareTo(o2.stuData[3]);
			}
		});
		for (StudentData d1 : join_data) {
			flag = false;
			for (StudentData d2 : join_data2) {
				tempd1 = Integer.parseInt(d1.stuData[3]);
				tempd2 = Integer.parseInt(d2.stuData[3]);

				if (d1.stuData[3].equalsIgnoreCase(d2.stuData[3])) {
					flag = true;
					for (int y = 0; y < FieldTF1.length; y++) {
						if (FieldTF1[y] == 1) {
							System.out.print(d1.stuData[y] + "\t");
							fw.write(d1.stuData[y] + "\t");
						}
					}
					for (int x = 0; x < FieldTF2.length; x++) {
						if (FieldTF2[x] == 1) {
							System.out.print(d2.stuData[x] + "\t");
							fw.write(d2.stuData[x] + "\t");
						}
					}
					System.out.println();
					fw.write("\n");
					// break;
				} else if ((tempd1 - tempd2) < 0) {
					break;
				}
			}
			/*if (flag == false) {
				for (int y = 0; y < FieldTF1.length; y++) {
					if (FieldTF1[y] == 1) {
						System.out.print(d1.stuData[y] + "\t");
						fw.write(d1.stuData[y] + "\t");
					}
				}
				System.out.println();
				fw.write("\n");
			}*/
		}
		dataInput.close();

	}

	public static void openInstructFile() {
		try {
			instructInput = new Scanner(new File("instruct.txt"));
		} catch (FileNotFoundException filesNotFoundException) {
			System.err.println("Error opening file.");
			System.exit(1);
		}
	}

	public static void closeFile() throws IOException {
		if (dataInput != null)
			dataInput.close();
		if (instructInput != null)
			instructInput.close();
		if (fw != null)
			fw.close();
	}

	public static void chkdata() {
		chk = dataInput.nextLine();
		Pattern pat = Pattern.compile(" +");
		dataraw = pat.split(chk);
		tmpdata = new String[chk.length()];
	}

	public static void dataFrom() {
		Pattern pat = Pattern.compile(",");
		SpiltedFrom = pat.split(SpiltedString[fromStart]);
		for (int i = 0; i < SpiltedFrom.length; i++)
			if (SpiltedFrom[i].equals("data")) {
				openDataFile();
			} else {
				openData2File();
			}
		chkdata();
	}

	public static void spiltString() throws IOException {
		try {
			s = instructInput.nextLine();
			Pattern pat = Pattern.compile(" +"); // 建立要比對的pattern
			SpiltedString = pat.split(s); // 將s字串分解
			tmpString = new String[SpiltedString.length];
			whereEnd = SpiltedString.length;
			if (SpiltedString[0].equalsIgnoreCase("undo")) {
				recover();
			} else {
				// System.out.println("原始字串為： " + s);
				for (int i = 0; i < SpiltedString.length; i++) {
					if (SpiltedString[i].matches(">=")) {
						SpiltedString[i] = SpiltedString[i].replaceAll(">=",
								"+");
					} else if (SpiltedString[i].matches("<=")) {
						SpiltedString[i] = SpiltedString[i].replaceAll("<=",
								"-");
					} else if (SpiltedString[i].matches("!=")) {
						SpiltedString[i] = SpiltedString[i].replaceAll("!=",
								"*");
					} else if (SpiltedString[i].matches("[Oo][Rr]")) {
						SpiltedString[i] = SpiltedString[i].replaceAll(
								"[Oo][Rr]", "|");
					} else if (SpiltedString[i].matches("[Aa][Nn][Dd]")) {
						SpiltedString[i] = SpiltedString[i].replaceAll(
								"[Aa][Nn][Dd]", "&");
					} else if (SpiltedString[i].matches("where")) {
						whereStart = i + 1;

					} else if (SpiltedString[i].matches("order")) {
						whereEnd = i;
						orderStart = i + 2;
					} else if (SpiltedString[i].matches("DESC")) {
						DESC = true;

					} else if (SpiltedString[i].matches("from")) {
						fromStart = i + 1;
					} else if (SpiltedString[i].matches("join")) {
						joinFlag = true;
						resultfile();
						jointable();
					} else if (SpiltedString[i].equalsIgnoreCase("distinct")) {
						isDISTINCT = true;
					}
				}
				dataFrom();
				if (SpiltedString[0].equalsIgnoreCase("delete")) {
					type = 1;
					resultfile();
					Delete();
				} else if (SpiltedString[0].equalsIgnoreCase("select")) {
					type = 2;
					if (!joinFlag) {
						resultfile();
						Select();
					}
				}
			}
		} catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed.");
			instructInput.close();
			System.exit(1);
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file.");
			System.exit(1);
		}
	}

	public static void Select() throws IOException {
		// System.out.println("Select");
		tableStart = 1;
		Datastate = new int[dataraw.length];
		if (isDISTINCT)
			tableStart = 2;
		if (!SpiltedString[tableStart].equals("*")) {
			Pattern pat = Pattern.compile(","); // 建立要比對的pattern
			SpiltedSelecttable = pat.split(SpiltedString[tableStart]); // 將s字串分解
			Arrays.fill(Datastate, 0);
			for (int i = 0; i < SpiltedSelecttable.length; i++) {
				for (int j = 0; j < dataraw.length; j++) {
					if (SpiltedSelecttable[i].equals(dataraw[j])) {
						Datastate[j] = 1;
						DISTINCTtype = j;
					}
				}
			}
		} else {
			Arrays.fill(Datastate, 1);
		}

		for (int i = 0; i < dataraw.length; i++) {
			if (Datastate[i] == 1) {
				fw.write(dataraw[i] + "      ");
				System.out.print(dataraw[i] + "      ");
			}
		}
		fw.write("\n");
		System.out.println();

		readData();
		closeFile();
	}

	public static void recover() throws IOException {
		String recoverString;
		Scanner recoverdataInput;
		recoverdataInput = new Scanner(new File("result.txt"));
		FileWriter recoverfw;
		recoverfw = new FileWriter(new File("resulta.txt"));
		recoverString = recoverdataInput.nextLine();// 掠過第一行
		recoverfw.write(recoverString + "\n");
		while (recoverdataInput.hasNextLine()) {
			recoverString = recoverdataInput.nextLine();
			if (recoverString.matches("[*].*")) {
				recoverString = recoverString.replace("*", "");
			}
			recoverfw.write(recoverString + "\n");
		}
		recoverdataInput.close();
		recoverfw.close();
	}

	public static void orderBy() throws IOException {
		String[] obtemp = new String[dataraw.length];
		Pattern pat = Pattern.compile(","); // 建立要比對的pattern
		int k = 0;
		SpiltedOrderby = pat.split(SpiltedString[orderStart]); // 將s字串分解

		for (int i = 0; i < dataraw.length; i++) {
			if (SpiltedOrderby[0].equals(dataraw[i])) {
				orderType = i;
				break;
			}
		}
		if (DESC) {// DESC
			Collections.sort(m_data, new Comparator<StudentData>() {
				public int compare(StudentData o1, StudentData o2) {
					return o2.stuData[orderType]
							.compareTo(o1.stuData[orderType]);
				}
			});
		} else {// ASC
			Collections.sort(m_data, new Comparator<StudentData>() {
				public int compare(StudentData o1, StudentData o2) {
					return o1.stuData[orderType]
							.compareTo(o2.stuData[orderType]);
				}
			});
		}
		if (SpiltedOrderby.length > 1) {
			for (int i = 1; i < SpiltedOrderby.length; i++) {

				for (int j = 0; j < dataraw.length; j++) {
					if (SpiltedOrderby[i].equals(dataraw[j])) {
						tempordertype = j;
						break;
					}
				}

				for (StudentData c : m_data) {
					if (k == 0) {
						for (int j = 0; j < dataraw.length; j++) {
							obtemp[j] = c.stuData[j];
						}
						temp_data.add(new StudentData(obtemp[0], obtemp[1],
								obtemp[2], obtemp[3]));
						k++;
						continue;
					} else {
						if (c.stuData[orderType].equals(obtemp[orderType])) {
							temp_data.add(new StudentData(c.stuData[0],
									c.stuData[1], c.stuData[2], c.stuData[3]));

						} else {
							// //

							if (DESC) {// DESC
								Collections.sort(temp_data,
										new Comparator<StudentData>() {
											public int compare(StudentData o1,
													StudentData o2) {
												return o2.stuData[tempordertype]
														.compareTo(o1.stuData[tempordertype]);
											}
										});
							} else {// ASC
								Collections.sort(temp_data,
										new Comparator<StudentData>() {
											public int compare(StudentData o1,
													StudentData o2) {
												return o1.stuData[tempordertype]
														.compareTo(o2.stuData[tempordertype]);
											}
										});
							}
							temp_data.add(new StudentData(c.stuData[0],
									c.stuData[1], c.stuData[2], c.stuData[3]));
							// System.out.println(c.stuData[0]+"\t"+
							// c.stuData[1]+"\t"+c.stuData[2]);

							for (StudentData re : temp_data) {
								Result_data.add(new StudentData(re.stuData[0],
										re.stuData[1], re.stuData[2],
										c.stuData[3]));
								// System.out.println(re.stuData[0]+"\t"+re.stuData[1]+"\t"+re.stuData[2]+"\t");
							}
							k = 0;
							temp_data.clear();
						}
					}

				}
				for (StudentData c : Result_data) {
					for (int m = 0; m < dataraw.length; m++) {
						if (Datastate[m] == 1) {
							System.out.print(c.stuData[m] + "\t");
							fw.write(c.stuData[m] + "\t");
						}
					}
					System.out.println();
					fw.write("\n");
				}
			}
		} else {

			for (StudentData c : m_data) {
				for (int m = 0; m < dataraw.length; m++) {
					if (Datastate[m] == 1) {
						System.out.print(c.stuData[m] + "\t");
						fw.write(c.stuData[m] + "\t");
					}
				}
				System.out.println();
				fw.write("\n");
			}
		}
	}

	public static int priority(char c) {// '+' -> ">=" '-' -> "<=" '*' ->
		// "!=" '/'
		// -> "!"
		return c == '>' || c == '<' || c == '=' || c == '+' || c == '-'
				|| c == '*' ? 1
				: c == '|' || c == '&' || c == '/' || c == '!' ? 2 : 0;
	}

	public static String toPostfix(String infix) {
		String expr = infix;
		LinkedList<Character> stack = new LinkedList<>();
		StringBuilder output = new StringBuilder();
		char toStack = '(';
		char toOutput = ')';
		for (char c : expr.toCharArray()) {
			if (c == toStack) {
				stack.add(c);
			} else if ("><=|&+-*/!".indexOf(c) != -1) {
				while (!stack.isEmpty()
						&& priority(stack.getLast()) >= priority(c)) {
					output.append(stack.removeLast());
				}
				output.append(',');
				stack.add(c);

			} else if (c == toOutput) {
				while (stack.getLast() != toStack) {
					output.append(stack.removeLast());
				}
				stack.removeLast();
			} else {
				output.append(c);
			}
		}
		while (!stack.isEmpty()) {
			output.append(stack.removeLast());
		}
		// System.out.println(output.toString());
		return output.toString();
	}

	public static Integer cal(char op, String p1, String p2) {
		int tempp1 = 0, tempp2 = 0;
		if (p1.matches("[a-zA-Z\u4e00-\u9fff]+")
				&& p2.matches("[a-zA-Z\u4e00-\u9fff]+")) {
			switch (op) {
			case '=':
				return p1.equals(p2) ? 1 : 0;
			case '*':
				return !(p1.equals(p2)) ? 1 : 0;
			default:
				throw new ArithmeticException(op + " not defined");
			}
		} else if (p1.matches("[\\d]+") && p2.matches("[\\d]+")) {
			tempp1 = Integer.valueOf(p1);
			tempp2 = Integer.valueOf(p2);
			switch (op) {

			case '>':
				return tempp1 > tempp2 ? 1 : 0;
			case '<':
				return tempp1 < tempp2 ? 1 : 0;
			case '=':
				return tempp1 == tempp2 ? 1 : 0;
			case '+':
				return tempp1 >= tempp2 ? 1 : 0;
			case '-':
				return tempp1 <= tempp2 ? 1 : 0;
			case '*':
				return tempp1 != tempp2 ? 1 : 0;
			case '&':
				return (tempp1 == 1 && tempp2 == 1) ? 1 : 0;
			case '|':
				return (tempp1 == 1 || tempp2 == 1) ? 1 : 0;
			default:
				throw new ArithmeticException(op + " not defined");
			}
		}
		return 0;
	}

	public static String eval(String expr) {
		String evaltmp = "", evaltmp2 = "";
		String p2;
		String p1;
		LinkedList<String> stack = new LinkedList<>();
		for (char c : toPostfix(expr).toCharArray()) {
			if (c == '!') {
				p1 = stack.removeLast();
				stack.add(p1.equals("1") ? "0" : "1");
				continue;
			}
			if (c == ',') {
				if (evaltmp2 != "")
					stack.add(evaltmp2);
				evaltmp2 = "";
				continue;
			}
			if ("><=+-*&|".indexOf(c) != -1) {
				if (evaltmp2 != "") {
					stack.add(evaltmp2);
					evaltmp2 = "";
				}
				p2 = stack.removeLast();
				p1 = stack.removeLast();
				evaltmp = Integer.toString(cal(c, p1, p2));
				stack.add(evaltmp);
			} else if (c != ' ') {
				evaltmp2 = evaltmp2 + c;
			}
		}
		return stack.getLast();
	}

	public static void Tovar() {

		for (int i = 0; i < tmpString.length; i++) {
			tmpString[i] = SpiltedString[i];
			if (tmpString[i].matches(dataraw[0])) {
				tmpString[i] = tmpString[i].replaceAll(dataraw[0], tmpdata[0]);
			}
			if (tmpString[i].matches(dataraw[1])) {
				tmpString[i] = tmpString[i].replaceAll(dataraw[1], tmpdata[1]);
			}
			if (tmpString[i].matches(dataraw[2])) {
				tmpString[i] = tmpString[i].replaceAll(dataraw[2], tmpdata[2]);
			}
			if (tmpString[i].matches(dataraw[3])) {
				tmpString[i] = tmpString[i].replaceAll(dataraw[3], tmpdata[3]);
			}
		}
		tmptovarString = "";
		for (int i = whereStart; i < whereEnd; i++) {
			tmptovarString = tmptovarString + tmpString[i];

		}
	}

	public static void readData() throws IOException {
		try {
			while (dataInput.hasNext()) {
				tmpdata[0] = (dataInput.next());
				tmpdata[1] = (dataInput.next());
				tmpdata[2] = (dataInput.next());
				tmpdata[3] = (dataInput.next());
				if ((tmpdata[0].matches("[*].*")))
					continue;
				if (whereStart != 0)
					Tovar();
				if (type == 1) {// Delete
					if (Integer.valueOf(eval(tmptovarString)) == 0) {
						fw.write(tmpdata[0] + "      " + tmpdata[1] + "      "
								+ tmpdata[2] + "      " + tmpdata[3] + "\n");
						System.out.printf("%-10s%-10s%-10s%-10s\n", tmpdata[0],
								tmpdata[1], tmpdata[2], tmpdata[3]);

					} else {
						fw.write("*" + tmpdata[0] + "      " + tmpdata[1]
								+ "      " + tmpdata[2] + "      " + tmpdata[3]
								+ "\n");
						System.out.printf("*%-10s%-10s%-10s%-10s\n",
								tmpdata[0], tmpdata[1], tmpdata[2], tmpdata[3]);
					}
				} else if (type == 2) {// select
					if (whereStart != 0 && orderStart == 0) {// select with
																// where
																// condition
						if (Integer.valueOf(eval(tmptovarString)) == 1) {
							resultrecords++;
							for (int i = 0; i < dataraw.length; i++) {
								if (Datastate[i] == 1) {
									fw.write(tmpdata[i] + "      ");
									System.out.printf("%-10s", tmpdata[i]);
								}
							}
							fw.write("\n");
							System.out.println();
						}
					} else if (whereStart != 0 && orderStart != 0) {// select
																	// with
																	// where and
																	// orderby
						if (Integer.valueOf(eval(tmptovarString)) == 1) {
							resultrecords++;
							m_data.add(new StudentData(tmpdata[0], tmpdata[1],
									tmpdata[2], tmpdata[3]));
						}
					} else {
						if (orderStart == 0 && !isDISTINCT) {// select without
																// order by nor
							// where
							resultrecords++;
							for (int i = 0; i < dataraw.length; i++) {
								if (Datastate[i] == 1) {
									fw.write(tmpdata[i] + "      ");
									System.out.printf("%-10s", tmpdata[i]);
								}
							}
							fw.write("\n");
							System.out.println();
						} else if (isDISTINCT) {// select without where but with
												// order by
							resultrecords++;

							for (int q = 0; q < DISnum; q++) {
								if (tmpdata[DISTINCTtype]
										.equals(Distincttemp[q])) {
									DISTINCTFLAG = true;
									break;
								}
							}
							if (!DISTINCTFLAG) {
								Distincttemp[DISnum] = tmpdata[DISTINCTtype];
								DISnum++;
								m_data.add(new StudentData(tmpdata[0],
										tmpdata[1], tmpdata[2], tmpdata[3]));
							}
							DISTINCTFLAG = false;
						} else {
							m_data.add(new StudentData(tmpdata[0], tmpdata[1],
									tmpdata[2], tmpdata[3]));
						}
					}
				}
			}
			if (isDISTINCT && orderStart == 0) {
				for (StudentData c : m_data) {
					System.out.println(c.stuData[DISTINCTtype]);
					fw.write(c.stuData[DISTINCTtype] + "\n");
				}
			}
			if (orderStart != 0)
				orderBy();
		} catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed.");
			dataInput.close();
			System.exit(1);
		} catch (IllegalStateException stateException) {
			System.err.println("Error reading from file.");
			System.exit(1);
		}
	}

	public static void Delete() throws IOException {
		Datastate = new int[dataraw.length];
		Arrays.fill(Datastate, 1);
		for (String v : dataraw) {
			fw.write(v + "      ");
			System.out.print(v + "      ");
		}
		fw.write("\n");
		System.out.println();
		readData();
		closeFile();
	}

	public static void main(String[] args) throws IOException {
		openDataFile();
		openInstructFile();
		spiltString();
	}
}
