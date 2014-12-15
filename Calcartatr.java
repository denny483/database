import java.util.LinkedList;

public class Calcartatr {

	public static int priority(char c) {// '+' -> ">=" '-' -> "<=" '*' ->
										// "!=" '/'
		// -> "!"
		return c == '>' || c == '<' || c == '=' || c == '+' || c == '-'
				|| c == '*' ? 1 : c == '|' || c == '&' || c == '/' ? 2 : 0;
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
			} else if ("><=|&+-*/".indexOf(c) != -1) {
				while (!stack.isEmpty()
						&& priority(stack.getLast()) >= priority(c)) {
					output.append(stack.removeLast());
				}
				// stack.add(' ');
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
		} else if (p1.matches("[\\d]+") || p2.matches("[\\d]+")) {
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
				return tempp1 == 1 && tempp2 == 1 ? 1 : 0;
			case '|':
				return tempp1 == 1 || tempp2 == 1 ? 1 : 0;
			default:
				throw new ArithmeticException(op + " not defined");
			}
		}
		return 0;
	}

	public static String eval(String expr) {
		String evaltmp = "", evaltmp2 = "";
		LinkedList<String> stack = new LinkedList<>();
 		for (char c : toPostfix(expr).toCharArray()) {
			if (c == ',') {
				//isnext = true;
				stack.add(evaltmp2);
				evaltmp2="";
 				continue;

			}
			if ("><=+-*&|".indexOf(c) != -1) {
				stack.add(evaltmp2);
				String p2 = stack.removeLast();
				String p1 = stack.removeLast();
				evaltmp = "" + cal(c, p1, p2);
				stack.add(evaltmp);
			} else if (c != ' ') {	
					evaltmp2 = evaltmp2 + c;
			}

		}
		return stack.getLast();
	}

	public static void main(String arg[]) {
		System.out.println(eval("123=123"));
	}
}