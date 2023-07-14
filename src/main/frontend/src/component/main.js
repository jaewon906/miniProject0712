import style from "../css/main.module.css"
import {Link} from "react-router-dom";
export default function Main() {

    return(
        <div className={style.container}>
            <div className={style.main}>
                <h1> 게시판 메인화면</h1>
                <Link to="/board"><button className={style.entranceBtn} type="button">입장하기</button></Link>
            </div>
        </div>
    )
}
