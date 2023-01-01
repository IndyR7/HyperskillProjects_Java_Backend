class Problem {
    public static void main(String[] args) {
        boolean test = true;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("test")) {
                System.out.println(i);
                test = false;
            }
        }
        if (test) {
            System.out.println("-1");
        }
    }
}