package nguyenlexuantung15026121.finalexam.models;

public class Product {
    private int productID;
    private String productName;
    private String productSize;
    private int productAmount;
    private int producerID;

    public Product() {
    }

    public Product(int productID, String productName, String productSize, int productAmount, int producerID) {
        this.productID = productID;
        this.productName = productName;
        this.productSize = productSize;
        this.productAmount = productAmount;
        this.producerID = producerID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProducerID() {
        return producerID;
    }

    public void setProducerID(int producerID) {
        this.producerID = producerID;
    }

    public int getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount = productAmount;
    }

    @Override
    public String toString() {
        return "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productAmount=" + productAmount;
    }
}
