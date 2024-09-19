package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.model.Receipt;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private Map<String, Receipt> receiptsMap = new HashMap<>();
    private Map<String, Integer> pointsMap = new HashMap<>();
    
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptsMap.put(id, receipt);

        int points = getPoints(receipt);
        pointsMap.put(id, points);

        Map<String, String> response = new HashMap<>();
        response.put("id", id);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> processPoints(@PathVariable String id) {
        if (pointsMap.containsKey(id)) {
            Map<String, Integer> response = new HashMap<>();
            response.put("points", pointsMap.get(id));
            return ResponseEntity.ok(response);
        }
        else {
            return ResponseEntity.status(404).body(null);
        }
    }

    public int getPoints(Receipt receipt) {
        int points = 0;

        String retailer = receipt.getRetailer();
        points += retailer.replaceAll("[^a-zA-Z0-9]", "").length();

        double amount = Double.parseDouble(receipt.getTotal());
        if(amount % 1 == 0) {
            points += 50;
        }

        if(amount % 0.25 == 0) {
            points += 25;
        }

        List<Item> items = receipt.getItems();
        points += (items.size() / 2) * 5;

        for (Item item : items) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                double price = Double.parseDouble(item.getPrice());
                points += Math.ceil(price * 0.2);
            }
        }

        LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate());
        int day = purchaseDate.getDayOfMonth();
        if (day % 2 != 0) {
            points += 6;
        }
        
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        if(purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}
