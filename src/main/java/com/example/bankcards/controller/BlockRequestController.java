package com.example.bankcards.controller;

import com.example.bankcards.dto.requestdtos.BlockRequestCreateDto;
import com.example.bankcards.dto.requestdtos.BlockRequestFilterDto;
import com.example.bankcards.dto.requestdtos.BlockRequestReadDto;
import com.example.bankcards.dto.requestdtos.BlockRequestUpdateDto;
import com.example.bankcards.entity.BlockRequest;
import com.example.bankcards.service.BlockRequestService;
import com.example.bankcards.util.specification.BlockRequestSpecificationBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/block")
public class BlockRequestController extends AbstractController<BlockRequest,
        BlockRequestReadDto, BlockRequestCreateDto, BlockRequestUpdateDto, BlockRequestFilterDto> {

    public BlockRequestController(ModelMapper modelMapper,
                                  BlockRequestService service,
                                  BlockRequestSpecificationBuilder blockRequestSpecificationBuilder) {
        super(modelMapper, service, BlockRequest.class, BlockRequestReadDto.class, blockRequestSpecificationBuilder);
    }
}
