package in.eureka.catalogue.dto;

import in.eureka.jpa.enums.Category;

public class CatalogueDto {

	private int id;

	private String name;

	private String description;

	private Category category;

	private Double sellingPrice;
	
	private Double strikePrice;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Double getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(Double sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public Double getStrikePrice() {
		return strikePrice;
	}

	public void setStrikePrice(Double strikePrice) {
		this.strikePrice = strikePrice;
	}

	@Override
	public String toString() {
		return "CatalogueDto [id=" + id + ", name=" + name + ", description=" + description + ", category=" + category
				+ ", sellingPrice=" + sellingPrice + ", strikePrice=" + strikePrice + "]";
	}
	
}
