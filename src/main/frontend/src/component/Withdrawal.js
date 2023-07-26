import style from "../css/logIn.module.css"
import axios from "axios";
import {useRef, useState} from "react";

export default function Withdrawal() {

    const password = useRef();
    const myInfo = document.cookie.split(';')
    const userId = myInfo[1].split('=')[1]
    const toWithdrawal = () => {

        const ret = window.confirm("ㄹㅇ로 삭제?");

        if (ret) {
            axios.post("/api/withdrawal", null, {
                params: {
                    userId: userId,
                    userPassword: password.current.value,
                }
            })
                .then(res => {
                        if (res.data === false) {
                            alert("아이디 또는 비밀번호가 다릅니다.")
                        } else {
                            alert("그동안 이용해주셔서 감사합니다.")
                            window.sessionStorage.clear();
                            window.location.href = "/"
                        }
                    }
                )
                .catch(err => {
                    alert("잠시 후 다시 시도해주세요")
                    console.error(err)
                })


        } else {
            alert("취소했습니다.")
        }

    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<p>{userId}</p></div>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <button onClick={toWithdrawal}>회원탈퇴 하기</button>
            </div>
        </div>
    )
}