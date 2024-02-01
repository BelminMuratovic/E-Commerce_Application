package com.example.ECommerce.e_commerce_core.impl;

import com.example.ECommerce.e_commerce_api.ProductService;
import com.example.ECommerce.e_commerce_api.model.product.Product;
import com.example.ECommerce.e_commerce_api.model.product.ProductCreateRequest;
import com.example.ECommerce.e_commerce_api.model.product.ProductUpdateRequest;
import com.example.ECommerce.e_commerce_core.mapper.ProductMapper;
import com.example.ECommerce.e_commerce_dao.model.ProductEntity;
import com.example.ECommerce.e_commerce_dao.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    private ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public ResponseEntity<List<Product>> getProducts() throws Exception {
        List<ProductEntity> productEntities = productRepository.findAll();
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByType(String type) throws Exception {
        List<ProductEntity> productEntities = productRepository.findByTypeContaining(type);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByName(String name) throws Exception {
        List<ProductEntity> productEntities = productRepository.findByNameContaining(name);
        List<Product> dtos = productMapper.entitiesToDtos(productEntities);
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
    public ResponseEntity<ProductEntity> getImage(final Long id) throws Exception {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if (productEntity != null) {
            ProductEntity product = new ProductEntity();
            product.setImageName(productEntity.getImageName());
            product.setImageType(productEntity.getImageType());
            product.setPicByte(decompressBytes(productEntity.getPicByte()));

            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Product> create(final ProductCreateRequest request) throws Exception {
        ProductEntity productEntity = productMapper.dtoToEntity(request);
        ProductEntity savedProduct = productRepository.save(productEntity);

        return new ResponseEntity<>(productMapper.entityToDto(savedProduct), HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<Product> uploadImage(Long id, MultipartFile request) throws Exception {
        ProductEntity productEntity = productRepository.findById(id).orElse(null);
        if (productEntity != null && request != null) {
            productEntity.setImageName(request.getOriginalFilename());
            productEntity.setImageType(request.getContentType());
            productEntity.setPicByte(compressBytes(request.getBytes()));

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Product> update(final Long productId, final ProductUpdateRequest request) throws Exception {
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity != null && request != null) {
            productMapper.updateEntity(request, productEntity);
            return new ResponseEntity<>(productMapper.entityToDto(productEntity), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @Override
    @Transactional
    public ResponseEntity<Product> delete(final Long productId) throws Exception {
        productRepository.deleteById(productId);
        ProductEntity productEntity = productRepository.findById(productId).orElse(null);
        if (productEntity == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
