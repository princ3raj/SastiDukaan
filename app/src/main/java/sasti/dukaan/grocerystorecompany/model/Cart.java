package sasti.dukaan.grocerystorecompany.model;

public class Cart {


    private String product_name, product_quantity, product_image;
    private Long product_price, product_id;

    public Cart() {
    }

    public Cart(String product_name, String product_quantity, String product_image, Long product_price, Long product_id) {
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_image = product_image;
        this.product_price = product_price;
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public Long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Long product_price) {
        this.product_price = product_price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }
}
