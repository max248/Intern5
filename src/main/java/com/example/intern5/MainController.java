package com.example.intern5;

import com.example.intern5.model.Messages;
import com.example.intern5.model.Users;
import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MessageRepository messageRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @GetMapping("")
    public String viewHomePage(){
        return "home";
    }

    @PostMapping("/send_message")
    public void sendMessage(HttpServletRequest request, Model model, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String receiverId = request.getParameter("receiverId");
        String theme = request.getParameter("theme");
        String content = request.getParameter("content");

        Messages messages = new Messages();
        messages.setReceiverUser(userRepository.findUserById(Long.valueOf(receiverId)));
        messages.setTheme(theme);
        messages.setContent(content);
        Date date = new Date();
        messages.setSendDate(formatter.format(date));
        messages.setSenderUser(userRepository.findUserById(Long.valueOf(session.getAttribute("userId").toString())));
        messageRepository.save(messages);
        List<Messages> messagesList = messageRepository.findAll();
        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(messagesList));
    }

    @PostMapping("/login")
    public String viewSendMsgPage(HttpServletRequest request, HttpServletResponse response, Model model){
        String username = request.getParameter("username");
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("username",username);
        request.setAttribute("username",username);
        Users user = userRepository.findByUserName(username);
        if(user != null && user.getId()>0){
            request.setAttribute("userId",user.getId());
            httpSession.setAttribute("userId",user.getId());
            List<Messages> messagesList = messageRepository.findMessagesByReceiverID(user.getId());
            List<Users> usersList = userRepository.findAll();
            model.addAttribute("usersList",usersList);
            model.addAttribute("messagesList",messagesList);
        } else {
            user = new Users();
            user.setUsername(username);
            Date date = new Date();
            user.setDate(formatter.format(date));
            userRepository.save(user);
            user = userRepository.findByUserName(username);
            request.setAttribute("userId",user.getId());
            httpSession.setAttribute("userId",user.getId());
            List<Messages> messagesList = messageRepository.findMessagesByReceiverID(user.getId());
            List<Users> usersList = userRepository.findAll();
            model.addAttribute("usersList",usersList);
            model.addAttribute("messagesList",messagesList);
        }

        return "send_msg";
    }

    @PostMapping("/list_message")
    public void listMessages(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        HttpSession httpSession = request.getSession();
        String userId = request.getParameter("userId") != null ? request.getParameter("userId") .toString() : "";
        if(userId.length()>0){
            Users user = userRepository.findUserById(Long.parseLong(userId));
            List<Messages> messagesList = messageRepository.findNewMessagesByReceiverID(user.getId());
            messageRepository.updateReadStatus(user.getId());
            model.addAttribute("messagesList",messagesList);
            request.setAttribute("messagesList",messagesList);

            Gson gson = new Gson();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(messagesList));
        }
        System.out.println("httpSession: " + httpSession);
    }

}
