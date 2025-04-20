package com.demo.model;


import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Products")
public class Products {

	// 物件屬性
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "product_name")
	@NotBlank(message = "商品名稱不能為空")
	private String name;

	@Column(name = "product_desc")
	private String desc;

	@Column(name = "needmiles")
	@NotNull(message = "商品兌換所需里程不能為空")
    @Min(value = 0, message = "里程不能小於 0")
	private Integer needmiles;

	@Column(name = "quantity")
	@Min(value = 0, message = "庫存不能為負數")
	private Integer quantity;

	//這是圖片儲存路徑
	@Column(name = "product_image")
	private String image;

	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	@JoinColumn(name = "category_id", referencedColumnName = "category_id")
	private Category category;

	


}
