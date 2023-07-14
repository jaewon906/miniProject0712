import style from "../css/boardMain.module.css"
import {Link, useParams} from "react-router-dom";
import axios from "axios";
import {useEffect, useState} from "react";
export default function BoardMain(){
    const urlParams = useParams();

    const selectList=[10, 30, 50, 100]
    const [boardListAmount, setBoardListAmount] = useState([{}]);
    const [getBoard, setGetBoard] = useState([]);
    const [categoryTemp,setCategoryTemp] = useState("");
    let sortedList=[{}];
    let i=0
    let j=0;
    useEffect(()=>{
        axios.get("/api/board")
            .then(res=>  setGetBoard(res.data))
            .catch(err => console.log(err))

        switch(urlParams.id){
            case "1": setCategoryTemp("자유게시판");break;
            case "2": setCategoryTemp("공략게시판");break;
            case "3": setCategoryTemp("공부게시판");break;
            case "4": setCategoryTemp("꿀팁게시판");break;
            default:setCategoryTemp("전체게시글")
        }
    },[urlParams.id])

    const boardAmount = (e) =>{ // 게시글 개수 조절
         setBoardListAmount(e.target.value);
        axios.post("/api/board",)
             .then(res=> console.log(res))
             .catch(err => console.log(err))

    }
    const setCategory =() =>{
    }

    return(
        <div className={style.container}>
            <div className={style.main}>
                <h1>{categoryTemp}</h1>
                <div className={style.category}>
                    <Link to="/board/category/1"><div>자유게시판</div></Link>
                    <Link to="/board/category/2"><div>공략게시판</div></Link>
                    <Link to="/board/category/3"><div>공부게시판</div></Link>
                    <Link to="/board/category/4"><div>꿀팁게시판</div></Link>
                </div>
                <div className={style.boardListAll_header}>
                    <input type={"text"} className={style.find} placeholder={"검색어를 입력하세요"}/>

                    <div style={{marginLeft:"50px", marginRight:"10px"}}>글 개수 : </div>
                    <select onChange={boardAmount}>
                        {selectList.map(el => {
                            return(
                                <option key={el} value={el}>{el}개</option>
                            )
                        })}
                    </select>
                </div>
                <div className={style.no_title_author_time}>
                    <div>번호</div>
                    <div>제목</div>
                    <div>작성자</div>
                    <div>작성시간</div>
                    <div>조회수</div>
                    <div>좋아요</div>
                </div>

                <div className={style.boardListAll}>

                    {getBoard.map(el=>{

                        if(categoryTemp==="전체게시글"){
                            sortedList[i++]=el;
                        }
                        if(categoryTemp===el.category){
                            sortedList[j++]=el;
                        }
                    })}
                    {sortedList.map(el=>{
                        let yyyy = "";
                        let MM ="";
                        let dd = "";
                        console.log(el);
                        try{
                            yyyy = el.boardCreatedTime.substring(0,5);
                            MM = el.boardCreatedTime.substring(5,8);
                            dd = el.boardCreatedTime.substring(8,10);
                        }
                        catch (e){
                        }

                        return(
                            <div className={style.list} key={el.id}>
                                <div style={{width:"4%"}}>{el.id}</div>
                                <div style={{width:"45%"}}><Link to={"No="+urlParams.id}>{el.title}</Link></div>
                                <div style={{width:"20%"}}>{el.author}</div>
                                <div style={{width:"11%"}}>{yyyy+MM+dd}</div>
                                <div style={{width:"10%"}}>{el.hit}</div>
                                <div style={{width:"10%"}}>{el.boardLikes}</div>

                            </div>
                        )
                    })}
                </div>
               <Link to="/write"><button className={style.toWriteBtn}  type="button">글쓰기</button></Link>
            </div>
        </div>
    )
}