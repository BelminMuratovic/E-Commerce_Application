package com.example.ECommerce.e_commerce_core.impl;

import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_core.mapper.ProductMapper;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import com.example.ECommerce.e_commerce_dao.repository.ProductRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final Path IMAGES_PATH = Paths.get("uploads/images/");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(IMAGES_PATH);
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        logger.info("Fetched {} products", dtos.size());
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByType(String type) {
        List<ProductEntity> productEntities = productRepository.findByTypeContaining(type);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        logger.info("Fetched {} products for type '{}'", dtos.size(), type);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByName(String name) {
        List<ProductEntity> productEntities = productRepository.findByNameContaining(name);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        logger.info("Fetched {} products for name '{}'", dtos.size(), name);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> findProductById(final Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null) {
            logger.info("Product found: {}", productEntity.getName());
            Product dto = productMapper.entityToDto(productEntity);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            logger.warn("Product not found with ID: {}", productId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Product> create(ProductCreateRequest request) {
        MultipartFile image = request.getImage();
        String imageName = saveImage(image);

        ProductEntity productEntity = productMapper.dtoToEntity(request);
        productEntity.setImage(imageName);
        ProductEntity savedProduct = productRepository.save(productEntity);

        logger.info("Product created with ID: {}", savedProduct.getId());
        return new ResponseEntity<>(productMapper.entityToDto(savedProduct), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Product> update(final Long productId, ProductUpdateRequest request) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null && request != null) {
            productMapper.updateEntity(request, productEntity);

            MultipartFile image = request.getImage();
            if (image != null && !image.isEmpty()) {
                if (productEntity.getImage() != null && !productEntity.getImage().isEmpty()) {
                    Path filePath = IMAGES_PATH.resolve(productEntity.getImage());
                    try {
                        Files.deleteIfExists(filePath);
                        logger.info("Deleted old image: {}", filePath);
                    } catch (IOException e) {
                        logger.error("Failed to delete image: {}", filePath, e);
                        throw new RuntimeException("Failed to update image", e);
                    }

                }

                String imageName = saveImage(image);
                productEntity.setImage(imageName);
                logger.info("Updated image for product ID: {}", productId);
            }
            logger.info("Product ID {} updated successfully", productId);
            return new ResponseEntity<>(productMapper.entityToDto(productEntity), HttpStatus.OK);
        } else {
            logger.warn("Product ID {} not updated (not found or request null)", productId);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> delete(final Long productId) {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity == null) {
            logger.warn("Product ID {} not found", productId);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (productEntity.getImage() != null && !productEntity.getImage().isEmpty()) {
            Path filePath = IMAGES_PATH.resolve(productEntity.getImage());
            try {
                Files.deleteIfExists(filePath);
                logger.info("Deleted image: {}", filePath);
            } catch (IOException e) {
                logger.error("Failed to delete image: {}", filePath, e);
                throw new RuntimeException("Failed to update image", e);
            }
        }

        productRepository.deleteById(productId);
        logger.info("Product ID {} deleted", productId);

        boolean stillExists = productRepository.existsById(productId);
        if (!stillExists) {
            logger.info("Product ID {} deleted successfully", productId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Product ID {} still exists after deletion attempt", productId);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void attachImagesToDto(List<ProductEntity> productEntities, List<Product> dtos) {
        for (int i = 0; i < productEntities.size(); i++) {
            ProductEntity productEntity = productEntities.get(i);
            Product dto = dtos.get(i);

            if (productEntity.getImage() != null && !productEntity.getImage().isEmpty()) {
                Path imagePath = IMAGES_PATH.resolve(productEntity.getImage());
                if (Files.exists(imagePath)) {
                    try {
                        byte[] imageBytes = Files.readAllBytes(imagePath);
                        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                        dto.setImage(base64Image);
                    } catch (IOException e) {
                        dto.setImage(null);
                        logger.warn("Failed to read image file: {}", imagePath);
                    }
                }
            }
        }
    }

    private String saveImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return "";
        }

        String originalFilename = image.getOriginalFilename();
        if (originalFilename == null)
            throw new IllegalArgumentException("There is no file name");

        String extension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = originalFilename.substring(dotIndex);
            originalFilename = originalFilename.substring(0, dotIndex);
        }

        String imageName = originalFilename + "_" + System.currentTimeMillis() + extension;

        Path target = IMAGES_PATH.resolve(imageName);
        try {
            Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Saved image to {}", target);
        } catch (IOException e) {
            logger.error("Failed to save image to {}", target, e);
            throw new RuntimeException("Failed to save image", e);
        }

        return imageName;
    }
}
