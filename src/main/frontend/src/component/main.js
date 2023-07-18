import style from "../css/main.module.css"
export default function Main() {

    const toLogIn=()=>{
        const sessionStorage = window.sessionStorage
        const getID = sessionStorage.getItem("ID")
        console.log(getID)
        if(getID===null){
            const ret = window.confirm("로그인 서비스입니다. 로그인 하시겠습니까?")
            if(ret){

                window.location.href="/login"
            }
        }
        else window.location.href="/board"
    }
    return(
        <div className={style.container}>
            <div className={style.main}>
                <h1> 게시판 메인화면</h1>
                <button onClick={toLogIn} className={style.entranceBtn} type="button">입장하기</button>
            </div>
        </div>
    )
}
