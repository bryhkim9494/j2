package org.zerock.j2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.PageRequestDTO;
import org.zerock.j2.dto.PageResponseDTO;
import org.zerock.j2.dto.ProductDTO;
import org.zerock.j2.dto.ProductListDTO;
import org.zerock.j2.service.ProductService;
import org.zerock.j2.util.FileUploader;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController // 이 클래스를 REST 컨트롤러로 지정
@CrossOrigin // 다른 도메인으로부터의 크로스 오리진 요청을 허용
@RequestMapping("/api/products/") // 이 컨트롤러의 모든 매핑 메서드의 기본 URL 지정
@RequiredArgsConstructor // 필요한 인자를 가진 생성자를 자동으로 생성
@Log4j2 // 로깅을 위한 Lombok 어노테이션
public class ProductController {

    private final ProductService service; // ProductService 인터페이스를 구현한 서비스 객체를 주입받음

    private final FileUploader uploader; // FileUploader 클래스의 객체를 주입받음

    @PostMapping("") // POST 요청으로 "/api/products/" URL에 접근 시 실행되는 메서드
    public Map<String, Long> register(ProductDTO productDTO) {
        log.info(productDTO); // 받은 요청의 바디에 포함된 ProductDTO 객체의 정보를 로그로 출력

        List<String> fileNames = uploader.uploadFiles(productDTO.getFiles(), true); // 업로드된 파일들을 저장하고, 해당 파일명들의 리스트를 반환
        productDTO.setImages(fileNames); // ProductDTO 객체에 파일명 리스트를 설정

        Long pno = service.register(productDTO); // 받은 정보를 이용하여 상품을 등록하고 결과로 생성된 상품 번호를 반환

        return Map.of("result", pno); // 생성된 상품 번호를 결과로 반환
    }

    @PostMapping("modify") // POST 요청으로 "/api/products/modify" URL에 접근 시 실행되는 메서드
    public Map<String, Long> modify(ProductDTO productDTO) {
        // 받은 요청의 바디에 포함된 ProductDTO 객체의 정보를 로그로 출력
        log.info("------------------------------------------modify");
        log.info("------------------------------------------modify");
        log.info("------------------------------------------modify");
        log.info(productDTO);

        if (productDTO.getFiles() != null && productDTO.getFiles().size() > 0) {
            // 새로 업로드된 파일이 있는 경우, 업로드된 파일들을 저장하고, 해당 파일명들의 리스트를 반환
            List<String> uploadFileNames = uploader.uploadFiles(productDTO.getFiles(), true);
            List<String> oldFileNames = productDTO.getImages(); // 기존 파일명 리스트를 가져옴

            // 기존 파일명 리스트에 새로운 파일명들을 추가
            uploadFileNames.forEach(fname -> oldFileNames.add(fname));
        }

        // 상품 정보를 수정
        service.modify(productDTO);

        return Map.of("result", 111L); // 테스트를 위해 임의의 결과값을 반환
    }

    @GetMapping(value = "list") // GET 요청으로 "/api/products/list" URL에 접근 시 실행되는 메서드
    public PageResponseDTO<ProductListDTO> list(PageRequestDTO pageRequestDTO) {
        log.info("-------------------------------------------------");
        log.info(pageRequestDTO); // 받은 페이지 요청 정보를 로그로 출력
        return service.list(pageRequestDTO); // 페이지 요청 정보를 이용하여 상품 목록을 조회하고, 조회 결과를 반환
    }

    @GetMapping("{pno}") // GET 요청으로 "/api/products/{pno}" URL에 접근 시 실행되는 메서드
    public ProductDTO getOne(@PathVariable("pno") Long pno) {
        log.info("PNO....................." + pno); // 받은 상품 번호를 로그로 출력

        return service.readOne(pno); // 받은 상품 번호를 이용하여 해당 상품의 정보를 조회하고, 조회 결과를 반환
    }

    @DeleteMapping("{pno}") // DELETE 요청으로 "/api/products/{pno}" URL에 접근 시 실행되는 메서드
    public Map<String, Long> delete(@PathVariable("pno") Long pno) {
        log.info("pno....................." + pno); // 받은 상품 번호를 로그로 출력
        service.remove(pno); // 받은 상품 번호를 이용하여 해당 상품을 삭제

        return Map.of("result", pno); // 삭제한 상품 번호를 결과로 반환
    }
}

