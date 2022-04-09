package sample;

public class BrandModel {
    int id;
    String brandName;
    int status;

    public BrandModel(int id, String brandName, int status) {
        this.id = id;
        this.brandName = brandName;
        this.status = status;
    }

    public String getStatus() {
        if(status == 1 ){
            return "Импортное";
        }
        if(status == 0){
            return "НАШЕ";
        }
        return null;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

}
