package com.amazing.shop.service;

import com.amazing.shop.dto.CategoryRegistrationModel;
import com.amazing.shop.entity.Category;
import com.amazing.shop.repository.CategoryRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> findByName(String name) {

        return categoryRepository.findByName(name);
    }

    public Category save(CategoryRegistrationModel registrationModel) {
        Category category = new Category();
        category.setId(registrationModel.getId());
        category.setName(registrationModel.getName());
        return categoryRepository.save(category);
    }

    private Collection<Category> remapCategories(Collection<String> categories) {
        return categories.stream()
                .map(Category::new)
                .collect(toSet());
    }

    public Category loadCategoryByName(String name) throws NotFoundException {
        Category category = categoryRepository.findByName(name).orElse(null);
        if (category == null) {
            throw new NotFoundException ("Category not found.");
        }
        return category;
    }

    public List<Category> findAll() {

        return categoryRepository.findAll();
    }
}
