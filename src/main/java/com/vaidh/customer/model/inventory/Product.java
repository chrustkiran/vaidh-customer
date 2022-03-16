package com.vaidh.customer.model.inventory;

import com.vaidh.customer.dto.CommonResults;
import com.vaidh.customer.model.enums.ProductCategory;
import com.vaidh.customer.model.enums.ProductStatus;
import com.vaidh.customer.model.enums.ProductUnit;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements CommonResults {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;
    private String name;

    @Enumerated(EnumType.STRING)
    private ProductCategory productCategory;

    @Enumerated(EnumType.STRING)
    private ProductUnit productUnit;
    private Double price;
    private String imageUrl;
    @Enumerated(EnumType.STRING)
    private ProductStatus productStatus;

    private String companyName;

    @Column(length = Integer.MAX_VALUE)
    private String description;

    public Product() {}

    public Product(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductUnit getProductUnit() {
        return productUnit;
    }

    public void setProductUnit(ProductUnit productUnit) {
        this.productUnit = productUnit;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ProductStatus getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getProductId() {
        return productId;
    }
}
