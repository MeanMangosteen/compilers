public class ArrayParam{
	public ArrayParam() {}

	void f(float i[]) {
		System.out.println(i[0]);
		return;
	}

	public static void main(String argv[]) {
		ArrayParam vc$;
		vc$ = new ArrayParam();
		
		float j[] = {2.1F, 5.6F};
		vc$.f(j);

		return;
	}
}
