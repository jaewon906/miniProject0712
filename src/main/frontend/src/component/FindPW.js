import style from "../css/logIn.module.css";
import {Link} from "react-router-dom";
import {useRef, useState} from "react";
import axios from "axios";

export default function FindID() {

    const email = useRef();
    const userId = useRef();
    const newPassword = useRef()
    const confirmNewPassword = useRef()
    const verification = useRef();
    const [isCodeSent, setIsCodeSent] = useState();
    const [sentMyId, setSentMyId] = useState();
    const [isAuthenticated, setIsAuthenticated] = useState("");
    const [checkPassword, setCheckPassword] = useState("");
    const [changeFontColor, setChangeFontColor] = useState(true)
    const [modifyPasswordResult, setModifyPasswordResult] = useState()


    const AuthenticationEmail = () => {
        axios.post("/api/findPw/sendEmailAndUserId", null, {
            params: {
                userEmail: email.current.value,
                userId: userId.current.value
            }
        }).then(res => {
            setIsCodeSent(res.data[1])
            setSentMyId(res.data[0])
            alert("전송 되었습니다")
        })
            .catch(() => {
                alert("가입하신 아이디 또는 이메일이 존재하지 않습니다.")
            })
    }


    const Authentication = () => {
        axios.post("/api/findPw/sendEmailAndVerificationCodeAndUserId", null, {
            params: {
                userId: sentMyId,
                userEmail: isCodeSent,
                verificationCode: verification.current.value
            }
        }).then(res => {
            alert("인증에 성공했습니다.")
            setIsAuthenticated(res.data)
        })
            .catch(() => {
                alert("인증에 실패했습니다. 다시 시도해주세요")
            })
    }
    const a = () => {
        console.log(isCodeSent)
        console.log(sentMyId)
        console.log(isAuthenticated)
        console.log(modifyPasswordResult)
    }

    const modifyPassword = () => {
        if (newPassword.current.value === confirmNewPassword.current.value) {
            const ret = window.confirm("변경하시겠습니까?")
            if (ret) {

                axios.post("/api/findPw/modifyPassword", null, {
                    params: {
                        userId: sentMyId,
                        userPassword: newPassword.current.value
                    }
                })
                    .then(res => {setModifyPasswordResult(res.data)
                        alert("변경되었습니다")
                        window.location.href="/"
                    })
                    .catch(() => {
                        alert("잠시 후 다시 시도해주세요")
                    })
            }
        } else {
            alert("비밀번호를 확인해주세요")
        }
    }
    const matchPassword = () => {
        if (newPassword.current.value === confirmNewPassword.current.value) {
            setCheckPassword("비밀번호가 일치합니다.")
            setChangeFontColor(false)
        } else {
            setCheckPassword("비밀번호가 일치하지 않습니다.")
            setChangeFontColor(true)
        }
    }

    return (

        <div className={style.container}>
            <div className={style.main}>
                {!isAuthenticated ? (isCodeSent === undefined ?
                    <div>아이디<input ref={userId} type="text" placeholder="아이디"/></div> :
                    <div>아이디<p>{sentMyId}</p></div>) : ""}
                {!isAuthenticated ? (isCodeSent === undefined ?
                    <div>이메일<input ref={email} type="text" placeholder="이메일"/></div> :
                    <div>이메일<p>{isCodeSent}</p></div>) : ""}
                {!isAuthenticated ? (isCodeSent === undefined ? "" :
                    <div>인증번호<input ref={verification} type="text" placeholder="인증번호"/></div>) : ""}
                {!isAuthenticated ? (isCodeSent === undefined ?
                    <button onClick={AuthenticationEmail}>인증코드 전송하기</button> : "") : ""}
                {!isAuthenticated ? (isCodeSent === undefined ? "" :
                    <button onClick={Authentication}>인증하기</button>) : ""}
                {isAuthenticated !== "" ? <>
                        {/*<input type="hidden" ref={id}/>*/}
                        <div>비밀번호 <input style={{width: "150px"}} type="password" ref={newPassword}/></div>
                        <div>비밀번호 확인<input onInput={matchPassword} style={{width: "150px"}} type="password"
                                           ref={confirmNewPassword}/></div>
                        {changeFontColor ?
                            <p style={{fontSize: "12px", color: "red", marginTop: "20px"}}>{checkPassword}</p> :
                            <p style={{fontSize: "12px", color: "green", marginTop: "20px"}}>{checkPassword}</p>}
                        <button onClick={modifyPassword}>변경하기</button>
                    </>
                    : ""}
                <div style={{width: "30%", marginTop: "50px", display: "flex", justifyContent: "center"}}>
                    <Link to="/findId">아이디 찾기</Link>
                    <button onClick={a}>nooler</button>
                </div>
            </div>
        </div>
    )
}