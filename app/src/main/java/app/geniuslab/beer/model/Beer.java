package app.geniuslab.beer.model;

public class Beer {
    private int id ;
    private String name;
    private String price;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image ;

    public Beer(int id, String name, String price,String image) {
    }

    public int getId() {
        return 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
