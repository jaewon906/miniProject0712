import style from "../css/header.module.css";
import {Link} from "react-router-dom";

export default function Header() {
    return (
        <div className={style.header}>
            <div>
                <Link to="/">홈</Link>
            </div>

            <div>
                <Link to="/signUp">회원가입 하기</Link>
                <p style={{margin: "0px 20px"}}></p>
                <Link to="/logIn">로그인 하기</Link>
                <p style={{margin: "0px 20px"}}></p>
                <Link to="withdrawal">회원 탈퇴하기</Link>
            </div>
        </div>
    )
}