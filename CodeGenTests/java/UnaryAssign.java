public class UnaryAssign{
	public UnaryAssign() {}

	public static void main(String argv[]) {
		UnaryAssign vc$;
		vc$ = new UnaryAssign();

		int i = 1;
		int j;
		boolean k = false;
		boolean l;
	
		j = -i;
		j = +j;
		l = !k;

		System.out.println("j: " + j + " l: " + l);

		return;
	}
}
