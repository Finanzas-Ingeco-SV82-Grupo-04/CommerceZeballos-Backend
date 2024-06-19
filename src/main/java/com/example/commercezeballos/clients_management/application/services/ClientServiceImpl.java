package com.example.commercezeballos.clients_management.application.services;
import com.example.commercezeballos.clients_management.application.dtos.response.ClientResponseDto;
import com.example.commercezeballos.clients_management.infraestucture.ClientManageRepository;
import com.example.commercezeballos.security_management.domain.entities.Client;
import com.example.commercezeballos.security_management.infraestructure.repositories.UserRepository;
import com.example.commercezeballos.shared.config.ModelMapperConfig;
import com.example.commercezeballos.shared.exception.ResourceNotFoundException;
import com.example.commercezeballos.shared.model.dto.response.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class ClientServiceImpl implements IClientService{
    private final ClientManageRepository clientRepository;
    private final ModelMapperConfig modelMapperConfig;
    private final UserRepository userRepository;

    public ClientServiceImpl(ClientManageRepository clientRepository, ModelMapperConfig modelMapperConfig, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.modelMapperConfig = modelMapperConfig;
        this.userRepository = userRepository;
    }

    @Override
    public List<ClientResponseDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream()
                .map(client -> modelMapperConfig.modelMapper().map(client, ClientResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse<ClientResponseDto> getClientByDni(String dni) {

        var client = clientRepository.findByDni(dni)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found with dni: " + dni));

        var clientResponseDto = modelMapperConfig.modelMapper().map(client, ClientResponseDto.class);

        return new ApiResponse<>(true, "Client found", clientResponseDto);
    }

    @Override
    @Transactional
    public boolean deleteByDni(String dni) {
        Optional<Client> clientOptional = clientRepository.findByDni(dni);
        if (clientOptional.isPresent()) {
            Long userId = clientOptional.get().getId();
            userRepository.deleteUserRolesByUserId(userId);
            userRepository.deleteUserById(userId);
            return true;
        }

        return false;
    }

}
