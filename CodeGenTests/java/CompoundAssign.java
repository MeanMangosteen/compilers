public class CompoundAssign{
	static int j = 2;
	static int k[] = new int [3];
	public CompoundAssign() {}

	public static void main(String argv[]) {
		CompoundAssign vc$;
		vc$ = new CompoundAssign();

		{
			int i;
			int j;
			int k;

			i = j = k = 5;
			System.out.println(i);
			System.out.println(j);
			System.out.println(k);
		}
		System.out.println(j);
		j = k[2] = 5;
		System.out.println(j);
		System.out.println(k[2]);
		

		return;
	}
}
