package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GraphicCardModel {
    int id;
    String name;
    int brandId;
    int memory;
    String brandName;
    int gpuFreq;
    int price;

    public int getGpuFreq() {
        return gpuFreq;
    }

    public void setGpuFreq(int gpuFreq) {
        this.gpuFreq = gpuFreq;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBrandId() {
        return brandId;
    }

    public int getMemory() {
        return memory;
    }

    public GraphicCardModel(int id, String name, int brandId, int memory, String brandName, int gpuFreq, int price) {
        this.setBrandName(brandName);
        this.setId(id);
        this.setName(name);
        this.setBrandId(brandId);
        this.setMemory(memory);
        this.setGpuFreq(gpuFreq);
        this.setPrice(price);
    }
}

