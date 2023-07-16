import style from "../css/logIn.module.css"
import axios from "axios";
import {useRef, useState} from "react";

export default function Withdrawal(){

    const id = useRef();
    const password = useRef();
    const [myAccount, setMyAccount] = useState();

    const toWithdrawal = () => {

        const ret = window.confirm("ㄹㅇ로 삭제?");

        if(ret){
            axios.post("/api/withdrawal",null, {
                params: {
                    userId: id.current.value,
                    userPassword: password.current.value,
                }
            })
                .then(res => setMyAccount(res.data))
                .catch(err => console.error(err))
                .finally(el => {
                    try{
                        console.log(myAccount)
                        if(myAccount.id===null){
                            alert("아이디 또는 비밀번호가 다릅니다.")
                        }
                        else{
                            alert("그동안 이용해주셔서 감사합니다.")
                        }
                    }catch (e) {

                    }
                })
        }
        else {
            alert("취소했습니다.")
        }

    }

    return (
        <div className={style.container}>
            <div className={style.main}>
                <div>아이디<input ref={id} type="text" placeholder="아이디"/></div>
                <div>비밀번호<input ref={password} type="password" placeholder="비밀번호"/></div>
                <button onClick={toWithdrawal}>회원탈퇴 하기</button>
            </div>
        </div>
    )
}