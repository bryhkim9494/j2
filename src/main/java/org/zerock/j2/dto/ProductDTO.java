package org.zerock.j2.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ProductDTO {

    private Long pno;
    private String pname;
    private String pdesc;
    private int price;
    private List<String> images;

    
    private List<MultipartFile> files; // 등록하고 수정할때 업로드된 파일 데이터를 수집하는 용도
}
