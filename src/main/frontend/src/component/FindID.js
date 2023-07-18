import style from "../css/logIn.module.css";
import {Link} from "react-router-dom";
import {useRef, useState} from "react";
import axios from "axios";

export default function FindID(){

    const email = useRef();
    const verification = useRef();
    const [isMessaged, setIsMessaged] = useState();
    const [isAuthenticated, setIsAuthenticated] = useState("");

    const AuthenticationEmail = () => {
        axios.post("/api/findId/sendEmail",null,{
            params:{
                userEmail : email.current.value
            }
        }).then(res=>{
            setIsMessaged(res.data)
            alert("전송 되었습니다")
        })
          .catch(()=>{
              alert("가입하신 이메일이 존재하지 않습니다.")
          })
    }


    const Authentication =()=>{
        axios.post("/api/findId/sendEmailAndVerificationCode",null,{
            params:{
                userEmail : isMessaged,
                verificationCode : verification.current.value
            }
        }).then(res=>{
            alert("인증에 성공했습니다.")
            setIsAuthenticated(res.data)
        })
            .catch(()=>{
                alert("인증에 실패했습니다. 다시 시도해주세요")
            })
    }

    return (

        <div className={style.container}>
            <div className={style.main}>
                {!isAuthenticated?(isMessaged===undefined?<div>이메일<input ref={email} type="text" placeholder="이메일"/></div>:<div>이메일<p>{isMessaged}</p></div>):""}
                {!isAuthenticated?(isMessaged===undefined?"":<div>인증번호<input ref={verification} type="text" placeholder="인증번호"/></div>):""}
                {!isAuthenticated?(isMessaged===undefined?<button onClick={AuthenticationEmail}>인증코드 전송하기</button>:""):""}
                {!isAuthenticated?(isMessaged===undefined?"":<button onClick={Authentication}>인증하기</button>):""}
                {isAuthenticated!==""?<div style={{display:"flex", justifyContent:"center"}}>고객님의 ID는 {isAuthenticated} 입니다.</div>:""}
                <div style={{width:"30%", marginTop:"50px", display:"flex", justifyContent:"center"}}>
                    <Link to="/findPW">비밀번호 찾기</Link>
                </div>
            </div>
        </div>
    )
}