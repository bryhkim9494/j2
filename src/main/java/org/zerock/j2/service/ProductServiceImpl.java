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
        // 상품 목록과 리뷰 정보를 함께 조회하는 메서드 호출하여 결과 반환
        return productRepository.listWithReview(requestDTO);
    }

    @Override
    public Long register(ProductDTO productDTO) {

        // 상품 등록을 위해 DTO를 Entity로 변환
        Product product = Product.builder().pname(productDTO.getPname()).pdesc(productDTO.getPdesc())
                .price(productDTO.getPrice()).build();

        // 상품에 첨부된 이미지 파일명들을 등록
        productDTO.getImages().forEach(fname -> {
            product.addImage(fname);
        });

        // 상품 저장 후 등록된 상품의 고유번호 반환
        return productRepository.save(product).getPno();

    }

    @Override
    public ProductDTO readOne(Long pno) {

        // 특정 상품의 상세 정보 조회
        Product product = productRepository.selectOne(pno);

        // Entity를 DTO로 변환하여 반환
        ProductDTO dto = ProductDTO.builder()
                .pno(product.getPno())
                .pname(product.getPname())
                .price(product.getPrice())
                .pdesc(product.getPdesc())
                .images(product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList()))
                .build();

        return dto;
    }

    @Override
    public void remove(Long pno) {

        // 특정 상품을 삭제하기 위해 상품 정보 조회
        Product product = productRepository.selectOne(pno);

        // 상품 삭제를 위해 삭제 플래그 변경
        product.changeDel(true);

        // 변경된 상품 정보 저장
        productRepository.save(product);

        // 상품에 첨부된 이미지 파일들을 삭제
        List<String> fileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());
        fileUploader.removeFiles(fileNames);

    }

    @Override
    public void modify(ProductDTO productDTO) {

        // 수정할 상품의 기존 정보 조회
        Optional<Product> result = productRepository.findById(productDTO.getPno());
        Product product = result.orElseThrow();

        // 기존 상품 정보를 새로운 상품 정보로 수정
        product.changePname(productDTO.getPname());
        product.changePdesc(productDTO.getPdesc());
        product.changePrice(productDTO.getPrice());

        // 기존에 첨부되었던 이미지 파일들을 삭제하기 위해 파일명들을 저장
        List<String> oldFileNames = product.getImages().stream().map(pi -> pi.getFname()).collect(Collectors.toList());

        // 기존에 첨부된 이미지들을 초기화
        product.clearImages();

        // 새로운 이미지 파일들을 추가
        productDTO.getImages().forEach(fname -> product.addImage(fname));
        log.info("===========================================");
        log.info("===========================================");
        log.info(product);
        log.info("===========================================");
        log.info("===========================================");

        // 변경된 상품 정보 저장
        productRepository.save(product);

        // 기존 파일들[=oldFileNames] 중의 productDTO.getImages()[=새로운파일]에 없는 파일을 찾기
        List<String> newFiles = productDTO.getImages();
        List<String> wantDeleteFiles = oldFileNames.stream().filter(f -> newFiles.indexOf(f) == -1).collect(Collectors.toList());

        // 이미지 파일 삭제
        fileUploader.removeFiles(wantDeleteFiles);
    }

}
