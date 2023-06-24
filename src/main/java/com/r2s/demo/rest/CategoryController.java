package com.r2s.demo.rest;

import java.util.List;
import java.util.Map;import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.r2s.demo.DTO.CategoryDTOResponse;
import com.r2s.demo.Repository.CategoryRepository;
import com.r2s.demo.contains.ResponseCode;
import com.r2s.demo.model.Category;
import com.r2s.demo.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.ObjectUtils;

@RestController
@RequestMapping(path = "/category")
public class CategoryController extends BaseRestController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CategoryRepository categoryRepository;

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@GetMapping("")
	public ResponseEntity<?> getAllCategory() {
		// goi toi service lay category
		try {
			List<Category> categories = this.categoryService.getAllCategory();
			List<CategoryDTOResponse> categoryDTOResponses = categories.stream().map(category -> 
			new CategoryDTOResponse(category)).collect(Collectors.toList());
			return super.success(categoryDTOResponses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(),ResponseCode.NO_CONTENT.getMessage());
	}

	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	@PostMapping("")
	public ResponseEntity<?> addCategory(@RequestBody(required = true) Map<String, Object> newUser) {
		try {
			if (ObjectUtils.isEmpty(newUser)) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			if (ObjectUtils.isEmpty(newUser.get("name"))) {
				return super.error(ResponseCode.NO_PARAM.getCode(), ResponseCode.NO_PARAM.getMessage());
			}
			Category foundcaCategory = this.categoryRepository.findByName(newUser.get("name").toString()).orElse(null);
			if (!ObjectUtils.isEmpty(foundcaCategory)) {
				return super.error(ResponseCode.DATA_ALREADY_EXISTS.getCode(),
						ResponseCode.DATA_ALREADY_EXISTS.getMessage());
			}
			Category insertcategory = new Category();
			insertcategory.setName(newUser.get("name").toString());
			this.categoryRepository.save(insertcategory);
			if (!ObjectUtils.isEmpty(insertcategory)) {
				return super.success(new CategoryDTOResponse(insertcategory));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.error(ResponseCode.NO_CONTENT.getCode(), ResponseCode.NO_CONTENT.getMessage());

	}

}
