package com.ohgiraffers.jpatest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doa")
@Tag(name = "나 홀로 연동, JPA 연습")
public class JpaTestController {

    private final MenuService menuService;

    @Autowired
    public JpaTestController(MenuService menuService){
        this.menuService = menuService;
    }


    @Operation(summary = "전체 메뉴 조회", description = "우리 사이트의 전체 메뉴 조회")
    @GetMapping(value = "/menu", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage> findAllMenu(){

        // DTO 를 기반으로 데이터베이스에서 값을 꺼내오고
        List<MenuDTO> menuList = menuService.findAllMenu();

        menuList.forEach(System.out::println);
        // Message 에서 Map 으로 받았기 때문에 Map 만 쓰기 가능
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("menuList", menuList);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회성공",responseMap);


        return new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }







}
