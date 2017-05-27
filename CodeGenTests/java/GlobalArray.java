public class GlobalArray{
	static int a[] = {1,2,3,4};
	static float b[] = new float[3];
	static boolean c[] = {};

	public GlobalArray() {}

	public static void main(String argv[]) {
		GlobalArray vc$;
		vc$ = new GlobalArray();

		b[0] = 3.1F;

		for(int i=0; i<2; i++) {
			System.out.println(b[i]);
		}

		for (int i=0; i<4; i++) {
			System.out.println(a[i]);
		}


		return;
	}
}
