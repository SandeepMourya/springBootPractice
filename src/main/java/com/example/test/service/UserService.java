package com.example.test.service;
import com.example.test.dto.AddressDTO;
import com.example.test.dto.UserRequestDTO;
import com.example.test.dto.UserResponseDTO;
import com.example.test.model.Address;
import com.example.test.model.User;
import com.example.test.model.UserRole;
import com.example.test.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor//this thing is genrating my constructor innjection for repository @RequiredArgsConstructor + final field = constructor injection without manually writing the constructor.
public class UserService {
    //private List<User> userList = new ArrayList<>();
    private final UserRepository userRepo;

    public List<UserResponseDTO> getAllUser(){

        return userRepo.findAll().stream()
                .map(x->{
                    UserResponseDTO userRespDTO = new UserResponseDTO();
                    convertUserTOUserResp(x, userRespDTO);
                    return  userRespDTO;
                }).collect(Collectors.toList());

    }


    public UserResponseDTO addUser(UserRequestDTO user){

        User u = new User();
        convertUserRequestToUser(user, u);
        userRepo.save(u);

        UserResponseDTO uResp = new UserResponseDTO();
        convertUserTOUserResp(u, uResp);
        return uResp;

    }

    public boolean updateUser(UserRequestDTO user, Long id) {

        return userRepo.findById(id)
                .map(u -> {
                    u.setName(user.getName());
                    u.setEmail(user.getEmail());
                    if (user.getAddress() != null) {
                        u.setAddress(convertAddressDTOToAddress(user.getAddress()));
                    }
                    userRepo.save(u);
                    return true;
                })
                .orElse(false);
    }


    private void convertUserTOUserResp(User x, UserResponseDTO userRespDTO) {

        userRespDTO.setId(String.valueOf(x.getId()));
        userRespDTO.setName(x.getName());
        userRespDTO.setEmail(x.getEmail());
        userRespDTO.setUserRole(x.getUserRole());

        if (x.getAddress() != null) {
            userRespDTO.setAddress(convertAddressToAddressDTO(x.getAddress()));
        }


    }

    private void convertUserRequestToUser(UserRequestDTO userRequestDTO, User user) {

        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserRole(UserRole.CUSTOMER);

        if (userRequestDTO.getAddress() != null) {
            user.setAddress(convertAddressDTOToAddress(userRequestDTO.getAddress()));
        }

    }

    private Address convertAddressDTOToAddress(AddressDTO addressDTO) {
        Address address = new Address();

        address.setCountry(addressDTO.getCountry());
        address.setState(addressDTO.getState());
        address.setAddress(addressDTO.getAddress());

        return address;
    }

    private AddressDTO convertAddressToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setCountry(address.getCountry());
        addressDTO.setState(address.getState());
        addressDTO.setAddress(address.getAddress());

        return addressDTO;
    }


    public UserResponseDTO getUser(Long id) {

        return userRepo.findById(id)
                .map(x -> {
                    UserResponseDTO userResp = new UserResponseDTO();
                    convertUserTOUserResp(x, userResp);
                    return userResp;
                }).orElseGet(() -> new UserResponseDTO());
                //.orElseThrow(() -> new RuntimeException("User not found"));

    }
}
