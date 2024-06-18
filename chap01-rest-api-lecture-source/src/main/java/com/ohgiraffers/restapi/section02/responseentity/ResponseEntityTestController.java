package com.ohgiraffers.restapi.section02.responseentity;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/entity")
public class ResponseEntityTestController {

    /* 필기.
     *   ResponseEntity 란?
     *   결과 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스이다.
     *   HttpStatus, HttpHeaders, HttpBody 를 포함하고 있다.
     *  */

    private List<UserDTO> users;

    public ResponseEntityTestController() {
        users = new ArrayList<>();

        users.add(new UserDTO(1,"user01","pass01","너구리", LocalDate.now()));
        users.add(new UserDTO(2,"user02","pass02","코알라", LocalDate.now()));
        users.add(new UserDTO(3,"user03","pass03","푸바오", LocalDate.now()));
    }

    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        Map<String,Object> responseMap = new HashMap<>();

        responseMap.put("users",users);
        ResponseMessage responseMessage = new ResponseMessage(200,"조회 성공",responseMap);

        return new ResponseEntity<>(responseMessage,headers, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userNo}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable("userNo") int userNo ){

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(new MediaType("application","json", Charset.forName("UTF-8")));

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);

        Map<String,Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        /* 메소드 체이닝 */
        return ResponseEntity.ok()
//                .headers(headers)
                .body(new ResponseMessage(200,"조회성공",responseMap));

    }

    @PostMapping( "/users")
    // <?> -> 반환 타입이 머인지 모를때 (권장XX)
    // @RequestBody : post   // @RequestParam : 쿼리가 있을때 값 받아오는거 (예) form method ="get")
    // @ResponseBody -> controller -> view 데이터나감  @RequestBody : view -> controller
    //
    public ResponseEntity <?> regist(@RequestBody UserDTO newUser){

        System.out.println("newUser 잘 들어오니? " + newUser);

       int lastUserNo =  users.get(users.size() -1).getNo();
       newUser.setNo(lastUserNo + 1);
       users.add(newUser);

       return ResponseEntity
               // 201 상태코드 -> 등록 관련(생성)
               // List 에 마지막 애 꺼내오기
               .created(URI.create("/entity/users/" + users.get(users.size() -1).getNo())).build();
    }


    /* 수정 */
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDTO modifyInfo){

        // userNo 값으로 한행 식별
        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        foundUser.setId(modifyInfo.getId());
        foundUser.setPwd(modifyInfo.getPwd());
        foundUser.setName(modifyInfo.getName());

        return ResponseEntity.created(URI.create("/entity/users/" + userNo)).build();

    }

    /* 삭제 */
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo){

        UserDTO foundUser = users.stream().filter(user -> user.getNo() == userNo).collect(Collectors.toList()).get(0);
        users.remove(foundUser);

        return ResponseEntity.noContent().build();
    }

    // 상태 코드 공부
    // 200,400,500


}
