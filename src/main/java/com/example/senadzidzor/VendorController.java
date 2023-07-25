package com.example.senadzidzor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VendorController {
    private Map<Integer, Vendor> vendorMap;  // HashMap to store vendor data

    public VendorController() {
        vendorMap = new HashMap<>();
    }

    // Method to add a new vendor
    public void addVendor(int id, String name, String phone, String location) {
        Vendor vendor = new Vendor(id, name, phone, location);
        vendorMap.put(id, vendor);
    }

    // Method to remove a vendor
    public void removeVendor(int id) {
        vendorMap.remove(id);
    }

    // Method to retrieve a vendor by ID
    public Vendor getVendorById(int id) {
        return vendorMap.get(id);
    }
    // Method to retrieve all vendors
    public List<Vendor> getAllVendors() {
        return new ArrayList<>(vendorMap.values());
    }

    // Method to update vendor information
    public void updateVendor(int id, String name, String phone, String location) {
        Vendor vendor = vendorMap.get(id);
        if (vendor != null) {
            vendor.setName(name);
            vendor.setPhone(phone);
            vendor.setLocation(location);
        }
    }


    public void addVendor(Vendor vendor) {
    }
}
