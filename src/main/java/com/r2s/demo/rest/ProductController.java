package com.r2s.demo.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.ProductDTOResponse;
import com.r2s.demo.Repository.CategoryRepository;
import com.r2s.demo.Repository.ProductRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Category;
import com.r2s.demo.model.Product;
import com.r2s.demo.service.ProductService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/product")
public class ProductController extends BaseRestController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/category/{category_id}")
	public ResponseEntity<?> getAllProductByCategoryId(@PathVariable("category_id") Long categoryId,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		try {
			Pageable paging = (Pageable) PageRequest.of(page, size);
			Page<Product> products = this.productService.getAllProductByCategoryId(categoryId, paging);
			List<ProductDTOResponse> productDTOResponses = products.stream().map(ProductDTOResponse::new).toList();
			return super.success(productDTOResponses);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getById")
	public ResponseEntity<?> getProductById(@RequestParam(name = "id") Long id) {
		try {
			Product foundProduct = this.productService.findProductById(id);
			if (ObjectUtils.isEmpty(foundProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			ProductDTOResponse productDTOResponse = new ProductDTOResponse(foundProduct);
			return super.success(productDTOResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.INTERNAL_SERVER.getCode(), ResponseCode.INTERNAL_SERVER.getMessage());
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addProduct(@RequestBody(required = false) Map<String, Object> newProduct) {
		try {
			if (ObjectUtils.isEmpty(newProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			if (ObjectUtils.isEmpty(newProduct.get("name")) || ObjectUtils.isEmpty(newProduct.get("categoryId"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			long categoryId = Long.parseLong(newProduct.get("categoryId").toString());
			Category foundCategory = this.categoryRepository.findById(categoryId).orElse(null);
			if (ObjectUtils.isEmpty(foundCategory)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product product = new Product();
			product.setName(newProduct.get("name").toString());
			//product.setId(categoryId);
			product.setCategory(foundCategory);
			this.productRepository.save(product);
			return super.success(product);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable long id, @RequestBody(required = false) 
			Map<String, Object> newProduct){
		try {
			if(ObjectUtils.isEmpty(newProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}
			if(ObjectUtils.isEmpty(newProduct.get("name"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Product foundProduct = this.productRepository.findById(id).orElse(null);
			if(ObjectUtils.isEmpty(foundProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			foundProduct.setName(newProduct.get("name").toString());
			this.productRepository.save(foundProduct);
			// tra ve doi tương product moi dc update
			return super.success(newProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	
	
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllProduct(){
		try {
			List<Product> products = this.productRepository.findAll();
			return super.success(products.stream().map(ProductDTOResponse::new).toList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

}