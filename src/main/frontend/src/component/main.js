import style from "../css/main.module.css"
import {Link} from "react-router-dom";
export default function Main() {

    return(
        <div className={style.container}>
            <div className={style.main}>
                <div className={style.header}>
                    <Link to="/signUp">회원가입 하기</Link>
                    <p style={{margin:"0px 20px"}}></p>
                    <Link to="/logIn">로그인 하기</Link>
                    <p style={{margin:"0px 20px"}}></p>
                    <Link to="withdrawal">회원 탈퇴하기</Link>
                </div>
                <h1> 게시판 메인화면</h1>
                <Link to="/login"><button className={style.entranceBtn} type="button">입장하기</button></Link>
            </div>
        </div>
    )
}
