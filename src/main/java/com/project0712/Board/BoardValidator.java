package com.project0712.Board;

import com.project0712.Member.MemberDTO;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BoardValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        //검증할 데이터의 타입을 검사.
        //대상 객체의 클래스를 인자로 받아서 검사함
        return clazz.isAssignableFrom(MemberDTO.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        //유효성 검사를 수행할 대상 객체와 검사 결과를 저장할 객체를 인자로 받음
        BoardDTO boardDTO= (BoardDTO) target;

        if(boardDTO.getAuthor().length()<3){
            errors.rejectValue("author", "userId는 3글자 이상입니다.");

        }

        if(StringUtils.hasText(boardDTO.getTitle())){
            errors.rejectValue("title","입력되었습니다.");
        }

    }
}
