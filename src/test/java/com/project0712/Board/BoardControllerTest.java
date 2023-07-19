package com.project0712.Board;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
* @WebMvcTest 여러 스프링 테스트 어노테이션 중 web에만 집중할 수 있는 어노테이션이다.
* web에만 집중하기 때문에 서비스 계층이나 레포지토리 계층, JPA등을 사용할 수 없다.
* 따라서 컨트롤러 뿐만 아니라 서비스계층, 레포지토리계층 등을 함께 사용하는 로직이라면 @AutoConfigureMockMvc를 사용한다.*/
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureMockMvc
@RequiredArgsConstructor
class BoardControllerTest {
    private final BoardServiceImpl boardServiceImpl;
    private static final String url = "/api/board/paging";

    @Test
    @DisplayName("paging의 컨트롤러부터 시작해서 데이터가 잘 전달될까?")
    void paging() {
        Pageable pageable=new PageableImpl();
        Page<BoardDTO> pagingList = boardServiceImpl.paging(pageable, 50);
        assertEquals(50,pagingList.getSize());
        List<Object> list = new ArrayList<>();

        int blockLimit = 10; //하단 페이지 번호 개수
        int startPage = (((int)Math.ceil((double)pageable.getPageNumber() / blockLimit))-1) * blockLimit + 1;
        int endPage = ((startPage - blockLimit -1) < pagingList.getTotalPages()) ? startPage + blockLimit -1 :pagingList.getTotalPages();

        pagingList.map(list::add);
        list.add(blockLimit);
        list.add(startPage);
        list.add(endPage);
    }
}