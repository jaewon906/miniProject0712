import style from "../css/logIn.module.css"
import axios from "axios";
import {useRef, useState} from "react";

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
            .then(res => setMyAccount(res.data))
            .catch(err => console.error(err))


        try{ //현재 로그인 버튼을 두 번 눌러야 되는 상황
            if (myAccount.id!==null) {
                window.location.href = "/"
            } else {
                alert("아이디 또는 비밀번호가 잘못되었습니다.")
            }
        }
        catch(e){
        }
    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<input ref={id} type="text" placeholder="아이디"/></div>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <button onClick={toLogIn}>로그인하기</button>
            </div>
        </div>
    )
}