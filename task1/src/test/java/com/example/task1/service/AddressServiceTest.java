package com.example.task1.service;

import com.example.task1.model.Address;
import com.example.task1.repository.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private AddressService addressService;

    @Test
    public void testAddAddress() {
        // Arrange
        Address address = Address.builder().id(5L)
                .location("locatio")
                .build();
        // Act
        addressService.addAddress(address);
        // Assert
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    public void testGetAddresses() {
        // Arrange
        Address address1 = Address.builder().id(5L)
                .location("nablus")
                .build();
        Address address2 = Address.builder().id(6L)
                .location("gaza")
                .build();
        List<Address> addressList = Arrays.asList(address1, address2);
        // Mock the behavior of the repository
        when(addressRepository.findAll()).thenReturn(addressList);
        // Act
        List<Address> result = addressService.getAddresses();
        // Assert
        assertEquals(2, result.size());
        assertEquals(address1, result.get(0));
        assertEquals(address2, result.get(1));
    }

    @Test
    public void testDeleteAddress() {
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(true);

        //when deleteById(addressId) is called on the mocked addressRepository,
        // nothing should happen—no action, no exception, nothing.
        doNothing().when(addressRepository).deleteById(addressId);

        // Act
        addressService.deleteAddress(addressId);

        // Assert
        verify(addressRepository).deleteById(addressId);

    }
    @Test
    public void testDeleteAddress_ThrowsExceptionWhenAddressDoesNotExist() {
        // Arrange
        Long addressId = 1L;
        when(addressRepository.existsById(addressId)).thenReturn(false);

        // Act & Assert
        assertThrows(IllegalStateException.class, () -> addressService.deleteAddress(addressId));
    }
    @Test
    public  void testUpdateAddress(){
        Address address = Address.builder().id(5L)
                .location("location")
                .build();
        when(addressRepository.findById(5L)).thenReturn(java.util.Optional.of(address));
        addressService.updateAddress(5L,"new location");
        verify(addressRepository).save(address);
        assertEquals("new location", address.getLocation());

    }
    @Test
    public void testUpdateAddress_ThrowsExceptionWhenAddressDoesNotExist() {
        // Arrange
        Long addressId = 1L;
        String newLocation = "new location";

        // Mock the repository behavior
        when(addressRepository.findById(addressId)).thenReturn(java.util.Optional.empty());
        // Act & Assert
        assertThrows(IllegalStateException.class, () -> addressService.updateAddress(addressId, newLocation));

    }
}
