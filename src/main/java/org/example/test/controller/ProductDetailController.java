package org.example.test.controller;

import jakarta.servlet.http.HttpSession;
import org.example.test.model.Cart;
import org.example.test.model.Product;
import org.example.test.service.CartService;
import org.example.test.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Other imports remain the same

@Controller
@RequestMapping("/product")
public class ProductDetailController {

    private final ProductService productService;
    private final CartService cartService; // Ensure CartService is injected

    @Autowired
    public ProductDetailController(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("/detail/{id}")
    public String productDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "home/detail"; // Thymeleaf template for product details
        } else {
            return "error/404"; // Redirect to a custom 404 error page if product not found
        }
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            redirectAttributes.addFlashAttribute("error", "No cart found. Please login or create a cart.");
            return "redirect:/login";
        }

        try {
            cartService.addToCart(cart.getId(), id);
            cart = cartService.findCart(cart.getCustomer());
            session.setAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("success", "Product added to cart successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to add product to cart: " + e.getMessage());
        }

        return "redirect:/product/detail/" + id;
    }
}