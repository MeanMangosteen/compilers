int main() {
	boolean a = true;
	int b = 1;

	// this is okay
	if (a) {
		// not okay
		break;
		continue;
	}

	// this is not
	if (b) {
		// not okay
		break;
		continue;
	}

	// this is not
	if (1) {
	}

	// this is okay
	while(a) {
		// okay
		break;
		continue;
	}

	// this in not
	while(b) {
	}

	// this is not
	while(0) {
		// okay
		break;
		continue;
	}

	// this is okay
	for(;a;) {
		// okay
		break;
		continue;
	}
	
	// this is not
	for(;b;){
	}

	// this is not
	for(;1;) {
		// okay
		break;
		continue;
	}

	// not okay
	break;
	continue;
}
