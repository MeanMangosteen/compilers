void f(int a, float b) {
}


int main() {
	
	float c = 1.0;
	int d = 5;

	// too few args
	f(d);

	// too few and wrong type
	f(c);

	// correct number wrong type
	f(c, d);

	// too many args 
	f(d, c, "hi");

	// this is correct usage
	f(d,c );

	return 0;
}
