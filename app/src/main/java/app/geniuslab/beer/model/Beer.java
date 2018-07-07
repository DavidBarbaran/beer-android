package app.geniuslab.beer.model;
import java.io.Serializable;

public class Beer implements Serializable{
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


    public Beer(int id, String name, String image, String price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }
    public Beer() {

    }

    public int getId() {
        return id;
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
