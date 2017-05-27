public class WhileBreak{
	public WhileBreak() {}

	public static void main(String argv[]) {
		WhileBreak vc$;
		vc$ = new WhileBreak();

		int i = 0;

		while (i != 10) {
			System.out.println(i);
			
			if (i == 5) {
				break;
			}
			i = i + 1;
		}

		return;
	}
}
