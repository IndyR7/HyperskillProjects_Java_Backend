class Problem {
    public static void main(String[] args) {
        int result;
        switch (args[0]) {
            case "*":
                result = Integer.parseInt(args[1]) * Integer.parseInt(args[2]);
                break;
            case "+":
                result = Integer.parseInt(args[1]) + Integer.parseInt(args[2]);
                break;
            case "-":
                result = Integer.parseInt(args[1]) - Integer.parseInt(args[2]);
                break;
            default:
                System.out.println("Unknown operator");
                return;
        }
        System.out.println(result);
        System.out.println("\"Bolek_and_Lolek\"");
    }
}