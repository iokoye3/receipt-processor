package com.example.receiptprocessor;

import com.example.receiptprocessor.controller.ReceiptController;
import com.example.receiptprocessor.model.Receipt;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    private ReceiptController receiptController;

    @BeforeEach
    public void setup() {
        receiptController = new ReceiptController();
    }

    @Test
    public void testGetPoints() throws Exception {
        String json = "{\n" +
                "  \"retailer\": \"M&M Corner Market\",\n" +
                "  \"purchaseDate\": \"2022-01-01\",\n" +
                "  \"purchaseTime\": \"13:01\",\n" +
                "  \"items\": [\n" +
                "    {\n" +
                "      \"shortDescription\": \"Mountain Dew 12PK\",\n" +
                "      \"price\": \"6.49\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"shortDescription\": \"Doritos Nacho Cheese\",\n" +
                "      \"price\": \"3.99\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"total\": \"10.48\"\n" +
                "}";

        ObjectMapper objectMapper = new ObjectMapper();

        Receipt receipt = objectMapper.readValue(json, Receipt.class);

        int points = receiptController.getPoints(receipt);

        assertEquals(109, points);
    }
}