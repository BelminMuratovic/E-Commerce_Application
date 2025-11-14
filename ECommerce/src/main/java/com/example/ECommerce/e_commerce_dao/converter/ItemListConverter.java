package com.example.ECommerce.e_commerce_dao.converter;

import com.example.ECommerce.e_commerce_api.model.order.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.List;

@Converter
public class ItemListConverter implements AttributeConverter<List<Item>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<Item> items) {
        try {
            return objectMapper.writeValueAsString(items);
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert List<Item> to JSON", e);
        }
    }

    @Override
    public List<Item> convertToEntityAttribute(String dbData) {
        try {
            if (dbData == null || dbData.isEmpty()) {
                return List.of();
            }
            return objectMapper.readValue(dbData, new TypeReference<List<Item>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Unable to convert JSON to List<Item>", e);
        }
    }
}
