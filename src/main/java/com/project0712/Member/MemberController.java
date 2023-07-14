package com.project0712.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    @PostMapping("/api/signUp")
    public void signUp(MemberDTO memberDTO){
        memberService.save(memberDTO);
    }
    @GetMapping("/api/signUp")
    public MemberDTO sendMemberInfoToFrontend(MemberDTO memberDTO){
        return memberDTO;
    }

}
