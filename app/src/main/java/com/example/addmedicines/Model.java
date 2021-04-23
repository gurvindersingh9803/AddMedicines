package com.example.addmedicines;

public class Model {

    String medName,composition,companyName,mImageUrl,category;;
    int mrp,ptr;

    public Model(){
    }

    public int getMrp() {
        return mrp;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public int getPtr() {
        return ptr;
    }

    public void setPtr(int ptr) {
        this.ptr = ptr;
    }

    public Model(String medName, String mImageUrl, String composition, String companyName, String category, int mrp, int ptr){

        this.medName = medName;
        this.composition = composition;
        this.companyName = companyName;
        this.mrp = mrp;
        this.ptr = ptr;
        this.mImageUrl = mImageUrl;
        this.category = category;


    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getMedName() {
        return medName;
    }

    public void setMedName(String medName) {
        this.medName = medName;
    }

    public String getComposition() {
        return composition;
    }

    public void setComposition(String composition) {
        this.composition = composition;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


}
