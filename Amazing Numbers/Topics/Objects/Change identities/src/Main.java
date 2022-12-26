class Person {
    String name;
    int age;
}

class MakingChanges {
    public static void changeIdentities(Person p1, Person p2) {
        String nameHelper = p1.name;
        p1.name = p2.name;
        p2.name = nameHelper;
        int ageHelper = p1.age;
        p1.age = p2.age;
        p2.age = ageHelper;

    }
}