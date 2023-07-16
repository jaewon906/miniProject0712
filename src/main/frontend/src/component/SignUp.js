import style from '../css/signUp.module.css'
import {Link} from "react-router-dom";
import axios from "axios";
import {useRef, useState} from "react";

export default function SignUp() {

    const id = useRef();
    const password = useRef();
    const nickname = useRef();
    const email = useRef([]);
    const tel = useRef([]);
    const address = useRef();
    const sex = useRef([]);


    const toSignUp = () => {
        axios.post("/api/signUp", null, {
            params: {
                userId: id.current.value,
                userPassword: password.current.value,
                userNickname: nickname.current.value,
                userEmail: email.current[0].value + "@" + email.current[1].value,
                userTel: tel.current[0].value + "-" + tel.current[1].value + "-" + tel.current[2].value,
                userAddress: address.current.value,
                userSex: sex.current[0].value,
            }
        })
            .then(res => res.data)
            .catch(err => console.error(err))
        // window.location.href="/"
    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<input ref={id} type="text" placeholder="아이디"/></div>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <div>닉네임<input ref={nickname} type="text" placeholder="닉네임"/></div>
                <div>이메일
                    <div>
                        <input ref={el => email.current[0] = el} type="text" placeholder="qwer1234"/>
                        @
                        <input ref={el => email.current[1] = el} type="text" placeholder="naver.com"/>
                    </div>
                </div>
                <div>전화번호
                    <div>
                        <input ref={el => tel.current[0] = el} type="number" placeholder="010" />-
                        <input ref={el => tel.current[1] = el} type="number" placeholder="1234" />-
                        <input ref={el => tel.current[2] = el} type="number" placeholder="5678" />
                    </div>
                </div>

                <div>주소<input ref={address} type="text" placeholder="경기도 성남시 중원구 중앙로 326-21"/></div>
                <div>성별
                    <div style={{width: "70%", display:"flex", justifyContent:"space-between" }}>
                        <input ref={el => sex.current[0] = el} type="radio" value="남자" checked/>남자
                        <input ref={el => sex.current[1] = el} type="radio" value="여자"/>여자
                    </div>
                </div>

                <button onClick={toSignUp} type="button">회원가입</button>
                <div className={style.findAndSignUpArea}>
                    <Link to="/findMyAcoount">ID / PW 찾기</Link>
                    <Link to="/signUp">회원가입 하기</Link>
                </div>
            </div>
        </div>
    )
}