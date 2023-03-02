import java.util.*;

class Date {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String date = scn.nextLine();
        String dateRegex = "(([0-2][0-9]|3[0-1])|(19|20)[0-9][0-9])" +
                "([-/.])(0[1-9]|1[0-2])" +
                "([-/.])(([0-2][0-9]|3[0-1])|(19|20)[0-9][0-9])";
        System.out.println(date.matches(dateRegex) ? "Yes" : "No");
    }
}