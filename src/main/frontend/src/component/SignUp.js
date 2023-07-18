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
    const [idState, setIdState] = useState("");
    const [nicknameState, setNicknameState] = useState("");
    const [emailState, setEmailState] = useState("");


    const validateID = () => {
        axios.get("/api/signUp/id", {
            params: {
                userId: id.current.value
            }
        })
            .then(res => setIdState(res.data))
            .catch()

    }
    const validateNickname = () => {
        axios.get("/api/signUp/nickname", {
            params:
                {
                    userNickname: nickname.current.value
                }
        })
            .then(res => setNicknameState(res.data))
            .catch()
    }
    const validateEmail = () => {
        axios.get("/api/signUp/email", {
            params: {
                userEmail:  email.current[0].value + "@" + email.current[1].value
            }
        })
            .then(res => setEmailState(res.data))
            .catch()
    }
    const toSignUp = () => {
        if(idState.length===0 && nicknameState.length===0 && emailState.length===0){
            const ret = window.confirm("등록하시겠습니까?")
            if(ret){
                alert("회원가입이 완료되었습니다.")
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
            }



            window.location.href="/"
        }
        else{
            alert("회원가입 양식을 다시 확인해주세요")
        }

    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<input onInput={validateID} ref={id} type="text" placeholder="아이디"/></div>
                <p style={{color:"red", fontSize:"10px"}}>{idState}</p>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <div>닉네임<input onInput={validateNickname} ref={nickname} type="text" placeholder="닉네임"/></div>
                <p style={{color:"red", fontSize:"10px"}}>{nicknameState}</p>
                <div>이메일
                    <div>
                        <input onInput={validateEmail} ref={el => email.current[0] = el} type="text" placeholder="qwer1234"/>
                        @
                        <input onInput={validateEmail} ref={el => email.current[1] = el} type="text"
                               placeholder="naver.com"/>
                    </div>
                </div>
                <p style={{color:"red", fontSize:"10px"}}>{emailState}</p>
                <div>전화번호
                    <div>
                        <input ref={el => tel.current[0] = el} type="number" placeholder="010"/>-
                        <input ref={el => tel.current[1] = el} type="number" placeholder="1234"/>-
                        <input ref={el => tel.current[2] = el} type="number" placeholder="5678"/>
                    </div>
                </div>

                <div>주소<input ref={address} type="text" placeholder="경기도 성남시 중원구 중앙로 326-21"/></div>
                <div>성별
                    <div style={{width: "70%", display: "flex", justifyContent: "space-between"}}>
                        <input ref={el => sex.current[0] = el} type="radio" value="남자" checked/>남자
                        <input ref={el => sex.current[1] = el} type="radio" value="여자"/>여자
                    </div>
                </div>

                <button onClick={toSignUp} type="button">회원가입</button>
                <div className={style.findAndSignUpArea}>
                    <Link to="/findId">아이디 찾기</Link>
                    <Link to="/findPw">비밀번호 찾기</Link>
                </div>
            </div>
        </div>
    )
}