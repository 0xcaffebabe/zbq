package wang.ismy.zbq.handler;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import wang.ismy.zbq.dto.Result;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result handle(MethodArgumentNotValidException e){
        System.out.println(e.getClass());
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return result;
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result hanndle1(Exception e){
        System.out.println(e.getClass());
        Result result = new Result();
        result.setSuccess(false);
        result.setMsg(e.getMessage());
        return result;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseBody
    public Result hanndle2(MaxUploadSizeExceededException e){

        Result result = new Result();
        result.setSuccess(false);
        result.setMsg("文件上传限制256KB");
        return result;
    }
}
