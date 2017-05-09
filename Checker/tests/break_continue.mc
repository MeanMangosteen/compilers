int main() {
	if (true) {
		while (true) {
		}
		// not okay
		continue;
		break;
	}

	while(true)
		continue;	// okay

	for(;true;) 
		break;		// okay

	// not okay
	break;
}
