package com.ohgiraffers.jpatest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/doa")
@Tag(name = "나 홀로 연동, JPA 연습")
public class JpaTestController {

    private final MenuService menuService;
    private List<MenuDTO> menus;

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

    @GetMapping(value = "/menu/{menuCode}", produces = "application/json; charset=UTF-8")
    @Operation(summary = "메뉴 상세 조회", description = "메뉴 코드로 메뉴 상세 조회")
    public ResponseEntity<ResponseMessage> findMenuByCode(@PathVariable int menuCode){

        MenuDTO menuList = menuService.findMenu(menuCode);

        // stream.filter 를 안쓴 이유는 service 로 menuCode 만 보내줘서 뽑아내기 때문에
        // menuList 안에 menuCode만 들어 있다.

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("menu", menuList);

        return ResponseEntity.ok()
                .body(new ResponseMessage(200,"조회 성공",responseMap));

    }

    @Operation(summary = "새로운 메뉴 등록", description = "새로운 메뉴 등록하기")
    @PostMapping(value = "/menu", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage> registMenu(@RequestBody MenuDTO newMenu){


        menuService.registMenu(newMenu);





    }





}
