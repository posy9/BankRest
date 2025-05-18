package com.example.bankcards.service;

import com.example.bankcards.entity.BlockRequest;
import com.example.bankcards.repository.BlockRequestRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bankcards.util.registry.EntityNameRegistry.BLOCK_REQUEST;

@Service
@Transactional
public class BlockRequestService extends AbstractService<BlockRequest> {

    public BlockRequestService(BlockRequestRepository repository) {
        super(repository, BLOCK_REQUEST);
    }

    @Override
    @PreAuthorize("principal.id eq request.card.user.id")
    public BlockRequest createEntity(BlockRequest request) {
        return super.createEntity(request);
    }
}
