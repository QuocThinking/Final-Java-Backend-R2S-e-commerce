package com.r2s.demo.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.VariantProductDTOResponse;
import com.r2s.demo.Repository.ProductRepository;
import com.r2s.demo.Repository.VariantProductRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Product;
import com.r2s.demo.model.VariantProduct;
import com.r2s.demo.service.VariantProductService;

@RestController
@RequestMapping(path = "/variantProduct")
public class VariantProductController extends BaseRestController {
	@Autowired
	private VariantProductService variantProductService;

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private VariantProductRepository variantProductRepository;

//	@GetMapping("/variantProduct")
//	public ResponseEntity<?> getVariantProductByParams(@RequestParam(value = "variant_id") Long variantId,
//			@RequestParam(value = "name") String name){
//		try {
//			List<VariantProduct> variantProducts = null;
//			if(variantId != null) {
//				VariantProduct variantProduct = this.variantProductService.getVariantProductById(variantId);
//				if(variantProduct != null) {
//				variantProducts = new ArrayList<>();
//				variantProducts.add(variantProduct);
//				}
//				else if(name != null) {
//				variantProducts = this.variantProductService.getVariantProductByName(name);
//			}else {
//				 return super.error(ResponseCode.BAD_REQUEST.getCode(), "At least one of the parameters is required.");
//	        }
//	        if (variantProducts == null) {
//	            return super.error(ResponseCode.NOT_FOUND.getCode(), "Variant product not found.");
//	        }
//	        List<VariantProductDTOResponse> variantProductDTOResponses = variantProducts.stream().map(VariantProductDTOResponse::new).toList();
//	        return super.success(variantProductDTOResponses);
//		}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//	}
//		 return super.error(ResponseCode.INTERNAL_SERVER.getCode(), ResponseCode.INTERNAL_SERVER.getMessage());
//}
	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("")
	public ResponseEntity<?> getAllVariantProduct() {
		try {
			List<VariantProduct> variantProducts = this.variantProductService.getAllVariantProduct();
			List<VariantProductDTOResponse> variantProductDTOResponses = variantProducts.stream()
					.map(VariantProductDTOResponse::new).toList();
			return super.success(variantProductDTOResponses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@GetMapping("/getvariantbyname")
	public ResponseEntity<?> getVariantProductByIdName(@RequestParam String name) {
		try {
			List<VariantProduct> variantProducts = this.variantProductService.findByName("%" + name + "%");
			List<VariantProductDTOResponse> variantProductDTOResponses = variantProducts.stream()
					.map(VariantProductDTOResponse::new).toList();
			return super.success(variantProductDTOResponses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addVariantProduct(@RequestBody(required = false) Map<String, Object> newvariantProduct) {
		try {
			if (ObjectUtils.isEmpty(newvariantProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			if (ObjectUtils.isEmpty(newvariantProduct.get("productId"))
					|| ObjectUtils.isEmpty(newvariantProduct.get("name"))
					|| ObjectUtils.isEmpty(newvariantProduct.get("model_year"))
					|| ObjectUtils.isEmpty(newvariantProduct.get("price"))
					|| ObjectUtils.isEmpty(newvariantProduct.get("color"))
					|| ObjectUtils.isEmpty(newvariantProduct.get("size"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			// tim doi tuong productid trong variantProduct
			Long productId = Long.parseLong(newvariantProduct.get("productId").toString());
			Product foundProduct = this.productRepository.findById(productId).orElse(null);
			if (ObjectUtils.isEmpty(foundProduct)) {
				return super.error(ResponseCode.NOT_FOUND.getCode(), ResponseCode.NOT_FOUND.getMessage());
			}

			VariantProduct variantProduct = new VariantProduct();
			variantProduct.setColor(newvariantProduct.get("color").toString());
			variantProduct.setModel_year(Integer.parseInt(newvariantProduct.get("model_year").toString()));
			variantProduct.setName(newvariantProduct.get("name").toString());
			variantProduct.setSize(newvariantProduct.get("size").toString());
			variantProduct.setPrice(Double.parseDouble(newvariantProduct.get("price").toString()));
			//luu doi tuong product id vào variant
			variantProduct.setProduct(foundProduct);
			this.variantProductRepository.save(variantProduct);
			return super.success(variantProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PutMapping("/{id}")
	public ResponseEntity<?> updateVariantProduct(@PathVariable long id,
			@RequestBody(required = false) Map<String, Object> newProduct) {
		try {
			if (ObjectUtils.isEmpty(newProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			if (ObjectUtils.isEmpty(newProduct.get("name")) || ObjectUtils.isEmpty(newProduct.get("model_year"))
					|| ObjectUtils.isEmpty(newProduct.get("price")) || ObjectUtils.isEmpty(newProduct.get("color"))
					|| ObjectUtils.isEmpty(newProduct.get("size"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			// tim product id co ton tai ko
			VariantProduct variantProduct = this.variantProductRepository.findById(id).orElse(null);
			if (ObjectUtils.isEmpty(variantProduct)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}

			variantProduct.setName(newProduct.get("name").toString());
			variantProduct.setColor(newProduct.get("color").toString());
			variantProduct.setModel_year(Integer.parseInt(newProduct.get("model_year").toString()));
			variantProduct.setPrice(Double.parseDouble(newProduct.get("price").toString()));
			variantProduct.setSize(newProduct.get("size").toString());
			this.variantProductRepository.save(variantProduct);
			return super.success(variantProduct);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());
	}

}
