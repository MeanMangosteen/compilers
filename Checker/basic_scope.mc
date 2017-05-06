int main() {
	int i = 5;
	i = 0;	// this should be fine
	j = 0; 	// this shoud not be fine
	{
		int i = 5;	// this should be fine
		int j = 5;	// this should be fine
	}

	j = 0;	// this should not be fine
}

void hello() {}
void hello() {}	// not okay

