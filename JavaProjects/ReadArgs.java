class ReadArgs {
	public static void main(String[] args) {
		System.out.println("Program called with " + args.length + " arguments:");
		for (int counter = 0; counter < args.length; counter++) {
			System.out.println(args[counter]);
		}
	}
}

