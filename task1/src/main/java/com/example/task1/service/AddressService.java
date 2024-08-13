package com.example.task1.service;

import com.example.task1.model.Address;
import com.example.task1.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public Address addAddress(Address address) {
        addressRepository.save(address);
        return address;
    }

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public void deleteAddress(Long addressId) {
        boolean exists = addressRepository.existsById(addressId);
        if (!exists) {
            throw new IllegalStateException("address with id " + addressId + " does not exists");
        }
        addressRepository.deleteById(addressId);
    }

    public void updateAddress(Long addressId, String location) {
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalStateException(
                        "address with id " + addressId + " does not exists"
                ));
        address.setLocation(location);
        addressRepository.save(address);
    }
}
