package com.amazing.shop.controller;

import com.amazing.shop.dto.CategoryRegistrationModel;
import com.amazing.shop.entity.Category;
import com.amazing.shop.repository.CategoryRepository;
import com.amazing.shop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @ModelAttribute("category")
    public CategoryRegistrationModel categoryRegistrationModel() {
        return new CategoryRegistrationModel();
    }

    @GetMapping
    public String showCategoriesForm() {
        return "category";
    }

    @PostMapping
    public String add(@ModelAttribute("category") @Valid CategoryRegistrationModel registrationModel,
                           BindingResult result) {
        Category existing = categoryService.findByName(registrationModel.getName()).orElse(null);
        if (existing != null
                && registrationModel.getId() == null) {
            result.rejectValue("name", ""
                    , "There is already category with that name!");
        }

        if (result.hasErrors()) {
            return "category";
        }

        categoryService.save(registrationModel);
        return "redirect:/admin/category?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        Category existing = categoryService.findById(id).orElse(null);
        if (existing == null) {
            new RuntimeException("There is no category with this id");
        }
        categoryRepository.delete(existing);
        return "redirect:/admin/categories-list?deleted";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            new RuntimeException("There is no category with this id");
        }
        CategoryRegistrationModel categoryRegistrationModel = new CategoryRegistrationModel();
        categoryRegistrationModel.setName(category.getName());
        categoryRegistrationModel.setId(category.getId());
        model.addAttribute("category", categoryRegistrationModel);
        return "category";
    }
}
