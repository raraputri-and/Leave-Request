//package id.co.mii.clientapp.controllers;
//
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.http.HttpServletRequest;
//
//@Controller
//@RequestMapping
//@AllArgsConstructor
//public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
//
//    @RequestMapping("/error")
//    public String handleError(HttpServletRequest request,Model model){
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//
//        if (status!= null){
//            Integer statuscode= Integer.valueOf(status.toString());
//            String message;
//
//            if (statuscode == HttpStatus.INTERNAL_SERVER_ERROR.value()){
//                message= HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
//                model.addAttribute("code", statuscode);
//                model.addAttribute("message", message);
//                return "error/index";
//            } else if (statuscode == HttpStatus.NOT_FOUND.value()){
//                message= HttpStatus.NOT_FOUND.getReasonPhrase();
//                model.addAttribute("code", statuscode);
//                model.addAttribute("message", message);
//                return "error/index";
//            } else if (statuscode == HttpStatus.FORBIDDEN.value()){
//                message= HttpStatus.FORBIDDEN.getReasonPhrase();
//                model.addAttribute("code", statuscode);
//                model.addAttribute("message", message);
//                return "error/index";
//            } else if (statuscode == HttpStatus.UNAUTHORIZED.value()){
//                message= HttpStatus.UNAUTHORIZED.getReasonPhrase();
//                model.addAttribute("code", statuscode);
//                model.addAttribute("message", message);
//                return "error/index";
//            }
//            else if (statuscode == HttpStatus.CONFLICT.value()){
//                message= HttpStatus.CONFLICT.getReasonPhrase();
//                model.addAttribute("code", statuscode);
//                model.addAttribute("message", message);
//                return "error/index";
//            }
//
//        }
//        return "error/index";
//    }
//}
