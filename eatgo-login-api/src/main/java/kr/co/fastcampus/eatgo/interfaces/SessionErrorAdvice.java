package kr.co.fastcampus.eatgo.interfaces;

import kr.co.fastcampus.eatgo.domain.EmailNotExistedException;
import kr.co.fastcampus.eatgo.domain.PasswordWrongException;
import kr.co.fastcampus.eatgo.domain.RestaurantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// 해당 Annotation을 이용하여 컨트롤러의 Exception을 던져줄 수 있다.
@ControllerAdvice
public class SessionErrorAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailNotExistedException.class)
    public String handleEmailNotExisted() {
        return "{}";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PasswordWrongException.class)
    public String handlePasswordWrong() {
        // Not found 인 경우 처리로직
        return "{}";
    }
}
