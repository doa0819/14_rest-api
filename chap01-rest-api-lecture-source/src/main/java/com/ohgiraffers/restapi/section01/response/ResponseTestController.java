package com.ohgiraffers.restapi.section01.response;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

//@Controller
@RestController     // @ResponseBody + @Controller 합친 개념이다!!!
@RequestMapping("/response")
public class ResponseTestController {

    /* 문자열 응답 test */
    @GetMapping("/hello")
//    @ResponseBody
    public String helloworld(){

        // @ResponseBody 으로 인해 view html 이 없어도 해당 데이터로 반환을 해준다!!

        System.out.println("hello world");

        return "hello world!!!";
    }

    /* 기본 자료형 test */
    @GetMapping("/random")
//    @ResponseBody
    public int getRandomNumber(){

        return (int) (Math.random() * 10) +1;
    }

    /* Object 타입 응답 */
    @GetMapping("/message")
    public Message getMessage(){

        return new Message(200,"정상 응답 완료!!");

    }

    /* List 타입 응답 */
    @GetMapping("/list")
    public List<String> getList(){

        return List.of(new String[] {"햄버거", "피자", "닭가슴살"});
    }

    @GetMapping("/map")
    public Map<Integer, String> getMap(){

        Map<Integer,String> messageMap = new HashMap<>();
        messageMap.put(200, "정상 응답 완료!!");
        messageMap.put(404, "페이지 찾을 수 없음");
        messageMap.put(500, "서버 내부 오류 -> 개발자의 잘못");

        return messageMap;
    }

    /* image response
    *   produces 설정을 해주지 않으면 이미지가 텍스트 형태로 전송된다.
    *   produces 는 response header 의 content-type 에 대한 설정이다.
    * */
    @GetMapping(value = "/image",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage() throws IOException {

        return getClass().getResourceAsStream("/images/IMG_7985.jpg").readAllBytes();
    }

    /* responseEntity 를 이용한 응답 -> HTTP 응답을 구성하는 세 가지 주요 요소인 상태 코드, 헤더, 본문을 모두 포함할 수 있는 클래스
    *    code: 200 : 정상적인 응답
    *          201 : 요청 정상적으로 처리 하고 새로운 리소스 생성
    *          400 : 잘못된 요청 (클라이언트 요청 유효하지 않음)
    *          401 : 인증 필요
    *          404 : 요청한 리소스 찾을 수 없음
    *          500 : 서버에서 요청 처리하는 동안 오류 (개발자 잘못)
    *
    *   헤더 : 키-값
    *      Content-Type: 응답 본문의 데이터 타입을 지정합니다. 예: application/json, text/html, image/jpeg.
           Authorization: 인증 정보를 포함합니다. 예: Bearer token.
           Cache-Control: 캐싱 관련 지시사항을 포함합니다. 예: no-cache, max-age=3600.
           Set-Cookie: 서버가 클라이언트에 쿠키를 설정할 때 사용합니다.
           Location: 리소스의 URL을 포함하며, 주로 리다이렉션 시 사용됩니다.

        본문 : 요청 또는 응답의 실제 데이터를 포함
        *  예) (200, " 정상응답 맞니??") 본문에 나온다.
    * */

    @GetMapping("/entity")
    public ResponseEntity<Message> getEntity(){
        // 알아서 200,400 이 나오지만 우리가 쓰는 이유는 꺼내 쓰기 위해서 이다
        return ResponseEntity.ok(new Message(200, " 정상응답 맞니??"));
    }

}
