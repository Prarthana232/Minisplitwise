package com.splitwise.backend.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class SplitController {

    // -------------------------------
    // 🧠 In-memory storage (BEGINNER)
    // -------------------------------
    private List<String> users = new ArrayList<>();
    private List<Map<String, Object>> expenses = new ArrayList<>();


    // -------------------------------
    // 👤 USER MANAGEMENT
    // -------------------------------

    @PostMapping("/users")
    public List<String> addUser(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        if (name != null && !name.isEmpty()) {
            users.add(name);
        }
        return users;
    }

    @GetMapping("/users")
    public List<String> getUsers() {
        return users;
    }

    @DeleteMapping("/users")
    public List<String> removeUser(@RequestParam String name) {
        users.remove(name);
        return users;
    }


    // -------------------------------
    // 💰 EQUAL SPLIT
    // -------------------------------

    @PostMapping("/equal")
    public Map<String, Double> equalSplit(@RequestBody Map<String, Object> body) {

        double total = Double.parseDouble(body.getOrDefault("total", 0).toString());

        List<String> userList = new ArrayList<>();
        Object usersObj = body.get("users");

        if (usersObj instanceof List<?>) {
            for (Object u : (List<?>) usersObj) {
                userList.add(u.toString());
            }
        }

        if (userList.isEmpty()) {
            throw new RuntimeException("Users list cannot be empty");
        }

        double share = total / userList.size();

        Map<String, Double> result = new HashMap<>();

        for (String user : userList) {
            result.put(user, share);
        }

        return result;
    }


    // -------------------------------
    // 🍕 DUTCH SPLIT + SETTLEMENT
    // -------------------------------

    @PostMapping("/dutch")
    public Map<String, String> dutchSplit(@RequestBody Map<String, Object> body) {

        List<Map<String, Object>> items = new ArrayList<>();
        Object itemsObj = body.get("items");

        if (itemsObj instanceof List<?>) {
            for (Object obj : (List<?>) itemsObj) {
                items.add((Map<String, Object>) obj);
            }
        }

        double taxPercent = Double.parseDouble(body.getOrDefault("tax", 0).toString());

        Map<String, Double> balances = new HashMap<>();
        double total = 0;

        for (Map<String, Object> item : items) {

            double price = Double.parseDouble(item.get("price").toString());

            List<String> itemUsers = new ArrayList<>();
            Object usersObj = item.get("users");

            if (usersObj instanceof List<?>) {
                for (Object u : (List<?>) usersObj) {
                    itemUsers.add(u.toString());
                }
            }

            if (itemUsers.isEmpty()) continue;

            double share = price / itemUsers.size();

            for (String user : itemUsers) {
                balances.put(user, balances.getOrDefault(user, 0.0) + share);
            }

            total += price;
        }

        // Apply tax proportionally
        double tax = total * (taxPercent / 100);

        for (String user : balances.keySet()) {
            double ratio = balances.get(user) / total;
            balances.put(user, balances.get(user) + (ratio * tax));
        }

        // 👉 Convert to settlement
        return settleDebts(balances);
    }


    // -------------------------------
    // 🔄 SETTLEMENT LOGIC
    // -------------------------------

    private Map<String, String> settleDebts(Map<String, Double> balances) {

        Map<String, String> result = new HashMap<>();

        List<Map.Entry<String, Double>> positive = new ArrayList<>();
        List<Map.Entry<String, Double>> negative = new ArrayList<>();

        double avg = balances.values().stream()
                .mapToDouble(Double::doubleValue).sum() / balances.size();

        for (Map.Entry<String, Double> entry : balances.entrySet()) {
            double diff = entry.getValue() - avg;

            if (diff > 0) positive.add(Map.entry(entry.getKey(), diff));
            else negative.add(Map.entry(entry.getKey(), diff));
        }

        int i = 0, j = 0;

        while (i < positive.size() && j < negative.size()) {

            var creditor = positive.get(i);
            var debtor = negative.get(j);

            double amount = Math.min(creditor.getValue(), -debtor.getValue());

            result.put(debtor.getKey(),
                    debtor.getKey() + " pays " + creditor.getKey() + " ₹" +
                            String.format("%.2f", amount));

            positive.set(i, Map.entry(creditor.getKey(), creditor.getValue() - amount));
            negative.set(j, Map.entry(debtor.getKey(), debtor.getValue() + amount));

            if (positive.get(i).getValue() == 0) i++;
            if (negative.get(j).getValue() == 0) j++;
        }

        return result;
    }


    // -------------------------------
    // 📦 SIMPLE EXPENSE STORAGE
    // -------------------------------

    @PostMapping("/expense")
    public List<Map<String, Object>> addExpense(@RequestBody Map<String, Object> exp) {
        expenses.add(exp);
        return expenses;
    }

    @GetMapping("/expense")
    public List<Map<String, Object>> getExpenses() {
        return expenses;
    }
}