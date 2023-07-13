package org.zerock.j2.service;

import org.springframework.stereotype.Service;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.entity.Product;
import org.zerock.j2.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.zerock.j2.util.FileUploader;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
    // j1 + j2 프로젝트 합치기 나중에
    private final ProductRepository productRepository;

    private final FileUploader fileUploader;

    @Override
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO requestDTO) {
        return productRepository.listWithReview(requestDTO);
    }

    @Override
    public Long register(ProductDTO productDTO) {
        Product product = Product.builder().pname(productDTO.getPname()).pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice()).build();
        productDTO.getImages().forEach(fname -> {
            product.addImage(fname);
        });
        return productRepository.save(product).getPno();

    }

    @Override
    public ProductDTO readOne(Long pno) {
        Product product = productRepository.selectOne(pno);
        ProductDTO dto = ProductDTO.builder()
                .pno(product.getPno()).pname(product.getPname()).price(product.getPrice()).pdesc(product.getPdesc()).images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
                .build();


        return dto;
    }

    @Override
    public void remove(Long pno) {
        Product product = productRepository.selectOne(pno);
        product.changeDel(true);

        productRepository.save(product);
        List<String> fileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());
        fileUploader.removeFiles(fileNames);

    }

    @Override
    public void modify(ProductDTO productDTO) {
        // 기존의 Product를 로딩
        Optional<Product> result = productRepository.findById(productDTO.getPno());

        Product product = result.orElseThrow();
        // 기존 정보를 수정
        product.changePname(productDTO.getPname());
        product.changePdesc(productDTO.getPdesc());
        product.changePrice(productDTO.getPrice());

        // 기존 이미지 목롤들을 살린다. ----------> 나중에 비교해서 삭제
        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        // 이미지들은 clearImages( ) 한 후에
        product.clearImages();

        // 이미지 문자열들을 추가해줘야함(addImage( ))
        productDTO.getImages().forEach(fname -> product.addImage(fname));
        log.info("===========================================");
        log.info("===========================================");
        log.info(product);
        log.info("===========================================");
        log.info("===========================================");
        productRepository.save(product);
        // 그리고나서 save( )

        // 기존 파일들[=oldFileNames] 중의 productDTO.getImages()[=새로운파일]에 없는 파일을 찾기
        List<String> newFiles = productDTO.getImages();
        List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1).collect(Collectors.toList());

        fileUploader.removeFiles(wantDeleteFiles);
    }

}
