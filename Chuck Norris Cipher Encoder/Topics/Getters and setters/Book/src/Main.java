class Book {

    private String title;
    private int yearOfPublishing;
    private String[] authors;

    public String getTitle() {
        return this.title;
    }
    public int getYearOfPublishing() {
        return this.yearOfPublishing;
    }
    public String[] getAuthors() {
        return this.authors;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthors(String[] authors) {
        this.authors = authors;
    }
    public void setYearOfPublishing(int yearOfPublishing) {
        this.yearOfPublishing = yearOfPublishing;
    }
}