package sasti.dukaan.grocerystorecompany.model;

public class Products {



     private String p_name,p_description,p_image;

     private Long p_price, p_id;

    public Products() {
    }

    public Products(String p_name, String p_description, String p_image, Long p_price, Long p_id) {
        this.p_name = p_name;
        this.p_description = p_description;
        this.p_image = p_image;
        this.p_price = p_price;
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_description() {
        return p_description;
    }

    public void setP_description(String p_description) {
        this.p_description = p_description;
    }

    public String getP_image() {
        return p_image;
    }

    public void setP_image(String p_image) {
        this.p_image = p_image;
    }

    public Long getP_price() {
        return p_price;
    }

    public void setP_price(Long p_price) {
        this.p_price = p_price;
    }

    public Long getP_id() {
        return p_id;
    }

    public void setP_id(Long p_id) {
        this.p_id = p_id;
    }
}
