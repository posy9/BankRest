package com.example.bankcards.controller;

import com.example.bankcards.dto.requestdtos.BlockRequestCreateDto;
import com.example.bankcards.dto.requestdtos.BlockRequestFilterDto;
import com.example.bankcards.dto.requestdtos.BlockRequestReadDto;
import com.example.bankcards.dto.requestdtos.BlockRequestUpdateDto;
import com.example.bankcards.entity.BlockRequest;
import com.example.bankcards.security.service.SecurityService;
import com.example.bankcards.service.BlockRequestService;
import com.example.bankcards.util.specification.BlockRequestSpecificationBuilder;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.bankcards.util.registry.ErrorMessagesRegistry.CARD_BELONG;

@RestController
@RequestMapping("/api/block")
public class BlockRequestController extends AbstractController<BlockRequest,
        BlockRequestReadDto, BlockRequestCreateDto, BlockRequestUpdateDto, BlockRequestFilterDto> {

    private final SecurityService securityService;

    public BlockRequestController(ModelMapper modelMapper,
                                  BlockRequestService service,
                                  BlockRequestSpecificationBuilder blockRequestSpecificationBuilder, SecurityService securityService) {
        super(modelMapper, service, BlockRequest.class, BlockRequestReadDto.class, blockRequestSpecificationBuilder);
        this.securityService = securityService;
    }

    @Override
    @PostMapping
    public BlockRequestReadDto createEntity(@RequestBody @Valid BlockRequestCreateDto blockRequestCreateDto) {
        if (securityService.checkCardBelonging(blockRequestCreateDto.getCard().getId())) {
            return super.createEntity(blockRequestCreateDto);
        } else {
            throw new IllegalStateException(CARD_BELONG.getMessage());
        }
    }
}
