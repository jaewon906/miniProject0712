import style from "../css/header.module.css";
import {Link} from "react-router-dom";
import axios from "axios";

export default function Header() {

    const myNickname = document.cookie.split('=')[1]

    const toLogOut = () => {
        const ret = window.confirm("로그아웃 하시겠습니까?")
        if (ret) {
            axios.post("/api/logOut")
                .then(() =>
                {
                    alert("로그아웃 되셨습니다.")
                    window.location.href="/"
                })
                .catch(err => {
                    console.error(err)
                    alert("잠시후 다시 시도해주세요")
                })

        }
    }

    return (
        <div className={style.header}>
            <div>
                <Link to="/">홈</Link>
            </div>

            <div>
                <Link to="/signUp">회원가입 하기</Link>
                <p style={{margin: "0px 20px"}}></p>
                {myNickname ?
                    <a onClick={toLogOut}>로그아웃</a> :
                    <Link to="/logIn">로그인</Link>}
                <p style={{margin: "0px 20px"}}></p>
                {myNickname ? <Link to="withdrawal">회원 탈퇴하기</Link>:""}
            </div>
        </div>
    )
}