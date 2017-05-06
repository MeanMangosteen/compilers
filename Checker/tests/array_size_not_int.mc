int b[1];

int main() {
	// this is okay
	b[1] = 1;
	// this is not okay
	b["1"] = 1;
}
