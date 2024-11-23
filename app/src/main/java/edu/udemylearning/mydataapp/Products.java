package edu.udemylearning.mydataapp;

public class Products {
    private String _productname;
    private int _id;

    public Products(String productname) {
        this._productname = productname;
    }

    public void set_productname(String _productname) {
        this._productname = _productname;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_productname() {
        return _productname;
    }

    public int get_id() {
        return _id;
    }
}
