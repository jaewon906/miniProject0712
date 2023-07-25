import style from "../css/logIn.module.css"
import axios from "axios";
import {useRef, useState} from "react";
import {Link} from "react-router-dom";

export default function LogIn() {

    const id = useRef();
    const password = useRef();
    const [myAccount, setMyAccount] = useState();

    const toLogIn = () => {
        axios.get("/api/logIn", {
            params: {
                userId: id.current.value,
                userPassword: password.current.value,
            }
        })
            .then(res => {setMyAccount(res.data)
                window.sessionStorage.setItem("key","ss");
                // window.location.href = "/"
            })
            .catch(err => {
                alert("아이디 또는 비밀번호가 잘못되었습니다.")
            })

    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<input ref={id} type="text" placeholder="아이디"/></div>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <button onClick={toLogIn}>로그인하기</button>
                <div style={{width:"65%", marginTop:"50px"}}>
                    <Link to="/findID">아이디 찾기</Link>
                    <Link to="/findPW">비밀번호 찾기</Link>
                </div>
            </div>
        </div>
    )
}