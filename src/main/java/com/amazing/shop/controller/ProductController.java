package com.amazing.shop.controller;

import com.amazing.shop.dto.ProductRegistrationModel;
import com.amazing.shop.entity.Product;
import com.amazing.shop.repository.ProductRepository;
import com.amazing.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @ModelAttribute("/product")
    public ProductRegistrationModel productRegistrationModel() {
        return new ProductRegistrationModel();
    }

    @GetMapping
    public String showProductsForm(Model model) {
        model.addAttribute("product", productRegistrationModel());
        return "product";
    }

    @PostMapping
    public String add(@ModelAttribute("product") @Valid ProductRegistrationModel registrationModel,
                           BindingResult result) {
        Product existing = productService.findByTitle(registrationModel.getTitle()).orElse(null);
        if (existing != null
                && registrationModel.getTitle() == null) {
            result.rejectValue("name", ""
                    , "Mother Fucker, there is already a product with that title!");
        }

        if (result.hasErrors()) {
            return "product";
        }

        registrationModel.setBrand(registrationModel.getBrand().trim());

        productService.save(registrationModel);
        return "redirect:/products?success";
    }

    @GetMapping("/delete/{id}")
    public String delete(Model model, @PathVariable Long id){
        Product existing = productService.findById(id).orElse(null);
        if (existing == null) {
            new RuntimeException("There is no product with this id");
        }
        productRepository.delete(existing);
        return "redirect:/products?deleted";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            new RuntimeException("There is no product with this id");
        }
        ProductRegistrationModel productRegistrationModel = new ProductRegistrationModel();
        productRegistrationModel.setId(product.getId());
        productRegistrationModel.setTitle(product.getTitle());
        productRegistrationModel.setBrand(product.getBrand());
        productRegistrationModel.setDescription(product.getDescription());
        productRegistrationModel.setThumbnail(product.getThumbnail());
        productRegistrationModel.setPrice(product.getPrice());
        productRegistrationModel.setQuantity(product.getQuantity());

        model.addAttribute("product", productRegistrationModel);
        return "/product";
    }
}
