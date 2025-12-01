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

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final Path IMAGES_PATH = Paths.get("uploads/images/");

    @PostConstruct
    public void init() throws IOException {
        Files.createDirectories(IMAGES_PATH);
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() throws Exception {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByType(String type) throws Exception {
        List<ProductEntity> productEntities = productRepository.findByTypeContaining(type);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByName(String name) throws Exception {
        List<ProductEntity> productEntities = productRepository.findByNameContaining(name);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        attachImagesToDto(productEntities, dtos);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> findProductById(final Long productId) throws Exception {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null) {
            Product dto = productMapper.entityToDto(productEntity);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Product> create(ProductCreateRequest request) throws Exception {
        MultipartFile image = request.getImage();
        String imageName = saveImage(image);

        ProductEntity productEntity = productMapper.dtoToEntity(request);
        productEntity.setImage(imageName);
        ProductEntity savedProduct = productRepository.save(productEntity);

        return new ResponseEntity<>(productMapper.entityToDto(savedProduct), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Product> update(final Long productId, ProductUpdateRequest request) throws Exception {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null && request != null) {
            productMapper.updateEntity(request, productEntity);

            MultipartFile image = request.getImage();
            if (image != null && !image.isEmpty()) {
                if (productEntity.getImage() != null && !productEntity.getImage().isEmpty()) {
                    Path filePath = IMAGES_PATH.resolve(productEntity.getImage());
                    Files.deleteIfExists(filePath);
                }

                String imageName = saveImage(image);
                productEntity.setImage(imageName);
            }

            return new ResponseEntity<>(productMapper.entityToDto(productEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Void> delete(final Long productId) throws Exception {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (productEntity.getImage() != null && !productEntity.getImage().isEmpty()) {
            Path filePath = IMAGES_PATH.resolve(productEntity.getImage());
            Files.deleteIfExists(filePath);
        }

        productRepository.deleteById(productId);

        boolean stillExists = productRepository.existsById(productId);
        if (!stillExists) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void attachImagesToDto(List<ProductEntity> productEntities, List<Product> dtos) throws Exception {
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
                    }
                }
            }
        }
    }

    private String saveImage(MultipartFile image) throws Exception {
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
        Files.copy(image.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);

        return imageName;
    }
}
