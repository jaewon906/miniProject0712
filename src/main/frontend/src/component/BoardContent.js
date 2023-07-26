import style from "../css/boardContent.module.css"
import axios from "axios";
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";

export default function BoardContent() {
    const boardId = useParams()
    const [title, setTitle] = useState();
    const [category, setCategory] = useState();
    const [content, setContent] = useState();
    const [author, setAuthor] = useState();


    useEffect(() => {
        axios.get("/api/board/browsePost", {
            params: {
                id: boardId.id
            }
        })
            .then(res => {
                setTitle(res.data.title)
                setCategory(res.data.category)
                setContent(res.data.content)
                setAuthor(res.data.author)

            })
            .catch()
    }, [boardId.id])
    const toModify = () => { // 수정 페이지로 이동
        window.location.href = "/write/" + boardId.id
    }

    const toDelete = () => {
        const ret = window.confirm("삭제 하시겠습니까?")
        if (ret) {
            axios.post("/api/board/deletePost", null, {
                params: {
                    id: boardId.id
                }
            })
                .then((res)=>{
                    if(res.data){
                        alert("삭제되었습니다.");
                        window.location.href='/board'
                    }
                    else{
                        alert("다시 시도해주세요")
                    }
                })
                .catch()
        }
    }


    return (
        <div className={style.container}>
            <div className={style.main}>
                <h1>글쓰기</h1>
                <div className={style.title_header}>
                    <div className={style.title}>
                        <p>제목 :</p>
                        <div>{title}</div>
                    </div>
                    <div>{category}</div>
                </div>

                <div className={style.contents}>
                    <div>{content}</div>
                </div>
                <div style={{marginTop: "50px", width: "50%", display: "flex", justifyContent: "space-evenly"}}>
                    {author === window.sessionStorage.getItem("ID") ?
                        <button style={{width: "120px"}} onClick={toModify} className={style.toWriteBtn}
                                type="button">수정하기</button> : ""}
                    {author === window.sessionStorage.getItem("ID") ?
                        <button style={{width: "120px"}} onClick={toDelete} className={style.toWriteBtn}
                                type="button">삭제하기</button> : ""}
                </div>
            </div>
        </div>
    )
}