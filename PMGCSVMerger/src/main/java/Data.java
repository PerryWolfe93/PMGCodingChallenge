public class Data {

    private final String hash;
    private final String category;
    private final String filename;

    public Data(String hash, String category, String filename) {
        this.hash = hash;
        this.category = category;
        this.filename = filename;
    }

    public String getHash() {
        return hash;
    }

    public String getCategory() {
        return category;
    }

    public String getFilename() {
        return filename;
    }
}
