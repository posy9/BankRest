package com.example.bankcards.service;

import com.example.bankcards.entity.BlockRequest;
import com.example.bankcards.repository.BlockRequestRepository;
import com.example.bankcards.repository.StatusRepository;
import org.springframework.stereotype.Service;

import static com.example.bankcards.util.registry.EntityNameRegistry.BLOCK_REQUEST;

@Service
public class BlockRequestService extends AbstractService<BlockRequest> {

    private final BlockRequestRepository blockRequestRepository;

    public BlockRequestService(BlockRequestRepository repository, StatusRepository statusRepository) {
        super(repository, BLOCK_REQUEST);
        this.blockRequestRepository = repository;
    }

//    @Override
//    public BlockRequest createEntity(BlockRequest request){
//
//    }


}
